package io.github.dunwu.tool.swing;

import io.github.dunwu.tool.lang.Console;
import io.github.dunwu.tool.swing.clipboard.ClipboardListener;
import io.github.dunwu.tool.swing.clipboard.ClipboardUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;

public class ClipboardMonitorTest {

    @Test
    @Disabled
    public void monitorTest() {
        // 第一个监听
        ClipboardUtil.listen(new ClipboardListener() {

            @Override
            public Transferable onChange(Clipboard clipboard, Transferable contents) {
                Object object = ClipboardUtil.getStr(contents);
                Console.log("1# {}", object);
                return contents;
            }
        }, false);

        // 第二个监听
        ClipboardUtil.listen(new ClipboardListener() {

            @Override
            public Transferable onChange(Clipboard clipboard, Transferable contents) {
                Object object = ClipboardUtil.getStr(contents);
                Console.log("2# {}", object);
                return contents;
            }
        });
    }

}
