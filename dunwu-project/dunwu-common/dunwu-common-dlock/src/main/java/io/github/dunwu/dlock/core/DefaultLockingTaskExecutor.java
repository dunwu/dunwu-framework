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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * Default {@link LockingTaskExecutor} implementation.
 */
public class DefaultLockingTaskExecutor implements LockingTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLockingTaskExecutor.class);

    @NotNull
    private final LockProvider lockProvider;

    public DefaultLockingTaskExecutor(@NotNull LockProvider lockProvider) {
        this.lockProvider = requireNonNull(lockProvider);
    }

    @Override
    public void executeWithLock(@NotNull Runnable task, @NotNull LockConfiguration lockConfig) {
        try {
            executeWithLock((Task) task::run, lockConfig);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable throwable) {
            // Should not happen
            throw new IllegalStateException(throwable);
        }
    }

    @Override
    public void executeWithLock(@NotNull Task task, @NotNull LockConfiguration lockConfig) throws Throwable {
        executeWithLock(() -> {
            task.call();
            return null;
        }, lockConfig);
    }

    @Override
    @NotNull
    public <T> TaskResult<T> executeWithLock(@NotNull TaskWithResult<T> task, @NotNull LockConfiguration lockConfig)
        throws Throwable {
        Optional<DistributedLock> lock = lockProvider.lock(lockConfig);
        String lockName = lockConfig.getName();

        if (LockAssert.alreadyLockedBy(lockName)) {
            logger.debug("Already locked '{}'", lockName);
            return TaskResult.result(task.call());
        } else if (lock.isPresent()) {
            try {
                LockAssert.startLock(lockName);
                logger.debug("Locked '{}', lock will be held at most until {}", lockName,
                    lockConfig.getLockMaxTime());
                return TaskResult.result(task.call());
            } finally {
                LockAssert.endLock();
                lock.get().unlock();
                if (logger.isDebugEnabled()) {
                    Instant lockAtLeastUntil = lockConfig.getLockMinTime();
                    Instant now = Instant.now();
                    if (lockAtLeastUntil.isAfter(now)) {
                        logger.debug("Task finished, lock '{}' will be released at {}", lockName, lockAtLeastUntil);
                    } else {
                        logger.debug("Task finished, lock '{}' released", lockName);
                    }
                }
            }
        } else {
            logger.debug("Not executing '{}'. It's locked.", lockName);
            return TaskResult.notExecuted();
        }
    }

}
