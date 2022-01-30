package io.github.dunwu.tool.generator;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.github.dunwu.tool.generator.config.*;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.config.rules.NamingStrategy;
import io.github.dunwu.tool.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

/**
 * 默认代码生成器
 * <p>
 * 基于 mybatis plus 代码生成器，做了定制修改。
 * <ul>
 * <li>生成 Mapper xml</li>
 * <li>生成 Entity类</li>
 * <li>生成 Dao类</li>
 * <li>生成 Controller类（Controller做了深层定制，自动生成继承自BaseController的增删改查REST接口）</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">mybatis plus 代码生成器</a>
 * @since 2019-04-15
 */
@Slf4j
public class CodeGeneratorUtil {

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

        // 自定义配置
        // InjectionConfig cfg = getInjectionConfig(properties, pc);

        // 模板配置
        TemplateConfig templateConfig = getTemplateConfig();

        return new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig, templateConfig);
    }

    public static ConfigBuilder initConfigBuilder(String... configFiles) {
        if (ArrayUtil.isEmpty(configFiles)) {
            log.error("加载文件为空");
            return null;
        }
        Properties properties = loadProperties(Arrays.asList(configFiles));
        return initConfigBuilder(properties);
    }

    /**
     * 从 {@link Properties} 中尝试读取代码生成器的全局配置 {@link GlobalConfig}
     *
     * @param properties Properties
     * @return /
     */
    private static GlobalConfig getGlobalConfig(Properties properties) {
        // 全局配置
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
     * 从 {@link Properties} 中数据源配置 {@link DataSourceConfig}
     *
     * @param properties Properties
     * @return /
     */
    private static DataSourceConfig getDataSourceConfig(Properties properties) {
        String url = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_URL);
        String driverName = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_DRIVER);
        String username = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_USERNAME);
        String password = getProperty(properties, MybatisPlusGenProps.SPRING_DATASOURCE_PASSWORD);
        return new DataSourceConfig(url, driverName, username, password);
    }

    /**
     * 从 {@link Properties} 中读取代码生成器的 package 配置 {@link PackageConfig}
     *
     * @param properties Properties
     * @return /
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
     * 从 {@link Properties} 中读取代码生成器的策略配置 {@link StrategyConfig}
     *
     * @param properties Properties
     * @return /
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
     * 从 {@link Properties} 中读取代码生成器的注入配置 {@link InjectionConfig}
     *
     * @param properties Properties
     * @return /
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
     * 获取代码生成器的策略配置 {@link TemplateConfig}
     *
     * @return /
     */
    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig();
    }

    /**
     * 从指定的配置文件中读取 属性
     *
     * @param configFiles 配置文件路径列表
     * @return /
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
     * 属性文件中未检索到的配置项，通过控制台输入方式读取
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

    private static String getProperty(Properties properties, MybatisPlusGenProps key) {
        return PropertiesUtil.getString(properties, key.getKey(), key.getValue());
    }

}
