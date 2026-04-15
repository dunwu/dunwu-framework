package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * 分布式锁提供者接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
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
