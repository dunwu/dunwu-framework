package io.github.dunwu.tool.thread;

import io.github.dunwu.tool.lang.Console;
import io.github.dunwu.tool.util.RandomUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ConcurrencyTesterTest {

    @Test
    @Disabled
    public void concurrencyTesterTest() {
        ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, new Runnable() {

            @Override
            public void run() {
                long delay = RandomUtil.randomLong(100, 1000);
                ThreadUtil.sleep(delay);
                Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
            }
        });
        Console.log(tester.getInterval());
    }

}
