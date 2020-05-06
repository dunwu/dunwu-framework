package io.github.dunwu.data.core.annotation;

import io.github.dunwu.tool.bean.support.NamingStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 查询注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String[] blurry() default {};

    QueryType type() default QueryType.EQUAL;

    String joinName() default "";

    boolean nullable() default false;

    NamingStrategy namingStrategy() default NamingStrategy.LOWER_UNDERLINE;

    enum QueryType {
        EQUAL,
        LIKE,
        NOT_LIKE,
        LIKE_LEFT,
        LIKE_RIGHT,
        GREATER_THAN,
        LESS_THAN,
        INNER_LIKE,
        LESS_THAN_NQ,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_NULL
    }

}
