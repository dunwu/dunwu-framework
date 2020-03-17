package io.github.dunwu.autoconfigure.web;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "dunwu.swagger")
public class DunwuSwaggerProperties {

    private boolean enabled = true;

    private String basePackage = "io.github.dunwu";

    private String title = "dunwu";

    private String description = "dunwu api docs";

    private String version = "2.0";

    private String author = "dunwu";

    private String url = "https://github.com/dunwu";

    private String email = "forbreak@163.com";

    private String license = "Apache 2.0";

    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.html";

}
