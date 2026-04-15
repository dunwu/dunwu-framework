package io.github.dunwu.common.log.service;

/**
 * 解析函数服务接口
 * <p>
 * 用于日志记录中的 SpEL 表达式解析和函数调用
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public interface FunctionService {

    /**
     * 解析函数值
     *
     * @param methodName 方法名称
     * @param value      待解析的值
     * @return 解析后的结果
     */
    String parse(String methodName, String value);

    /**
     * 判断是否为前置函数
     *
     * @param functionName 函数名称
     * @return true 表示是前置函数
     */
    boolean isBeforeFunction(String functionName);

}
