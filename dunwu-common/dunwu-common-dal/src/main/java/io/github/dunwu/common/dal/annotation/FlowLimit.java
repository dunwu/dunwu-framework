package io.github.dunwu.common.dal.annotation;

import io.github.dunwu.common.dal.constant.FlowLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流控注解
 * <p>
 * 用于标记需要进行流量控制的方法，通常配合 AOP 或拦截器实现限流逻辑。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowLimit {

    /**
     * 资源名称
     * <p>
     * 用于描述接口功能，便于日志记录和监控展示
     *
     * @return 资源名称
     */
    String name() default "";

    /**
     * 资源 Key
     * <p>
     * 用于标识限流的维度，例如 IP、用户 ID 或接口路径
     *
     * @return 资源 Key
     */
    String key() default "";

    /**
     * Key 前缀
     * <p>
     * 用于在缓存或计数器中区分不同的限流策略组
     *
     * @return Key 前缀
     */
    String prefix() default "";

    /**
     * 时间范围
     * <p>
     * 限流的时间窗口大小，单位为秒
     *
     * @return 时间范围（秒）
     */
    int period();

    /**
     * 限制访问次数
     * <p>
     * 在指定的 {@link #period()} 内允许的最大请求次数
     *
     * @return 最大访问次数
     */
    int count();

    /**
     * 限制类型
     * <p>
     * 指定限流的策略类型，例如基于 IP、用户或自定义维度
     *
     * @return 限流类型，默认为 {@link FlowLimitType#CUSTOMER}
     */
    FlowLimitType limitType() default FlowLimitType.CUSTOMER;

}
