/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.dunwu.tool.generator.config.po;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.tool.generator.config.StrategyConfig;
import io.github.dunwu.tool.generator.config.rules.IColumnType;
import io.github.dunwu.tool.generator.config.rules.JavaColumnType;
import io.github.dunwu.tool.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 表字段信息
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

    /** schema 名称 */
    private String schemaName;
    /** table 名称 */
    private String tableName;
    /** 字段名称（表字段） */
    private String fieldName;
    /** 字段注释 */
    private String comment;
    /** 字段数据类型 */
    private String type;
    /** 字段类型（Java 类型） */
    private IColumnType javaType;
    /** Key类型 */
    private String keyType;
    /** 是否为Key */
    private boolean keyFlag = false;
    /** 是否为自增主键 */
    private boolean keyIdentityFlag = false;
    /** 不允许为空 */
    private boolean notNull;

    // =========================================================
    // 根据定制规则获得的配置
    // =========================================================

    /** 字段别名（如果没有配置，以fieldName填充） */
    private String propertyName;
    /** 字段 label 名称（如果没有配置，以comment填充） */
    private String labelName;
    /** 出现在表单 */
    private boolean enableForm = true;
    /** 出现在列表 */
    private boolean enableList = true;
    /** 出现在搜索 */
    private boolean enableQuery = true;
    /** 允许排序 */
    private boolean enableSort = false;
    /** 允许校验 */
    private Boolean enableValidate = false;
    /** 表单类型 */
    private String formType = "Input";
    /** 列表类型 */
    private String listType = "Text";
    /** 查询类型 */
    private String queryType = "EQUALS";
    /** 排序类型 */
    private String sortType = "asc";
    /** 校验类型 */
    private String validateType = "string";
    /** 字典名称 */
    private String dictName;
    /** 日期表达式（只有当字段为时间类型时才有效） */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";
    /** 字段名称是否经过了转换 */
    private boolean convert;
    /** {@link @TableField} 填充属性（可选） */
    private String fill;
    /** 自定义查询字段列表 */
    private Map<String, Object> customMap;

    public TableField setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    public String getPropertyName() {
        if (StrUtil.isBlank(propertyName)) {
            return fieldName;
        }
        return propertyName;
    }

    public TableField setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        this.propertyName = propertyName;
        this.setConvert(strategyConfig);
        return this;
    }

    public String getLabelName() {
        if (StrUtil.isBlank(labelName)) {
            return comment;
        }
        return labelName;
    }

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

    public String getPropertyType() {
        if (null != javaType) {
            return javaType.getType();
        }
        return null;
    }

}
