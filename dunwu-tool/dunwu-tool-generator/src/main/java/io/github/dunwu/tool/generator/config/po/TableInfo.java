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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.tool.generator.config.StrategyConfig;
import io.github.dunwu.tool.generator.config.rules.JavaColumnType;
import io.github.dunwu.tool.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    private final Set<String> importPackages = new HashSet<>();
    private boolean convert;
    /** schema名称 */
    private String schemaName;
    /** 表名 */
    private String tableName;
    /** 表备注 */
    private String comment;
    /** 表字段 */
    private List<TableField> fields;
    private List<TableField> sortFields;
    private List<TableField> listFields;
    private List<TableField> formFields;
    private List<TableField> queryFields;
    private List<TableField> queryExtFields;
    private List<TableField> dictFields;
    /** 公共字段 */
    private List<TableField> commonFields;
    /** 字段名称，以逗号分隔 */
    private String fieldNames;
    private Map<String, TableField> fieldMap;

    /** 开启 Swagger */
    private boolean enableSwagger = true;
    /** 开启 EasyExcel */
    private boolean enableEasyExcel = false;
    /** 开启权限校验 */
    private boolean enablePermission = false;
    /** 开启搜索 */
    private boolean enableQuery = true;
    /** 开启列表 */
    private boolean enableList = true;
    /** 开启表单 */
    private boolean enableForm = true;
    /** 开启排序 */
    private boolean enableSort = true;
    /** 开启校验 */
    private boolean enableValidate = true;
    /** 记录操作日志 */
    private boolean enableLog = true;
    /** REST接口根路径 */
    private String apiBaseUrl;

    /******************* 以下为生成的后端文件名（开始） *******************/
    private String xmlName;
    private String entityName;
    private String dtoName;
    private String queryName;
    private String mapperName;
    private String daoName;
    private String daoImplName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    /******************* 以下为生成的后端文件名（结束） *******************/

    /******************* 以下为生成的前端文件名（结束） *******************/
    private String apiName;
    private String listName;
    private String formName;

    /******************* 以下为生成的前端文件名（结束） *******************/

    public TableInfo setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    /**
     * REST接口根路径，如果没配置，返回 entityPath 的值
     *
     * @return
     */
    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    public String getApiBaseUrl() {
        if (apiBaseUrl == null) {
            return getEntityPath();
        }
        return apiBaseUrl;
    }

    public TableInfo setEntityName(StrategyConfig strategyConfig, String entityName) {
        this.entityName = entityName;
        this.setConvert(strategyConfig);
        return this;
    }

    public String getDtoPath() {
        return getEntityPath() + "Dto";
    }

    public String getQueryPath() {
        return getEntityPath() + "Query";
    }

    public String getDtoName() {
        return getEntityName() + "Dto";
    }

    public String getQueryName() {
        return getEntityName() + "Query";
    }

    protected TableInfo setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.containsTablePrefix(tableName) || strategyConfig.isEntityTableFieldAnnotationEnable()) {
            // 包含前缀
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(tableName)) {
            // 包含
            this.convert = false;
        } else {
            // 转换字段
            if (NamingStrategy.underline_to_camel == strategyConfig.getColumnNaming()) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(tableName)) {
                    this.convert = true;
                }
            } else if (!entityName.equalsIgnoreCase(tableName)) {
                this.convert = true;
            }
        }
        return this;
    }

    public TableInfo setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
            // 收集导入包信息
            for (TableField field : fields) {
                if (null != field.getJavaType() && null != field.getJavaType().getPkg()) {
                    importPackages.add(field.getJavaType().getPkg());
                    if (JavaColumnType.isDateType(field.getJavaType())) {
                        importPackages.add(com.fasterxml.jackson.annotation.JsonFormat.class.getCanonicalName());
                    }
                }

                if (field.isKeyFlag()) {
                    // 主键
                    if (field.isConvert() || field.isKeyIdentityFlag()) {
                        importPackages.add(com.baomidou.mybatisplus.annotation.TableId.class.getCanonicalName());
                    }
                    // 自增
                    if (field.isKeyIdentityFlag()) {
                        importPackages.add(com.baomidou.mybatisplus.annotation.IdType.class.getCanonicalName());
                    }
                } else {
                    // 普通字段
                    importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                }
                if (null != field.getFill()) {
                    // 填充字段
                    // importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                    importPackages.add(com.baomidou.mybatisplus.annotation.FieldFill.class.getCanonicalName());
                }
            }
        }
        return this;
    }

    public TableInfo setImportPackages(String pkg) {
        importPackages.add(pkg);
        return this;
    }

    /**
     * 逻辑删除
     */
    public boolean isLogicDelete(String logicDeletePropertyName) {
        return fields.parallelStream().anyMatch(tf -> tf.getFieldName().equals(logicDeletePropertyName));
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        if (StringUtils.isBlank(fieldNames)
            && CollectionUtils.isNotEmpty(fields)) {
            StringBuilder names = new StringBuilder();
            IntStream.range(0, fields.size()).forEach(i -> {
                TableField fd = fields.get(i);
                if (i == fields.size() - 1) {
                    names.append(fd.getFieldName());
                } else {
                    names.append(fd.getFieldName()).append(", ");
                }
            });
            fieldNames = names.toString();
        }
        return fieldNames;
    }

}
