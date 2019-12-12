package io.github.dunwu.tool.util;

import io.github.dunwu.tool.lang.Console;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 命令行单元测试
 *
 * @author looly
 */
public class RuntimeUtilTest {

    @Test
    @Disabled
    public void execTest() {
        String str = RuntimeUtil.execForStr("ipconfig");
        Console.log(str);
    }

    @Test
    @Disabled
    public void execCmdTest() {
        String str = RuntimeUtil.execForStr("cmd /c dir");
        Console.log(str);
    }

}
