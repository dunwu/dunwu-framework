package io.github.dunwu.tool.web.log.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.net.IpUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.entity.LogRecord;
import io.github.dunwu.tool.web.log.entity.MethodInfo;
import io.github.dunwu.tool.web.log.entity.OperationLogInfo;
import io.github.dunwu.tool.web.log.service.LogRecordService;
import io.github.dunwu.tool.web.log.support.LogRecordContext;
import io.github.dunwu.tool.web.log.support.OperationLogParser;
import io.github.dunwu.tool.web.log.support.SpElValueParser;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link OperationLog} 注解的处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class OperationLogAspect {

    private final String appName;
    private final LogRecordService logRecordService;
    private final SecurityService securityService;
    private final SpElValueParser spElValueParser;
    private final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Pointcut("@annotation(io.github.dunwu.tool.web.log.annotation.OperationLog)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 执行方法前，记录起始时间
        currentTime.set(System.currentTimeMillis());

        // 反射获取类、方法、参数信息
        MethodInfo methodInfo = getMethodInfo(joinPoint);

        LogRecordContext.putEmptySpan();

        Collection<OperationLogInfo> operations = null;
        Map<String, String> functionNameAndReturnMap = new HashMap<>();
        try {
            operations = OperationLogParser.parseLogRecordOpsList(methodInfo.getMethod(), methodInfo.getClass());
            if (CollectionUtil.isNotEmpty(operations)) {
                List<String> spElTemplates = getBeforeExecuteFunctionTemplate(operations);
                functionNameAndReturnMap =
                    spElValueParser.processBeforeExecuteFunctionTemplate(spElTemplates, methodInfo);
            }
        } catch (Exception e) {
            log.error("方法执行前的日志记录解析异常", e);
        }

        // 执行方法
        ExecuteResult executeResult = new ExecuteResult(true, null, "");
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            executeResult = new ExecuteResult(false, e, e.toString());
        }

        // 计算方法执行时间
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();

        try {
            handle(methodInfo, operations, functionNameAndReturnMap, executeResult, result, time);
        } catch (Exception e) {
            log.error("操作日志解析异常", e);
        } finally {
            LogRecordContext.clear();
        }

        if (executeResult.throwable != null) {
            throw executeResult.throwable;
        }
        return result;
    }

    @Async
    public void handle(MethodInfo methodInfo, Collection<OperationLogInfo> operations,
        Map<String, String> functionNameAndReturnMap, ExecuteResult executeResult, Object result, long time) {
        if (CollectionUtil.isEmpty(operations)) {
            return;
        }

        // 获取用户身份信息、请求信息
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        String address = IpUtil.getRegionName(requestIdentityInfo.getIp());
        String serverIp = IpUtil.getLocalIp();

        Long userId = securityService.getCurrentUserId();
        String username = securityService.getCurrentUsername();

        // 【填充】日志记录实体
        for (OperationLogInfo operation : operations) {
            try {
                String params;
                if (ArrayUtil.isNotEmpty(methodInfo.getArgs())) {
                    Map<String, Object> argMap = new HashMap<>(methodInfo.getArgs().length);
                    for (Object arg : methodInfo.getArgs()) {
                        argMap.put(arg.getClass().getSimpleName(), arg);
                    }
                    params = JSONUtil.toJsonStr(argMap);
                } else {
                    params = "";
                }

                String messageTemplate = getMessageTemplate(executeResult, operation);

                // 获取需要解析的表达式
                List<String> spElTemplates =
                    CollectionUtil.newArrayList(operation.getBizNo(), operation.getDetail());
                if (StrUtil.isNotBlank(operation.getCondition())) {
                    spElTemplates.add(operation.getCondition());
                }
                if (StrUtil.isNotBlank(messageTemplate)) {
                    spElTemplates.add(messageTemplate);
                }

                Map<String, String> expMap = spElValueParser.processTemplate(spElTemplates, result, methodInfo,
                    executeResult.getError(), functionNameAndReturnMap);
                String condition = operation.getCondition();
                if (StrUtil.isBlank(condition) || StrUtil.endWithIgnoreCase(expMap.get(condition), "true")) {
                    LogRecord logRecord = LogRecord.builder()
                                                   .appName(appName)
                                                   .bizNo(expMap.get(operation.getBizNo()))
                                                   .bizType(operation.getBizType())
                                                   .success(executeResult.isSuccess())
                                                   .message(expMap.get(messageTemplate))
                                                   .detail(expMap.get(operation.getDetail()))
                                                   .exception(executeResult.getError())
                                                   .className(methodInfo.getClazz().getName())
                                                   .methodName(methodInfo.getMethod().getName())
                                                   .params(params)
                                                   .operation(operation.getOperation())
                                                   .operatorId(userId)
                                                   .operatorName(username)
                                                   .serverIp(serverIp)
                                                   .clientIp(requestIdentityInfo.getIp())
                                                   .clientLocation(address)
                                                   .clientDevice(requestIdentityInfo.getBrowser())
                                                   .requestTime(time)
                                                   .createTime(new Date())
                                                   .build();

                    if (StrUtil.isBlank(logRecord.getDetail())) {
                        String detail = StrUtil.format("{}——请求参数：{}；响应结果：{}",
                            logRecord.getMessage(), params, result);
                        logRecord.setDetail(detail);
                    }

                    if (logRecord.isSuccess()) {
                        log.info("{}", logRecord.getMessage());
                    } else {
                        log.error("{}", logRecord.getMessage());
                    }
                    logRecordService.store(logRecord);
                }
            } catch (Exception e) {
                log.error("操作日志执行异常", e);
            }
        }
    }

    private String getMessageTemplate(ExecuteResult executeResult, OperationLogInfo operation) {
        String message;
        if (executeResult.isSuccess()) {
            message = operation.getSuccess();
            if (StrUtil.isBlank(message)) {
                switch (operation.getOperation()) {
                    case EDIT:
                    case SAVE:
                    case DEL:
                    case BATCH_DEL:
                    case EXPORT_LIST:
                        message = StrUtil.format("{}{}(id = {})『成功』",
                            operation.getOperation().getName(), operation.getBizType(), operation.getBizNo());
                        break;
                    default:
                        message = StrUtil.format("{}{}『成功』",
                            operation.getOperation().getName(), operation.getBizType());
                        break;
                }
            }
        } else {
            message = operation.getFail();
            if (StrUtil.isBlank(message)) {
                switch (operation.getOperation()) {
                    case EDIT:
                    case SAVE:
                    case DEL:
                    case BATCH_DEL:
                    case EXPORT_LIST:
                        message = StrUtil.format("{}{}(id = {})『失败』",
                            operation.getOperation().getName(), operation.getBizType(), operation.getBizNo());
                        break;
                    default:
                        message = StrUtil.format("{}{}『失败』",
                            operation.getOperation().getName(), operation.getBizType());
                        break;
                }
            }
        }
        return message;
    }

    private MethodInfo getMethodInfo(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Class<?> clazz = joinPoint.getTarget().getClass();
        return MethodInfo.builder()
                         .method(method)
                         .args(args)
                         .clazz(clazz)
                         .build();
    }

    private List<String> getBeforeExecuteFunctionTemplate(Collection<OperationLogInfo> operations) {
        List<String> spElTemplates = new ArrayList<>();
        for (OperationLogInfo operation : operations) {
            List<String> list = CollectionUtil.newArrayList(operation.getBizNo(), operation.getSuccess(),
                operation.getDetail());
            if (CollectionUtil.isNotEmpty(list)) {
                spElTemplates.addAll(list);
            }
            if (StrUtil.isNotEmpty(operation.getCondition())) {
                spElTemplates.add(operation.getCondition());
            }
        }
        return spElTemplates;
    }

    /**
     * 方法执行结果实体
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ExecuteResult {

        private boolean success;
        private Throwable throwable;
        private String error;

    }

}
