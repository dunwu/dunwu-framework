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

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.config.SchemaConfig;
import com.wix.mysql.distribution.Version;
import io.github.dunwu.tool.dlock.core.StorageBasedLockProvider;
import io.github.dunwu.tool.dlock.test.support.AbstractJdbcLockProviderIntegrationTest;
import io.github.dunwu.tool.dlock.test.support.DbConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class MySqlJdbcTemplateLockProviderIntegrationTest extends AbstractJdbcLockProviderIntegrationTest {

    private static final MySqlConfig dbConfig = new MySqlConfig();

    @AfterAll
    public static void shutDownMysql() {
        dbConfig.shutdownDb();
    }

    @BeforeAll
    public static void startMySql() {
        dbConfig.startDb();
    }

    @Override
    protected DbConfig getDbConfig() {
        return dbConfig;
    }

    @Override
    protected StorageBasedLockProvider getLockProvider() {
        return new JdbcTemplateLockProvider(getDatasource());
    }

    static class MySqlConfig implements DbConfig {

        private static final String TEST_SCHEMA_NAME = "dunwulock_test";

        private static final String USERNAME = "SA";

        private static final String PASSWORD = "";

        private static final SchemaConfig schemaConfig = SchemaConfig.aSchemaConfig(TEST_SCHEMA_NAME).build();

        private EmbeddedMysql mysqld;

        public void startDb() {
            MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v5_6_latest)
                .withUser(USERNAME, PASSWORD)
                .build();

            mysqld = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema(schemaConfig)
                .start();
        }

        public void shutdownDb() {
            mysqld.dropSchema(schemaConfig);
            mysqld.stop();
        }

        public String getJdbcUrl() {
            return "jdbc:mysql://localhost:3310/" + TEST_SCHEMA_NAME;
        }

        @Override
        public String getUsername() {
            return USERNAME;
        }

        @Override
        public String getPassword() {
            return PASSWORD;
        }

    }

}
