package io.github.dunwu.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Properties;

/**
 * 代码生成器模板方法类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-19
 */
public abstract class BaseCodeGenerator {

    /**
     * 模板方法
     */
    public void generate(String... files) {
        Properties properties = loadProperties(files);
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
        TemplateConfig tc = getTemplateConfig();

        // 将配置项注入 AutoGenerator
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(gc).setDataSource(dsc).setPackageInfo(pc)
            .setStrategy(sc).setCfg(cfg).setTemplate(tc)
            .setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    /**
     * 从配置文件中加载属性
     *
     * @param files 待加载的 properties 文件路径
     * @return {@link Properties}
     */
    public abstract Properties loadProperties(String... files);

    /**
     * 全局配置
     *
     * @param properties Properties
     * @return {@link GlobalConfig}
     */
    public abstract GlobalConfig getGlobalConfig(Properties properties);

    /**
     * 数据源配置
     *
     * @param properties Properties
     * @return {@link DataSourceConfig}
     */
    public abstract DataSourceConfig getDataSourceConfig(Properties properties);

    /**
     * 包名配置
     *
     * @param properties Properties
     * @return {@link PackageConfig}
     */
    public abstract PackageConfig getPackageConfig(Properties properties);

    /**
     * 策略配置
     *
     * @param properties Properties
     * @param pc         PackageConfig
     * @return {@link StrategyConfig}
     */
    public abstract StrategyConfig getStrategyConfig(Properties properties,
        PackageConfig pc);

    /**
     * 自定义配置
     *
     * @param properties Properties
     * @param pc         PackageConfig
     * @return {@link InjectionConfig}
     */
    public abstract InjectionConfig getInjectionConfig(Properties properties,
        PackageConfig pc);

    /**
     * 模板配置
     *
     * @return {@link TemplateConfig}
     */
    public abstract TemplateConfig getTemplateConfig();

}
