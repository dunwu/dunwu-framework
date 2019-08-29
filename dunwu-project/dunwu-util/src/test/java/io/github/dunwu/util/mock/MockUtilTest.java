package io.github.dunwu.util.mock;

import io.github.dunwu.util.text.RegexUtil;
import io.github.dunwu.util.time.DateUtil;
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

    @RepeatedTest(10)
    void anyDate() {
        LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
        LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

        String date = MockUtil.anyDate(min, max, DATE_PATTERN);
        System.out.println("MockUtil.anyDate(min, max, \"yyyy-MM-dd hh:mm:ss\"): " + date);
        Assertions.assertTrue(DateUtil.verify(date, DATE_PATTERN));

        Date date2 = MockUtil.anyDate(min, max);
        System.out.println("MockUtil.anyDate(min, max): " + date2);
    }

    @RepeatedTest(10)
    void anyIPv4() {
        String ip = MockUtil.anyIpv4();
        System.out.println("MockUtil.anyIpv4(): " + ip);
        Assertions.assertTrue(RegexUtil.Checker.isValidIpv4(ip));
    }

    @RepeatedTest(10)
    void anyMac() {
        System.out.println("MockUtil.anyMac(): " + MockUtil.anyMac());
        System.out.println("MockUtil.anyMac(\":\"): " + MockUtil.anyMac(":"));
    }

    @RepeatedTest(10)
    void anyDomain() {
        String domain = MockUtil.anyDomain();
        System.out.println("random anyDomain: " + domain);
    }

    @RepeatedTest(10)
    void anyEmail() {
        String email = MockUtil.anyEmail();
        System.out.println("random anyEmail: " + email);
    }

    @RepeatedTest(10)
    void anyFirstName() {
        System.out.println("随机英文女孩名：" + MockUtil.anyFirstName(true));
        System.out.println("随机英文男孩名：" + MockUtil.anyFirstName());
    }

    @RepeatedTest(10)
    void anyLastName() {
        System.out.println("随机英文姓氏：" + MockUtil.anyLastName());
    }

    @RepeatedTest(10)
    void anyCLastName() {
        System.out.println("随机中文姓氏：" + MockUtil.anyChineseLastName());
    }

    @RepeatedTest(30)
    void anyCBoyFirstName() {
        System.out.println("随机中文男孩名：" + MockUtil.anyChineseBoyFirstName());
    }

    @RepeatedTest(30)
    void anyCGirlFirstName() {
        System.out.println("随机中文女孩名：" + MockUtil.anyChineseGirlFirstName());
    }

    @RepeatedTest(100)
    void anyCBoyName() {
        System.out.println("随机中文男孩姓名：" + MockUtil.anyChineseBoyName());
    }

    @RepeatedTest(100)
    void anyCGirlName() {
        System.out.println("随机中文女孩姓名：" + MockUtil.anyChineseGirlName());
    }

    @RepeatedTest(100)
    void anyCName() {
        System.out.println("随机中文姓名：" + MockUtil.anyChineseName());
    }

    @RepeatedTest(10)
    void anyLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out.println("随机英文字母组成的字符串：" + MockUtil.anyLetterString(10, count));
    }

    @RepeatedTest(10)
    void anyCLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out.println("随机中文字组成的字符串：" + MockUtil.anyChineseLetterString(10, count));
    }

    @RepeatedTest(10)
    void anySimpleCLetter() {
        System.out.println("随机简体中文字组成的字符：" + MockUtil.anyChineseSimpleLetter());
    }

    @RepeatedTest(10)
    void anySimpleCLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        System.out.println("随机简体中文字组成的字符串：" + MockUtil.anyChineseSimpleLetterString(10, count));
    }

    @RepeatedTest(10)
    void anyNumString() {
        int count = RandomUtils.nextInt(5, 50);
        System.out.println("随机数字组成的字符串：" + MockUtil.anyNumString(5, count));
    }

    @RepeatedTest(10)
    void mock() {
        String[] charset = new String[] {"A", "B", "C", "D"};
        List<String> list = Arrays.asList(charset);
        System.out.println("random param: " + MockUtil.mock(list));
    }

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
}
