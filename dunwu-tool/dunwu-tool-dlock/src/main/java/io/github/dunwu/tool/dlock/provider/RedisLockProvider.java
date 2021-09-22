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
package io.github.dunwu.tool.dlock.provider;

import io.github.dunwu.tool.dlock.core.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;

/**
 * Uses Redis's `SET resource-name anystring NX PX max-lock-ms-time` as locking mechanism. See
 * https://redis.io/commands/set
 */
public class RedisLockProvider implements LockProvider {

    private static final String KEY_PREFIX_DEFAULT = "job-lock";

    private static final String ENV_DEFAULT = "default";

    private final StringRedisTemplate redisTemplate;

    private final String environment;

    private final String keyPrefix;

    public RedisLockProvider(@NotNull RedisConnectionFactory redisConn) {
        this(redisConn, ENV_DEFAULT);
    }

    /**
     * Creates RedisLockProvider
     *
     * @param redisConn   RedisConnectionFactory
     * @param environment environment is part of the key and thus makes sure there is not key conflict between multiple
     *                    DunwuLock instances running on the same Redis
     */
    public RedisLockProvider(@NotNull RedisConnectionFactory redisConn, @NotNull String environment) {
        this(redisConn, environment, KEY_PREFIX_DEFAULT);
    }

    /**
     * Creates RedisLockProvider
     *
     * @param redisConn   RedisConnectionFactory
     * @param environment environment is part of the key and thus makes sure there is not key conflict between multiple
     *                    DunwuLock instances running on the same Redis
     * @param keyPrefix   prefix of the key in Redis.
     */
    public RedisLockProvider(@NotNull RedisConnectionFactory redisConn, @NotNull String environment,
        @NotNull String keyPrefix) {
        this(new StringRedisTemplate(redisConn), environment, keyPrefix);
    }

    public RedisLockProvider(@NotNull StringRedisTemplate redisTemplate, @NotNull String environment,
        @NotNull String keyPrefix) {
        this.redisTemplate = redisTemplate;
        this.environment = environment;
        this.keyPrefix = keyPrefix;
    }

    public RedisLockProvider(@NotNull StringRedisTemplate redisTemplate) {
        this(redisTemplate, ENV_DEFAULT, KEY_PREFIX_DEFAULT);
    }

    @Override
    @NotNull
    public Optional<DistributedLock> lock(@NotNull LockConfiguration lockConfiguration) {
        String key = buildKey(lockConfiguration.getName());
        Expiration expiration = getExpiration(lockConfiguration.getLockMaxTime());
        if (Boolean.TRUE.equals(tryToSetExpiration(redisTemplate, key, expiration, SetOption.SET_IF_ABSENT))) {
            return Optional.of(new RedisLock(key, redisTemplate, lockConfiguration));
        } else {
            return Optional.empty();
        }
    }

    private static Expiration getExpiration(Instant until) {
        return Expiration.from(getMsUntil(until), TimeUnit.MILLISECONDS);
    }

    private static long getMsUntil(Instant until) {
        return Duration.between(Instant.now(), until).toMillis();
    }

    String buildKey(String lockName) {
        return RedisLockProvider.buildKey(lockName, keyPrefix, environment);
    }

    static String buildKey(String lockName, String keyPrefix, String environment) {
        return String.format("%s:%s:%s", keyPrefix, environment, lockName);
    }

    static Boolean tryToSetExpiration(StringRedisTemplate redisTemplate, String key, Expiration expiration,
        SetOption option) {
        byte[] finalKey = serialize(redisTemplate, key);
        byte[] finalValue = serialize(redisTemplate, buildValue());
        return redisTemplate.execute(connection -> connection.set(finalKey, finalValue, expiration, option), false);
    }

    static String buildValue() {
        return String.format("ADDED:%s@%s", Utils.toIsoString(Instant.now()), Utils.getHostname());
    }

    static byte[] serialize(StringRedisTemplate redisTemplate, String string) {
        return redisTemplate.getStringSerializer().serialize(string);
    }

    private static final class RedisLock extends AbstractDistributedLock {

        private final String key;

        private final StringRedisTemplate redisTemplate;

        private RedisLock(String key, StringRedisTemplate redisTemplate, LockConfiguration lockConfiguration) {
            super(lockConfiguration);
            this.key = key;
            this.redisTemplate = redisTemplate;
        }

        @Override
        public void doUnlock() {
            Expiration keepLockFor = getExpiration(lockConfiguration.getLockMinTime());
            // lock at least until is in the past
            if (keepLockFor.getExpirationTimeInMilliseconds() <= 0) {
                try {
                    redisTemplate.delete(key);
                } catch (Exception e) {
                    throw new LockException("Can not remove node", e);
                }
            } else {
                RedisLockProvider.tryToSetExpiration(redisTemplate, key, keepLockFor, SetOption.SET_IF_PRESENT);
            }
        }

    }

}
