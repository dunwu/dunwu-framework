package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-13
 */
public class DateUtilTest {

    @Test
    public void test() {
        Duration duration =
            Duration.ofDays(7).plusMinutes(32).plusSeconds(21).plusMillis(500).plusNanos(1000);
        String formatTime = DateUtil.formatDurationChineseString(duration);
        System.out.println("formatTime: " + formatTime);
    }

}
