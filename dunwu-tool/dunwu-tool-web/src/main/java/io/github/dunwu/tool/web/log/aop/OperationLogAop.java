package io.github.dunwu.tool.web.log.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.net.ip.IpUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.aop.entity.MethodInfo;
import io.github.dunwu.tool.web.aop.util.AopUtil;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.entity.ExecuteResult;
import io.github.dunwu.tool.web.log.entity.OperationLogInfo;
import io.github.dunwu.tool.web.log.entity.OperationLogRecord;
import io.github.dunwu.tool.web.log.service.OperationLogService;
import io.github.dunwu.tool.web.log.support.LogRecordContext;
import io.github.dunwu.tool.web.log.support.OperationLogParser;
import io.github.dunwu.tool.web.log.support.SpElValueParser;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OperationLogAop {

    private final String appName;
    private final OperationLogService operationLogService;
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
        MethodInfo methodInfo = AopUtil.getMethodInfo(joinPoint);

        LogRecordContext.putEmptySpan();

        OperationLogInfo logInfo = null;
        try {
            logInfo = OperationLogParser.parse(methodInfo.getMethod(), methodInfo.getClass());
        } catch (Exception e) {
            log.error("方法执行前的日志记录解析异常", e);
        }

        // 执行方法
        ExecuteResult executeResult = new ExecuteResult(true, "", null);
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            executeResult = new ExecuteResult(false, e.getMessage(), e);
        }

        // 计算方法执行时间
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();

        try {
            handle(methodInfo, logInfo, executeResult, result, time);
        } catch (Exception e) {
            log.error("操作日志解析异常", e);
        } finally {
            LogRecordContext.clear();
        }

        if (executeResult.getThrowable() != null) {
            throw executeResult.getThrowable();
        }
        return result;
    }

    @Async
    public void handle(MethodInfo methodInfo, OperationLogInfo logInfo, ExecuteResult executeResult,
        Object result, long time) {

        if (logInfo == null) {
            return;
        }

        if (!executeResult.isSuccess()) {
            return;
        }

        // 获取用户身份信息、请求信息
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        String address = IpUtil.getRegion(requestIdentityInfo.getIp());
        String serverIp = IpUtil.getLocalIp();

        Long userId = securityService.getCurrentUserId();
        String username = securityService.getCurrentUsername();

        // 【填充】日志记录实体
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

            String messageTemplate = getMessageTemplate(executeResult, logInfo);

            // 获取需要解析的表达式
            List<String> spElTemplates = CollectionUtil.newArrayList(logInfo.getBizNo(), logInfo.getDetail());
            if (StrUtil.isNotBlank(logInfo.getCondition())) {
                spElTemplates.add(logInfo.getCondition());
            }
            if (StrUtil.isNotBlank(messageTemplate)) {
                spElTemplates.add(messageTemplate);
            }

            Map<String, String> expMap = spElValueParser.parse(spElTemplates, result, methodInfo,
                executeResult.getError());
            String condition = logInfo.getCondition();
            if (StrUtil.isBlank(condition) || StrUtil.endWithIgnoreCase(expMap.get(condition), "true")) {
                OperationLogRecord logRecord = OperationLogRecord.builder()
                                                                 .appName(appName)
                                                                 .bizNo(expMap.get(logInfo.getBizNo()))
                                                                 .bizType(logInfo.getBizType())
                                                                 .success(executeResult.isSuccess())
                                                                 .message(expMap.get(messageTemplate))
                                                                 .detail(expMap.get(logInfo.getDetail()))
                                                                 .exception(executeResult.getError())
                                                                 .className(methodInfo.getClazz().getName())
                                                                 .methodName(methodInfo.getMethod().getName())
                                                                 .params(params)
                                                                 .operation(logInfo.getOperation())
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
                    OperationLogAop.log.info("{}", logRecord.getMessage());
                } else {
                    OperationLogAop.log.error("{}", logRecord.getMessage());
                }
                operationLogService.store(logRecord);
            }
        } catch (Exception e) {
            OperationLogAop.log.error("操作日志执行异常", e);
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
                    case IMPORT_EXCEL:
                    case EXPORT_EXCEL:
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
                    case IMPORT_EXCEL:
                    case EXPORT_EXCEL:
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

}
