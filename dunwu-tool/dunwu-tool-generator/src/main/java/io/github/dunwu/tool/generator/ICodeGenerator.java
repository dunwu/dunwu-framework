package io.github.dunwu.tool.generator;

import io.github.dunwu.tool.generator.config.*;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.engine.CodeGenerateContentDto;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * 代码生成器模板方法类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-19
 */
public interface ICodeGenerator {

    /**
     * 模板方法
     */
    void generate();

    void generate(Collection<TableInfo> tables);

    List<CodeGenerateContentDto> getPreviewList();

    /**
     * 全局配置
     *
     * @param properties Properties
     * @return {@link GlobalConfig}
     */
    GlobalConfig getGlobalConfig(Properties properties);

    /**
     * 数据源配置
     *
     * @param properties Properties
     * @return {@link DataSourceConfig}
     */
    DataSourceConfig getDataSourceConfig(Properties properties);

    /**
     * 包名配置
     *
     * @param properties Properties
     * @return {@link PackageConfig}
     */
    PackageConfig getPackageConfig(Properties properties);

    /**
     * 策略配置
     *
     * @param properties Properties
     * @param pc         PackageConfig
     * @return {@link StrategyConfig}
     */
    StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc);

    /**
     * 自定义配置
     *
     * @param properties Properties
     * @param pc         PackageConfig
     * @return {@link InjectionConfig}
     */
    InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc);

    /**
     * 模板配置
     *
     * @return {@link TemplateConfig}
     */
    TemplateConfig getTemplateConfig();

}
