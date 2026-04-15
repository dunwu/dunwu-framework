package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;

/**
 * 存储访问器接口，用于操作锁记录的存储
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public interface StorageAccessor {

    /**
     * 插入一条锁记录。如果记录已存在，则返回 false。
     *
     * @param lockConfiguration 锁配置
     * @return 如果插入成功返回 true，否则返回 false
     */
    boolean insertRecord(@NotNull LockConfiguration lockConfiguration);

    /**
     * 尝试更新锁记录。如果已存在有效的锁记录（锁被其他进程持有），则不做任何操作并返回 false。
     *
     * @param lockConfiguration 锁配置
     * @return 如果更新成功返回 true，否则返回 false
     */
    boolean updateRecord(@NotNull LockConfiguration lockConfiguration);

    /**
     * 解锁指定的锁
     *
     * @param lockConfiguration 锁配置
     */
    void unlock(@NotNull LockConfiguration lockConfiguration);

    /**
     * 延长锁的持有时间
     *
     * @param lockConfiguration 锁配置
     * @return 如果延期成功返回 true，否则返回 false
     * @throws UnsupportedOperationException 如果不支持锁延期
     */
    default boolean extend(@NotNull LockConfiguration lockConfiguration) {
        throw new UnsupportedOperationException();
    }

}
