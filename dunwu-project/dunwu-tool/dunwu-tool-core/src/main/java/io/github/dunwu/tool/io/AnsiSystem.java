package io.github.dunwu.tool.io;

import io.github.dunwu.tool.io.ansi.AnsiColor;
import io.github.dunwu.tool.io.ansi.AnsiElement;
import io.github.dunwu.tool.io.ansi.AnsiStyle;

/**
 * 以 Ansi 方式在控制台输出（输出彩色字体、粗体、斜体、下划线等）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 * @since 2019/10/30
 */
public class AnsiSystem {

    public static final AnsiSystem RED = new AnsiSystem(AnsiColor.RED);

    public static final AnsiSystem GREEN = new AnsiSystem(AnsiColor.GREEN);

    public static final AnsiSystem YELLOW = new AnsiSystem(AnsiColor.YELLOW);

    public static final AnsiSystem BLUE = new AnsiSystem(AnsiColor.BLUE);

    public static final AnsiSystem MAGENTA = new AnsiSystem(AnsiColor.MAGENTA);

    public static final AnsiSystem CYAN = new AnsiSystem(AnsiColor.CYAN);

    public static final AnsiSystem WHITE = new AnsiSystem(AnsiColor.WHITE);

    public static final AnsiSystem BOLD_RED = new AnsiSystem(AnsiColor.RED, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_GREEN = new AnsiSystem(AnsiColor.GREEN, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_YELLOW = new AnsiSystem(AnsiColor.YELLOW, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_BLUE = new AnsiSystem(AnsiColor.BLUE, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_MAGENTA = new AnsiSystem(AnsiColor.MAGENTA, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_CYAN = new AnsiSystem(AnsiColor.CYAN, AnsiStyle.BOLD);

    public static final AnsiSystem BOLD_WHITE = new AnsiSystem(AnsiColor.WHITE, AnsiStyle.BOLD);

    private static final String ENCODE_JOIN = ";";

    private static final String ENCODE_START = "\033[";

    private static final String ENCODE_END = "m";

    private static final String RESET = "\033[0;m";

    private static boolean enabled = true;

    private String code;

    public AnsiSystem(AnsiElement... elements) {
        setCode(elements);
    }

    public void printf(String format, Object... args) {
        String message = String.format(format, args);
        if (enabled) {
            System.out.print(encode(message));
        } else {
            System.out.print(message);
        }
    }

    public void print(String message) {
        if (enabled) {
            System.out.print(encode(message));
        } else {
            System.out.print(message);
        }
    }

    public void println(String message) {
        if (enabled) {
            System.out.println(encode(message));
        } else {
            System.out.println(message);
        }
    }

    public static void setEnabled(boolean enabled) {
        AnsiSystem.enabled = enabled;
    }

    public String getCode() {
        return code;
    }

    private void setCode(AnsiElement... elements) {
        StringBuilder sb = new StringBuilder();
        sb.append(ENCODE_START);
        for (AnsiElement element : elements) {
            sb.append(ENCODE_JOIN).append(element.toString());
        }
        sb.append(ENCODE_END);
        this.code = sb.toString();
    }

    private String encode(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.code).append(message).append(RESET);
        return sb.toString();
    }

}
