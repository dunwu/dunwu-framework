package io.github.dunwu.config;

/**
 * @author Zhang Peng
 * @since 2019-04-15
 */
public interface Const {
    enum ParamKeyEnum {
        // @formatter:off
        SPRING_DATASOURCE_URL("spring.datasource.url", "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8"),
        SPRING_DATASOURCE_DRIVER("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"),
        SPRING_DATASOURCE_USERNAME("spring.datasource.username", "root"),
        SPRING_DATASOURCE_PASSWORD("spring.datasource.password", "root"),
        MYBATIS_GENERATOR_JAVA_DIR("mybatis.generator.java.dir", System.getProperty("user.dir") + "/dunwu-framework/src/main/java"),
        MYBATIS_GENERATOR_RESOURCE_DIR("mybatis.generator.resources.dir", System.getProperty("user.dir") + "/dunwu-framework/src/main/resources"),
        MYBATIS_GENERATOR_PACKAGE_NAME("mybatis.generator.package.name", "io.github.dunwu"),
        MYBATIS_GENERATOR_MODULE_NAME("mybatis.generator.module.name", null),
        MYBATIS_GENERATOR_TABLE_NAME("mybatis.generator.table.name", null);
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
