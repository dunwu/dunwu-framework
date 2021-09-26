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
public class AnsiColorUtil {

    public static final AnsiColorUtil RED = new AnsiColorUtil(AnsiColor.RED);

    public static final AnsiColorUtil GREEN = new AnsiColorUtil(AnsiColor.GREEN);

    public static final AnsiColorUtil YELLOW = new AnsiColorUtil(AnsiColor.YELLOW);

    public static final AnsiColorUtil BLUE = new AnsiColorUtil(AnsiColor.BLUE);

    public static final AnsiColorUtil MAGENTA = new AnsiColorUtil(AnsiColor.MAGENTA);

    public static final AnsiColorUtil CYAN = new AnsiColorUtil(AnsiColor.CYAN);

    public static final AnsiColorUtil WHITE = new AnsiColorUtil(AnsiColor.WHITE);

    public static final AnsiColorUtil BOLD_RED = new AnsiColorUtil(AnsiColor.RED, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_GREEN = new AnsiColorUtil(AnsiColor.GREEN, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_YELLOW = new AnsiColorUtil(AnsiColor.YELLOW, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_BLUE = new AnsiColorUtil(AnsiColor.BLUE, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_MAGENTA = new AnsiColorUtil(AnsiColor.MAGENTA, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_CYAN = new AnsiColorUtil(AnsiColor.CYAN, AnsiStyle.BOLD);

    public static final AnsiColorUtil BOLD_WHITE = new AnsiColorUtil(AnsiColor.WHITE, AnsiStyle.BOLD);

    private static final String ENCODE_JOIN = ";";

    private static final String ENCODE_START = "\033[";

    private static final String ENCODE_END = "m";

    private static final String RESET = "\033[0;m";

    private static boolean enabled = true;

    private String code;

    public AnsiColorUtil(AnsiElement... elements) {
        setCode(elements);
    }

    public static void setEnabled(boolean enabled) {
        AnsiColorUtil.enabled = enabled;
    }

    public void printf(String format, Object... args) {
        String msg = String.format(format, args);
        if (enabled) {
            System.out.print(encode(msg));
        } else {
            System.out.print(msg);
        }
    }

    private String encode(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.code).append(msg).append(RESET);
        return sb.toString();
    }

    public void print(String msg) {
        if (enabled) {
            System.out.print(encode(msg));
        } else {
            System.out.print(msg);
        }
    }

    public void println(String msg) {
        if (enabled) {
            System.out.println(encode(msg));
        } else {
            System.out.println(msg);
        }
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

}
