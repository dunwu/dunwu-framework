package io.github.dunwu.common.dal.generator.config;

import io.github.dunwu.common.dal.generator.config.po.TableField;
import io.github.dunwu.common.dal.generator.config.rules.IColumnType;

/**
 * 数据库字段类型转换接口。
 * <p>
 * 用于将数据库字段类型转换为 Java 类型。不同数据库（MySQL, Oracle 等）应提供不同的实现。
 * 实现类通常根据数据库的列类型字符串（如 {@code VARCHAR}, {@code INT}, {@code DATETIME}）映射到对应的 Java 类型。
 *
 * @author hubin
 * @since 2017-01-20
 */
public interface ITypeConvert {

    /**
     * 执行类型转换（基于 {@link TableField}）。
     * <p>
     * 根据全局配置和字段列信息进行类型转换。
     * 默认调用 {@link #processTypeConvert(GlobalConfig, String)}。
     *
     * @param globalConfig 全局配置
     * @param tableField   字段列信息
     * @return 转换后的 Java 列类型 {@link IColumnType}
     */
    default IColumnType processTypeConvert(GlobalConfig globalConfig, TableField tableField) {
        return processTypeConvert(globalConfig, tableField.getType());
    }

    /**
     * 执行类型转换（基于类型字符串）。
     * <p>
     * 根据全局配置和数据库字段类型字符串进行转换。
     *
     * @param globalConfig 全局配置
     * @param fieldType    数据库字段类型字符串
     * @return 转换后的 Java 列类型 {@link IColumnType}
     */
    IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType);

}
