package io.github.dunwu.common.web.aop.util;

import io.github.dunwu.common.web.aop.domain.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * AOP 工具类
 * <p>
 * 提供从 JoinPoint 中提取方法信息的功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-12-14
 */
public class AopUtil {

    /**
     * 从 JoinPoint 中提取方法信息
     *
     * @param joinPoint 切点
     * @return 方法信息实体
     */
    public static MethodInfo getMethodInfo(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        return MethodInfo.builder().clazz(clazz).method(method).args(args).build();
    }

}
