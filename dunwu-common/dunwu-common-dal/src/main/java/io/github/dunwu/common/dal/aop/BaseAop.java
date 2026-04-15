package io.github.dunwu.common.dal.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * AOP 切面基类
 * <p>
 * 提供通用的方法解析功能，支持从 {@link ProceedingJoinPoint} 中获取目标方法的 {@link Method} 对象。
 * 即使目标方法定义在父类中，也能正确解析。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
public abstract class BaseAop {

    /**
     * 解析目标方法
     * <p>
     * 从切点连接点中获取实际执行的方法对象。如果方法在当前类中未找到，会递归向上查找父类。
     *
     * @param point 切点连接点
     * @return 目标方法的 {@link Method} 对象
     * @throws IllegalStateException 如果无法找到目标方法
     */
    protected Method resolveMethod(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<?> targetClass = point.getTarget().getClass();
        Method method = getDeclaredMethod(targetClass, signature.getName(),
            signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("无法解析目标方法: " + signature.getMethod().getName());
        }
        return method;
    }

    /**
     * 获取声明的方法
     * <p>
     * 尝试从指定类中获取方法，如果未找到则递归查找其父类。
     *
     * @param clazz         目标类
     * @param name          方法名
     * @param parameterTypes 参数类型数组
     * @return 找到的 {@link Method} 对象，如果未找到返回 null
     */
    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, name, parameterTypes);
            }
        }
        return null;
    }

}
