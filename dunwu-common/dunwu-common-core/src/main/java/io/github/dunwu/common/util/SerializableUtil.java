package io.github.dunwu.common.util;

import java.io.Serializable;

/**
 * 序列化工具类，提供 {@link Serializable} 类型转换方法
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-05
 */
public class SerializableUtil {

    /**
     * 将 {@link Serializable} 值转为 Long
     *
     * @param value 序列化值
     * @return Long 值，无法转换时返回 null
     */
    public static Long getLong(Serializable value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer intValue) {
            return intValue.longValue();
        } else {
            return null;
        }
    }

    /**
     * 将 {@link Serializable} 值转为 Integer
     *
     * @param value 序列化值
     * @return Integer 值，无法转换时返回 null
     */
    public static Integer getInteger(Serializable value) {
        if (value instanceof Long longValue) {
            return longValue.intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return null;
        }
    }

}
