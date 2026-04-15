package io.github.dunwu.common.dal.generator.config;

import io.github.dunwu.common.dal.generator.config.po.TableField;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;

/**
 * 名称转换接口。
 * <p>
 * 用于自定义数据库表名到实体类名、数据库字段名到实体属性名的转换逻辑。
 * 默认实现通常使用下划线转驼峰命名法，用户可通过实现此接口自定义命名规则。
 *
 * @author hubin
 * @since 2017-01-20
 */
public interface INameConvert {

    /**
     * 执行实体类名称转换。
     * <p>
     * 根据表信息对象生成对应的 Java 实体类名。
     *
     * @param tableInfo 表信息对象
     * @return 转换后的实体类名
     */
    String entityNameConvert(TableInfo tableInfo);

    /**
     * 执行属性名称转换。
     * <p>
     * 根据表字段对象生成对应的 Java 属性名。
     * 如果属性名与字段名不一致，需注意设置 {@link TableField} 的 convert 属性。
     *
     * @param field 表字段对象
     * @return 转换后的属性名
     */
    String propertyNameConvert(TableField field);

}
