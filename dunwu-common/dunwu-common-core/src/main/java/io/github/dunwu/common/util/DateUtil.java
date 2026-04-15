package io.github.dunwu.common.util;

import java.time.Duration;

/**
 * 日期工具类，扩展 Hutool 的 {@link cn.hutool.core.date.DateUtil}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * 将 {@link Duration} 格式化为英文时间字符串，如 "1d 2h 3m 4s 5ms 6ns"
     *
     * @param duration {@link Duration} 时间间隔
     * @return 格式化后的英文时间字符串
     */
    public static String formatDurationTime(Duration duration) {
        Duration temp = duration;
        StringBuilder sb = new StringBuilder();

        long days = temp.toDays();
        if (days > 0) {
            sb.append(days + "d ");
        }
        temp = temp.minusDays(days);

        long hours = temp.toHours();
        if (hours > 0) {
            sb.append(hours + "h ");
        }
        temp = temp.minusHours(hours);

        long minutes = temp.toMinutes();
        if (minutes > 0) {
            sb.append(minutes + "m ");
        }
        temp = temp.minusMinutes(minutes);

        long seconds = temp.getSeconds();
        if (seconds > 0) {
            sb.append(minutes + "s ");
        }
        temp = temp.minusSeconds(seconds);

        long millis = temp.toMillis();
        if (millis > 0) {
            sb.append(millis + "ms ");
        }
        temp = temp.minusMillis(millis);

        long nanos = temp.toNanos();
        if (nanos > 0) {
            sb.append(nanos + "ns ");
        }
        return sb.toString();
    }

    /**
     * 将 {@link Duration} 格式化为中文时间字符串，如 "1天 2时 3分 4秒 5毫秒 6纳秒"
     *
     * @param duration {@link Duration} 时间间隔
     * @return 格式化后的中文时间字符串
     */
    public static String formatDurationChineseTime(Duration duration) {
        Duration temp = duration;
        StringBuilder sb = new StringBuilder();

        long days = temp.toDays();
        if (days > 0) {
            sb.append(days + "天 ");
        }
        temp = temp.minusDays(days);

        long hours = temp.toHours();
        if (hours > 0) {
            sb.append(hours + "时 ");
        }
        temp = temp.minusHours(hours);

        long minutes = temp.toMinutes();
        if (minutes > 0) {
            sb.append(minutes + "分 ");
        }
        temp = temp.minusMinutes(minutes);

        long seconds = temp.getSeconds();
        if (seconds > 0) {
            sb.append(minutes + "秒 ");
        }
        temp = temp.minusSeconds(seconds);

        long millis = temp.toMillis();
        if (millis > 0) {
            sb.append(millis + "毫秒 ");
        }
        temp = temp.minusMillis(millis);

        long nanos = temp.toNanos();
        if (nanos > 0) {
            sb.append(nanos + "纳秒 ");
        }
        return sb.toString();
    }

}
