package io.github.dunwu.util.io;

import io.github.dunwu.bean.TestBean;
import io.github.dunwu.bean.TestBeanUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-26
 */
class SerializeUtilTest {

    private static final int BATCH_SIZE = 100000;

    @Test
    void testWriteReadBytes() throws IOException {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            TestBean oldBean = TestBeanUtils.initJdk8Bean();
            byte[] bytes = SerializeUtil.writeToBytes(oldBean);
            assertThat(bytes).isNotEmpty();
            TestBean newBean = SerializeUtil.readFromBytes(bytes, TestBean.class);
            assertThat(newBean).isNotNull();
        }
        long end = System.currentTimeMillis();
        System.out.printf("FST 序列化/反序列化耗时：%s", (end - begin));
    }

    @Test
    void testWriteReadString() throws IOException {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            TestBean oldBean = TestBeanUtils.initJdk8Bean();
            String text = SerializeUtil.writeToString(oldBean);
            assertThat(text).isNotEmpty();
            TestBean newBean = SerializeUtil.readFromString(text, TestBean.class);
            assertThat(newBean).isNotNull();
        }
        long end = System.currentTimeMillis();
        System.out.printf("FST 序列化/反序列化耗时：%s", (end - begin));
    }

}
