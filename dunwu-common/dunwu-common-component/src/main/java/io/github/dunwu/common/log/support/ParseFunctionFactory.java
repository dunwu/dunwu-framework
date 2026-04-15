package io.github.dunwu.common.log.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.log.service.ParseFunction;

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

    /** 所有解析函数映射，key 为函数名称 */
    private Map<String, ParseFunction> allFunctionMap;

    /**
     * 构造函数，初始化解析函数集合
     *
     * @param parseFunctions 解析函数列表
     */
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

    /**
     * 根据函数名称获取解析函数
     *
     * @param functionName 函数名称
     * @return 解析函数实例
     */
    public ParseFunction getFunction(String functionName) {
        return allFunctionMap.get(functionName);
    }

    /**
     * 判断指定函数是否为前置函数
     *
     * @param functionName 函数名称
     * @return true 表示是前置函数
     */
    public boolean isBeforeFunction(String functionName) {
        return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).isBeforeFunction();
    }

}
