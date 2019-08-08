package io.github.dunwu.scheduler.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-05
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.scheduler")
public class DunwuSchedulerProperties {
    private Boolean enabled = true;
    private String quartzConfigLocation = "/quartz.properties";
    private List<String> scanPackages;
}
