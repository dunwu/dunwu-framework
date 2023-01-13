package io.github.dunwu.tool.data.util;

import cn.hutool.core.util.ReflectUtil;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-14
 */
public class ReflectionUtil extends ReflectUtil {

    private static final Reflections reflections = new Reflections();

    public static <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

}
