package io.github.dunwu.generator;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
public interface CodeGeneratorProperties {
    enum ParamKeyEnum {
        // @formatter:off
        /** 数据源配置 **/
        SPRING_DATASOURCE_URL("spring.datasource.url", "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8"),
        SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"),
        SPRING_DATASOURCE_USERNAME("spring.datasource.username", "root"),
        SPRING_DATASOURCE_PASSWORD("spring.datasource.password", "root"),

        /** 全局配置 **/
        MYBATIS_GENERATOR_GC_JAVA_DIR("mybatis.generator.gc.java.dir", System.getProperty("user.dir") + "/src/main/java"),
        MYBATIS_GENERATOR_GC_RESOURCE_DIR("mybatis.generator.gc.resources.dir", System.getProperty("user.dir") + "/src/main/resources"),
        MYBATIS_GENERATOR_GC_ENABLE_SWAGGER("mybatis.generator.gc.enable.swagger", "false"),
        MYBATIS_GENERATOR_GC_AUTHOR_NAME("mybatis.generator.gc.author.name", "<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>"),
        MYBATIS_GENERATOR_GC_XML_NAME("mybatis.generator.gc.xml-name", "%sMapper"),
        MYBATIS_GENERATOR_GC_MAPPER_NAME("mybatis.generator.gc.mapper-name", "%sDao"),
        MYBATIS_GENERATOR_GC_SERVICE_NAME("mybatis.generator.gc.service-name", "%sService"),
        MYBATIS_GENERATOR_GC_CONTROLLER_NAME("mybatis.generator.gc.controller-name", "%sController"),

        /** package 配置 **/
        MYBATIS_GENERATOR_PC_PACKAGE_NAME("mybatis.generator.pc.package.name", null),
        MYBATIS_GENERATOR_PC_MODULE_NAME("mybatis.generator.pc.module.name", null),
        MYBATIS_GENERATOR_PC_MAPPER_NAME("mybatis.generator.pc.mapper.name", "dao"),
        MYBATIS_GENERATOR_PC_XML_NAME("mybatis.generator.pc.xml.name", "mapper"),

        /** 策略配置 **/
        MYBATIS_GENERATOR_SC_TABLE_NAME("mybatis.generator.sc.table.name", null),
        MYBATIS_GENERATOR_SC_SUPER_ENTITY("mybatis.generator.sc.super.entity", "io.github.dunwu.data.entity.BaseEntity"),
        MYBATIS_GENERATOR_SC_SUPER_CONTROLLER("mybatis.generator.sc.super.controller", "io.github.dunwu.web.controller.CrudController");
        // @formatter:on

        /**
         * 配置参数 key
         */
        private String key;
        /**
         * 配置参数默认值
         */
        private String value;

        ParamKeyEnum(String key, String value) {
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
}
