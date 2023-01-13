package io.github.dunwu.autoconfigure.log;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spring Boot 集成 MyBatis-Plus 配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-16
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.log")
public class DunwuLogProperties {

    /**
     * 应用名
     */
    private String appName = "dunwu";
    private Lock lock = new Lock();
    private Operation operation = new Operation();

    @Data
    public static class Operation {

        /**
         * 操作日志开启开关
         */
        private boolean enabled;

    }

    @Data
    public static class Lock {

        /**
         * 数据所动日志开启开关
         */
        private boolean enabled;

    }

}
