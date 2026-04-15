package io.github.dunwu.common.dal.generator.config.rules;

/**
 * Java 列类型信息接口。
 * <p>
 * 用于定义数据库字段转换后的 Java 类型信息，包括类型名称和完整包名。
 * 代码生成器通过此接口获取字段对应的 Java 类型详情。
 *
 * @author miemie
 * @since 2018-08-22
 */
public interface IColumnType {

    /**
     * 获取字段类型名称。
     * <p>
     * 例如：{@code String}, {@code Integer}, {@code LocalDate}。
     *
     * @return 类型名称（简单类名）
     */
    String getType();

    /**
     * 获取字段类型完整包名。
     * <p>
     * 例如：{@code java.lang.String}, {@code java.time.LocalDate}。
     * 用于生成代码时的 import 语句。
     *
     * @return 完整包名，若为基本类型或 java.lang 包下则可能返回 null
     */
    String getPkg();

}
