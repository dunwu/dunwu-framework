package io.github.dunwu.data.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface JobHandler {

    @AliasFor("beanName")
    String value() default "";

    @AliasFor("value")
    String beanName() default "";

    String executeMethod() default "execute";

    Class<?>[] paramTypes() default {};

}
