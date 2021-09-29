package io.github.dunwu.tool.web.log.annotation;

import io.github.dunwu.tool.web.log.constant.LogLevel;

import java.lang.annotation.*;

/**
 * 日志注解
 * <p>
 * LogAspect 是 {@link AppLog} 注解的处理器
 * <p>
 * 被标记的方法被调用时，会将调用信息写入 DB
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2020-04-09
 */
@Inherited
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AppLog {

    /**
     * 业务类型（便于搜索的关键字）
     */
    String bizType() default "";

    /**
     * 日志内容（支持 Spring EL 表达式）
     */
    String value();

    /**
     * 操作者（支持 Spring EL 表达式）
     */
    String operator() default "";

    /**
     * 操作类型
     */
    String operType() default "";

    /**
     * 日志级别
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 是否存储到 DB
     */
    boolean storeDb() default true;

}
