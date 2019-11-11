package io.github.dunwu.util.text;

import io.github.dunwu.util.collection.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see RegexUtils
 * @since 2019-01-16
 */
@DisplayName("正则校验测试例集")
class RegexUtilsTest {

	@Test
	void test2() {
		String content = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
			+ "\"window_time\":1547605830000,\"judgementId\":\"hello\"},\"allowed\":false,\"version\":0}";
		String content2 = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
			+ "\"window_time\":1547605830000,\"judgementId\":hello},\"allowed\":false,\"version\":0}";
		List<String> matchValues = RegexUtils.getMatchValuesInJson(content, "judgementId");
		if (CollectionUtils.isNotEmpty(matchValues)) {
			matchValues.forEach(System.out::println);
		}

		List<String> matchValues2 = RegexUtils.getMatchValuesInJson(content2,
			"judgementId");
		if (CollectionUtils.isNotEmpty(matchValues2)) {
			matchValues2.forEach(System.out::println);
		}
	}

	@Nested
	@DisplayName("校验常用场景")
	class CheckCommon {

		@Test
		@DisplayName("正则校验日期")
		void isDate() {
			Assertions.assertTrue(RegexUtils.isDate("2016/1/1"));
			Assertions.assertTrue(RegexUtils.isDate("2016/01/01"));
			Assertions.assertTrue(RegexUtils.isDate("20160101"));
			Assertions.assertTrue(RegexUtils.isDate("2016-01-01"));
			Assertions.assertTrue(RegexUtils.isDate("2016.01.01"));
			Assertions.assertTrue(RegexUtils.isDate("2000-02-29"));
			Assertions.assertTrue(RegexUtils.isDate("2011-03-11"));
			Assertions.assertTrue(RegexUtils.isDate("2012-02-29"));

			Assertions.assertFalse(RegexUtils.isDate("2001-02-29"));
			Assertions.assertFalse(RegexUtils.isDate("2016/12/32"));
			Assertions.assertFalse(RegexUtils.isDate("2016.13.1"));
			Assertions.assertFalse(RegexUtils.isDate("2011-02-29"));
			Assertions.assertFalse(RegexUtils.isDate("201a-02-30"));
			Assertions.assertFalse(RegexUtils.isDate("2011-0211"));
		}

		@Test
		@DisplayName("正则校验有效邮箱")
		void isEmail() {
			Assertions.assertTrue(RegexUtils.isEmail("he_llo@worl.d.com"));
			Assertions.assertTrue(RegexUtils.isEmail("hel.l-o@wor-ld.museum"));
			Assertions.assertTrue(RegexUtils.isEmail("h1ello@123.com"));
			Assertions.assertTrue(RegexUtils.isEmail("abc@abc.com"));
			Assertions.assertTrue(RegexUtils.isEmail("hello@worl_d.com"));

			Assertions.assertFalse(RegexUtils.isEmail("he&llo@world.co1"));
			Assertions.assertFalse(RegexUtils.isEmail(".hello@wor#.co.uk"));
			Assertions.assertFalse(RegexUtils.isEmail("gdsakh23124"));
			Assertions.assertFalse(RegexUtils.isEmail("abc"));
			Assertions.assertFalse(RegexUtils.isEmail("abc@a"));
			Assertions.assertFalse(RegexUtils.isEmail("中文@a.com"));
		}

		@Test
		@DisplayName("正则校验15位身份证号")
		void isIdCard15() {
			Assertions.assertTrue(RegexUtils.isIdCard15("110001700101031"));
			Assertions.assertFalse(RegexUtils.isIdCard15("110001701501031"));
		}

		@Test
		@DisplayName("正则校验18位身份证号")
		void isIdCard18() {
			Assertions.assertTrue(RegexUtils.isIdCard18("11000019900101015X"));
			Assertions.assertFalse(RegexUtils.isIdCard18("990000199001010310"));
			Assertions.assertFalse(RegexUtils.isIdCard18("110001199013010310"));
			Assertions.assertFalse(RegexUtils.isIdCard18("440101198909204518"));
			// 含字母
			Assertions.assertFalse(RegexUtils.isIdCard18("440101198987754ab"));
			// 月份不对
			Assertions.assertFalse(RegexUtils.isIdCard18("440101198987754122"));
			// 日期不对
			Assertions.assertFalse(RegexUtils.isIdCard18("440101891232451"));
		}

