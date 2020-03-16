package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.thread.ThreadUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 控制台单元测试
 *
 * @author Looly
 */
public class ConsoleTest {

    @Test
    public void logTest() {
        Console.log();

        String[] a = { "abc", "bcd", "def" };
        Console.log(a);

        Console.log("This is Console log for {}.", "test");
    }

    @Test
    public void printTest() {
        String[] a = { "abc", "bcd", "def" };
        Console.print(a);

        Console.log("This is Console print for {}.", "test");
    }

    @Test
    public void errorTest() {
        Console.error();

        String[] a = { "abc", "bcd", "def" };
        Console.error(a);

        Console.error("This is Console error for {}.", "test");
    }

    @Test
    @Disabled
    public void inputTest() {
        Console.log("Please input something: ");
        String input = Console.input();
        Console.log(input);
    }

    @Test
    @Disabled
    public void printProgressTest() {
        for (int i = 0; i < 100; i++) {
            Console.printProgress('#', 100, i / 100D);
            ThreadUtil.sleep(200);
        }
    }

}
