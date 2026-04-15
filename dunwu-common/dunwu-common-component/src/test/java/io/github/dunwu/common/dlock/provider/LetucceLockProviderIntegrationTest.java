package io.github.dunwu.common.dlock.provider;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class LetucceLockProviderIntegrationTest extends BaseRedisLockProviderIntegrationTest {

    public LetucceLockProviderIntegrationTest() {
        super(createLettuceConnectionFactory());
    }

    private static RedisConnectionFactory createLettuceConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(HOST, PORT);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

}
