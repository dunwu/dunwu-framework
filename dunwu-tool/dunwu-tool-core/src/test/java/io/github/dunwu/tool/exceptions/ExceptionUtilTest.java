package io.github.dunwu.tool.exceptions;

import io.github.dunwu.tool.io.IORuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 异常工具单元测试
 *
 * @author looly
 */
public class ExceptionUtilTest {

    @Test
    public void wrapTest() {
        IORuntimeException e = ExceptionUtil.wrap(new IOException(), IORuntimeException.class);
        Assertions.assertNotNull(e);
    }

    @Test
    public void getRootTest() {
        // 查找入口方法
        StackTraceElement ele = ExceptionUtil.getRootStackElement();
        Assertions.assertEquals("main", ele.getMethodName());
    }

    @Test
    public void convertTest() {
        // RuntimeException e = new RuntimeException();
        IOException ioException = new IOException();
        IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        IOException ioException1 =
            ExceptionUtil.convertFromOrSuppressedThrowable(argumentException, IOException.class, true);
        Assertions.assertNotNull(ioException1);
    }

}
