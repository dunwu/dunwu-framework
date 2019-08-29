package io.github.dunwu.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Manager 标记注解
 * <p>
 * 用于指示被修饰的类型是一个管理类。管理类通常由多个 Service 类组成，用于处理一些跨职能的服务。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Manager {
    @AliasFor(annotation = Component.class) String value() default "";
}
