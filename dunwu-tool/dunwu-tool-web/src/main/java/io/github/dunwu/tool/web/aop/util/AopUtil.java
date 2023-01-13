package io.github.dunwu.tool.web.aop.util;

import io.github.dunwu.tool.web.aop.entity.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author 11123558
 * @date 2022-12-14
 */
public class AopUtil {

    public static MethodInfo getMethodInfo(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        return MethodInfo.builder()
                         .clazz(clazz)
                         .method(method)
                         .args(args)
                         .build();
    }

}
