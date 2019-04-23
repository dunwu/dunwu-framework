package io.github.dunwu.data;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.dunwu.config.Const;
import io.github.dunwu.util.base.PropertiesUtil;
import io.github.dunwu.web.controller.CrudController;

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
 * @author Zhang Peng
 * @see CrudController
 * @see <a href="https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">mybatis plus 代码生成器</a>
 * @since 2019-04-15
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
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
    public static void main(String... args) {

        Properties properties = PropertiesUtil.loadFromFile("classpath://application.properties");

        // 全局配置
        String javaDir = PropertiesUtil.getString(properties, Const.ParamKeyEnum.MYBATIS_GENERATOR_JAVA_DIR.key(),
            Const.ParamKeyEnum.MYBATIS_GENERATOR_JAVA_DIR.value());
        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false).setFileOverride(true).setActiveRecord(false).setOutputDir(javaDir).setAuthor("Zhang Peng")
            .setMapperName("%sDao").setXmlName("%sDao").setServiceName("%sService").setSwagger2(true);

        // 数据源配置
        String url = PropertiesUtil.getString(properties, Const.ParamKeyEnum.SPRING_DATASOURCE_URL.key(),
            Const.ParamKeyEnum.SPRING_DATASOURCE_URL.value());
        String driverName = PropertiesUtil.getString(properties, Const.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.key(),
            Const.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.value());
        String username = PropertiesUtil.getString(properties, Const.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.key(),
            Const.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.value());
        String password = PropertiesUtil.getString(properties, Const.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.key(),
            Const.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.value());
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url).setDriverName(driverName).setUsername(username).setPassword(password);

        // 包名配置
        String packageName = PropertiesUtil
            .getString(properties, Const.ParamKeyEnum.MYBATIS_GENERATOR_PACKAGE_NAME.key(),
                Const.ParamKeyEnum.MYBATIS_GENERATOR_PACKAGE_NAME.value());
        String moduleName = PropertiesUtil.getString(properties, Const.ParamKeyEnum.MYBATIS_GENERATOR_MODULE_NAME.key(),
            Const.ParamKeyEnum.MYBATIS_GENERATOR_MODULE_NAME.value());
        PackageConfig pc = new PackageConfig();
        if (moduleName != null) {
            pc.setModuleName(moduleName);
        }
        pc.setMapper("dao").setXml("dao").setParent(packageName);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setEntityLombokModel(true).setControllerMappingHyphenStyle(true).setRestControllerStyle(true)
            .setNaming(NamingStrategy.underline_to_camel).setColumnNaming(NamingStrategy.underline_to_camel)
            .setSuperEntityClass("io.github.dunwu.data.entity.BaseEntity")
            .setSuperControllerClass("io.github.dunwu.web.controller.CrudController").setSuperEntityColumns("id")
            .setTablePrefix("t_").setTablePrefix(pc.getModuleName() + "_");
        String tableName = PropertiesUtil.getString(properties, Const.ParamKeyEnum.MYBATIS_GENERATOR_TABLE_NAME.key(),
            Const.ParamKeyEnum.MYBATIS_GENERATOR_TABLE_NAME.value());
        if (StringUtils.isNotEmpty(tableName)) {
            tableName = tableName.replaceAll(" ", "");
            sc.setInclude(tableName.split(","));
        } else {
            sc.setInclude(scanner("表名"));
        }

        // 自定义配置
        String resourcesDir = PropertiesUtil
            .getString(properties, Const.ParamKeyEnum.MYBATIS_GENERATOR_RESOURCE_DIR.key(),
                Const.ParamKeyEnum.MYBATIS_GENERATOR_RESOURCE_DIR.value());
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
}
