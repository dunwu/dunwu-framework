package io.github.dunwu.common.dal.constant;

/**
 * 命名策略枚举
 * <p>
 * 定义了常见的命名转换规则，如驼峰命名、下划线命名、短横线命名等。
 * 通常用于数据库字段与 Java 属性之间的名称映射。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-18
 */
public enum NamingStrategy {
    /**
     * 默认命名
     * <p>
     * 不对命名做任何转换或要求，保持原样
     */
    DEFAULT,
    /**
     * 小驼峰命名
     * <p>
     * 例如：{@code namingStrategy}
     */
    CAMEL,
    /**
     * 下划线命名（全小写）
     * <p>
     * 例如：{@code naming_strategy}
     */
    LOWER_UNDERLINE,
    /**
     * 下划线命名（全大写）
     * <p>
     * 例如：{@code NAMING_STRATEGY}
     */
    UPPER_UNDERLINE,
    /**
     * 短横线命名（全小写）
     * <p>
     * 例如：{@code naming-strategy}
     */
    LOWER_DASHED,
    /**
     * 短横线命名（全大写）
     * <p>
     * 例如：{@code NAMING-STRATEGY}
     */
    UPPER_DASHED
}
