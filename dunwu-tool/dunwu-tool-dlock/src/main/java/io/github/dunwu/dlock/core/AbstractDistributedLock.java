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
package io.github.dunwu.dlock.core;

import java.time.Instant;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public abstract class AbstractDistributedLock implements DistributedLock {

    protected final LockConfiguration lockConfiguration;

    private boolean valid = true;

    protected AbstractDistributedLock(LockConfiguration lockConfiguration) {
        this.lockConfiguration = lockConfiguration;
    }

    @Override
    public final void unlock() {
        checkValidity();
        doUnlock();
        valid = false;
    }

    protected abstract void doUnlock();

    @Override
    public final Optional<DistributedLock> extend(@NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        checkValidity();
        Optional<DistributedLock> result =
            doExtend(new LockConfiguration(lockConfiguration.getName(), lockMaxTime, lockMinTime));
        valid = false;
        return result;
    }

    protected Optional<DistributedLock> doExtend(LockConfiguration newConfiguration) {
        throw new UnsupportedOperationException();
    }

    private void checkValidity() {
        if (!valid) {
            throw new IllegalStateException("Lock is not valid, it has already been unlocked or extended");
        }
    }

}
