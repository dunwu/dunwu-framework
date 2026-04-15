package io.github.dunwu.common.dlock.manager;

import jakarta.validation.constraints.NotNull;

/**
 * 锁管理器接口，在未被锁定时执行任务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public interface LockManager {

    /**
     * 使用分布式锁执行任务
     *
     * @param task 待执行的任务
     */
    void executeWithLock(@NotNull Runnable task);

}
