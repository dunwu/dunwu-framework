package io.github.dunwu.autoconfigure.dlock;

import io.github.dunwu.tool.dlock.core.LockProvider;
import io.github.dunwu.tool.dlock.provider.RedisLockProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@Configuration
@ConditionalOnMissingBean(LockProvider.class)
@ConditionalOnProperty(value = "dunwu.dlock.enable", havingValue = "true")
@AutoConfigureAfter({ RedisAutoConfiguration.class })
public class RedisDistributedLockConfiguration {

    @Bean
    @ConditionalOnProperty(name = "dunwu.dlock.type", havingValue = "redis")
    public RedisLockProvider redisLockProvider(@NotNull StringRedisTemplate stringRedisTemplate) {
        return new RedisLockProvider(stringRedisTemplate);
    }

}
