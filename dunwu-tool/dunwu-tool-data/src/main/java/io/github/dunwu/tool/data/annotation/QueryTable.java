package io.github.dunwu.tool.data.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryTable {

    @AliasFor("entity")
    Class<?> value() default Object.class;

    @AliasFor("value")
    Class<?> entity() default Object.class;

}
