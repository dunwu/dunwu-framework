package io.github.dunwu.common.dal.generator.config.po;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.common.dal.generator.config.StrategyConfig;
import io.github.dunwu.common.dal.generator.config.rules.IColumnType;
import io.github.dunwu.common.dal.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 数据库表字段信息实体类。
 * <p>
 * 用于封装代码生成过程中从数据库元数据提取的字段信息，以及根据策略配置转换后的 Java 属性信息。
 * 包含字段名、类型、注释、主键标识、是否自增，以及表单/列表/查询等前端展示相关的配置属性。
 *
 * @author YangHu
 * @since 2016-12-03
 */
@Data
@Accessors(chain = true)
public class TableField {

    // =========================================================
    // 完全根据数据表中字段属性获得的配置
    // =========================================================

    /**
     * Schema 名称
     */
    private String schemaName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 字段名称（数据库原始字段名）
     */
    private String fieldName;

    /**
     * 字段注释/描述
     */
    private String comment;

    /**
     * 字段数据类型（数据库类型）
     */
    private String type;

    /**
     * 字段对应的 Java 类型
     */
    private IColumnType javaType;

    /**
     * 主键类型（如 PRIMARY, UNIQUE 等）
     */
    private String keyType;

    /**
     * 是否为主键
     */
    private boolean keyFlag = false;

    /**
     * 是否为自增主键
     */
    private boolean keyIdentityFlag = false;

    /**
     * 是否不允许为空
     */
    private boolean notNull;

    // =========================================================
    // 根据定制规则获得的配置
    // =========================================================

    /**
     * 转换后的 Java 属性名
     * <p>
     * 如果未配置，默认使用 {@link #fieldName}
     */
    private String propertyName;

    /**
     * 字段标签名称（用于前端展示或注解描述）
     * <p>
     * 如果未配置，默认使用 {@link #comment}
     */
    private String labelName;

    /**
     * 是否出现在表单中
     */
    private boolean enableForm = true;

    /**
     * 是否出现在列表中
     */
    private boolean enableList = true;

    /**
     * 是否出现在搜索条件中
     */
    private boolean enableQuery = true;

    /**
     * 是否允许排序
     */
    private boolean enableSort = false;

    /**
     * 是否允许校验
     */
    private Boolean enableValidate = false;

    /**
     * 表单组件类型（如 Input, Select 等）
     */
    private String formType = "Input";

    /**
     * 列表展示类型（如 Text, Tag 等）
     */
    private String listType = "Text";

    /**
     * 查询类型（如 EQUALS, LIKE 等）
     */
    private String queryType = "EQUALS";

    /**
     * 默认排序类型（asc 或 desc）
     */
    private String sortType = "asc";

    /**
     * 校验类型（如 string, number 等）
     */
    private String validateType = "string";

    /**
     * 关联的字典编码
     */
    private String dictCode;

    /**
     * 日期格式化表达式
     * <p>
     * 仅当字段为时间类型时有效
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字段名称是否经过了转换
     * <p>
     * 如果数据库字段名与 Java 属性名不一致，则为 true
     */
    private boolean convert;

    /**
     * {@link com.baomidou.mybatisplus.annotation.TableField} 填充策略
     * <p>
     * 例如：INSERT, UPDATE, INSERT_UPDATE
     */
    private String fill;

    /**
     * 自定义查询字段映射
     */
    private Map<String, Object> customMap;

    /**
     * 设置字段名称是否转换
     *
     * @param convert 是否转换
     * @return 当前 TableField 实例
     */
    public TableField setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    /**
     * 获取 Java 属性名
     * <p>
     * 如果 {@link #propertyName} 为空，则返回 {@link #fieldName}
     *
     * @return Java 属性名
     */
    public String getPropertyName() {
        if (StrUtil.isBlank(propertyName)) {
            return fieldName;
        }
        return propertyName;
    }

    /**
     * 设置 Java 属性名
     * <p>
     * 同时根据 {@link StrategyConfig} 自动判断是否需要设置 {@link #convert} 标志
     *
     * @param strategyConfig 策略配置
     * @param propertyName   Java 属性名
     * @return 当前 TableField 实例
     */
    public TableField setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        this.propertyName = propertyName;
        this.setConvert(strategyConfig);
        return this;
    }

    /**
     * 获取字段标签名称
     * <p>
     * 如果 {@link #labelName} 为空，则返回 {@link #comment}
     *
     * @return 字段标签名称
     */
    public String getLabelName() {
        if (StrUtil.isBlank(labelName)) {
            return comment;
        }
        return labelName;
    }

    /**
     * 根据策略配置自动设置转换标志
     *
     * @param strategyConfig 策略配置
     * @return 当前 TableField 实例
     */
    protected TableField setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.isEntityTableFieldAnnotationEnable()) {
            this.convert = true;
            return this;
        }
        if (strategyConfig.isCapitalModeNaming(fieldName)) {
            this.convert = false;
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == strategyConfig.getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(fieldName)) {
                    this.convert = true;
                }
            } else if (!fieldName.equals(propertyName)) {
                this.convert = true;
            }
        }
        return this;
    }

    /**
     * 获取 Java 属性类型名称
     *
     * @return Java 类型名称，如果未设置则返回 null
     */
    public String getPropertyType() {
        if (null != javaType) {
            return javaType.getType();
        }
        return null;
    }

}
