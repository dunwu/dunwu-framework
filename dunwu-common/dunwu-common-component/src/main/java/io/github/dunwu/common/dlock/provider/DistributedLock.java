package io.github.dunwu.common.dlock.provider;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Optional;

/**
 * 分布式锁接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public interface DistributedLock {

    /**
     * 解锁。一旦解锁后，不应再对该锁执行任何操作。
     *
     * @throws IllegalStateException 如果锁已被解锁或延期
     */
    void unlock();

    /**
     * 延长锁的持有时间。如果锁可以延期，则返回一个新的锁。调用 extend 后，当前锁不可再执行其他操作。
     * <p>
     * 注意：并非所有 LockProvider 都支持此方法。
     *
     * @param lockMaxTime 锁的最大持有时间
     * @param lockMinTime 锁的最小持有时间
     * @return 新的锁实例，若无法延期则返回空 Optional
     * @throws IllegalStateException         如果锁已被解锁或延期
     * @throws UnsupportedOperationException 如果 LockProvider 不支持锁延期
     */
    @NotNull
    default Optional<DistributedLock> extend(@NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        throw new UnsupportedOperationException();
    }

}
