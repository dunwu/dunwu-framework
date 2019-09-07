package io.github.dunwu.util.text;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see RegexUtil
 * @since 2019-01-16
 */
@DisplayName("正则校验测试例集")
public class RegexUtilTest {

	@Test
	public void test2() {
		String content = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
				+ "\"window_time\":1547605830000,\"judgementId\":\"hello\"},\"allowed\":false,\"version\":0}";
		String content2 = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
				+ "\"window_time\":1547605830000,\"judgementId\":hello},\"allowed\":false,\"version\":0}";
		List<String> matchValues = RegexUtil.getMatchValuesInJson(content, "judgementId");
		if (CollectionUtils.isNotEmpty(matchValues)) {
			matchValues.forEach(item -> {
				System.out.println(item);
			});
		}

		List<String> matchValues2 = RegexUtil.getMatchValuesInJson(content2,
				"judgementId");
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
		public void isIdCard15() {
			Assertions.assertTrue(RegexUtil.isIdCard15("110001700101031"));
			Assertions.assertFalse(RegexUtil.isIdCard15("110001701501031"));
		}

		@Test
		@DisplayName("正则校验18位身份证号")
		public void isIdCard18() {
			Assertions.assertTrue(RegexUtil.isIdCard18("11000019900101015X"));
			Assertions.assertFalse(RegexUtil.isIdCard18("990000199001010310"));
			Assertions.assertFalse(RegexUtil.isIdCard18("110001199013010310"));
			Assertions.assertFalse(RegexUtil.isIdCard18("440101198909204518"));
			// 含字母
			Assertions.assertFalse(RegexUtil.isIdCard18("440101198987754ab"));
			// 月份不对
			Assertions.assertFalse(RegexUtil.isIdCard18("440101198987754122"));
			// 日期不对
			Assertions.assertFalse(RegexUtil.isIdCard18("440101891232451"));
		}

		@Test
		@DisplayName("正则校验用户名有效")
		public void isUsername() {
			Assertions.assertTrue(RegexUtil.isUsername("gdasg_"));
			Assertions.assertFalse(RegexUtil.isUsername("$dhsagk"));
		}

		@Test
		@DisplayName("正则校验有效邮箱")
		public void isEmail() {
			Assertions.assertTrue(RegexUtil.isEmail("he_llo@worl.d.com"));
			Assertions.assertTrue(RegexUtil.isEmail("hel.l-o@wor-ld.museum"));
			Assertions.assertTrue(RegexUtil.isEmail("h1ello@123.com"));
			Assertions.assertTrue(RegexUtil.isEmail("abc@abc.com"));
			Assertions.assertTrue(RegexUtil.isEmail("hello@worl_d.com"));

			Assertions.assertFalse(RegexUtil.isEmail("he&llo@world.co1"));
			Assertions.assertFalse(RegexUtil.isEmail(".hello@wor#.co.uk"));
			Assertions.assertFalse(RegexUtil.isEmail("gdsakh23124"));
			Assertions.assertFalse(RegexUtil.isEmail("abc"));
			Assertions.assertFalse(RegexUtil.isEmail("abc@a"));
			Assertions.assertFalse(RegexUtil.isEmail("中文@a.com"));
		}

		@Test
		@DisplayName("正则校验URL")
		public void isUrl() {
			Assertions.assertTrue(RegexUtil.isUrl("http://google.com/help/me"));
			Assertions.assertTrue(RegexUtil.isUrl("http://www.google.com/help/me/"));
			Assertions.assertTrue(RegexUtil.isUrl("https://www.google.com/help.asp"));
			Assertions.assertTrue(RegexUtil.isUrl("ftp://www.google.com"));
			Assertions.assertTrue(RegexUtil.isUrl("ftps://google.org"));
			Assertions.assertTrue(RegexUtil.isUrl("http://abc.com"));

			Assertions.assertFalse(RegexUtil.isUrl("http://un/www.google.com/index.asp"));
			Assertions.assertFalse(RegexUtil.isUrl("abc.com"));
			Assertions.assertFalse(RegexUtil.isUrl("http://abc.c om"));
			Assertions.assertFalse(RegexUtil.isUrl("http2://abc.com"));
		}

		@Test
		@DisplayName("正则校验IPv4")
		public void isIpv4() {
			Assertions.assertTrue(RegexUtil.isIpv4("0.0.0.0"));
			Assertions.assertTrue(RegexUtil.isIpv4("255.255.255.255"));
			Assertions.assertTrue(RegexUtil.isIpv4("127.0.0.1"));
			Assertions.assertFalse(RegexUtil.isIpv4("10.10.10"));
			Assertions.assertFalse(RegexUtil.isIpv4("10.10.10.2561"));
		}

		@Test
		@DisplayName("正则校验IPv6")
		public void isIpv6() {
			Assertions.assertTrue(RegexUtil.isIpv6("1:2:3:4:5:6:7:8"));
			Assertions.assertTrue(RegexUtil.isIpv6("1:2:3:4:5::8"));
			Assertions.assertTrue(RegexUtil.isIpv6("::255.255.255.255"));
			Assertions.assertTrue(RegexUtil.isIpv6("1:2:3:4:5:6:7::"));
			Assertions.assertFalse(RegexUtil.isIpv6("1.2.3.4.5.6.7.8"));
			Assertions.assertFalse(RegexUtil.isIpv6("1::2::3"));
		}

		@Test
		@DisplayName("正则校验时间")
		public void isTime() {
			Assertions.assertTrue(RegexUtil.isTime("00:00:00"));
			Assertions.assertTrue(RegexUtil.isTime("23:59:59"));
			Assertions.assertTrue(RegexUtil.isTime("17:06:30"));
			Assertions.assertFalse(RegexUtil.isTime("17:6:30"));
			Assertions.assertFalse(RegexUtil.isTime("24:16:30"));
		}

		@Test
		@DisplayName("正则校验日期")
		public void isDate() {
			Assertions.assertTrue(RegexUtil.isDate("2016/1/1"));
			Assertions.assertTrue(RegexUtil.isDate("2016/01/01"));
			Assertions.assertTrue(RegexUtil.isDate("20160101"));
			Assertions.assertTrue(RegexUtil.isDate("2016-01-01"));
			Assertions.assertTrue(RegexUtil.isDate("2016.01.01"));
			Assertions.assertTrue(RegexUtil.isDate("2000-02-29"));
			Assertions.assertTrue(RegexUtil.isDate("2011-03-11"));
			Assertions.assertTrue(RegexUtil.isDate("2012-02-29"));

			Assertions.assertFalse(RegexUtil.isDate("2001-02-29"));
			Assertions.assertFalse(RegexUtil.isDate("2016/12/32"));
			Assertions.assertFalse(RegexUtil.isDate("2016.13.1"));
			Assertions.assertFalse(RegexUtil.isDate("2011-02-29"));
			Assertions.assertFalse(RegexUtil.isDate("201a-02-30"));
			Assertions.assertFalse(RegexUtil.isDate("2011-0211"));
		}

		@Test
		@DisplayName("正则校验手机号码")
		public void isMobile() {
			Assertions.assertFalse(RegexUtil.isMobile(null));
			Assertions.assertFalse(RegexUtil.isMobile(""));
			Assertions.assertFalse(RegexUtil.isMobile("1234a"));
			Assertions.assertFalse(RegexUtil.isMobile("1234561"));

			Assertions.assertTrue(RegexUtil.isMobile("11170998762"));
		}

		@Test
		@DisplayName("正则校验真实的手机号码")
		public void isRealMobile() {
			Assertions.assertFalse(RegexUtil.isRealMobile(null));
			Assertions.assertFalse(RegexUtil.isRealMobile(""));
			Assertions.assertFalse(RegexUtil.isRealMobile("1234a"));
			Assertions.assertFalse(RegexUtil.isRealMobile("1234561"));
			Assertions.assertFalse(RegexUtil.isRealMobile("11170998762"));

			Assertions.assertTrue(RegexUtil.isRealMobile("13970998762"));
			Assertions.assertTrue(RegexUtil.isRealMobile("15812345678"));
			Assertions.assertTrue(RegexUtil.isRealMobile("+86 18012345678"));
			Assertions.assertTrue(RegexUtil.isRealMobile("86 18012345678"));
		}

		@Test
		@DisplayName("正则校验座机号码")
		public void isPhone() {
			Assertions.assertFalse(RegexUtil.isPhone(null));
			Assertions.assertFalse(RegexUtil.isPhone("8802973a"));
			Assertions.assertFalse(RegexUtil.isPhone("8908222222"));
			Assertions.assertFalse(RegexUtil.isPhone("89081"));

			Assertions.assertTrue(RegexUtil.isPhone("89019739"));
			Assertions.assertTrue(RegexUtil.isPhone("020-89019739"));
			Assertions.assertTrue(RegexUtil.isPhone("025-85951888"));
		}

	}

