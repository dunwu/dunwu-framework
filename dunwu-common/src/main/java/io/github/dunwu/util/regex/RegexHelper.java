package io.github.dunwu.util.regex;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2016/10/27.
 */
public class RegexHelper {

    public static List<String> getMatchValuesInJson(final String text, final String key) {
        String pattern = String.format("\"%s\":\"?(\\w+)\"?", key);
        return getMatchValues(text, pattern);
    }

    public static List<String> getMatchValues(final String text, final String pattern) {
        return getMatchValues(text, Pattern.compile(pattern));
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

    public static String replaceAllMatchContent(final String text, final String pattern, final String dest) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.replaceAll(dest);
    }

    public static class Checker {
        /** 最实用的正则 */
        public static final String REGEX_ID_CARD_15 =
            "^((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|"
                + "(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)"
                + "(\\d{3})$";
        public static final String REGEX_ID_CARD_18 =
            "^((1[1-5]|2[1-3]|3[1-7]|4[1-3]|5[0-4]|6[1-5])\\d{4})((\\d{4}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|"
                + "(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)"
                + "(\\d{3}(\\d|X))$";
        public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
        public static final String REGEX_EMAIL =
            "^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";
        public static final String REGEX_URI = "^(ht|f)(tp|tps)\\://[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3})?(/\\S*)?$";
        public static final String REGEX_IPV4 =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        public static final String REGEX_IPV6 =
            "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,"
                + "6}:[0-9a-fA-F]{1,"
                + "4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,"
                + "3}|"
                + "([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,"
                + "5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,"
                + "4})"
                + "{0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}"
                + "(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,"
                + "1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";
        public static final String REGEX_TIME = "^([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";
        public static final String REGEX_DATE =
            "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1"
                + "(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|"
                + "(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";
        public static final String REGEX_MOBILE = "^((\\+)?86\\s*)?((13[0-9])|(15([0-3]|[5-9]))|(18[0,2,5-9]))\\d{8}$";
        public static final String REGEX_PHONE = "^(010|02[0-9])(\\s|-)\\d{8}|(0[3-9]\\d{2})(\\s|-)\\d{7}$";

        /** 特定数字 */
        public static final String REGEX_NUM_ALL = "^[\\d]*$";
        public static final String REGEX_NUM_NONE = "^[\\D]*$";
        public static final String REGEX_N_NUMBER = "^\\d{%d}$";
        public static final String REGEX_MORE_THAN_N_NUMBER = "^\\d{%d,}$";
        public static final String REGEX_M_TO_N_NUMBER = "^\\d{%d,%d}$";

        /** 特定字符 */
        public static final String REGEX_CHAR_ZH_ALL = "^[\\u4e00-\\u9fa5]+$";
        public static final String REGEX_CHAR_EN_ALL = "^[A-Za-z]+$";
        public static final String REGEX_CHAR_EN_UPPER_ALL = "^[A-Z]+$";
        public static final String REGEX_CHAR_EN_LOWER_ALL = "^[a-z]+$";
        public static final String REGEX_CHAR_UNI_WORD_ALL = "^\\w+$";
        public static final String REGEX_CHAR_UNI_WORD_NONE = "^\\W+$";

        public static final String REGEX_MARKDOWN_IMAGE_TAG = "!\\[.+\\]";

        /******************************************** 最常用的正则 ********************************************/
        /**
         * 校验15位身份证号有效 描述：由15位数字组成。排列顺序从左至右依次为：六位数字地区码；六位数字出生日期；三位顺序号，其中15位男为单数，女为双数。 匹配：110001700101031
         * 不匹配：110001701501031
         */
        public static boolean isValidIdCard15(final String text) {
            return checkMatches(text, REGEX_ID_CARD_15);
        }

        /**
         * 判断content是否匹配pattern的正则表达式
         */
        public static boolean checkMatches(final String text, final String regex) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(text);
            return m.matches();
        }

        /**
         * 校验18位身份证号有效 描述：由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地区码；八位数字出生日期；三位数字顺序码和一位数字校验码（也可能是X）。 匹配：110001199001010310
         * |
         * 11000019900101015X 不匹配：990000199001010310 | 110001199013010310
         */
        public static boolean isValidIdCard18(final String text) {
            return checkMatches(text, REGEX_ID_CARD_18);
        }

