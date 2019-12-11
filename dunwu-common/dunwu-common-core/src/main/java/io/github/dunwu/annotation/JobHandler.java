package io.github.dunwu.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface JobHandler {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
