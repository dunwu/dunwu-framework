package io.github.dunwu.dlock.redis;

import io.github.dunwu.dlock.DLock;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
public class RedisLock implements DLock {


    protected Jedis jedis;
    protected String lockKey;

    public RedisLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
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
            String lockValue = String.valueOf(expire);
            if (jedis.setnx(lockKey, lockValue) == 1) {
                jedis.expire(lockKey, (int) expire);
                return true;
            }

            String current = jedis.get(lockKey);
            if (lessCurrentTimeMillis(current)) {
                String oldValue = jedis.getSet(lockKey, lockValue);
                if (oldValue != null && oldValue.equals(current)) {
                    jedis.expire(lockKey, (int) expire);
                    return true;
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
        jedis.del(lockKey);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public void sleepBySencond(int sencond) {
        try {
            Thread.sleep(sencond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean lessCurrentTimeMillis(String current) {
        if (current == null) {
            return false;
        }
        String[] currs = current.split(",");
        return Long.parseLong(currs[0]) < System.currentTimeMillis();
    }
}
