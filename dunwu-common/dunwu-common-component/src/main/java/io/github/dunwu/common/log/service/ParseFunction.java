package io.github.dunwu.common.log.service;

import io.github.dunwu.common.log.annotation.OperationLog;

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
     * 获取解析函数的名称
     *
     * @return 函数名称
     */
    String getFunctionName();

    /**
     * 解析数据并返回结果
     *
     * @param value 待解析的数据
     * @return 解析后的字符串结果
     */
    String parse(String value);

}
