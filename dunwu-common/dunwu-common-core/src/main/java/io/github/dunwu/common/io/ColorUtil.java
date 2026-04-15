package io.github.dunwu.common.io;

import io.github.dunwu.common.io.ansi.AnsiColor;
import io.github.dunwu.common.io.ansi.AnsiElement;
import io.github.dunwu.common.io.ansi.AnsiStyle;

/**
 * 以 Ansi 方式在控制台输出（输出彩色字体、粗体、斜体、下划线等）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape code</a>
 * @since 2019/10/30
 */
public class ColorUtil {

    public static final ColorUtil RED = new ColorUtil(AnsiColor.RED);

    public static final ColorUtil GREEN = new ColorUtil(AnsiColor.GREEN);

    public static final ColorUtil YELLOW = new ColorUtil(AnsiColor.YELLOW);

    public static final ColorUtil BLUE = new ColorUtil(AnsiColor.BLUE);

    public static final ColorUtil MAGENTA = new ColorUtil(AnsiColor.MAGENTA);

    public static final ColorUtil CYAN = new ColorUtil(AnsiColor.CYAN);

    public static final ColorUtil WHITE = new ColorUtil(AnsiColor.WHITE);

    public static final ColorUtil BOLD_RED = new ColorUtil(AnsiColor.RED, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_GREEN = new ColorUtil(AnsiColor.GREEN, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_YELLOW = new ColorUtil(AnsiColor.YELLOW, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_BLUE = new ColorUtil(AnsiColor.BLUE, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_MAGENTA = new ColorUtil(AnsiColor.MAGENTA, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_CYAN = new ColorUtil(AnsiColor.CYAN, AnsiStyle.BOLD);

    public static final ColorUtil BOLD_WHITE = new ColorUtil(AnsiColor.WHITE, AnsiStyle.BOLD);

    private static final String ENCODE_JOIN = ";";

    private static final String ENCODE_START = "\033[";

    private static final String ENCODE_END = "m";

    private static final String RESET = "\033[0;m";

    private static boolean enabled = true;

    private String code;

    /**
     * 构造方法
     *
     * @param elements ANSI 元素（颜色、样式等）
     */
    public ColorUtil(AnsiElement... elements) {
        setCode(elements);
    }

    /**
     * 设置是否启用 ANSI 彩色输出
     *
     * @param enabled 是否启用
     */
    public static void setEnabled(boolean enabled) {
        ColorUtil.enabled = enabled;
    }

    /**
     * 以格式化方式输出彩色文本（不换行）
     *
     * @param format 格式字符串
     * @param args   格式参数
     */
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

    /**
     * 输出彩色文本（不换行）
     *
     * @param msg 文本内容
     */
    public void print(String msg) {
        if (enabled) {
            System.out.print(encode(msg));
        } else {
            System.out.print(msg);
        }
    }

    /**
     * 输出彩色文本（换行）
     *
     * @param msg 文本内容
     */
    public void println(String msg) {
        if (enabled) {
            System.out.println(encode(msg));
        } else {
            System.out.println(msg);
        }
    }

    /**
     * 获取 ANSI 编码
     *
     * @return ANSI 编码字符串
     */
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
