package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link DateUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@DisplayName("DateUtil 单元测试")
class DateUtilTest {

    @Nested
    @DisplayName("formatDurationTime 测试")
    class formatDurationTimeTest {

        @Test
        @DisplayName("只有天时只显示天")
        void shouldShowOnlyDaysWhenDurationIsExactDays() {
            Duration duration = Duration.ofDays(3);

            String result = DateUtil.formatDurationTime(duration);

            assertThat(result).contains("3d");
        }

        @Test
        @DisplayName("只有小时时只显示小时")
        void shouldShowOnlyHoursWhenDurationIsExactHours() {
            Duration duration = Duration.ofHours(5);

            String result = DateUtil.formatDurationTime(duration);

            assertThat(result).contains("5h");
            assertThat(result).doesNotContain("d");
        }

        @Test
        @DisplayName("只有分钟时只显示分钟")
        void shouldShowOnlyMinutesWhenDurationIsExactMinutes() {
            Duration duration = Duration.ofMinutes(45);

            String result = DateUtil.formatDurationTime(duration);

            assertThat(result).contains("45m");
        }

        @Test
        @DisplayName("毫秒级 Duration 显示 ms")
        void shouldShowMillisecondsWhenDurationIsMillis() {
            Duration duration = Duration.ofMillis(500);

            String result = DateUtil.formatDurationTime(duration);

            assertThat(result).contains("500ms");
        }

        @Test
        @DisplayName("零时长返回空字符串")
        void shouldReturnEmptyStringForZeroDuration() {
            Duration duration = Duration.ZERO;

            String result = DateUtil.formatDurationTime(duration);

            assertThat(result).isEmpty();
        }

    }

    @Nested
    @DisplayName("formatDurationChineseTime 测试")
    class formatDurationChineseTimeTest {

        @Test
        @DisplayName("只有天时只显示天（中文）")
        void shouldShowOnlyDaysInChinese() {
            Duration duration = Duration.ofDays(2);

            String result = DateUtil.formatDurationChineseTime(duration);

            assertThat(result).contains("2天");
        }

        @Test
        @DisplayName("只有小时时只显示小时（中文）")
        void shouldShowOnlyHoursInChinese() {
            Duration duration = Duration.ofHours(3);

            String result = DateUtil.formatDurationChineseTime(duration);

            assertThat(result).contains("3时");
            assertThat(result).doesNotContain("天");
        }

        @Test
        @DisplayName("只有分钟时只显示分钟（中文）")
        void shouldShowOnlyMinutesInChinese() {
            Duration duration = Duration.ofMinutes(20);

            String result = DateUtil.formatDurationChineseTime(duration);

            assertThat(result).contains("20分");
        }

        @Test
        @DisplayName("毫秒级 Duration 显示毫秒（中文）")
        void shouldShowMillisecondsInChinese() {
            Duration duration = Duration.ofMillis(200);

            String result = DateUtil.formatDurationChineseTime(duration);

            assertThat(result).contains("200毫秒");
        }

        @Test
        @DisplayName("零时长返回空字符串（中文）")
        void shouldReturnEmptyStringForZeroDurationInChinese() {
            Duration duration = Duration.ZERO;

            String result = DateUtil.formatDurationChineseTime(duration);

            assertThat(result).isEmpty();
        }

    }

}
