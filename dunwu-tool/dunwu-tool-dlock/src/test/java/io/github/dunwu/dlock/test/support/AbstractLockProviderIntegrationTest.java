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
package io.github.dunwu.dlock.test.support;

import io.github.dunwu.dlock.core.DistributedLock;
import io.github.dunwu.dlock.core.LockConfiguration;
import io.github.dunwu.dlock.core.LockProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractLockProviderIntegrationTest {

    public static final Duration LOCK_AT_LEAST_FOR = Duration.of(2, SECONDS);

    protected static final String LOCK_NAME1 = "name";

    @Test
    public void shouldCreateLock() {
        Optional<DistributedLock> lock = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock).isNotEmpty();

        assertLocked(LOCK_NAME1);
        lock.get().unlock();
        assertUnlocked(LOCK_NAME1);
    }

    protected abstract LockProvider getLockProvider();

    protected abstract void assertUnlocked(String lockName);

    protected abstract void assertLocked(String lockName);

    protected static LockConfiguration lockConfig(String name) {
        return lockConfig(name, Duration.of(5, MINUTES), Duration.ZERO);
    }

    protected static LockConfiguration lockConfig(String name, Duration lockAtMostFor, Duration lockAtLeastFor) {
        Instant now = Instant.now();
        return new LockConfiguration(name, now.plus(lockAtMostFor), now.plus(lockAtLeastFor));
    }

    @Test
    public void shouldNotReturnSecondLock() {
        Optional<DistributedLock> lock = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock).isNotEmpty();
        assertThat(getLockProvider().lock(lockConfig(LOCK_NAME1))).isEmpty();
        lock.get().unlock();
    }

    @Test
    public void shouldCreateTwoIndependentLocks() {
        Optional<DistributedLock> lock1 = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock1).isNotEmpty();

        Optional<DistributedLock> lock2 = getLockProvider().lock(lockConfig("name2"));
        assertThat(lock2).isNotEmpty();

        lock1.get().unlock();
        lock2.get().unlock();
    }

    @Test
    public void shouldLockTwiceInARow() {
        Optional<DistributedLock> lock1 = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock1).isNotEmpty();
        lock1.get().unlock();

        Optional<DistributedLock> lock2 = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock2).isNotEmpty();
        lock2.get().unlock();
    }

    @Test
    public void shouldTimeout() throws InterruptedException {
        LockConfiguration configWithShortTimeout = lockConfig(LOCK_NAME1, Duration.ofMillis(50), Duration.ZERO);
        Optional<DistributedLock> lock1 = getLockProvider().lock(configWithShortTimeout);
        assertThat(lock1).isNotEmpty();

        sleep(55);
        assertUnlocked(LOCK_NAME1);

        Optional<DistributedLock> lock2 =
            getLockProvider().lock(lockConfig(LOCK_NAME1, Duration.ofMillis(50), Duration.ZERO));
        assertThat(lock2).isNotEmpty();
        lock2.get().unlock();
    }

    @Test
    public void shouldBeAbleToLockRightAfterUnlock() {
        LockConfiguration lockConfiguration = lockConfig(LOCK_NAME1);
        for (int i = 0; i < 10; i++) {
            Optional<DistributedLock> lock = getLockProvider().lock(lockConfiguration);
            assertThat(getLockProvider().lock(lockConfiguration)).isEmpty();
            assertThat(lock).isNotEmpty();
            lock.get().unlock();
        }
    }

    @Test
    public void fuzzTestShouldPass() throws ExecutionException, InterruptedException {
        new FuzzTester(getLockProvider()).doFuzzTest();
    }

    @Test
    public void shouldLockAtLeastFor() throws InterruptedException {
        // Lock for LOCK_AT_LEAST_FOR - we do not expect the lock to be released before this time
        Optional<DistributedLock> lock1 =
            getLockProvider().lock(lockConfig(LOCK_NAME1, LOCK_AT_LEAST_FOR.multipliedBy(2), LOCK_AT_LEAST_FOR));
        assertThat(lock1).isNotEmpty();
        lock1.get().unlock();

        // Even though we have unlocked the lock, it will be held for some time
        assertThat(getLockProvider().lock(lockConfig(LOCK_NAME1))).describedAs(
            "Can not acquire lock, grace period did not pass yet").isEmpty();

        // Let's wait wor the lock to be automatically released
        sleep(LOCK_AT_LEAST_FOR.toMillis());

        // Should be able to acquire now
        Optional<DistributedLock> lock3 = getLockProvider().lock(lockConfig(LOCK_NAME1));
        assertThat(lock3).describedAs("Can acquire the lock after grace period").isNotEmpty();
        lock3.get().unlock();
    }

    protected void sleepFor(Duration duration) {
        try {
            sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
