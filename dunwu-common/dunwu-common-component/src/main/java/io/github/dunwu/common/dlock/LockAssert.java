package io.github.dunwu.common.dlock;

import jakarta.validation.constraints.NotNull;

/**
 * 锁断言工具，用于确保当前任务确实持有分布式锁
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class LockAssert {

    private static final ThreadLocal<String> CURRENT_LOCK_NAME = ThreadLocal.withInitial(() -> null);

    /**
     * 断言当前线程已持有锁，若未持有则抛出异常
     *
     * @throws IllegalStateException 如果当前线程未持有锁
     */
    public static void assertLocked() {
        if (CURRENT_LOCK_NAME.get() == null) {
            throw new IllegalStateException("The task is not locked.");
        }
    }

    /**
     * 记录当前线程开始持有指定名称的锁
     *
     * @param name 锁名称
     */
    public static void startLock(String name) {
        CURRENT_LOCK_NAME.set(name);
    }

    /**
     * 判断当前线程是否已持有指定名称的锁
     *
     * @param name 锁名称
     * @return 如果已持有返回 true，否则返回 false
     */
    public static boolean alreadyLockedBy(@NotNull String name) {
        return name.equals(CURRENT_LOCK_NAME.get());
    }

    /**
     * 清除当前线程的锁记录
     */
    public static void endLock() {
        CURRENT_LOCK_NAME.remove();
    }

}
