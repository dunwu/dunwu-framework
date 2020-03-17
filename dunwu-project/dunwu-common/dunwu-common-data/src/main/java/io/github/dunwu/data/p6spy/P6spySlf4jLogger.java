package io.github.dunwu.data.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义 <a href="https://github.com/p6spy/p6spy">p6spy</a> 日志输出器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-16
 */
public class P6spySlf4jLogger extends FormattedLogger {

    private Logger log = LoggerFactory.getLogger("p6spy");

    public P6spySlf4jLogger() {}

    @Override
    public void logException(Exception e) {
        log.error("", e);
    }

    @Override
    public void logText(String text) {
        log.info(text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql,
        String url) {
        String msg = this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        return true;
    }

}
