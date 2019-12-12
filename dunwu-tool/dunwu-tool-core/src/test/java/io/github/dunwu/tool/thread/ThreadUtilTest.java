package io.github.dunwu.tool.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadUtilTest {

    @Test
    public void executeTest() {
        final boolean isValid = true;

        ThreadUtil.execute(new Runnable() {

            @Override
            public void run() {
                Assertions.assertTrue(isValid);
            }
        });
    }

}
