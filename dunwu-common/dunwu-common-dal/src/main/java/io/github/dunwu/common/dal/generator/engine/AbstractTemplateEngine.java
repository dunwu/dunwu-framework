package io.github.dunwu.common.dal.generator.engine;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import freemarker.template.TemplateException;
import io.github.dunwu.common.dal.generator.InjectionConfig;
import io.github.dunwu.common.dal.generator.config.ConstVal;
import io.github.dunwu.common.dal.generator.config.FileOutConfig;
import io.github.dunwu.common.dal.generator.config.GlobalConfig;
import io.github.dunwu.common.dal.generator.config.TemplateConfig;
import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import io.github.dunwu.common.dal.generator.config.rules.FileType;
import io.github.dunwu.common.io.ColorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模板引擎抽象类
 * <p>
 * 定义了代码生成器的核心流程，包括模板渲染、文件输出、目录创建等。
 * 子类需实现具体的模板解析逻辑（如 {@link #getMergeContent(Map, String)} 和 {@link #outputFile(Map, String, String)}）。
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    /**
     * 日志记录器
     */
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);

    /**
     * 代码生成配置信息构建器
     */
    private ConfigBuilder configBuilder;

    /**
     * 初始化模板引擎
     *
     * @param configBuilder 配置信息构建器
     * @return 当前模板引擎实例
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 预览所有待生成文件的内容
     *
     * @return 包含文件类型和生成内容的列表
     * @throws IOException       如果读取模板失败
     * @throws TemplateException 如果模板渲染失败
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
     * 生成全部前后端代码
     *
     * @return 当前模板引擎实例
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

    /**
     * 生成指定类型的代码文件
     *
     * @param types 需要生成的文件类型集合
     * @return 当前模板引擎实例
     */
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

                ColorUtil.BOLD_BLUE.println(JSONUtil.toJsonStr(objectMap));

                for (FileType type : types) {
                    generateSpecifiedTypeFile(tableInfo, objectMap, pathInfoMap, template, type);
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 根据文件类型分发到具体的生成方法
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @param type        文件类型
     * @throws Exception 如果生成过程中发生错误
     */
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

    /**
     * 生成 API 接口文件（前端）
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成列表页面文件（前端）
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成表单页面文件（前端）
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成实体类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 DTO 类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成查询对象类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 Mapper 接口文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 DAO 接口文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 DAO 实现类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 Service 接口文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 Service 实现类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 Controller 类文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 生成 MyBatis XML 映射文件
     *
     * @param tableInfo   表信息
     * @param objectMap   渲染数据
     * @param pathInfoMap 路径信息
     * @param template    模板配置
     * @throws Exception 如果生成失败
     */
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

    /**
     * 获取合并后的模板内容（用于预览）
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件路径
     * @return 渲染后的模板内容字符串
     * @throws IOException       如果读取失败
     * @throws TemplateException 如果渲染失败
     */
    public abstract String getMergeContent(Map<String, Object> objectMap, String templatePath)
        throws IOException, TemplateException;

    /**
     * 将模板转化成为文件并输出
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件路径
     * @param outputFile   输出文件完整路径
     * @throws Exception 如果输出失败
     */
    public abstract void outputFile(Map<String, Object> objectMap, String templatePath, String outputFile)
        throws Exception;

    /**
     * 构建用于模板渲染的对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return 包含所有模板变量的 Map
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
     * 用于渲染对象 MAP 信息 {@link #getObjectMap(TableInfo)} 时的 superClassPackage 非空校验
     *
     * @param classPacket 类全限定名
     * @return 如果为空则返回 null，否则返回原值
     */
    private String verifyClassPacket(String classPacket) {
        return StringUtils.isBlank(classPacket) ? null : classPacket;
    }

    /**
     * 从类全限定名中提取类名
     *
     * @param classPath 类全限定名
     * @return 类名
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isBlank(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }

    /**
     * 获取模板文件的真实路径
     *
     * @param filePath 模板文件相对路径或标识
     * @return 模板文件绝对路径
     */
    public abstract String getTemplatePath(String filePath);

    /**
     * 检测文件是否需要创建（是否存在或允许覆盖）
     *
     * @param fileType 文件类型
     * @param filePath 文件路径
     * @return 如果需要创建返回 true，否则返回 false
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
     * 获取生成的 Java/Kotlin 文件后缀
     *
     * @return 文件后缀，默认为 ".java"
     */
    protected String suffixJavaOrKt() {
        return ConstVal.JAVA_SUFFIX;
    }

    /**
     * 设置配置信息构建器
     *
     * @param configBuilder 配置信息构建器
     * @return 当前模板引擎实例
     */
    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 创建所有必要的输出目录
     *
     * @return 当前模板引擎实例
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
     * 打开代码输出目录（支持 Windows 和 macOS）
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
