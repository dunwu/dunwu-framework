package io.github.dunwu.autoconfigure.dlock;

import io.github.dunwu.autoconfigure.zookeeper.ZookeeperAutoConfiguration;
import io.github.dunwu.dlock.core.LockProvider;
import io.github.dunwu.dlock.provider.ZookeeperLockProvider;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@Configuration
@ConditionalOnMissingBean(LockProvider.class)
@ConditionalOnProperty(value = "dunwu.dlock.enable", havingValue = "true")
@ConditionalOnBean(value = CuratorFramework.class)
@AutoConfigureAfter({ ZookeeperAutoConfiguration.class })
public class ZookeeperDistributedLockConfiguration {

    @Bean
    @ConditionalOnProperty(name = "dunwu.dlock.type", havingValue = "zookeeper")
    public ZookeeperLockProvider zookeeperCuratorLockProvider(@NotNull CuratorFramework zookeeperClient) {
        return new ZookeeperLockProvider(zookeeperClient);
    }

}
