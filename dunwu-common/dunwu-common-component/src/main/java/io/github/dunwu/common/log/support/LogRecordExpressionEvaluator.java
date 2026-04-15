package io.github.dunwu.common.log.support;

import io.github.dunwu.common.web.aop.domain.MethodInfo;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志记录表达式解析器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-12-30
 */
public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {

    /** 表达式缓存 */
    private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);

    /** 目标方法缓存 */
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /**
     * 解析表达式
     *
     * @param expression 表达式字符串
     * @param methodKey  方法注解键
     * @param context    评估上下文
     * @return 表达式计算结果
     */
    public Object parseExpression(String expression, AnnotatedElementKey methodKey, EvaluationContext context) {
        return getExpression(this.expressionCache, methodKey, expression).getValue(context);
    }

    /**
     * 创建表达式评估上下文
     *
     * @param methodInfo  方法信息
     * @param result      响应结果
     * @param error       错误信息
     * @param beanFactory Spring Bean 工厂
     * @return 评估上下文实例
     */
    public EvaluationContext createEvaluationContext(MethodInfo methodInfo, Object result, String error,
        BeanFactory beanFactory) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(methodInfo.getMethod(), methodInfo.getClass());
        Method method = this.targetMethodCache.get(methodKey);
        if (method == null) {
            method = AopUtils.getMostSpecificMethod(methodInfo.getMethod(), methodInfo.getClass());
            this.targetMethodCache.put(methodKey, method);
        }
        LogRecordEvaluationContext evaluationContext =
            new LogRecordEvaluationContext(null, method, methodInfo.getArgs(), getParameterNameDiscoverer(), result,
                error);
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

}
