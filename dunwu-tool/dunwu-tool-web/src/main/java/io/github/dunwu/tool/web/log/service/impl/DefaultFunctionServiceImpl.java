package io.github.dunwu.tool.web.log.service.impl;

import io.github.dunwu.tool.web.log.service.FunctionService;
import io.github.dunwu.tool.web.log.service.ParseFunction;
import io.github.dunwu.tool.web.log.support.ParseFunctionFactory;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@RequiredArgsConstructor
public class DefaultFunctionServiceImpl implements FunctionService {

    private final ParseFunctionFactory parseFunctionFactory;

    @Override
    public String parse(String methodName, String value) {
        ParseFunction function = parseFunctionFactory.getFunction(methodName);
        if (function == null) {
            return value;
        }
        return function.parse(value);
    }

    @Override
    public boolean isBeforeFunction(String functionName) {
        return parseFunctionFactory.isBeforeFunction(functionName);
    }

}
