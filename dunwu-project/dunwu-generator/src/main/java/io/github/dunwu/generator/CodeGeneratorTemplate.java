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
public abstract class CodeGeneratorTemplate {

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
		AutoGenerator mpg = new AutoGenerator();
		mpg.setTemplate(tc);
		mpg.setGlobalConfig(gc).setDataSource(dsc).setPackageInfo(pc).setStrategy(sc)
				.setCfg(cfg).setTemplate(tc)
				.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

	/**
	 * 从配置文件中加载属性
	 * @return
	 */
	public abstract Properties loadProperties(String... files);

	/**
	 * 全局配置
	 * @param properties Properties
	 * @return GlobalConfig
	 */
	public abstract GlobalConfig getGlobalConfig(Properties properties);

	/**
	 * 数据源配置
	 * @param properties Properties
	 * @return DataSourceConfig
	 */
	public abstract DataSourceConfig getDataSourceConfig(Properties properties);

	/**
	 * 包名配置
	 * @param properties Properties
	 * @return PackageConfig
	 */
	public abstract PackageConfig getPackageConfig(Properties properties);

	/**
	 * 策略配置
	 * @param properties Properties
	 * @param pc PackageConfig
	 * @return StrategyConfig
	 */
	public abstract StrategyConfig getStrategyConfig(Properties properties,
			PackageConfig pc);

	/**
	 * 自定义配置
	 * @param properties Properties
	 * @param pc PackageConfig
	 * @return InjectionConfig
	 */
	public abstract InjectionConfig getInjectionConfig(Properties properties,
			PackageConfig pc);

	/**
	 * 模板配置
	 * @return
	 */
	public abstract TemplateConfig getTemplateConfig();

}
