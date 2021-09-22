package io.github.dunwu.autoconfigure.dlock;

import io.github.dunwu.tool.dlock.core.LockProvider;
import io.github.dunwu.tool.dlock.provider.JdbcTemplateLockProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@Configuration
@ConditionalOnMissingBean(LockProvider.class)
@ConditionalOnProperty(value = "dunwu.dlock.enable", havingValue = "true")
@AutoConfigureAfter({ JdbcTemplateAutoConfiguration.class })
public class JdbcDistributedLockConfiguration {

    private final DistributedLockProperties properties;

    public JdbcDistributedLockConfiguration(DistributedLockProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(name = "dunwu.dlock.type", havingValue = "jdbc")
    public JdbcTemplateLockProvider jdbcTemplateLockProvider(@NotNull JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateLockProvider(jdbcTemplate, properties.getJdbc().getTableName());
    }

}
