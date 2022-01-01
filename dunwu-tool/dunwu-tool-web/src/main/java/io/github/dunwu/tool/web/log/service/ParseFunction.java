package io.github.dunwu.tool.web.log.service;

import io.github.dunwu.tool.web.log.annotation.OperationLog;

/**
 * 操作日志注解 {@link OperationLog} 的 SpEl 模板中使用的解析函数
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public interface ParseFunction {

    /**
     * 是否在方法执行前进行解析
     *
     * @return true / false
     */
    default boolean isBeforeFunction() {
        return false;
    }

    /**
     * 获取方法名
     *
     * @return /
     */
    String getFunctionName();

    /**
     * 解析数据
     *
     * @param value 待解析的数据
     * @return /
     */
    String parse(String value);

}
