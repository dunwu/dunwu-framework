package io.github.dunwu.tool.web.log.support;

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

    public LogRecordEvaluationContext(Object obj, Method method, Object[] arguments,
        ParameterNameDiscoverer parameterNameDiscoverer, Object result, String error) {
        super(obj, method, arguments, parameterNameDiscoverer);
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
