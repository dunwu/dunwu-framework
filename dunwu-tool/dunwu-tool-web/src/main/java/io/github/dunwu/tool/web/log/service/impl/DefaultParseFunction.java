package io.github.dunwu.tool.web.log.service.impl;

import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.service.ParseFunction;

/**
 * 操作日志注解 {@link OperationLog} 的 SpEl 模板中使用的解析函数默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class DefaultParseFunction implements ParseFunction {

    @Override
    public boolean isBeforeFunction() {
        return true;
    }

    @Override
    public String getFunctionName() {
        return null;
    }

    @Override
    public String parse(String value) {
        return null;
    }

}