	@Nested
	@DisplayName("校验字符")
	class CheckChar {

		@Test
		@DisplayName("正则校验全是汉字字符")
		public void isAllChineseChar() {
			Assertions.assertTrue(RegexUtil.isAllChineseChar("春眠不觉晓"));
			Assertions.assertFalse(RegexUtil.isAllChineseChar("春眠不觉晓，"));
			Assertions.assertFalse(RegexUtil.isAllChineseChar("English"));
		}

		@Test
		@DisplayName("正则校验全是英文字母")
		public void isAllEnglishChar() {
			Assertions
					.assertTrue(RegexUtil.isAllEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
					.assertTrue(RegexUtil.isAllEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtil.isAllEnglishChar("How are you?"));
			Assertions.assertFalse(RegexUtil.isAllEnglishChar("你奈我何"));
		}

		@Test
		@DisplayName("正则校验全是大写字母")
		public void isAllUpperEnglishChar() {
			Assertions.assertTrue(
					RegexUtil.isAllUpperEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertFalse(
					RegexUtil.isAllUpperEnglishChar("abcdefghijklmnopqrstuvwxyz"));
		}

		@Test
		@DisplayName("正则校验全是小写字母")
		public void isAllLowerEnglishChar() {
			Assertions.assertTrue(
					RegexUtil.isAllLowerEnglishChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(
					RegexUtil.isAllLowerEnglishChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		}

		@Test
		@DisplayName("正则校验全是单词字符")
		public void isAllWordChar() {
			Assertions.assertTrue(RegexUtil.isAllWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions.assertTrue(RegexUtil.isAllWordChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertTrue(RegexUtil.isAllWordChar("0123456789"));
			Assertions.assertTrue(RegexUtil.isAllWordChar("_"));
		}

		@Test
		@DisplayName("正则校验全是非单词字符")
		public void isNoneWordChar() {
			Assertions
					.assertFalse(RegexUtil.isNoneWordChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
			Assertions
					.assertFalse(RegexUtil.isNoneWordChar("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtil.isNoneWordChar("0123456789"));
			Assertions.assertFalse(RegexUtil.isNoneWordChar("_"));
		}

	}

	@Nested
	@DisplayName("校验数字")
	class CheckNumber {

		@Test
		@DisplayName("正则校验全是数字")
		public void isAllNumber() {
			Assertions.assertTrue(RegexUtil.isAllNumber("0123456789"));
			Assertions.assertFalse(RegexUtil.isAllNumber("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtil.isAllNumber("How are you?"));
			Assertions.assertFalse(RegexUtil.isAllNumber("你奈我何"));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		public void isNoneNumber() {
			Assertions.assertTrue(RegexUtil.isNoneNumber("abcdefghijklmnopqrstuvwxyz"));
			Assertions.assertFalse(RegexUtil.isNoneNumber("0123456789"));
			Assertions.assertFalse(RegexUtil.isNoneNumber("test008"));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		public void isNDigitNumber() {
			Assertions.assertTrue(RegexUtil.isDigitNumber("0123456789", 10));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		public void isLeastNDigitNumber() {
			Assertions.assertTrue(RegexUtil.isLeastDigitNumber("0123456789", 5));
			Assertions.assertTrue(RegexUtil.isLeastDigitNumber("0123456789", 10));
			Assertions.assertFalse(RegexUtil.isLeastDigitNumber("0123456789", 11));
		}

		@Test
		@DisplayName("正则校验不含任何数字")
		public void isMToNDigitNumber() {
			Assertions.assertTrue(RegexUtil.isRangeDigitNumber("0123456789", 1, 10));
			Assertions.assertFalse(RegexUtil.isRangeDigitNumber("0123456789", 6, 9));
			Assertions.assertFalse(RegexUtil.isRangeDigitNumber("0123456789", 11, 20));
		}

	}

	@Nested
	@DisplayName("校验Markdonw")
	class CheckMarkdown {

		@Test
		@DisplayName("校验含有 Markdonw ![]() ")
		public void isMarkdownImageTag() {
			String newstr = RegexUtil.replaceAllMatchContent(
					"![asgad](http://www.baidu.com/test.png)",
					RegexUtil.REGEX_MARKDOWN_IMAGE_TAG, "![](");
			System.out.println(newstr);
		}

	}

}
