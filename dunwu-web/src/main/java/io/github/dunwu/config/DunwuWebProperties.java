package io.github.dunwu.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Custom Web Configuration
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.web")
public class DunwuWebProperties {

    private Boolean request = true;

    /**
     * 放开跨域限制开关
     */
    private Boolean corsEnable = false;

    /**
     * http 数据自动格式化开关
     */
    private Boolean formatEnable = false;
}
