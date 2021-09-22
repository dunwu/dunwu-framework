package io.github.dunwu.tool.data.p6spy;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * 自定义 <a href="https://github.com/p6spy/p6spy">p6spy</a> sql 输出格式
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-16
 */
public class P6spySqlFormat implements MessageFormattingStrategy {

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