        /**
         * 校验用户名有效
         */
        public static boolean isValidUsername(String text) {
            return checkMatches(text, REGEX_USERNAME);
        }

        /**
         * 校验有效邮箱 描述：不允许使用IP作为域名，如 : hello@154.145.68.12 '@'符号前的邮箱用户和'.'符号前的域名(domain)必须满足以下条件：
         * 字符只能是英文字母、数字、下划线'_'、'.'、'-'
         * ； 首字符必须为字母或数字； '_'、'.'、'-' 不能连续出现。 域名的根域只能为字母，且至少为两个字符。 匹配：he_llo@worl.d.com | hel.l-o@wor-ld.museum |
         * h1ello@123.com 不匹配：hello@worl_d.com | he&llo@world.co1 | .hello@wor#.co.uk
         */
        public static boolean isValidEmail(final String text) {
            return checkMatches(text, REGEX_EMAIL);
        }

        /**
         * 校验URL。 描述：支持http、https、ftp、ftps。 匹配：http://google.com/help/me | http://www.google.com/help/me/ |
         * https://www.google.com/help.asp | ftp://www.google.com | ftps://google.org
         * 不匹配：http://un/www.google.com/index.asp
         */
        public static boolean isValidUrl(final String text) {
            return checkMatches(text, REGEX_URI);
        }

        /**
         * 校验IPv4 描述：IP地址是一个32位的二进制数，通常被分割为4个“8位二进制数”（也就是4个字节）。 IP地址通常用“点分十进制”表示成（a.b.c.d）的形式，其中，a,b,c,d都是0~255之间的十进制整数。
         * 匹配：0.0.0.0 | 255.255.255.255 | 127.0.0.1 不匹配：10.10.10 | 10.10.10.256
         */
        public static boolean isValidIpv4(final String text) {
            return checkMatches(text, REGEX_IPV4);
        }

        /**
         * 校验IPv6 描述：IPv6的128位地址通常写成8组，每组为四个十六进制数的形式。 IPv6地址可以表示为以下形式： IPv6 地址 零压缩 IPv6 地址(section 2.2 of rfc5952)
         * 带有本地链接区域索引的 IPv6 地址 (section 11 of rfc4007) 嵌入IPv4的 IPv6 地址(section 2 of rfc6052 映射IPv4的 IPv6 地址 (section 2.1
         * of
         * rfc2765) 翻译IPv4的 IPv6 地址 (section 2.1 of rfc2765) 匹配：1:2:3:4:5:6:7:8 | 1:: | 1::8 | 1::6:7:8 | 1::5:6:7:8 |
         * 1::4:5:6:7:8 | 1::3:4:5:6:7:8 | ::2:3:4:5:6:7:8 | 1:2:3:4:5:6:7:: | 1:2:3:4:5:6::8 | 1:2:3:4:5::8 |
         * 1:2:3:4::8 |
         * 1:2:3::8 | 1:2::8 | 1::8 | ::8 | fe80::7:8%1 | ::255.255.255.255 | 2001:db8:3:4::192.0.2.33 |
         * 64:ff9b::192.0.2.33
         * 不匹配：1.2.3.4.5.6.7.8 | 1::2::3
         */
        public static boolean isValidIpv6(final String text) {
            return checkMatches(text, REGEX_IPV6);
        }

        /**
         * 校验时间。 描述：时、分、秒必须是有效数字，如果数值不是两位数，十位需要补零。 匹配：00:00:00 | 23:59:59 | 17:06:30 不匹配：17:6:30 | 24:16:30
         */
        public static boolean isValidTime(final String text) {
            return checkMatches(text, REGEX_TIME);
        }

        /**
         * 校验日期。 日期满足以下条件： 格式yyyy-MM-dd或yyyy-M-d 连字符可以没有或是“-”、“/”、“.”之一 闰年的二月可以有29日；而平年不可以。
         * 一、三、五、七、八、十、十二月为31日。四、六、九、十一月为30日。 匹配：2016/1/1 | 2016/01/01 | 20160101 | 2016-01-01 | 2016.01.01 | 2000-02-29
         * 不匹配：2001-02-29 | 2016/12/32 | 2016/6/31 | 2016/13/1 | 2016/0/1
         */
        public static boolean isValidDate(final String text) {
            return checkMatches(text, REGEX_DATE);
        }

