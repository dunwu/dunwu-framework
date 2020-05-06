/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.github.dunwu.dlock.provider;

import io.github.dunwu.dlock.core.LockProvider;
import io.github.dunwu.dlock.test.support.AbstractLockProviderIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.embedded.RedisServer;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractRedisLockProviderIntegrationTest extends AbstractLockProviderIntegrationTest {

    protected final static int PORT = 6380;

    protected final static String HOST = "localhost";

    private final static String ENV = "test";

    private final static String KEY_PREFIX = "test-prefix";

    private static RedisServer redisServer;

    private RedisLockProvider lockProvider;

    private StringRedisTemplate redisTemplate;

    public AbstractRedisLockProviderIntegrationTest(RedisConnectionFactory connectionFactory) {
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
