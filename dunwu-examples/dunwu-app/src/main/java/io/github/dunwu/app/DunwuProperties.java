package io.github.dunwu.app;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * 系统配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
@Data
@SpringBootConfiguration
@PropertySource(value = { "classpath:config/dunwu.properties" })
@ConfigurationProperties(prefix = "dunwu")
public class DunwuProperties {

    private int maxBatchInsertNum = 1000;

}
