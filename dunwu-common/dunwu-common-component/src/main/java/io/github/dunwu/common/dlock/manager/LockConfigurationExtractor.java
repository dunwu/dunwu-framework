package io.github.dunwu.common.dlock.manager;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * 锁配置提取器接口，从任务中提取锁参数
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public interface LockConfigurationExtractor {

    /**
     * 从任务中提取锁配置
     *
     * @param task 待执行的任务
     * @return 锁配置，若任务不需要加锁则返回空 Optional
     */
    @NotNull
    Optional<LockConfiguration> getLockConfiguration(@NotNull Runnable task);

}
