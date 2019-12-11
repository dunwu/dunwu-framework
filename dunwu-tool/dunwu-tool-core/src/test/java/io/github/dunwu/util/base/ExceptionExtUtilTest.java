package io.github.dunwu.util.base;

import io.github.dunwu.util.base.type.CloneableException;
import io.github.dunwu.util.base.type.CloneableRuntimeException;
import io.github.dunwu.util.base.type.UncheckedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ExceptionExtUtilTest {

    private static RuntimeException TIMEOUT_EXCEPTION = ExceptionExtUtil.setStackTrace(
        new RuntimeException("Timeout"), ExceptionExtUtilTest.class, "hello");

    private static CloneableException TIMEOUT_EXCEPTION2 = new CloneableException(
        "Timeout").setStackTrace(ExceptionExtUtilTest.class, "hello");

    private static CloneableRuntimeException TIMEOUT_EXCEPTION3 = new CloneableRuntimeException(
        "Timeout").setStackTrace(ExceptionExtUtilTest.class, "hello");

    @Test
    public void buildMessage() {
        IOException ioexception = new IOException("my exception");
        assertThat(ExceptionExtUtil.getMessage(ioexception))
            .isEqualTo("IOException: my exception");
        assertThat(ExceptionExtUtil.getMessage(null)).isEqualTo("");

        RuntimeException runtimeExcetpion = new RuntimeException("my runtimeException",
            ioexception);
        assertThat(ExceptionExtUtil.toStringWithRootCause(runtimeExcetpion)).isEqualTo(
            "RuntimeException: my runtimeException; <---IOException: my exception");

        assertThat(ExceptionExtUtil.toStringWithRootCause(null)).isEqualTo("");
        // 无cause
        assertThat(ExceptionExtUtil.toStringWithRootCause(ioexception))
            .isEqualTo("IOException: my exception");
    }

    @Test
    public void cause() {
        IOException ioexception = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(
            ioexception);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(ExceptionExtUtil.isCausedBy(runtimeException, IOException.class))
            .isTrue();
        assertThat(ExceptionExtUtil.isCausedBy(runtimeException,
            IllegalStateException.class, IOException.class)).isTrue();
        assertThat(ExceptionExtUtil.isCausedBy(runtimeException, Exception.class))
            .isTrue();
        assertThat(ExceptionExtUtil.isCausedBy(runtimeException,
            IllegalAccessException.class)).isFalse();

        assertThat(ExceptionExtUtil.findCause(runtimeException,
            IllegalStateException.class)).isSameAs(illegalStateException);
        assertThat(ExceptionExtUtil.findCause(runtimeException, IOException.class))
            .isSameAs(ioexception);
        assertThat(
            ExceptionExtUtil.findCause(runtimeException, UncheckedException.class))
            .isNull();
    }

    @Test
    public void clearStackTrace() {
        IOException ioexception = new IOException("my exception");
        RuntimeException runtimeException = new RuntimeException(ioexception);

        System.out.println(ExceptionExtUtil
            .stackTraceText(ExceptionExtUtil.clearStackTrace(runtimeException)));
    }

    @Test
    public void getRootCause() {
        IOException ioexception = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(
            ioexception);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(ExceptionExtUtil.getRootCause(runtimeException))
            .isSameAs(ioexception);
        // 无cause
        assertThat(ExceptionExtUtil.getRootCause(ioexception)).isSameAs(ioexception);
    }

    @Test
    public void getStackTraceAsString() {
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = new RuntimeException(exception);

        String stack = ExceptionExtUtil.stackTraceText(runtimeException);
        System.out.println(stack);
    }

    @Test
    public void staticException() {
        assertThat(ExceptionExtUtil.stackTraceText(TIMEOUT_EXCEPTION)).hasLineCount(2)
            .contains("java.lang.RuntimeException: Timeout").contains(
            "at io.github.dunwu.utils.base.ExceptionUtilTest.hello(Unknown Source)");

        assertThat(ExceptionExtUtil.stackTraceText(TIMEOUT_EXCEPTION2)).hasLineCount(2)
            .contains("io.github.dunwu.utils.base.type.CloneableException: Timeout")
            .contains(
                "at io.github.dunwu.utils.base.ExceptionUtilTest.hello(Unknown Source)");

        CloneableException timeoutException = TIMEOUT_EXCEPTION2
            .clone("Timeout for 30ms");
        assertThat(ExceptionExtUtil.stackTraceText(timeoutException)).hasLineCount(2)
            .contains(
                "io.github.dunwu.utils.base.type.CloneableException: Timeout for 30ms")
            .contains(
                "at io.github.dunwu.utils.base.ExceptionUtilTest.hello(Unknown Source)");

        assertThat(ExceptionExtUtil.stackTraceText(TIMEOUT_EXCEPTION3)).hasLineCount(2)
            .contains(
                "io.github.dunwu.utils.base.type.CloneableRuntimeException: Timeout")
            .contains(
                "at io.github.dunwu.utils.base.ExceptionUtilTest.hello(Unknown Source)");

        CloneableRuntimeException timeoutRuntimeException = TIMEOUT_EXCEPTION3
            .clone("Timeout for 40ms");
        assertThat(ExceptionExtUtil.stackTraceText(timeoutRuntimeException))
            .hasLineCount(2)
            .contains(
                "io.github.dunwu.utils.base.type.CloneableRuntimeException: Timeout for 40ms")
            .contains(
                "at io.github.dunwu.utils.base.ExceptionUtilTest.hello(Unknown Source)");
    }

    @Test
    public void unchecked() {
        // convert Exception to RuntimeException with cause
        Exception exception = new Exception("my exception");
        try {
            ExceptionExtUtil.unchecked(exception);
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t.getCause()).isSameAs(exception);
        }

        // do nothing of Error
        Error error = new LinkageError();
        try {
            ExceptionExtUtil.unchecked(error);
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isSameAs(error);
        }

        // do nothing of RuntimeException
        RuntimeException runtimeException = new RuntimeException("haha");
        try {
            ExceptionExtUtil.unchecked(runtimeException);
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isSameAs(runtimeException);
        }
    }

    @Test
    public void unwrap() {
        RuntimeException re = new RuntimeException("my runtime");
        assertThat(ExceptionExtUtil.unwrap(re)).isSameAs(re);

        ExecutionException ee = new ExecutionException(re);
        assertThat(ExceptionExtUtil.unwrap(ee)).isSameAs(re);

        InvocationTargetException ie = new InvocationTargetException(re);
        assertThat(ExceptionExtUtil.unwrap(ie)).isSameAs(re);

        Exception e = new Exception("my exception");
        ExecutionException ee2 = new ExecutionException(e);
        try {
            ExceptionExtUtil.unwrapAndUnchecked(ee2);
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UncheckedException.class)
                .hasCauseExactlyInstanceOf(Exception.class);
        }
    }

}
