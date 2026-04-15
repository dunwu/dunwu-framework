package io.github.dunwu.common.dlock;

import io.github.dunwu.common.dlock.manager.LockManager;
import jakarta.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * 可加锁的 Runnable 包装器，使用 {@link LockManager#executeWithLock(Runnable)} 执行任务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class LockableRunnable implements Runnable {

    private final Runnable task;

    private final LockManager lockManager;

    /**
     * 构造方法
     *
     * @param task        待执行的任务
     * @param lockManager 锁管理器
     */
    public LockableRunnable(@NotNull Runnable task, @NotNull LockManager lockManager) {
        this.task = requireNonNull(task);
        this.lockManager = requireNonNull(lockManager);
    }

    @Override
    public void run() {
        lockManager.executeWithLock(task);
    }

}
