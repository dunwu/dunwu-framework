package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.date.DatePattern;
import io.github.dunwu.tool.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomUtilTest {

    @Test
    public void randomEleSetTest() {
        Set<Integer> set = RandomUtil.randomEleSet(CollectionUtil.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(set.size(), 2);
    }

    @Test
    public void randomElesTest() {
        List<Integer> result = RandomUtil.randomEles(CollectionUtil.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    public void randomDoubleTest() {
        double randomDouble = RandomUtil.randomDouble(0, 1, 0, RoundingMode.HALF_UP);
        Assertions.assertTrue(randomDouble <= 1);
    }

    @Test
    @Disabled
    public void randomBooleanTest() {
        Console.log(RandomUtil.randomBoolean());
    }

    @RepeatedTest(30)
    void anyCBoyFirstName() {
        System.out.println("随机中文男孩名：" + RandomUtil.randomChineseBoyFirstName());
    }

    @RepeatedTest(100)
    void anyCBoyName() {
        System.out.println("随机中文男孩姓名：" + RandomUtil.randomChineseBoyName());
    }

    @RepeatedTest(30)
    void anyCGirlFirstName() {
        System.out.println("随机中文女孩名：" + RandomUtil.randomChineseGirlFirstName());
    }

    @RepeatedTest(100)
    void anyCGirlName() {
        System.out.println("随机中文女孩姓名：" + RandomUtil.randomChineseGirlName());
    }

    @RepeatedTest(10)
    void anyCLastName() {
        System.out.println("随机中文姓氏：" + RandomUtil.randomChineseLastName());
    }

    @RepeatedTest(10)
    void anyCLetterString() {
        int count = RandomUtil.randomInt(10, 100);
        System.out.println(
            "随机中文字组成的字符串：" + RandomUtil.randomChineseLetterString(10, count));
    }

    @RepeatedTest(100)
    void anyCName() {
        System.out.println("随机中文姓名：" + RandomUtil.randomChineseName());
    }

    @RepeatedTest(10)
    void anyDate() {
        LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
        LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

        String date = RandomUtil.randomDate(min, max, DatePattern.NORM_DATETIME_PATTERN);
        System.out.println(
            "RandomUtil.randomDate(min, max, DatePattern.NORM_DATETIME_PATTERN) : " + date);
        // Assertions.assertTrue(DateUtil.che(date, DATE_PATTERN));

        Date date2 = RandomUtil.randomDate(min, max);
        System.out.println("RandomUtil.anyDate(min, max): " + date2);
    }

    @RepeatedTest(10)
    void anyDomain() {
        String domain = RandomUtil.randomDomain();
        System.out.println("random anyDomain: " + domain);
    }

    @RepeatedTest(10)
    void anyEmail() {
        String email = RandomUtil.randomEmail();
        System.out.println("random anyEmail: " + email);
    }

    @RepeatedTest(10)
    void anyFirstName() {
        System.out.println("随机英文女孩名：" + RandomUtil.randomFirstName(true));
        System.out.println("随机英文男孩名：" + RandomUtil.randomFirstName());
    }

    @RepeatedTest(10)
    void anyIPv4() {
        String ip = RandomUtil.randomIpv4();
        System.out.println("RandomUtil.anyIpv4(): " + ip);
        Assertions.assertTrue(ValidatorUtil.isIpv4(ip));
    }

    @RepeatedTest(10)
    void anyLastName() {
        System.out.println("随机英文姓氏：" + RandomUtil.randomLastName());
    }

    @RepeatedTest(10)
    void anyLetterString() {
        int count = RandomUtil.randomInt(10, 100);
        System.out
            .println("随机英文字母组成的字符串：" + RandomUtil.randomString(10, count));
    }

    @RepeatedTest(10)
    void anyMac() {
        System.out.println("RandomUtil.anyMac(): " + RandomUtil.randomMac());
        System.out.println(
            "RandomUtil.anyMac(\":\"): " + RandomUtil.randomMac(":"));
    }

    @RepeatedTest(10)
    void anyNumString() {
        int count = RandomUtil.randomInt(6, 50);
        System.out.println("随机数字组成的字符串：" + RandomUtil.randomNumString(5, count));
    }

    @RepeatedTest(10)
    void anySimpleCLetter() {
        System.out.println("随机简体中文字组成的字符：" + RandomUtil.randomChineseSimpleLetter());
    }

    @RepeatedTest(10)
    void anySimpleCLetterString() {
        int count = RandomUtil.randomInt(11, 100);
        System.out.println("随机简体中文字组成的字符串："
            + RandomUtil.randomChineseSimpleLetterString(10, count));
    }

    @RepeatedTest(10)
    void getRandom() {
        System.out.println(RandomUtil.secureRandom().nextInt());
        System.out.println(RandomUtil.threadLocalRandom().nextInt());
    }

    @RepeatedTest(10)
    void nextDouble() {
        double value = RandomUtil.randomDouble();
        assertThat(value).isBetween(0d, Double.MAX_VALUE);

        value = RandomUtil.randomDouble();
        assertThat(value).isBetween(0d, Double.MAX_VALUE);

        value = RandomUtil.randomDouble(0d, 10d);
        assertThat(value).isBetween(0d, 10d);

        value = RandomUtil.randomDouble(0d, 10d);
        assertThat(value).isBetween(0d, 10d);
    }

    @RepeatedTest(10)
    void nextInt() {
        int value = RandomUtil.randomInt();
        assertThat(value).isBetween(0, Integer.MAX_VALUE);

        value = RandomUtil.randomInt();
        assertThat(value).isBetween(0, Integer.MAX_VALUE);

        value = RandomUtil.randomInt(0, 10);
        assertThat(value).isBetween(0, 10);

        value = RandomUtil.randomInt(0, 10);
        assertThat(value).isBetween(0, 10);
    }

    @RepeatedTest(10)
    void nextLong() {
        long value = RandomUtil.randomLong();
        assertThat(value).isBetween(0L, Long.MAX_VALUE);

        value = RandomUtil.randomLong();
        assertThat(value).isBetween(0L, Long.MAX_VALUE);

        value = RandomUtil.randomLong(0L, 10L);
        assertThat(value).isBetween(0L, 10L);

        value = RandomUtil.randomLong(0L, 10L);
        assertThat(value).isBetween(0L, 10L);
    }

    @RepeatedTest(10)
    void randomEnum() {
        System.out.println(RandomUtil.randomEnum(Color.class).name());
    }

    @RepeatedTest(10)
    void randomString() {
        System.out.println(RandomUtil.randomString(5, 10));
        System.out.println(
            RandomUtil.randomString(5, 10));
    }

    @RepeatedTest(10)
    void randomStringInList() {
        String[] charset = new String[] { "A", "B", "C", "D" };
        List<String> list = Arrays.asList(charset);
        System.out.println("random param: " + RandomUtil.randomInList(list));
    }

    enum Color {

        RED,
        YELLOW,
        BLUE
    }

}
