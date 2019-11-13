package io.github.dunwu.quickstart.user.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.web.security")
public class DunwuWebSecurityProperties {

	private Boolean enable;

	private String registerUrl = "/user/register";

	private String loginUrl = "/user/login";

	private String logoutUrl = "/user/logout";

	private String validUrl = "/user/isValidUser";

}
