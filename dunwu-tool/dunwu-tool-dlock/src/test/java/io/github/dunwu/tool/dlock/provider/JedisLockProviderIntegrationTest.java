package io.github.dunwu.tool.dlock.provider;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class JedisLockProviderIntegrationTest extends AbstractRedisLockProviderIntegrationTest {

    public JedisLockProviderIntegrationTest() {
        super(createJedisConnectionFactory());
    }

    private static RedisConnectionFactory createJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(HOST, PORT);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

}
