package io.github.dunwu.util.time;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类. 在不方便使用joda-time时，使用本类降低Date处理的复杂度与性能消耗, 封装Common Lang及移植Jodd的最常用日期方法
 */
public class DateExtUtils extends DateUtils {

	// milliseconds in a
	// standard day.

	public static final int NUM4 = 4;

	public static final int NUM100 = 100;

	public static final int NUM400 = 400;

	public static final int MONTH_DECEMBER = 12;

	public static final int MONTH_FEBRUARY = 2;

	private static final int[] MONTH_LENGTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
		30, 31 };

	/**
	 * 判断日期是否在范围内，包含相等的日期
	 */
	public static boolean isBetween(final Date date, final Date start, final Date end) {
		if (date == null || start == null || end == null || start.after(end)) {
			throw new IllegalArgumentException(
				"some date parameters is null or dateBein after dateEnd");
		}
		return !date.before(start) && !date.after(end);
	}

	// 获取指定日期
	// -------------------------------------------------------------------------------------------------

	/**
	 * 获得日期是一年的第几天，返回值从1开始
	 */
	public static int getDayOfYear(final Date date) {
		return get(date, Calendar.DAY_OF_YEAR);
	}

	private static int get(final Date date, final int field) {
		Validate.notNull(date, "The date must not be null");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 获得日期是一月的第几周，返回值从1开始. 开始的一周，只要有一天在那个月里都算. 已改为中国习惯，1 是Monday，而不是Sunday
	 */
	public static int getWeekOfMonth(final Date date) {
		return getFirstMonday(date, Calendar.WEEK_OF_MONTH);
	}

	private static int getFirstMonday(final Date date, final int field) {
		Validate.notNull(date, "The date must not be null");
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		return cal.get(field);
	}

	/**
	 * 获得日期是一年的第几周，返回值从1开始. 开始的一周，只要有一天在那一年里都算.已改为中国习惯，1 是Monday，而不是Sunday
	 */
	public static int getWeekOfYear(final Date date) {
		return getFirstMonday(date, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-11-10 00:00:00
	 */
	public static Date beginOfDate(final Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}

	// beginOf
	// -------------------------------------------------------------------------------------------------

	/**
	 * 2016-11-10 07:33:23, 则返回2016-1-1 00:00:00
	 */
	public static Date beginOfYear(final Date date) {
		return DateUtils.truncate(date, Calendar.YEAR);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-11-1 00:00:00
	 */
	public static Date beginOfMonth(final Date date) {
		return DateUtils.truncate(date, Calendar.MONTH);
	}

	/**
	 * 2017-1-20 07:33:23, 则返回2017-1-16 00:00:00
	 */
	public static Date beginOfWeek(final Date date) {
		return DateUtils.truncate(
			DateUtils.addDays(date, 1 - DateExtUtils.getDayOfWeek(date)),
			Calendar.DATE);
	}

	/**
	 * 获得日期是一周的第几天. 已改为中国习惯，1 是Monday，而不是Sundays.
	 */
	public static int getDayOfWeek(final Date date) {
		int result = getFirstMonday(date, Calendar.DAY_OF_WEEK);
		return result == 1 ? 7 : result - 1;
	}

	public static Date beginOfDay(final Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 2016-12-10 07:33:23, 则返回2016-12-10 07:00:00
	 */
	public static Date beginOfHour(final Date date) {
		return DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 2016-12-10 07:33:23, 则返回2016-12-10 07:33:00
	 */
	public static Date beginOfMinute(final Date date) {
		return DateUtils.truncate(date, Calendar.MINUTE);
	}

	// endOf
	// -------------------------------------------------------------------------------------------------

	/**
	 * 2017-1-23 07:33:23, 则返回2017-1-23 23:59:59.999
	 */
	public static Date endOfDate(final Date date) {
		return new Date(nextDate(date).getTime() - 1);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-11-11 00:00:00
	 */
	public static Date nextDate(final Date date) {
		return DateUtils.ceiling(date, Calendar.DATE);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-12-31 23:59:59.999
	 */
	public static Date endOfYear(final Date date) {
		return new Date(nextYear(date).getTime() - 1);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2017-1-1 00:00:00
	 */
	public static Date nextYear(final Date date) {
		return DateUtils.ceiling(date, Calendar.YEAR);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-11-30 23:59:59.999
	 */
	public static Date endOfMonth(final Date date) {
		return new Date(nextMonth(date).getTime() - 1);
	}

	/**
	 * 2016-11-10 07:33:23, 则返回2016-12-1 00:00:00
	 */
	public static Date nextMonth(final Date date) {
		return DateUtils.ceiling(date, Calendar.MONTH);
	}

	// next
	// -------------------------------------------------------------------------------------------------

	/**
	 * 2017-1-20 07:33:23, 则返回2017-1-22 23:59:59.999
	 */
	public static Date endOfWeek(final Date date) {
		return new Date(nextWeek(date).getTime() - 1);
	}

	/**
	 * 2017-1-23 07:33:23, 则返回2017-1-22 00:00:00
	 */
	public static Date nextWeek(final Date date) {
		return DateUtils.truncate(
			DateExtUtils.addDays(date, 8 - DateExtUtils.getDayOfWeek(date)),
			Calendar.DATE);
	}

	/**
	 * 2017-1-23 07:33:23, 则返回2017-1-23 07:59:59.999
	 */
	public static Date endOfHour(final Date date) {
		return new Date(nextHour(date).getTime() - 1);
	}

	/**
	 * 2016-12-10 07:33:23, 则返回2016-12-10 08:00:00
	 */
	public static Date nextHour(final Date date) {
		return DateUtils.ceiling(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 2017-1-23 07:33:23, 则返回2017-1-23 07:33:59.999
	 */
	public static Date endOfMinute(final Date date) {
		return new Date(nextMinute(date).getTime() - 1);
	}

	/**
	 * 2016-12-10 07:33:23, 则返回2016-12-10 07:34:00
	 */
	public static Date nextMinute(final Date date) {
		return DateUtils.ceiling(date, Calendar.MINUTE);
	}

	// isLeapYear
	// -------------------------------------------------------------------------------------------------

	/**
	 * 是否闰年.
	 */
	public static boolean isLeapYear(final Date date) {
		return isLeapYear(get(date, Calendar.YEAR));
	}

	/**
	 * 是否闰年，copy from Jodd Core的TimeUtil 参数是公元计数, 如2016
	 */
	public static boolean isLeapYear(final int y) {
		if (((y % NUM4) == 0)) {
			if ((y % NUM100) != 0) {
				return true;
			}
			return (y % NUM400) == 0;
		}
		return false;
	}

	// getMonthLength
	// -------------------------------------------------------------------------------------------------

	/**
	 * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
	 */
	public static int getMonthLength(final Date date) {
		int year = get(date, Calendar.YEAR);
		int month = get(date, Calendar.MONTH);
		return getMonthLength(year, month);
	}

	/**
	 * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
	 */
	public static int getMonthLength(final int year, final int month) {

		if ((month < 1) || (month > MONTH_DECEMBER)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month == MONTH_FEBRUARY) {
			return isLeapYear(year) ? 29 : 28;
		}

		return MONTH_LENGTH[month];
	}

	public static boolean verify(final String date, final String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);// 此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
			sdf.parse(date);// 从给定字符串的开始解析文本，以生成一个日期
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static LocalDateTime date2LocalDateTime(final Date date) {
		// A time-zone ID, such as {@link Europe/Paris}.(时区)
		Instant instant = date.toInstant();
		// A time-zone ID, such as {@link Europe/Paris}.(时区)
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	public static Date localDateTime2Date(final LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

}
