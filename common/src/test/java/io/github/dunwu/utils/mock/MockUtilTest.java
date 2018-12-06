package io.github.dunwu.utils.mock;

import io.github.dunwu.utils.regex.RegexUtil;
import io.github.dunwu.utils.time.DateUtil;
import org.junit.Assert;
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
    void testDate() {
        LocalDateTime min = LocalDateTime.of(2018, 12, 1, 0, 0, 0);
        LocalDateTime max = LocalDateTime.of(2018, 12, 6, 23, 59, 59);

        String date = MockUtil.date(min, max, DATE_PATTERN);
        log.info("random date: {}", date);
        Assert.assertTrue(DateUtil.verify(date, DATE_PATTERN));

        Date date2 = MockUtil.date(min, max);
        log.info("random date2: {}", date2);
    }

    @RepeatedTest(10)
    void testIpv4() {
        String ip = MockUtil.ipv4();
        log.info("random ipv4: {}", ip);
        Assert.assertTrue(RegexUtil.isValidateIpv4(ip));
    }

    @RepeatedTest(10)
    void testMac() {
        String mac = MockUtil.mac();
        log.info("random mac: {}", mac);
    }

    @RepeatedTest(10)
    public void testDomain() {
        String domain = MockUtil.domain();
        log.info("random domain: {}", domain);
    }

    @RepeatedTest(10)
    public void testEmail() {
        String email = MockUtil.email();
        log.info("random email: {}", email);
    }

    @RepeatedTest(10)
    public void testMock() {
        String[] charset = new String[] {"A", "B", "C", "D"};
        List<String> list = Arrays.asList(charset);
        log.info("random param: {}", MockUtil.mock(list));
    }
}
