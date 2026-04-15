package io.github.dunwu.common.dlock.support;

import io.github.dunwu.common.dlock.provider.DistributedLock;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class BaseExtensibleLockProviderIntegrationTest extends BaseLockProviderIntegrationTest {

    @Test
    public void shouldBeAbleToExtendLock() {
        Duration originalLockDuration = Duration.ofMillis(1_000);
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, originalLockDuration, Duration.ZERO));
        assertThat(lock).isNotEmpty();
        assertLocked(LOCK_NAME1);
        Optional<DistributedLock> newLock = lock.get().extend(Instant.now().plusSeconds(10), Instant.now());
        assertThat(newLock).isNotEmpty();

        // wait for the original lock to be released
        sleepFor(originalLockDuration);
        assertLocked(LOCK_NAME1);

        newLock.get().unlock();
        assertUnlocked(LOCK_NAME1);
    }

    @Test
    public void shouldNotBeAbleToExtendUnlockedLock() {
        Duration originalLockDuration = Duration.ofMillis(1_000);
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, originalLockDuration, Duration.ZERO));
        assertThat(lock).isNotEmpty();
        assertLocked(LOCK_NAME1);
        lock.get().unlock();
        assertUnlocked(LOCK_NAME1);

        assertInvalidLock(() -> lock.get().extend(Instant.now().plusSeconds(10), Instant.now()));
    }

    void assertInvalidLock(ThrowableAssert.ThrowingCallable operation) {
        assertThatThrownBy(operation).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldNotBeAbleToExtendExpiredLock() {
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, Duration.ofMillis(1), Duration.ZERO));
        sleepFor(Duration.ofMillis(1));
        assertThat(lock).isNotEmpty();

        Optional<DistributedLock> newLock = lock.get().extend(Instant.now().plusSeconds(10), Instant.now());
        assertThat(newLock).isEmpty();
        assertUnlocked(LOCK_NAME1);
    }

    @Test
    public void shouldBeAbleToExtendAtLeast() {
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, Duration.ofSeconds(10), Duration.ZERO));
        assertThat(lock).isNotEmpty();

        Optional<DistributedLock> newLock =
            lock.get().extend(Instant.now().plusSeconds(10), Instant.now().plusSeconds(9));
        assertThat(newLock).isNotEmpty();
        newLock.get().unlock();
        assertLocked(LOCK_NAME1);
    }

    @Test
    public void lockCanNotBeExtendedTwice() {
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, Duration.ofSeconds(10), Duration.ZERO));
        assertThat(lock).isNotEmpty();
        Optional<DistributedLock> newLock =
            lock.get().extend(Instant.now().plusSeconds(10), Instant.now().plusSeconds(9));
        assertThat(newLock).isNotEmpty();

        assertInvalidLock(() -> lock.get().extend(Instant.now().plusSeconds(10), Instant.now().plusSeconds(9)));
    }

    @Test
    public void lockCanNotBeUnlockedAfterExtending() {
        Optional<DistributedLock> lock =
            getLockProvider().lock(lockConfig(LOCK_NAME1, Duration.ofSeconds(10), Duration.ZERO));
        assertThat(lock).isNotEmpty();
        Optional<DistributedLock> newLock =
            lock.get().extend(Instant.now().plusSeconds(10), Instant.now().plusSeconds(9));
        assertThat(newLock).isNotEmpty();

        assertInvalidLock(() -> lock.get().unlock());
    }

}
