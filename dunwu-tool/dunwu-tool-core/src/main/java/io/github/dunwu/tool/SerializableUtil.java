package io.github.dunwu.tool;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-05
 */
public class SerializableUtil {

    public static Long getLong(Serializable value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            return intValue.longValue();
        } else {
            return null;
        }
    }

    public static Integer getInteger(Serializable value) {
        if (value instanceof Long) {
            Long longValue = (Long) value;
            return longValue.intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return null;
        }
    }

}
