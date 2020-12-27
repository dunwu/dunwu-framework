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
import io.github.dunwu.dlock.core.StorageBasedLockProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractStorageBasedLockProviderIntegrationTest
    extends AbstractExtensibleLockProviderIntegrationTest {

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
