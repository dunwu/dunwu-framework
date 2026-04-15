package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Optional;

/**
 * 分布式锁抽象基类，提供锁的有效性校验和解锁/延期的模板方法
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public abstract class BaseDistributedLock implements DistributedLock {

    /** 锁配置 */
    protected final LockConfiguration lockConfiguration;

    /** 锁是否有效 */
    private boolean valid = true;

    /**
     * 构造方法
     *
     * @param lockConfiguration 锁配置
     */
    protected BaseDistributedLock(LockConfiguration lockConfiguration) {
        this.lockConfiguration = lockConfiguration;
    }

    @Override
    public final void unlock() {
        checkValidity();
        doUnlock();
        valid = false;
    }

    /**
     * 执行实际的解锁操作，由子类实现
     */
    protected abstract void doUnlock();

    @Override
    public final Optional<DistributedLock> extend(@NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        checkValidity();
        Optional<DistributedLock> result =
            doExtend(new LockConfiguration(lockConfiguration.getName(), lockMaxTime, lockMinTime));
        valid = false;
        return result;
    }

    /**
     * 执行实际的锁延期操作，子类可重写
     *
     * @param newConfiguration 新的锁配置
     * @return 延期后的分布式锁，若不支持则抛出异常
     */
    protected Optional<DistributedLock> doExtend(LockConfiguration newConfiguration) {
        throw new UnsupportedOperationException();
    }

    /**
     * 检查锁的有效性，若已解锁或延期则抛出异常
     */
    private void checkValidity() {
        if (!valid) {
            throw new IllegalStateException("Lock is not valid, it has already been unlocked or extended");
        }
    }

}
