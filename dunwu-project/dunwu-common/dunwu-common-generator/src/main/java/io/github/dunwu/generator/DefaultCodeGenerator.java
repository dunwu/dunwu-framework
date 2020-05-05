package io.github.dunwu.generator;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.generator.config.rules.NamingStrategy;
import io.github.dunwu.generator.engine.FreemarkerTemplateEngine;
import io.github.dunwu.generator.engine.TemplateContent;
import io.github.dunwu.tool.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
public class DefaultCodeGenerator implements ICodeGenerator {

    private final Logger log = LoggerFactory.getLogger(DefaultCodeGenerator.class);
    private Properties properties;

    public DefaultCodeGenerator() {
        this("classpath://conf/mybatis.properties", "classpath://application.properties");
    }

    public DefaultCodeGenerator(String... configFiles) {
        loadProperties(configFiles);
    }

    public DefaultCodeGenerator(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void generate() {
        AutoGenerator generator = newAutoGenerator();
        if (generator != null) {
            generator.execute();
        }
    }

    @Override
    public List<TemplateContent> getPreviewList() {
        AutoGenerator generator = newAutoGenerator();
        if (generator != null) {
            return generator.preview();
        }
        return new ArrayList<>();
    }

    @Override
    public void loadProperties(String... files) {
        this.properties = new Properties();
        if (ArrayUtil.isEmpty(files)) {
            return;
        }

        for (String file : files) {
            try {
                this.properties.putAll(PropertiesUtil.loadFromFile(file));
            } catch (Exception e) {
                log.debug("未检索到 {} 文件", file);
            }
        }
    }

    @Override
    public GlobalConfig getGlobalConfig(Properties properties) {
        // 全局配置
        String javaDir = getPropertie(properties, MybatisPlusGeneratorProps.GC_JAVA_DIR);
        String authorName = getPropertie(properties, MybatisPlusGeneratorProps.GC_AUTHOR_NAME);
        boolean enableSwagger =
            Boolean.parseBoolean(getPropertie(properties, MybatisPlusGeneratorProps.GC_ENABLE_SWAGGER));
        String xmlName = getPropertie(properties, MybatisPlusGeneratorProps.GC_XML_NAME);
        String mapperName = getPropertie(properties, MybatisPlusGeneratorProps.GC_MAPPER_NAME);
        String daoName = getPropertie(properties, MybatisPlusGeneratorProps.GC_DAO_NAME);
        String daoImplName = getPropertie(properties, MybatisPlusGeneratorProps.GC_DAO_IMPL_NAME);
        String serviceName = getPropertie(properties, MybatisPlusGeneratorProps.GC_SERVICE_NAME);
        String serviceImplName = getPropertie(properties, MybatisPlusGeneratorProps.GC_SERVICE_IMPL_NAME);
        String controllerName = getPropertie(properties, MybatisPlusGeneratorProps.GC_CONTROLLER_NAME);

        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false)
            .setFileOverride(true)
            .setActiveRecord(false)
            .setOutputDir(javaDir)
            .setXmlName(xmlName)
            .setMapperName(mapperName)
            .setDaoName(daoName)
            .setDaoImplName(daoImplName)
            .setServiceName(serviceName)
            .setServiceImplName(serviceImplName)
            .setControllerName(controllerName)
            .setSwagger2(enableSwagger);

        if (StrUtil.isNotBlank(authorName)) {
            gc.setAuthor(authorName);
        }
        return gc;
    }

