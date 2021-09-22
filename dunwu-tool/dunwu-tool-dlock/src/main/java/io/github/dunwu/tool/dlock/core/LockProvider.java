package io.github.dunwu.tool.dlock.core;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分布式锁接口
 */
public interface LockProvider {

    /**
     * @param name        锁名称
     * @param lockMaxTime 锁定最长时间
     * @return 如果获取成功，返回持有的锁；失败则返回空。其中，{@link DistributedLock} 包含了释放锁的方法
     */
    default Optional<DistributedLock> lock(@NotBlank String name, Instant lockMaxTime) {
        LockConfiguration lockConfiguration = new LockConfiguration(name, lockMaxTime, Instant.now());
        return lock(lockConfiguration);
    }

    /**
     * @param lockConfiguration 锁的配置信息，{@link LockConfiguration}
     * @return 如果获取成功，返回持有的锁；失败则返回空。其中，{@link DistributedLock} 包含了释放锁的方法
     */
    @NotNull
    Optional<DistributedLock> lock(@NotNull LockConfiguration lockConfiguration);

    /**
     * @param name        锁名称
     * @param lockMaxTime 锁定最长时间
     * @param unit        时间单位
     * @return 如果获取成功，返回持有的锁；失败则返回空。其中，{@link DistributedLock} 包含了释放锁的方法
     */
    default Optional<DistributedLock> lock(@NotBlank String name, long lockMaxTime, ChronoUnit unit) {
        Instant maxTime = Instant.now().plus(lockMaxTime, unit);
        LockConfiguration lockConfiguration = new LockConfiguration(name, maxTime, Instant.now());
        return lock(lockConfiguration);
    }

}
