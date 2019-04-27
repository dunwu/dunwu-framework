package io.github.dunwu.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.dunwu.constant.DatabaseConst;
import io.github.dunwu.util.base.PropertiesUtil;
import io.github.dunwu.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * 代码生成器
 * <p>
 * 基于 mybatis plus 代码生成器，做了定制修改。
 * <ul>
 * <li>生成 Mapper xml</li>
 * <li>生成 Entity类</li>
 * <li>生成 DAO类(mapper)</li>
 * <li>生成 Service类</li>
 * <li>生成 Controller类（Controller做了深层定制，自动生成继承自BaseController的增删改查REST接口）</li>
 * </ul>
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see CrudController
 * @see <a href="https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">mybatis plus 代码生成器</a>
 * @since 2019-04-15
 */
public class CodeGenerator {

    private static final Logger log = LoggerFactory.getLogger(CodeGenerator.class);
    private static final String FIRST_CONF = "classpath://conf/mybatis.properties";
    private static final String SECOND_CONF = "classpath://application.properties";

    /**
     * <p>
     * 属性文件中未检索到的配置项，通过控制台输入方式读取
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void generate() {
        Properties properties = loadProperties();
        if (properties == null) {
            return;
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
        TemplateConfig templateConfig =
            new TemplateConfig().setEntity("templates/entity.java.ftl").setController("templates/controller.java.ftl");

        // 将配置项注入 AutoGenerator
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplate(templateConfig);
        mpg.setGlobalConfig(gc).setDataSource(dsc).setPackageInfo(pc).setStrategy(sc).setCfg(cfg)
            .setTemplate(new TemplateConfig().setXml(null)).setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static Properties loadProperties() {
        // 读取第一优先级属性文件
        Properties properties = null;
        try {
            properties = PropertiesUtil.loadFromFile(FIRST_CONF);
        } catch (Exception e) {
            log.debug("未检索到 {} 文件", FIRST_CONF);
        }

        // 读取第二优先级属性文件
        if (properties == null) {
            try {
                properties = PropertiesUtil.loadFromFile(SECOND_CONF);
            } catch (Exception e) {
                log.error("未检索到 {} 文件", SECOND_CONF);
            }
        }
        return properties;
    }

    /**
     * 全局配置
     * @param properties Properties
     * @return GlobalConfig
     */
    private static GlobalConfig getGlobalConfig(Properties properties) {
        // 全局配置
        String javaDir = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_JAVA_DIR.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_JAVA_DIR.value());
        String authorName = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_AUTHOR_NAME.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_AUTHOR_NAME.value());
        Boolean enableSwagger = PropertiesUtil
            .getBoolean(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_ENABLE_SWAGGER.key(),
                Boolean.valueOf(DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_ENABLE_SWAGGER.value()));
        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false).setFileOverride(true).setActiveRecord(false).setOutputDir(javaDir).setAuthor(authorName)
            .setMapperName("%sDao").setXmlName("%sDao").setServiceName("%sService").setSwagger2(enableSwagger);
        return gc;
    }

    /**
     * 数据源配置
     * @param properties Properties
     * @return DataSourceConfig
     */
    private static DataSourceConfig getDataSourceConfig(Properties properties) {
        String url = PropertiesUtil.getString(properties, DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_URL.key(),
            DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_URL.value());
        String driverName = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.key(),
                DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.value());
        String username = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.key(),
                DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.value());
        String password = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.key(),
                DatabaseConst.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.value());
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url).setDriverName(driverName).setUsername(username).setPassword(password);
        return dsc;
    }

    /**
     * 包名配置
     * @param properties Properties
     * @return PackageConfig
     */
    private static PackageConfig getPackageConfig(Properties properties) {
        String packageName = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_PACKAGE_NAME.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_PACKAGE_NAME.value());
        String moduleName = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_MODULE_NAME.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_MODULE_NAME.value());
        PackageConfig pc = new PackageConfig();
        if (moduleName != null) {
            pc.setModuleName(moduleName);
        }
        pc.setMapper("dao").setXml("dao").setParent(packageName);
        return pc;
    }

    /**
     * 策略配置
     * @param properties Properties
     * @param pc PackageConfig
     * @return StrategyConfig
     */
    private static StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc) {
        String tableName = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_TABLE_NAME.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_TABLE_NAME.value());
        String superEntity = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_SUPER_ENTITY.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_SUPER_ENTITY.value());
        String superController = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_SUPER_CONTROLLER.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_SUPER_CONTROLLER.value());

        StrategyConfig sc = new StrategyConfig();
        sc.setEntityLombokModel(true).setControllerMappingHyphenStyle(true).setRestControllerStyle(true)
            .setNaming(NamingStrategy.underline_to_camel).setColumnNaming(NamingStrategy.underline_to_camel)
            .setSuperEntityClass(superEntity).setSuperControllerClass(superController).setSuperEntityColumns("id")
            .setTablePrefix("t_").setTablePrefix(pc.getModuleName() + "_");
        if (StringUtils.isNotEmpty(tableName)) {
            tableName = tableName.replaceAll(" ", "");
            sc.setInclude(tableName.split(","));
        } else {
            sc.setInclude(scanner("表名"));
        }
        return sc;
    }

    /**
     * 自定义配置
     * @param properties Properties
     * @param pc PackageConfig
     * @return InjectionConfig
     */
    private static InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc) {
        String resourcesDir = PropertiesUtil
            .getString(properties, DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_RESOURCE_DIR.key(),
                DatabaseConst.ParamKeyEnum.MYBATIS_GENERATOR_RESOURCE_DIR.value());
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
                sb.append("/mapper/");
                if (StringUtils.isNotEmpty(pc.getModuleName())) {
                    sb.append(pc.getModuleName()).append("/");
                }
                sb.append(tableInfo.getEntityName());
                sb.append("Dao");
                sb.append(StringPool.DOT_XML);
                return sb.toString();
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }
}
