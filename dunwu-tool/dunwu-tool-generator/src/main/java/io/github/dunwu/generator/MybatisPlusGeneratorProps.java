package io.github.dunwu.generator;

/**
 * <a href="https://mybatis.plus/guide/generator.html">MyBatis-Plus 代码生成器</a> 配置 Key
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
public enum MybatisPlusGeneratorProps {

    // ------------------------------------- 数据源配置 -------------------------------------
    /**
     * 数据源 URL
     */
    SPRING_DATASOURCE_URL("spring.datasource.url", null),
    /**
     * 数据源驱动类
     */
    SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", null),
    /**
     * 数据源用户名
     */
    SPRING_DATASOURCE_USERNAME("spring.datasource.username", null),
    /**
     * 数据源密码
     */
    SPRING_DATASOURCE_PASSWORD("spring.datasource.password", null),

    // ------------------------------------- 全局配置 -------------------------------------
    /**
     * java 根路径
     */
    GC_JAVA_DIR("mybatis.generator.gc.java.dir",
        System.getProperty("user.dir") + "/src/main/java"),

    /**
     * resources 根路径
     */
    GC_RESOURCE_DIR("mybatis.generator.gc.resources.dir",
        System.getProperty("user.dir") + "/src/main/resources"),
    /**
     * 是否自动生成 Swagger 注解
     */
    GC_ENABLE_SWAGGER("mybatis.generator.gc.enable.swagger", "true"),
    /**
     * 作者名
     */
    GC_AUTHOR_NAME("mybatis.generator.gc.author.name", null),
    /**
     * Mapper xml 文件名后缀
     */
    GC_XML_NAME("mybatis.generator.gc.xml-name", "%sMapper"),
    /**
     * Mapper 类名后缀
     */
    GC_MAPPER_NAME("mybatis.generator.gc.mapper-name", "%sMapper"),
    /**
     * Dao 类名后缀
     */
    GC_DAO_NAME("mybatis.generator.gc.dao-name", "%sDao"),
    /**
     * Dao 实现类类名后缀
     */
    GC_DAO_IMPL_NAME("mybatis.generator.gc.dao-impl-name", "%sDaoImpl"),
    /**
     * Dao 类名后缀
     */
    GC_SERVICE_NAME("mybatis.generator.gc.service-name", "%sService"),
    /**
     * Dao 实现类类名后缀
     */
    GC_SERVICE_IMPL_NAME("mybatis.generator.gc.service-impl-name", "%sServiceImpl"),
    /**
     * Controller 类名后缀
     */
    GC_CONTROLLER_NAME("mybatis.generator.gc.controller-name", "%sController"),

    // ------------------------------------- 包路径配置 -------------------------------------
    /**
     * 包根路径
     */
    PC_PACKAGE_NAME("mybatis.generator.pc.package.name", null),
    /**
     * 模块名
     */
    PC_MODULE_NAME("mybatis.generator.pc.module.name", null),
    /**
     * Mapper 类根路径
     */
    PC_MAPPER_NAME("mybatis.generator.pc.mapper.name", "dao.mapper"),
    /**
     * Dao 类根路径
     */
    PC_DAO_NAME("mybatis.generator.pc.dao.name", "dao"),
    /**
     * Dao 实现类根路径
     */
    PC_DAO_IMPL_NAME("mybatis.generator.pc.dao.impl.name", "dao.impl"),
    /**
     * Service 类根路径
     */
    PC_SERVICE_NAME("mybatis.generator.pc.service.name", "service"),
    /**
     * Service 实现类根路径
     */
    PC_SERVICE_IMPL_NAME("mybatis.generator.pc.service.impl.name", "service.impl"),
    /**
     * Mapper xml 文件根路径
     */
    PC_XML_NAME("mybatis.generator.pc.xml.name", "mapper"),

    // ------------------------------------- 策略配置 -------------------------------------
    /**
     * 目标表名
     */
    SC_TABLE_NAME("mybatis.generator.sc.table.name", null),
    /**
     * Entity 类超类
     */
    SC_SUPER_ENTITY("mybatis.generator.sc.super.entity", null),
    /**
     * Dao 类超类
     */
    SC_SUPER_DAO("mybatis.generator.sc.super.dao", "io.github.dunwu.data.mybatis.IExtDao"),
    /**
     * Dao 实现类超类
     */
    SC_SUPER_DAO_IMPL("mybatis.generator.sc.super.dao.impl", "io.github.dunwu.data.mybatis.BaseExtDaoImpl");

    /**
     * 配置参数 key
     */
    private String key;

    /**
     * 配置参数默认值
     */
    private String value;

    MybatisPlusGeneratorProps(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
