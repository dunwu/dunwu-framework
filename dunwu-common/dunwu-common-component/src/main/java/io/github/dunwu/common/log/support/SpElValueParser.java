package io.github.dunwu.common.log.support;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.common.web.aop.domain.MethodInfo;
import io.github.dunwu.common.log.annotation.OperationLog;
import io.github.dunwu.common.log.service.FunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析 {@link OperationLog} 中的 SpEl 表达式
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@RequiredArgsConstructor
public class SpElValueParser implements BeanFactoryAware {

    /** Spring Bean 工厂 */
    protected BeanFactory beanFactory;

    /** 函数服务 */
    private final FunctionService functionService;

    /** 表达式解析器 */
    private final LogRecordExpressionEvaluator expressionEvaluator = new LogRecordExpressionEvaluator();

    /** SpEL 表达式匹配模式 */
    private static final Pattern PATTERN = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");

    /**
     * 解析 SpEL 表达式模板
     *
     * @param templates  模板集合
     * @param result     执行结果
     * @param methodInfo 方法信息
     * @param errorMsg   错误信息
     * @return 解析后的键值对映射
     */
    public Map<String, String> parse(Collection<String> templates, Object result, MethodInfo methodInfo,
        String errorMsg) {
        Map<String, String> expressionValues = new HashMap<>();
        EvaluationContext context =
            expressionEvaluator.createEvaluationContext(methodInfo, result, errorMsg, beanFactory);

        for (String expressionTemplate : templates) {
            if (expressionTemplate.contains("{")) {
                Matcher matcher = PATTERN.matcher(expressionTemplate);
                StringBuffer parsedStr = new StringBuffer();
                while (matcher.find()) {
                    String expression = matcher.group(2);
                    AnnotatedElementKey key = new AnnotatedElementKey(methodInfo.getMethod(), methodInfo.getClass());
                    Object value = expressionEvaluator.parseExpression(expression, key, context);
                    if (value != null) {
                        if (value instanceof String) {
                            matcher.appendReplacement(parsedStr, (String)value);
                        } else if (value instanceof Number) {
                            matcher.appendReplacement(parsedStr, String.valueOf(value));
                        } else {
                            matcher.appendReplacement(parsedStr, JSONUtil.toJsonStr(value));
                        }
                    }
                }
                matcher.appendTail(parsedStr);
                expressionValues.put(expressionTemplate, parsedStr.toString());
            } else {
                expressionValues.put(expressionTemplate, expressionTemplate);
            }
        }
        return expressionValues;
    }

    /**
     * 处理方法执行前的函数模板
     *
     * @param templates  模板集合
     * @param methodInfo 方法信息
     * @return 解析后的键值对映射
     */
    public Map<String, String> processBeforeExecuteFunctionTemplate(Collection<String> templates,
        MethodInfo methodInfo) {
        Map<String, String> expressionValues = new HashMap<>();
        EvaluationContext context = expressionEvaluator.createEvaluationContext(methodInfo, null, null, beanFactory);

        for (String expressionTemplate : templates) {
            if (expressionTemplate.contains("{")) {
                Matcher matcher = PATTERN.matcher(expressionTemplate);
                StringBuffer parsedStr = new StringBuffer();
                while (matcher.find()) {
                    String expression = matcher.group(2);
                    AnnotatedElementKey key = new AnnotatedElementKey(methodInfo.getMethod(), methodInfo.getClass());
                    Object value = expressionEvaluator.parseExpression(expression, key, context);
                    if (value != null) {
                        if (value instanceof String) {
                            matcher.appendReplacement(parsedStr, (String)value);
                        } else if (value instanceof Number) {
                            matcher.appendReplacement(parsedStr, String.valueOf(value));
                        } else {
                            matcher.appendReplacement(parsedStr, JSONUtil.toJsonStr(value));
                        }
                    }
                }
                matcher.appendTail(parsedStr);
                expressionValues.put(expressionTemplate, parsedStr.toString());
            } else {
                expressionValues.put(expressionTemplate, expressionTemplate);
            }
        }
        return expressionValues;
    }

    /**
     * 获取函数返回值
     *
     * @param beforeFunctionNameAndReturnMap 前置函数名称与返回值映射
     * @param value                          当前值
     * @param functionName                   函数名称
     * @return 函数返回结果
     */
    private String getFunctionReturnValue(Map<String, String> beforeFunctionNameAndReturnMap, Object value,
        String functionName) {
        String functionReturnValue = "";
        if (beforeFunctionNameAndReturnMap != null) {
            functionReturnValue = beforeFunctionNameAndReturnMap.get(functionName);
        }
        if (StringUtils.isEmpty(functionReturnValue)) {
            functionReturnValue = functionService.parse(functionName, (String)value);
        }
        return functionReturnValue;
    }

    /**
     * 设置 Bean 工厂
     *
     * @param beanFactory Spring Bean 工厂
     * @throws BeansException Bean 异常
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
