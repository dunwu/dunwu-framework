package io.github.dunwu.tool.swing;

import io.github.dunwu.tool.swing.clipboard.ClipboardUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 剪贴板工具类单元测试
 *
 * @author looly
 */
public class ClipboardUtilTest {

    @Test
    public void setAndGetStrTest() {
        try {
            ClipboardUtil.setStr("test");

            String test = ClipboardUtil.getStr();
            Assertions.assertEquals("test", test);
        } catch (java.awt.HeadlessException e) {
            // 忽略 No X11 DISPLAY variable was set, but this program performed an operation which requires it.
            // ignore
        }
    }

}
