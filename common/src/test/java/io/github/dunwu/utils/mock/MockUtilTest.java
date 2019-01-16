package io.github.dunwu.utils.mock;

import io.github.dunwu.utils.regex.RegexHelper;
import io.github.dunwu.utils.time.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * MockUtil 单元测试
 * @author Zhang Peng
 * @date 2018-12-04
 */
public class MockUtilTest {
    private static final Logger log = LoggerFactory.getLogger(MockUtilTest.class);
    final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

    @RepeatedTest(10)
    void anyDate() {
        LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
        LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

        String date = MockUtil.anyDate(min, max, DATE_PATTERN);
        log.info("MockUtil.anyDate(min, max, \"yyyy-MM-dd hh:mm:ss\"): {}", date);
        Assertions.assertTrue(DateUtil.verify(date, DATE_PATTERN));

        Date date2 = MockUtil.anyDate(min, max);
        log.info("MockUtil.anyDate(min, max): {}", date2);
    }

    @RepeatedTest(10)
    void anyIPv4() {
        String ip = MockUtil.anyIPv4();
        log.info("MockUtil.anyIPv4(): {}", ip);
        Assertions.assertTrue(RegexHelper.Checker.isValidIpv4(ip));
    }

    @RepeatedTest(10)
    void anyMac() {
        log.info("MockUtil.anyMac(): {}", MockUtil.anyMac());
        log.info("MockUtil.anyMac(\":\"): {}", MockUtil.anyMac(":"));
    }

    @RepeatedTest(10)
    void anyDomain() {
        String domain = MockUtil.anyDomain();
        log.info("random anyDomain: {}", domain);
    }

    @RepeatedTest(10)
    void anyEmail() {
        String email = MockUtil.anyEmail();
        log.info("random anyEmail: {}", email);
    }

    @RepeatedTest(10)
    void anyFirstName() {
        log.info("随机英文女孩名：{}", MockUtil.anyFirstName(true));
        log.info("随机英文男孩名：{}", MockUtil.anyFirstName());
    }

    @RepeatedTest(10)
    void anyLastName() {
        log.info("随机英文姓氏：{}", MockUtil.anyLastName());
    }

    @RepeatedTest(10)
    void anyCLastName() {
        log.info("随机中文姓氏：{}", MockUtil.anyCLastName());
    }

    @RepeatedTest(30)
    void anyCBoyFirstName() {
        log.info("随机中文男孩名：{}", MockUtil.anyCBoyFirstName());
    }

    @RepeatedTest(30)
    void anyCGirlFirstName() {
        log.info("随机中文女孩名：{}", MockUtil.anyCGirlFirstName());
    }

    @RepeatedTest(100)
    void anyCBoyName() {
        log.info("随机中文男孩姓名：{}", MockUtil.anyCBoyName());
    }

    @RepeatedTest(100)
    void anyCGirlName() {
        log.info("随机中文女孩姓名：{}", MockUtil.anyCGirlName());
    }

    @RepeatedTest(100)
    void anyCName() {
        log.info("随机中文姓名：{}", MockUtil.anyCName());
    }

    @RepeatedTest(10)
    void anyLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        log.info("随机英文字母组成的字符串：{}", MockUtil.anyLetterString(10, count));
    }

    @RepeatedTest(10)
    void anyCLetterString() {
        int count = RandomUtils.nextInt(10, 100);
        log.info("随机中文字组成的字符串：{}", MockUtil.anyCLetterString(10, count));
    }

    @RepeatedTest(10)
    void anyNumString() {
        int count = RandomUtils.nextInt(5, 50);
        log.info("随机数字组成的字符串：{}", MockUtil.anyNumString(10, count));
    }

    @RepeatedTest(10)
    void mock() {
        String[] charset = new String[] {"A", "B", "C", "D"};
        List<String> list = Arrays.asList(charset);
        log.info("random param: {}", MockUtil.mock(list));
    }
}
