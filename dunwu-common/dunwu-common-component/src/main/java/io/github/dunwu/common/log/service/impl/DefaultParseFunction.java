package io.github.dunwu.common.log.service.impl;

import io.github.dunwu.common.log.annotation.OperationLog;
import io.github.dunwu.common.log.service.ParseFunction;

/**
 * 操作日志注解 {@link OperationLog} 的 SpEl 模板中使用的解析函数默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class DefaultParseFunction implements ParseFunction {

    /**
     * 判断是否为前置函数
     *
     * @return true 表示是前置函数
     */
    @Override
    public boolean isBeforeFunction() {
        return true;
    }

    /**
     * 获取函数名称
     *
     * @return 函数名称
     */
    @Override
    public String getFunctionName() {
        return null;
    }

    /**
     * 解析值
     *
     * @param value 待解析的值
     * @return 解析后的结果
     */
    @Override
    public String parse(String value) {
        return null;
    }

}
