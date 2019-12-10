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
public class ValidatorUtilsTest {

	@Nested
	@DisplayName("校验常用场景")
	class CheckCommon {

		@Test
		@DisplayName("正则校验邮政编码")
		void isZipCode() {
			Assertions.assertTrue(ValidatorUtils.isZipCode("243000"));
			Assertions.assertFalse(ValidatorUtils.isZipCode("2430001"));
		}

		@Test
		@DisplayName("正则校验有效邮箱")
		void isEmail() {
			Assertions.assertTrue(ValidatorUtils.isEmail("he_llo@worl.d.com"));
			Assertions.assertTrue(ValidatorUtils.isEmail("hel.l-o@wor-ld.museum"));
			Assertions.assertTrue(ValidatorUtils.isEmail("h1ello@123.com"));
			Assertions.assertTrue(ValidatorUtils.isEmail("abc@abc.com"));
			Assertions.assertTrue(ValidatorUtils.isEmail("he&llo@world.co1"));

			Assertions.assertFalse(ValidatorUtils.isEmail("hello@worl_d.com"));
			Assertions.assertFalse(ValidatorUtils.isEmail(".hello@wor#.co.uk"));
			Assertions.assertFalse(ValidatorUtils.isEmail("gdsakh23124"));
			Assertions.assertFalse(ValidatorUtils.isEmail("abc"));
			Assertions.assertFalse(ValidatorUtils.isEmail("abc@a"));
			Assertions.assertFalse(ValidatorUtils.isEmail("中文@a.com"));
		}

		@Test
		@DisplayName("正则校验15位身份证号")
		void isIdCard15() {
			Assertions.assertTrue(ValidatorUtils.isCitizenId15("110001700101031"));
			Assertions.assertFalse(ValidatorUtils.isCitizenId15("110001701501031"));
		}

