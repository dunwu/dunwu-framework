package io.github.dunwu.common.util;

import org.reflections.Reflections;

import java.util.Set;

/**
 * 反射工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-12-14
 */
public class ReflectUtil extends cn.hutool.core.util.ReflectUtil {

    private static final Reflections reflections = new Reflections();

    public static <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

}
