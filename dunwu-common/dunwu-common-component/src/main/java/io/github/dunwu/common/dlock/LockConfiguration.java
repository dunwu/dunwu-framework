package io.github.dunwu.common.dlock;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;

/**
 * 分布式锁配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class LockConfiguration {

    /** 锁名称 */
    private final String name;

    /**
     * 锁的最大持有时间。超过此时间后锁将自动释放（持有锁的进程很可能已异常退出）。
     * 能够检测死进程的提供者（如 Zookeeper）可以忽略此字段。
     */
    private final Instant lockMaxTime;

    /**
     * 锁的最小持有时间。即使持有锁的任务提前完成，锁也会保持到此时间。
     */
    private final Instant lockMinTime;

    /**
     * 构造方法，lockMinTime 默认为当前时间
     *
     * @param name        锁名称
     * @param lockMaxTime 锁的最大持有时间
     */
    public LockConfiguration(@NotNull String name, @NotNull Instant lockMaxTime) {
        this(name, lockMaxTime, Instant.now());
    }

    /**
     * 构造方法
     *
     * @param name        锁名称
     * @param lockMaxTime 锁的最大持有时间
     * @param lockMinTime 锁的最小持有时间
     */
    public LockConfiguration(@NotNull String name, @NotNull Instant lockMaxTime, @NotNull Instant lockMinTime) {
        this.name = Objects.requireNonNull(name);
        this.lockMaxTime = Objects.requireNonNull(lockMaxTime);
        this.lockMinTime = Objects.requireNonNull(lockMinTime);
        if (lockMinTime.isAfter(lockMaxTime)) {
            throw new IllegalArgumentException("lockAtMostUntil is before lockAtLeastUntil for lock '" + name + "'.");
        }
        if (lockMaxTime.isBefore(Instant.now())) {
            throw new IllegalArgumentException("lockAtMostUntil is in the past for lock '" + name + "'.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("lock name can not be empty");
        }
    }

    @Override
    public String toString() {
        return "LockConfiguration{" +
            "name='" + name + '\'' +
            ", lockAtMostUntil=" + lockMaxTime +
            ", lockAtLeastUntil=" + lockMinTime +
            '}';
    }

    /**
     * 获取锁名称
     *
     * @return 锁名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取锁的最大持有时间
     *
     * @return 锁的最大持有时间
     */
    public Instant getLockMaxTime() {
        return lockMaxTime;
    }

    /**
     * 获取锁的最小持有时间
     *
     * @return 锁的最小持有时间
     */
    public Instant getLockMinTime() {
        return lockMinTime;
    }

    /**
     * 获取解锁时间，返回当前时间和最小持有时间中较晚的那个
     *
     * @return 解锁时间
     */
    public Instant getUnlockTime() {
        Instant now = Instant.now();
        return lockMinTime.isAfter(now) ? lockMinTime : now;
    }

}
