package io.github.dunwu.common.dlock.manager;

import io.github.dunwu.common.dlock.LockAssert;
import io.github.dunwu.common.dlock.LockConfiguration;
import io.github.dunwu.common.dlock.provider.DistributedLock;
import io.github.dunwu.common.dlock.provider.LockProvider;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * {@link LockingTaskExecutor} 的默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DefaultLockingTaskExecutor implements LockingTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLockingTaskExecutor.class);

    @NotNull
    private final LockProvider lockProvider;

    /**
     * 构造方法
     *
     * @param lockProvider 锁提供者
     */
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
