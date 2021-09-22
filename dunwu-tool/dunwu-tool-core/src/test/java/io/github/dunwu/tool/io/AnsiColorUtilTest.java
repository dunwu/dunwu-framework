package io.github.dunwu.tool.io;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public class AnsiColorUtilTest {

    @Test
    public void test() {
        AnsiColorUtil.setEnabled(false);
        AnsiColorUtil.BOLD_RED.println("abc");
        System.out.println("abc");

        AnsiColorUtil.setEnabled(true);
        AnsiColorUtil.RED.print("A");
        AnsiColorUtil.GREEN.print("B");
        AnsiColorUtil.BLUE.print("C");
        System.out.println("D");

        AnsiColorUtil.RED.printf("HELLO \n%s", "World");
        AnsiColorUtil.BOLD_BLUE.printf("HELLO\n");
    }

}
