package io.github.dunwu.common.dlock.support;

import io.github.dunwu.common.dlock.provider.DistributedLock;
import io.github.dunwu.common.dlock.LockConfiguration;
import io.github.dunwu.common.dlock.provider.StorageBasedLockProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseStorageBasedLockProviderIntegrationTest
    extends BaseExtensibleLockProviderIntegrationTest {

    @Test
    public void lockShouldSurviveCacheClearingInTheMiddle() {
        StorageBasedLockProvider provider = getLockProvider();

        LockConfiguration configuration = lockConfig(LOCK_NAME1);
        Optional<DistributedLock> lock = provider.lock(configuration);
        assertThat(lock).isPresent();

        provider.clearCache();

        // lock is still locked
        assertThat(provider.lock(lockConfig(LOCK_NAME1))).isEmpty();

        lock.get().unlock();
    }

    @Override
    protected abstract StorageBasedLockProvider getLockProvider();

}
