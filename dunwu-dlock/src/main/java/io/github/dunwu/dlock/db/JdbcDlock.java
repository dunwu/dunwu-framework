package io.github.dunwu.dlock.db;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.github.dunwu.dlock.DLock;
import io.github.dunwu.dlock.db.entity.LockInfo;
import io.github.dunwu.dlock.db.mapper.LockInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
public class JdbcDlock implements DLock {

    private String lockKey;
    private static JdbcDlock instance;
    public static final long TIMEOUT = 3000;

    @Autowired
    private LockInfoMapper lockInfoMapper;

    private JdbcDlock(String lockKey) {
        this.lockKey = lockKey;
    }

    public static JdbcDlock getInstance(String lockKey) {
        return new JdbcDlock(lockKey);
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        long expire = System.currentTimeMillis() + unit.toMillis(time);

        long timeout = TIMEOUT;
        while (timeout >= 0) {
            LockInfo query = new LockInfo();
            query.setKey(lockKey);
            Wrapper<LockInfo> queryWrapper = new QueryWrapper<>();
            LockInfo oldLock = lockInfoMapper.selectOne(queryWrapper);
            LockInfo newLock = new LockInfo();
            newLock.setKey(lockKey);
            newLock.setTime(time);
            newLock.setTimeUnit(unit);
            newLock.setExpire(expire);
            if (oldLock == null) {
                try {
                    if (lockInfoMapper.insert(newLock) == 1) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (oldLock.getExpire() != null && oldLock.getExpire() < System.currentTimeMillis()) {
                    UpdateWrapper<LockInfo> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.setEntity(oldLock);
                    if (lockInfoMapper.update(newLock, updateWrapper) > 0) {
                        return true;
                    }
                }
            }
            timeout -= 100;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
