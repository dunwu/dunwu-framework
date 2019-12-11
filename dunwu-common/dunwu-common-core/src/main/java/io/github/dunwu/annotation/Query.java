package io.github.dunwu.annotation;

import java.lang.annotation.*;

/**
 * Dao 标记注解
 * <p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    Class<?> entityClass() default Object.class;

}
