package io.github.dunwu.common.log.aop;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.common.net.ip.IpUtil;
import io.github.dunwu.common.web.aop.domain.MethodInfo;
import io.github.dunwu.common.web.aop.util.AopUtil;
import io.github.dunwu.common.log.annotation.OperationLog;
import io.github.dunwu.common.log.domain.ExecuteResult;
import io.github.dunwu.common.log.domain.OperationLogInfo;
import io.github.dunwu.common.log.domain.OperationLogRecord;
import io.github.dunwu.common.log.service.OperationLogService;
import io.github.dunwu.common.log.support.LogRecordContext;
import io.github.dunwu.common.log.support.OperationLogParser;
import io.github.dunwu.common.log.support.SpElValueParser;
import io.github.dunwu.common.web.security.SecurityService;
import io.github.dunwu.common.web.util.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    /** 应用名称 */
    private final String appName;
    /** 操作日志服务 */
    private final OperationLogService operationLogService;
    /** 安全服务 */
    private final SecurityService securityService;
    /** SpEL 值解析器 */
    private final SpElValueParser spElValueParser;
    /** 记录当前时间的线程局部变量 */
    private final ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 定义切点，匹配所有带有 @OperationLog 注解的方法
     */
    @Pointcut("@annotation(io.github.dunwu.common.log.annotation.OperationLog)")
    public void pointcut() { }

    /**
     * 环绕通知，记录方法执行前后的日志
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 方法执行异常
     */
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

    /**
     * 异步处理操作日志记录
     *
     * @param methodInfo    方法信息
     * @param logInfo       日志信息
     * @param executeResult 执行结果
     * @param result        方法返回值
     * @param time          方法执行耗时
     */
    @Async
    public void handle(MethodInfo methodInfo, OperationLogInfo logInfo, ExecuteResult executeResult, Object result,
        long time) {

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

            Map<String, String> expMap =
                spElValueParser.parse(spElTemplates, result, methodInfo, executeResult.getError());
            String condition = logInfo.getCondition();
            if (StrUtil.isBlank(condition) || StrUtil.endWithIgnoreCase(expMap.get(condition), "true")) {
                OperationLogRecord logRecord =
                    OperationLogRecord.builder().appName(appName).bizNo(expMap.get(logInfo.getBizNo()))
                        .bizType(logInfo.getBizType()).success(executeResult.isSuccess())
                        .message(expMap.get(messageTemplate)).detail(expMap.get(logInfo.getDetail()))
                        .exception(executeResult.getError()).className(methodInfo.getClazz().getName())
                        .methodName(methodInfo.getMethod().getName()).params(params).operation(logInfo.getOperation())
                        .operatorId(userId).operatorName(username).serverIp(serverIp)
                        .clientIp(requestIdentityInfo.getIp()).clientLocation(address)
                        .clientDevice(requestIdentityInfo.getBrowser()).requestTime(time).createTime(new Date())
                        .build();

                if (StrUtil.isBlank(logRecord.getDetail())) {
                    String detail =
                        StrUtil.format("{}——请求参数：{}；响应结果：{}", logRecord.getMessage(), params, result);
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

    /**
     * 获取日志消息模板
     *
     * @param executeResult 执行结果
     * @param operation     操作日志信息
     * @return 格式化后的消息模板
     */
    private String getMessageTemplate(ExecuteResult executeResult, OperationLogInfo operation) {
        String message;
        if (executeResult.isSuccess()) {
            message = operation.getSuccess();
            if (StrUtil.isBlank(message)) {
                message = switch (operation.getOperation()) {
                    case EDIT, SAVE, DEL, BATCH_DEL, IMPORT_EXCEL, EXPORT_EXCEL ->
                        StrUtil.format("{}{}(id = {})『成功』", operation.getOperation().getName(),
                            operation.getBizType(), operation.getBizNo());
                    default -> StrUtil.format("{}{}『成功』", operation.getOperation().getName(), operation.getBizType());
                };
            }
        } else {
            message = operation.getFail();
            if (StrUtil.isBlank(message)) {
                message = switch (operation.getOperation()) {
                    case EDIT, SAVE, DEL, BATCH_DEL, IMPORT_EXCEL, EXPORT_EXCEL ->
                        StrUtil.format("{}{}(id = {})『失败』", operation.getOperation().getName(),
                            operation.getBizType(), operation.getBizNo());
                    default -> StrUtil.format("{}{}『失败』", operation.getOperation().getName(), operation.getBizType());
                };
            }
        }
        return message;
    }

}
