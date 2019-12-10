package io.github.dunwu.util.text;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2016/10/27
 */
public class RegexUtils {

	// @formatter:off

	// 字符类型正则表达式
	// -----------------------------------------------------------------------------

	/**
	 * 全是英文字母 、数字和下划线
	 */
	public final static String ALL_GENERAL_CHAR = "\\w+";

	/**
	 * 中文字、英文字母、数字和下划线
	 */
	public final static String ALL_GENERAL_AND_ZH_CHAR = "[\u4E00-\u9FFF\\w]+";

	/**
	 * 不含英文字母 、数字和下划线
	 */
	public final static String NONE_GENERAL_CHAR = "\\W+";

	/**
	 * 全是中文字符
	 */
	public final static String ALL_ZH_CHAR = "[\\u4e00-\\u9fa5]+";

	/**
	 * 全是英文字符
	 */
	public final static String ALL_EN_CHAR = "[A-Za-z]+";

	/**
	 * 全是英文大写字符
	 */
	public final static String ALL_EN_UPPER_CHAR = "[A-Z]+";

	/**
	 * 全是英文小写字符
	 */
	public final static String ALL_EN_LOWER_CHAR = "[a-z]+";

	/**
	 * 文本长度在指定范围内
	 */
	public final static String TEXT_LENGTH_RANGE = ".{%d,%d}";

	// 数字类型正则表达式
	// -----------------------------------------------------------------------------

	/**
	 * 全是数字字符
	 */
	public final static String ALL_NUM_CHAR = "[\\d]*";

	/**
	 * 不含数字字符
	 */
	public final static String NONE_NUM_CHAR = "[\\D]*";

	/**
	 * N 位的数字
	 */
	public final static String NUMBER_DIGIT = "\\d{%d}";

	/**
	 * 大于 N 位的数字
	 */
	public final static String NUMBER_DIGIT_MORE = "\\d{%d,}";

	/**
	 * 位数在 [M,N] 之间的数字
	 */
	public final static String NUMBER_DIGIT_RANGE = "\\d{%d,%d}";

	/**
	 * 整数
	 */
	public final static String INTEGER = "-?[1-9]\\d*";

	/**
	 * 正整数
	 */
	public final static String POSITIVE_INTEGER = "[1-9]\\d*";

	/**
	 * 负整数
	 */
	public final static String NEGATIVE_INTEGER = "-[1-9]\\d*";

	/**
	 * 非负整数
	 */
	public final static String NOT_NEGATIVE_INTEGER = "[1-9]\\d*|0";

	/**
	 * 浮点数
	 */
	public final static String FLOAT = "-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)";

	/**
	 * 正浮点数
	 */
	public final static String POSITIVE_FLOAT = "[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*";

	/**
	 * 浮点数
	 */
	public final static String NEGATIVE_FLOAT = "-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)";

	/**
	 * 非负浮点数
	 */
	public final static String NOT_NEGATIVE_FLOAT = "[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0";

	/**
	 * 16进制字符串
	 */
	public final static String HEX = "[a-f0-9]+";

	// 应用场景类型正则表达式
	// -----------------------------------------------------------------------------

	/**
	 * 分组
	 */
	public final static String GROUP_VAR = "\\$(\\d+)";

	/**
	 * IP v4
	 */
	public final static String IPV4 = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

	/**
	 * IP v6
	 */
	public final static String IPV6 = "(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))";

	/**
	 * 邮件，符合RFC 5322规范，正则来自：http://emailregex.com/
	 */
	public final static String EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

	/**
	 * 邮政编码
	 */
	public final static String ZIP_CODE = "[1-9]\\d{5}(?!\\d)";

	/**
	 * 固定电话
	 */
	public final static String PHONE = "(\\d{3,4}-)?\\d{6,8}";

	/**
	 * 移动电话
	 * <p>
	 * 匹配所有支持短信功能的号码（手机卡 + 上网卡）
	 *
	 * @see <a href="https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md">ChinaMobilePhoneNumberRegex</a>
	 */
	public final static String MOBILE = "(?:\\+?86)?(\\s)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[01356789]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[189]\\d{2}|6[567]\\d{2}|4[579]\\d{2})\\d{6}";

	/**
	 * 15位身份证号码
	 */
	public final static String CITIZEN_ID15 = "((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3})";

	/**
	 * 18位身份证号码
	 */
	public final static String CITIZEN_ID18 = "((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{4}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3}(\\d|X))";

	/**
	 * URL
	 *
	 * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
	 */
	public final static String URL = "(ht|f)(tp|tps)\\://[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3})?(/\\S*)?";

