package io.github.dunwu.common.dlock.provider;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 锁记录注册表，帮助 LockProvider 跟踪已存在的锁记录，以判断是创建新记录还是更新旧记录
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
class LockRecordRegistry {

    private final Set<String> lockRecords = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    /**
     * 添加锁记录
     *
     * @param lockName 锁名称
     */
    public void addLockRecord(String lockName) {
        lockRecords.add(lockName);
    }

    /**
     * 判断锁记录是否最近已创建
     *
     * @param lockName 锁名称
     * @return 如果锁记录存在返回 true，否则返回 false
     */
    public boolean lockRecordRecentlyCreated(String lockName) {
        return lockRecords.contains(lockName);
    }

    /**
     * 清除所有锁记录
     */
    public void clear() {
        lockRecords.clear();
    }

    /**
     * 获取锁记录数量
     *
     * @return 锁记录数量
     */
    int getSize() {
        return lockRecords.size();
    }

}
