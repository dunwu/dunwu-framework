package io.github.dunwu.util.base;

import org.apache.commons.lang3.EnumUtils;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;

/**
 * 枚举工具类
 * <p>
 * 1. 继承 {@link org.apache.commons.lang3.EnumUtils} 所有方法
 * <p>
 * 2. 扩展部分功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-08-22
 */
public class EnumUtil extends EnumUtils {

    /**
     * Enum转换为String
     */
    public static String toString(Enum e) {
        return e.name();
    }

    /**
     * String转换为Enum
     */
    public static <E extends Enum<E>> E valueOf(Class<E> enumClass, String value) {
        return Enum.valueOf(enumClass, value);
    }

    public static <E extends Enum<E>> EnumMap<E, String> enumMap(Class<E> enumClass) {
        return new EnumMap<>(enumClass);
    }

    public static <E extends Enum<E>> EnumSet<E> enumSet(Class<E> enumClass) {
        return EnumSet.allOf(enumClass);
    }

    public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> s) {
        return EnumSet.copyOf(s);
    }

    public static <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> c) {
        return EnumSet.copyOf(c);
    }
}
