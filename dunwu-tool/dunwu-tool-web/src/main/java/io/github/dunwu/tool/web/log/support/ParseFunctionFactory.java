package io.github.dunwu.tool.web.log.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.web.log.service.ParseFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析方法工厂
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class ParseFunctionFactory {

    private Map<String, ParseFunction> allFunctionMap;

    public ParseFunctionFactory(List<ParseFunction> parseFunctions) {
        if (CollectionUtil.isEmpty(parseFunctions)) {
            return;
        }
        allFunctionMap = new HashMap<>();
        for (ParseFunction parseFunction : parseFunctions) {
            if (StrUtil.isBlank(parseFunction.getFunctionName())) {
                continue;
            }
            allFunctionMap.put(parseFunction.getFunctionName(), parseFunction);
        }
    }

    public ParseFunction getFunction(String functionName) {
        return allFunctionMap.get(functionName);
    }

    public boolean isBeforeFunction(String functionName) {
        return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).isBeforeFunction();
    }

}
