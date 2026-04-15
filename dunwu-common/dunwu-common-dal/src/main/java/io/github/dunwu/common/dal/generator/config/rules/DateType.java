package io.github.dunwu.common.dal.generator.config.rules;

/**
 * 数据库时间类型到 Java 实体类时间类型的映射策略枚举。
 * <p>
 * 用于代码生成器配置，决定将数据库中的日期/时间类型字段转换为哪种 Java 时间类型。
 *
 * @author miemie
 * @since 2018/5/22
 */
public enum DateType {
    /**
     * 仅使用 {@link java.util.Date} 类型。
     * <p>
     * 兼容旧版本 Java 代码，所有日期/时间字段均映射为 {@code java.util.Date}。
     */
    ONLY_DATE,

    /**
     * 使用 {@code java.sql} 包下的时间类型。
     * <p>
     * 例如：{@link java.sql.Date}、{@link java.sql.Time}、{@link java.sql.Timestamp}，适用于传统 JDBC 场景。
     */
    SQL_PACK,

    /**
     * 使用 {@code java.time} 包下的时间类型。
     * <p>
     * Java 8 引入的新时间 API（JSR-310），例如：{@link java.time.LocalDate}、{@link java.time.LocalDateTime}，推荐使用。
     */
    TIME_PACK
}
