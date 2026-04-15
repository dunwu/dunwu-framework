package io.github.dunwu.common.dal.generator;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.github.dunwu.common.dal.generator.config.*;
import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import io.github.dunwu.common.dal.generator.config.rules.NamingStrategy;
import io.github.dunwu.common.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

/**
 * 代码生成器工具类
 * <p>
 * 基于 MyBatis-Plus 代码生成器进行深度定制，提供便捷的代码生成入口。
 * 主要功能包括：
 * <ul>
 * <li>生成 Mapper XML 映射文件</li>
 * <li>生成 Entity 实体类</li>
 * <li>生成 Dao 接口及实现类</li>
 * <li>生成 Controller 控制器（继承自 BaseController，自动实现增删改查 REST 接口）</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">MyBatis-Plus 代码生成器</a>
 * @since 2019-04-15
 */
@Slf4j
public class CodeGeneratorUtil {

    /**
     * 私有构造方法，防止实例化
     */
    private CodeGeneratorUtil() {
    }

    /**
     * 从 {@link Properties} 对象初始化配置构造器 {@link ConfigBuilder}
     *
     * @param properties 包含配置信息的 Properties 对象
     * @return 配置构造器实例，如果 properties 为空则返回 null
     */
    public static ConfigBuilder initConfigBuilder(Properties properties) {
        if (MapUtil.isEmpty(properties)) {
            return null;
        }

        // 数据源配置
        DataSourceConfig dataSourceConfig = getDataSourceConfig(properties);

        // 全局配置
        GlobalConfig globalConfig = getGlobalConfig(properties);

        // 包名配置
        PackageConfig packageConfig = getPackageConfig(properties);

        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(properties);

        // 模板配置
        TemplateConfig templateConfig = getTemplateConfig();

        return new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig, templateConfig);
    }

    /**
     * 从指定的配置文件路径初始化配置构造器 {@link ConfigBuilder}
     *
     * @param configFiles 配置文件路径数组
     * @return 配置构造器实例，如果文件数组为空则返回 null
     */
    public static ConfigBuilder initConfigBuilder(String... configFiles) {
        if (ArrayUtil.isEmpty(configFiles)) {
            log.error("加载文件为空");
            return null;
        }
        Properties properties = loadProperties(Arrays.asList(configFiles));
        return initConfigBuilder(properties);
    }

    /**
     * 从 {@link Properties} 中解析全局配置 {@link GlobalConfig}
     *
     * @param properties 配置属性集
     * @return 全局配置实例
     */
    private static GlobalConfig getGlobalConfig(Properties properties) {
        boolean enableSwagger = PropertiesUtil.getBoolean(properties,
            MybatisPlusGenProps.GC_ENABLE_SWAGGER.getKey(), false);
        boolean enableEasyExcel = PropertiesUtil.getBoolean(properties,
            MybatisPlusGenProps.GC_ENABLE_EASY_EXCEL.getKey(), false);

        String authorName = getProperty(properties, MybatisPlusGenProps.GC_AUTHOR_NAME);

        String xmlName = getProperty(properties, MybatisPlusGenProps.GC_XML_NAME);
        String mapperName = getProperty(properties, MybatisPlusGenProps.GC_MAPPER_NAME);
        String daoName = getProperty(properties, MybatisPlusGenProps.GC_DAO_NAME);
        String daoImplName = getProperty(properties, MybatisPlusGenProps.GC_DAO_IMPL_NAME);
        String serviceName = getProperty(properties, MybatisPlusGenProps.GC_SERVICE_NAME);
        String serviceImplName = getProperty(properties, MybatisPlusGenProps.GC_SERVICE_IMPL_NAME);
        String controllerName = getProperty(properties, MybatisPlusGenProps.GC_CONTROLLER_NAME);

        String outputDir = getProperty(properties, MybatisPlusGenProps.OUTPUT_DIR);
        String backendDir = getProperty(properties, MybatisPlusGenProps.BACKEND_DIR);
        String frontendDir = getProperty(properties, MybatisPlusGenProps.FRONTEND_DIR);
        if (StrUtil.isBlank(backendDir)) {
            backendDir = outputDir + File.separator + "backend";
        }
        if (StrUtil.isBlank(frontendDir)) {
            frontendDir = outputDir + File.separator + "frontend";
        }

        GlobalConfig config = new GlobalConfig();
        config.setOpen(false)
              .setEnableOverride(true)
              .setEnableActiveRecord(false)
              .setOutputDir(outputDir)
              .setBackendDir(backendDir)
              .setFrontendDir(frontendDir)
              .setXmlName(xmlName)
              .setMapperName(mapperName)
              .setDaoName(daoName)
              .setDaoImplName(daoImplName)
              .setServiceName(serviceName)
              .setServiceImplName(serviceImplName)
              .setControllerName(controllerName)
              .setEnableSwagger(enableSwagger)
              .setEnableEasyExcel(enableEasyExcel);

        if (StrUtil.isNotBlank(authorName)) {
            config.setAuthor(authorName);
        }
        return config;
    }

    /**
     * 从 {@link Properties} 中解析数据源配置 {@link DataSourceConfig}
     *
     * @param properties 配置属性集
     * @return 数据源配置实例
     */
    private static DataSourceConfig getDataSourceConfig(Properties properties) {
        String url = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_URL);
        String driverName = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_DRIVER);
        String username = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_USERNAME);
        String password = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_PASSWORD);
        return new DataSourceConfig(url, driverName, username, password);
    }

    /**
     * 从 {@link Properties} 中解析包配置 {@link PackageConfig}
     *
     * @param properties 配置属性集
     * @return 包配置实例
     */
    private static PackageConfig getPackageConfig(Properties properties) {
        String moduleName = getProperty(properties, MybatisPlusGenProps.PC_MODULE_NAME);
        String packageName = getProperty(properties, MybatisPlusGenProps.PC_PACKAGE_NAME);
        String mapperPackageName = getProperty(properties, MybatisPlusGenProps.PC_MAPPER_NAME);
        String daoPackageName = getProperty(properties, MybatisPlusGenProps.PC_DAO_NAME);
        String daoImplPackageName = getProperty(properties, MybatisPlusGenProps.PC_DAO_IMPL_NAME);
        String servicePackageName = getProperty(properties, MybatisPlusGenProps.PC_SERVICE_NAME);
        String serviceImplPackageName = getProperty(properties, MybatisPlusGenProps.PC_SERVICE_IMPL_NAME);
        String xmlPackageName = getProperty(properties, MybatisPlusGenProps.PC_XML_NAME);
        PackageConfig config = new PackageConfig();
        if (StrUtil.isNotBlank(moduleName)) {
            config.setModuleName(moduleName);
        }
        if (StrUtil.isNotBlank(packageName)) {
            config.setParent(packageName);
        }
        config.setMapper(mapperPackageName)
              .setDao(daoPackageName)
              .setDaoImpl(daoImplPackageName)
              .setService(servicePackageName)
              .setServiceImpl(serviceImplPackageName)
              .setXml(xmlPackageName);
        return config;
    }

    /**
     * 从 {@link Properties} 中解析策略配置 {@link StrategyConfig}
     *
     * @param properties 配置属性集
     * @return 策略配置实例
     */
    private static StrategyConfig getStrategyConfig(Properties properties) {
        String tableName = getProperty(properties, MybatisPlusGenProps.SC_TABLE_NAME);
        String superEntity = getProperty(properties, MybatisPlusGenProps.SC_SUPER_ENTITY);
        String superDao = getProperty(properties, MybatisPlusGenProps.SC_SUPER_DAO);
        String superDaoImpl = getProperty(properties, MybatisPlusGenProps.SC_SUPER_DAO_IMPL);

        StrategyConfig config = new StrategyConfig();
        config.setEntityLombokModel(true)
              .setRestControllerStyle(true)
              .setControllerMappingHyphenStyle(false)
              .setNaming(NamingStrategy.underline_to_camel)
              .setColumnNaming(NamingStrategy.underline_to_camel)
              .setSuperDaoClass(superDao)
              .setSuperDaoImplClass(superDaoImpl);
        if (StrUtil.isNotBlank(tableName)) {
            tableName = tableName.trim();
            config.setInclude(tableName.split(","));
        } else {
            config.setInclude(scanner("表名"));
        }

        if (StrUtil.isNotBlank(superEntity)) {
            config.setSuperEntityClass(superEntity);
        }
        return config;
    }

    /**
     * 从 {@link Properties} 中解析注入配置 {@link InjectionConfig}
     *
     * @param properties 配置属性集
     * @param pc         包配置信息，用于确定输出路径
     * @return 注入配置实例
     */
    private static InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc) {
        String resourcesDir = getProperty(properties, MybatisPlusGenProps.OUTPUT_DIR) + "/src/main/resources";

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/backend/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder sb = new StringBuilder(resourcesDir);
                sb.append("/").append(pc.getXml()).append("/");

                if (StrUtil.isNotEmpty(pc.getModuleName())) {
                    sb.append(pc.getModuleName()).append("/");
                }
                sb.append(tableInfo.getEntityName());
                sb.append("Mapper");
                sb.append(StringPool.DOT_XML);
                return sb.toString();
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 获取默认的模板配置 {@link TemplateConfig}
     *
     * @return 模板配置实例
     */
    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig();
    }

    /**
     * 从指定的配置文件列表中加载属性
     *
     * @param configFiles 配置文件路径集合
     * @return 加载后的 Properties 对象
     */
    public static Properties loadProperties(Collection<String> configFiles) {
        Properties properties = new Properties();
        if (CollectionUtil.isNotEmpty(configFiles)) {
            for (String file : configFiles) {
                try {
                    properties.putAll(PropertiesUtil.loadFromFile(file));
                } catch (Exception e) {
                    log.error(StrUtil.format("读取属性文件 {} 失败", file), e);
                }
            }
        }
        return properties;
    }

    /**
     * 当属性文件中未检索到配置项时，通过控制台交互方式读取用户输入
     *
     * @param tip 提示信息
     * @return 用户输入的字符串
     * @throws MybatisPlusException 如果用户未输入有效内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(StrUtil.format("请输入 {} ：", tip));
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException(StrUtil.format("请输入正确的 {} ！", tip));
    }

    /**
     * 从 Properties 中获取指定键的值，如果不存在则返回默认值
     *
     * @param properties 配置属性集
     * @param key        配置键枚举
     * @return 属性值
     */
    private static String getProperty(Properties properties, MybatisPlusGenProps key) {
        return PropertiesUtil.getString(properties, key.getKey(), key.getValue());
    }

}
