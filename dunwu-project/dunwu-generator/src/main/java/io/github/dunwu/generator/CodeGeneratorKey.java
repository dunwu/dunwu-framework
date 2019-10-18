package io.github.dunwu.generator;

/**
 * <a href="https://mybatis.plus/guide/generator.html">MyBatis-Plus 代码生成器</a> 配置 Key
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
public enum CodeGeneratorKey {

	// ------------------------------------- 数据源配置 -------------------------------------
	/**
	 * 数据源 URL
	 */
	SPRING_DATASOURCE_URL("spring.datasource.url", "jdbc:mysql://localhost:3306/test"),
	/**
	 * 数据源驱动类
	 */
	SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"),
	/**
	 * 数据源用户名
	 */
	SPRING_DATASOURCE_USERNAME("spring.datasource.username", "root"),
	/**
	 * 数据源密码
	 */
	SPRING_DATASOURCE_PASSWORD("spring.datasource.password", "root"),

	// ------------------------------------- 全局配置 -------------------------------------
	/**
	 * java 根路径
	 */
	MYBATIS_GENERATOR_GC_JAVA_DIR("mybatis.generator.gc.java.dir", System.getProperty("user.dir") + "/src/main/java"),

	/**
	 * resources 根路径
	 */
	MYBATIS_GENERATOR_GC_RESOURCE_DIR("mybatis.generator.gc.resources.dir",
			System.getProperty("user.dir") + "/src/main/resources"),
	/**
	 * 是否自动生成 Swagger 注解
	 */
	MYBATIS_GENERATOR_GC_ENABLE_SWAGGER("mybatis.generator.gc.enable.swagger", "false"),
	/**
	 * 作者名
	 */
	MYBATIS_GENERATOR_GC_AUTHOR_NAME("mybatis.generator.gc.author.name",
			"<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>"),
	/**
	 * Mapper xml 文件名后缀
	 */
	MYBATIS_GENERATOR_GC_XML_NAME("mybatis.generator.gc.xml-name", "%sMapper"),
	/**
	 * Mapper 类名后缀
	 */
	MYBATIS_GENERATOR_GC_MAPPER_NAME("mybatis.generator.gc.mapper-name", "%sMapper"),
	/**
	 * Service 类名后缀
	 */
	MYBATIS_GENERATOR_GC_SERVICE_NAME("mybatis.generator.gc.service-name", "%sService"),
	/**
	 * Service 实现类类名后缀
	 */
	MYBATIS_GENERATOR_GC_SERVICE_IMPL_NAME("mybatis.generator.gc.service-impl-name", "%sServiceImpl"),
	/**
	 * Controller 类名后缀
	 */
	MYBATIS_GENERATOR_GC_CONTROLLER_NAME("mybatis.generator.gc.controller-name", "%sController"),

	// ------------------------------------- 包路径配置 -------------------------------------
	/**
	 * 包根路径
	 */
	MYBATIS_GENERATOR_PC_PACKAGE_NAME("mybatis.generator.pc.package.name", null),
	/**
	 * 模块名
	 */
	MYBATIS_GENERATOR_PC_MODULE_NAME("mybatis.generator.pc.module.name", null),
	/**
	 * Mapper 类根路径
	 */
	MYBATIS_GENERATOR_PC_MAPPER_NAME("mybatis.generator.pc.mapper.name", "mapper"),
	/**
	 * Service 类根路径
	 */
	MYBATIS_GENERATOR_PC_SERVICE_NAME("mybatis.generator.pc.service.name", "service"),
	/**
	 * Service 实现类根路径
	 */
	MYBATIS_GENERATOR_PC_SERVICE_IMPL_NAME("mybatis.generator.pc.service.impl.name", "service.impl"),
	/**
	 * Mapper xml 文件根路径
	 */
	MYBATIS_GENERATOR_PC_XML_NAME("mybatis.generator.pc.xml.name", "mapper"),

	// ------------------------------------- 策略配置 -------------------------------------
	/**
	 * 目标表名
	 */
	MYBATIS_GENERATOR_SC_TABLE_NAME("mybatis.generator.sc.table.name", null),
	/**
	 * Entity 类超类
	 */
	MYBATIS_GENERATOR_SC_SUPER_ENTITY("mybatis.generator.sc.super.entity", "io.github.dunwu.data.entity.BaseEntity"),
	/**
	 * Service 类超类
	 */
	MYBATIS_GENERATOR_SC_SUPER_SERVICE("mybatis.generator.sc.super.service", "io.github.dunwu.data.service.IService"),
	/**
	 * Service 实现类超类
	 */
	MYBATIS_GENERATOR_SC_SUPER_SERVICE_IMPL("mybatis.generator.sc.super.service.impl",
			"io.github.dunwu.data.service.ServiceImpl");
	/**
	 * 配置参数 key
	 */
	private String key;

	/**
	 * 配置参数默认值
	 */
	private String value;

	CodeGeneratorKey(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String key() {
		return key;
	}

	public String value() {
		return value;
	}

}