		@Test
		@DisplayName("正则校验18位身份证号")
		void isIdCard18() {
			Assertions.assertTrue(ValidatorUtils.isCitizenId18("11000019900101015X"));
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("990000199001010310"));
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("110001199013010310"));
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("440101198909204518"));
			// 含字母
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("440101198987754ab"));
			// 月份不对
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("440101198987754122"));
			// 日期不对
			Assertions.assertFalse(ValidatorUtils.isCitizenId18("440101891232451"));
		}

		@Test
		@DisplayName("正则校验IPv4")
		void isIpv4() {
			Assertions.assertTrue(ValidatorUtils.isIpv4("0.0.0.0"));
			Assertions.assertTrue(ValidatorUtils.isIpv4("255.255.255.255"));
			Assertions.assertTrue(ValidatorUtils.isIpv4("127.0.0.1"));
			Assertions.assertFalse(ValidatorUtils.isIpv4("10.10.10"));
			Assertions.assertFalse(ValidatorUtils.isIpv4("10.10.10.2561"));
		}

		@Test
		@DisplayName("正则校验IPv6")
		void isIpv6() {
			Assertions.assertTrue(ValidatorUtils.isIpv6("1:2:3:4:5:6:7:8"));
			Assertions.assertTrue(ValidatorUtils.isIpv6("1:2:3:4:5::8"));
			Assertions.assertTrue(ValidatorUtils.isIpv6("::255.255.255.255"));
			Assertions.assertTrue(ValidatorUtils.isIpv6("1:2:3:4:5:6:7::"));
			Assertions.assertFalse(ValidatorUtils.isIpv6("1.2.3.4.5.6.7.8"));
			Assertions.assertFalse(ValidatorUtils.isIpv6("1::2::3"));
		}

		@Test
		@DisplayName("正则校验 MAC 地址")
		void isMac() {
			Assertions.assertTrue(ValidatorUtils.isMac("01:23:45:67:89:ab"));
			Assertions.assertTrue(ValidatorUtils.isMac("01:23:45:67:89:AB"));
			Assertions.assertTrue(ValidatorUtils.isMac("fE:dC:bA:98:76:54"));
			Assertions.assertTrue(ValidatorUtils.isMac("A0:B1:C2:D3:E4:F5"));
			Assertions.assertTrue(ValidatorUtils.isMac("A0-B1-C2-D3-E4-F5"));
			Assertions.assertTrue(ValidatorUtils.isMac("ffff.ffff.ffff"));
			Assertions.assertTrue(ValidatorUtils.isMac("1234.abcd.1234"));
			Assertions.assertTrue(ValidatorUtils.isMac("001e.1324.683f"));

			Assertions.assertFalse(ValidatorUtils.isMac("01:23:45:67:89:ab:cd"));
			Assertions.assertFalse(ValidatorUtils.isMac("01:23:45:67:89:Az"));
			Assertions.assertFalse(ValidatorUtils.isMac("01:23:45:56:"));
		}

		@Test
		@DisplayName("正则校验座机号码")
		void isPhone() {
			Assertions.assertFalse(ValidatorUtils.isPhone(null));
			Assertions.assertFalse(ValidatorUtils.isPhone("8802973a"));
			Assertions.assertFalse(ValidatorUtils.isPhone("8908222222"));
			Assertions.assertFalse(ValidatorUtils.isPhone("89081"));

			Assertions.assertTrue(ValidatorUtils.isPhone("89019739"));
			Assertions.assertTrue(ValidatorUtils.isPhone("020-89019739"));
			Assertions.assertTrue(ValidatorUtils.isPhone("025-85951888"));
		}

		@Test
		@DisplayName("正则校验手机号码")
		void isMobile() {
			Assertions.assertFalse(ValidatorUtils.isMobile(null));
			Assertions.assertFalse(ValidatorUtils.isMobile(""));
			Assertions.assertFalse(ValidatorUtils.isMobile("1234a"));
			Assertions.assertFalse(ValidatorUtils.isMobile("1234561"));
			Assertions.assertFalse(ValidatorUtils.isMobile("11170998762"));

			Assertions.assertTrue(ValidatorUtils.isMobile("13970998762"));
			Assertions.assertTrue(ValidatorUtils.isMobile("15812345678"));
			Assertions.assertTrue(ValidatorUtils.isMobile("15812345678"));
			Assertions.assertTrue(ValidatorUtils.isMobile("+8618012345678"));
			Assertions.assertTrue(ValidatorUtils.isMobile("+86 18012345678"));
			Assertions.assertTrue(ValidatorUtils.isMobile("8618012345678"));
			Assertions.assertTrue(ValidatorUtils.isMobile("86 18012345678"));
		}

		@Test
		@DisplayName("正则校验日期")
		void isDate() {
			Assertions.assertTrue(ValidatorUtils.isDate("2016/1/1"));
			Assertions.assertTrue(ValidatorUtils.isDate("2016/01/01"));
			Assertions.assertTrue(ValidatorUtils.isDate("20160101"));
			Assertions.assertTrue(ValidatorUtils.isDate("2016-01-01"));
			Assertions.assertTrue(ValidatorUtils.isDate("2016.01.01"));
			Assertions.assertTrue(ValidatorUtils.isDate("2000-02-29"));
			Assertions.assertTrue(ValidatorUtils.isDate("2011-03-11"));
			Assertions.assertTrue(ValidatorUtils.isDate("2012-02-29"));

			Assertions.assertFalse(ValidatorUtils.isDate("2001-02-29"));
			Assertions.assertFalse(ValidatorUtils.isDate("2016/12/32"));
			Assertions.assertFalse(ValidatorUtils.isDate("2016.13.1"));
			Assertions.assertFalse(ValidatorUtils.isDate("2011-02-29"));
			Assertions.assertFalse(ValidatorUtils.isDate("201a-02-30"));
			Assertions.assertFalse(ValidatorUtils.isDate("2011-0211"));
		}

		@Test
		@DisplayName("正则校验时间")
		void isTime() {
			Assertions.assertTrue(ValidatorUtils.isTime("00:00:00"));
			Assertions.assertTrue(ValidatorUtils.isTime("23:59:59"));
			Assertions.assertTrue(ValidatorUtils.isTime("17:06:30"));
			Assertions.assertFalse(ValidatorUtils.isTime("17:6:30"));
			Assertions.assertFalse(ValidatorUtils.isTime("13:69:30"));
			Assertions.assertFalse(ValidatorUtils.isTime("24:16:30"));
		}

		@Test
		@DisplayName("正则校验URL")
		void isUrl() {
			Assertions.assertTrue(ValidatorUtils.isUrl("http://google.com/help/me"));
			Assertions.assertTrue(ValidatorUtils.isUrl("http://www.google.com/help/me/"));
			Assertions.assertTrue(ValidatorUtils.isUrl("https://www.google.com/help.asp"));
			Assertions.assertTrue(ValidatorUtils.isUrl("ftp://www.google.com"));
			Assertions.assertTrue(ValidatorUtils.isUrl("ftps://foo.bar/"));
			Assertions.assertTrue(ValidatorUtils.isUrl("http://abc.com"));

			Assertions.assertFalse(ValidatorUtils.isUrl("http://un/www.google.com/index.asp"));
			Assertions.assertFalse(ValidatorUtils.isUrl("abc.com"));
			Assertions.assertFalse(ValidatorUtils.isUrl("http://abc.c om"));
			Assertions.assertFalse(ValidatorUtils.isUrl("http2://abc.com"));
		}

		@Test
		@DisplayName("正则校验HTTP URL")
		void isHttpUrl() {
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://www.foufos.gr"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("https://www.foufos.gr"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://foufos.gr"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://www.foufos.gr/kino"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://werer.gr"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("www.foufos.gr"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("www.mp3.com"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("www.t.co"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://t.co"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://www.t.co"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("https://www.t.co"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("www.aa.com"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://aa.com"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("http://www.aa.com"));
			Assertions.assertTrue(ValidatorUtils.isHttpUrl("https://www.aa.com"));

			Assertions.assertFalse(ValidatorUtils.isHttpUrl("www.foufos"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("www.foufos-.gr"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("www.-foufos.gr"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("foufos.gr"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("http://www.foufos"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("http://foufos"));
			Assertions.assertFalse(ValidatorUtils.isHttpUrl("www.mp3#.com"));
		}

		@Test
		@DisplayName("正则校验HTTP URL")
		void isUuid() {
			for (int i = 0; i < 10; i++) {
				String uuid = UUID.randomUUID().toString();
				Assertions.assertTrue(ValidatorUtils.isUuid(uuid));
			}

			Assertions.assertFalse(ValidatorUtils.isUuid("1234"));
		}

		@Test
		@DisplayName("正则校验中国车牌号码")
		void isPlateNumber() {
			Assertions.assertTrue(ValidatorUtils.isPlateNumber("京A12345"));
			Assertions.assertTrue(ValidatorUtils.isPlateNumber("沪BK2345"));
			Assertions.assertTrue(ValidatorUtils.isPlateNumber("粤BD12345"));

			Assertions.assertFalse(ValidatorUtils.isPlateNumber("1234"));
		}
	}

	@Nested
	@DisplayName("校验字符")
	class CheckChar {

		@Test
		@DisplayName("正则校验全是汉字字符")
		void isAllChineseChar() {
			Assertions.assertTrue(ValidatorUtils.isAllChineseChar("春眠不觉晓"));
			Assertions.assertFalse(ValidatorUtils.isAllChineseChar("春眠不觉晓，"));
			Assertions.assertFalse(ValidatorUtils.isAllChineseChar("English"));
		}

		@Test
		@DisplayName("正则校验全是英文字母")
		void isAllEnglishChar() {
			Assertions
				.assertTrue(ValidatorUtils.isAllEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
				.assertTrue(ValidatorUtils.isAllEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(ValidatorUtils.isAllEnglishChar("How are you?"));
			Assertions.assertFalse(ValidatorUtils.isAllEnglishChar("你奈我何"));
		}

		@Test
		@DisplayName("正则校验全是小写字母")
		void isAllLowerEnglishChar() {
			Assertions.assertTrue(ValidatorUtils.isAllLowerEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(ValidatorUtils.isAllLowerEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		}

		@Test
		@DisplayName("正则校验全是大写字母")
		void isAllUpperEnglishChar() {
			Assertions.assertTrue(ValidatorUtils.isAllUpperEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertFalse(ValidatorUtils.isAllUpperEnglishChar("abcdefghijklmnopqrstuvwxyz"));
		}

		@Test
		@DisplayName("校验文本中是否包含禁止的字符")
		void hasForbiddenChar() {
			Assertions.assertTrue(ValidatorUtils.hasForbiddenChar("abcdefghijk,;=stuvwxyz", "cde"));
		}

		@Test
		@DisplayName("正则校验全是常规字符（数字、英文字母、下划线）")
		void isAllGeneralChar() {
			Assertions.assertTrue(ValidatorUtils.isAllGeneralChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralChar("0123456789"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralChar("_"));
			Assertions.assertFalse(ValidatorUtils.isAllGeneralChar("!@#$"));
		}

		@Test
		@DisplayName("正则校验全是单词字符（数字、英文字母、下划线，以及中文字符）")
		void isGeneralWithChinese() {
			Assertions.assertTrue(ValidatorUtils.isAllGeneralAndChineseChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralAndChineseChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralAndChineseChar("0123456789"));
			Assertions.assertTrue(ValidatorUtils.isAllGeneralAndChineseChar("_"));
			Assertions.assertFalse(ValidatorUtils.isAllGeneralChar("!@#$"));

			Assertions.assertTrue(ValidatorUtils.isAllGeneralAndChineseChar("abcdefghijklmnopqrstuvwxyz一二三四五"));
		}

		@Test
		@DisplayName("正则校验全是非单词字符")
		void isNoneGeneralChar() {
			Assertions
				.assertFalse(ValidatorUtils.isNoneGeneralChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
				.assertFalse(ValidatorUtils.isNoneGeneralChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(ValidatorUtils.isNoneGeneralChar("0123456789"));
			Assertions.assertFalse(ValidatorUtils.isNoneGeneralChar("_"));
		}

		@Test
		@DisplayName("正则校验文本长度")
		void isLengthInRange() {
			Assertions.assertTrue(ValidatorUtils.isLengthInRange("12345", 3, 8));
			Assertions.assertFalse(ValidatorUtils.isLengthInRange("12345", 6, 9));
		}

	}

	@Nested
	@DisplayName("校验数字")
	class CheckNumber {

		@Test
		@DisplayName("正则校验全是数字")
		void isAllNumber() {
			Assertions.assertTrue(ValidatorUtils.isAllNumberChar("0123456789"));
			Assertions.assertFalse(ValidatorUtils.isAllNumberChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(ValidatorUtils.isAllNumberChar("How are you?"));
			Assertions.assertFalse(ValidatorUtils.isAllNumberChar("你奈我何"));
		}

		@Test
		@DisplayName("正则校验没有数字")
		void isNoneNumberChar() {
			Assertions.assertTrue(ValidatorUtils.isNoneNumberChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertTrue(ValidatorUtils.isNoneNumberChar("你奈我何"));
			Assertions.assertFalse(ValidatorUtils.isNoneNumberChar("0123456789"));
			Assertions.assertFalse(ValidatorUtils.isNoneNumberChar("test008"));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isLeastNDigitNumber() {
			Assertions.assertTrue(ValidatorUtils.isLeastDigitNumber("0123456789", 5));
			Assertions.assertTrue(ValidatorUtils.isLeastDigitNumber("0123456789", 10));
			Assertions.assertFalse(ValidatorUtils.isLeastDigitNumber("0123456789", 11));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isMToNDigitNumber() {
			Assertions.assertTrue(ValidatorUtils.isRangeDigitNumber("0123456789", 1, 10));
			Assertions.assertFalse(ValidatorUtils.isRangeDigitNumber("0123456789", 6, 9));
			Assertions.assertFalse(ValidatorUtils.isRangeDigitNumber("0123456789", 11, 20));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isNDigitNumber() {
			Assertions.assertTrue(ValidatorUtils.isDigitNumber("0123456789", 10));
			Assertions.assertTrue(ValidatorUtils.isDigitNumber("12345", 5));
			Assertions.assertFalse(ValidatorUtils.isDigitNumber("12345", 3));
		}

		@Test
		@DisplayName("正则校验整数")
		void isInteger() {
			Assertions.assertTrue(ValidatorUtils.isInteger("123456789"));
			Assertions.assertTrue(ValidatorUtils.isInteger("-100"));
			Assertions.assertTrue(ValidatorUtils.isInteger("1000"));
			Assertions.assertFalse(ValidatorUtils.isInteger("1000.0"));
			Assertions.assertFalse(ValidatorUtils.isInteger("3adgshals123"));

			Assertions.assertTrue(ValidatorUtils.isPositiveInteger("1000"));
			Assertions.assertFalse(ValidatorUtils.isPositiveInteger("-100"));

			Assertions.assertFalse(ValidatorUtils.isNegativeInteger("1000"));
			Assertions.assertTrue(ValidatorUtils.isNegativeInteger("-100"));

			Assertions.assertTrue(ValidatorUtils.isNotNegativeInteger("1000"));
			Assertions.assertFalse(ValidatorUtils.isNotNegativeInteger("-100"));

			Assertions.assertFalse(ValidatorUtils.isNotPositiveInteger("1000"));
			Assertions.assertTrue(ValidatorUtils.isNotPositiveInteger("-100"));
		}

		@Test
		@DisplayName("正则校验浮点数")
		void isFloat() {
			Assertions.assertTrue(ValidatorUtils.isFloat("0.50"));
			Assertions.assertTrue(ValidatorUtils.isFloat("0.00023"));
			Assertions.assertTrue(ValidatorUtils.isFloat("100.0"));
			Assertions.assertFalse(ValidatorUtils.isFloat("100"));
			Assertions.assertFalse(ValidatorUtils.isFloat("016.4"));
			Assertions.assertFalse(ValidatorUtils.isFloat("100.0a"));

			Assertions.assertTrue(ValidatorUtils.isPositiveFloat("1000.0"));
			Assertions.assertFalse(ValidatorUtils.isPositiveFloat("-100.4"));

			Assertions.assertFalse(ValidatorUtils.isNegativeFloat("1000.0"));
			Assertions.assertTrue(ValidatorUtils.isNegativeFloat("-100.4"));

			Assertions.assertTrue(ValidatorUtils.isNotNegativeFloat("1000.0"));
			Assertions.assertFalse(ValidatorUtils.isNotNegativeFloat("-100.4"));

			Assertions.assertFalse(ValidatorUtils.isNotPositiveFloat("1000.0"));
			Assertions.assertTrue(ValidatorUtils.isNotPositiveFloat("-100.4"));
		}

	}

}
