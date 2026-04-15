package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * 基于抽象存储的分布式锁提供者
 * <p>
 * 使用一个包含 ID（锁名称）和 locked_until 字段的表/集合。
 * <ol>
 * <li>尝试插入新的锁记录。作为优化，在内存中跟踪已创建的锁记录。如果记录已插入，则返回锁。</li>
 * <li>尝试使用 ID == name AND lock_until &lt;= now 条件更新锁记录。</li>
 * <li>如果更新成功（1 行被更新），则获取到锁。如果更新失败（0 行被更新），则其他进程持有锁。</li>
 * <li>解锁时，将 lock_until 设置为当前时间。</li>
 * </ol>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class StorageBasedLockProvider implements LockProvider {

    @NotNull
    private final StorageAccessor storageAccessor;

    private final LockRecordRegistry lockRecordRegistry = new LockRecordRegistry();

    /**
     * 构造方法
     *
     * @param storageAccessor 存储访问器
     */
    protected StorageBasedLockProvider(@NotNull StorageAccessor storageAccessor) {
        this.storageAccessor = storageAccessor;
    }

    @Override
    @NotNull
    public Optional<DistributedLock> lock(@NotNull LockConfiguration lockConfiguration) {
        boolean lockObtained = doLock(lockConfiguration);
        if (lockObtained) {
            return Optional.of(new StorageLock(lockConfiguration, storageAccessor));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 根据锁配置尝试获取锁，当 lockUntil &lt;= now 时设置新的 lockUntil
     *
     * @param lockConfiguration 锁配置
     * @return 如果获取锁成功返回 true，否则返回 false
     */
    protected boolean doLock(LockConfiguration lockConfiguration) {
        String name = lockConfiguration.getName();

        if (!lockRecordRegistry.lockRecordRecentlyCreated(name)) {
            // create record in case it does not exist yet
            if (storageAccessor.insertRecord(lockConfiguration)) {
                lockRecordRegistry.addLockRecord(name);
                // we were able to create the record, we have the lock
                return true;
            }
            // we were not able to create the record, it already exists, let's put it to the cache so we do not try again
            lockRecordRegistry.addLockRecord(name);
        }

        // let's try to update the record, if successful, we have the lock
        return storageAccessor.updateRecord(lockConfiguration);
    }

    /**
     * 清除已存在锁记录的缓存
     */
    public void clearCache() {
        lockRecordRegistry.clear();
    }

    /**
     * 基于存储的分布式锁实现
     */
    private static class StorageLock extends BaseDistributedLock {

        private final StorageAccessor storageAccessor;

        /**
         * 构造方法
         *
         * @param lockConfiguration 锁配置
         * @param storageAccessor   存储访问器
         */
        StorageLock(LockConfiguration lockConfiguration, StorageAccessor storageAccessor) {
            super(lockConfiguration);
            this.storageAccessor = storageAccessor;
        }

        @Override
        public void doUnlock() {
            storageAccessor.unlock(lockConfiguration);
        }

        @Override
        public Optional<DistributedLock> doExtend(LockConfiguration newConfig) {
            if (storageAccessor.extend(newConfig)) {
                return Optional.of(new StorageLock(newConfig, storageAccessor));
            } else {
                return Optional.empty();
            }
        }

    }

}