        /**
         * 校验中国手机号码 描述：中国手机号码正确格式：11位数字。
         * 移动有16个号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188。其中147、157、188是3G号段，其他都是2G号段。
         * 联通有7种号段：130、131、132、155、156、185、186。其中186是3G（WCDMA）号段，其余为2G号段。
         * 电信有4个号段：133、153、180、189。其中189是3G号段（CDMA2000），133号段主要用作无线网卡号。 总结：13开头手机号0-9；15开头手机号0-3、5-9；18开头手机号0、2、5-9。
         * 此外，中国在国际上的区号为86，所以手机号开头有+86、86也是合法的。 匹配：+86 18012345678 | 86 18012345678 | 15812345678 不匹配：15412345678 |
         * 12912345678 | 180123456789
         */
        public static boolean isValidChinaMobile(final String text) {
            return checkMatches(text, REGEX_MOBILE);
        }

        /******************************************** 特定数字 ********************************************/

        /**
         * 校验中国固话号码（大陆地区） 描述：固话号码，必须加区号（以0开头）。 3位有效区号：010、020~029，固话位数为8位 4位有效区号：03xx开头到09xx，固话位数为7
         */
        public static boolean isValidPhone(final String text) {
            return checkMatches(text, REGEX_PHONE);
        }

        /**
         * 校验全是数字 正确格式：任意位数的数字
         */
        public static boolean isAllNumber(final String text) {
            return checkMatches(text, REGEX_NUM_ALL);
        }

        /**
         * 校验不含任何数字
         */
        public static boolean isNoneNumber(final String text) {
            return checkMatches(text, REGEX_NUM_NONE);
        }

        /**
         * 校验是否为N位的数字
         */
        public static boolean isNDigitNumber(final String text, int n) {
            String format = String.format(REGEX_N_NUMBER, n);
            return checkMatches(text, format);
        }

        /**
         * 校验字符串至少是N位的数字
         */
        public static boolean isLeastNDigitNumber(final String text, int n) {
            String format = String.format(REGEX_MORE_THAN_N_NUMBER, n);
            return checkMatches(text, format);
        }

        /******************************************** 特定字符 ********************************************/

        /**
         * 校验字符串是M到N位之间的数字
         */
        public static boolean isMToNDigitNumber(final String text, int m, int n) {
            String format = String.format(REGEX_M_TO_N_NUMBER, m, n);
            return checkMatches(text, format);
        }

        /**
         * 校验全是汉字字符 描述：校验字符串中只能有中文字符（不包括中文标点符号）。 匹配： 春眠不觉晓 不匹配：春眠不觉晓，
         */
        public static boolean isAllChineseChar(final String text) {
            return checkMatches(text, REGEX_CHAR_ZH_ALL);
        }

        /**
         * 校验全是英文字母
         */
        public static boolean isAllEnglishChar(final String text) {
            return checkMatches(text, REGEX_CHAR_EN_ALL);
        }

        /**
         * 校验全是大写字母
         */
        public static boolean isAllUpperEnglishChar(final String text) {
            return checkMatches(text, REGEX_CHAR_EN_UPPER_ALL);
        }

        /**
         * 校验全是小写字母
         */
        public static boolean isAllLowerEnglishChar(final String text) {
            return checkMatches(text, REGEX_CHAR_EN_LOWER_ALL);
        }

        /**
         * 校验全是单词字符，即只能由数字、26个英文字母或者下划线组成
         */
        public static boolean isAllWordChar(final String text) {
            return checkMatches(text, REGEX_CHAR_UNI_WORD_ALL);
        }

        /**
         * 校验全部都不是单词字符，即不能由数字、26个英文字母或者下划线组成
         */
        public static boolean isNoneWordChar(final String text) {
            return checkMatches(text, REGEX_CHAR_UNI_WORD_NONE);
        }

        public static boolean isMarkdownImageTag(final String text) {
            return checkMatches(text, REGEX_MARKDOWN_IMAGE_TAG);
        }
    }
}
