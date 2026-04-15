package io.github.dunwu.common.web.aop.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * 方法信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Data
@ToString
@Builder
public class MethodInfo {

    /** 目标类 */
    private Class<?> clazz;

    /** 目标方法 */
    private Method method;

    /** 方法参数 */
    private Object[] args;

}
