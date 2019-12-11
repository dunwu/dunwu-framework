package io.github.dunwu.util.mock;

import io.github.dunwu.util.RandomExtUtil;
import io.github.dunwu.util.text.ValidatorUtil;
import io.github.dunwu.util.time.DateExtUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * MockUtil 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-04
 */
public class MockUtilTest {

    final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    @Test
    public void test() {
        char begin = '\u4E00';
        char end = '\u9FA5';
        int count = 0;
        for (char index = begin; index <= end; index++) {
            System.out.print(index + "\t");
            count++;
            if (count % 10 == 0) {
                System.out.println("");
            }
        }
        System.out.println("count: " + count);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        for (int i = 0xB0; i < 0xF7; i++) {
            for (int j = 0xA1; j < 0xFF; j++) {
                byte[] bytes = new byte[2];
                bytes[0] = (byte) i;
                bytes[1] = (byte) j;
                String s = new String(bytes, "GB2312");
                System.out.println(s);
            }
        }
    }

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
    void mock() {
        String[] charset = new String[] { "A", "B", "C", "D" };
        List<String> list = Arrays.asList(charset);
        System.out.println("random param: " + RandomExtUtil.randomInList(list));
    }

}
