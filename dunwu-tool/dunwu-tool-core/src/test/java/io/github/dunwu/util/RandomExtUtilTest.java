package io.github.dunwu.util;

import io.github.dunwu.util.text.ValidatorUtil;
import io.github.dunwu.util.time.DateExtUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RandomExtUtilTest {

    @RepeatedTest(30)
    void anyCBoyFirstName() {
        System.out.println("随机中文男孩名：" + RandomExtUtil.randomChineseBoyFirstName());
    }

    @RepeatedTest(100)
    void anyCBoyName() {
        System.out.println("随机中文男孩姓名：" + RandomExtUtil.randomChineseBoyName());
    }

    @RepeatedTest(30)
    void anyCGirlFirstName() {
        System.out.println("随机中文女孩名：" + RandomExtUtil.randomChineseGirlFirstName());
    }

    @RepeatedTest(100)
    void anyCGirlName() {
        System.out.println("随机中文女孩姓名：" + RandomExtUtil.randomChineseGirlName());
    }

    @RepeatedTest(10)
    void anyCLastName() {
        System.out.println("随机中文姓氏：" + RandomExtUtil.randomChineseLastName());
    }

    @RepeatedTest(10)
    void anyCLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out.println(
            "随机中文字组成的字符串：" + RandomExtUtil.randomChineseLetterString(10, count));
    }

    @RepeatedTest(100)
    void anyCName() {
        System.out.println("随机中文姓名：" + RandomExtUtil.randomChineseName());
    }

    @RepeatedTest(10)
    void anyDate() {
        LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
        LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

        String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
        String date = RandomExtUtil.randomDate(min, max, DATE_PATTERN);
        System.out.println(
            "RandomExtUtils.anyDate(min, max, \"yyyy-MM-dd hh:mm:ss\"): " + date);
        Assertions.assertTrue(DateExtUtil.verify(date, DATE_PATTERN));

        Date date2 = RandomExtUtil.randomDate(min, max);
        System.out.println("RandomExtUtils.anyDate(min, max): " + date2);
    }

    @RepeatedTest(10)
    void anyDomain() {
        String domain = RandomExtUtil.randomDomain();
        System.out.println("random anyDomain: " + domain);
    }

    @RepeatedTest(10)
    void anyEmail() {
        String email = RandomExtUtil.randomEmail();
        System.out.println("random anyEmail: " + email);
    }

    @RepeatedTest(10)
    void anyFirstName() {
        System.out.println("随机英文女孩名：" + RandomExtUtil.randomFirstName(true));
        System.out.println("随机英文男孩名：" + RandomExtUtil.randomFirstName());
    }

    @RepeatedTest(10)
    void anyIPv4() {
        String ip = RandomExtUtil.randomIpv4();
        System.out.println("RandomExtUtils.anyIpv4(): " + ip);
        Assertions.assertTrue(ValidatorUtil.isIpv4(ip));
    }

    @RepeatedTest(10)
    void anyLastName() {
        System.out.println("随机英文姓氏：" + RandomExtUtil.randomLastName());
    }

    @RepeatedTest(10)
    void anyLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out
            .println("随机英文字母组成的字符串：" + RandomExtUtil.randomLetterString(10, count));
    }

    @RepeatedTest(10)
    void anyMac() {
        System.out.println("RandomExtUtils.anyMac(): " + RandomExtUtil.randomMac());
        System.out.println(
            "RandomExtUtils.anyMac(\":\"): " + RandomExtUtil.randomMac(":"));
    }

    @RepeatedTest(10)
    void anyNumString() {
        int count = RandomUtils.nextInt(5, 50);
        System.out.println("随机数字组成的字符串：" + RandomExtUtil.randomNumString(5, count));
    }

    @RepeatedTest(10)
    void anySimpleCLetter() {
        System.out.println("随机简体中文字组成的字符：" + RandomExtUtil.randomChineseSimpleLetter());
    }

    @RepeatedTest(10)
    void anySimpleCLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out.println("随机简体中文字组成的字符串："
            + RandomExtUtil.randomChineseSimpleLetterString(10, count));
    }

    @RepeatedTest(10)
    void getRandom() {
        System.out.println(RandomExtUtil.secureRandom().nextInt());
        System.out.println(RandomExtUtil.threadLocalRandom().nextInt());
    }

    @RepeatedTest(10)
    void nextDouble() {
        double value = RandomExtUtil.nextDouble();
        assertThat(value).isBetween(0d, Double.MAX_VALUE);

        value = RandomExtUtil.nextDouble(RandomExtUtil.threadLocalRandom());
        assertThat(value).isBetween(0d, Double.MAX_VALUE);

        value = RandomExtUtil.nextDouble(0d, 10d);
        assertThat(value).isBetween(0d, 10d);

        value = RandomExtUtil.nextDouble(RandomExtUtil.threadLocalRandom(), 0d, 10d);
        assertThat(value).isBetween(0d, 10d);
    }

    @RepeatedTest(10)
    void nextFloat() {
        float value = RandomExtUtil.nextFloat();
        assertThat(value).isBetween(0f, Float.MAX_VALUE);

        value = RandomExtUtil.nextFloat(RandomExtUtil.threadLocalRandom());
        assertThat(value).isBetween(0f, Float.MAX_VALUE);

        value = RandomExtUtil.nextFloat(0f, 10f);
        assertThat(value).isBetween(0f, 10f);

        value = RandomExtUtil.nextFloat(RandomExtUtil.threadLocalRandom(), 0f, 10f);
        assertThat(value).isBetween(0f, 10f);
    }

    @RepeatedTest(10)
    void nextInt() {
        int value = RandomExtUtil.nextInt();
        assertThat(value).isBetween(0, Integer.MAX_VALUE);

        value = RandomExtUtil.nextInt(RandomExtUtil.threadLocalRandom());
        assertThat(value).isBetween(0, Integer.MAX_VALUE);

        value = RandomExtUtil.nextInt(0, 10);
        assertThat(value).isBetween(0, 10);

        value = RandomExtUtil.nextInt(RandomExtUtil.threadLocalRandom(), 0, 10);
        assertThat(value).isBetween(0, 10);
    }

    @RepeatedTest(10)
    void nextLong() {
        long value = RandomExtUtil.nextLong();
        assertThat(value).isBetween(0L, Long.MAX_VALUE);

        value = RandomExtUtil.nextLong(RandomExtUtil.threadLocalRandom());
        assertThat(value).isBetween(0L, Long.MAX_VALUE);

        value = RandomExtUtil.nextLong(0L, 10L);
        assertThat(value).isBetween(0L, 10L);

        value = RandomExtUtil.nextLong(RandomExtUtil.threadLocalRandom(), 0L, 10L);
        assertThat(value).isBetween(0L, 10L);
    }

    @RepeatedTest(10)
    void randomAscii() {
        System.out.println(RandomExtUtil.randomAscii(5, 10));
        System.out.println(
            RandomExtUtil.randomAscii(RandomExtUtil.threadLocalRandom(), 5, 10));
    }

    @RepeatedTest(10)
    void randomEnum() {
        System.out.println(RandomExtUtil.randomEnum(Color.class).name());
    }

    @RepeatedTest(10)
    void randomLetter() {
        System.out.println(RandomExtUtil.randomLetter(5, 10));
        System.out.println(
            RandomExtUtil.randomLetter(RandomExtUtil.threadLocalRandom(), 5, 10));
    }

    @RepeatedTest(10)
    void randomString() {
        System.out.println(RandomExtUtil.randomString(5, 10));
        System.out.println(
            RandomExtUtil.randomString(RandomExtUtil.threadLocalRandom(), 5, 10));
    }

    @RepeatedTest(10)
    void randomStringInList() {
        String[] charset = new String[] { "A", "B", "C", "D" };
        List<String> list = Arrays.asList(charset);
        System.out.println("random param: " + RandomExtUtil.randomInList(list));
    }

    enum Color {

        RED,
        YELLOW,
        BLUE
    }

}