    @Override
    public DataSourceConfig getDataSourceConfig(Properties properties) {
        String url = getPropertie(properties, MybatisPlusGeneratorProps.SPRING_DATASOURCE_URL);
        String driverName = getPropertie(properties, MybatisPlusGeneratorProps.SPRING_DATASOURCE_DRIVER);
        String username = getPropertie(properties, MybatisPlusGeneratorProps.SPRING_DATASOURCE_USERNAME);
        String password = getPropertie(properties, MybatisPlusGeneratorProps.SPRING_DATASOURCE_PASSWORD);
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url).setDriverName(driverName)
            .setUsername(username).setPassword(password);
        return dsc;
    }

    @Override
    public PackageConfig getPackageConfig(Properties properties) {
        String moduleName = getPropertie(properties, MybatisPlusGeneratorProps.PC_MODULE_NAME);
        String packageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_PACKAGE_NAME);
        String mapperPackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_MAPPER_NAME);
        String daoPackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_DAO_NAME);
        String daoImplPackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_DAO_IMPL_NAME);
        String servicePackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_SERVICE_NAME);
        String serviceImplPackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_SERVICE_IMPL_NAME);
        String xmlPackageName = getPropertie(properties, MybatisPlusGeneratorProps.PC_XML_NAME);
        PackageConfig pc = new PackageConfig();
        if (StrUtil.isNotBlank(moduleName)) {
            pc.setModuleName(moduleName);
        }
        if (StrUtil.isNotBlank(packageName)) {
            pc.setParent(packageName);
        }
        pc.setMapper(mapperPackageName)
            .setDao(daoPackageName)
            .setDaoImpl(daoImplPackageName)
            .setService(servicePackageName)
            .setServiceImpl(serviceImplPackageName)
            .setXml(xmlPackageName);
        return pc;
    }

    @Override
    public StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc) {
        String tableName = getPropertie(properties, MybatisPlusGeneratorProps.SC_TABLE_NAME);
        String superEntity = getPropertie(properties, MybatisPlusGeneratorProps.SC_SUPER_ENTITY);
        String superDao = getPropertie(properties, MybatisPlusGeneratorProps.SC_SUPER_DAO);
        String superDaoImpl = getPropertie(properties, MybatisPlusGeneratorProps.SC_SUPER_DAO_IMPL);

        StrategyConfig sc = new StrategyConfig();
        sc.setEntityLombokModel(true)
            .setRestControllerStyle(true)
            .setControllerMappingHyphenStyle(true)
            .setNaming(NamingStrategy.underline_to_camel)
            .setColumnNaming(NamingStrategy.underline_to_camel)
            .setSuperDaoClass(superDao)
            .setSuperDaoImplClass(superDaoImpl);
        if (StrUtil.isNotBlank(tableName)) {
            tableName = tableName.trim();
            sc.setInclude(tableName.split(","));
        } else {
            sc.setInclude(scanner("表名"));
        }

        if (StrUtil.isNotBlank(superEntity)) {
            sc.setSuperEntityClass(superEntity);
        }
        return sc;
    }

    @Override
    public InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc) {
        String resourcesDir = getPropertie(properties, MybatisPlusGeneratorProps.GC_RESOURCE_DIR);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
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

    @Override
    public TemplateConfig getTemplateConfig() {
        return new TemplateConfig().setXml(null);
    }

    private AutoGenerator newAutoGenerator() {
        if (MapUtil.isEmpty(this.properties)) {
            return null;
        }

        // 全局配置
        GlobalConfig gc = getGlobalConfig(properties);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig(properties);

        // 包名配置
        PackageConfig pc = getPackageConfig(properties);

        // 策略配置
        StrategyConfig sc = getStrategyConfig(properties, pc);

        // 自定义配置
        InjectionConfig cfg = getInjectionConfig(properties, pc);

        // 自定义 controller 模板
        TemplateConfig tc = getTemplateConfig();

        // 将配置项注入 AutoGenerator
        AutoGenerator generator = new AutoGenerator();
        generator.setTemplateEngine(new FreemarkerTemplateEngine())
            .setGlobalConfig(gc)
            .setDataSource(dsc)
            .setPackageInfo(pc)
            .setStrategy(sc)
            .setCfg(cfg)
            .setTemplate(tc);
        return generator;
    }

    /**
     * 属性文件中未检索到的配置项，通过控制台输入方式读取
     */
    private String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("请输入" + tip + "：");
        System.out.println(sb.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public String getPropertie(Properties properties, MybatisPlusGeneratorProps key) {
        return PropertiesUtil.getString(properties, key.getKey(), key.getValue());
    }

}
