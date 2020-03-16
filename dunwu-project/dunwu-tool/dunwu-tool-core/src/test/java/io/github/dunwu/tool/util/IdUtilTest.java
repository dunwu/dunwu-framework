package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.ConcurrentHashSet;
import io.github.dunwu.tool.date.DateUtil;
import io.github.dunwu.tool.date.TimeInterval;
import io.github.dunwu.tool.exceptions.UtilException;
import io.github.dunwu.tool.lang.Console;
import io.github.dunwu.tool.lang.Snowflake;
import io.github.dunwu.tool.thread.ThreadUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * {@link IdUtil} 单元测试
 *
 * @author looly
 */
public class IdUtilTest {

    @Test
    public void randomUUIDTest() {
        String simpleUUID = IdUtil.simpleUUID();
        Assertions.assertEquals(32, simpleUUID.length());

        String randomUUID = IdUtil.randomUUID();
        Assertions.assertEquals(36, randomUUID.length());
    }

    @Test
    public void fastUUIDTest() {
        String simpleUUID = IdUtil.fastSimpleUUID();
        Assertions.assertEquals(32, simpleUUID.length());

        String randomUUID = IdUtil.fastUUID();
        Assertions.assertEquals(36, randomUUID.length());
    }

    /**
     * UUID的性能测试
     */
    @Test
    @Disabled
    public void benchTest() {
        TimeInterval timer = DateUtil.timer();
        for (int i = 0; i < 1000000; i++) {
            IdUtil.simpleUUID();
        }
        Console.log(timer.interval());

        timer.restart();
        for (int i = 0; i < 1000000; i++) {
            UUID.randomUUID().toString().replace("-", "");
        }
        Console.log(timer.interval());
    }

    @Test
    public void objectIdTest() {
        String id = IdUtil.objectId();
        Assertions.assertEquals(24, id.length());
    }

    @Test
    public void createSnowflakeTest() {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        Assertions.assertTrue(id > 0);
    }

    @Test
    public void snowflakeBenchTest() {
        final Set<Long> set = new ConcurrentHashSet<>();
        final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

        //线程数
        int threadCount = 100;
        //每个线程生成的ID数
        final int idCountPerThread = 10000;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadUtil.execute(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < idCountPerThread; i++) {
                        long id = snowflake.nextId();
                        set.add(id);
                        //						Console.log("Add new id: {}", id);
                    }
                    latch.countDown();
                }
            });
        }

        //等待全部线程结束
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new UtilException(e);
        }
        Assertions.assertEquals(threadCount * idCountPerThread, set.size());
    }

    @Test
    public void snowflakeBenchTest2() {
        final Set<Long> set = new ConcurrentHashSet<>();

        //线程数
        int threadCount = 100;
        //每个线程生成的ID数
        final int idCountPerThread = 10000;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadUtil.execute(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < idCountPerThread; i++) {
                        long id = IdUtil.getSnowflake(1, 1).nextId();
                        set.add(id);
                        //						Console.log("Add new id: {}", id);
                    }
                    latch.countDown();
                }
            });
        }

        //等待全部线程结束
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new UtilException(e);
        }
        Assertions.assertEquals(threadCount * idCountPerThread, set.size());
    }

}
