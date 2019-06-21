package io.github.dunwu.generator;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
public interface CodeGeneratorProperties {
    enum ParamKeyEnum {
        // @formatter:off
        SPRING_DATASOURCE_URL("spring.datasource.url", "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8"),
        SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"),
        SPRING_DATASOURCE_USERNAME("spring.datasource.username", "root"),
        SPRING_DATASOURCE_PASSWORD("spring.datasource.password", "root"),
        MYBATIS_GENERATOR_ENABLE_SWAGGER("mybatis.generator.enable.swagger", "true"),
        MYBATIS_GENERATOR_JAVA_DIR("mybatis.generator.java.dir", System.getProperty("user.dir") + "/src/main/java"),
        MYBATIS_GENERATOR_RESOURCE_DIR("mybatis.generator.resources.dir", System.getProperty("user.dir") + "/src/main/resources"),
        MYBATIS_GENERATOR_AUTHOR_NAME("mybatis.generator.author.name", "<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>"),
        MYBATIS_GENERATOR_PACKAGE_NAME("mybatis.generator.package.name", "io.github.dunwu"),
        MYBATIS_GENERATOR_MODULE_NAME("mybatis.generator.module.name", null),
        MYBATIS_GENERATOR_TABLE_NAME("mybatis.generator.table.name", null),
        MYBATIS_GENERATOR_SUPER_ENTITY("mybatis.generator.super.entity", "io.github.dunwu.data.entity.BaseEntity"),
        MYBATIS_GENERATOR_SUPER_CONTROLLER("mybatis.generator.super.controller", "io.github.dunwu.web.controller.CrudController"),
        ;
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
