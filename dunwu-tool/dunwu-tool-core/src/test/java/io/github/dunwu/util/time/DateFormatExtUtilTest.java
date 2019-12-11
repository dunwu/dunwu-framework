package io.github.dunwu.util.time;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatExtUtilTest {

    @Test
    void format() {
        Date date = new Date(116, 10, 1, 12, 23, 44);

        assertThat(DateFormatExtUtil.DEFAULT_FORMAT.format(date))
            .isEqualTo("2016-11-01 12:23:44.000");
        assertThat(DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT.format(date))
            .isEqualTo("2016-11-01 12:23:44");

        assertThat(DateFormatExtUtil.format(date,
            DateFormatExtUtil.DatePattern.PATTERN_DEFAULT.pattern()))
            .isEqualTo("2016-11-01 12:23:44.000");
        assertThat(DateFormatExtUtil.format(date,
            DateFormatExtUtil.DatePattern.PATTERN_DEFAULT.pattern()))
            .isEqualTo("2016-11-01 12:23:44.000");
    }

    @Test
    void formatDuration() {
        assertThat(DateFormatExtUtil.formatDuration(100)).isEqualTo("00:00:00.100");

        assertThat(DateFormatExtUtil.formatDuration(new Date(100), new Date(3000)))
            .isEqualTo("00:00:02.900");

        assertThat(DateFormatExtUtil.formatDuration(
            DateExtUtil.MILLIS_PER_DAY * 2 + DateExtUtil.MILLIS_PER_HOUR * 4))
            .isEqualTo("52:00:00.000");

        assertThat(
            DateFormatExtUtil.formatDurationOnSecond(new Date(100), new Date(3000)))
            .isEqualTo("00:00:02");

        assertThat(DateFormatExtUtil.formatDurationOnSecond(2000)).isEqualTo("00:00:02");

        assertThat(DateFormatExtUtil.formatDurationOnSecond(
            DateExtUtil.MILLIS_PER_DAY * 2 + DateExtUtil.MILLIS_PER_HOUR * 4))
            .isEqualTo("52:00:00");
    }

    @Test
    void formatFriendlyTimeSpanByNow() throws ParseException {
        try {
            Date now = DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT
                .parse("2016-12-11 23:30:00");

            ClockUtil.useDummyClock(now);

            Date lessOneSecond = DateFormatExtUtil.DEFAULT_FORMAT
                .parse("2016-12-11 23:29:59.500");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(lessOneSecond))
                .isEqualTo("刚刚");

            Date lessOneMinute = DateFormatExtUtil.DEFAULT_FORMAT
                .parse("2016-12-11 23:29:55.000");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(lessOneMinute))
                .isEqualTo("5秒前");

            Date lessOneHour = DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT
                .parse("2016-12-11 23:00:00");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(lessOneHour))
                .isEqualTo("30分钟前");

            Date today = DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT
                .parse("2016-12-11 1:00:00");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(today))
                .isEqualTo("今天01:00");

            Date yesterday = DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT
                .parse("2016-12-10 1:00:00");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(yesterday))
                .isEqualTo("昨天01:00");

            Date threeDayBefore = DateFormatExtUtil.DEFAULT_ON_SECOND_FORMAT
                .parse("2016-12-09 1:00:00");
            assertThat(DateFormatExtUtil.formatFriendlyTimeSpanByNow(threeDayBefore))
                .isEqualTo("2016-12-09");
        } finally {
            ClockUtil.useDefaultClock();
        }
    }

    @Test
    void isoDateFormat() {
        Date date = new Date(116, 10, 1, 12, 23, 44);
        assertThat(DateFormatExtUtil.ISO_FORMAT.format(date))
            .contains("2016-11-01T12:23:44.000");
        assertThat(DateFormatExtUtil.ISO_ON_SECOND_FORMAT.format(date))
            .contains("2016-11-01T12:23:44");
        assertThat(DateFormatExtUtil.ISO_ON_DATE_FORMAT.format(date))
            .isEqualTo("2016-11-01");
    }

    @Test
    void parseDate() throws ParseException {
        Date date = new Date(116, 10, 1, 12, 23, 44);
        Date resultDate = DateFormatExtUtil.parseDate(
            DateFormatExtUtil.DatePattern.PATTERN_DEFAULT,
            "2016-11-01 12:23:44.000");
        assertThat(resultDate.getTime() == date.getTime()).isTrue();
    }

}
