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

        Collection<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            Map<String, Object> objectMap = getObjectMap(tableInfo);
            TemplateConfig template = getConfigBuilder().getTemplateConfig();

            // 自定义内容
            InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
            if (null != injectionConfig) {
                injectionConfig.initTableMap(tableInfo);
                objectMap.put("cfg", injectionConfig.getMap());
            }

            CodeGenerateContentDto entity = new CodeGenerateContentDto(ConstVal.ENTITY, getMergeContent(objectMap,
                templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isEnableKotlin()))));
            codeGenerateContentDtos.add(entity);
            CodeGenerateContentDto dto = new CodeGenerateContentDto(ConstVal.DTO,
                getMergeContent(objectMap, templateFilePath(template.getDto())));
            codeGenerateContentDtos.add(dto);
            CodeGenerateContentDto query = new CodeGenerateContentDto(ConstVal.QUERY,
                getMergeContent(objectMap, templateFilePath(template.getQuery())));
            codeGenerateContentDtos.add(query);
            CodeGenerateContentDto mapper = new CodeGenerateContentDto(ConstVal.MAPPER,
                getMergeContent(objectMap, templateFilePath(template.getMapper())));
            codeGenerateContentDtos.add(mapper);
            CodeGenerateContentDto dao = new CodeGenerateContentDto(ConstVal.DAO,
                getMergeContent(objectMap, templateFilePath(template.getDao())));
            codeGenerateContentDtos.add(dao);
            CodeGenerateContentDto daoImpl = new CodeGenerateContentDto(ConstVal.DAO_IMPL,
                getMergeContent(objectMap, templateFilePath(template.getDaoImpl())));
            codeGenerateContentDtos.add(daoImpl);
            CodeGenerateContentDto service = new CodeGenerateContentDto(ConstVal.SERVICE,
                getMergeContent(objectMap, templateFilePath(template.getService())));
            codeGenerateContentDtos.add(service);
            CodeGenerateContentDto serviceImpl = new CodeGenerateContentDto(ConstVal.SERVICE_IMPL,
                getMergeContent(objectMap, templateFilePath(template.getServiceImpl())));
            codeGenerateContentDtos.add(serviceImpl);
            CodeGenerateContentDto controller = new CodeGenerateContentDto(ConstVal.CONTROLLER,
                getMergeContent(objectMap, templateFilePath(template.getController())));
            codeGenerateContentDtos.add(controller);
            CodeGenerateContentDto xml = new CodeGenerateContentDto(ConstVal.XML,
                getMergeContent(objectMap, templateFilePath(template.getXml())));
            codeGenerateContentDtos.add(xml);
            CodeGenerateContentDto api = new CodeGenerateContentDto(ConstVal.API,
                getMergeContent(objectMap, templateFilePath(template.getApi())));
            codeGenerateContentDtos.add(api);
            CodeGenerateContentDto list = new CodeGenerateContentDto(ConstVal.LIST,
                getMergeContent(objectMap, templateFilePath(template.getList())));
            codeGenerateContentDtos.add(list);
            CodeGenerateContentDto form = new CodeGenerateContentDto(ConstVal.FORM,
                getMergeContent(objectMap, templateFilePath(template.getForm())));
            codeGenerateContentDtos.add(form);
        }

        return codeGenerateContentDtos;
    }

    /**
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {

        try {
            Collection<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfoMap = getConfigBuilder().getPathInfoMap();
                TemplateConfig template = getConfigBuilder().getTemplateConfig();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }

                AnsiColorUtil.BOLD_BLUE.println(JSONUtil.toJsonStr(objectMap));

                // 生成后端源码文件
                outputBackendFiles(tableInfo, objectMap, pathInfoMap, template);
                outputFrontendFiles(tableInfo, objectMap, pathInfoMap, template);
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    private void outputFrontendFiles(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        // MpApi.js
        if (null != tableInfo.getApiName() && null != pathInfoMap.get(ConstVal.API_PATH)) {
            String apiFile = String.format((pathInfoMap.get(ConstVal.API_PATH)
                + File.separator
                + tableInfo.getApiName()
                + ConstVal.JS_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.API, apiFile)) {
                writer(objectMap, templateFilePath(template.getApi()), apiFile);
            }
        }
        // MpList.vue
        if (null != tableInfo.getListName() && null != pathInfoMap.get(ConstVal.LIST_PATH)) {
            String listFile = String.format((pathInfoMap.get(ConstVal.LIST_PATH)
                + File.separator
                + tableInfo.getListName()
                + ConstVal.VUE_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.LIST, listFile)) {
                writer(objectMap, templateFilePath(template.getList()), listFile);
            }
        }
        // MpForm.vue
        if (null != tableInfo.getFormName() && null != pathInfoMap.get(ConstVal.FORM_PATH)) {
            String formFile = String.format((pathInfoMap.get(ConstVal.FORM_PATH)
                + File.separator
                + tableInfo.getFormName()
                + ConstVal.VUE_SUFFIX), tableInfo.getEntityName());
            if (isCreate(FileType.FORM, formFile)) {
                writer(objectMap, templateFilePath(template.getForm()), formFile);
            }
        }
    }

    private void outputBackendFiles(TableInfo tableInfo, Map<String, Object> objectMap,
        Map<String, String> pathInfoMap, TemplateConfig template) throws Exception {
        // Mp.java
        String entityName = tableInfo.getEntityName();
        if (null != entityName && null != pathInfoMap.get(ConstVal.ENTITY_PATH)) {
            String entityFile = String.format(
                (pathInfoMap.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
            if (isCreate(FileType.ENTITY, entityFile)) {
                writer(objectMap,
                    templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isEnableKotlin())),
                    entityFile);
            }
        }
        // MpDto.java
        String dtoName = tableInfo.getDtoName();
        if (null != dtoName && null != pathInfoMap.get(ConstVal.DTO_PATH)) {
            String dtoFile = String.format(
                (pathInfoMap.get(ConstVal.DTO_PATH) + File.separator + "%s" + suffixJavaOrKt()), dtoName);
            if (isCreate(FileType.DTO, dtoFile)) {
                writer(objectMap, templateFilePath(template.getDto()), dtoFile);
            }
        }
        // MpQuery.java
        String queryName = tableInfo.getQueryName();
        if (null != queryName && null != pathInfoMap.get(ConstVal.QUERY_PATH)) {
            String queryFile = String.format(
                (pathInfoMap.get(ConstVal.QUERY_PATH) + File.separator + "%s" + suffixJavaOrKt()), queryName);
            if (isCreate(FileType.QUERY, queryFile)) {
                writer(objectMap, templateFilePath(template.getQuery()), queryFile);
            }
        }
        // MpMapper.java
        if (null != tableInfo.getMapperName() && null != pathInfoMap.get(ConstVal.MAPPER_PATH)) {
            String mapperFile = String.format(
                (pathInfoMap.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()),
                entityName);
            if (isCreate(FileType.MAPPER, mapperFile)) {
                writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
            }
        }
        // IMpDao.java
        if (null != tableInfo.getDaoName() && null != pathInfoMap.get(ConstVal.DAO_PATH)) {
            String daoFile = String.format(
                (pathInfoMap.get(ConstVal.DAO_PATH) + File.separator + tableInfo.getDaoName() + suffixJavaOrKt()),
                entityName);
            if (isCreate(FileType.DAO, daoFile)) {
                writer(objectMap, templateFilePath(template.getDao()), daoFile);
            }
        }
        // MpDaoImpl.java
        if (null != tableInfo.getDaoImplName() && null != pathInfoMap.get(ConstVal.DAO_IMPL_PATH)) {
            String implFile = String.format((pathInfoMap.get(ConstVal.DAO_IMPL_PATH)
                + File.separator
                + tableInfo.getDaoImplName()
                + suffixJavaOrKt()), entityName);
            if (isCreate(FileType.DAO_IMPL, implFile)) {
                writer(objectMap, templateFilePath(template.getDaoImpl()), implFile);
            }
        }
        // IMpService.java
        if (null != tableInfo.getServiceName() && null != pathInfoMap.get(ConstVal.SERVICE_PATH)) {
            String serviceFile = String.format((pathInfoMap.get(ConstVal.SERVICE_PATH)
                + File.separator
                + tableInfo.getServiceName()
                + suffixJavaOrKt()), entityName);
            if (isCreate(FileType.SERVICE, serviceFile)) {
                writer(objectMap, templateFilePath(template.getService()), serviceFile);
            }
        }
        // MpServiceImpl.java
        if (null != tableInfo.getServiceImplName() && null != pathInfoMap.get(ConstVal.SERVICE_IMPL_PATH)) {
            String implFile = String.format((pathInfoMap.get(ConstVal.SERVICE_IMPL_PATH)
                + File.separator
                + tableInfo.getServiceImplName()
                + suffixJavaOrKt()), entityName);
            if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
            }
        }
        // MpController.java
        if (null != tableInfo.getControllerName() && null != pathInfoMap.get(ConstVal.CONTROLLER_PATH)) {
            String controllerFile = String.format((pathInfoMap.get(ConstVal.CONTROLLER_PATH)
                + File.separator
                + tableInfo.getControllerName()
                + suffixJavaOrKt()), entityName);
            if (isCreate(FileType.CONTROLLER, controllerFile)) {
                writer(objectMap, templateFilePath(template.getController()), controllerFile);
            }
        }
        // MpMapper.xml
        if (null != tableInfo.getXmlName() && null != pathInfoMap.get(ConstVal.XML_PATH)) {
            String xmlFile = String.format((pathInfoMap.get(ConstVal.XML_PATH)
                + File.separator
                + tableInfo.getXmlName()
                + ConstVal.XML_SUFFIX), entityName);
            if (isCreate(FileType.XML, xmlFile)) {
                writer(objectMap, templateFilePath(template.getXml()), xmlFile);
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
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
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
        objectMap.put("enableKotlin", globalConfig.isEnableKotlin());
        objectMap.put("enableSwagger", tableInfo.isEnableSwagger());
        objectMap.put("enableEasyExcel", tableInfo.isEnableEasyExcel());
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
    public abstract String templateFilePath(String filePath);

    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     */
    protected boolean isCreate(FileType fileType, String filePath) {
        ConfigBuilder cb = getConfigBuilder();
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
        return !exist || getConfigBuilder().getGlobalConfig().isEnableOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return getConfigBuilder().getGlobalConfig().isEnableKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 处理输出目录
     */
    public AbstractTemplateEngine mkdirs() {
        getConfigBuilder().getPathInfoMap().forEach((key, value) -> {
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
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (getConfigBuilder().getGlobalConfig().isOpen()
            && StringUtils.isNotBlank(outDir)) {
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
