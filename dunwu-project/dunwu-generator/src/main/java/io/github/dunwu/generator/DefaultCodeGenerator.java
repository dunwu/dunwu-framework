package io.github.dunwu.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import io.github.dunwu.util.parser.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
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
 * <li>生成 DAO类(mapper)</li>
 * <li>生成 Service类</li>
 * <li>生成 Controller类（Controller做了深层定制，自动生成继承自BaseController的增删改查REST接口）</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href=
 * "https://mybatis.plus/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B">mybatis
 * plus 代码生成器</a>
 * @since 2019-04-15
 */
public class DefaultCodeGenerator extends CodeGeneratorTemplate {

	public final Logger log = LoggerFactory.getLogger(DefaultCodeGenerator.class);

	public final String[] CONF_FILES = new String[] {
			"classpath://conf/mybatis.properties", "classpath://application.properties" };

	/**
	 * 从配置文件中加载属性
	 * @return
	 */
	@Override
	public Properties loadProperties(String... files) {
		if (files == null || files.length == 0) {
			files = CONF_FILES;
		}

		Properties properties = null;
		for (String file : files) {
			try {
				properties = PropertiesUtil.loadFromFile(file);
			}
			catch (Exception e) {
				log.debug("未检索到 {} 文件", file);
			}
			if (properties != null) {
				break;
			}
		}
		return properties;
	}

	/**
	 * 全局配置
	 * @param properties Properties
	 * @return GlobalConfig
	 */
	@Override
	public GlobalConfig getGlobalConfig(Properties properties) {
		// 全局配置
		String javaDir = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_JAVA_DIR.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_JAVA_DIR
						.value());
		String authorName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_AUTHOR_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_AUTHOR_NAME
						.value());
		Boolean enableSwagger = PropertiesUtil.getBoolean(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_ENABLE_SWAGGER
						.key(),
				Boolean.valueOf(
						CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_ENABLE_SWAGGER
								.value()));
		String mapperName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_MAPPER_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_MAPPER_NAME
						.value());
		String xmlName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_XML_NAME.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_XML_NAME
						.value());
		String daoName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_DAO_NAME.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_DAO_NAME
						.value());
		String daoImplName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_DAO_IMPL_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_DAO_IMPL_NAME
						.value());
		String serviceName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_SERVICE_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_SERVICE_NAME
						.value());
		String serviceImplName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_SERVICE_IMPL_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_SERVICE_IMPL_NAME
						.value());
		String controllerName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_CONTROLLER_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_CONTROLLER_NAME
						.value());
		GlobalConfig gc = new GlobalConfig();
		gc.setOpen(false).setFileOverride(true).setActiveRecord(false)
				.setOutputDir(javaDir).setAuthor(authorName).setXmlName(xmlName)
				.setMapperName(mapperName).setDaoName(daoName).setDaoImplName(daoImplName)
				.setServiceName(serviceName).setServiceImplName(serviceImplName)
				.setControllerName(controllerName).setSwagger2(enableSwagger);
		return gc;
	}

	/**
	 * 数据源配置
	 * @param properties Properties
	 * @return DataSourceConfig
	 */
	@Override
	public DataSourceConfig getDataSourceConfig(Properties properties) {
		String url = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_URL.key(),
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_URL.value());
		String driverName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.key(),
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_DRIVER.value());
		String username = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.key(),
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_USERNAME.value());
		String password = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.key(),
				CodeGeneratorProperties.ParamKeyEnum.SPRING_DATASOURCE_PASSWORD.value());
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(url).setDriverName(driverName).setUsername(username)
				.setPassword(password);
		return dsc;
	}

	/**
	 * 包名配置
	 * @param properties Properties
	 * @return PackageConfig
	 */
	@Override
	public PackageConfig getPackageConfig(Properties properties) {
		String packageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_PACKAGE_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_PACKAGE_NAME
						.value());
		String moduleName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_MODULE_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_MODULE_NAME
						.value());

		String mapperPackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_MAPPER_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_MAPPER_NAME
						.value());
		String daoPackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_DAO_NAME.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_DAO_NAME
						.value());
		String daoImplPackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_DAO_IMPL_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_DAO_IMPL_NAME
						.value());
		String servicePackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_SERVICE_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_SERVICE_NAME
						.value());
		String serviceImplPackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_SERVICE_IMPL_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_SERVICE_IMPL_NAME
						.value());
		String xmlPackageName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_XML_NAME.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_PC_XML_NAME
						.value());
		PackageConfig pc = new PackageConfig();
		if (StringUtils.isNotBlank(moduleName)) {
			pc.setModuleName(moduleName);
		}
		if (StringUtils.isNotBlank(packageName)) {
			pc.setParent(packageName);
		}
		pc.setMapper(mapperPackageName);
		pc.setDao(daoPackageName);
		pc.setDaoImpl(daoImplPackageName);
		pc.setService(servicePackageName);
		pc.setServiceImpl(serviceImplPackageName);
		pc.setXml(xmlPackageName);
		return pc;
	}

	/**
	 * 策略配置
	 * @param properties Properties
	 * @param pc PackageConfig
	 * @return StrategyConfig
	 */
	@Override
	public StrategyConfig getStrategyConfig(Properties properties, PackageConfig pc) {
		String tableName = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_TABLE_NAME
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_TABLE_NAME
						.value());
		String superEntity = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_ENTITY
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_ENTITY
						.value());
		String superDao = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_DAO.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_DAO
						.value());
		String superDaoImpl = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_DAO_IMPL
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_DAO_IMPL
						.value());
		String superService = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_SERVICE
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_SERVICE
						.value());
		String superServiceImpl = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_SERVICE_IMPL
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_SERVICE_IMPL
						.value());
		String superController = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_CONTROLLER
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_SC_SUPER_CONTROLLER
						.value());

		StrategyConfig sc = new StrategyConfig();
		sc.setEntityLombokModel(true).setControllerMappingHyphenStyle(true)
				.setRestControllerStyle(true).setNaming(NamingStrategy.underline_to_camel)
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setSuperEntityClass(superEntity).setSuperDaoClass(superDao)
				.setSuperDaoImplClass(superDaoImpl).setSuperServiceClass(superService)
				.setSuperServiceImplClass(superServiceImpl)
				// .setSuperControllerClass(superController)
				.setSuperEntityColumns("id").setTablePrefix("t_")
				.setTablePrefix(pc.getModuleName() + "_");
		if (StringUtils.isNotEmpty(tableName)) {
			tableName = tableName.replaceAll(" ", "");
			sc.setInclude(tableName.split(","));
		}
		else {
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
	@Override
	public InjectionConfig getInjectionConfig(Properties properties, PackageConfig pc) {
		String resourcesDir = PropertiesUtil.getString(properties,
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_RESOURCE_DIR
						.key(),
				CodeGeneratorProperties.ParamKeyEnum.MYBATIS_GENERATOR_GC_RESOURCE_DIR
						.value());

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

				if (StringUtils.isNotEmpty(pc.getModuleName())) {
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
	 * 模板配置
	 * @return
	 */
	@Override
	public TemplateConfig getTemplateConfig() {
		return new TemplateConfig().setEntity("templates/entity.java")
				.setController("templates/controller.java").setDao("templates/dao.java")
				.setDaoImpl("templates/daoImpl.java").setService("templates/service.java")
				.setServiceImpl("templates/serviceImpl.java")
				.setMapper("templates/mapper.java").setXml(null);
	}

	/**
	 * <p>
	 * 属性文件中未检索到的配置项，通过控制台输入方式读取
	 * </p>
	 */
	private String scanner(String tip) {
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

}
