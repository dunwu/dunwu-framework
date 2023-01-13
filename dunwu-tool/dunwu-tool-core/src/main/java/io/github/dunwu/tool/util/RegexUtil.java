package io.github.dunwu.tool.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2016/10/27
 */
public class RegexUtil {

    // @formatter:off

    /**
     * 最实用的正则
     */
    public static final String REGEX_ONLY_CHINESE_CHAR = "^[\u4e00-\u9fa5]+$";

    public static final String REGEX_ID_CARD_15 =
        "^((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3})$";

    public static final String REGEX_ID_CARD_18 =
        "^((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{4}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3}(\\d|X))$";

    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    public static final String REGEX_EMAIL =
        "^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";

    public static final String REGEX_URI = "^(ht|f)(tp|tps)\\://[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3})?(/\\S*)?$";

    public static final String REGEX_IPV4 =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static final String REGEX_IPV6 =
        "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

    public static final String REGEX_TIME = "^([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

    public static final String REGEX_DATE =
        "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";

    public static final String REGEX_MOBILE = "^((\\+)?86\\s*)?((13[0-9])|(15([0-3]|[5-9]))|(18[0,2,5-9]))\\d{8}$";

    public static final String REGEX_PHONE = "^(010|02[0-9])(\\s|-)\\d{8}|(0[3-9]\\d{2})(\\s|-)\\d{7}$";

    /**
     * 特定数字
     */
    public static final String REGEX_NUM_ALL = "^[\\d]*$";

    public static final String REGEX_NUM_NONE = "^[\\D]*$";

    public static final String REGEX_N_NUMBER = "^\\d{%d}$";

    public static final String REGEX_MORE_THAN_N_NUMBER = "^\\d{%d,}$";

    public static final String REGEX_M_TO_N_NUMBER = "^\\d{%d,%d}$";

    /**
     * 特定字符
     */
    public static final String REGEX_CHAR_EN_ALL = "^[A-Za-z]+$";

    public static final String REGEX_CHAR_EN_UPPER_ALL = "^[A-Z]+$";

    public static final String REGEX_CHAR_EN_LOWER_ALL = "^[a-z]+$";

    public static final String REGEX_CHAR_UNI_WORD_ALL = "^\\w+$";

    public static final String REGEX_CHAR_UNI_WORD_NONE = "^\\W+$";

    // 字符类型正则表达式
    // -----------------------------------------------------------------------------

    /**
     * 英文字符
     */
    public static final String EN_CHAR = "[A-Za-z]+";

	/**
	 * 英文小写字符
	 */
	public static final String EN_LOWER_CHAR = "[a-z]+";

	/**
	 * 英文大写字符
	 */
	public static final String EN_UPPER_CHAR = "[A-Z]+";

    /**
     * 中文字符
     */
    public static final String ZH_CHAR = "[\\u4e00-\\u9fa5]+";

	/**
	 * 中文字、英文字母、数字和下划线
	 */
	public static final String ALL_GENERAL_AND_ZH_CHAR = "[\u4E00-\u9FFF\\w]+";

	/**
	 * 英文字母 、数字和下划线
	 */
	public static final String GENERAL_CHAR = "\\w+";

	/**
	 * 15位身份证号码
	 */
	public static final String CITIZEN_ID15 = "((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3})";

	/**
	 * 18位身份证号码
	 */
	public static final String CITIZEN_ID18 = "((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{4}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{3}(\\d|X))";

	/**
	 * 日期正则
	 */
	public static final String DATE = "(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))";

	// 数字类型正则表达式
	// -----------------------------------------------------------------------------

	/**
	 * 邮件，符合RFC 5322规范，正则来自：http://emailregex.com/
	 */
	public static final String EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

	/**
	 * 浮点数
	 */
	public static final String FLOAT = "-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)";

	/**
	 * 分组
	 */
	public static final String GROUP_VAR = "\\$(\\d+)";

	/**
	 * 16进制字符串
	 */
	public static final String HEX = "[a-f0-9]+";

	/**
	 * 整数
	 */
	public static final String INTEGER = "-?[1-9]\\d*";

	/**
	 * IP v4
	 */
	public static final String IPV4 = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

	/**
	 * IP v6
	 */
	public static final String IPV6 = "(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))";

	/**
	 * MAC地址正则
	 */
	public static final String MAC = "((([a-fA-F0-9][a-fA-F0-9]+[-]){5}|([a-fA-F0-9][a-fA-F0-9]+[:]){5})([a-fA-F0-9][a-fA-F0-9])$)|(^([a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]+[.]){2}([a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]))";

	/**
	 * 移动电话
	 * <p>
	 * 匹配所有支持短信功能的号码（手机卡 + 上网卡）
	 *
	 * @see <a href="https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md">ChinaMobilePhoneNumberRegex</a>
	 */
	public static final String MOBILE = "(?:\\+?86)?(\\s)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[01356789]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[189]\\d{2}|6[567]\\d{2}|4[579]\\d{2})\\d{6}";

	/**
	 * 浮点数
	 */
	public static final String NEGATIVE_FLOAT = "-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)";

	/**
	 * 负整数
	 */
	public static final String NEGATIVE_INTEGER = "-[1-9]\\d*";

