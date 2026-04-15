package io.github.dunwu.common.dal.p6spy;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6Spy 自定义 SQL 日志格式化策略
 * <p>
 * 实现了 {@link MessageFormattingStrategy} 接口，用于美化 P6Spy 拦截到的 SQL 日志输出。
 * 输出格式包含执行耗时和格式化后的 SQL 语句。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-16
 */
public class P6spySqlFormat implements MessageFormattingStrategy {

    /**
     * 格式化 SQL 日志消息
     * <p>
     * 如果 SQL 语句不为空，则返回包含执行耗时和 SQL 内容的格式化字符串；否则返回空字符串。
     * 同时会对 SQL 中的多余空白字符进行压缩处理。
     *
     * @param connectionId 数据库连接 ID
     * @param now          当前时间戳
     * @param elapsed      执行耗时（毫秒）
     * @param category     日志类别
     * @param prepared     预编译 SQL
     * @param sql          实际执行的 SQL 语句
     * @param url          数据库连接 URL
     * @return 格式化后的日志消息字符串
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
        String sql, String url) {
        if (StrUtil.isNotBlank(sql)) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("耗时：%s ms ", elapsed))
              .append(String.format("执行 SQL：\n%s\n", sql.replaceAll("[\\s]+", StrUtil.SPACE)));
            return sb.toString();
        } else {
            return StrUtil.EMPTY;
        }
    }

}
