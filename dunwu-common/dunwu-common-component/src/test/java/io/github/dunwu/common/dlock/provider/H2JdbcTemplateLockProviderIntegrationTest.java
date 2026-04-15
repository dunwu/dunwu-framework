package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.support.BaseJdbcLockProviderIntegrationTest;
import io.github.dunwu.common.dlock.support.DbConfig;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2JdbcTemplateLockProviderIntegrationTest extends BaseJdbcLockProviderIntegrationTest {

    private static final H2Config dbConfig = new H2Config();

    @Override
    protected DbConfig getDbConfig() {
        return dbConfig;
    }

    @Override
    protected StorageBasedLockProvider getLockProvider() {
        return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration.builder()
            .withJdbcTemplate(new JdbcTemplate(getDatasource()))
            .build()
        );
    }

    static class H2Config implements DbConfig {

        public void startDb() {
        }

        public void shutdownDb() {
        }

        public String getJdbcUrl() {
            return "jdbc:h2:mem:test";
        }

        @Override
        public String getUsername() {
            return "SA";
        }

        @Override
        public String getPassword() {
            return "";
        }

    }

}
