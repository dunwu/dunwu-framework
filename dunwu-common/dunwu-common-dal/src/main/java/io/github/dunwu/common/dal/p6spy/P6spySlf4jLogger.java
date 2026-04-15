package io.github.dunwu.common.dal.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * P6Spy 自定义 SLF4J 日志输出器
 * <p>
 * 将 P6Spy 拦截到的 SQL 语句及执行信息通过 SLF4J 输出，方便在开发环境中调试 SQL。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-16
 */
public class P6spySlf4jLogger extends FormattedLogger {

    /**
     * 日志记录器，名称为 "p6spy"
     */
    private final Logger log = LoggerFactory.getLogger("p6spy");

    /**
     * 构造日志输出器实例
     */
    public P6spySlf4jLogger() {
    }

    /**
     * 记录异常信息
     *
     * @param e 发生的异常
     */
    @Override
    public void logException(Exception e) {
        log.error("", e);
    }

    /**
     * 记录普通文本信息
     *
     * @param text 需要记录的文本
     */
    @Override
    public void logText(String text) {
        log.info(text);
    }

    /**
     * 检查指定类别的日志是否启用
     * <p>
     * 默认返回 true，表示启用所有类别的日志记录。
     *
     * @param category 日志类别
     * @return true
     */
    @Override
    public boolean isCategoryEnabled(Category category) {
        return true;
    }

    /**
     * 记录 SQL 执行信息
     * <p>
     * 使用配置的格式化策略将 SQL 信息格式化为字符串，并以 DEBUG 级别输出。
     *
     * @param connectionId 连接 ID
     * @param now          当前时间戳
     * @param elapsed      执行耗时（毫秒）
     * @param category     日志类别
     * @param prepared     预编译 SQL
     * @param sql          实际执行的 SQL
     * @param url          数据库连接 URL
     */
    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql,
        String url) {
        String msg = this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

}
