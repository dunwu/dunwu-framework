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
package io.github.dunwu.tool.dlock.test.support;

import io.github.dunwu.tool.dlock.core.DistributedLock;
import io.github.dunwu.tool.dlock.core.LockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractJdbcLockProviderIntegrationTest extends AbstractStorageBasedLockProviderIntegrationTest {

    protected JdbcTestUtils testUtils;

    @Override
    protected void assertUnlocked(String lockName) {
        Instant lockedUntil = getLockedUntil(lockName);
        assertThat(lockedUntil).isBeforeOrEqualTo(Instant.now().truncatedTo(ChronoUnit.MILLIS).plusMillis(1));
    }

    private Instant getLockedUntil(String lockName) {
        return testUtils.getJdbcTemplate()
            .queryForObject("SELECT lock_until FROM dunwulock WHERE name = ?", new Object[] { lockName },
                Instant.class);
    }

    @Override
    protected void assertLocked(String lockName) {
        Instant lockedUntil = getLockedUntil(lockName);
        assertThat(lockedUntil).isAfter(Instant.now());
    }

    @BeforeEach
    public void initTestUtils() {
        testUtils = new JdbcTestUtils(getDbConfig());
    }

    protected abstract DbConfig getDbConfig();

    @AfterEach
    public void cleanup() {
        testUtils.clean();
    }

    @Test
    public void shouldCreateLockIfRecordAlreadyExists() {
        Calendar now = now();
        testUtils.getJdbcTemplate()
            .update("INSERT INTO dunwulock(name, lock_until, locked_at, locked_by) VALUES(?, ?, ?, ?)", LOCK_NAME1, now,
                now, "me");
        shouldCreateLock();
    }

    protected Calendar now() {
        return Calendar.getInstance();
    }

    @Test
    public void fuzzTestShouldWorkWithTransaction() throws ExecutionException, InterruptedException {
        TransactionalFuzzTester.fuzzTestShouldWorkWithTransaction(getLockProvider(), getDatasource());
    }

    protected DataSource getDatasource() {
        return testUtils.getDatasource();
    }

    @Test
    public void shouldNotFailIfKeyNameTooLong() {
        LockConfiguration configuration = lockConfig(
            "lock name that is too long Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        Optional<DistributedLock> lock = getLockProvider().lock(configuration);
        assertThat(lock).isEmpty();
    }

}
