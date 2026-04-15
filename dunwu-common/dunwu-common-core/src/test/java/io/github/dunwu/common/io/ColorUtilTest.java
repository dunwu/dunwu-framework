package io.github.dunwu.common.io;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public class ColorUtilTest {

    @Test
    public void test() {
        ColorUtil.setEnabled(false);
        ColorUtil.BOLD_RED.println("abc");
        System.out.println("abc");

        ColorUtil.setEnabled(true);
        ColorUtil.RED.print("A");
        ColorUtil.GREEN.print("B");
        ColorUtil.BLUE.print("C");
        System.out.println("D");

        ColorUtil.RED.printf("HELLO \n%s", "World");
        ColorUtil.BOLD_BLUE.printf("HELLO\n");
    }

}
