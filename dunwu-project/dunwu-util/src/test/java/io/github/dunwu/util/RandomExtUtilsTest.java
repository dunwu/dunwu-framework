package io.github.dunwu.util;

import io.github.dunwu.util.text.RegexUtil;
import io.github.dunwu.util.time.DateExtUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RandomExtUtilsTest {

	final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

	@RepeatedTest(10)
	void getRandom() {
		System.out.println(RandomExtUtils.secureRandom().nextInt());
		System.out.println(RandomExtUtils.threadLocalRandom().nextInt());
	}

	@RepeatedTest(10)
	void nextInt() {
		int value = RandomExtUtils.nextInt();
		assertThat(value).isBetween(0, Integer.MAX_VALUE);

		value = RandomExtUtils.nextInt(RandomExtUtils.threadLocalRandom());
		assertThat(value).isBetween(0, Integer.MAX_VALUE);

		value = RandomExtUtils.nextInt(0, 10);
		assertThat(value).isBetween(0, 10);

		value = RandomExtUtils.nextInt(RandomExtUtils.threadLocalRandom(), 0, 10);
		assertThat(value).isBetween(0, 10);
	}

	@RepeatedTest(10)
	void nextLong() {
		long value = RandomExtUtils.nextLong();
		assertThat(value).isBetween(0L, Long.MAX_VALUE);

		value = RandomExtUtils.nextLong(RandomExtUtils.threadLocalRandom());
		assertThat(value).isBetween(0L, Long.MAX_VALUE);

		value = RandomExtUtils.nextLong(0L, 10L);
		assertThat(value).isBetween(0L, 10L);

		value = RandomExtUtils.nextLong(RandomExtUtils.threadLocalRandom(), 0L, 10L);
		assertThat(value).isBetween(0L, 10L);
	}

	@RepeatedTest(10)
	void nextDouble() {
		double value = RandomExtUtils.nextDouble();
		assertThat(value).isBetween(0d, Double.MAX_VALUE);

		value = RandomExtUtils.nextDouble(RandomExtUtils.threadLocalRandom());
		assertThat(value).isBetween(0d, Double.MAX_VALUE);

		value = RandomExtUtils.nextDouble(0d, 10d);
		assertThat(value).isBetween(0d, 10d);

		value = RandomExtUtils.nextDouble(RandomExtUtils.threadLocalRandom(), 0d, 10d);
		assertThat(value).isBetween(0d, 10d);
	}

	@RepeatedTest(10)
	void nextFloat() {
		float value = RandomExtUtils.nextFloat();
		assertThat(value).isBetween(0f, Float.MAX_VALUE);

		value = RandomExtUtils.nextFloat(RandomExtUtils.threadLocalRandom());
		assertThat(value).isBetween(0f, Float.MAX_VALUE);

		value = RandomExtUtils.nextFloat(0f, 10f);
		assertThat(value).isBetween(0f, 10f);

		value = RandomExtUtils.nextFloat(RandomExtUtils.threadLocalRandom(), 0f, 10f);
		assertThat(value).isBetween(0f, 10f);
	}

	@RepeatedTest(10)
	void randomString() {
		System.out.println(RandomExtUtils.randomString(5, 10));
		System.out.println(
			RandomExtUtils.randomString(RandomExtUtils.threadLocalRandom(), 5, 10));
	}

	@RepeatedTest(10)
	void randomLetter() {
		System.out.println(RandomExtUtils.randomLetter(5, 10));
		System.out.println(
			RandomExtUtils.randomLetter(RandomExtUtils.threadLocalRandom(), 5, 10));
	}

	@RepeatedTest(10)
	void randomAscii() {
		System.out.println(RandomExtUtils.randomAscii(5, 10));
		System.out.println(
			RandomExtUtils.randomAscii(RandomExtUtils.threadLocalRandom(), 5, 10));
	}

	@RepeatedTest(10)
	void randomEnum() {
		System.out.println(RandomExtUtils.randomEnum(Color.class).name());
	}

	@RepeatedTest(10)
	void anyDate() {
		LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
		LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

		String date = RandomExtUtils.randomDate(min, max, DATE_PATTERN);
		System.out.println(
			"RandomExtUtils.anyDate(min, max, \"yyyy-MM-dd hh:mm:ss\"): " + date);
		Assertions.assertTrue(DateExtUtils.verify(date, DATE_PATTERN));

		Date date2 = RandomExtUtils.randomDate(min, max);
		System.out.println("RandomExtUtils.anyDate(min, max): " + date2);
	}

	@RepeatedTest(10)
	void anyIPv4() {
		String ip = RandomExtUtils.randomIpv4();
		System.out.println("RandomExtUtils.anyIpv4(): " + ip);
		Assertions.assertTrue(RegexUtil.isIpv4(ip));
	}

	@RepeatedTest(10)
	void anyMac() {
		System.out.println("RandomExtUtils.anyMac(): " + RandomExtUtils.randomMac());
		System.out.println(
			"RandomExtUtils.anyMac(\":\"): " + RandomExtUtils.randomMac(":"));
	}

	@RepeatedTest(10)
	void anyDomain() {
		String domain = RandomExtUtils.randomDomain();
		System.out.println("random anyDomain: " + domain);
	}

	@RepeatedTest(10)
	void anyEmail() {
		String email = RandomExtUtils.randomEmail();
		System.out.println("random anyEmail: " + email);
	}

	@RepeatedTest(10)
	void anyFirstName() {
		System.out.println("随机英文女孩名：" + RandomExtUtils.randomFirstName(true));
		System.out.println("随机英文男孩名：" + RandomExtUtils.randomFirstName());
	}

	@RepeatedTest(10)
	void anyLastName() {
		System.out.println("随机英文姓氏：" + RandomExtUtils.randomLastName());
	}

	@RepeatedTest(10)
	void anyCLastName() {
		System.out.println("随机中文姓氏：" + RandomExtUtils.randomChineseLastName());
	}

	@RepeatedTest(30)
	void anyCBoyFirstName() {
		System.out.println("随机中文男孩名：" + RandomExtUtils.randomChineseBoyFirstName());
	}

	@RepeatedTest(30)
	void anyCGirlFirstName() {
		System.out.println("随机中文女孩名：" + RandomExtUtils.randomChineseGirlFirstName());
	}

	@RepeatedTest(100)
	void anyCBoyName() {
		System.out.println("随机中文男孩姓名：" + RandomExtUtils.randomChineseBoyName());
	}

	@RepeatedTest(100)
	void anyCGirlName() {
		System.out.println("随机中文女孩姓名：" + RandomExtUtils.randomChineseGirlName());
	}

	@RepeatedTest(100)
	void anyCName() {
		System.out.println("随机中文姓名：" + RandomExtUtils.randomChineseName());
	}

	@RepeatedTest(10)
	void anyLetterString() {
		int count = RandomUtils.nextInt(10, 100);
		System.out
			.println("随机英文字母组成的字符串：" + RandomExtUtils.randomLetterString(10, count));
	}

	@RepeatedTest(10)
	void anyCLetterString() {
		int count = RandomUtils.nextInt(10, 100);
		System.out.println(
			"随机中文字组成的字符串：" + RandomExtUtils.randomChineseLetterString(10, count));
	}

	@RepeatedTest(10)
	void anySimpleCLetter() {
		System.out.println("随机简体中文字组成的字符：" + RandomExtUtils.randomChineseSimpleLetter());
	}

	@RepeatedTest(10)
	void anySimpleCLetterString() {
		int count = RandomUtils.nextInt(10, 100);
		System.out.println("随机简体中文字组成的字符串："
			+ RandomExtUtils.randomChineseSimpleLetterString(10, count));
	}

	@RepeatedTest(10)
	void anyNumString() {
		int count = RandomUtils.nextInt(5, 50);
		System.out.println("随机数字组成的字符串：" + RandomExtUtils.randomNumString(5, count));
	}

	@RepeatedTest(10)
	void randomStringInList() {
		String[] charset = new String[] { "A", "B", "C", "D" };
		List<String> list = Arrays.asList(charset);
		System.out.println("random param: " + RandomExtUtils.randomStringInList(list));
	}

	enum Color {

		RED,
		YELLOW,
		BLUE
	}

}
