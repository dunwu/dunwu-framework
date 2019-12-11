package io.github.dunwu.util.code;

import io.github.dunwu.util.code.support.SnowFlakeId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @see IdUtil
 */
public class IdUtilTest {

    public static final int COUNT = 100000;

    @Test
    void randomUuid() {
        long begin = System.currentTimeMillis();
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < COUNT; i++) {
            String id = IdUtil.randomUuid();
            ids.add(id);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin));
        Assertions.assertEquals(ids.size(), COUNT);
    }

    @Test
    void randomUuid2() {
        long begin = System.currentTimeMillis();
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < COUNT; i++) {
            String id = IdUtil.randomUuid2();
            ids.add(id);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin));
        Assertions.assertEquals(ids.size(), COUNT);
    }

    @Test
    void testSnowFlakeId() {
        SnowFlakeId snowFlakeId = IdUtil.newSnowFlakeId(2, 3);
        long begin = System.currentTimeMillis();
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < COUNT; i++) {
            set.add(snowFlakeId.generate());
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin));
        Assertions.assertEquals(set.size(), COUNT);
        set.forEach(item -> {
            System.out.println(item.toString());
        });
    }

}
