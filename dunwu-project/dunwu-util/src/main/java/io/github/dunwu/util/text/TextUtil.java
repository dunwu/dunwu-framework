package io.github.dunwu.util.text;

/**
 * 文本工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/08/28
 */
public class TextUtil {

	public static final int MOBILE_NUM_SIZE = 11;

	public static final char CHAR_ALL_WILDCARD = '*';

	public static final char CHAR_ANYONE_WILDCARD = '?';

	/**
	 * 判定源字符串是否匹配任意通配符表达式
	 *
	 * @param text      文本内容
	 * @param wildcards 通配符表达式数组
	 * @return 匹配第几个通配符表达式
	 */
	public static int matchOne(String text, String... wildcards) {
		for (int i = 0; i < wildcards.length; i++) {
			if (match(text, wildcards[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 判定源字符串是否匹配通配符表达式
	 *
	 * @param text     文本内容
	 * @param wildcard 通配符表达式
	 * @return true / false
	 */
	public static boolean match(String text, String wildcard) {
		int m = text.length(), n = wildcard.length();
		boolean[][] f = new boolean[m + 1][n + 1];

		f[0][0] = true;
		for (int i = 1; i <= n; i++) {
			f[0][i] = f[0][i - 1] && wildcard.charAt(i - 1) == CHAR_ALL_WILDCARD;
		}

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (text.charAt(i - 1) == wildcard.charAt(j - 1)
					|| wildcard.charAt(j - 1) == CHAR_ANYONE_WILDCARD) {
					f[i][j] = f[i - 1][j - 1];
				}
				if (wildcard.charAt(j - 1) == CHAR_ALL_WILDCARD) {
					f[i][j] = f[i][j - 1] || f[i - 1][j];
				}
			}
		}
		return f[m][n];
	}

	/**
	 * 如果文本内容是敏感信息，则隐去部分内容
	 *
	 * @param text
	 * @return
	 */
	public static String maskText(String text) {
		if (RegexUtil.isEmail(text)) {
			int atIndex = text.indexOf('@');
			return (text.substring(0, 1) + "*****" + text.substring(atIndex - 1))
				.toLowerCase();
		} else if (RegexUtil.isMobile(text)) {
			String digits = text.replaceAll("\\D+", "");
			String local = "***-***-" + digits.substring(digits.length() - 4);
			if (digits.length() == MOBILE_NUM_SIZE - 1) {
				return local;
			}
			String ans = "+";
			for (int i = 0; i < digits.length() - (MOBILE_NUM_SIZE - 1); ++i) {
				ans += "*";
			}
			return ans + "-" + local;
		} else {
			return text;
		}
	}

}
