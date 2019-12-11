package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.SystemExtUtil;
import io.github.dunwu.util.base.ObjectExtUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadUtilTest {

    @Test
    public void testCaller() {
        hello();
        new MyClass().hello();
        assertThat(SystemExtUtil.getCurrentClass())
            .isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
        assertThat(SystemExtUtil.getCurrentMethod()).isEqualTo(
            "io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
    }

    private void hello() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        System.out.println(ObjectExtUtil.toPrettyString(stacktrace));

        assertThat(SystemExtUtil.getCallerClass())
            .isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
        assertThat(SystemExtUtil.getCallerMethod()).isEqualTo(
            "io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
    }

    public static class MyClass {

        public void hello() {
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            System.out.println(ObjectExtUtil.toPrettyString(stacktrace));

            assertThat(SystemExtUtil.getCallerClass())
                .isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
            assertThat(SystemExtUtil.getCallerMethod()).isEqualTo(
                "io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
        }

    }

}
