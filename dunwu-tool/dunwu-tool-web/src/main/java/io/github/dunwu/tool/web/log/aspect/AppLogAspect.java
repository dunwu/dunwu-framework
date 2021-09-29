package io.github.dunwu.tool.web.log.aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.net.IpUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.log.AppLogInfo;
import io.github.dunwu.tool.web.log.LogStorage;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.tool.web.log.constant.LogLevel;
import io.github.dunwu.tool.web.security.SecurityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link AppLog} 注解的处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Aspect
public class AppLogAspect {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    private LogStorage logStorage;
    private SecurityService securityService;

    private final ThreadLocal<Long> currentTime = new ThreadLocal<>();
    private final ExpressionParser parser = new SpelExpressionParser();
    private final LocalVariableTableParameterNameDiscoverer discoverer =
        new LocalVariableTableParameterNameDiscoverer();

    public AppLogAspect() { }

    public AppLogAspect(LogStorage logStorage) {
        this.logStorage = logStorage;
    }

    public AppLogAspect(LogStorage logStorage, SecurityService securityService) {
        this.securityService = securityService;
        this.logStorage = logStorage;
    }

    @Pointcut("@annotation(io.github.dunwu.tool.web.log.annotation.AppLog)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 执行方法前，记录起始时间
        currentTime.set(System.currentTimeMillis());

        // 执行方法
        Object result = joinPoint.proceed();

        // 计算方法执行时间
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();

        Method method = null;
        try {
            // 获取方法信息
            method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            // 组装操作日志并记录
            resolveLog(joinPoint, method, time, null);
        } catch (Exception e) {
            String msg;
            if (method != null) {
                msg = StrUtil.format("【AppLogAspect】处理 class = {}, method = {} 上的 @AppLog 失败！",
                    method.getDeclaringClass().getCanonicalName(), method.getName());
            } else {
                msg = "【AppLogAspect】处理操作日志失败！";
            }
            log.error(msg, e);
        }

        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param t         exception
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "t")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable t) {

        // 计算方法执行时间
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();

        Method method = null;
        try {
            // 获取方法信息
            method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            // 组装操作日志并记录
            resolveLog(joinPoint, method, time, t);
        } catch (Exception e) {
            String msg;
            if (method != null) {
                msg = StrUtil.format("【AppLogAspect】处理 class = {}, method = {} 上的 @AppLog 失败！",
                    method.getDeclaringClass().getCanonicalName(), method.getName());
            } else {
                msg = "【AppLogAspect】处理操作日志失败！";
            }
            log.error(msg, e);
        }
    }

    @Async
    public void resolveLog(JoinPoint pjp, Method method, long time, Throwable e) {

        AppLog appLog = method.getAnnotation(AppLog.class);

        Object[] args = pjp.getArgs();
        String[] params = discoverer.getParameterNames(method);

        EvaluationContext context = null;
        if (ArrayUtil.isNotEmpty(params)) {
            // 参数不为空时，根据 Spring EL 表达式组装日志内容
            context = new StandardEvaluationContext();
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], args[len]);
            }
        }

        // 获取用户身份信息、请求信息
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);

        // 【填充】附加信息
        String address = IpUtil.getRegionName(requestIdentityInfo.getIp());
        AppLogInfo appLogInfo = new AppLogInfo();
        appLogInfo.setBizType(appLog.bizType())
            .setOperateType(appLog.operType())
            .setRequestTime(time)
            .setClassName(pjp.getTarget().getClass().getName())
            .setMethodName(method.getName())
            .setParams(getParameter(method, pjp.getArgs()))
            .setServerIp(IpUtil.getLocalIp())
            .setClientIp(requestIdentityInfo.getIp())
            .setClientLocation(address)
            .setClientDevice(requestIdentityInfo.getBrowser());

        String message = "";

        // 【填充】业务类型
        if (StrUtil.isNotBlank(appLog.bizType())) {
            message = message + StrUtil.format("【{}】", appLog.bizType());
        }

        // 【填充】操作者
        if (StrUtil.isNotBlank(appLog.operator())) {
            String elOperator = tryGetSpringElValue(context, appLog.operator());
            appLogInfo.setOperatorName(elOperator);
            message = message + elOperator;
        } else {
            if (securityService != null) {
                appLogInfo.setOperatorId(securityService.getCurrentUserId())
                    .setOperatorName(securityService.getCurrentUsername());
                message = message + securityService.getCurrentUsername();
            }
        }

        // 【填充】日志消息
        String elValue = tryGetSpringElValue(context, appLog.value());
        message = message + elValue;

        // 【填充】异常处理信息（发生异常时）
        if (e == null) {
            String level = appLog.level().name();
            appLogInfo.setLevel(level)
                .setMessage(message);
        } else {
            message = message + "失败";
            appLogInfo.setLevel(LogLevel.ERROR.name())
                .setMessage(message)
                .setExceptionMessage(e.getLocalizedMessage());
        }

        // 根据组装后的信息，打印日志
        switch (appLogInfo.getLevel()) {
            case "DEBUG":
                log.debug(message);
                break;
            case "WARN":
                log.warn(message);
                break;
            case "ERROR":
                if (StrUtil.isNotBlank(appLogInfo.getExceptionMessage())) {
                    log.error(appLogInfo.getMessage(), e);
                } else {
                    log.error(appLogInfo.getMessage());
                }
                break;
            case "INFO":
            default:
                log.info(message);
                break;
        }

        // 将组装后的信息存储
        if (appLog.storeDb()) {
            if (logStorage != null) {
                logStorage.store(appLogInfo);
            }
        }
    }

    private String tryGetSpringElValue(EvaluationContext context, String value) {
        if (context != null) {
            if (value.contains("#")) {
                Expression exp = parser.parseExpression(value);
                return exp.getValue(context, String.class);
            }
        }

        return value;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StrUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

}
