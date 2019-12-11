package io.github.dunwu.util.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-09
 */
public class ValidatorUtilTest {

    @Nested
    @DisplayName("校验常用场景")
    class CheckCommon {

        @Test
        @DisplayName("正则校验邮政编码")
        void isZipCode() {
            Assertions.assertTrue(ValidatorUtil.isZipCode("243000"));
            Assertions.assertFalse(ValidatorUtil.isZipCode("2430001"));
        }

        @Test
        @DisplayName("正则校验有效邮箱")
        void isEmail() {
            Assertions.assertTrue(ValidatorUtil.isEmail("he_llo@worl.d.com"));
            Assertions.assertTrue(ValidatorUtil.isEmail("hel.l-o@wor-ld.museum"));
            Assertions.assertTrue(ValidatorUtil.isEmail("h1ello@123.com"));
            Assertions.assertTrue(ValidatorUtil.isEmail("abc@abc.com"));
            Assertions.assertTrue(ValidatorUtil.isEmail("he&llo@world.co1"));

            Assertions.assertFalse(ValidatorUtil.isEmail("hello@worl_d.com"));
            Assertions.assertFalse(ValidatorUtil.isEmail(".hello@wor#.co.uk"));
            Assertions.assertFalse(ValidatorUtil.isEmail("gdsakh23124"));
            Assertions.assertFalse(ValidatorUtil.isEmail("abc"));
            Assertions.assertFalse(ValidatorUtil.isEmail("abc@a"));
            Assertions.assertFalse(ValidatorUtil.isEmail("中文@a.com"));
        }

        @Test
        @DisplayName("正则校验15位身份证号")
        void isIdCard15() {
            Assertions.assertTrue(ValidatorUtil.isCitizenId15("110001700101031"));
            Assertions.assertFalse(ValidatorUtil.isCitizenId15("110001701501031"));
        }