	/**
	 * 不含英文字母 、数字和下划线
	 */
	public static final String NONE_GENERAL_CHAR = "\\W+";

	/**
	 * 不含数字字符
	 */
	public static final String NONE_NUM_CHAR = "[\\D]*";

	/**
	 * 非负浮点数
	 */
	public static final String NOT_NEGATIVE_FLOAT = "[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0";

	// 应用场景类型正则表达式
	// -----------------------------------------------------------------------------

	/**
	 * 非负整数
	 */
	public static final String NOT_NEGATIVE_INTEGER = "[1-9]\\d*|0";

	/**
	 * 全是数字字符
	 */
	public static final String NUMBERS = "\\d+";

	/**
	 * N 位的数字
	 */
	public static final String NUMBER_DIGIT = "\\d{%d}";

	/**
	 * 大于 N 位的数字
	 */
	public static final String NUMBER_DIGIT_MORE = "\\d{%d,}";

	/**
	 * 位数在 [M,N] 之间的数字
	 */
	public static final String NUMBER_DIGIT_RANGE = "\\d{%d,%d}";

	/**
	 * 固定电话
	 */
	public static final String PHONE = "(\\d{3,4}-)?\\d{6,8}";

	/**
	 * 中国车牌号码（包含绿色车牌）
	 */
	public static final String PLATE_NUMBER = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][DF]?[A-Z0-9]{4}[A-Z0-9挂学警港澳]";

	/**
	 * 正浮点数
	 */
	public static final String POSITIVE_FLOAT = "[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*";

	/**
	 * 正整数
	 */
	public static final String POSITIVE_INTEGER = "[1-9]\\d*";

    /**
     * 正则中需要被转义的关键字
     */
    public static final Set<Character> REGEX_ESCAPE_CHARS = CollectionUtil.newHashSet(
        '$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|');

	public static final String REGEX_MARKDOWN_IMAGE_TAG = "!\\[.+\\]";

	/**
	 * 文本长度在指定范围内
	 */
	public static final String TEXT_LENGTH_RANGE = ".{%d,%d}";

	/**
	 * 时间正则
	 */
	public static final String TIME = "([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])";

	/**
	 * URL
	 *
	 * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
	 */
	public static final String URL = "(ht|f)(tp|tps)\\://[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3})?(/\\S*)?";

	/**
	 * Http URL
	 *
	 * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
	 */
	public static final String URL_HTTP = "https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,}";

	/**
	 * UUID
	 */
	public static final String UUID = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";

	/**
	 * 邮政编码
	 */
	public static final String ZIP_CODE = "[1-9]\\d{5}(?!\\d)";

	// @formatter:on

    // ----------------------------------------------------------------------------------------------- contains

    /**
     * 指定内容中是否有表达式匹配的文本
     *
     * @param text  被查找的文本
     * @param regex 正则表达式
     * @return 指定内容中是否有表达式匹配的文本
     * @since 3.3.1
     */
    public static boolean contains(final CharSequence text, final String regex) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex)) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        return contains(text, pattern);
    }

    /**
     * 指定内容中是否有表达式匹配的文本
     *
     * @param text    被查找的文本
     * @param pattern 编译后的正则模式
     * @return 指定内容中是否有表达式匹配的文本
     * @since 3.3.1
     */
    public static boolean contains(final CharSequence text, final Pattern pattern) {
        if (StrUtil.isBlank(text) || pattern == null) {
            return false;
        }
        return pattern.matcher(text).find();
    }

    // ----------------------------------------------------------------------------------------------- count

    /**
     * 计算指定字符串中，匹配pattern的个数
     *
     * @param text  被查找的文本
     * @param regex 正则表达式
     * @return 匹配个数
     */
    public static int count(final CharSequence text, final String regex) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex)) {
            return 0;
        }

        Pattern pattern = Pattern.compile(regex);
        return count(text, pattern);
    }

    /**
     * 计算指定字符串中，匹配pattern的个数
     *
     * @param text    被查找的文本
     * @param pattern 编译后的正则模式
     * @return 匹配个数
     */
    public static int count(final CharSequence text, final Pattern pattern) {
        if (StrUtil.isBlank(text) || pattern == null) {
            return 0;
        }

        int count = 0;
        final Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    // ----------------------------------------------------------------------------------------------- escape

    /**
     * 转义字符串，将正则的关键字转义
     *
     * @param text 文本
     * @return 转义后的文本
     */
    public static String escape(CharSequence text) {
        if (StrUtil.isBlank(text)) {
            return StrUtil.str(text);
        }

        final StringBuilder builder = new StringBuilder();
        int len = text.length();
        char current;
        for (int i = 0; i < len; i++) {
            current = text.charAt(i);
            if (REGEX_ESCAPE_CHARS.contains(current)) {
                builder.append('\\');
            }
            builder.append(current);
        }
        return builder.toString();
    }

    /**
     * 转义字符，将正则的关键字转义
     *
     * @param c 字符
     * @return 转义后的文本
     */
    public static String escape(char c) {
        final StringBuilder builder = new StringBuilder();
        if (REGEX_ESCAPE_CHARS.contains(c)) {
            builder.append('\\');
        }
        builder.append(c);
        return builder.toString();
    }

    // ----------------------------------------------------------------------------------------------- extract

    /**
     * 从content中匹配出多个值并根据template生成新的字符串<br> 匹配结束后会删除匹配内容之前的内容（包括匹配内容）<br> 例如：<br> content 2013年5月 pattern (.*?)年(.*?)月
     * template： $1-$2 return 2013-5
     *
     * @param content  被匹配的内容
     * @param regex    匹配正则字符串
     * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
     * @return 按照template拼接后的字符串
     */
    public static String extractMulti(CharSequence content, String regex, String template) {
        if (null == content || null == regex || null == template) {
            return null;
        }

        // Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        final Pattern pattern = getPattern(regex, Pattern.DOTALL);
        return extractMulti(pattern, content, template);
    }

    /**
     * 从content中匹配出多个值并根据template生成新的字符串<br> 例如：<br> content 2013年5月 pattern (.*?)年(.*?)月 template： $1-$2 return 2013-5
     *
     * @param pattern  匹配正则
     * @param content  被匹配的内容
     * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
     * @return 新字符串
     */
    public static String extractMulti(Pattern pattern, CharSequence content, String template) {
        if (null == content || null == pattern || null == template) {
            return null;
        }

        //提取模板中的编号
        final TreeSet<Integer> varNums = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return ObjectUtil.compare(o2, o1);
            }
        });
        Pattern groupPattern = getPattern(GROUP_VAR, 0);
        final Matcher matcherForTemplate = groupPattern.matcher(template);
        while (matcherForTemplate.find()) {
            varNums.add(Integer.parseInt(matcherForTemplate.group(1)));
        }

        final Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            for (Integer group : varNums) {
                template = template.replace("$" + group, matcher.group(group));
            }
            return template;
        }
        return null;
    }

    /**
     * 返回正则模式 {@link Pattern}
     *
     * @param regex 正则表达式
     * @param flag  匹配标志，可选值：{@link Pattern#CASE_INSENSITIVE}, {@link Pattern#MULTILINE}, {@link Pattern#DOTALL},
     *              {@link Pattern#UNICODE_CASE}, {@link Pattern#CANON_EQ}, {@link Pattern#UNIX_LINES},
     *              {@link Pattern#LITERAL}, {@link Pattern#UNICODE_CHARACTER_CLASS}, {@link Pattern#COMMENTS}
     * @return 正则模式 {@link Pattern}
     */
    public static Pattern getPattern(final String regex, int flag) {
        if (StrUtil.isBlank(regex)) {
            return null;
        }
        return Pattern.compile(regex, flag);
    }

    // ----------------------------------------------------------------------------------------------- get

    /**
     * 取得内容中匹配的所有结果
     *
     * @param <T>        集合类型
     * @param text       被查找的文本
     * @param regex      正则
     * @param group      正则的分组
     * @param collection 返回的集合类型
     * @return 结果集
     */
    public static <T extends Collection<String>> T getAll(CharSequence text, String regex, int group, T collection) {
        if (null == regex) {
            return collection;
        }

        return RegexUtil.getAll(text, Pattern.compile(regex, Pattern.DOTALL), group, collection);
    }

    /**
     * 取得内容中匹配的所有结果
     *
     * @param <T>        集合类型
     * @param text       被查找的文本
     * @param pattern    编译后的正则模式
     * @param group      正则的分组
     * @param collection 返回的集合类型
     * @return 结果集
     */
    public static <T extends Collection<String>> T getAll(CharSequence text, Pattern pattern, int group, T collection) {
        if (null == pattern || null == text) {
            return null;
        }

        if (null == collection) {
            throw new NullPointerException("Null collection param provided!");
        }

        final Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            collection.add(matcher.group(group));
        }
        return collection;
    }

    /**
     * 取得内容中匹配的所有结果
     *
     * @param text  被查找的文本
     * @param regex 正则
     * @param group 正则的分组
     * @return 结果列表
     * @since 3.0.6
     */
    public static List<String> getAll(CharSequence text, String regex, int group) {
        return getAll(text, regex, group, new ArrayList<String>());
    }

    /**
     * 获得匹配的字符串匹配到的所有分组
     *
     * @param text    被匹配的文本
     * @param pattern 编译后的正则模式
     * @return 匹配后得到的字符串数组，按照分组顺序依次列出，未匹配到返回空列表，任何一个参数为null返回null
     * @since 3.1.0
     */
    public static List<String> getAllGroups(CharSequence text, Pattern pattern) {
        return getAllGroups(text, pattern, true);
    }

    /**
     * 获得匹配的字符串匹配到的所有分组
     *
     * @param text       被匹配的文本
     * @param pattern    编译后的正则模式
     * @param withGroup0 是否包括分组0，此分组表示全匹配的信息
     * @return 匹配后得到的字符串数组，按照分组顺序依次列出，未匹配到返回空列表，任何一个参数为null返回null
     * @since 4.0.13
     */
    public static List<String> getAllGroups(CharSequence text, Pattern pattern, boolean withGroup0) {
        if (null == text || null == pattern) {
            return null;
        }

        ArrayList<String> result = new ArrayList<>();
        final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            final int startGroup = withGroup0 ? 0 : 1;
            final int groupCount = matcher.groupCount();
            for (int i = startGroup; i <= groupCount; i++) {
                result.add(matcher.group(i));
            }
        }
        return result;
    }

    public static List<String> getAllInJson(final String text, final String key) {
        String pattern = String.format("\"%s\":\"?(\\w+)\"?", key);
        return getAll(text, pattern);
    }

    public static List<String> getAll(final CharSequence text, final String regex) {
        return getAll(text, Pattern.compile(regex));
    }

    public static List<String> getAll(final CharSequence text, final Pattern pattern) {
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

    public static String getFirst(final CharSequence text, final String regex) {
        return get(text, regex, 0);
    }

    /**
     * 获得匹配的字符串
     *
     * @param text  被匹配的文本
     * @param regex 匹配的正则
     * @param group 匹配正则的分组序号
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String get(final CharSequence text, final String regex, final int group) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex)) {
            return null;
        }

        Pattern pattern = Pattern.compile(regex);
        return get(text, pattern, group);
    }

    /**
     * 获得匹配的字符串，对应分组0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     *
     * @param text    被匹配的文本
     * @param pattern 编译后的正则模式
     * @param group   匹配正则的分组序号，0表示整个匹配内容，1表示第一个括号分组内容，依次类推
     * @return 匹配后得到的字符串，未匹配返回null
     */
    public static String get(final CharSequence text, final Pattern pattern, final int group) {
        if (StrUtil.isBlank(text) || pattern == null) {
            return null;
        }

        final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

    // ----------------------------------------------------------------------------------------------- matches

    /**
     * 校验文本是否满足正则表达式
     *
     * @param text  被校验的文本
     * @param regex 正则表达式
     * @return text、regex 如果为 null 或空字符串则返回 false
     */
    public static boolean matches(final CharSequence text, final String regex) {
        return matches(text, regex, 0);
    }

    /**
     * 校验文本是否满足正则表达式
     *
     * @param text  被校验的文本
     * @param regex 正则表达式
     * @param flag  匹配标志，可选值：{@link Pattern#CASE_INSENSITIVE}, {@link Pattern#MULTILINE}, {@link Pattern#DOTALL},
     *              {@link Pattern#UNICODE_CASE}, {@link Pattern#CANON_EQ}, {@link Pattern#UNIX_LINES},
     *              {@link Pattern#LITERAL}, {@link Pattern#UNICODE_CHARACTER_CLASS}, {@link Pattern#COMMENTS}
     * @return text、regex 如果为 null 或空字符串则返回 false
     */
    public static boolean matches(final CharSequence text, final String regex, final int flag) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex, flag);
        return matches(text, pattern);
    }

    /**
     * 校验文本是否满足正则表达式
     *
     * @param text    被校验的文本
     * @param pattern 正则模式 {@link Pattern}
     * @return text、regex 如果为 null 或空字符串则返回 false
     */
    public static boolean matches(final CharSequence text, final Pattern pattern) {
        if (StrUtil.isBlank(text) || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(text).matches();
    }

    // ----------------------------------------------------------------------------------------------- remove

    /**
     * 移除文本中所有匹配正则表达式的子字符串
     * <p>
     * 搬迁自 apache-common
     *
     * <pre>
     * StrUtil.removeAll(null, *)      = null
     * StrUtil.removeAll("any", (Pattern) null)  = "any"
     * StrUtil.removeAll("any", Pattern.compile(""))    = "any"
     * StrUtil.removeAll("any", Pattern.compile(".*"))  = ""
     * StrUtil.removeAll("any", Pattern.compile(".+"))  = ""
     * StrUtil.removeAll("abc", Pattern.compile(".?"))  = ""
     * StrUtil.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;"))      = "A\nB"
     * StrUtil.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("(?s)&lt;.*&gt;"))  = "AB"
     * StrUtil.removeAll("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;", Pattern.DOTALL))  = "AB"
     * StrUtil.removeAll("ABCabc123abc", Pattern.compile("[a-z]"))     = "ABC123"
     * </pre>
     *
     * @param text    被替换的文本
     * @param pattern 正则表达式
     * @return 移除后的字符串
     */
    public static String removeAll(final CharSequence text, final Pattern pattern) {
        return replaceAll(text, pattern, StrUtil.EMPTY);
    }

    /**
     * 替换文本中所有匹配正则表达式的子字符串为目标字符串。
     * <p>
     * 搬迁自 apache-common
     * <p>
     * 允许传入 null 值
     *
     * <pre>
     * RegexUtil.replaceAll(null, *, *)       = null
     * RegexUtil.replaceAll("any", (Pattern) null, *)   = "any"
     * RegexUtil.replaceAll("any", *, null)   = "any"
     * RegexUtil.replaceAll("", Pattern.compile(""), "zzz")    = "zzz"
     * RegexUtil.replaceAll("", Pattern.compile(".*"), "zzz")  = "zzz"
     * RegexUtil.replaceAll("", Pattern.compile(".+"), "zzz")  = ""
     * RegexUtil.replaceAll("abc", Pattern.compile(""), "ZZ")  = "ZZaZZbZZcZZ"
     * RegexUtil.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;"), "z")                 = "z\nz"
     * RegexUtil.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;", Pattern.DOTALL), "z") = "z"
     * RegexUtil.replaceAll("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("(?s)&lt;.*&gt;"), "z")             = "z"
     * RegexUtil.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_")       = "ABC___123"
     * RegexUtil.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_")  = "ABC_123"
     * RegexUtil.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "")   = "ABC123"
     * RegexUtil.replaceAll("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2")  = "Lorem_ipsum_dolor_sit"
     * </pre>
     *
     * @param text        被替换的文本
     * @param pattern     正则表达式
     * @param replacement 目标字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(final CharSequence text, final Pattern pattern, final String replacement) {
        if (StrUtil.isBlank(text) || pattern == null || replacement == null) {
            return StrUtil.str(text);
        }
        return pattern.matcher(text).replaceAll(replacement);
    }

    /**
     * 移除文本中所有匹配正则表达式的子字符串
     * <p>
     * 搬迁自 apache-common
     *
     * <pre>
     * StrUtil.removeAll(null, *)      = null
     * StrUtil.removeAll("any", (String) null)  = "any"
     * StrUtil.removeAll("any", "")    = "any"
     * StrUtil.removeAll("any", ".*")  = ""
     * StrUtil.removeAll("any", ".+")  = ""
     * StrUtil.removeAll("abc", ".?")  = ""
     * StrUtil.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\nB"
     * StrUtil.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
     * StrUtil.removeAll("ABCabc123abc", "[a-z]")     = "ABC123"
     * </pre>
     *
     * @param text  被替换的文本
     * @param regex 正则表达式
     * @return 移除后的字符串
     */
    public static String removeAll(final CharSequence text, final String regex) {
        return replaceAll(text, regex, StrUtil.EMPTY);
    }

    /**
     * 替换文本中所有匹配正则表达式的子字符串为目标字符串。
     * <p>
     * 搬迁自 apache-common
     * <p>
     * 允许传入 null 值
     *
     * <pre>
     * RegexUtil.replaceAll(null, *, *)       = null
     * RegexUtil.replaceAll("any", (String) null, *)   = "any"
     * RegexUtil.replaceAll("any", *, null)   = "any"
     * RegexUtil.replaceAll("", "", "zzz")    = "zzz"
     * RegexUtil.replaceAll("", ".*", "zzz")  = "zzz"
     * RegexUtil.replaceAll("", ".+", "zzz")  = ""
     * RegexUtil.replaceAll("abc", "", "ZZ")  = "ZZaZZbZZcZZ"
     * RegexUtil.replaceAll("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\nz"
     * RegexUtil.replaceAll("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
     * RegexUtil.replaceAll("ABCabc123", "[a-z]", "_")       = "ABC___123"
     * RegexUtil.replaceAll("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
     * RegexUtil.replaceAll("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
     * RegexUtil.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum_dolor_sit"
     * </pre>
     *
     * @param text        被替换的文本
     * @param regex       正则表达式
     * @param replacement 目标字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(final CharSequence text, final String regex, final String replacement) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex) || replacement == null) {
            return StrUtil.str(text);
        }
        Pattern pattern = Pattern.compile(regex);
        return replaceAll(text, pattern, replacement);
    }

    /**
     * 移除文本中第一个匹配正则表达式的子字符串
     * <p>
     * 搬迁自 apache-common
     *
     * <pre>
     * StrUtil.removeFirst(null, *)      = null
     * StrUtil.removeFirst("any", (String) null)  = "any"
     * StrUtil.removeFirst("any", "")    = "any"
     * StrUtil.removeFirst("any", ".*")  = ""
     * StrUtil.removeFirst("any", ".+")  = ""
     * StrUtil.removeFirst("abc", ".?")  = "bc"
     * StrUtil.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\n&lt;__&gt;B"
     * StrUtil.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
     * StrUtil.removeFirst("ABCabc123", "[a-z]")          = "ABCbc123"
     * StrUtil.removeFirst("ABCabc123abc", "[a-z]+")      = "ABC123abc"
     * </pre>
     *
     * @param text  被替换的文本
     * @param regex 正则表达式
     * @return 移除后的字符串
     */
    public static String removeFirst(final CharSequence text, final String regex) {
        Pattern pattern = Pattern.compile(regex);
        return removeFirst(text, pattern);
    }

    // ----------------------------------------------------------------------------------------------- replace

    /**
     * 移除文本中第一个匹配正则表达式的子字符串
     * <p>
     * 搬迁自 apache-common
     *
     * <pre>
     * StrUtil.removeFirst(null, *)      = null
     * StrUtil.removeFirst("any", (Pattern) null)  = "any"
     * StrUtil.removeFirst("any", Pattern.compile(""))    = "any"
     * StrUtil.removeFirst("any", Pattern.compile(".*"))  = ""
     * StrUtil.removeFirst("any", Pattern.compile(".+"))  = ""
     * StrUtil.removeFirst("abc", Pattern.compile(".?"))  = "bc"
     * StrUtil.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("&lt;.*&gt;"))      = "A\n&lt;__&gt;B"
     * StrUtil.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", Pattern.compile("(?s)&lt;.*&gt;"))  = "AB"
     * StrUtil.removeFirst("ABCabc123", Pattern.compile("[a-z]"))          = "ABCbc123"
     * StrUtil.removeFirst("ABCabc123abc", Pattern.compile("[a-z]+"))      = "ABC123abc"
     * </pre>
     *
     * @param text    被替换的文本
     * @param pattern 正则表达式
     * @return 移除后的字符串
     */
    public static String removeFirst(final CharSequence text, final Pattern pattern) {
        return replaceFirst(text, pattern, StrUtil.EMPTY);
    }

    /**
     * 替换文本中第一个匹配正则表达式的子字符串为目标字符串
     *
     * <pre>
     * RegexUtil.replaceFirst(null, *, *)       = null
     * RegexUtil.replaceFirst("any", (Pattern) null, *)   = "any"
     * RegexUtil.replaceFirst("any", *, null)   = "any"
     * RegexUtil.replaceFirst("", Pattern.compile(""), "zzz")    = "zzz"
     * RegexUtil.replaceFirst("", Pattern.compile(".*"), "zzz")  = "zzz"
     * RegexUtil.replaceFirst("", Pattern.compile(".+"), "zzz")  = ""
     * RegexUtil.replaceFirst("abc", Pattern.compile(""), "ZZ")  = "ZZabc"
     * RegexUtil.replaceFirst("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("&lt;.*&gt;"), "z")      = "z\n&lt;__&gt;"
     * RegexUtil.replaceFirst("&lt;__&gt;\n&lt;__&gt;", Pattern.compile("(?s)&lt;.*&gt;"), "z")  = "z"
     * RegexUtil.replaceFirst("ABCabc123", Pattern.compile("[a-z]"), "_")          = "ABC_bc123"
     * RegexUtil.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "_")  = "ABC_123abc"
     * RegexUtil.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "")   = "ABC123abc"
     * RegexUtil.replaceFirst("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2")  = "Lorem_ipsum  dolor   sit"
     * </pre>
     *
     * @param text        被替换的文本
     * @param pattern     正则表达式
     * @param replacement 目标字符串
     * @return 替换后的文本
     */
    public static String replaceFirst(final CharSequence text, final Pattern pattern, final String replacement) {
        if (StrUtil.isBlank(text) || pattern == null || replacement == null) {
            return StrUtil.str(text);
        }
        return pattern.matcher(text).replaceFirst(replacement);
    }

    /**
     * 删除正则匹配到的文本之前的字符 如果没有找到，则返回原文
     *
     * @param text  被查找的文本
     * @param regex 定位正则
     * @return 删除前缀后的新内容
     */
    public static String removePre(final CharSequence text, final String regex) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex)) {
            return StrUtil.str(text);
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return StrUtil.sub(text, matcher.end(), text.length());
        }
        return StrUtil.str(text);
    }

    /**
     * 替换所有正则匹配的文本，并使用自定义函数决定如何替换
     *
     * @param text     要替换的字符串
     * @param regex    用于匹配的正则式
     * @param callback 决定如何替换的函数
     * @return 替换后的文本
     * @since 4.2.2
     */
    public static String replaceAll(final CharSequence text, final String regex, Func1<Matcher, String> callback) {
        return replaceAll(text, Pattern.compile(regex), callback);
    }

    /**
     * 替换所有正则匹配的文本，并使用自定义函数决定如何替换
     *
     * @param text     要替换的字符串
     * @param pattern  用于匹配的正则式
     * @param callback 决定如何替换的函数,可能被多次调用（当有多个匹配时）
     * @return 替换后的字符串
     * @since 4.2.2
     */
    public static String replaceAll(final CharSequence text, final Pattern pattern, Func1<Matcher, String> callback) {
        if (StrUtil.isBlank(text) || pattern == null || callback == null) {
            return StrUtil.str(text);
        }

        final Matcher matcher = pattern.matcher(text);
        final StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(buffer, callback.call(matcher));
            } catch (Exception e) {
                throw new UtilException(e);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static String replaceAll(String text, String regex, List<String> values) {
        Pattern pattern = Pattern.compile(regex);
        return replaceAll(text, pattern, values);
    }

    public static String replaceAll(String text, Pattern pattern, List<String> values) {
        Matcher matcher = pattern.matcher(text);
        final StringBuffer buffer = new StringBuffer();
        Iterator<String> iterator = values.iterator();
        while (matcher.find() && iterator.hasNext()) {
            try {
                matcher.appendReplacement(buffer, iterator.next());
            } catch (Exception e) {
                throw new UtilException(e);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 替换文本中第一个匹配正则表达式的子字符串为目标字符串
     *
     * <pre>
     * RegexUtil.replaceFirst(null, *, *)       = null
     * RegexUtil.replaceFirst("any", (String) null, *)   = "any"
     * RegexUtil.replaceFirst("any", *, null)   = "any"
     * RegexUtil.replaceFirst("", "", "zzz")    = "zzz"
     * RegexUtil.replaceFirst("", ".*", "zzz")  = "zzz"
     * RegexUtil.replaceFirst("", ".+", "zzz")  = ""
     * RegexUtil.replaceFirst("abc", "", "ZZ")  = "ZZabc"
     * RegexUtil.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\n&lt;__&gt;"
     * RegexUtil.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
     * RegexUtil.replaceFirst("ABCabc123", "[a-z]", "_")          = "ABC_bc123"
     * RegexUtil.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_")  = "ABC_123abc"
     * RegexUtil.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "")   = "ABC123abc"
     * RegexUtil.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2")  = "Lorem_ipsum  dolor   sit"
     * </pre>
     *
     * @param text        被替换的文本
     * @param regex       正则表达式
     * @param replacement 目标字符串
     * @return 替换后的文本
     */
    public static String replaceFirst(final CharSequence text, final String regex, final String replacement) {
        if (StrUtil.isBlank(text) || StrUtil.isBlank(regex) || replacement == null) {
            return StrUtil.str(text);
        }
        Pattern pattern = Pattern.compile(regex);
        return replaceFirst(text, pattern, replacement);
    }

    /********************************************
     * 最常用的正则
     ********************************************/
    /**
     * 验证全是汉字字符 描述：校验字符串中只能有中文字符（不包括中文标点符号）。 匹配： 春眠不觉晓 不匹配：春眠不觉晓，
     */
    public static boolean isAllChineseChar(String content) {
        return checkMatches(RegexUtil.REGEX_ONLY_CHINESE_CHAR, content);
    }

    /**
     * 判断content是否匹配pattern的正则表达式
     */
    public static boolean checkMatches(String regex, String content) {
        return checkMatches(Pattern.compile(regex), content);
    }

    public static boolean checkMatches(Pattern p, String content) {
        Matcher m = p.matcher(content);
        boolean flag = m.matches();
        // if (flag) {
        //     System.out.println(content + "\t匹配正则表达式： " + p.pattern());
        // } else {
        //     System.out.println(content + "\t不匹配正则表达式： " + p.pattern());
        // }
        return flag;
    }

    /**
     * 验证全是英文字母
     */
    public static boolean isAllEnglishChar(String content) {
        return checkMatches(RegexUtil.REGEX_CHAR_EN_ALL, content);
    }

    /**
     * 验证全是小写字母
     */
    public static boolean isAllLowerEnglishChar(String content) {
        return checkMatches(RegexUtil.REGEX_CHAR_EN_LOWER_ALL, content);
    }

    /**
     * 验证全是数字 正确格式：任意位数的数字
     */
    public static boolean isAllNumber(String content) {
        return checkMatches(RegexUtil.REGEX_NUM_ALL, content);
    }

    /**
     * 验证全是大写字母
     */
    public static boolean isAllUpperEnglishChar(String content) {
        return checkMatches(RegexUtil.REGEX_CHAR_EN_UPPER_ALL, content);
    }

    /**
     * 验证全是单词字符，即只能由数字、26个英文字母或者下划线组成
     */
    public static boolean isAllWordChar(String content) {
        return checkMatches(RegexUtil.REGEX_CHAR_UNI_WORD_ALL, content);
    }

    /**
     * 验证字符串至少是N位的数字
     */
    public static boolean isLeastNDigitNumber(String content, int n) {
        String format = String.format(RegexUtil.REGEX_MORE_THAN_N_NUMBER, n);
        return checkMatches(format, content);
    }

    /**
     * 验证字符串是M到N位之间的数字
     */
    public static boolean isMToNDigitNumber(String content, int m, int n) {
        String format = String.format(RegexUtil.REGEX_M_TO_N_NUMBER, m, n);
        return checkMatches(format, content);
    }

    /**
     * 验证是否为N位的数字
     */
    public static boolean isNDigitNumber(String content, int n) {
        String format = String.format(RegexUtil.REGEX_N_NUMBER, n);
        return checkMatches(format, content);
    }

    /**
     * 验证不含任何数字
     */
    public static boolean isNoneNumber(String content) {
        return checkMatches(RegexUtil.REGEX_NUM_NONE, content);
    }

    /**
     * 验证全部都不是单词字符，即不能由数字、26个英文字母或者下划线组成
     */
    public static boolean isNoneWordChar(String content) {
        return checkMatches(RegexUtil.REGEX_CHAR_UNI_WORD_NONE, content);
    }

    /********************************************
     * 特定数字
     ********************************************/

    /**
     * 校验时间。 描述：时、分、秒必须是有效数字，如果数值不是两位数，十位需要补零。 匹配：00:00:00 | 23:59:59 | 17:06:30 不匹配：17:6:30 | 24:16:30
     */
    public static boolean isValidTime(String content) {
        return checkMatches(RegexUtil.REGEX_TIME, content);
    }

    /**
     * 校验日期。 日期满足以下条件： 格式yyyy-MM-dd或yyyy-M-d 连字符可以没有或是“-”、“/”、“.”之一 闰年的二月可以有29日；而平年不可以。
     * 一、三、五、七、八、十、十二月为31日。四、六、九、十一月为30日。 匹配：2016/1/1 | 2016/01/01 | 20160101 | 2016-01-01 | 2016.01.01 | 2000-02-29
     * 不匹配：2001-02-29 | 2016/12/32 | 2016/6/31 | 2016/13/1 | 2016/0/1
     */
    public static boolean isValidateDate(String content) {
        return checkMatches(RegexUtil.REGEX_DATE, content);
    }

    /**
     * 校验有效邮箱 描述：不允许使用IP作为域名，如 : hello@154.145.68.12 '@'符号前的邮箱用户和'.'符号前的域名(domain)必须满足以下条件： 字符只能是英文字母、数字、下划线'_'、'.'、'-'
     * ； 首字符必须为字母或数字； '_'、'.'、'-' 不能连续出现。 域名的根域只能为字母，且至少为两个字符。 匹配：he_llo@worl.d.com | hel.l-o@wor-ld.museum |
     * h1ello@123.com 不匹配：hello@worl_d.com | he&llo@world.co1 | .hello@wor#.co.uk
     */
    public static boolean isValidateEmail(String content) {
        return checkMatches(RegexUtil.REGEX_EMAIL, content);
    }

    /**
     * 验证15位身份证号有效 描述：由15位数字组成。排列顺序从左至右依次为：六位数字地区码；六位数字出生日期；三位顺序号，其中15位男为单数，女为双数。 匹配：110001700101031
     * 不匹配：110001701501031
     */
    public static boolean isValidateIdCard15(String content) {
        return checkMatches(RegexUtil.REGEX_ID_CARD_15, content);
    }

    /**
     * 验证18位身份证号有效 描述：由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地区码；八位数字出生日期；三位数字顺序码和一位数字校验码（也可能是X）。 匹配：110001199001010310 |
     * 11000019900101015X 不匹配：990000199001010310 | 110001199013010310
     */
    public static boolean isValidateIdCard18(String content) {
        return checkMatches(RegexUtil.REGEX_ID_CARD_18, content);
    }

    /********************************************
     * 特定字符
     ********************************************/

    /**
     * 验证IPv4有效 描述：IP地址是一个32位的二进制数，通常被分割为4个“8位二进制数”（也就是4个字节）。 IP地址通常用“点分十进制”表示成（a.b.c.d）的形式，其中，a,b,c,d都是0~255之间的十进制整数。
     * 匹配：0.0.0.0 | 255.255.255.255 | 127.0.0.1 不匹配：10.10.10 | 10.10.10.256
     */
    public static boolean isValidateIpv4(String content) {
        return checkMatches(RegexUtil.REGEX_IPV4, content);
    }

    /**
     * 验证IPv6有效 描述：IPv6的128位地址通常写成8组，每组为四个十六进制数的形式。 IPv6地址可以表示为以下形式： IPv6 地址 零压缩 IPv6 地址(section 2.2 of rfc5952)
     * 带有本地链接区域索引的 IPv6 地址 (section 11 of rfc4007) 嵌入IPv4的 IPv6 地址(section 2 of rfc6052 映射IPv4的 IPv6 地址 (section 2.1 of
     * rfc2765) 翻译IPv4的 IPv6 地址 (section 2.1 of rfc2765) 匹配：1:2:3:4:5:6:7:8 | 1:: | 1::8 | 1::6:7:8 | 1::5:6:7:8 |
     * 1::4:5:6:7:8 | 1::3:4:5:6:7:8 | ::2:3:4:5:6:7:8 | 1:2:3:4:5:6:7:: | 1:2:3:4:5:6::8 | 1:2:3:4:5::8 | 1:2:3:4::8 |
     * 1:2:3::8 | 1:2::8 | 1::8 | ::8 | fe80::7:8%1 | ::255.255.255.255 | 2001:db8:3:4::192.0.2.33 | 64:ff9b::192.0.2.33
     * 不匹配：1.2.3.4.5.6.7.8 | 1::2::3
     */
    public static boolean isValidateIpv6(String content) {
        return checkMatches(RegexUtil.REGEX_IPV6, content);
    }

    /**
     * 校验中国手机号码 描述：中国手机号码正确格式：11位数字。
     * 移动有16个号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188。其中147、157、188是3G号段，其他都是2G号段。
     * 联通有7种号段：130、131、132、155、156、185、186。其中186是3G（WCDMA）号段，其余为2G号段。
     * 电信有4个号段：133、153、180、189。其中189是3G号段（CDMA2000），133号段主要用作无线网卡号。 总结：13开头手机号0-9；15开头手机号0-3、5-9；18开头手机号0、2、5-9。
     * 此外，中国在国际上的区号为86，所以手机号开头有+86、86也是合法的。 匹配：+86 18012345678 | 86 18012345678 | 15812345678 不匹配：15412345678 |
     * 12912345678 | 180123456789
     */
    public static boolean isValidateMobile(String content) {
        return checkMatches(RegexUtil.REGEX_MOBILE, content);
    }

    /**
     * 校验中国固话号码（大陆地区） 描述：固话号码，必须加区号（以0开头）。 3位有效区号：010、020~029，固话位数为8位 4位有效区号：03xx开头到09xx，固话位数为7
     */
    public static boolean isValidatePhone(String content) {
        return checkMatches(RegexUtil.REGEX_PHONE, content);
    }

    /**
     * 校验URL。 描述：支持http、https、ftp、ftps。 匹配：http://google.com/help/me | http://www.google.com/help/me/ |
     * https://www.google.com/help.asp | ftp://www.google.com | ftps://google.org
     * 不匹配：http://un/www.google.com/index.asp
     */
    public static boolean isValidateUrl(String content) {
        return checkMatches(RegexUtil.REGEX_URI, content);
    }

    /**
     * 验证密码有效
     */
    public static boolean isValidateUsername(String content) {
        return checkMatches(RegexUtil.REGEX_USERNAME, content);
    }

    public static void main(String[] args) {
        checkMatches(REGEX_IPV4, "127.0.0.1");
        checkMatches(REGEX_IPV4, "0.0.0.0");
        checkMatches(REGEX_IPV4, "255.255.255.255");
    }

}
