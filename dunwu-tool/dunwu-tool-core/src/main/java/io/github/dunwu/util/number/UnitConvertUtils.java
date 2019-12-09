package io.github.dunwu.util.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.将带单位的时间，大小字符串转换为数字. copy from Facebook https://github.com/facebook/jcommon/blob/master/config/src/main/java/com/facebook/config/ConfigUtil.java
 * 2.将数字转为带单位的字符串
 */
public class UnitConvertUtils {

	public static final String TIME_UNIT_DAY = "d";

	public static final String TIME_UNIT_HOUR = "h";

	public static final String TIME_UNIT_MINUTE = "m";

	public static final String TIME_UNIT_SECOND = "s";

	public static final String TIME_UNIT_MILLI_SECOND = "ms";

	public static final long SIZE_KB = 1024L;

	public static final long SIZE_MB = SIZE_KB * 1024;

	public static final long SIZE_GB = SIZE_MB * 1024;

	public static final long SIZE_TB = SIZE_GB * 1024;

	private static final long MILLIS_PER_SECOND = 1000L;

	private static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;

	private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;

	private static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

	private static final Pattern NUMBER_AND_UNIT = Pattern.compile("(\\d+)([a-zA-Z]+)?");

	private UnitConvertUtils() {}

	/**
	 * 将带单位的时间字符串转化为毫秒数。
	 * <p/>
	 * 单位包括不分大小写的ms(毫秒),s(秒),m(分钟),h(小时),d(日),y(年)，不带任何单位的话，默认单位是毫秒
	 * <p>
	 * 示例：
	 * <pre>
	 * 	10 = 10,000
	 * 	10ms = 10
	 * 	11s = 11,000
	 * 	3m = 180,000
	 * 	1h = 3,600,000
	 * 	1d = 86,400,000
	 * </pre>
	 *
	 * @param duration string to translate
	 * @return returns duration in millis
	 */
	public static long getDurationMillis(final String duration) {
		Matcher matcher = NUMBER_AND_UNIT.matcher(duration);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("malformed duration string: " + duration);
		}

		long number = Long.parseLong(matcher.group(1));
		String unitStr = matcher.group(2);
		if (unitStr == null) {
			return number;
		}

		switch (unitStr.toLowerCase()) {
			case TIME_UNIT_MILLI_SECOND:
				return number;
			case TIME_UNIT_SECOND:
				return number * MILLIS_PER_SECOND;
			case TIME_UNIT_MINUTE:
				return number * MILLIS_PER_MINUTE;
			case TIME_UNIT_HOUR:
				return number * MILLIS_PER_HOUR;
			case TIME_UNIT_DAY:
				return number * MILLIS_PER_DAY;
			default:
				throw new IllegalArgumentException("unknown time unit :" + unitStr.toLowerCase());
		}
	}

	/**
	 * 将带单位的大小字符串转化为字节数。
	 * <p>
	 * 单位包括不分大小写的b(b),k(kb),m(mb),g(gb),t(tb)，不带任何单位的话，默认单位是 b
	 */
	public static long getSizeBytes(final String size) {
		Matcher matcher = NUMBER_AND_UNIT.matcher(size);

		if (matcher.matches()) {
			long number = Long.parseLong(matcher.group(1));

			String unitStr = matcher.group(2);
			if (unitStr != null) {
				char unit = unitStr.toLowerCase().charAt(0);

				switch (unit) {
					case 'b':
						return number;
					case 'k':
						return number * SIZE_KB;
					case 'm':
						return number * SIZE_MB;
					case 'g':
						return number * SIZE_GB;
					case 't':
						return number * SIZE_TB;
					default:
						throw new IllegalArgumentException("unknown size unit :" + unit);
				}
			} else {
				return number;
			}
		} else {
			throw new IllegalArgumentException("malformed size string: " + size);
		}
	}

	/**
	 * 从bytes转换为带单位的字符串, 单位最大只支持到G级别，四舍五入
	 * <p>
	 * 示例：
	 * <pre>
	 * 966L, 0 = 966
	 * 1522L, 0 = 1k
	 * 1522L, 1 = 1.5k
	 * 1024L * 1024 * 2 + 1024 * 200, 0 = 2m
	 * 1024L * 1024 * 2 + 1024 * 600, 0 = 3m
	 * 1024L * 1024 * 2 + 1024 * 140, 1 = 2.1m
	 * 1024L * 1024 * 2 + 1024 * 160, 1 = 2.2m
	 * 1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 0 = 2g
	 * 1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 1 = 2.2g
	 * 1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 0 = 2t
	 * 1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 1 = 2.2t
	 * </pre>
	 *
	 * @param scale 小数后的精度
	 */
	public static String getSizeUnit(final Long bytes, final int scale) {
		if (bytes == null) {
			return "n/a";
		}
		if (bytes < SIZE_KB) {
			return String.format("%4d", bytes).trim();
		}

		if (bytes < SIZE_MB) {
			return String.format("%" + (scale == 0 ? 4 : 5 + scale) + '.' + scale + "fk", bytes * 1d / SIZE_KB).trim();
		}

		if (bytes < SIZE_GB) {
			return String.format("%" + (scale == 0 ? 4 : 5 + scale) + '.' + scale + "fm", bytes * 1d / SIZE_MB).trim();
		}

		if (bytes < SIZE_TB) {
			return String.format("%" + (scale == 0 ? 4 : 5 + scale) + '.' + scale + "fg", bytes * 1d / SIZE_GB).trim();
		}

		return String.format("%" + (scale == 0 ? 4 : 5 + scale) + '.' + scale + "ft", bytes * 1d / SIZE_TB).trim();
	}

	/**
	 * 转换毫秒为带时间单位的字符串，单位最大到day级别，四舍五入
	 * <p>
	 * 示例：
	 * <pre>
	 * 1322L, 0 = 1s
	 * 1322L, 1 = 1.3s
	 * 1000L * 62, 0 = 1m
	 * 1000L * 90, 0 = 2m
	 * 1000L * 90, 1 = 1.5m
	 * 1000L * 60 * 70, 1 = 1.2h
	 * 1000L * 60 * 60 * 28, 1 = 1.2d
	 * </pre>
	 *
	 * @param scale 小数后的精度
	 */
	public static String getTimeWithUnit(final long millis, final int scale) {
		if (millis < MILLIS_PER_SECOND) {
			return String.format("%4dms", millis).trim();
		}

		if (millis < MILLIS_PER_MINUTE) {
			return String.format("%" + (scale == 0 ? 2 : 3 + scale) + '.' + scale + "fs",
				millis * 1d / MILLIS_PER_SECOND).trim();
		}

		if (millis < MILLIS_PER_HOUR) {
			return String.format("%" + (scale == 0 ? 2 : 3 + scale) + '.' + scale + "fm",
				millis * 1d / MILLIS_PER_MINUTE).trim();
		}

		if (millis < MILLIS_PER_DAY) {
			return String.format("%" + (scale == 0 ? 2 : 3 + scale) + '.' + scale + "fh",
				millis * 1d / MILLIS_PER_HOUR).trim();
		}

		return String.format("%" + (scale == 0 ? 2 : 3 + scale) + '.' + scale + "fd",
			millis * 1d / MILLIS_PER_DAY).trim();
	}

}
