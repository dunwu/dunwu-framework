package io.github.dunwu.common.dal.constant;

import java.util.Locale;
import java.util.Optional;

/**
 * 排序类型枚举
 * <p>
 * 定义了查询排序的方向，支持升序（ASC）和降序（DESC）。
 * 提供了从字符串解析排序类型的方法，并兼容大小写。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
public enum OrderType {

    /**
     * 升序排序
     */
    ASC,
    /**
     * 降序排序
     */
    DESC;

    /**
     * 从字符串解析排序类型
     * <p>
     * 如果字符串无法解析为有效的枚举值，则返回空的 {@link Optional}。
     *
     * @param value 字符串值（如 "asc", "desc"）
     * @return 包含解析结果的 Optional 对象
     */
    public static Optional<OrderType> fromOptionalString(String value) {

        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * 从字符串解析排序类型
     * <p>
     * 支持不区分大小写的 "asc" 和 "desc"。
     *
     * @param value 字符串值
     * @return 对应的 {@link OrderType} 枚举
     * @throws IllegalArgumentException 如果字符串无法解析为有效的排序类型
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
     * 判断是否为升序
     *
     * @return 如果是升序返回 true，否则返回 false
     * @since 1.13
     */
    public boolean isAscending() {
        return this.equals(ASC);
    }

    /**
     * 判断是否为降序
     *
     * @return 如果是降序返回 true，否则返回 false
     * @since 1.13
     */
    public boolean isDescending() {
        return this.equals(DESC);
    }
}
