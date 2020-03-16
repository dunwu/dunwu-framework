package io.github.dunwu.autoconfigure.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@ConditionalOnProperty(value = "dunwu.zookeeper.enable", havingValue = "true")
@ConditionalOnClass(CuratorFramework.class)
@Configuration
public class ZookeeperAutoConfiguration {

    @Value("${dunwu.zookeeper.host}")
    private String host;

    @Value("${dunwu.zookeeper.port}")
    private int port;

    @Bean
    public CuratorFramework zookeeperClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .connectString(host + ":" + port).build();
        client.start();
        return client;
    }

}
