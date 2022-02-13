package io.github.dunwu.tool.generator;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.generator.config.*;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.config.po.TableField;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.io.AnsiColorUtil;

import java.util.Collection;

/**
 * 代码生成器
 * <p>
 * （1）如果使用默认代码生成器，可以在 conf/mybatis.properties 中配置 ParamKeyEnum 的 key 来控制代码生成
 * <p>
 * （2）如果需要定制，可以覆写 DefaultCodeGenerator 中的方法来订制更细粒度的配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see CodeGeneratorUtil
 * @since 2019-07-19
 */
public class MyCodeGenerator {

    public static void main(String[] args) {
        generateByConfig();
        // generateByProperties();
    }

    public static void generateByConfig() {
        String url =
            "jdbc:mysql://localhost:3306/dunwu?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig(url, "com.mysql.cj.jdbc.Driver", "root", "root");
        PackageConfig packageConfig = new PackageConfig("io.github.dunwu.module", "generator");
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>").setOutputDir("E:\\Temp\\codes").setOpen(true).setEnableSwagger(true);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("code_global_config");
        TemplateConfig templateConfig = new TemplateConfig();

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);

        Collection<TableInfo> tableInfoList = builder.queryTableInfoList();
        for (TableInfo table : tableInfoList) {
            for (TableField field : table.getFields()) {
                if (field.getFieldName().equals("rating")) {
                    field.setQueryType("Between");
                    field.setFormType("Date");
                } else {
                    field.setQueryType("Equals");
                    field.setFormType("Input");
                }
            }
        }

        builder.setTableInfoList(tableInfoList);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generateAll();
        AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(builder.getTableInfoList()));
    }

    public static void generateByProperties() {
        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder("classpath://conf/mybatis.properties");
        // builder.initTableInfoList();
        if (builder != null) {
            Collection<TableInfo> tableInfoList = builder.queryTableInfoList();
            for (TableInfo table : tableInfoList) {
                for (TableField field : table.getFields()) {
                    if (field.getFieldName().equals("rating")) {
                        field.setQueryType("Between");
                        field.setFormType("Date");
                    } else {
                        field.setQueryType("Equals");
                        field.setFormType("Input");
                    }
                }
            }
            builder.setTableInfoList(tableInfoList);
            CodeGenerator codeGenerator = new CodeGenerator(builder);
            codeGenerator.generateAll();
            AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(builder.getTableInfoList()));
        }
    }

}
