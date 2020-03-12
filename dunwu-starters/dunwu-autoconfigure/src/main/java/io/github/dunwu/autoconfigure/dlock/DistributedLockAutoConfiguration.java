package io.github.dunwu.autoconfigure.dlock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@Configuration
@EnableConfigurationProperties(DistributedLockProperties.class)
@ConditionalOnProperty(value = "dunwu.dlock.enable", havingValue = "true")
@Import({ JdbcDistributedLockConfiguration.class, RedisDistributedLockConfiguration.class,
    ZookeeperDistributedLockConfiguration.class })
public class DistributedLockAutoConfiguration {}
