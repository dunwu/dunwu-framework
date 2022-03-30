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
package io.github.dunwu.tool.generator.engine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import freemarker.template.TemplateException;
import io.github.dunwu.tool.generator.InjectionConfig;
import io.github.dunwu.tool.generator.config.ConstVal;
import io.github.dunwu.tool.generator.config.FileOutConfig;
import io.github.dunwu.tool.generator.config.GlobalConfig;
import io.github.dunwu.tool.generator.config.TemplateConfig;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.config.rules.FileType;
import io.github.dunwu.tool.io.AnsiColorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 模板引擎初始化
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 获取预览模板内容
     *
     * @return /
     * @throws IOException       /
     * @throws TemplateException /
     */
    public List<CodeGenerateContentDto> preview() throws IOException, TemplateException {
        List<CodeGenerateContentDto> codeGenerateContentDtos = new ArrayList<>();

        Collection<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            Map<String, Object> objectMap = getObjectMap(tableInfo);
            TemplateConfig template = configBuilder.getTemplateConfig();

            // 自定义内容
            InjectionConfig injectionConfig = configBuilder.getInjectionConfig();
            if (null != injectionConfig) {
                injectionConfig.initTableMap(tableInfo);
                objectMap.put("cfg", injectionConfig.getMap());
            }

            // @formatter:off
            CodeGenerateContentDto entity = new CodeGenerateContentDto(FileType.ENTITY.getCode(), getMergeContent(objectMap, getTemplatePath(template.getEntity())));
            codeGenerateContentDtos.add(entity);
            CodeGenerateContentDto dto = new CodeGenerateContentDto(FileType.DTO.getCode(), getMergeContent(objectMap, getTemplatePath(template.getDto())));
            codeGenerateContentDtos.add(dto);
            CodeGenerateContentDto query = new CodeGenerateContentDto(FileType.QUERY.getCode(), getMergeContent(objectMap, getTemplatePath(template.getQuery())));
            codeGenerateContentDtos.add(query);
            CodeGenerateContentDto mapper = new CodeGenerateContentDto(FileType.MAPPER.getCode(), getMergeContent(objectMap, getTemplatePath(template.getMapper())));
            codeGenerateContentDtos.add(mapper);
            CodeGenerateContentDto dao = new CodeGenerateContentDto(FileType.DAO.getCode(), getMergeContent(objectMap, getTemplatePath(template.getDao())));
            codeGenerateContentDtos.add(dao);
            CodeGenerateContentDto daoImpl = new CodeGenerateContentDto(FileType.DAO_IMPL.getCode(), getMergeContent(objectMap, getTemplatePath(template.getDaoImpl())));
            codeGenerateContentDtos.add(daoImpl);
            CodeGenerateContentDto service = new CodeGenerateContentDto(FileType.SERVICE.getCode(), getMergeContent(objectMap, getTemplatePath(template.getService())));
            codeGenerateContentDtos.add(service);
            CodeGenerateContentDto serviceImpl = new CodeGenerateContentDto(FileType.SERVICE_IMPL.getCode(), getMergeContent(objectMap, getTemplatePath(template.getServiceImpl())));
            codeGenerateContentDtos.add(serviceImpl);
            CodeGenerateContentDto controller = new CodeGenerateContentDto(FileType.CONTROLLER.getCode(), getMergeContent(objectMap, getTemplatePath(template.getController())));
            codeGenerateContentDtos.add(controller);
            CodeGenerateContentDto xml = new CodeGenerateContentDto(FileType.XML.getCode(), getMergeContent(objectMap, getTemplatePath(template.getXml())));
            codeGenerateContentDtos.add(xml);
            CodeGenerateContentDto api = new CodeGenerateContentDto(FileType.API.getCode(), getMergeContent(objectMap, getTemplatePath(template.getApi())));
            codeGenerateContentDtos.add(api);
            CodeGenerateContentDto list = new CodeGenerateContentDto(FileType.LIST.getCode(), getMergeContent(objectMap, getTemplatePath(template.getList())));
            codeGenerateContentDtos.add(list);
            CodeGenerateContentDto form = new CodeGenerateContentDto(FileType.FORM.getCode(), getMergeContent(objectMap, getTemplatePath(template.getForm())));
            codeGenerateContentDtos.add(form);
            // @formatter:on
        }

        return codeGenerateContentDtos;
    }

    /**
     * 生成前部前后端代码
     */
    public AbstractTemplateEngine generateAll() {
        List<FileType> frontendTypes = CollectionUtil.newArrayList(FileType.API, FileType.LIST, FileType.FORM);
        List<FileType> backendTypes = CollectionUtil.newArrayList(FileType.ENTITY, FileType.DTO, FileType.QUERY,
            FileType.MAPPER, FileType.DAO, FileType.DAO_IMPL, FileType.SERVICE, FileType.SERVICE_IMPL,
            FileType.CONTROLLER, FileType.XML);
        List<FileType> types = new ArrayList<>();
        types.addAll(frontendTypes);
        types.addAll(backendTypes);
        return generate(types);
    }

    public AbstractTemplateEngine generate(Collection<FileType> types) {

        if (CollectionUtil.isEmpty(types)) {
            logger.error("未指定类型");
            return null;
        }

        try {
            Collection<TableInfo> tableInfoList = configBuilder.getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfoMap = configBuilder.getPathInfoMap();
                TemplateConfig template = configBuilder.getTemplateConfig();
                // 自定义内容
                InjectionConfig injectionConfig = configBuilder.getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                outputFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }

                AnsiColorUtil.BOLD_BLUE.println(JSONUtil.toJsonStr(objectMap));

                for (FileType type : types) {
                    generateSpecifiedTypeFile(tableInfo, objectMap, pathInfoMap, template, type);
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    private void generateSpecifiedTypeFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template, FileType type) throws Exception {
        switch (type) {
            case ENTITY:
                generateEntityFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case DTO:
                generateDtoFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case QUERY:
                generateQueryFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case MAPPER:
                generateMapperFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case DAO:
                generateDaoFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case DAO_IMPL:
                generateDaoImplFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case SERVICE:
                generateServiceFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case SERVICE_IMPL:
                generateServiceImplFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case CONTROLLER:
                generateControllerFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case XML:
                generateXmlFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case API:
                generateApiFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case LIST:
                generateListFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            case FORM:
                generateFormFile(tableInfo, objectMap, pathInfoMap, template);
                break;
            default:
                logger.error("不支持生成 type = {} 类型的文件", type);
                break;
        }
    }

    private void generateApiFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getApiName() && null != pathInfoMap.get(FileType.API.getFilePath())) {
            String apiFile = String.format((pathInfoMap.get(FileType.API.getFilePath())
                + File.separator
                + tableInfo.getApiName()
                + ConstVal.JS_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.API, apiFile)) {
                outputFile(objectMap, getTemplatePath(template.getApi()), apiFile);
            }
        }
    }

    private void generateListFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getListName() && null != pathInfoMap.get(FileType.LIST.getFilePath())) {
            String listFile = String.format((pathInfoMap.get(FileType.LIST.getFilePath())
                + File.separator
                + tableInfo.getListName()
                + ConstVal.VUE_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.LIST, listFile)) {
                outputFile(objectMap, getTemplatePath(template.getList()), listFile);
            }
        }
    }

    private void generateFormFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getFormName() && null != pathInfoMap.get(FileType.FORM.getFilePath())) {
            String formFile = String.format((pathInfoMap.get(FileType.FORM.getFilePath())
                + File.separator
                + tableInfo.getFormName()
                + ConstVal.VUE_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.FORM, formFile)) {
                outputFile(objectMap, getTemplatePath(template.getForm()), formFile);
            }
        }
    }

    private void generateEntityFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getEntityName() && null != pathInfoMap.get(FileType.ENTITY.getFilePath())) {
            String entityFile = String.format(
                (pathInfoMap.get(FileType.ENTITY.getFilePath()) + File.separator + "%s" + suffixJavaOrKt()),
                tableInfo.getEntityName());
            if (isCreate(FileType.ENTITY, entityFile)) {
                outputFile(objectMap,
                    getTemplatePath(template.getEntity()),
                    entityFile);
            }
        }
    }

    private void generateDtoFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        String dtoName = tableInfo.getDtoName();
        if (null != dtoName && null != pathInfoMap.get(FileType.DTO.getFilePath())) {
            String dtoFile = String.format(
                (pathInfoMap.get(FileType.DTO.getFilePath()) + File.separator + "%s" + suffixJavaOrKt()), dtoName);
            if (isCreate(FileType.DTO, dtoFile)) {
                outputFile(objectMap, getTemplatePath(template.getDto()), dtoFile);
            }
        }
    }

    private void generateQueryFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        String queryName = tableInfo.getQueryName();
        if (null != queryName && null != pathInfoMap.get(FileType.QUERY.getFilePath())) {
            String queryFile = String.format(
                (pathInfoMap.get(FileType.QUERY.getFilePath()) + File.separator + "%s" + suffixJavaOrKt()), queryName);
            if (isCreate(FileType.QUERY, queryFile)) {
                outputFile(objectMap, getTemplatePath(template.getQuery()), queryFile);
            }
        }
    }

    private void generateMapperFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getMapperName() && null != pathInfoMap.get(FileType.MAPPER.getFilePath())) {
            String mapperFile = String.format(
                (pathInfoMap.get(FileType.MAPPER.getFilePath())
                    + File.separator
                    + tableInfo.getMapperName()
                    + suffixJavaOrKt()),
                tableInfo.getEntityName());
            if (isCreate(FileType.MAPPER, mapperFile)) {
                outputFile(objectMap, getTemplatePath(template.getMapper()), mapperFile);
            }
        }
    }

    private void generateDaoFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getDaoName() && null != pathInfoMap.get(FileType.DAO.getFilePath())) {
            String daoFile = String.format(
                (pathInfoMap.get(FileType.DAO.getFilePath())
                    + File.separator
                    + tableInfo.getDaoName()
                    + suffixJavaOrKt()),
                tableInfo.getEntityName());
            if (isCreate(FileType.DAO, daoFile)) {
                outputFile(objectMap, getTemplatePath(template.getDao()), daoFile);
            }
        }
    }

    private void generateDaoImplFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getDaoImplName() && null != pathInfoMap.get(FileType.DAO_IMPL.getFilePath())) {
            String implFile = String.format((pathInfoMap.get(FileType.DAO_IMPL.getFilePath())
                + File.separator
                + tableInfo.getDaoImplName()
                + suffixJavaOrKt()), tableInfo.getEntityName());
            if (isCreate(FileType.DAO_IMPL, implFile)) {
                outputFile(objectMap, getTemplatePath(template.getDaoImpl()), implFile);
            }
        }
    }

    private void generateServiceFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getServiceName() && null != pathInfoMap.get(FileType.SERVICE.getFilePath())) {
            String serviceFile = String.format((pathInfoMap.get(FileType.SERVICE.getFilePath())
                + File.separator
                + tableInfo.getServiceName()
                + suffixJavaOrKt()), tableInfo.getEntityName());
            if (isCreate(FileType.SERVICE, serviceFile)) {
                outputFile(objectMap, getTemplatePath(template.getService()), serviceFile);
            }
        }
    }

    private void generateServiceImplFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getServiceImplName() && null != pathInfoMap.get(FileType.SERVICE_IMPL.getFilePath())) {
            String implFile = String.format((pathInfoMap.get(FileType.SERVICE_IMPL.getFilePath())
                + File.separator
                + tableInfo.getServiceImplName()
                + suffixJavaOrKt()), tableInfo.getEntityName());
            if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                outputFile(objectMap, getTemplatePath(template.getServiceImpl()), implFile);
            }
        }
    }

    private void generateControllerFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getControllerName() && null != pathInfoMap.get(FileType.CONTROLLER.getFilePath())) {
            String controllerFile = String.format((pathInfoMap.get(FileType.CONTROLLER.getFilePath())
                + File.separator
                + tableInfo.getControllerName()
                + suffixJavaOrKt()), tableInfo.getEntityName());
            if (isCreate(FileType.CONTROLLER, controllerFile)) {
                outputFile(objectMap, getTemplatePath(template.getController()), controllerFile);
            }
        }
    }

    private void generateXmlFile(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        if (null != tableInfo.getXmlName() && null != pathInfoMap.get(FileType.XML.getFilePath())) {
            String xmlFile = String.format((pathInfoMap.get(FileType.XML.getFilePath())
                + File.separator
                + tableInfo.getXmlName()
                + ConstVal.XML_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.XML, xmlFile)) {
                outputFile(objectMap, getTemplatePath(template.getXml()), xmlFile);
            }
        }
    }

    public abstract String getMergeContent(Map<String, Object> objectMap, String templatePath)
        throws IOException, TemplateException;

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void outputFile(Map<String, Object> objectMap, String templatePath, String outputFile)
        throws Exception;

    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = configBuilder;
        if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
            objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
            objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }
        objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("enableActiveRecord", globalConfig.isEnableActiveRecord());
        objectMap.put("enableSwagger", tableInfo.isEnableSwagger());
        objectMap.put("enableEasyExcel", tableInfo.isEnableEasyExcel());
        objectMap.put("enableLog", tableInfo.isEnableLog());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", globalConfig.isEnableCache());
        objectMap.put("enableBaseResultMap", globalConfig.isEnableBaseResultMap());
        objectMap.put("enableBaseColumnList", globalConfig.isEnableBaseColumnList());
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
        objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
        objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
        objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
        objectMap.put("entityBooleanColumnRemoveIsPrefix",
            config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superDaoClassPackage", config.getSuperDaoClass());
        objectMap.put("superDaoClass", getSuperClassName(config.getSuperDaoClass()));
        objectMap.put("superDaoImplClassPackage", config.getSuperDaoImplClass());
        objectMap.put("superDaoImplClass", getSuperClassName(config.getSuperDaoImplClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", verifyClassPacket(config.getSuperControllerClass()));
        objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
        return Objects.isNull(config.getInjectionConfig()) ? objectMap
            : config.getInjectionConfig().prepareObjectMap(objectMap);
    }

    /**
     * 用于渲染对象MAP信息 {@link #getObjectMap(TableInfo)} 时的superClassPacket非空校验
     *
     * @param classPacket ignore
     * @return ignore
     */
    private String verifyClassPacket(String classPacket) {
        return StringUtils.isBlank(classPacket) ? null : classPacket;
    }

    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isBlank(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }

    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String getTemplatePath(String filePath);

    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     */
    protected boolean isCreate(FileType fileType, String filePath) {
        ConfigBuilder cb = configBuilder;
        // 自定义判断
        InjectionConfig ic = cb.getInjectionConfig();
        if (null != ic && null != ic.getFileCreate()) {
            return ic.getFileCreate().isCreate(cb, fileType, filePath);
        }
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.getParentFile().mkdirs();
        }
        return !exist || configBuilder.getGlobalConfig().isEnableOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return ConstVal.JAVA_SUFFIX;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 处理输出目录
     */
    public AbstractTemplateEngine mkdirs() {
        configBuilder.getPathInfoMap().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug(">>>> 创建目录：\n【目录】{}", value);
                }
            }
        });
        return this;
    }

    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = configBuilder.getGlobalConfig().getOutputDir();
        if (configBuilder.getGlobalConfig().isOpen() && StringUtils.isNotBlank(outDir)) {
            try {
                String osName = System.getProperty("os.name");
                if (osName != null) {
                    if (osName.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + outDir);
                    } else if (osName.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + outDir);
                    } else {
                        logger.debug("文件输出目录:" + outDir);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
