package io.github.dunwu.common.log.service.impl;

import io.github.dunwu.common.log.service.FunctionService;
import io.github.dunwu.common.log.service.ParseFunction;
import io.github.dunwu.common.log.support.ParseFunctionFactory;
import lombok.RequiredArgsConstructor;

/**
 * 默认函数服务实现类
 * <p>
 * 基于 {@link ParseFunctionFactory} 实现函数解析逻辑
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@RequiredArgsConstructor
public class DefaultFunctionServiceImpl implements FunctionService {

    /** 解析函数工厂 */
    private final ParseFunctionFactory parseFunctionFactory;

    /**
     * 解析函数值
     *
     * @param methodName 方法名称
     * @param value      待解析的值
     * @return 解析后的结果
     */
    @Override
    public String parse(String methodName, String value) {
        ParseFunction function = parseFunctionFactory.getFunction(methodName);
        if (function == null) {
            return value;
        }
        return function.parse(value);
    }

    /**
     * 判断是否为前置函数
     *
     * @param functionName 函数名称
     * @return true 表示是前置函数
     */
    @Override
    public boolean isBeforeFunction(String functionName) {
        return parseFunctionFactory.isBeforeFunction(functionName);
    }

}
