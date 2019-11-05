package io.github.dunwu.util.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 扩展 {@link org.apache.commons.lang3.StringUtils}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 */
public class StringExtUtil extends StringUtils {

	// 字符串替换
	// ---------------------------------------------------------------------------------

	/**
	 * 替换第一个符合匹配条件的字符
	 * @param text 目标字符串
	 * @param search 查找的字符
	 * @param replace 替换的字符
	 * @return 替换后的字符串
	 */
	public static String replaceFirst(final String text, final char search,
			final char replace) {
		if (isBlank(text)) {
			return null;
		}
		int index = text.indexOf(search);
		if (index == -1) {
			return text;
		}
		char[] str = text.toCharArray();
		str[index] = replace;
		return new String(str);
	}

	/**
	 * 替换最后一个符合匹配条件的字符
	 * @param text 目标字符串
	 * @param search 查找的字符
	 * @param replace 替换的字符
	 * @return 替换后的字符串
	 */
	public static String replaceLast(final String text, final char search,
			final char replace) {
		if (isBlank(text)) {
			return null;
		}
		int index = text.lastIndexOf(search);
		if (index == -1) {
			return text;
		}
		char[] str = text.toCharArray();
		str[index] = replace;
		return new String(str);
	}

}
