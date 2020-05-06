package io.github.dunwu.tool.io;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public class AnsiSystemTest {

    @Test
    public void test() {
        AnsiSystem.setEnabled(false);
        AnsiSystem.BOLD_RED.println("abc");
        System.out.println("abc");

        AnsiSystem.setEnabled(true);
        AnsiSystem.RED.print("A");
        AnsiSystem.GREEN.print("B");
        AnsiSystem.BLUE.print("C");
        System.out.println("D");

        AnsiSystem.RED.printf("HELLO \n%s", "World");
        AnsiSystem.BOLD_BLUE.printf("HELLO\n");
    }

}
