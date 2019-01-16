package io.github.dunwu.utils.regex;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Zhang Peng
 * @date 2019-01-16
 * @see RegexHelper
 */
@DisplayName("正则校验测试例集")
public class RegexHelperTests {

    @Test
    public void test2() {
        String content = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":\"hello\"},\"allowed\":false,\"version\":0}";
        String content2 = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":hello},\"allowed\":false,\"version\":0}";
        List<String> matchValues = RegexHelper.getMatchValuesInJson(content, "judgementId");
        if (CollectionUtils.isNotEmpty(matchValues)) {
            matchValues.forEach(item -> {
                System.out.println(item);
            });
        }

        List<String> matchValues2 = RegexHelper.getMatchValuesInJson(content2, "judgementId");
        if (CollectionUtils.isNotEmpty(matchValues2)) {
            matchValues2.forEach(item -> {
                System.out.println(item);
            });
        }
    }


    @Nested
    @DisplayName("校验常用场景")
    class CheckCommon {
        @Test
        @DisplayName("正则校验15位身份证号")
        public void isValidIdCard15() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIdCard15("110001700101031"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIdCard15("110001701501031"));
        }

        @Test
        @DisplayName("正则校验18位身份证号")
        public void isValidIdCard18() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIdCard18("11000019900101015X"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIdCard18("990000199001010310"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIdCard18("110001199013010310"));
        }

        @Test
        @DisplayName("正则校验用户名有效")
        public void isValidUsername() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUsername("gdasg_"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidUsername("$dhsagk"));
        }

        @Test
        @DisplayName("正则校验有效邮箱")
        public void isValidEmail() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidEmail("he_llo@worl.d.com"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidEmail("hel.l-o@wor-ld.museum"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidEmail("h1ello@123.com"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidEmail("hello@worl_d.com"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidEmail("he&llo@world.co1"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidEmail(".hello@wor#.co.uk"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidEmail("gdsakh23124"));
        }

        @Test
        @DisplayName("正则校验URL")
        public void isValidUrl() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUrl("http://google.com/help/me"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUrl("http://www.google.com/help/me/"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUrl("https://www.google.com/help.asp"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUrl("ftp://www.google.com"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidUrl("ftps://google.org"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidUrl("http://un/www.google.com/index.asp"));
        }

        @Test
        @DisplayName("正则校验IPv4")
        public void isValidIpv4() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv4("0.0.0.0"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv4("255.255.255.255"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv4("127.0.0.1"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIpv4("10.10.10"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIpv4("10.10.10.2561"));
        }

        @Test
        @DisplayName("正则校验IPv6")
        public void isValidIpv6() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv6("1:2:3:4:5:6:7:8"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv6("1:2:3:4:5::8"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv6("::255.255.255.255"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidIpv6("1:2:3:4:5:6:7::"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIpv6("1.2.3.4.5.6.7.8"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidIpv6("1::2::3"));
        }

        @Test
        @DisplayName("正则校验时间")
        public void isValidTime() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidTime("00:00:00"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidTime("23:59:59"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidTime("17:06:30"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidTime("17:6:30"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidTime("24:16:30"));
        }

        @Test
        @DisplayName("正则校验日期")
        public void isValidDate() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("2016/1/1"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("2016/01/01"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("20160101"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("2016-01-01"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("2016.01.01"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidDate("2000-02-29"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidDate("2001-02-29"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidDate("2016/12/32"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidDate("2016.13.1"));
        }

        @Test
        @DisplayName("正则校验中国手机号码")
        public void isValidChinaMobile() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidChinaMobile("+86 18012345678"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidChinaMobile("86 18012345678"));
            Assertions.assertEquals(true, RegexHelper.Checker.isValidChinaMobile("15812345678"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidChinaMobile("15412345678"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidChinaMobile("12912345678"));
            Assertions.assertEquals(false, RegexHelper.Checker.isValidChinaMobile("180123456789"));
        }

        @Test
        @DisplayName("正则校验中国手机号码")
        public void isValidPhone() {
            Assertions.assertEquals(true, RegexHelper.Checker.isValidPhone("025-85951888"));
        }
    }


    @Nested
    @DisplayName("校验字符")
    class CheckChar {
        @Test
        @DisplayName("正则校验全是汉字字符")
        public void isAllChineseChar() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllChineseChar("春眠不觉晓"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllChineseChar("春眠不觉晓，"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllChineseChar("English"));
        }

        @Test
        @DisplayName("正则校验全是英文字母")
        public void isAllEnglishChar() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertEquals(true, RegexHelper.Checker.isAllEnglishChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllEnglishChar("How are you?"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllEnglishChar("你奈我何"));
        }

        @Test
        @DisplayName("正则校验全是大写字母")
        public void isAllUpperEnglishChar() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllUpperEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllUpperEnglishChar("abcdefghijklmnopqrstuvwxyz"));
        }

        @Test
        @DisplayName("正则校验全是小写字母")
        public void isAllLowerEnglishChar() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllLowerEnglishChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllLowerEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        }

        @Test
        @DisplayName("正则校验全是单词字符")
        public void isAllWordChar() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertEquals(true, RegexHelper.Checker.isAllWordChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(true, RegexHelper.Checker.isAllWordChar("0123456789"));
            Assertions.assertEquals(true, RegexHelper.Checker.isAllWordChar("_"));
        }

        @Test
        @DisplayName("正则校验全是非单词字符")
        public void isNoneWordChar() {
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneWordChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneWordChar("0123456789"));
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneWordChar("_"));
        }
    }


    @Nested
    @DisplayName("校验数字")
    class CheckNumber {

        @Test
        @DisplayName("正则校验全是数字")
        public void isAllNumber() {
            Assertions.assertEquals(true, RegexHelper.Checker.isAllNumber("0123456789"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllNumber("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllNumber("How are you?"));
            Assertions.assertEquals(false, RegexHelper.Checker.isAllNumber("你奈我何"));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        public void isNoneNumber() {
            Assertions.assertEquals(true, RegexHelper.Checker.isNoneNumber("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneNumber("0123456789"));
            Assertions.assertEquals(false, RegexHelper.Checker.isNoneNumber("test008"));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        public void isNDigitNumber() {
            Assertions.assertEquals(true, RegexHelper.Checker.isNDigitNumber("0123456789", 10));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        public void isLeastNDigitNumber() {
            Assertions.assertEquals(true, RegexHelper.Checker.isLeastNDigitNumber("0123456789", 5));
            Assertions.assertEquals(true, RegexHelper.Checker.isLeastNDigitNumber("0123456789", 10));
            Assertions.assertEquals(false, RegexHelper.Checker.isLeastNDigitNumber("0123456789", 11));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        public void isMToNDigitNumber() {
            Assertions.assertEquals(true, RegexHelper.Checker.isMToNDigitNumber("0123456789", 1, 10));
            Assertions.assertEquals(false, RegexHelper.Checker.isMToNDigitNumber("0123456789", 6, 9));
            Assertions.assertEquals(false, RegexHelper.Checker.isMToNDigitNumber("0123456789", 11, 20));
        }
    }
}
