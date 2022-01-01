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
@ConfigurationProperties(prefix = "dunwu.operation.log")
public class DunwuOperationLogProperties {

    /**
     * 操作日志开启开关
     */
    private boolean enabled = true;

    /**
     * 应用名
     */
    private String appName = "dunwu";

}
