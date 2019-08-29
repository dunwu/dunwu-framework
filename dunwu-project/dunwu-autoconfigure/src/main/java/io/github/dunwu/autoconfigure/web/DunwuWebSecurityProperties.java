package io.github.dunwu.autoconfigure.web;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Web 安全配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.web.security")
public class DunwuWebSecurityProperties {
    private Boolean enable = false;
    private String authTokenKey = "token";
}
