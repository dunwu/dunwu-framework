package io.github.dunwu.common.dlock;

import jakarta.validation.constraints.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * 分布式锁工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public final class Utils {

    /**
     * A {@link DateTimeFormatter} like {@link DateTimeFormatter#ISO_INSTANT} with the exception that it always appends
     * exactly three fractional digits (nano seconds).
     * <p>
     * This is required in order to guarantee natural sorting, which enables us to use
     * <code>&lt;=</code> comparision in queries.
     *
     * <pre>
     * 2018-12-07T12:30:37.000Z
     * 2018-12-07T12:30:37.810Z
     * 2018-12-07T12:30:37.819Z
     * 2018-12-07T12:30:37.820Z
     * </pre>
     * <p>
     * When using variable fractional digit count as done in {@link DateTimeFormatter#ISO_INSTANT ISO_INSTANT} and
     * {@link DateTimeFormatter#ISO_OFFSET_DATE_TIME ISO_OFFSET_DATE_TIME} the following sorting occurs:
     *
     * <pre>
     * 2018-12-07T12:30:37.819Z
     * 2018-12-07T12:30:37.81Z
     * 2018-12-07T12:30:37.820Z
     * 2018-12-07T12:30:37Z
     * </pre>
     *
     * @see <a href="https://stackoverflow.com/a/5098252">natural sorting of ISO 8601 time format</a>
     */
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendInstant(3)
        .toFormatter();

    private static final String hostname = initHostname();

    private Utils() {
    }

    /**
     * 获取当前主机名
     *
     * @return 主机名
     */
    @NotNull
    public static String getHostname() {
        return hostname;
    }

    /**
     * 将 {@link Instant} 转换为 ISO 格式字符串，固定 3 位毫秒精度
     *
     * @param instant 时间戳
     * @return ISO 格式时间字符串
     */
    public static String toIsoString(@NotNull Instant instant) {
        OffsetDateTime utc = instant.atOffset(ZoneOffset.UTC);
        return formatter.format(utc);
    }

    /**
     * 初始化主机名
     *
     * @return 主机名，若获取失败返回 "unknown"
     */
    @NotNull
    private static String initHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

}
