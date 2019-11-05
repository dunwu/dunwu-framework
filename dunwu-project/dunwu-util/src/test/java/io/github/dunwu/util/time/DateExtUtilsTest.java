package io.github.dunwu.util.time;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("all")
class DateExtUtilsTest {

	@Test
	void isSameDay() {
		Date date1 = new Date(106, 10, 1);
		Date date2 = new Date(106, 10, 1, 12, 23, 44);
		assertThat(DateExtUtils.isSameDay(date1, date2)).isTrue();

		Date date3 = new Date(106, 10, 1);
		assertThat(DateExtUtils.isSameDay(date1, date3)).isTrue();

		Date date4 = new Date(106, 10, 2);
		assertThat(DateExtUtils.isSameDay(date1, date4)).isFalse();
	}

	@Test
	void isBetween() {
		Date date1 = new Date(106, 10, 1);
		Date date2 = new Date(106, 10, 1, 12, 23, 44);
		Date date3 = new Date(106, 10, 1);
		Date date4 = new Date(106, 10, 1, 12, 23, 43);
		Date date5 = new Date(106, 10, 2);
		assertThat(DateExtUtils.isBetween(date3, date1, date2)).isTrue();
		assertThat(DateExtUtils.isBetween(date4, date1, date2)).isTrue();

		try {
			DateExtUtils.isBetween(null, date1, date2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DateExtUtils.isBetween(date3, date2, date1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertThat(DateExtUtils.isBetween(date5, date1, date2)).isFalse();
	}

	@Test
	void truncateAndCelling() {
		Date date = new Date(117, 0, 21, 12, 12, 12);

		Date beginYear = new Date(117, 0, 1, 0, 0, 0);
		Date endYear = new Date(new Date(117, 11, 31, 23, 59, 59).getTime() + 999);
		Date nextYear = new Date(118, 0, 1, 0, 0, 0);

		Date beginMonth = new Date(117, 0, 1);
		Date endMonth = new Date(new Date(117, 0, 31, 23, 59, 59).getTime() + 999);
		Date nextMonth = new Date(117, 1, 1);

		Date beginWeek = new Date(117, 0, 16);
		Date endWeek = new Date(new Date(117, 0, 22, 23, 59, 59).getTime() + 999);
		Date nextWeek = new Date(117, 0, 23);

		Date beginDate = new Date(117, 0, 21);
		Date endDate = new Date(new Date(117, 0, 21, 23, 59, 59).getTime() + 999);
		Date nextDate = new Date(117, 0, 22);

		Date beginHour = new Date(117, 0, 21, 12, 0, 0);
		Date endHour = new Date(new Date(117, 0, 21, 12, 59, 59).getTime() + 999);
		Date nextHour = new Date(117, 0, 21, 13, 0, 0);

		Date beginMinute = new Date(117, 0, 21, 12, 12, 0);
		Date endMinute = new Date(new Date(117, 0, 21, 12, 12, 59).getTime() + 999);
		Date nextMinute = new Date(117, 0, 21, 12, 13, 0);

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfYear(date), beginYear))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfYear(date), endYear))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextYear(date), nextYear))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfMonth(date), beginMonth))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfMonth(date), endMonth))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextMonth(date), nextMonth))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfWeek(date), beginWeek))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfWeek(date), endWeek))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextWeek(date), nextWeek))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfDate(date), beginDate))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfDate(date), endDate))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextDate(date), nextDate))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfHour(date), beginHour))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfHour(date), endHour))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextHour(date), nextHour))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.beginOfMinute(date), beginMinute))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.endOfMinute(date), endMinute))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.nextMinute(date), nextMinute))
			.isTrue();
	}

	@Test
	void addDate() {
		Date date = new Date(106, 10, 1, 12, 23, 44);
		Date expectDate1 = new Date(106, 10, 3);
		Date expectDate2 = new Date(106, 9, 31);
		Date expectDate3 = new Date(106, 11, 1);
		Date expectDate4 = new Date(106, 7, 1);
		Date expectDate5 = new Date(106, 10, 1, 13, 23, 44);
		Date expectDate6 = new Date(106, 10, 1, 10, 23, 44);
		Date expectDate7 = new Date(106, 10, 1, 12, 24, 44);
		Date expectDate8 = new Date(106, 10, 1, 12, 21, 44);

		Date expectDate9 = new Date(106, 10, 1, 12, 23, 45);
		Date expectDate10 = new Date(106, 10, 1, 12, 23, 42);

		Date expectDate11 = new Date(106, 10, 8);
		Date expectDate12 = new Date(106, 9, 25);

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addDays(date, 2), expectDate1))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.addDays(date, -1), expectDate2))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addWeeks(date, 1), expectDate11))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.addWeeks(date, -1), expectDate12))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addMonths(date, 1), expectDate3))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.addMonths(date, -3), expectDate4))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addHours(date, 1), expectDate5))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.addHours(date, -2), expectDate6))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addMinutes(date, 1), expectDate7))
			.isTrue();
		assertThat(DateExtUtils.isSameDay(DateExtUtils.addMinutes(date, -2), expectDate8))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.addSeconds(date, 1), expectDate9))
			.isTrue();
		assertThat(
			DateExtUtils.isSameDay(DateExtUtils.addSeconds(date, -2), expectDate10))
			.isTrue();
	}

	@Test
	void setDay() {
		Date date = new Date(116, 10, 1, 10, 10, 1);
		Date expectedDate = new Date(116, 10, 3);
		Date expectedDate2 = new Date(116, 11, 1);
		Date expectedDate3 = new Date(117, 10, 1);
		Date expectedDate4 = new Date(116, 10, 1, 9, 10, 1);
		Date expectedDate5 = new Date(116, 10, 1, 10, 9, 1);
		Date expectedDate6 = new Date(116, 10, 1, 10, 10, 10);

		assertThat(DateExtUtils.isSameDay(DateExtUtils.setDays(date, 3), expectedDate))
			.isTrue();
		assertThat(
			DateExtUtils.isSameDay(DateExtUtils.setMonths(date, 11), expectedDate2))
			.isTrue();
		assertThat(
			DateExtUtils.isSameDay(DateExtUtils.setYears(date, 2017), expectedDate3))
			.isTrue();

		assertThat(DateExtUtils.isSameDay(DateExtUtils.setHours(date, 9), expectedDate4))
			.isTrue();
		assertThat(
			DateExtUtils.isSameDay(DateExtUtils.setMinutes(date, 9), expectedDate5))
			.isTrue();
		assertThat(
			DateExtUtils.isSameDay(DateExtUtils.setSeconds(date, 10), expectedDate6))
			.isTrue();
	}

	@Test
	void getDayOfWeek() {
		Date date = new Date(117, 0, 9);
		assertThat(DateExtUtils.getDayOfWeek(date)).isEqualTo(1);

		Date date2 = new Date(117, 0, 15);
		assertThat(DateExtUtils.getDayOfWeek(date2)).isEqualTo(7);
	}

	@Test
	void isLeapYear() {
		// 2008-01-09,整除4年, true
		Date date = new Date(108, 0, 9);
		assertThat(DateExtUtils.isLeapYear(date)).isTrue();

		// 2000-01-09,整除400年，true
		date = new Date(100, 0, 9);
		assertThat(DateExtUtils.isLeapYear(date)).isTrue();

		// 1900-01-09，整除100年，false
		date = new Date(0, 0, 9);
		assertThat(DateExtUtils.isLeapYear(date)).isFalse();
	}

	@Test
	void getXXofXX() {
		// 2008-02-09, 整除4年, 闰年
		Date date = new Date(108, 2, 9);
		assertThat(DateExtUtils.getMonthLength(date)).isEqualTo(29);

		// 2009-02-09, 整除4年, 非闰年
		Date date2 = new Date(109, 2, 9);
		assertThat(DateExtUtils.getMonthLength(date2)).isEqualTo(28);

		Date date3 = new Date(108, 8, 9);
		assertThat(DateExtUtils.getMonthLength(date3)).isEqualTo(31);

		Date date4 = new Date(109, 11, 30);
		assertThat(DateExtUtils.getDayOfYear(date4)).isEqualTo(364);

		Date date5 = new Date(117, 0, 12);
		assertThat(DateExtUtils.getWeekOfMonth(date5)).isEqualTo(3);
		assertThat(DateExtUtils.getWeekOfYear(date5)).isEqualTo(3);
	}

}