		@Test
		@DisplayName("正则校验IPv4")
		void isIpv4() {
			Assertions.assertTrue(RegexUtils.isIpv4("0.0.0.0"));
			Assertions.assertTrue(RegexUtils.isIpv4("255.255.255.255"));
			Assertions.assertTrue(RegexUtils.isIpv4("127.0.0.1"));
			Assertions.assertFalse(RegexUtils.isIpv4("10.10.10"));
			Assertions.assertFalse(RegexUtils.isIpv4("10.10.10.2561"));
		}

		@Test
		@DisplayName("正则校验IPv6")
		void isIpv6() {
			Assertions.assertTrue(RegexUtils.isIpv6("1:2:3:4:5:6:7:8"));
			Assertions.assertTrue(RegexUtils.isIpv6("1:2:3:4:5::8"));
			Assertions.assertTrue(RegexUtils.isIpv6("::255.255.255.255"));
			Assertions.assertTrue(RegexUtils.isIpv6("1:2:3:4:5:6:7::"));
			Assertions.assertFalse(RegexUtils.isIpv6("1.2.3.4.5.6.7.8"));
			Assertions.assertFalse(RegexUtils.isIpv6("1::2::3"));
		}

		@Test
		@DisplayName("正则校验手机号码")
		void isMobile() {
			Assertions.assertFalse(RegexUtils.isMobile(null));
			Assertions.assertFalse(RegexUtils.isMobile(""));
			Assertions.assertFalse(RegexUtils.isMobile("1234a"));
			Assertions.assertFalse(RegexUtils.isMobile("1234561"));

			Assertions.assertTrue(RegexUtils.isMobile("11170998762"));
		}

		@Test
		@DisplayName("正则校验座机号码")
		void isPhone() {
			Assertions.assertFalse(RegexUtils.isPhone(null));
			Assertions.assertFalse(RegexUtils.isPhone("8802973a"));
			Assertions.assertFalse(RegexUtils.isPhone("8908222222"));
			Assertions.assertFalse(RegexUtils.isPhone("89081"));

			Assertions.assertTrue(RegexUtils.isPhone("89019739"));
			Assertions.assertTrue(RegexUtils.isPhone("020-89019739"));
			Assertions.assertTrue(RegexUtils.isPhone("025-85951888"));
		}

		@Test
		@DisplayName("正则校验真实的手机号码")
		void isRealMobile() {
			Assertions.assertFalse(RegexUtils.isRealMobile(null));
			Assertions.assertFalse(RegexUtils.isRealMobile(""));
			Assertions.assertFalse(RegexUtils.isRealMobile("1234a"));
			Assertions.assertFalse(RegexUtils.isRealMobile("1234561"));
			Assertions.assertFalse(RegexUtils.isRealMobile("11170998762"));

			Assertions.assertTrue(RegexUtils.isRealMobile("13970998762"));
			Assertions.assertTrue(RegexUtils.isRealMobile("15812345678"));
			Assertions.assertTrue(RegexUtils.isRealMobile("+86 18012345678"));
			Assertions.assertTrue(RegexUtils.isRealMobile("86 18012345678"));
		}

		@Test
		@DisplayName("正则校验时间")
		void isTime() {
			Assertions.assertTrue(RegexUtils.isTime("00:00:00"));
			Assertions.assertTrue(RegexUtils.isTime("23:59:59"));
			Assertions.assertTrue(RegexUtils.isTime("17:06:30"));
			Assertions.assertFalse(RegexUtils.isTime("17:6:30"));
			Assertions.assertFalse(RegexUtils.isTime("24:16:30"));
		}

		@Test
		@DisplayName("正则校验URL")
		void isUrl() {
			Assertions.assertTrue(RegexUtils.isUrl("http://google.com/help/me"));
			Assertions.assertTrue(RegexUtils.isUrl("http://www.google.com/help/me/"));
			Assertions.assertTrue(RegexUtils.isUrl("https://www.google.com/help.asp"));
			Assertions.assertTrue(RegexUtils.isUrl("ftp://www.google.com"));
			Assertions.assertTrue(RegexUtils.isUrl("ftps://google.org"));
			Assertions.assertTrue(RegexUtils.isUrl("http://abc.com"));

			Assertions.assertFalse(RegexUtils.isUrl("http://un/www.google.com/index.asp"));
			Assertions.assertFalse(RegexUtils.isUrl("abc.com"));
			Assertions.assertFalse(RegexUtils.isUrl("http://abc.c om"));
			Assertions.assertFalse(RegexUtils.isUrl("http2://abc.com"));
		}

