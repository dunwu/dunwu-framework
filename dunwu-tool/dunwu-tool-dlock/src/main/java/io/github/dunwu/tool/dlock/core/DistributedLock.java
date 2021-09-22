package io.github.dunwu.tool.dlock.core;

import java.time.Instant;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface DistributedLock {

    /**
     * Unlocks the lock. Once you unlock it, you should not use for any other operation.
     *
     * @throws IllegalStateException if the lock has already been unlocked or extended
     */
    void unlock();

    /**
     * Extends the lock. If the lock can be extended a new lock is returned. After calling extend, no other operation
     * can be called on current lock.
     * <p>
     * This method is NOT supported by all lock providers.
     *
     * @return a new lock or empty optional if the lock can not be extended
     * @throws IllegalStateException         if the lock has already been unlocked or extended
     * @throws UnsupportedOperationException if the lock extension is not supported by LockProvider.
     */
    @NotNull
    default Optional<DistributedLock> extend(@NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        throw new UnsupportedOperationException();
    }

}
