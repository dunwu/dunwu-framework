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

import io.github.dunwu.tool.dlock.core.StorageBasedLockProvider;
import io.github.dunwu.tool.dlock.test.support.AbstractJdbcLockProviderIntegrationTest;
import io.github.dunwu.tool.dlock.test.support.DbConfig;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2JdbcTemplateLockProviderIntegrationTest extends AbstractJdbcLockProviderIntegrationTest {

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
