package io.github.dunwu.web.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-14
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.mail")
public class DunwuMailExtProperties {

    private String domain = "163.com";

    private String from;

    private Pool pool = new Pool();


    @Data
    @ToString
    public static class Pool {
        private Integer minSize = 0;
        private Integer maxSize = 1000;
        private Integer keepAliveSecs = 10;
        private Integer queueSize = 100;
        private String threadNamePrefix = "mail-service";
        private Boolean daemon = true;
    }
}
