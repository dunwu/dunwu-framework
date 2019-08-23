package io.github.dunwu.dlock;

import io.github.dunwu.dlock.redis.RedisLock;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
public class RedisLockTest {

    @Test
    public void test() {
        //定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 1, TimeUnit.SECONDS, new SynchronousQueue<>());

        //添加10个线程获取锁
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    Jedis jedis = new Jedis("localhost", 6379);
                    RedisLock lock = new RedisLock(jedis, UUID.randomUUID().toString());
                    boolean locked = lock.tryLock(5, TimeUnit.SECONDS);
                    if (locked) {
                        System.out.println("线程id:" + Thread.currentThread().getId() + "加锁成功!时间:" + LocalTime.now());
                    }
                    lock.sleepBySencond(5);
//                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        //当线程池中的线程数为0时，退出
        while (pool.getPoolSize() != 0) {}
    }
}
