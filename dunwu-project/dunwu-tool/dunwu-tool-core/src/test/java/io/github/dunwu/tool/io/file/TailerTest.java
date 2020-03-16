package io.github.dunwu.tool.io.file;

import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.util.CharsetUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TailerTest {

    @Test
    @Disabled
    public void tailTest() {
        FileUtil.tail(FileUtil.file("e:/tail.txt"), CharsetUtil.CHARSET_GBK);
    }

    @Test
    @Disabled
    public void tailWithLinesTest() {
        Tailer tailer = new Tailer(FileUtil.file("f:/test/test.log"), Tailer.CONSOLE_HANDLER, 2);
        tailer.start();
    }

}
