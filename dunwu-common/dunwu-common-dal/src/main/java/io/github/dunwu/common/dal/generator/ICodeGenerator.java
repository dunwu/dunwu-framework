package io.github.dunwu.common.dal.generator;

import io.github.dunwu.common.dal.generator.config.*;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import io.github.dunwu.common.dal.generator.engine.CodeGenerateContentDto;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * 代码生成器接口
 * <p>
 * 定义了代码生成器的核心模板方法和配置获取接口。
 * 实现类需提供具体的配置解析逻辑以及代码生成流程的实现。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-19
 */
public interface ICodeGenerator {

    /**
     * 执行代码生成
     * <p>
     * 使用默认或预设的配置生成所有代码。
     */
    void generate();

    /**
     * 根据指定的表信息列表执行代码生成
     *
     * @param tables 需要生成代码的表信息集合
     */
    void generate(Collection<TableInfo> tables);

    /**
     * 获取代码预览列表
     *
     * @return 包含所有待生成文件内容的列表
     */
    List<CodeGenerateContentDto> getPreviewList();

    /**
     * 获取全局配置
     *
     * @param properties 属性配置集
     * @return 全局配置 {@link GlobalConfig} 实例
     */
    GlobalConfig getGlobalConfig(Properties properties);

    /**
     * 获取数据源配置
     *
     * @param properties 属性配置集
     * @return 数据源配置 {@link DataSourceConfig} 实例
     */
    DataSourceConfig getDataSourceConfig(Properties properties);

    /**
     * 获取包名配置
     *
     * @param properties 属性配置集
     * @return 包名配置 {@link PackageConfig} 实例
     */
    PackageConfig getPackageConfig(Properties properties);

    /**
     * 获取策略配置
     *
     * @param properties 属性配置集
     * @param pc         包名配置实例
     * @return 策略配置 {@link StrategyConfig} 实例
     */
    StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc);

    /**
     * 获取自定义注入配置
     *
     * @param properties 属性配置集
     * @param pc         包名配置实例
     * @return 自定义注入配置 {@link InjectionConfig} 实例
     */
    InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc);

    /**
     * 获取模板配置
     *
     * @return 模板配置 {@link TemplateConfig} 实例
     */
    TemplateConfig getTemplateConfig();

}
