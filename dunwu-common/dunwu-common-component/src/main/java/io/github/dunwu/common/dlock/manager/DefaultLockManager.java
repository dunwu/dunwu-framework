package io.github.dunwu.common.dlock.manager;

import io.github.dunwu.common.dlock.LockConfiguration;
import io.github.dunwu.common.dlock.provider.LockProvider;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * {@link LockManager} 的默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DefaultLockManager implements LockManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLockManager.class);

    private final LockingTaskExecutor lockingTaskExecutor;

    private final LockConfigurationExtractor lockConfigurationExtractor;

    /**
     * 根据 {@link LockProvider} 和 {@link LockConfigurationExtractor} 构造锁管理器
     *
     * @param lockProvider                 锁提供者
     * @param lockConfigurationExtractor   锁配置提取器
     */
    public DefaultLockManager(@NotNull LockProvider lockProvider,
        @NotNull LockConfigurationExtractor lockConfigurationExtractor) {
        this(new DefaultLockingTaskExecutor(lockProvider), lockConfigurationExtractor);
    }

    /**
     * 根据 {@link LockingTaskExecutor} 和 {@link LockConfigurationExtractor} 构造锁管理器
     *
     * @param lockingTaskExecutor          加锁任务执行器
     * @param lockConfigurationExtractor   锁配置提取器
     */
    public DefaultLockManager(@NotNull LockingTaskExecutor lockingTaskExecutor,
        @NotNull LockConfigurationExtractor lockConfigurationExtractor) {
        this.lockingTaskExecutor = requireNonNull(lockingTaskExecutor);
        this.lockConfigurationExtractor = requireNonNull(lockConfigurationExtractor);
    }

    @Override
    public void executeWithLock(@NotNull Runnable task) {
        Optional<LockConfiguration> lockConfigOptional = lockConfigurationExtractor.getLockConfiguration(task);
        if (!lockConfigOptional.isPresent()) {
            logger.debug("No lock configuration for {}. Executing without lock.", task);
            task.run();
        } else {
            lockingTaskExecutor.executeWithLock(task, lockConfigOptional.get());
        }
    }

}