        @Test
        @DisplayName("正则校验18位身份证号")
        void isIdCard18() {
            Assertions.assertTrue(ValidatorUtil.isCitizenId18("11000019900101015X"));
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("990000199001010310"));
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("110001199013010310"));
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("440101198909204518"));
            // 含字母
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("440101198987754ab"));
            // 月份不对
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("440101198987754122"));
            // 日期不对
            Assertions.assertFalse(ValidatorUtil.isCitizenId18("440101891232451"));
        }

        @Test
        @DisplayName("正则校验IPv4")
        void isIpv4() {
            Assertions.assertTrue(ValidatorUtil.isIpv4("0.0.0.0"));
            Assertions.assertTrue(ValidatorUtil.isIpv4("255.255.255.255"));
            Assertions.assertTrue(ValidatorUtil.isIpv4("127.0.0.1"));
            Assertions.assertFalse(ValidatorUtil.isIpv4("10.10.10"));
            Assertions.assertFalse(ValidatorUtil.isIpv4("10.10.10.2561"));
        }

        @Test
        @DisplayName("正则校验IPv6")
        void isIpv6() {
            Assertions.assertTrue(ValidatorUtil.isIpv6("1:2:3:4:5:6:7:8"));
            Assertions.assertTrue(ValidatorUtil.isIpv6("1:2:3:4:5::8"));
            Assertions.assertTrue(ValidatorUtil.isIpv6("::255.255.255.255"));
            Assertions.assertTrue(ValidatorUtil.isIpv6("1:2:3:4:5:6:7::"));
            Assertions.assertFalse(ValidatorUtil.isIpv6("1.2.3.4.5.6.7.8"));
            Assertions.assertFalse(ValidatorUtil.isIpv6("1::2::3"));
        }

        @Test
        @DisplayName("正则校验 MAC 地址")
        void isMac() {
            Assertions.assertTrue(ValidatorUtil.isMac("01:23:45:67:89:ab"));
            Assertions.assertTrue(ValidatorUtil.isMac("01:23:45:67:89:AB"));
            Assertions.assertTrue(ValidatorUtil.isMac("fE:dC:bA:98:76:54"));
            Assertions.assertTrue(ValidatorUtil.isMac("A0:B1:C2:D3:E4:F5"));
            Assertions.assertTrue(ValidatorUtil.isMac("A0-B1-C2-D3-E4-F5"));
            Assertions.assertTrue(ValidatorUtil.isMac("ffff.ffff.ffff"));
            Assertions.assertTrue(ValidatorUtil.isMac("1234.abcd.1234"));
            Assertions.assertTrue(ValidatorUtil.isMac("001e.1324.683f"));

            Assertions.assertFalse(ValidatorUtil.isMac("01:23:45:67:89:ab:cd"));
            Assertions.assertFalse(ValidatorUtil.isMac("01:23:45:67:89:Az"));
            Assertions.assertFalse(ValidatorUtil.isMac("01:23:45:56:"));
        }

        @Test
        @DisplayName("正则校验座机号码")
        void isPhone() {
            Assertions.assertFalse(ValidatorUtil.isPhone(null));
            Assertions.assertFalse(ValidatorUtil.isPhone("8802973a"));
            Assertions.assertFalse(ValidatorUtil.isPhone("8908222222"));
            Assertions.assertFalse(ValidatorUtil.isPhone("89081"));

            Assertions.assertTrue(ValidatorUtil.isPhone("89019739"));
            Assertions.assertTrue(ValidatorUtil.isPhone("020-89019739"));
            Assertions.assertTrue(ValidatorUtil.isPhone("025-85951888"));
        }

        @Test
        @DisplayName("正则校验手机号码")
        void isMobile() {
            Assertions.assertFalse(ValidatorUtil.isMobile(null));
            Assertions.assertFalse(ValidatorUtil.isMobile(""));
            Assertions.assertFalse(ValidatorUtil.isMobile("1234a"));
            Assertions.assertFalse(ValidatorUtil.isMobile("1234561"));
            Assertions.assertFalse(ValidatorUtil.isMobile("11170998762"));

            Assertions.assertTrue(ValidatorUtil.isMobile("13970998762"));
            Assertions.assertTrue(ValidatorUtil.isMobile("15812345678"));
            Assertions.assertTrue(ValidatorUtil.isMobile("15812345678"));
            Assertions.assertTrue(ValidatorUtil.isMobile("+8618012345678"));
            Assertions.assertTrue(ValidatorUtil.isMobile("+86 18012345678"));
            Assertions.assertTrue(ValidatorUtil.isMobile("8618012345678"));
            Assertions.assertTrue(ValidatorUtil.isMobile("86 18012345678"));
        }

        @Test
        @DisplayName("正则校验日期")
        void isDate() {
            Assertions.assertTrue(ValidatorUtil.isDate("2016/1/1"));
            Assertions.assertTrue(ValidatorUtil.isDate("2016/01/01"));
            Assertions.assertTrue(ValidatorUtil.isDate("20160101"));
            Assertions.assertTrue(ValidatorUtil.isDate("2016-01-01"));
            Assertions.assertTrue(ValidatorUtil.isDate("2016.01.01"));
            Assertions.assertTrue(ValidatorUtil.isDate("2000-02-29"));
            Assertions.assertTrue(ValidatorUtil.isDate("2011-03-11"));
            Assertions.assertTrue(ValidatorUtil.isDate("2012-02-29"));

            Assertions.assertFalse(ValidatorUtil.isDate("2001-02-29"));
            Assertions.assertFalse(ValidatorUtil.isDate("2016/12/32"));
            Assertions.assertFalse(ValidatorUtil.isDate("2016.13.1"));
            Assertions.assertFalse(ValidatorUtil.isDate("2011-02-29"));
            Assertions.assertFalse(ValidatorUtil.isDate("201a-02-30"));
            Assertions.assertFalse(ValidatorUtil.isDate("2011-0211"));
        }

        @Test
        @DisplayName("正则校验时间")
        void isTime() {
            Assertions.assertTrue(ValidatorUtil.isTime("00:00:00"));
            Assertions.assertTrue(ValidatorUtil.isTime("23:59:59"));
            Assertions.assertTrue(ValidatorUtil.isTime("17:06:30"));
            Assertions.assertFalse(ValidatorUtil.isTime("17:6:30"));
            Assertions.assertFalse(ValidatorUtil.isTime("13:69:30"));
            Assertions.assertFalse(ValidatorUtil.isTime("24:16:30"));
        }

        @Test
        @DisplayName("正则校验URL")
        void isUrl() {
            Assertions.assertTrue(ValidatorUtil.isUrl("http://google.com/help/me"));
            Assertions.assertTrue(ValidatorUtil.isUrl("http://www.google.com/help/me/"));
            Assertions.assertTrue(ValidatorUtil.isUrl("https://www.google.com/help.asp"));
            Assertions.assertTrue(ValidatorUtil.isUrl("ftp://www.google.com"));
            Assertions.assertTrue(ValidatorUtil.isUrl("ftps://foo.bar/"));
            Assertions.assertTrue(ValidatorUtil.isUrl("http://abc.com"));

            Assertions.assertFalse(ValidatorUtil.isUrl("http://un/www.google.com/index.asp"));
            Assertions.assertFalse(ValidatorUtil.isUrl("abc.com"));
            Assertions.assertFalse(ValidatorUtil.isUrl("http://abc.c om"));
            Assertions.assertFalse(ValidatorUtil.isUrl("http2://abc.com"));
        }

        @Test
        @DisplayName("正则校验HTTP URL")
        void isHttpUrl() {
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://www.foufos.gr"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("https://www.foufos.gr"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://foufos.gr"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://www.foufos.gr/kino"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://werer.gr"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("www.foufos.gr"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("www.mp3.com"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("www.t.co"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://t.co"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://www.t.co"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("https://www.t.co"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("www.aa.com"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://aa.com"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("http://www.aa.com"));
            Assertions.assertTrue(ValidatorUtil.isHttpUrl("https://www.aa.com"));

            Assertions.assertFalse(ValidatorUtil.isHttpUrl("www.foufos"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("www.foufos-.gr"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("www.-foufos.gr"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("foufos.gr"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("http://www.foufos"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("http://foufos"));
            Assertions.assertFalse(ValidatorUtil.isHttpUrl("www.mp3#.com"));
        }

        @Test
        @DisplayName("正则校验HTTP URL")
        void isUuid() {
            for (int i = 0; i < 10; i++) {
                String uuid = UUID.randomUUID().toString();
                Assertions.assertTrue(ValidatorUtil.isUuid(uuid));
            }

            Assertions.assertFalse(ValidatorUtil.isUuid("1234"));
        }

        @Test
        @DisplayName("正则校验中国车牌号码")
        void isPlateNumber() {
            Assertions.assertTrue(ValidatorUtil.isPlateNumber("京A12345"));
            Assertions.assertTrue(ValidatorUtil.isPlateNumber("沪BK2345"));
            Assertions.assertTrue(ValidatorUtil.isPlateNumber("粤BD12345"));

            Assertions.assertFalse(ValidatorUtil.isPlateNumber("1234"));
        }

    }

    @Nested
    @DisplayName("校验字符")
    class CheckChar {

        @Test
        @DisplayName("正则校验全是汉字字符")
        void isAllChineseChar() {
            Assertions.assertTrue(ValidatorUtil.isAllChineseChar("春眠不觉晓"));
            Assertions.assertFalse(ValidatorUtil.isAllChineseChar("春眠不觉晓，"));
            Assertions.assertFalse(ValidatorUtil.isAllChineseChar("English"));
        }

        @Test
        @DisplayName("正则校验全是英文字母")
        void isAllEnglishChar() {
            Assertions
                .assertTrue(ValidatorUtil.isAllEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions
                .assertTrue(ValidatorUtil.isAllEnglishChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertFalse(ValidatorUtil.isAllEnglishChar("How are you?"));
            Assertions.assertFalse(ValidatorUtil.isAllEnglishChar("你奈我何"));
        }

        @Test
        @DisplayName("正则校验全是小写字母")
        void isAllLowerEnglishChar() {
            Assertions.assertTrue(ValidatorUtil.isAllLowerEnglishChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertFalse(ValidatorUtil.isAllLowerEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        }

        @Test
        @DisplayName("正则校验全是大写字母")
        void isAllUpperEnglishChar() {
            Assertions.assertTrue(ValidatorUtil.isAllUpperEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertFalse(ValidatorUtil.isAllUpperEnglishChar("abcdefghijklmnopqrstuvwxyz"));
        }

        @Test
        @DisplayName("校验文本中是否包含禁止的字符")
        void hasForbiddenChar() {
            Assertions.assertTrue(ValidatorUtil.hasForbiddenChar("abcdefghijk,;=stuvwxyz", "cde"));
        }

        @Test
        @DisplayName("正则校验全是常规字符（数字、英文字母、下划线）")
        void isAllGeneralChar() {
            Assertions.assertTrue(ValidatorUtil.isAllGeneralChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralChar("0123456789"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralChar("_"));
            Assertions.assertFalse(ValidatorUtil.isAllGeneralChar("!@#$"));
        }

        @Test
        @DisplayName("正则校验全是单词字符（数字、英文字母、下划线，以及中文字符）")
        void isGeneralWithChinese() {
            Assertions.assertTrue(ValidatorUtil.isAllGeneralAndChineseChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralAndChineseChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralAndChineseChar("0123456789"));
            Assertions.assertTrue(ValidatorUtil.isAllGeneralAndChineseChar("_"));
            Assertions.assertFalse(ValidatorUtil.isAllGeneralChar("!@#$"));

            Assertions.assertTrue(ValidatorUtil.isAllGeneralAndChineseChar("abcdefghijklmnopqrstuvwxyz一二三四五"));
        }

        @Test
        @DisplayName("正则校验全是非单词字符")
        void isNoneGeneralChar() {
            Assertions
                .assertFalse(ValidatorUtil.isNoneGeneralChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
            Assertions
                .assertFalse(ValidatorUtil.isNoneGeneralChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertFalse(ValidatorUtil.isNoneGeneralChar("0123456789"));
            Assertions.assertFalse(ValidatorUtil.isNoneGeneralChar("_"));
        }

        @Test
        @DisplayName("正则校验文本长度")
        void isLengthInRange() {
            Assertions.assertTrue(ValidatorUtil.isLengthInRange("12345", 3, 8));
            Assertions.assertFalse(ValidatorUtil.isLengthInRange("12345", 6, 9));
        }

    }

    @Nested
    @DisplayName("校验数字")
    class CheckNumber {

        @Test
        @DisplayName("正则校验全是数字")
        void isAllNumber() {
            Assertions.assertTrue(ValidatorUtil.isAllNumberChar("0123456789"));
            Assertions.assertFalse(ValidatorUtil.isAllNumberChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertFalse(ValidatorUtil.isAllNumberChar("How are you?"));
            Assertions.assertFalse(ValidatorUtil.isAllNumberChar("你奈我何"));
        }

        @Test
        @DisplayName("正则校验没有数字")
        void isNoneNumberChar() {
            Assertions.assertTrue(ValidatorUtil.isNoneNumberChar("abcdefghijklmnopqrstuvwxyz"));
            Assertions.assertTrue(ValidatorUtil.isNoneNumberChar("你奈我何"));
            Assertions.assertFalse(ValidatorUtil.isNoneNumberChar("0123456789"));
            Assertions.assertFalse(ValidatorUtil.isNoneNumberChar("test008"));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        void isLeastNDigitNumber() {
            Assertions.assertTrue(ValidatorUtil.isLeastDigitNumber("0123456789", 5));
            Assertions.assertTrue(ValidatorUtil.isLeastDigitNumber("0123456789", 10));
            Assertions.assertFalse(ValidatorUtil.isLeastDigitNumber("0123456789", 11));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        void isMToNDigitNumber() {
            Assertions.assertTrue(ValidatorUtil.isRangeDigitNumber("0123456789", 1, 10));
            Assertions.assertFalse(ValidatorUtil.isRangeDigitNumber("0123456789", 6, 9));
            Assertions.assertFalse(ValidatorUtil.isRangeDigitNumber("0123456789", 11, 20));
        }

        @Test
        @DisplayName("正则校验不含任何数字")
        void isNDigitNumber() {
            Assertions.assertTrue(ValidatorUtil.isDigitNumber("0123456789", 10));
            Assertions.assertTrue(ValidatorUtil.isDigitNumber("12345", 5));
            Assertions.assertFalse(ValidatorUtil.isDigitNumber("12345", 3));
        }

        @Test
        @DisplayName("正则校验整数")
        void isInteger() {
            Assertions.assertTrue(ValidatorUtil.isInteger("123456789"));
            Assertions.assertTrue(ValidatorUtil.isInteger("-100"));
            Assertions.assertTrue(ValidatorUtil.isInteger("1000"));
            Assertions.assertFalse(ValidatorUtil.isInteger("1000.0"));
            Assertions.assertFalse(ValidatorUtil.isInteger("3adgshals123"));

            Assertions.assertTrue(ValidatorUtil.isPositiveInteger("1000"));
            Assertions.assertFalse(ValidatorUtil.isPositiveInteger("-100"));

            Assertions.assertFalse(ValidatorUtil.isNegativeInteger("1000"));
            Assertions.assertTrue(ValidatorUtil.isNegativeInteger("-100"));

            Assertions.assertTrue(ValidatorUtil.isNotNegativeInteger("1000"));
            Assertions.assertFalse(ValidatorUtil.isNotNegativeInteger("-100"));

            Assertions.assertFalse(ValidatorUtil.isNotPositiveInteger("1000"));
            Assertions.assertTrue(ValidatorUtil.isNotPositiveInteger("-100"));
        }

        @Test
        @DisplayName("正则校验浮点数")
        void isFloat() {
            Assertions.assertTrue(ValidatorUtil.isFloat("0.50"));
            Assertions.assertTrue(ValidatorUtil.isFloat("0.00023"));
            Assertions.assertTrue(ValidatorUtil.isFloat("100.0"));
            Assertions.assertFalse(ValidatorUtil.isFloat("100"));
            Assertions.assertFalse(ValidatorUtil.isFloat("016.4"));
            Assertions.assertFalse(ValidatorUtil.isFloat("100.0a"));

            Assertions.assertTrue(ValidatorUtil.isPositiveFloat("1000.0"));
            Assertions.assertFalse(ValidatorUtil.isPositiveFloat("-100.4"));

            Assertions.assertFalse(ValidatorUtil.isNegativeFloat("1000.0"));
            Assertions.assertTrue(ValidatorUtil.isNegativeFloat("-100.4"));

            Assertions.assertTrue(ValidatorUtil.isNotNegativeFloat("1000.0"));
            Assertions.assertFalse(ValidatorUtil.isNotNegativeFloat("-100.4"));

            Assertions.assertFalse(ValidatorUtil.isNotPositiveFloat("1000.0"));
            Assertions.assertTrue(ValidatorUtil.isNotPositiveFloat("-100.4"));
        }

    }

}