	/**
	 * Http URL
	 *
	 * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
	 */
	public final static String URL_HTTP = "https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,}";

	/**
	 * UUID
	 */
	public final static String UUID = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";

	/**
	 * 中国车牌号码（包含绿色车牌）
	 */
	public final static String PLATE_NUMBER = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][DF]?[A-Z0-9]{4}[A-Z0-9挂学警港澳]";

	/**
	 * MAC地址正则
	 */
	public final static String MAC = "((([a-fA-F0-9][a-fA-F0-9]+[-]){5}|([a-fA-F0-9][a-fA-F0-9]+[:]){5})([a-fA-F0-9][a-fA-F0-9])$)|(^([a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]+[.]){2}([a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]))";

	/**
	 * 日期正则
	 */
	public final static String DATE = "(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))";

	/**
	 * 时间正则
	 */
	public final static String TIME = "([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])";

	public final static String REGEX_MARKDOWN_IMAGE_TAG = "!\\[.+\\]";
	// @formatter:on

	public static List<String> getMatchValuesInJson(final String text, final String key) {
		String pattern = String.format("\"%s\":\"?(\\w+)\"?", key);
		return getMatchValues(text, pattern);
	}

	public static List<String> getMatchValues(final String text, final String regex) {
		return getMatchValues(text, Pattern.compile(regex));
	}

	public static List<String> getMatchValues(final String text, final Pattern pattern) {
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			int count = matcher.groupCount();
			List<String> list = new LinkedList<>();
			for (int i = 1; i <= count; i++) {
				list.add(matcher.group(i));
			}
			return list;
		}
		return null;
	}

	/**
	 * 校验文本是否满足正则表达式
	 *
	 * @param text  被校验的文本
	 * @param regex 正则表达式
	 * @return boolean
	 */
	public static boolean find(final String text, final String regex) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(regex)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}

	/**
	 * 校验文本是否满足正则表达式
	 *
	 * @param text  被校验的文本
	 * @param regex 正则表达式
	 * @return boolean
	 */
	public static boolean matches(final String text, final String regex) {
		return matches(text, regex, 0);
	}

	/**
	 * 校验文本是否满足正则表达式
	 *
	 * @param text  被校验的文本
	 * @param regex 正则表达式
	 * @param flag  正则匹配的模式，可选值：{@link Pattern#CASE_INSENSITIVE}, {@link Pattern#MULTILINE}, {@link Pattern#DOTALL},
	 *              {@link Pattern#UNICODE_CASE}, {@link Pattern#CANON_EQ}, {@link Pattern#UNIX_LINES}, {@link
	 *              Pattern#LITERAL}, {@link Pattern#UNICODE_CHARACTER_CLASS}, {@link Pattern#COMMENTS}
	 * @return boolean
	 */
	public static boolean matches(final String text, final String regex, final int flag) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(regex)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex, flag);
		return matches(text, pattern);
	}

	/**
	 * 校验文本是否满足正则表达式
	 *
	 * @param text    被校验的文本
	 * @param pattern 已经初始化的 {@link Pattern}
	 * @return boolean
	 */
	public static boolean matches(final String text, final Pattern pattern) {
		if (StringUtils.isBlank(text) || pattern == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * 移除文本中所有匹配正则表达式的子字符串
	 * <p>
	 * 搬迁自 apache-common
	 *
	 * <pre>
	 * StringUtils.removeAll(null, *)      = null
	 * StringUtils.removeAll("any", (Pattern) null)  = "any"
	 * StringUtils.removeAll("any", Pattern.compile(""))    = "any"
	 * StringUtils.removeAll("any", Pattern.compile(".*"))  = ""
	 * StringUtils.removeAll("any", Pattern.compile(".+"))  = ""
	 * StringUtils.removeAll("abc", Pattern.compile(".?"))  = ""
	 * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;"))      = "A\nB"
	 * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("(?s)&lt;.*&gt;"))  = "AB"
	 * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;", Pattern.DOTALL))  = "AB"
	 * StringUtils.removeAll("ABCabc123abc", Pattern.compile("[a-z]"))     = "ABC123"
	 * </pre>
	 *
	 * @param text    被替换的文本
	 * @param pattern 正则表达式
	 * @return 移除后的字符串
	 */
	public static String removeAll(final String text, final Pattern pattern) {
		return replaceAll(text, pattern, StringUtils.EMPTY);
	}

	/**
	 * 移除文本中所有匹配正则表达式的子字符串
	 * <p>
	 * 搬迁自 apache-common
	 *
	 * <pre>
	 * StringUtils.removeAll(null, *)      = null
	 * StringUtils.removeAll("any", (String) null)  = "any"
	 * StringUtils.removeAll("any", "")    = "any"
	 * StringUtils.removeAll("any", ".*")  = ""
	 * StringUtils.removeAll("any", ".+")  = ""
	 * StringUtils.removeAll("abc", ".?")  = ""
	 * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\nB"
	 * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
	 * StringUtils.removeAll("ABCabc123abc", "[a-z]")     = "ABC123"
	 * </pre>
	 *
	 * @param text  被替换的文本
	 * @param regex 正则表达式
	 * @return 移除后的字符串
	 */
	public static String removeAll(final String text, final String regex) {
		return replaceAll(text, regex, StringUtils.EMPTY);
	}

	/**
	 * 移除文本中第一个匹配正则表达式的子字符串
	 * <p>
	 * 搬迁自 apache-common
	 *
	 * <pre>
	 * StringUtils.removeFirst(null, *)      = null
	 * StringUtils.removeFirst("any", (Pattern) null)  = "any"
	 * StringUtils.removeFirst("any", Pattern.compile(""))    = "any"
	 * StringUtils.removeFirst("any", Pattern.compile(".*"))  = ""
	 * StringUtils.removeFirst("any", Pattern.compile(".+"))  = ""
	 * StringUtils.removeFirst("abc", Pattern.compile(".?"))  = "bc"
	 * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;"))      = "A\n&lt;__&gt;B"
	 * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("(?s)&lt;.*&gt;"))  = "AB"
	 * StringUtils.removeFirst("ABCabc123", Pattern.compile("[a-z]"))          = "ABCbc123"
	 * StringUtils.removeFirst("ABCabc123abc", Pattern.compile("[a-z]+"))      = "ABC123abc"
	 * </pre>
	 *
	 * @param text    被替换的文本
	 * @param pattern 正则表达式
	 * @return 移除后的字符串
	 */
	public static String removeFirst(final String text, final Pattern pattern) {
		return replaceFirst(text, pattern, StringUtils.EMPTY);
	}

	/**
	 * 移除文本中第一个匹配正则表达式的子字符串
	 * <p>
	 * 搬迁自 apache-common
	 *
	 * <pre>
	 * StringUtils.removeFirst(null, *)      = null
	 * StringUtils.removeFirst("any", (String) null)  = "any"
	 * StringUtils.removeFirst("any", "")    = "any"
	 * StringUtils.removeFirst("any", ".*")  = ""
	 * StringUtils.removeFirst("any", ".+")  = ""
	 * StringUtils.removeFirst("abc", ".?")  = "bc"
	 * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\n&lt;__&gt;B"
	 * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
	 * StringUtils.removeFirst("ABCabc123", "[a-z]")          = "ABCbc123"
	 * StringUtils.removeFirst("ABCabc123abc", "[a-z]+")      = "ABC123abc"
	 * </pre>
	 *
	 * @param text  被替换的文本
	 * @param regex 正则表达式
	 * @return 移除后的字符串
	 */
	public static String removeFirst(final String text, final String regex) {
		return replaceFirst(text, regex, StringUtils.EMPTY);
	}

	/**
	 * 替换文本中所有匹配正则表达式的子字符串为目标字符串。
	 * <p>
	 * 搬迁自 apache-common
	 * <p>
	 * 允许传入 null 值
	 *
	 * <pre>
	 * RegexUtils.replaceAll(null, *, *)       = null
	 * RegexUtils.replaceAll("any", (Pattern) null, *)   = "any"
	 * RegexUtils.replaceAll("any", *, null)   = "any"
	 * RegexUtils.replaceAll("", Pattern.compile(""), "zzz")    = "zzz"
	 * RegexUtils.replaceAll("", Pattern.compile(".*"), "zzz")  = "zzz"
	 * RegexUtils.replaceAll("", Pattern.compile(".+"), "zzz")  = ""
	 * RegexUtils.replaceAll("abc", Pattern.compile(""), "ZZ")  = "ZZaZZbZZcZZ"
	 * RegexUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;"), "z")                 = "z\nz"
	 * RegexUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;", Pattern.DOTALL), "z") = "z"
	 * RegexUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("(?s)&lt;.*&gt;"), "z")             = "z"
	 * RegexUtils.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_")       = "ABC___123"
	 * RegexUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_")  = "ABC_123"
	 * RegexUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "")   = "ABC123"
	 * RegexUtils.replaceAll("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2")  = "Lorem_ipsum_dolor_sit"
	 * </pre>
	 *
	 * @param text        被替换的文本
	 * @param pattern     正则表达式
	 * @param replacement 目标字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(final String text, final Pattern pattern, final String replacement) {
		if (text == null || pattern == null || replacement == null) {
			return text;
		}
		return pattern.matcher(text).replaceAll(replacement);
	}

	/**
	 * 替换文本中所有匹配正则表达式的子字符串为目标字符串。
	 * <p>
	 * 搬迁自 apache-common
	 * <p>
	 * 允许传入 null 值
	 *
	 * <pre>
	 * RegexUtils.replaceAll(null, *, *)       = null
	 * RegexUtils.replaceAll("any", (String) null, *)   = "any"
	 * RegexUtils.replaceAll("any", *, null)   = "any"
	 * RegexUtils.replaceAll("", "", "zzz")    = "zzz"
	 * RegexUtils.replaceAll("", ".*", "zzz")  = "zzz"
	 * RegexUtils.replaceAll("", ".+", "zzz")  = ""
	 * RegexUtils.replaceAll("abc", "", "ZZ")  = "ZZaZZbZZcZZ"
	 * RegexUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\nz"
	 * RegexUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
	 * RegexUtils.replaceAll("ABCabc123", "[a-z]", "_")       = "ABC___123"
	 * RegexUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
	 * RegexUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
	 * RegexUtils.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
	 * </pre>
	 *
	 * @param text        被替换的文本
	 * @param regex       正则表达式
	 * @param replacement 目标字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(final String text, final String regex, final String replacement) {
		if (text == null || regex == null || replacement == null) {
			return text;
		}
		return text.replaceAll(regex, replacement);
	}

	/**
	 * 替换文本中第一个匹配正则表达式的子字符串为目标字符串
	 *
	 * <pre>
	 * RegexUtils.replaceFirst(null, *, *)       = null
	 * RegexUtils.replaceFirst("any", (Pattern) null, *)   = "any"
	 * RegexUtils.replaceFirst("any", *, null)   = "any"
	 * RegexUtils.replaceFirst("", Pattern.compile(""), "zzz")    = "zzz"
	 * RegexUtils.replaceFirst("", Pattern.compile(".*"), "zzz")  = "zzz"
	 * RegexUtils.replaceFirst("", Pattern.compile(".+"), "zzz")  = ""
	 * RegexUtils.replaceFirst("abc", Pattern.compile(""), "ZZ")  = "ZZabc"
	 * RegexUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;"), "z")      = "z\n&lt;__&gt;"
	 * RegexUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("(?s)&lt;.*&gt;"), "z")  = "z"
	 * RegexUtils.replaceFirst("ABCabc123", Pattern.compile("[a-z]"), "_")          = "ABC_bc123"
	 * RegexUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "_")  = "ABC_123abc"
	 * RegexUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "")   = "ABC123abc"
	 * RegexUtils.replaceFirst("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2")  = "Lorem_ipsum  dolor   sit"
	 * </pre>
	 *
	 * @param text        被替换的文本
	 * @param pattern     正则表达式
	 * @param replacement 目标字符串
	 * @return 替换后的文本
	 */
	public static String replaceFirst(final String text, final Pattern pattern, final String replacement) {
		if (text == null || pattern == null || replacement == null) {
			return text;
		}
		return pattern.matcher(text).replaceFirst(replacement);
	}

	/**
	 * 替换文本中第一个匹配正则表达式的子字符串为目标字符串
	 *
	 * <pre>
	 * RegexUtils.replaceFirst(null, *, *)       = null
	 * RegexUtils.replaceFirst("any", (String) null, *)   = "any"
	 * RegexUtils.replaceFirst("any", *, null)   = "any"
	 * RegexUtils.replaceFirst("", "", "zzz")    = "zzz"
	 * RegexUtils.replaceFirst("", ".*", "zzz")  = "zzz"
	 * RegexUtils.replaceFirst("", ".+", "zzz")  = ""
	 * RegexUtils.replaceFirst("abc", "", "ZZ")  = "ZZabc"
	 * RegexUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\n&lt;__&gt;"
	 * RegexUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
	 * RegexUtils.replaceFirst("ABCabc123", "[a-z]", "_")          = "ABC_bc123"
	 * RegexUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_")  = "ABC_123abc"
	 * RegexUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "")   = "ABC123abc"
	 * RegexUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum  dolor   sit"
	 * </pre>
	 *
	 * @param text        被替换的文本
	 * @param regex       正则表达式
	 * @param replacement 目标字符串
	 * @return 替换后的文本
	 */
	public static String replaceFirst(final String text, final String regex, final String replacement) {
		if (text == null || regex == null || replacement == null) {
			return text;
		}
		return text.replaceFirst(regex, replacement);
	}

}
