package io.github.dunwu.autoconfigure.mail;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-14
 */
@Data
@ToString
@ConfigurationProperties(prefix = "spring.mail")
public class DunwuMailProperties {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * SMTP server host. For instance, `smtp.example.com`.
     */
    private String host;

    /**
     * SMTP server port.
     */
    private Integer port;

    /**
     * Login user of the SMTP server.
     */
    private String username;

    /**
     * Login password of the SMTP server.
     */
    private String password;

    /**
     * Protocol used by the SMTP server.
     */
    private String protocol = "smtp";

    /**
     * Default MimeMessage encoding.
     */
    private Charset defaultEncoding = DEFAULT_CHARSET;

    /**
     * Additional JavaMail Session properties.
     */
    private Map<String, String> properties = new HashMap<>();

    /**
     * Session JNDI name. When set, takes precedence over other Session settings.
     */
    private String jndiName;

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
