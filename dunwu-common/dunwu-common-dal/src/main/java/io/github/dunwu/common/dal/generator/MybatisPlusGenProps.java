package io.github.dunwu.common.dal.generator;

/**
 * MyBatis-Plus 代码生成器配置属性键枚举。
 * <p>
 * 定义了代码生成过程中所有可配置项的属性键（Key）及其默认值。
 * 这些配置通常通过 {@link java.util.Properties} 对象或配置文件加载，
 * 用于初始化 {@link io.github.dunwu.common.dal.generator.config.GlobalConfig}、
 * {@link io.github.dunwu.common.dal.generator.config.DataSourceConfig}、
 * {@link io.github.dunwu.common.dal.generator.config.PackageConfig} 和
 * {@link io.github.dunwu.common.dal.generator.config.StrategyConfig} 等。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://mybatis.plus/guide/generator.html">MyBatis-Plus 代码生成器</a>
 * @since 2019-04-15
 */
public enum MybatisPlusGenProps {

    // ------------------------------------- 数据源配置 -------------------------------------
    /** 数据源 JDBC URL */
    SPRING_DATASOURCE_URL("spring.datasource.url", null),
    /** 数据源驱动类名 */
    SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", null),
    /** 数据源用户名 */
    SPRING_DATASOURCE_USERNAME("spring.datasource.username", null),
    /** 数据源密码 */
    SPRING_DATASOURCE_PASSWORD("spring.datasource.password", null),

    // ------------------------------------- 全局配置 -------------------------------------
    /** 代码输出根目录 */
    OUTPUT_DIR("mybatis.generator.gc.output.dir", System.getProperty("user.dir")),
    /** 后端代码输出目录 */
    BACKEND_DIR("mybatis.generator.gc.backend.dir", null),
    /** 前端代码输出目录 */
    FRONTEND_DIR("mybatis.generator.gc.frontend.dir", null),
    /** 是否自动生成 Swagger 注解 */
    GC_ENABLE_SWAGGER("mybatis.generator.gc.enable.swagger", "true"),
    /** 是否自动生成 EasyExcel 注解 */
    GC_ENABLE_EASY_EXCEL("mybatis.generator.gc.enable.easy.excel", "false"),
    /** 作者名称 */
    GC_AUTHOR_NAME("mybatis.generator.gc.author.name", null),
    /** Mapper XML 文件名后缀模板（如 %sMapper） */
    GC_XML_NAME("mybatis.generator.gc.xml-name", "%sMapper"),
    /** Mapper 接口类名后缀模板 */
    GC_MAPPER_NAME("mybatis.generator.gc.mapper-name", "%sMapper"),
    /** DAO 接口类名后缀模板 */
    GC_DAO_NAME("mybatis.generator.gc.dao-name", "%sDao"),
    /** DAO 实现类类名后缀模板 */
    GC_DAO_IMPL_NAME("mybatis.generator.gc.dao-impl-name", "%sDaoImpl"),
    /** Service 接口类名后缀模板 */
    GC_SERVICE_NAME("mybatis.generator.gc.service-name", "%sService"),
    /** Service 实现类类名后缀模板 */
    GC_SERVICE_IMPL_NAME("mybatis.generator.gc.service-impl-name", "%sServiceImpl"),
    /** Controller 类名后缀模板 */
    GC_CONTROLLER_NAME("mybatis.generator.gc.controller-name", "%sController"),

    // ------------------------------------- 包路径配置 -------------------------------------
    /** 基础包名（父包名） */
    PC_PACKAGE_NAME("mybatis.generator.pc.package.name", null),
    /** 模块名称 */
    PC_MODULE_NAME("mybatis.generator.pc.module.name", null),
    /** Mapper 接口包名 */
    PC_MAPPER_NAME("mybatis.generator.pc.mapper.name", "dao.mapper"),
    /** DAO 接口包名 */
    PC_DAO_NAME("mybatis.generator.pc.dao.name", "dao"),
    /** DAO 实现类包名 */
    PC_DAO_IMPL_NAME("mybatis.generator.pc.dao.impl.name", "dao.impl"),
    /** Service 接口包名 */
    PC_SERVICE_NAME("mybatis.generator.pc.service.name", "service"),
    /** Service 实现类包名 */
    PC_SERVICE_IMPL_NAME("mybatis.generator.pc.service.impl.name", "service.impl"),
    /** Mapper XML 文件包名（相对于 resources） */
    PC_XML_NAME("mybatis.generator.pc.xml.name", "mapper"),

    // ------------------------------------- 策略配置 -------------------------------------
    /** 需要生成的表名（多表逗号分隔） */
    SC_TABLE_NAME("mybatis.generator.sc.table.name", null),
    /** Entity 父类全限定名 */
    SC_SUPER_ENTITY("mybatis.generator.sc.super.entity", null),
    /** DAO 接口父类全限定名 */
    SC_SUPER_DAO("mybatis.generator.sc.super.dao", "io.github.dunwu.tool.data.mybatis.IExtDao"),
    /** DAO 实现类父类全限定名 */
    SC_SUPER_DAO_IMPL("mybatis.generator.sc.super.dao.impl", "io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl");

    /**
     * 配置属性键
     */
    private final String key;

    /**
     * 配置属性默认值
     */
    private final String value;

    /**
     * 构造方法
     *
     * @param key   属性键
     * @param value 默认值
     */
    MybatisPlusGenProps(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取属性键
     *
     * @return 属性键字符串
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取默认值
     *
     * @return 默认值字符串
     */
    public String getValue() {
        return value;
    }
}