		@Test
		@DisplayName("正则校验用户名有效")
		void isUsername() {
			Assertions.assertTrue(RegexUtils.isUsername("gdasg_"));
			Assertions.assertFalse(RegexUtils.isUsername("$dhsagk"));
		}

	}

	@Nested
	@DisplayName("校验字符")
	class CheckChar {

		@Test
		@DisplayName("正则校验全是汉字字符")
		void isAllChineseChar() {
			Assertions.assertTrue(RegexUtils.isAllChineseChar("春眠不觉晓"));
			Assertions.assertFalse(RegexUtils.isAllChineseChar("春眠不觉晓，"));
			Assertions.assertFalse(RegexUtils.isAllChineseChar("English"));
		}

		@Test
		@DisplayName("正则校验全是英文字母")
		void isAllEnglishChar() {
			Assertions
				.assertTrue(RegexUtils.isAllEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
				.assertTrue(RegexUtils.isAllEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtils.isAllEnglishChar("How are you?"));
			Assertions.assertFalse(RegexUtils.isAllEnglishChar("你奈我何"));
		}

		@Test
		@DisplayName("正则校验全是小写字母")
		void isAllLowerEnglishChar() {
			Assertions.assertTrue(
				RegexUtils.isAllLowerEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(
				RegexUtils.isAllLowerEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		}

		@Test
		@DisplayName("正则校验全是大写字母")
		void isAllUpperEnglishChar() {
			Assertions.assertTrue(
				RegexUtils.isAllUpperEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertFalse(
				RegexUtils.isAllUpperEnglishChar("abcdefghijklmnopqrstuvwxyz"));
		}

		@Test
		@DisplayName("正则校验全是单词字符")
		void isAllWordChar() {
			Assertions.assertTrue(RegexUtils.isAllWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertTrue(RegexUtils.isAllWordChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertTrue(RegexUtils.isAllWordChar("0123456789"));
			Assertions.assertTrue(RegexUtils.isAllWordChar("_"));
		}

		@Test
		@DisplayName("正则校验全是非单词字符")
		void isNoneWordChar() {
			Assertions
				.assertFalse(RegexUtils.isNoneWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
				.assertFalse(RegexUtils.isNoneWordChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtils.isNoneWordChar("0123456789"));
			Assertions.assertFalse(RegexUtils.isNoneWordChar("_"));
		}

	}

	@Nested
	@DisplayName("校验数字")
	class CheckNumber {

		@Test
		@DisplayName("正则校验全是数字")
		void isAllNumber() {
			Assertions.assertTrue(RegexUtils.isAllNumber("0123456789"));
			Assertions.assertFalse(RegexUtils.isAllNumber("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtils.isAllNumber("How are you?"));
			Assertions.assertFalse(RegexUtils.isAllNumber("你奈我何"));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isLeastNDigitNumber() {
			Assertions.assertTrue(RegexUtils.isLeastDigitNumber("0123456789", 5));
			Assertions.assertTrue(RegexUtils.isLeastDigitNumber("0123456789", 10));
			Assertions.assertFalse(RegexUtils.isLeastDigitNumber("0123456789", 11));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isMToNDigitNumber() {
			Assertions.assertTrue(RegexUtils.isRangeDigitNumber("0123456789", 1, 10));
			Assertions.assertFalse(RegexUtils.isRangeDigitNumber("0123456789", 6, 9));
			Assertions.assertFalse(RegexUtils.isRangeDigitNumber("0123456789", 11, 20));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isNDigitNumber() {
			Assertions.assertTrue(RegexUtils.isDigitNumber("0123456789", 10));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		void isNoneNumber() {
			Assertions.assertTrue(RegexUtils.isNoneNumber("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtils.isNoneNumber("0123456789"));
			Assertions.assertFalse(RegexUtils.isNoneNumber("test008"));
		}

	}

	@Nested
	@DisplayName("校验Markdonw")
	class CheckMarkdown {

		@Test
		@DisplayName("校验含有 Markdonw ![]() ")
		void isMarkdownImageTag() {
			String str = RegexUtils.replaceAllMatchContent(
				"![asgad](http://www.baidu.com/test.png)",
				RegexUtils.REGEX_MARKDOWN_IMAGE_TAG, "![](");
			System.out.println(str);
		}

	}

}
