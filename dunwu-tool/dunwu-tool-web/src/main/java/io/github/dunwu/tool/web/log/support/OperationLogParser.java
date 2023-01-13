package io.github.dunwu.tool.web.log.support;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.entity.OperationLogInfo;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * 操作日志注解 {@link OperationLog} 解析器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class OperationLogParser {

    public static OperationLogInfo parse(Method method, Class<?> targetClass) {
        // Don't allow no-public methods as required.
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        // First try is the method in the target class.
        Collection<OperationLog> logs =
            AnnotatedElementUtils.getAllMergedAnnotations(specificMethod, OperationLog.class);
        if (CollectionUtil.isNotEmpty(logs)) {
            for (OperationLog annotation : logs) {
                return doParse(specificMethod, annotation);
            }
        }
        return null;
    }

    private static OperationLogInfo doParse(AnnotatedElement element, OperationLog annotation) {
        OperationLogInfo logInfo = OperationLogInfo.builder()
                                                   .bizNo(annotation.bizNo())
                                                   .bizType(annotation.bizType())
                                                   .operation(annotation.operation())
                                                   .success(annotation.success())
                                                   .fail(annotation.fail())
                                                   .detail(annotation.detail())
                                                   .operatorId(annotation.operatorId())
                                                   .operatorName(annotation.operatorName())
                                                   .condition(annotation.condition())
                                                   .build();
        if (StrUtil.isBlank(logInfo.getBizType())) {
            throw new IllegalStateException(element.toString() + " 上的 @OperationLog 注解没有配置 bizType 属性");
        }
        return logInfo;
    }

}
