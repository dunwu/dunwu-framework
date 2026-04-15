package io.github.dunwu.common.log.support;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志记录上下文
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-12-30
 */
public class LogRecordEvaluationContext extends MethodBasedEvaluationContext {

    /**
     * 构造函数
     *
     * @param rootObject              根对象
     * @param method                  方法
     * @param arguments               参数
     * @param parameterNameDiscoverer 参数名发现器
     * @param result                  执行结果
     * @param error                   错误信息
     */
    public LogRecordEvaluationContext(Object rootObject, Method method, Object[] arguments,
        ParameterNameDiscoverer parameterNameDiscoverer, Object result, String error) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && variables.size() > 0) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                setVariable(entry.getKey(), entry.getValue());
            }
        }
        setVariable("_result", result);
        setVariable("_error", error);
    }

}
