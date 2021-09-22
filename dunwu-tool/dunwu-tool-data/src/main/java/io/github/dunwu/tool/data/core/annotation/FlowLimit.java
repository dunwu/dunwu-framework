package io.github.dunwu.tool.data.core.annotation;

import io.github.dunwu.tool.data.core.constant.FlowLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流控注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowLimit {

    /**
     * 资源名称，用于描述接口功能
     */
    String name() default "";

    /**
     * 资源 key
     */
    String key() default "";

    /**
     * key prefix
     */
    String prefix() default "";

    /**
     * 时间范围，单位秒
     */
    int period();

    /**
     * 限制访问次数
     */
    int count();

    /**
     * 限制类型
     */
    FlowLimitType limitType() default FlowLimitType.CUSTOMER;

}
