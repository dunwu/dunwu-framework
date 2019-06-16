package io.github.dunwu.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.security")
public class DunwuSecurityProperties {
    private Boolean enable = false;
    private String authTokenKey = "token";
}
