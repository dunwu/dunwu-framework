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
package io.github.dunwu.tool.generator.config.builder;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.generator.InjectionConfig;
import io.github.dunwu.tool.generator.config.*;
import io.github.dunwu.tool.generator.config.po.TableField;
import io.github.dunwu.tool.generator.config.po.TableFill;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.config.querys.H2Query;
import io.github.dunwu.tool.generator.config.rules.FormType;
import io.github.dunwu.tool.generator.config.rules.NamingStrategy;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 配置汇总 传递给文件生成工具
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class ConfigBuilder {

    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()-_=+\\\\|[{}];:\\'\\\",<.>/?]+");

    // =========================================================
    // 配置元数据
    // =========================================================

    /** 数据库配置信息 */
    private final DataSourceConfig dataSourceConfig;
    /** 全局配置信息 */
    private final GlobalConfig globalConfig;
    /** 策略配置信息 */
    private final StrategyConfig strategyConfig;
    /** 模板路径配置信息 */
    private final TemplateConfig templateConfig;
    /** 注入配置信息 */
    private InjectionConfig injectionConfig;
    /** 数据库表信息 */
    private Collection<TableInfo> tableInfoList;
    /** 包配置信息 */
    private Map<String, String> packageInfo;
    /** 路径配置信息 */
    private Map<String, String> pathInfoMap;

    /** 超类 */
    private String superEntityClass;
    private String superMapperClass;
    private String superDaoClass;
    private String superDaoImplClass;
    private String superServiceClass;
    private String superServiceImplClass;
    private String superControllerClass;

    /**
     * 在构造器中处理配置
     *
     * @param dataSourceConfig 数据源配置
     * @param globalConfig     全局配置
     * @param packageConfig    包配置
     * @param strategyConfig   表配置
     * @param templateConfig   模板配置
     */
    public ConfigBuilder(DataSourceConfig dataSourceConfig, GlobalConfig globalConfig, PackageConfig packageConfig,
        StrategyConfig strategyConfig, TemplateConfig templateConfig) {

        // 数据源配置
        this.dataSourceConfig = dataSourceConfig;
        // SQLITE 数据库不支持注释获取
        this.dataSourceConfig.setCommentSupported(!dataSourceConfig.getDbType().equals(DbType.SQLITE));

        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }

        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }

        // 模板配置
        if (null == templateConfig) {
            this.templateConfig = new TemplateConfig();
        } else {
            this.templateConfig = templateConfig;
        }

        // 包配置
        if (null == packageConfig) {
            handlerPackage(this.globalConfig, new PackageConfig(), this.templateConfig);
        } else {
            handlerPackage(this.globalConfig, packageConfig, this.templateConfig);
        }

        processTypes(this.strategyConfig);
    }

    /**
     * 处理包配置
     *
     * @param globalConfig   GlobalConfig
     * @param packageConfig  PackageConfig
     * @param templateConfig TemplateConfig
     */
    private void handlerPackage(GlobalConfig globalConfig, PackageConfig packageConfig, TemplateConfig templateConfig) {
        // 包信息
        packageInfo = new HashMap<>(11);
        packageInfo.put(ConstVal.MODULE_NAME, packageConfig.getModuleName());
        packageInfo.put(ConstVal.XML, packageConfig.getXml() +
            (StrUtil.isBlank(packageConfig.getModuleName()) ? "" : StringPool.SLASH + packageConfig.getModuleName()));
        packageInfo.put(ConstVal.ENTITY, joinPackage(packageConfig.getParent(), packageConfig.getEntity()));
        packageInfo.put(ConstVal.DTO, joinPackage(packageConfig.getParent(), packageConfig.getDto()));
        packageInfo.put(ConstVal.QUERY, joinPackage(packageConfig.getParent(), packageConfig.getQuery()));
        packageInfo.put(ConstVal.MAPPER, joinPackage(packageConfig.getParent(), packageConfig.getMapper()));
        packageInfo.put(ConstVal.DAO, joinPackage(packageConfig.getParent(), packageConfig.getDao()));
        packageInfo.put(ConstVal.DAO_IMPL, joinPackage(packageConfig.getParent(), packageConfig.getDaoImpl()));
        packageInfo.put(ConstVal.SERVICE, joinPackage(packageConfig.getParent(), packageConfig.getService()));
        packageInfo.put(ConstVal.SERVICE_IMPL, joinPackage(packageConfig.getParent(), packageConfig.getServiceImpl()));
        packageInfo.put(ConstVal.CONTROLLER, joinPackage(packageConfig.getParent(), packageConfig.getController()));

        // 自定义路径
        Map<String, String> configPathInfo = packageConfig.getPathInfo();
        if (null != configPathInfo) {
            pathInfoMap = configPathInfo;
        } else {
            // 生成路径信息
            pathInfoMap = new HashMap<>(11);

            // 设置 MyBatis Plus 各个 java 文件的包路径
            String javaDir = globalConfig.getBackendDir() +
                ConstVal.JAVA_PATH.replaceAll("//", StringPool.BACK_SLASH + File.separator);
            addPathInfo(pathInfoMap, templateConfig.getEntity(getGlobalConfig().isEnableKotlin()), javaDir,
                ConstVal.ENTITY_PATH, ConstVal.ENTITY);
            addPathInfo(pathInfoMap, templateConfig.getDto(), javaDir, ConstVal.DTO_PATH, ConstVal.DTO);
            addPathInfo(pathInfoMap, templateConfig.getQuery(), javaDir, ConstVal.QUERY_PATH, ConstVal.QUERY);
            addPathInfo(pathInfoMap, templateConfig.getMapper(), javaDir, ConstVal.MAPPER_PATH, ConstVal.MAPPER);
            addPathInfo(pathInfoMap, templateConfig.getDao(), javaDir, ConstVal.DAO_PATH, ConstVal.DAO);
            addPathInfo(pathInfoMap, templateConfig.getDaoImpl(), javaDir, ConstVal.DAO_IMPL_PATH, ConstVal.DAO_IMPL);
            addPathInfo(pathInfoMap, templateConfig.getService(), javaDir, ConstVal.SERVICE_PATH, ConstVal.SERVICE);
            addPathInfo(pathInfoMap, templateConfig.getServiceImpl(), javaDir, ConstVal.SERVICE_IMPL_PATH,
                ConstVal.SERVICE_IMPL);
            addPathInfo(pathInfoMap, templateConfig.getController(), javaDir, ConstVal.CONTROLLER_PATH,
                ConstVal.CONTROLLER);

            // 设置 MyBatis Plus 的 Mapper.xml 文件的包路径
            String resourcesDir = globalConfig.getBackendDir() +
                ConstVal.RESOURCES_PATH.replaceAll("//", StringPool.BACK_SLASH + File.separator);
            addPathInfo(pathInfoMap, templateConfig.getXml(), resourcesDir, ConstVal.XML_PATH, ConstVal.XML);

            // 设置前端文件的包路径
            String viewsDir = globalConfig.getFrontendDir() +
                ConstVal.VIEWS_PATH.replaceAll("//", StringPool.BACK_SLASH + File.separator);
            addPathInfo(pathInfoMap, templateConfig.getApi(), viewsDir, ConstVal.API_PATH, ConstVal.MODULE_NAME);
            addPathInfo(pathInfoMap, templateConfig.getList(), viewsDir, ConstVal.LIST_PATH, ConstVal.MODULE_NAME);
            addPathInfo(pathInfoMap, templateConfig.getForm(), viewsDir, ConstVal.FORM_PATH, ConstVal.MODULE_NAME);
        }
    }

    /**
     * 获取全局配置信息
     */
    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    // =====================================================================
    // 获取配置信息
    // =====================================================================

    private void addPathInfo(Map<String, String> pathInfo, String template, String outputDir, String path,
        String module) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
        }
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }

        if (StrUtil.isNotBlank(packageName)) {
            packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
            return parentDir + packageName;
        }
        return parentDir;
    }

    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (StringUtils.isBlank(parent)) {
            return subPackage;
        }
        return parent + StringPool.DOT + subPackage;
    }

    /**
     * 处理superClassName,IdClassType,IdStrategy配置
     *
     * @param config 策略配置
     */
    private void processTypes(StrategyConfig config) {
        if (StringUtils.isBlank(config.getSuperDaoClass())) {
            superDaoClass = ConstVal.SUPER_DAO_CLASS;
        } else {
            superDaoClass = config.getSuperDaoClass();
        }
        if (StringUtils.isBlank(config.getSuperDaoImplClass())) {
            superDaoImplClass = ConstVal.SUPER_DAO_IMPL_CLASS;
        } else {
            superDaoImplClass = config.getSuperDaoImplClass();
        }
        if (StringUtils.isBlank(config.getSuperServiceClass())) {
            superServiceClass = ConstVal.SUPER_SERVICE_CLASS;
        } else {
            superServiceClass = config.getSuperServiceClass();
        }
        if (StringUtils.isBlank(config.getSuperServiceImplClass())) {
            superServiceImplClass = ConstVal.SUPER_SERVICE_IMPL_CLASS;
        } else {
            superServiceImplClass = config.getSuperServiceImplClass();
        }
        if (StringUtils.isBlank(config.getSuperMapperClass())) {
            superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
        } else {
            superMapperClass = config.getSuperMapperClass();
        }
        superEntityClass = config.getSuperEntityClass();
        superControllerClass = config.getSuperControllerClass();
    }

    public static String getDefaultValidateTypeByJavaType(String javaType) {
        switch (javaType) {
            case "LocalDate":
            case "LocalTime":
            case "Year":
            case "YearMonth":
            case "LocalDateTime":
            case "Date":
            case "Time":
            case "Timestamp":
                return FormType.DateTimePicker.getCode();
            case "Boolean":
                return FormType.Switch.getCode();
            default:
                return FormType.Input.getCode();
        }
    }

    public List<TableInfo> queryTableInfoList() {

        checkConfig();

        boolean isInclude = (null != strategyConfig.getInclude() && strategyConfig.getInclude().length > 0);
        boolean isExclude = (null != strategyConfig.getExclude() && strategyConfig.getExclude().length > 0);
        if (isInclude && isExclude) {
            throw new RuntimeException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        }
        if (strategyConfig.getNotLikeTable() != null && strategyConfig.getLikeTable() != null) {
            throw new RuntimeException("<strategy> 标签中 <likeTable> 与 <notLikeTable> 只能配置一项！");
        }

        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();

        //需要反向生成或排除的表信息
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();

        //不存在的表名
        Set<String> notExistTables = new HashSet<>();
        try {
            String sql = getSearchTableSql(dataSourceConfig, strategyConfig, isInclude, isExclude);

            TableInfo tableInfo;
            try (PreparedStatement preparedStatement = dataSourceConfig.getConn().prepareStatement(sql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dataSourceConfig.getDbQuery().tableName());
                    if (StringUtils.isBlank(tableName)) {
                        System.err.println("当前数据库为空！！！");
                        continue;
                    }

                    tableInfo = new TableInfo();
                    tableInfo.setSchemaName(dataSourceConfig.getSchemaName()).setTableName(tableName);

                    if (dataSourceConfig.isCommentSupported()) {
                        String tableComment = results.getString(dataSourceConfig.getDbQuery().tableComment());
                        if (strategyConfig.isSkipView() && "VIEW".equals(tableComment)) {
                            // 跳过视图
                            continue;
                        }
                        tableInfo.setComment(tableComment);
                    }

                    if (isInclude) {
                        for (String includeTable : strategyConfig.getInclude()) {
                            // 忽略大小写等于 或 正则 true
                            if (tableNameMatches(includeTable, tableName)) {
                                includeTableList.add(tableInfo);
                            } else {
                                //过滤正则表名
                                if (!REGX.matcher(includeTable).find()) {
                                    notExistTables.add(includeTable);
                                }
                            }
                        }
                    } else if (isExclude) {
                        for (String excludeTable : strategyConfig.getExclude()) {
                            // 忽略大小写等于 或 正则 true
                            if (tableNameMatches(excludeTable, tableName)) {
                                excludeTableList.add(tableInfo);
                            } else {
                                //过滤正则表名
                                if (!REGX.matcher(excludeTable).find()) {
                                    notExistTables.add(excludeTable);
                                }
                            }
                        }
                    }
                    tableList.add(tableInfo);
                }
            }

            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                notExistTables.remove(tabInfo.getTableName());
            }
            if (notExistTables.size() > 0) {
                System.err.println("表 " + notExistTables + " 在数据库中不存在！！！");
            }

            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
                includeTableList = tableList;
            }
            if (!isInclude && !isExclude) {
                includeTableList = tableList;
            }

            for (TableInfo table : includeTableList) {
                processTable(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return includeTableList;
    }

    /**
     * 处理表对应的类名称
     *
     * @param tableInfo 表信息
     * @return 补充完整信息后的表
     */
    public TableInfo processTable(TableInfo tableInfo) {

        // 校验必要配置项
        checkConfig();
        // 根据配置项，动态填充字段部分相关属性
        convertTableFields(tableInfo, globalConfig, strategyConfig, dataSourceConfig);
        // 将字段根据是否出现在 列表、表单、查询、排序、校验 进行分组
        groupTableFields(tableInfo);

        String entityName;
        INameConvert nameConvert = strategyConfig.getNameConvert();
        if (null != nameConvert) {
            // 自定义处理实体名称
            entityName = nameConvert.entityNameConvert(tableInfo);
        } else {
            entityName = NamingStrategy.capitalFirst(
                processName(tableInfo.getTableName(), strategyConfig.getNaming(), strategyConfig.getTablePrefix()));
        }
        if (StringUtils.isNotBlank(globalConfig.getEntityName())) {
            tableInfo.setConvert(true);
            tableInfo.setEntityName(String.format(globalConfig.getEntityName(), entityName));
        } else {
            tableInfo.setEntityName(strategyConfig, entityName);
        }
        if (StringUtils.isNotBlank(globalConfig.getMapperName())) {
            tableInfo.setMapperName(String.format(globalConfig.getMapperName(), entityName));
        } else {
            tableInfo.setMapperName(entityName + ConstVal.MAPPER);
        }
        if (StringUtils.isNotBlank(globalConfig.getDaoName())) {
            tableInfo.setDaoName(String.format(globalConfig.getDaoName(), entityName));
        } else {
            tableInfo.setDaoName(entityName + ConstVal.DAO);
        }
        if (StringUtils.isNotBlank(globalConfig.getDaoImplName())) {
            tableInfo.setDaoImplName(String.format(globalConfig.getDaoImplName(), entityName));
        } else {
            tableInfo.setDaoImplName(entityName + ConstVal.DAO_IMPL);
        }
        if (StringUtils.isNotBlank(globalConfig.getServiceName())) {
            tableInfo.setServiceName(String.format(globalConfig.getServiceName(), entityName));
        } else {
            tableInfo.setServiceName(entityName + ConstVal.SERVICE);
        }
        if (StringUtils.isNotBlank(globalConfig.getServiceImplName())) {
            tableInfo.setServiceImplName(String.format(globalConfig.getServiceImplName(), entityName));
        } else {
            tableInfo.setServiceImplName(entityName + ConstVal.SERVICE_IMPL);
        }
        if (StringUtils.isNotBlank(globalConfig.getControllerName())) {
            tableInfo.setControllerName(String.format(globalConfig.getControllerName(), entityName));
        } else {
            tableInfo.setControllerName(entityName + ConstVal.CONTROLLER);
        }
        if (StringUtils.isNotBlank(globalConfig.getXmlName())) {
            tableInfo.setXmlName(String.format(globalConfig.getXmlName(), entityName));
        } else {
            tableInfo.setXmlName(entityName + ConstVal.MAPPER);
        }
        if (StringUtils.isNotBlank(globalConfig.getApiName())) {
            tableInfo.setApiName(String.format(globalConfig.getApiName(), entityName));
        } else {
            tableInfo.setApiName(entityName + ConstVal.API);
        }
        if (StringUtils.isNotBlank(globalConfig.getListName())) {
            tableInfo.setListName(String.format(globalConfig.getListName(), entityName));
        } else {
            tableInfo.setListName(entityName + ConstVal.LIST);
        }
        if (StringUtils.isNotBlank(globalConfig.getFormName())) {
            tableInfo.setFormName(String.format(globalConfig.getFormName(), entityName));
        } else {
            tableInfo.setFormName(entityName + ConstVal.FORM);
        }
        // 检测导入包
        checkImportPackages(tableInfo, globalConfig, strategyConfig);
        return tableInfo;
    }

    /**
     * 检测导入包
     *
     * @param tableInfo ignore
     */
    private void checkImportPackages(TableInfo tableInfo, GlobalConfig global, StrategyConfig strategy) {
        if (StringUtils.isNotBlank(strategy.getSuperEntityClass())) {
            // 自定义父类
            tableInfo.getImportPackages().add(strategy.getSuperEntityClass());
        } else if (global.isEnableActiveRecord()) {
            // 无父类开启 AR 模式
            tableInfo.getImportPackages()
                .add(com.baomidou.mybatisplus.extension.activerecord.Model.class.getCanonicalName());
        }
        if (null != global.getIdType()) {
            // 指定需要 IdType 场景
            tableInfo.getImportPackages().add(com.baomidou.mybatisplus.annotation.IdType.class.getCanonicalName());
            tableInfo.getImportPackages().add(com.baomidou.mybatisplus.annotation.TableId.class.getCanonicalName());
        }
        if (StringUtils.isNotBlank(strategy.getVersionFieldName())
            && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
            tableInfo.getFields().forEach(f -> {
                if (strategy.getVersionFieldName().equals(f.getFieldName())) {
                    tableInfo.getImportPackages()
                        .add(com.baomidou.mybatisplus.annotation.Version.class.getCanonicalName());
                }
            });
        }
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo 表信息
     * @param strategy  命名策略
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo,
        GlobalConfig global, StrategyConfig strategy, DataSourceConfig dataSource) {
        List<TableField> fieldList = new ArrayList<>();
        List<TableField> commonFieldList = new ArrayList<>();
        DbType dbType = dataSource.getDbType();
        IDbQuery dbQuery = dataSource.getDbQuery();
        Connection connection = dataSource.getConn();
        String tableName = tableInfo.getTableName();
        Map<String, TableField> fieldMap = tableInfo.getFieldMap();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();

            // 根据不同 SQL 类型，组织查询 SQL
            switch (dbType) {
                case POSTGRE_SQL:
                case KINGBASE_ES:
                case DB2:
                    tableFieldsSql = String.format(tableFieldsSql, dataSource.getSchemaName(), tableName);
                    break;
                case ORACLE:
                    tableName = tableName.toUpperCase();
                    tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSource.getSchemaName()),
                        tableName);
                    break;
                case DM:
                    tableName = tableName.toUpperCase();
                    tableFieldsSql = String.format(tableFieldsSql, tableName);
                    break;
                case H2:
                    tableName = tableName.toUpperCase();
                    try (PreparedStatement pkQueryStmt = connection.prepareStatement(
                        String.format(H2Query.PK_QUERY_SQL, tableName));
                         ResultSet pkResults = pkQueryStmt.executeQuery()) {
                        while (pkResults.next()) {
                            String primaryKey = pkResults.getString(dbQuery.fieldKey());
                            if (Boolean.parseBoolean(primaryKey)) {
                                h2PkColumns.add(pkResults.getString(dbQuery.fieldName()));
                            }
                        }
                    }
                    tableFieldsSql = String.format(tableFieldsSql, tableName);
                    break;
                default:
                    tableFieldsSql = String.format(tableFieldsSql, tableName);
                    break;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String columnName = results.getString(dbQuery.fieldName());
                    TableField field = null;
                    if (MapUtil.isNotEmpty(fieldMap)) {
                        field = fieldMap.get(columnName);
                    }

                    if (field == null) {
                        field = new TableField();
                    }

                    field.setSchemaName(dataSource.getSchemaName())
                        .setTableName(tableInfo.getTableName());

                    // 是否允许为空
                    String nullAble = results.getString(dbQuery.nullAble());
                    field.setNotNull("NO".equalsIgnoreCase(nullAble));

                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    boolean isPk;
                    if (DbType.H2 == dbType) {
                        isPk = h2PkColumns.contains(columnName);
                    } else {
                        String key = results.getString(dbQuery.fieldKey());
                        field.setKeyType(key);
                        // if (StrUtil.isNotBlank(key)) {
                        //     field.setEnableQuery(true);
                        // }

                        if (DbType.DB2 == dbType || DbType.SQLITE == dbType) {
                            isPk = StringUtils.isNotBlank(key) && "1".equals(key);
                        } else {
                            isPk = StringUtils.isNotBlank(key) && "PRI".equals(key.toUpperCase());
                        }
                    }

                    // 处理ID
                    if (isPk) {
                        field.setKeyFlag(true);
                        field.setEnableForm(false);
                        field.setEnableSort(true);
                        field.setSortType("asc");
                        if (DbType.H2 == dbType || DbType.SQLITE == dbType || dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        } else {
                            field.setKeyIdentityFlag(true);
                        }
                    } else {
                        field.setKeyFlag(false);
                    }

                    // 自定义字段查询
                    String[] fcs = dbQuery.fieldCustom();
                    if (null != fcs) {
                        Map<String, Object> customMap = new HashMap<>(fcs.length);
                        for (String fc : fcs) {
                            customMap.put(fc, results.getObject(fc));
                        }
                        field.setCustomMap(customMap);
                    }
                    // 处理其它信息
                    field.setFieldName(columnName);
                    field.setType(results.getString(dbQuery.fieldType()));

                    INameConvert nameConvert = strategy.getNameConvert();
                    if (null != nameConvert) {
                        field.setPropertyName(nameConvert.propertyNameConvert(field));
                    } else {
                        field.setPropertyName(strategy,
                            processName(field.getFieldName(), strategy.getNaming(), strategy));
                    }
                    field.setJavaType(dataSource.getTypeConvert().processTypeConvert(global, field));
                    if (field.getFormType() == null) {
                        field.setFormType(getDefaultFormTypeByJavaType(field.getJavaType().getType()));
                    }
                    field.setValidateType(field.getValidateType());

                    if (dataSource.isCommentSupported()) {
                        field.setComment(results.getString(dbQuery.fieldComment()));
                    }
                    if (strategy.includeSuperEntityColumns(field.getFieldName())) {
                        // 跳过公共字段
                        commonFieldList.add(field);
                        continue;
                    }
                    // 填充逻辑判断
                    List<TableFill> tableFillList = getStrategyConfig().getTableFillList();
                    if (null != tableFillList) {
                        // 忽略大写字段问题
                        for (TableFill tableFill : tableFillList) {
                            if (tableFill.getFieldName().equalsIgnoreCase(field.getFieldName())) {
                                field.setFill(tableFill.getFieldFill().name());
                                break;
                            }
                        }
                    }
                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }

    /**
     * 获取策略配置
     */
    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    // =====================================================================
    // 获取超类定义
    // =====================================================================

    /**
     * 处理字段名称
     *
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy namingStrategy, StrategyConfig strategy) {
        return processName(name, namingStrategy, strategy.getFieldPrefix());
    }

    /**
     * 处理表/字段名称
     *
     * @param name     ignore
     * @param strategy ignore
     * @param prefix   ignore
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy strategy, String[] prefix) {
        boolean removePrefix = false;
        if (prefix != null && prefix.length != 0) {
            removePrefix = true;
        }
        String propertyName;
        if (removePrefix) {
            if (strategy == NamingStrategy.underline_to_camel) {
                // 删除前缀、下划线转驼峰
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                // 删除前缀
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            // 下划线转驼峰
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // 不处理
            propertyName = name;
        }
        return propertyName;
    }

    public static String getDefaultFormTypeByJavaType(String javaType) {
        switch (javaType) {
            case "LocalDate":
            case "LocalTime":
            case "Year":
            case "YearMonth":
            case "LocalDateTime":
            case "Date":
            case "Time":
            case "Timestamp":
                return FormType.DateTimePicker.getCode();
            case "Boolean":
                return FormType.Switch.getCode();
            default:
                return FormType.Input.getCode();
        }
    }

    public void groupTableFields(TableInfo tableInfo) {
        if (tableInfo == null || CollectionUtil.isEmpty(tableInfo.getFields())) {
            return;
        }

        List<TableField> listFields = new ArrayList<>();
        List<TableField> formFields = new ArrayList<>();
        List<TableField> queryFields = new ArrayList<>();
        List<TableField> queryExtFields = new ArrayList<>();
        List<TableField> sortFields = new ArrayList<>();
        List<TableField> dictFields = new ArrayList<>();

        for (TableField field : tableInfo.getFields()) {
            if (field.isEnableList()) {
                listFields.add(field);
            }

            if (field.isEnableForm()) {
                formFields.add(field);
            }

            if (field.isEnableQuery()) {
                if (queryFields.size() < 3) {
                    queryFields.add(field);
                } else {
                    queryExtFields.add(field);
                }
            }

            if (field.isEnableSort()) {
                sortFields.add(field);
            }

            if (StrUtil.isNotBlank(field.getDictCode())) {
                dictFields.add(field);
            }
        }

        tableInfo.setListFields(listFields)
            .setFormFields(formFields)
            .setQueryFields(queryFields)
            .setQueryExtFields(queryExtFields)
            .setSortFields(sortFields)
            .setDictFields(dictFields);
    }

    /**
     * 组装查询表信息的 SQL
     *
     * @param config    策略配置
     * @param isInclude 包含的表
     * @param isExclude 排除的表
     * @return /
     */
    private String getSearchTableSql(DataSourceConfig dataSource, StrategyConfig config,
        boolean isInclude, boolean isExclude) {

        IDbQuery dbQuery = dataSource.getDbQuery();
        String tablesSql = dbQuery.tablesSql();

        if (DbType.POSTGRE_SQL == dataSource.getDbType()) {
            String schema = dataSource.getSchemaName();
            if (schema == null) {
                //pg 默认 schema=public
                schema = "public";
                dataSource.setSchemaName(schema);
            }
            tablesSql = String.format(tablesSql, schema);
        } else if (DbType.KINGBASE_ES == dataSource.getDbType()) {
            String schema = dataSource.getSchemaName();
            if (schema == null) {
                //kingbase 默认 schema=PUBLIC
                schema = "PUBLIC";
                dataSource.setSchemaName(schema);
            }
            tablesSql = String.format(tablesSql, schema);
        } else if (DbType.DB2 == dataSource.getDbType()) {
            String schema = dataSource.getSchemaName();
            if (schema == null) {
                //db2 默认 schema=current schema
                schema = "current schema";
                dataSource.setSchemaName(schema);
            }
            tablesSql = String.format(tablesSql, schema);
        }
        //oracle数据库表太多，出现最大游标错误
        else if (DbType.ORACLE == dataSource.getDbType()) {
            String schema = dataSource.getSchemaName();
            //oracle 默认 schema=username
            if (schema == null) {
                schema = dataSource.getUsername().toUpperCase();
                dataSource.setSchemaName(schema);
            }
            tablesSql = String.format(tablesSql, schema);
        }
        StringBuilder sql = new StringBuilder(tablesSql);
        if (config.isEnableSqlFilter()) {
            if (config.getLikeTable() != null) {
                sql.append(" AND ")
                    .append(dbQuery.tableName())
                    .append(" LIKE '")
                    .append(config.getLikeTable().getValue())
                    .append("'");
            } else if (config.getNotLikeTable() != null) {
                sql.append(" AND ")
                    .append(dbQuery.tableName())
                    .append(" NOT LIKE '")
                    .append(config.getNotLikeTable().getValue())
                    .append("'");
            }
            if (isInclude) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" IN (")
                    .append(Arrays.stream(config.getInclude())
                        .map(tb -> "'" + tb + "'")
                        .collect(Collectors.joining(","))).append(")");
            } else if (isExclude) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" NOT IN (")
                    .append(Arrays.stream(config.getExclude())
                        .map(tb -> "'" + tb + "'")
                        .collect(Collectors.joining(","))).append(")");
            }
        }
        return sql.toString();
    }

    /**
     * 表名匹配
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return ignore
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equalsIgnoreCase(dbTableName)
            || StringUtils.matches(setTableName, dbTableName);
    }

    private void checkConfig() {

        if (this.globalConfig == null) {
            throw new DataException("globalConfig 未配置");
        }

        if (this.dataSourceConfig == null) {
            throw new DataException("dataSourceConfig 未配置");
        }

        if (this.strategyConfig == null) {
            throw new DataException("strategyConfig 未配置");
        }

        if (this.templateConfig == null) {
            throw new DataException("templateConfig 未配置");
        }

        if (MapUtil.isEmpty(this.packageInfo)) {
            throw new DataException("packageInfo 未配置");
        }
    }

    // =====================================================================
    // 私有方法
    // =====================================================================

    /**
     * 获取数据源配置
     */
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    /**
     * 获取模板路径配置信息
     */
    public TemplateConfig getTemplateConfig() {
        return templateConfig == null ? new TemplateConfig() : templateConfig;
    }

    /**
     * 获取注入配置信息
     */
    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public ConfigBuilder setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    /**
     * 所有包配置信息
     *
     * @return 包配置
     */
    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }

    /**
     * 所有路径配置
     *
     * @return 路径配置
     */
    public Map<String, String> getPathInfoMap() {
        return pathInfoMap;
    }

    /**
     * 表信息
     *
     * @return 所有表信息
     */
    public Collection<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public ConfigBuilder setTableInfoList(Collection<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    public String getSuperDaoClass() {
        return superDaoClass;
    }

    public String getSuperDaoImplClass() {
        return superDaoImplClass;
    }

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

}
