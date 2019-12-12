package io.github.dunwu.tool.date;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.comparator.CompareUtil;
import io.github.dunwu.tool.convert.Convert;
import io.github.dunwu.tool.date.format.DateParser;
import io.github.dunwu.tool.date.format.DatePrinter;
import io.github.dunwu.tool.date.format.FastDateFormat;
import io.github.dunwu.tool.util.CharUtil;
import io.github.dunwu.tool.util.NumberUtil;
import io.github.dunwu.tool.util.RegexUtil;
import io.github.dunwu.tool.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author xiaoleilu
 */
public class DateUtil {

    /**
     * java.util.Date EEE MMM zzz 缩写数组
     */
    private final static String[] wtb = { //
        "sun", "mon", "tue", "wed", "thu", "fri", "sat", // 星期
        "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", //
        "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt"//
    };

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日，标准日期字符串
     * @return 年龄
     */
    public static int ageOfNow(String birthDay) {
        return ageOfNow(parse(birthDay));
    }

    /**
     * 将日期字符串转换为{@link DateTime}对象，格式：<br>
     * <ol>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy/MM/dd HH:mm:ss</li>
     * <li>yyyy.MM.dd HH:mm:ss</li>
     * <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy.MM.dd</li>
     * <li>HH:mm:ss</li>
     * <li>HH时mm分ss秒</li>
     * <li>yyyy-MM-dd HH:mm</li>
     * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyyMMddHHmmssSSS</li>
     * <li>yyyyMMdd</li>
     * <li>EEE, dd MMM yyyy HH:mm:ss z</li>
     * <li>EEE MMM dd HH:mm:ss zzz yyyy</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param dateCharSequence 日期字符串
     * @return 日期
     */
    public static DateTime parse(CharSequence dateCharSequence) {
        if (StringUtil.isBlank(dateCharSequence)) {
            return null;
        }
        String dateStr = dateCharSequence.toString();
        // 去掉两边空格并去掉中文日期中的“日”和“秒”，以规范长度
        dateStr = StringUtil.removeAll(dateStr.trim(), '日', '秒');
        int length = dateStr.length();

        if (NumberUtil.isNumber(dateStr)) {
            // 纯数字形式
            if (length == DatePattern.PURE_DATETIME_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATETIME_FORMAT);
            } else if (length == DatePattern.PURE_DATETIME_MS_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATETIME_MS_FORMAT);
            } else if (length == DatePattern.PURE_DATE_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATE_FORMAT);
            } else if (length == DatePattern.PURE_TIME_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_TIME_FORMAT);
            }
        } else if (RegexUtil.matches(dateStr, RegexUtil.TIME)) {
            // HH:mm:ss 或者 HH:mm 时间格式匹配单独解析
            return parseTimeToday(dateStr);
        } else if (StringUtil.containsAnyIgnoreCase(dateStr, wtb)) {
            // JDK的Date对象toString默认格式，类似于：
            // Tue Jun 4 16:25:15 +0800 2019
            // Thu May 16 17:57:18 GMT+08:00 2019
            // Wed Aug 01 00:00:00 CST 2012
            return parseCST(dateStr);
        } else if (StringUtil.contains(dateStr, 'T')) {
            // UTC时间
            return parseUTC(dateStr);
        }

        if (length == DatePattern.NORM_DATETIME_PATTERN.length()) {
            // yyyy-MM-dd HH:mm:ss
            return parseDateTime(dateStr);
        } else if (length == DatePattern.NORM_DATE_PATTERN.length()) {
            // yyyy-MM-dd
            return parseDate(dateStr);
        } else if (length == DatePattern.NORM_DATETIME_MINUTE_PATTERN.length()) {
            // yyyy-MM-dd HH:mm
            return parse(normalize(dateStr), DatePattern.NORM_DATETIME_MINUTE_FORMAT);
        } else if (length >= DatePattern.NORM_DATETIME_MS_PATTERN.length() - 2) {
            return parse(normalize(dateStr), DatePattern.NORM_DATETIME_MS_FORMAT);
        }

        // 没有更多匹配的时间格式
        throw new DateException("No format fit for date String [{}] !", dateStr);
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static DateTime parseDateTime(CharSequence dateString) {
        dateString = normalize(dateString);
        return parse(dateString, DatePattern.NORM_DATETIME_FORMAT);
    }

    /**
     * 解析格式为yyyy-MM-dd的日期，忽略时分秒
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static DateTime parseDate(CharSequence dateString) {
        dateString = normalize(dateString);
        return parse(dateString, DatePattern.NORM_DATE_FORMAT);
    }

    /**
     * 解析时间，格式HH:mm 或 HH:mm:ss，日期默认为今天
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     * @since 3.1.1
     */
    public static DateTime parseTimeToday(CharSequence timeString) {
        timeString = StringUtil.format("{} {}", today(), timeString);
        if (1 == StringUtil.count(timeString, ':')) {
            // 时间格式为 HH:mm
            return parse(timeString, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
        } else {
            // 时间格式为 HH:mm:ss
            return parse(timeString, DatePattern.NORM_DATETIME_FORMAT);
        }
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new DateTime());
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateStr 特定格式的日期
     * @param format  格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static DateTime parse(CharSequence dateStr, String format) {
        return new DateTime(dateStr, format);
    }

    /**
     * 解析UTC时间，格式：<br>
     * <ol>
     * <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param utcString UTC时间
     * @return 日期对象
     * @since 4.1.14
     */
    public static DateTime parseUTC(String utcString) {
        if (utcString == null) {
            return null;
        }
        int length = utcString.length();
        if (StringUtil.contains(utcString, 'Z')) {
            if (length == DatePattern.UTC_PATTERN.length() - 4) {
                // 格式类似：2018-09-13T05:34:31Z
                return parse(utcString, DatePattern.UTC_FORMAT);
            } else if (length == DatePattern.UTC_MS_PATTERN.length() - 4) {
                // 格式类似：2018-09-13T05:34:31.999Z
                return parse(utcString, DatePattern.UTC_MS_FORMAT);
            }
        } else {
            if (length == DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN.length() + 2
                || length == DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN.length() + 3) {
                // 格式类似：2018-09-13T05:34:31+0800 或 2018-09-13T05:34:31+08:00
                return parse(utcString, DatePattern.UTC_WITH_ZONE_OFFSET_FORMAT);
            } else if (length == DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN.length() + 2
                || length == DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN.length() + 3) {
                // 格式类似：2018-09-13T05:34:31.999+0800 或 2018-09-13T05:34:31.999+08:00
                return parse(utcString, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMAT);
            }
        }
        // 没有更多匹配的时间格式
        throw new DateException("No format fit for date String [{}] !", utcString);
    }

    /**
     * 解析CST时间，格式：<br>
     * <ol>
     * <li>EEE MMM dd HH:mm:ss z yyyy（例如：Wed Aug 01 00:00:00 CST 2012）</li>
     * </ol>
     *
     * @param cstString UTC时间
     * @return 日期对象
     * @since 4.6.9
     */
    public static DateTime parseCST(CharSequence cstString) {
        if (cstString == null) {
            return null;
        }

        return parse(cstString, DatePattern.JDK_DATETIME_FORMAT);
    }

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日
     * @return 年龄
     */
    public static int ageOfNow(Date birthDay) {
        return age(birthDay, toDateTime());
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthDay      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    public static int age(Date birthDay, Date dateToCompare) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToCompare);

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                StringUtil.format("Birthday is after date {}!", formatDate(dateToCompare)));
        }

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final boolean isLastDayOfMonth = dayOfMonth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int age = year - cal.get(Calendar.YEAR);

        final int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {

            final int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            final boolean isLastDayOfMonthBirth = dayOfMonthBirth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((false == isLastDayOfMonth || false == isLastDayOfMonthBirth) && dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

    /**
     * 格式化日期部分（不包括时间）<br> 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return null;
        }
        return DatePattern.NORM_DATE_FORMAT.format(date);
    }

    /**
     * 获取某月的开始时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime beginOfMonth(Date date) {
        return new DateTime(beginOfMonth(toCalendar(date)));
    }

    /**
     * 获取某月的开始时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar beginOfMonth(Calendar calendar) {
        return truncate(calendar, DateField.MONTH);
    }

    // -------------------------------------------------------------- Part of Date start

    /**
     * 获取某季度的开始时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime beginOfQuarter(Date date) {
        return new DateTime(beginOfQuarter(toCalendar(date)));
    }

    /**
     * 获取某季度的开始时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     * @since 4.1.0
     */
    public static Calendar beginOfQuarter(Calendar calendar) {
        calendar.set(Calendar.MONTH, calendar.get(DateField.MONTH.getValue()) / 3 * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return beginOfDay(calendar);
    }

    /**
     * 获取某天的开始时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar beginOfDay(Calendar calendar) {
        return truncate(calendar, DateField.DAY_OF_MONTH);
    }

    /**
     * 获取某周的开始时间，周一定为一周的开始时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime beginOfWeek(Date date) {
        return new DateTime(beginOfWeek(toCalendar(date)));
    }

    /**
     * 获取给定日期当前周的开始时间，周一定为一周的开始时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar beginOfWeek(Calendar calendar) {
        return beginOfWeek(calendar, true);
    }

    /**
     * 获取给定日期当前周的开始时间
     *
     * @param calendar           日期 {@link Calendar}
     * @param isMondayAsFirstDay 是否周一做为一周的第一天（false表示周日做为第一天）
     * @return {@link Calendar}
     * @since 3.1.2
     */
    public static Calendar beginOfWeek(Calendar calendar, boolean isMondayAsFirstDay) {
        if (isMondayAsFirstDay) {
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
        }
        // WEEK_OF_MONTH为上限的字段（不包括），实际调整的为DAY_OF_MONTH
        return truncate(calendar, DateField.WEEK_OF_MONTH);
    }

    /**
     * 获取某年的开始时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime beginOfYear(Date date) {
        return new DateTime(beginOfYear(toCalendar(date)));
    }

    /**
     * 获取某年的开始时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar beginOfYear(Calendar calendar) {
        return truncate(calendar, DateField.YEAR);
    }

    /**
     * 判断两个日期相差的天数<br>
     *
     * <pre>
     * 有时候我们计算相差天数的时候需要忽略时分秒。
     * 比如：2016-02-01 23:59:59和2016-02-02 00:00:00相差一秒
     * 如果isReset为<code>false</code>相差天数为0。
     * 如果isReset为<code>true</code>相差天数将被计算为1
     * </pre>
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param isReset   是否重置时间为起始时间
     * @return 日期差
     * @since 3.0.1
     */
    public static long betweenDay(Date beginDate, Date endDate, boolean isReset) {
        if (isReset) {
            beginDate = beginOfDay(beginDate);
            endDate = beginOfDay(endDate);
        }
        return between(beginDate, endDate, DateUnit.DAY);
    }

    /**
     * 获取某天的开始时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime beginOfDay(Date date) {
        return new DateTime(beginOfDay(toCalendar(date)));
    }

    /**
     * 判断两个日期相差的时长，只保留绝对值
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param unit      相差的单位：相差 天{@link DateUnit#DAY}、小时{@link DateUnit#HOUR} 等
     * @return 日期差
     */
    public static long between(Date beginDate, Date endDate, DateUnit unit) {
        return between(beginDate, endDate, unit, true);
    }

    /**
     * 判断两个日期相差的时长
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param unit      相差的单位：相差 天{@link DateUnit#DAY}、小时{@link DateUnit#HOUR} 等
     * @param isAbs     日期间隔是否只保留绝对值正数
     * @return 日期差
     * @since 3.3.1
     */
    public static long between(Date beginDate, Date endDate, DateUnit unit, boolean isAbs) {
        return new DateBetween(beginDate, endDate, isAbs).between(unit);
    }

    /**
     * 计算两个日期相差月数<br> 在非重置情况下，如果起始日期的天小于结束日期的天，月数要少算1（不足1个月）
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param isReset   是否重置时间为起始时间（重置天时分秒）
     * @return 相差月数
     * @since 3.0.8
     */
    public static long betweenMonth(Date beginDate, Date endDate, boolean isReset) {
        return new DateBetween(beginDate, endDate).betweenMonth(isReset);
    }

    /**
     * 判断两个日期相差的毫秒数
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return 日期差
     * @since 3.0.1
     */
    public static long betweenMs(Date beginDate, Date endDate) {
        return new DateBetween(beginDate, endDate).between(DateUnit.MS);
    }

    /**
     * 计算两个日期相差年数<br> 在非重置情况下，如果起始日期的月小于结束日期的月，年数要少算1（不足1年）
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param isReset   是否重置时间为起始时间（重置月天时分秒）
     * @return 相差年数
     * @since 3.0.8
     */
    public static long betweenYear(Date beginDate, Date endDate, boolean isReset) {
        return new DateBetween(beginDate, endDate).betweenYear(isReset);
    }

    /**
     * 修改日期为某个时间字段结束时间
     *
     * @param date      {@link Date}
     * @param dateField 时间字段
     * @return {@link DateTime}
     * @since 4.5.7
     */
    public static DateTime ceiling(Date date, DateField dateField) {
        return new DateTime(ceiling(toCalendar(date), dateField));
    }

    /**
     * 修改日期为某个时间字段结束时间
     *
     * @param calendar  {@link Calendar}
     * @param dateField 时间字段
     * @return 原{@link Calendar}
     * @since 4.5.7
     */
    public static Calendar ceiling(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.CEILING);
    }

    /**
     * {@code null}安全的日期比较，{@code null}对象排在末尾
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果，如果date1 &lt; date2，返回数小于0，date1==date2返回0，date1 &gt; date2 大于0
     * @since 4.6.2
     */
    public static int compare(Date date1, Date date2) {
        return CompareUtil.compare(date1, date2);
    }

    /**
     * {@code null}安全的{@link Calendar}比较，{@code null}小于任何日期
     *
     * @param calendar1 日期1
     * @param calendar2 日期2
     * @return 比较结果，如果calendar1 &lt; calendar2，返回数小于0，calendar1==calendar2返回0，calendar1 &gt; calendar2 大于0
     * @since 4.6.2
     */
    public static int compare(Calendar calendar1, Calendar calendar2) {
        return CompareUtil.compare(calendar1, calendar2);
    }

    /**
     * 当前时间的时间戳
     *
     * @param isNano 是否为高精度时间
     * @return 时间
     */
    public static long current(boolean isNano) {
        return isNano ? System.nanoTime() : System.currentTimeMillis();
    }

    /**
     * 当前时间的时间戳（秒）
     *
     * @return 当前时间秒数
     * @since 4.0.0
     */
    public static long currentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    // -------------------------------------------------------------- toDateTime

    /**
     * 当前时间，转换为{@link DateTime}对象
     *
     * @return 当前时间
     */
    public static DateTime toDateTime() {
        return new DateTime();
    }

    /**
     * Long类型时间转为{@link DateTime}<br> 只支持毫秒级别时间戳，如果需要秒级别时间戳，请自行×1000
     *
     * @param millis Long类型Date（Unix时间戳）
     * @return 时间对象
     */
    public static DateTime toDateTime(long millis) {
        return new DateTime(millis);
    }

    /**
     * {@link Calendar}类型时间转为{@link DateTime}<br> 始终根据已有{@link Calendar} 产生新的{@link DateTime}对象
     *
     * @param calendar {@link Calendar}
     * @return 时间对象
     */
    public static DateTime toDateTime(Calendar calendar) {
        return new DateTime(calendar);
    }

    /**
     * {@link TemporalAccessor}类型时间转为{@link DateTime}<br> 始终根据已有{@link TemporalAccessor} 产生新的{@link DateTime}对象
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return 时间对象
     * @since 5.0.0
     */
    public static DateTime toDateTime(TemporalAccessor temporalAccessor) {
        return new DateTime(temporalAccessor);
    }

    /**
     * 当前时间，转换为{@link DateTime}对象，忽略毫秒部分
     *
     * @return 当前时间
     * @since 4.6.2
     */
    public static DateTime dateSecond() {
        return beginOfSecond(toDateTime());
    }

    /**
     * 获取秒级别的开始时间，即忽略毫秒部分
     *
     * @param date 日期
     * @return {@link DateTime}
     * @since 4.6.2
     */
    public static DateTime beginOfSecond(Date date) {
        return new DateTime(beginOfSecond(toCalendar(date)));
    }

    // -------------------------------------------------------------- toCalendar

    /**
     * 创建Calendar对象，时间为默认时区的当前时间
     *
     * @return Calendar对象
     * @since 4.6.6
     */
    public static Calendar toCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 转换为Calendar对象
     *
     * @param date 日期对象
     * @return Calendar对象
     */
    public static Calendar toCalendar(Date date) {
        if (date instanceof DateTime) {
            return ((DateTime) date).toCalendar();
        } else {
            return toCalendar(date.getTime());
        }
    }

    /**
     * 转换为Calendar对象
     *
     * @param millis 时间戳
     * @return Calendar对象
     */
    public static Calendar toCalendar(long millis) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal;
    }

    /**
     * 获取秒级别的开始时间，即忽略毫秒部分
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     * @since 4.6.2
     */
    public static Calendar beginOfSecond(Calendar calendar) {
        return truncate(calendar, DateField.SECOND);
    }

    /**
     * 修改日期为某个时间字段起始时间
     *
     * @param calendar  {@link Calendar}
     * @param dateField 时间字段
     * @return 原{@link Calendar}
     * @since 4.5.7
     */
    public static Calendar truncate(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.TRUNCATE);
    }

    // ------------------------------------ Format start ----------------------------------------------

    /**
     * 获取某天的结束时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime endOfDay(Date date) {
        return new DateTime(endOfDay(toCalendar(date)));
    }

    /**
     * 获取某天的结束时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar endOfDay(Calendar calendar) {
        return ceiling(calendar, DateField.DAY_OF_MONTH);
    }

    /**
     * 获取某月的结束时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime endOfMonth(Date date) {
        return new DateTime(endOfMonth(toCalendar(date)));
    }

    /**
     * 获取某月的结束时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar endOfMonth(Calendar calendar) {
        return ceiling(calendar, DateField.MONTH);
    }

    /**
     * 获取某季度的结束时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime endOfQuarter(Date date) {
        return new DateTime(endOfQuarter(toCalendar(date)));
    }

    /**
     * 获取某季度的结束时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     * @since 4.1.0
     */
    public static Calendar endOfQuarter(Calendar calendar) {
        calendar.set(Calendar.MONTH, calendar.get(DateField.MONTH.getValue()) / 3 * 3 + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endOfDay(calendar);
    }

    /**
     * 获取秒级别的结束时间，即毫秒设置为999
     *
     * @param date 日期
     * @return {@link DateTime}
     * @since 4.6.2
     */
    public static DateTime endOfSecond(Date date) {
        return new DateTime(endOfSecond(toCalendar(date)));
    }

    /**
     * 获取秒级别的结束时间，即毫秒设置为999
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     * @since 4.6.2
     */
    public static Calendar endOfSecond(Calendar calendar) {
        return ceiling(calendar, DateField.SECOND);
    }

    /**
     * 获取某周的结束时间，周日定为一周的结束
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime endOfWeek(Date date) {
        return new DateTime(endOfWeek(toCalendar(date)));
    }
    // ------------------------------------ Format end ----------------------------------------------

    // ------------------------------------ Parse start ----------------------------------------------

    /**
     * 获取某周的结束时间，周日定为一周的结束
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar endOfWeek(Calendar calendar) {
        return endOfWeek(calendar, true);
    }

    /**
     * 获取某周的结束时间
     *
     * @param calendar          日期 {@link Calendar}
     * @param isSundayAsLastDay 是否周日做为一周的最后一天（false表示周六做为最后一天）
     * @return {@link Calendar}
     * @since 3.1.2
     */
    public static Calendar endOfWeek(Calendar calendar, boolean isSundayAsLastDay) {
        if (isSundayAsLastDay) {
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
        }
        // WEEK_OF_MONTH为上限的字段（不包括），实际调整的为DAY_OF_MONTH
        return ceiling(calendar, DateField.WEEK_OF_MONTH);
    }

    /**
     * 获取某年的结束时间
     *
     * @param date 日期
     * @return {@link DateTime}
     */
    public static DateTime endOfYear(Date date) {
        return new DateTime(endOfYear(toCalendar(date)));
    }

    /**
     * 获取某年的结束时间
     *
     * @param calendar 日期 {@link Calendar}
     * @return {@link Calendar}
     */
    public static Calendar endOfYear(Calendar calendar) {
        return ceiling(calendar, DateField.YEAR);
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format {@link DatePrinter} 或 {@link FastDateFormat}
     * @return 格式化后的字符串
     */
    public static String format(Date date, DatePrinter format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date);
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format {@link SimpleDateFormat}
     * @return 格式化后的字符串
     * @since 5.0.0
     */
    public static String format(Date date, DateTimeFormatter format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date.toInstant());
    }

    /**
     * 格式化日期间隔输出
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @param level     级别，按照天、小时、分、秒、毫秒分为5个等级
     * @return XX天XX小时XX分XX秒
     */
    public static String formatBetween(Date beginDate, Date endDate, BetweenFormater.Level level) {
        return formatBetween(between(beginDate, endDate, DateUnit.MS), level);
    }

    /**
     * 格式化日期间隔输出
     *
     * @param betweenMs 日期间隔
     * @param level     级别，按照天、小时、分、秒、毫秒分为5个等级
     * @return XX天XX小时XX分XX秒XX毫秒
     */
    public static String formatBetween(long betweenMs, BetweenFormater.Level level) {
        return new BetweenFormater(betweenMs, level).format();
    }

    /**
     * 格式化日期间隔输出，精确到毫秒
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return XX天XX小时XX分XX秒
     * @since 3.0.1
     */
    public static String formatBetween(Date beginDate, Date endDate) {
        return formatBetween(between(beginDate, endDate, DateUnit.MS));
    }

    /**
     * 格式化日期间隔输出，精确到毫秒
     *
     * @param betweenMs 日期间隔
     * @return XX天XX小时XX分XX秒XX毫秒
     * @since 3.0.1
     */
    public static String formatBetween(long betweenMs) {
        return new BetweenFormater(betweenMs, BetweenFormater.Level.MILLSECOND).format();
    }

    /**
     * 格式化为中文日期格式，如果isUppercase为false，则返回类似：2018年10月24日，否则返回二〇一八年十月二十四日
     *
     * @param date        被格式化的日期
     * @param isUppercase 是否采用大写形式
     * @return 中文日期字符串
     * @since 4.1.19
     */
    public static String formatChineseDate(Date date, boolean isUppercase) {
        if (null == date) {
            return null;
        }

        String format = DatePattern.CHINESE_DATE_FORMAT.format(date);
        if (isUppercase) {
            final StringBuilder builder = StringUtil.getStringBuilder(format.length());
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(0, 1)), false));
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(1, 2)), false));
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(2, 3)), false));
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(3, 4)), false));
            builder.append(format, 4, 5);
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(5, 7)), false));
            builder.append(format, 7, 8);
            builder.append(Convert.numberToChinese(Integer.parseInt(format.substring(8, 10)), false));
            builder.append(format.substring(10));
            format = builder.toString().replace('零', '〇');
        }
        return format;
    }

    /**
     * 格式化为Http的标准日期格式<br> 标准日期格式遵循RFC 1123规范，格式类似于：Fri, 31 Dec 1999 23:59:59 GMT
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
        if (null == date) {
            return null;
        }
        return DatePattern.HTTP_DATETIME_FORMAT.format(date);
    }

    // ------------------------------------ Parse end ----------------------------------------------

    // ------------------------------------ Offset start ----------------------------------------------

    /**
     * 格式化时间<br> 格式 HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     * @since 3.0.1
     */
    public static String formatTime(Date date) {
        if (null == date) {
            return null;
        }
        return DatePattern.NORM_TIME_FORMAT.format(date);
    }

    /**
     * 获取指定日期字段的最小值，例如分钟的最小值是0
     *
     * @param calendar  {@link Calendar}
     * @param dateField {@link DateField}
     * @return 字段最小值
     * @see Calendar#getActualMinimum(int)
     * @since 4.5.7
     */
    public static int getBeginValue(Calendar calendar, int dateField) {
        if (Calendar.DAY_OF_WEEK == dateField) {
            return calendar.getFirstDayOfWeek();
        }
        return calendar.getActualMinimum(dateField);
    }

    /**
     * 计算生肖，只计算1900年后出生的人
     *
     * @param year 农历年
     * @return 生肖名
     * @since 4.4.3
     */
    public static String getChineseZodiac(int year) {
        return Zodiac.getChineseZodiac(year);
    }

    /**
     * 获取指定日期字段的最大值，例如分钟的最大值是59
     *
     * @param calendar  {@link Calendar}
     * @param dateField {@link DateField}
     * @return 字段最大值
     * @see Calendar#getActualMaximum(int)
     * @since 4.5.7
     */
    public static int getEndValue(Calendar calendar, int dateField) {
        if (Calendar.DAY_OF_WEEK == dateField) {
            return (calendar.getFirstDayOfWeek() + 6) % 7;
        }
        return calendar.getActualMaximum(dateField);
    }

    /**
     * 通过生日计算星座
     *
     * @param month 月，从0开始计数
     * @param day   天
     * @return 星座名
     * @since 4.4.3
     */
    public static String getZodiac(int month, int day) {
        return Zodiac.getZodiac(month, day);
    }

    /**
     * 是否为上午
     *
     * @param date 日期
     * @return 是否为上午
     */
    public static boolean isAM(Date date) {
        return DateTime.of(date).isAM();
    }

    /**
     * 是否为上午
     *
     * @param calendar {@link Calendar}
     * @return 是否为上午
     * @since 4.5.7
     */
    public static boolean isAM(Calendar calendar) {
        return Calendar.AM == calendar.get(Calendar.AM_PM);
    }

    /**
     * 判定给定开始时间经过某段时间后是否过期
     *
     * @param startDate   开始时间
     * @param dateField   时间单位
     * @param timeLength  时长
     * @param checkedDate 被比较的时间。如果经过时长后的时间晚于被检查的时间，就表示过期
     * @return 是否过期
     * @since 3.1.1
     */
    public static boolean isExpired(Date startDate, DateField dateField, int timeLength, Date checkedDate) {
        final Date endDate = offset(startDate, dateField, timeLength);
        return endDate.after(checkedDate);
    }

    /**
     * 当前日期是否在日期指定范围内<br> 起始日期和结束日期可以互换
     *
     * @param date      被检查的日期
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return 是否在范围内
     * @since 3.0.8
     */
    public static boolean isIn(Date date, Date beginDate, Date endDate) {
        if (date instanceof DateTime) {
            return ((DateTime) date).isIn(beginDate, endDate);
        } else {
            return new DateTime(date).isIn(beginDate, endDate);
        }
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return 是否闰年
     */
    public static boolean isLeapYear(final int year) {
        return new GregorianCalendar().isLeapYear(year);
    }

    /**
     * 是否闰年
     *
     * @param date {@link Date}
     * @return 是否闰年
     */
    public static boolean isLeapYear(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 是否为下午
     *
     * @param date 日期
     * @return 是否为下午
     */
    public static boolean isPM(Date date) {
        return DateTime.of(date).isPM();
    }

    /**
     * 比较两个日期是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一天
     * @since 4.1.13
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return isSameDay(toCalendar(date1), toCalendar(date2));
    }

    /**
     * 比较两个日期是否为同一天
     *
     * @param cal1 日期1
     * @param cal2 日期2
     * @return 是否为同一天
     * @since 4.1.13
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && //
            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && //
            cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA);
    }

    /**
     * 是否为相同时间
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为相同时间
     * @since 4.1.13
     */
    public static boolean isSameTime(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 上个月
     *
     * @return 上个月
     */
    public static DateTime lastMonth() {
        return offsetMonth(new DateTime(), -1);
    }

    /**
     * 偏移月
     *
     * @param date   日期
     * @param offset 偏移月数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetMonth(Date date, int offset) {
        return offset(date, DateField.MONTH, offset);
    }

    /**
     * 上周
     *
     * @return 上周
     */
    public static DateTime lastWeek() {
        return offsetWeek(new DateTime(), -1);
    }

    /**
     * 偏移周
     *
     * @param date   日期
     * @param offset 偏移周数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetWeek(Date date, int offset) {
        return offset(date, DateField.WEEK_OF_YEAR, offset);
    }

    /**
     * 纳秒转毫秒
     *
     * @param duration 时长
     * @return 时长毫秒
     * @since 4.6.6
     */
    public static long nanosToMillis(long duration) {
        return TimeUnit.NANOSECONDS.toMillis(duration);
    }

    /**
     * 纳秒转秒，保留小数
     *
     * @param duration 时长
     * @return 秒
     * @since 4.6.6
     */
    public static double nanosToSeconds(long duration) {
        return duration / 1_000_000_000.0;
    }

    /**
     * 下个月
     *
     * @return 下个月
     * @since 3.0.1
     */
    public static DateTime nextMonth() {
        return offsetMonth(new DateTime(), 1);
    }

    /**
     * 下周
     *
     * @return 下周
     * @since 3.0.1
     */
    public static DateTime nextWeek() {
        return offsetWeek(new DateTime(), 1);
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return formatDateTime(new DateTime());
    }

    /**
     * 格式化日期时间<br> 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return DatePattern.NORM_DATETIME_FORMAT.format(date);
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date      基准日期
     * @param dateField 偏移的粒度大小（小时、天、月等）{@link DateField}
     * @param offset    偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     * @deprecated please use {@link DateUtil#offset(Date, DateField, int)}
     */
    @Deprecated
    public static DateTime offsetDate(Date date, DateField dateField, int offset) {
        return offset(date, dateField, offset);
    }

    /**
     * 偏移小时
     *
     * @param date   日期
     * @param offset 偏移小时数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetHour(Date date, int offset) {
        return offset(date, DateField.HOUR_OF_DAY, offset);
    }

    /**
     * 偏移毫秒数
     *
     * @param date   日期
     * @param offset 偏移毫秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetMillisecond(Date date, int offset) {
        return offset(date, DateField.MILLISECOND, offset);
    }

    /**
     * 偏移分钟
     *
     * @param date   日期
     * @param offset 偏移分钟数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetMinute(Date date, int offset) {
        return offset(date, DateField.MINUTE, offset);
    }

    /**
     * 偏移秒数
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetSecond(Date date, int offset) {
        return offset(date, DateField.SECOND, offset);
    }

    /**
     * 构建DateTime对象
     *
     * @param dateStr    Date字符串
     * @param dateFormat 格式化器 {@link SimpleDateFormat}
     * @return DateTime对象
     */
    public static DateTime parse(CharSequence dateStr, DateFormat dateFormat) {
        return new DateTime(dateStr, dateFormat);
    }

    /**
     * 构建DateTime对象
     *
     * @param dateStr   Date字符串
     * @param formatter 格式化器,{@link DateTimeFormatter}
     * @return DateTime对象
     * @since 5.0.0
     */
    public static DateTime parse(CharSequence dateStr, DateTimeFormatter formatter) {
        return new DateTime(dateStr, formatter);
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateStr 特定格式的日期
     * @param format  格式，例如yyyy-MM-dd
     * @param locale  区域信息
     * @return 日期对象
     * @since 4.5.18
     */
    public static DateTime parse(CharSequence dateStr, String format, Locale locale) {
        return new DateTime(dateStr, new SimpleDateFormat(format, locale));
    }

    // --------------------------------------------------- Offset for now

    /**
     * 解析时间，格式HH:mm:ss，日期部分默认为1970-01-01
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     */
    public static DateTime parseTime(CharSequence timeString) {
        timeString = normalize(timeString);
        return parse(timeString, DatePattern.NORM_TIME_FORMAT);
    }

    /**
     * 构建DateTime对象
     *
     * @param dateStr Date字符串
     * @param parser  格式化器,{@link FastDateFormat}
     * @return DateTime对象
     */
    public static DateTime parse(CharSequence dateStr, DateParser parser) {
        return new DateTime(dateStr, parser);
    }

    /**
     * 标准化日期，默认处理以空格区分的日期时间格式，空格前为日期，空格后为时间：<br> 将以下字符替换为"-"
     *
     * <pre>
     * "."
     * "/"
     * "年"
     * "月"
     * </pre>
     * <p>
     * 将以下字符去除
     *
     * <pre>
     * "日"
     * </pre>
     * <p>
     * 将以下字符替换为":"
     *
     * <pre>
     * "时"
     * "分"
     * "秒"
     * </pre>
     * <p>
     * 当末位是":"时去除之（不存在毫秒时）
     *
     * @param dateStr 日期时间字符串
     * @return 格式化后的日期字符串
     */
    private static String normalize(CharSequence dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return StringUtil.str(dateStr);
        }

        // 日期时间分开处理
        final List<String> dateAndTime = StringUtil.splitTrim(dateStr, ' ');
        final int size = dateAndTime.size();
        if (size < 1 || size > 2) {
            // 非可被标准处理的格式
            return StringUtil.str(dateStr);
        }

        final StringBuilder builder = StringUtil.getStringBuilder();

        // 日期部分（"\"、"/"、"."、"年"、"月"都替换为"-"）
        String datePart = dateAndTime.get(0).replaceAll("[/.年月]", "-");
        datePart = StringUtil.removeSuffix(datePart, "日");
        builder.append(datePart);

        // 时间部分
        if (size == 2) {
            builder.append(' ');
            String timePart = dateAndTime.get(1).replaceAll("[时分秒]", ":");
            timePart = StringUtil.removeSuffix(timePart, ":");
            builder.append(timePart);
        }

        return builder.toString();
    }

    /**
     * 获得指定日期所属季度，从1开始计数
     *
     * @param date 日期
     * @return 第几个季度
     * @since 4.1.0
     */
    public static int quarter(Date date) {
        return DateTime.of(date).quarter();
    }

    /**
     * 获得指定日期所属季度
     *
     * @param date 日期
     * @return 第几个季度枚举
     * @since 4.1.0
     */
    public static Quarter quarterEnum(Date date) {
        return DateTime.of(date).quarterEnum();
    }

    /**
     * 创建日期范围生成器
     *
     * @param start 起始日期时间
     * @param end   结束日期时间
     * @param unit  步进单位
     * @return {@link DateRange}
     */
    public static List<DateTime> rangeToList(Date start, Date end, final DateField unit) {
        return CollectionUtil.newArrayList((Iterable<DateTime>) range(start, end, unit));
    }

    /**
     * 创建日期范围生成器
     *
     * @param start 起始日期时间
     * @param end   结束日期时间
     * @param unit  步进单位
     * @return {@link DateRange}
     */
    public static DateRange range(Date start, Date end, final DateField unit) {
        return new DateRange(start, end, unit);
    }

    /**
     * 修改日期为某个时间字段四舍五入时间
     *
     * @param date      {@link Date}
     * @param dateField 时间字段
     * @return {@link DateTime}
     * @since 4.5.7
     */
    public static DateTime round(Date date, DateField dateField) {
        return new DateTime(round(toCalendar(date), dateField));
    }

    /**
     * 修改日期为某个时间字段四舍五入时间
     *
     * @param calendar  {@link Calendar}
     * @param dateField 时间字段
     * @return 原{@link Calendar}
     * @since 4.5.7
     */
    public static Calendar round(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.ROUND);
    }

    /**
     * 秒数转为时间格式(HH:mm:ss)<br> 参考：https://github.com/iceroot
     *
     * @param seconds 需要转换的秒数
     * @return 转换后的字符串
     * @since 3.1.2
     */
    public static String secondToTime(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds must be a positive number!");
        }

        int hour = seconds / 3600;
        int other = seconds % 3600;
        int minute = other / 60;
        int second = other % 60;
        final StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        sb.append(":");
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        return sb.toString();
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：纳秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，纳秒
     */
    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    /**
     * @return 当前日期是这个日期所在月份的第几天
     */
    public static int thisDayOfMonth() {
        return dayOfMonth(toDateTime());
    }

    /**
     * 获得指定日期是这个日期所在月份的第几天<br>
     *
     * @param date 日期
     * @return 天
     */
    public static int dayOfMonth(Date date) {
        return DateTime.of(date).dayOfMonth();
    }

    /**
     * @return 当前日期是星期几
     */
    public static int thisDayOfWeek() {
        return dayOfWeek(toDateTime());
    }
    // ------------------------------------ Offset end ----------------------------------------------

    /**
     * 获得指定日期是星期几，1表示周日，2表示周一
     *
     * @param date 日期
     * @return 天
     */
    public static int dayOfWeek(Date date) {
        return DateTime.of(date).dayOfWeek();
    }

    /**
     * @return 当前日期是星期几 {@link Week}
     */
    public static Week thisDayOfWeekEnum() {
        return dayOfWeekEnum(toDateTime());
    }

    /**
     * 获得指定日期是星期几
     *
     * @param date 日期
     * @return {@link Week}
     */
    public static Week dayOfWeekEnum(Date date) {
        return DateTime.of(date).dayOfWeekEnum();
    }

    /**
     * @param is24HourClock 是否24小时制
     * @return 当前日期的小时数部分<br>
     */
    public static int thisHour(boolean is24HourClock) {
        return hour(toDateTime(), is24HourClock);
    }

    /**
     * 获得指定日期的小时数部分<br>
     *
     * @param date          日期
     * @param is24HourClock 是否24小时制
     * @return 小时数
     */
    public static int hour(Date date, boolean is24HourClock) {
        return DateTime.of(date).hour(is24HourClock);
    }

    /**
     * @return 当前日期的毫秒数部分<br>
     */
    public static int thisMillsecond() {
        return millsecond(toDateTime());
    }

    /**
     * 获得指定日期的毫秒数部分<br>
     *
     * @param date 日期
     * @return 毫秒数
     */
    public static int millsecond(Date date) {
        return DateTime.of(date).millsecond();
    }

    /**
     * @return 当前日期的分钟数部分<br>
     */
    public static int thisMinute() {
        return minute(toDateTime());
    }

    /**
     * 获得指定日期的分钟数部分<br> 例如：10:04:15.250 =》 4
     *
     * @param date 日期
     * @return 分钟数
     */
    public static int minute(Date date) {
        return DateTime.of(date).minute();
    }

    /**
     * @return 当前月份
     */
    public static int thisMonth() {
        return month(toDateTime());
    }

    /**
     * 获得月份，从0开始计数
     *
     * @param date 日期
     * @return 月份，从0开始计数
     */
    public static int month(Date date) {
        return DateTime.of(date).month();
    }

    /**
     * @return 当前月份 {@link Month}
     */
    public static Month thisMonthEnum() {
        return monthEnum(toDateTime());
    }

    /**
     * 获得月份
     *
     * @param date 日期
     * @return {@link Month}
     */
    public static Month monthEnum(Date date) {
        return DateTime.of(date).monthEnum();
    }

    /**
     * @return 当前日期的秒数部分<br>
     */
    public static int thisSecond() {
        return second(toDateTime());
    }

    /**
     * 获得指定日期的秒数部分<br>
     *
     * @param date 日期
     * @return 秒数
     */
    public static int second(Date date) {
        return DateTime.of(date).second();
    }

    /**
     * @return 当前日期所在月份的第几周
     */
    public static int thisWeekOfMonth() {
        return weekOfMonth(toDateTime());
    }

    /**
     * 获得指定日期是所在月份的第几周<br>
     *
     * @param date 日期
     * @return 周
     */
    public static int weekOfMonth(Date date) {
        return DateTime.of(date).weekOfMonth();
    }

    /**
     * @return 当前日期所在年份的第几周
     */
    public static int thisWeekOfYear() {
        return weekOfYear(toDateTime());
    }

    /**
     * 获得指定日期是所在年份的第几周<br>
     *
     * @param date 日期
     * @return 周
     */
    public static int weekOfYear(Date date) {
        return DateTime.of(date).weekOfYear();
    }

    /**
     * @return 今年
     */
    public static int thisYear() {
        return year(toDateTime());
    }

    /**
     * 获得年的部分
     *
     * @param date 日期
     * @return 年的部分
     */
    public static int year(Date date) {
        return DateTime.of(date).year();
    }

    /**
     * HH:mm:ss 时间格式字符串转为秒数<br> 参考：https://github.com/iceroot
     *
     * @param timeStr 字符串时分秒(HH:mm:ss)格式
     * @return 时分秒转换后的秒数
     * @since 3.1.2
     */
    public static int timeToSecond(String timeStr) {
        if (StringUtil.isEmpty(timeStr)) {
            return 0;
        }

        final List<String> hms = StringUtil.splitTrim(timeStr, CharUtil.COLON, 3);
        int lastIndex = hms.size() - 1;

        int result = 0;
        for (int i = lastIndex; i >= 0; i--) {
            result += Integer.parseInt(hms.get(i)) * Math.pow(60, (lastIndex - i));
        }
        return result;
    }

    /**
     * 计时器<br> 计算某个过程花费的时间，精确到毫秒
     *
     * @return Timer
     */
    public static TimeInterval timer() {
        return new TimeInterval();
    }

    /**
     * Date对象转换为{@link Instant}对象
     *
     * @param date Date对象
     * @return {@link Instant}对象
     * @since 5.0.2
     */
    public static Instant toInstant(Date date) {
        return null == date ? null : date.toInstant();
    }

    /**
     * Calendar{@link Instant}对象
     *
     * @param calendar Date对象
     * @return {@link Instant}对象
     * @since 5.0.5
     */
    public static Instant toInstant(Calendar calendar) {
        return null == calendar ? null : calendar.toInstant();
    }

    /**
     * Date对象转换为{@link Instant}对象
     *
     * @param temporalAccessor Date对象
     * @return {@link Instant}对象
     * @since 5.0.2
     */
    public static Instant toInstant(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        Instant result;
        if (temporalAccessor instanceof Instant) {
            result = (Instant) temporalAccessor;
        } else if (temporalAccessor instanceof LocalDateTime) {
            result = ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof ZonedDateTime) {
            result = ((ZonedDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof OffsetDateTime) {
            result = ((OffsetDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof LocalDate) {
            result = ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof LocalTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((LocalTime) temporalAccessor).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof OffsetTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((OffsetTime) temporalAccessor).atDate(LocalDate.now()).toInstant();
        } else {
            result = Instant.from(temporalAccessor);
        }

        return result;
    }

    /**
     * 格式化成yyMMddHHmm后转换为int型
     *
     * @param date 日期
     * @return int
     */
    public static int toIntSecond(Date date) {
        return Integer.parseInt(DateUtil.format(date, "yyMMddHHmm"));
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 日期格式，常用格式见： {@link DatePattern}
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        if (null == date || StringUtil.isBlank(format)) {
            return null;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date instanceof DateTime) {
            final TimeZone timeZone = ((DateTime) date).getTimeZone();
            if (null != timeZone) {
                sdf.setTimeZone(timeZone);
            }
        }
        return format(date, sdf);
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format {@link SimpleDateFormat}
     * @return 格式化后的字符串
     */
    public static String format(Date date, DateFormat format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date);
    }

    /**
     * 将 {@link Calendar} 转化为 {@link Date}
     *
     * @param calendar {@link Calendar}
     * @return {@link Date}
     */
    public static Date toDate(final Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 将 {@link LocalDateTime} 转化为 {@link Date}
     *
     * @param localDateTime {@link LocalDateTime}
     * @return {@link Date}
     */
    public static Date toDate(final LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * {@link Instant} 转换为 {@link LocalDateTime}，使用系统默认时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     * @since 5.0.5
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * {@link Calendar} 转换为 {@link LocalDateTime}，使用系统默认时区
     *
     * @param calendar {@link Calendar}
     * @return {@link LocalDateTime}
     * @since 5.0.5
     */
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
    }

    /**
     * {@link Date} 转换为 {@link LocalDateTime}，使用系统默认时区
     *
     * @param date {@link Calendar}
     * @return {@link LocalDateTime}
     * @since 5.0.5
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        final DateTime dateTime = toDateTime(date);
        return LocalDateTime.ofInstant(dateTime.toInstant(), dateTime.getZoneId());
    }

    /**
     * {@link Date}类型时间转为{@link DateTime}<br> 如果date本身为DateTime对象，则返回强转后的对象，否则新建一个DateTime对象
     *
     * @param date Long类型Date（Unix时间戳）
     * @return 时间对象
     * @since 3.0.7
     */
    public static DateTime toDateTime(Date date) {
        if (date instanceof DateTime) {
            return (DateTime) date;
        }
        return dateNew(date);
    }

    /**
     * 明天
     *
     * @return 明天
     * @since 3.0.1
     */
    public static DateTime tomorrow() {
        return offsetDay(new DateTime(), 1);
    }

    /**
     * 修改日期为某个时间字段起始时间
     *
     * @param date      {@link Date}
     * @param dateField 时间字段
     * @return {@link DateTime}
     * @since 4.5.7
     */
    public static DateTime truncate(Date date, DateField dateField) {
        return new DateTime(truncate(toCalendar(date), dateField));
    }

    /**
     * 计算指定指定时间区间内的周数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 周数
     */
    public static int weekCount(Date start, Date end) {
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        final int startWeekofYear = startCalendar.get(Calendar.WEEK_OF_YEAR);
        final int endWeekofYear = endCalendar.get(Calendar.WEEK_OF_YEAR);

        int count = endWeekofYear - startWeekofYear + 1;

        if (Calendar.SUNDAY != startCalendar.get(Calendar.DAY_OF_WEEK)) {
            count--;
        }

        return count;
    }

    /**
     * 获得指定日期年份和季节<br> 格式：[20131]表示2013年第一季度
     *
     * @param date 日期
     * @return Quarter ，类似于 20132
     */
    public static String yearAndQuarter(Date date) {
        return yearAndQuarter(toCalendar(date));
    }

    /**
     * 获得指定日期年份和季节<br> 格式：[20131]表示2013年第一季度
     *
     * @param cal 日期
     */
    private static String yearAndQuarter(Calendar cal) {
        return StringUtil.getStringBuilder()
            .append(cal.get(Calendar.YEAR))
            .append(cal.get(Calendar.MONTH) / 3 + 1)
            .toString();
    }

    /**
     * 获得指定日期区间内的年份和季节<br>
     *
     * @param startDate 起始日期（包含）
     * @param endDate   结束日期（包含）
     * @return 季度列表 ，元素类似于 20132
     */
    public static LinkedHashSet<String> yearAndQuarter(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return new LinkedHashSet<>(0);
        }
        return yearAndQuarter(startDate.getTime(), endDate.getTime());
    }

    /**
     * 获得指定日期区间内的年份和季节<br>
     *
     * @param startDate 起始日期（包含）
     * @param endDate   结束日期（包含）
     * @return 季度列表 ，元素类似于 20132
     * @since 4.1.15
     */
    public static LinkedHashSet<String> yearAndQuarter(long startDate, long endDate) {
        LinkedHashSet<String> quarters = new LinkedHashSet<>();
        final Calendar cal = toCalendar(startDate);
        while (startDate <= endDate) {
            // 如果开始时间超出结束时间，让结束时间为开始时间，处理完后结束循环
            quarters.add(yearAndQuarter(cal));

            cal.add(Calendar.MONTH, 3);
            startDate = cal.getTimeInMillis();
        }

        return quarters;
    }

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static DateTime yesterday() {
        return offsetDay(new DateTime(), -1);
    }

    /**
     * 偏移天
     *
     * @param date   日期
     * @param offset 偏移天数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static DateTime offsetDay(Date date, int offset) {
        return offset(date, DateField.DAY_OF_YEAR, offset);
    }

    // ------------------------------------------------------------------------ Private method start

    /**
     * 获取指定日期偏移指定时间后的时间，生成的偏移日期不影响原日期
     *
     * @param date      基准日期
     * @param dateField 偏移的粒度大小（小时、天、月等）{@link DateField}
     * @param offset    偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static DateTime offset(Date date, DateField dateField, int offset) {
        return dateNew(date).offset(dateField, offset);
    }

    /**
     * 根据已有{@link Date} 产生新的{@link DateTime}对象
     *
     * @param date Date对象
     * @return {@link DateTime}对象
     * @since 4.3.1
     */
    public static DateTime dateNew(Date date) {
        return new DateTime(date);
    }
    // ------------------------------------------------------------------------ Private method end
}
