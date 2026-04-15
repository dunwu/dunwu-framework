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
package io.github.dunwu.common.dal.generator.config.po;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.common.dal.generator.config.StrategyConfig;
import io.github.dunwu.common.dal.generator.config.rules.JavaColumnType;
import io.github.dunwu.common.dal.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 数据库表信息实体类。
 * <p>
 * 用于封装代码生成过程中从数据库元数据提取的表结构信息，以及根据策略配置生成的各类 Java 类名（Entity, DTO, Mapper, Service, Controller 等）。
 * 包含表名、注释、字段列表、导入包集合以及前后端代码生成所需的文件名配置。
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    /**
     * 需要导入的包集合
     */
    private final Set<String> importPackages = new HashSet<>();

    /**
     * 表名是否需要进行转换（如去除前缀、驼峰转换等）
     */
    private boolean convert;

    /**
     * Schema 名称
     */
    private String schemaName;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表注释/描述
     */
    private String comment;

    /**
     * 表字段列表
     */
    private List<TableField> fields;

    /**
     * 排序字段列表
     */
    private List<TableField> sortFields;

    /**
     * 列表展示字段列表
     */
    private List<TableField> listFields;

    /**
     * 表单字段列表
     */
    private List<TableField> formFields;

    /**
     * 查询条件字段列表
     */
    private List<TableField> queryFields;

    /**
     * 扩展查询字段列表
     */
    private List<TableField> queryExtFields;

    /**
     * 字典字段列表
     */
    private List<TableField> dictFields;

    /**
     * 公共字段列表（如创建时间、更新时间等）
     */
    private List<TableField> commonFields;

    /**
     * 字段名称，以逗号分隔
     */
    private String fieldNames;

    /**
     * 字段映射 Map，键为字段名，值为 {@link TableField} 对象
     */
    private Map<String, TableField> fieldMap;

    /**
     * 是否开启 Swagger 注解生成
     */
    private boolean enableSwagger = true;

    /**
     * 是否开启 EasyExcel 注解生成
     */
    private boolean enableEasyExcel = false;

    /**
     * 是否记录操作日志
     */
    private boolean enableLog = true;

    /**
     * 是否开启权限校验
     */
    private boolean enablePermission = false;

    /**
     * 是否开启搜索功能
     */
    private boolean enableQuery = true;

    /**
     * 是否开启列表展示功能
     */
    private boolean enableList = true;

    /**
     * 是否开启表单功能
     */
    private boolean enableForm = true;

    /**
     * 是否开启排序功能
     */
    private boolean enableSort = true;

    /**
     * 是否开启参数校验
     */
    private boolean enableValidate = true;

    /**
     * REST 接口根路径
     */
    private String apiBaseUrl;

    /******************* 以下为生成的后端文件名（开始） *******************/
    /**
     * Mapper XML 文件名
     */
    private String xmlName;

    /**
     * 实体类名
     */
    private String entityName;

    /**
     * DTO 类名
     */
    private String dtoName;

    /**
     * 查询对象类名
     */
    private String queryName;

    /**
     * Mapper 接口名
     */
    private String mapperName;

    /**
     * DAO 接口名
     */
    private String daoName;

    /**
     * DAO 实现类名
     */
    private String daoImplName;

    /**
     * Service 接口名
     */
    private String serviceName;

    /**
     * Service 实现类名
     */
    private String serviceImplName;

    /**
     * Controller 类名
     */
    private String controllerName;
    /******************* 以下为生成的后端文件名（结束） *******************/

    /******************* 以下为生成的前端文件名（开始） *******************/
    /**
     * 前端 API 文件名
     */
    private String apiName;

    /**
     * 前端列表页文件名
     */
    private String listName;

    /**
     * 前端表单页文件名
     */
    private String formName;
    /******************* 以下为生成的前端文件名（结束） *******************/

    /**
     * 设置表名是否转换
     *
     * @param convert 是否转换
     * @return 当前 TableInfo 实例
     */
    public TableInfo setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    /**
     * 获取实体路径（用于 REST 接口）
     * <p>
     * 默认返回首字母小写的实体类名。
     *
     * @return 实体路径
     */
    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    /**
     * 获取 REST 接口根路径
     * <p>
     * 如果未配置 {@link #apiBaseUrl}，则返回 {@link #getEntityPath()} 的值。
     *
     * @return REST 接口根路径
     */
    public String getApiBaseUrl() {
        if (apiBaseUrl == null) {
            return getEntityPath();
        }
        return apiBaseUrl;
    }

    /**
     * 设置实体类名
     * <p>
     * 同时根据策略配置自动判断是否需要设置转换标志。
     *
     * @param strategyConfig 策略配置
     * @param entityName     实体类名
     * @return 当前 TableInfo 实例
     */
    public TableInfo setEntityName(StrategyConfig strategyConfig, String entityName) {
        this.entityName = entityName;
        this.setConvert(strategyConfig);
        return this;
    }

    /**
     * 获取 DTO 路径
     *
     * @return DTO 路径
     */
    public String getDtoPath() {
        return getEntityPath() + "Dto";
    }

    /**
     * 获取查询对象路径
     *
     * @return 查询对象路径
     */
    public String getQueryPath() {
        return getEntityPath() + "Query";
    }

    /**
     * 获取 DTO 类名
     *
     * @return DTO 类名
     */
    public String getDtoName() {
        return getEntityName() + "Dto";
    }

    /**
     * 获取查询对象类名
     *
     * @return 查询对象类名
     */
    public String getQueryName() {
        return getEntityName() + "Query";
    }

    /**
     * 根据策略配置自动设置转换标志
     *
     * @param strategyConfig 策略配置
     * @return 当前 TableInfo 实例
     */
    protected TableInfo setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.containsTablePrefix(tableName) || strategyConfig.isEntityTableFieldAnnotationEnable()) {
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(tableName)) {
            this.convert = false;
        } else {
            if (NamingStrategy.underline_to_camel == strategyConfig.getColumnNaming()) {
                if (StringUtils.containsUpperCase(tableName)) {
                    this.convert = true;
                }
            } else if (!entityName.equalsIgnoreCase(tableName)) {
                this.convert = true;
            }
        }
        return this;
    }

    /**
     * 设置字段列表并收集导入包信息
     *
     * @param fields 字段列表
     * @return 当前 TableInfo 实例
     */
    public TableInfo setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
            for (TableField field : fields) {
                if (null != field.getJavaType() && null != field.getJavaType().getPkg()) {
                    importPackages.add(field.getJavaType().getPkg());
                    if (JavaColumnType.isDateType(field.getJavaType())) {
                        importPackages.add(com.fasterxml.jackson.annotation.JsonFormat.class.getCanonicalName());
                    }
                }

                if (field.isKeyFlag()) {
                    if (field.isConvert() || field.isKeyIdentityFlag()) {
                        importPackages.add(com.baomidou.mybatisplus.annotation.TableId.class.getCanonicalName());
                    }
                    if (field.isKeyIdentityFlag()) {
                        importPackages.add(com.baomidou.mybatisplus.annotation.IdType.class.getCanonicalName());
                    }
                } else {
                    importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                }
                if (null != field.getFill()) {
                    importPackages.add(com.baomidou.mybatisplus.annotation.FieldFill.class.getCanonicalName());
                }
            }
        }
        return this;
    }

    /**
     * 添加需要导入的包
     *
     * @param pkg 包名
     * @return 当前 TableInfo 实例
     */
    public TableInfo setImportPackages(String pkg) {
        importPackages.add(pkg);
        return this;
    }

    /**
     * 判断是否存在逻辑删除字段
     *
     * @param logicDeletePropertyName 逻辑删除属性名
     * @return 是否存在
     */
    public boolean isLogicDelete(String logicDeletePropertyName) {
        return fields.parallelStream().anyMatch(tf -> tf.getFieldName().equals(logicDeletePropertyName));
    }

    /**
     * 获取字段名称字符串（逗号分隔）
     * <p>
     * 用于生成 XML Mapper 中的 base column 列表。
     *
     * @return 字段名称字符串
     */
    public String getFieldNames() {
        if (StringUtils.isBlank(fieldNames) && CollectionUtils.isNotEmpty(fields)) {
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
