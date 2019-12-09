package io.github.dunwu.util.time;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatExtUtilsTest {

	@Test
	void format() {
		Date date = new Date(116, 10, 1, 12, 23, 44);

		assertThat(DateFormatExtUtils.DEFAULT_FORMAT.format(date))
			.isEqualTo("2016-11-01 12:23:44.000");
		assertThat(DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT.format(date))
			.isEqualTo("2016-11-01 12:23:44");

		assertThat(DateFormatExtUtils.format(date,
			DateFormatExtUtils.DatePattern.PATTERN_DEFAULT.pattern()))
			.isEqualTo("2016-11-01 12:23:44.000");
		assertThat(DateFormatExtUtils.format(date,
			DateFormatExtUtils.DatePattern.PATTERN_DEFAULT.pattern()))
			.isEqualTo("2016-11-01 12:23:44.000");
	}

	@Test
	void formatDuration() {
		assertThat(DateFormatExtUtils.formatDuration(100)).isEqualTo("00:00:00.100");

		assertThat(DateFormatExtUtils.formatDuration(new Date(100), new Date(3000)))
			.isEqualTo("00:00:02.900");

		assertThat(DateFormatExtUtils.formatDuration(
			DateExtUtils.MILLIS_PER_DAY * 2 + DateExtUtils.MILLIS_PER_HOUR * 4))
			.isEqualTo("52:00:00.000");

		assertThat(
			DateFormatExtUtils.formatDurationOnSecond(new Date(100), new Date(3000)))
			.isEqualTo("00:00:02");

		assertThat(DateFormatExtUtils.formatDurationOnSecond(2000)).isEqualTo("00:00:02");

		assertThat(DateFormatExtUtils.formatDurationOnSecond(
			DateExtUtils.MILLIS_PER_DAY * 2 + DateExtUtils.MILLIS_PER_HOUR * 4))
			.isEqualTo("52:00:00");
	}

	@Test
	void formatFriendlyTimeSpanByNow() throws ParseException {
		try {
			Date now = DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT
				.parse("2016-12-11 23:30:00");

			ClockUtils.useDummyClock(now);

			Date lessOneSecond = DateFormatExtUtils.DEFAULT_FORMAT
				.parse("2016-12-11 23:29:59.500");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(lessOneSecond))
				.isEqualTo("刚刚");

			Date lessOneMinute = DateFormatExtUtils.DEFAULT_FORMAT
				.parse("2016-12-11 23:29:55.000");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(lessOneMinute))
				.isEqualTo("5秒前");

			Date lessOneHour = DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT
				.parse("2016-12-11 23:00:00");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(lessOneHour))
				.isEqualTo("30分钟前");

			Date today = DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT
				.parse("2016-12-11 1:00:00");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(today))
				.isEqualTo("今天01:00");

			Date yesterday = DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT
				.parse("2016-12-10 1:00:00");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(yesterday))
				.isEqualTo("昨天01:00");

			Date threeDayBefore = DateFormatExtUtils.DEFAULT_ON_SECOND_FORMAT
				.parse("2016-12-09 1:00:00");
			assertThat(DateFormatExtUtils.formatFriendlyTimeSpanByNow(threeDayBefore))
				.isEqualTo("2016-12-09");
		} finally {
			ClockUtils.useDefaultClock();
		}
	}

	@Test
	void isoDateFormat() {
		Date date = new Date(116, 10, 1, 12, 23, 44);
		assertThat(DateFormatExtUtils.ISO_FORMAT.format(date))
			.contains("2016-11-01T12:23:44.000");
		assertThat(DateFormatExtUtils.ISO_ON_SECOND_FORMAT.format(date))
			.contains("2016-11-01T12:23:44");
		assertThat(DateFormatExtUtils.ISO_ON_DATE_FORMAT.format(date))
			.isEqualTo("2016-11-01");
	}

	@Test
	void parseDate() throws ParseException {
		Date date = new Date(116, 10, 1, 12, 23, 44);
		Date resultDate = DateFormatExtUtils.parseDate(
			DateFormatExtUtils.DatePattern.PATTERN_DEFAULT,
			"2016-11-01 12:23:44.000");
		assertThat(resultDate.getTime() == date.getTime()).isTrue();
	}

}
