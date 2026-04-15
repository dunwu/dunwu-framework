package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.support.BaseLockProviderIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.embedded.RedisServer;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseRedisLockProviderIntegrationTest extends BaseLockProviderIntegrationTest {

    protected final static int PORT = 6380;

    protected final static String HOST = "localhost";

    private final static String ENV = "test";

    private final static String KEY_PREFIX = "test-prefix";

    private static RedisServer redisServer;

    private RedisLockProvider lockProvider;

    private StringRedisTemplate redisTemplate;

    public BaseRedisLockProviderIntegrationTest(RedisConnectionFactory connectionFactory) {
        lockProvider = new RedisLockProvider(connectionFactory, ENV, KEY_PREFIX);
        redisTemplate = new StringRedisTemplate(connectionFactory);
    }

    @BeforeAll
    public static void startRedis() throws IOException {
        redisServer = new RedisServer(PORT);
        redisServer.start();
    }

    @AfterAll
    public static void stopRedis() {
        redisServer.stop();
    }

    @Override
    protected LockProvider getLockProvider() {
        return lockProvider;
    }

    @Override
    protected void assertUnlocked(String lockName) {
        assertThat(redisTemplate.hasKey(buildKey(lockName))).isFalse();
    }

    private String buildKey(String lockName) {
        return lockProvider.buildKey(lockName);
    }

    @Override
    protected void assertLocked(String lockName) {
        assertThat(redisTemplate.getExpire(buildKey(lockName))).isGreaterThan(0);
    }

}
