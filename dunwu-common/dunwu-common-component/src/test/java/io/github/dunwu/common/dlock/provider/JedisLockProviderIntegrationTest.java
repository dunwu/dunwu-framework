package io.github.dunwu.common.dlock.provider;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class JedisLockProviderIntegrationTest extends BaseRedisLockProviderIntegrationTest {

    public JedisLockProviderIntegrationTest() {
        super(createJedisConnectionFactory());
    }

    private static RedisConnectionFactory createJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(HOST, PORT);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

}
