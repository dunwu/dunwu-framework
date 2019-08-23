package io.github.dunwu.dlock;

import io.github.dunwu.dlock.db.JdbcDlock;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
public class Locks {

    public static DLock getDLock(LockType lockType) {
        if (lockType == LockType.DB_LOCK) {
            return JdbcDlock.getInstance("asdfhglaksh");
        }
        return null;
    }
}
