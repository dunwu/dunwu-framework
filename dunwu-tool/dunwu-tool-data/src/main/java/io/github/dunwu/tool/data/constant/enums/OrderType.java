package io.github.dunwu.tool.data.constant.enums;

import java.util.Locale;
import java.util.Optional;

/**
 * 排序类型枚举
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
public enum OrderType {

    /** 升序 */
    ASC,
    /** 降序 */
    DESC;

    /**
     * Returns the {@link OrderType} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     *
     * @param value 字符串
     * @return 可选类型
     */
    public static Optional<OrderType> fromOptionalString(String value) {

        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the {@link OrderType} enum for the given {@link String} value.
     *
     * @param value 字符串
     * @return OrderType
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
     */
    public static OrderType fromString(String value) {

        try {
            return OrderType.valueOf(value.toUpperCase(Locale.US));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }

    /**
     * Returns whether the direction is ascending.
     *
     * @return true/false
     * @since 1.13
     */
    public boolean isAscending() {
        return this.equals(ASC);
    }

    /**
     * Returns whether the direction is descending.
     *
     * @return true/false
     * @since 1.13
     */
    public boolean isDescending() {
        return this.equals(DESC);
    }
}
