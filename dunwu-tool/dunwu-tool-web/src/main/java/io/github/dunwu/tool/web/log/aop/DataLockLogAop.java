package io.github.dunwu.tool.web.log.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.data.mybatis.DaoInfo;
import io.github.dunwu.tool.data.mybatis.util.MybatisPlusUtil;
import io.github.dunwu.tool.data.util.ReflectionUtil;
import io.github.dunwu.tool.web.SpringUtil;
import io.github.dunwu.tool.web.aop.entity.MethodInfo;
import io.github.dunwu.tool.web.aop.util.AopUtil;
import io.github.dunwu.tool.web.log.annotation.DataLockLog;
import io.github.dunwu.tool.web.log.entity.DataLockLogInfo;
import io.github.dunwu.tool.web.log.entity.DataLockLogRecord;
import io.github.dunwu.tool.web.log.service.DataLockLogService;
import io.github.dunwu.tool.web.log.service.TableColumnConfigService;
import io.github.dunwu.tool.web.log.support.DataLockLogParser;
import io.github.dunwu.tool.web.log.support.SpElValueParser;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * {@link DataLockLog} 注解的处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class DataLockLogAop {

    private final DataLockLogService dataLockLogService;
    private final TableColumnConfigService tableColumnConfigService;
    private final SecurityService securityService;
    private final SpElValueParser spElValueParser;

    @Pointcut("@annotation(io.github.dunwu.tool.web.log.annotation.DataLockLog)")
    public void pointcut() { }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {

        // 反射获取类、方法、参数信息
        MethodInfo methodInfo = AopUtil.getMethodInfo(joinPoint);

        try {
            DataLockLogInfo logInfo = DataLockLogParser.parse(methodInfo.getMethod(), methodInfo.getClass());
            if (logInfo != null) {
                DaoInfo daoInfo = MybatisPlusUtil.getDaoInfo(logInfo.getTableName());
                doBefore(methodInfo, logInfo, daoInfo);
            }
        } catch (Exception e) {
            log.error("方法执行前的日志记录解析异常", e);
        }
    }

    public void doBefore(MethodInfo methodInfo, DataLockLogInfo logInfo, DaoInfo daoInfo) {
        if (logInfo == null || daoInfo == null) {
            return;
        }

        // 获取需要解析的表达式
        List<String> spElTemplates = CollectionUtil.newArrayList(logInfo.getEntity());
        Map<String, String> expMap = spElValueParser.processBeforeExecuteFunctionTemplate(spElTemplates, methodInfo);
        if (MapUtil.isEmpty(expMap)) {
            log.error("解析 SpEL 失败！");
            return;
        }

        // 根据注解信息获取 Dao 方法
        DaoMethod daoMethod;
        try {
            daoMethod = getDaoMethod(logInfo);
            if (daoMethod == null) {
                log.error("根据注解信息 {} 获取 DAO 方法失败！", JSONUtil.toJsonStr(logInfo));
                return;
            }
        } catch (Exception e) {
            log.error("根据注解信息 {} 获取 DAO 方法异常！", JSONUtil.toJsonStr(logInfo));
            return;
        }

        String json = expMap.get(logInfo.getEntity());
        Object entity = JSONUtil.toBean(json, daoInfo.getEntityClass());
        if (entity instanceof Collection) {
            Collection list = (Collection) entity;
            List<Object> ids = new ArrayList<>();
            for (Object obj : list) {
                Object id = ReflectionUtil.getFieldValue(obj, logInfo.getId());
                if (id == null) {
                    continue;
                }
                ids.add(id);
            }
            try {
                List<?> oldList = (List<?>) daoMethod.getMethod().invoke(daoMethod.getDaoInstance(), ids);
                if (CollectionUtil.isEmpty(oldList)) {
                    return;
                }
                log.info("旧记录列表：{}", JSONUtil.toJsonStr(oldList));
            } catch (Exception e) {
                log.error("根据注解信息 {} 获取旧记录异常！", JSONUtil.toJsonStr(logInfo));
            }
        } else {
            Object id = ReflectionUtil.getFieldValue(entity, logInfo.getId());
            if (id == null) {
                log.error("未获取到 ID！");
                return;
            }

            try {
                List<?> oldList = (List<?>) daoMethod.getMethod().invoke(daoMethod.getDaoInstance(),
                    Collections.singleton(id));
                if (CollectionUtil.isEmpty(oldList)) {
                    return;
                }
                Object oldEntity = oldList.get(0);
                tryLocked(logInfo, id, oldEntity, entity);
                log.info("旧记录：{}", JSONUtil.toJsonStr(oldEntity));
            } catch (Exception e) {
                log.error("根据注解信息 {} 获取旧记录异常！", JSONUtil.toJsonStr(logInfo));
            }
        }
    }

    public DaoMethod getDaoMethod(DataLockLogInfo logInfo) {

        if (logInfo == null) {
            return null;
        }

        List<String> params = StrUtil.split(logInfo.getQueryMethod(), "#");
        if (CollectionUtil.isEmpty(params)) {
            log.error("{} 不是有效的方法！", logInfo.getQueryMethod());
        }

        String daoClassName = params.get(0);
        String methodName = params.get(1);
        Class<?> daoClass = null;
        try {
            daoClass = Class.forName(daoClassName);
        } catch (ClassNotFoundException e) {
            log.error("解析查询方法失败！", e);
            return null;
        }

        Object daoInstance = SpringUtil.getBean(daoClass);
        Method method = ReflectionUtil.getMethodByName(daoClass, methodName);
        return new DaoMethod(daoInstance, method);
    }

    public void tryLocked(DataLockLogInfo logInfo, Object id, Object oldEntity, Object newEntity) {
        if (oldEntity == null || newEntity == null) {
            return;
        }

        Set<String> lockedColumns = tableColumnConfigService.getLockedColumns(logInfo.getTableName());
        if (CollectionUtil.isEmpty(lockedColumns)) {
            return;
        }

        List<DataLockLogRecord> records = new ArrayList<>();
        Date date = new Date();
        Long userId = securityService.getCurrentUserId();
        String username = securityService.getCurrentUsername();
        for (String column : lockedColumns) {
            try {
                Object oldValue = ReflectionUtil.getFieldValue(oldEntity, column);
                if (oldValue == null) {
                    continue;
                }

                Object newValue = ReflectionUtil.getFieldValue(newEntity, column);
                if (newValue == null) {
                    continue;
                }

                if (Objects.equals(oldValue, newValue)) {
                    continue;
                }

                DataLockLogRecord record = DataLockLogRecord.builder()
                                                            .schemaName("")
                                                            .tableName(logInfo.getTableName())
                                                            .columnName(column)
                                                            .rowId(String.valueOf(id))
                                                            .cellValue(String.valueOf(newValue))
                                                            .operation(logInfo.getOperation())
                                                            .operatorId(userId)
                                                            .operatorName(username)
                                                            .createTime(date)
                                                            .build();
                records.add(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (CollectionUtil.isNotEmpty(records)) {
            dataLockLogService.batchSave(records);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaoMethod {

        private Object daoInstance;
        private Method method;

    }

}
