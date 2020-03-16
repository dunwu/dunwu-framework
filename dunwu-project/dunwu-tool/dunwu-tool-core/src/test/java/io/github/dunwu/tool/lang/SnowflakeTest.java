package io.github.dunwu.tool.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

/**
 * Snowflake单元测试
 *
 * @author Looly
 */
public class SnowflakeTest {

    @Test
    public void snowflakeTest1() {
        //构建Snowflake，提供终端ID和数据中心ID
        Snowflake idWorker = new Snowflake(0, 0);
        long nextId = idWorker.nextId();
        Assertions.assertTrue(nextId > 0);
    }

    @Test
    public void snowflakeTest() {
        HashSet<Long> hashSet = new HashSet<>();

        //构建Snowflake，提供终端ID和数据中心ID
        Snowflake idWorker = new Snowflake(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            hashSet.add(id);
        }
        Assertions.assertEquals(1000L, hashSet.size());
    }

    @Test
    public void snowflakeGetTest() {
        //构建Snowflake，提供终端ID和数据中心ID
        Snowflake idWorker = new Snowflake(1, 2);
        long nextId = idWorker.nextId();

        Assertions.assertEquals(1, idWorker.getWorkerId(nextId));
        Assertions.assertEquals(2, idWorker.getDataCenterId(nextId));
        Assertions.assertTrue(idWorker.getGenerateDateTime(nextId) - System.currentTimeMillis() < 10);
    }

}
