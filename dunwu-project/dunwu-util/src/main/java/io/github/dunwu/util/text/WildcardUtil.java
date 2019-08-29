package io.github.dunwu.util.text;

/**
 * 通配符工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/08/28
 */
public class WildcardUtil {

    public static final char CHAR_ALL_WILDCARD = '*';
    public static final char CHAR_ANYONE_WILDCARD = '?';

    /**
     * 判定源字符串是否匹配通配符表达式
     *
     * @param source 源字符串
     * @param pattern 匹配模式
     * @return true / false
     */
    public static boolean match(String source, String pattern) {
        int m = source.length(), n = pattern.length();
        boolean[][] f = new boolean[m + 1][n + 1];

        f[0][0] = true;
        for (int i = 1; i <= n; i++) {
            f[0][i] = f[0][i - 1] && pattern.charAt(i - 1) == CHAR_ALL_WILDCARD;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (source.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == CHAR_ANYONE_WILDCARD) {
                    f[i][j] = f[i - 1][j - 1];
                }
                if (pattern.charAt(j - 1) == CHAR_ALL_WILDCARD) {
                    f[i][j] = f[i][j - 1] || f[i - 1][j];
                }
            }
        }
        return f[m][n];
    }

    /**
     * 判定源字符串是否匹配任意通配符表达式
     *
     * @param source 源字符串
     * @param patterns 匹配模式
     * @return 匹配的模式的序号
     */
    public static int matchOne(String source, String... patterns) {
        for (int i = 0; i < patterns.length; i++) {
            if (match(source, patterns[i])) {
                return i;
            }
        }
        return -1;
    }
}
