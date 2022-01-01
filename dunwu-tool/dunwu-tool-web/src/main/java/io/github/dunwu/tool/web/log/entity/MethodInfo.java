package io.github.dunwu.tool.web.log.entity;

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

    private Class<?> clazz;
    private Method method;
    private Object[] args;

}
