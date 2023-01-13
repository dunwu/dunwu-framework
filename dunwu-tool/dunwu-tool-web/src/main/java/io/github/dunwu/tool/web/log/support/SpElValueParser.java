package io.github.dunwu.tool.web.log.support;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.web.aop.entity.MethodInfo;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.service.FunctionService;
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

    protected BeanFactory beanFactory;
    private final FunctionService functionService;
    private final LogRecordExpressionEvaluator expressionEvaluator = new LogRecordExpressionEvaluator();

    private static final Pattern PATTERN = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");

    public Map<String, String> parse(Collection<String> templates, Object result,
        MethodInfo methodInfo, String errorMsg) {
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
                            matcher.appendReplacement(parsedStr, (String) value);
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

    public Map<String, String> processBeforeExecuteFunctionTemplate(Collection<String> templates,
        MethodInfo methodInfo) {
        Map<String, String> expressionValues = new HashMap<>();
        EvaluationContext context =
            expressionEvaluator.createEvaluationContext(methodInfo, null, null, beanFactory);

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
                            matcher.appendReplacement(parsedStr, (String) value);
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

    private String getFunctionReturnValue(Map<String, String> beforeFunctionNameAndReturnMap, Object value,
        String functionName) {
        String functionReturnValue = "";
        if (beforeFunctionNameAndReturnMap != null) {
            functionReturnValue = beforeFunctionNameAndReturnMap.get(functionName);
        }
        if (StringUtils.isEmpty(functionReturnValue)) {
            functionReturnValue = functionService.parse(functionName, (String) value);
        }
        return functionReturnValue;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
