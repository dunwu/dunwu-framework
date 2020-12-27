package io.github.dunwu.data.validator.annotation;

import io.github.dunwu.data.validator.RegexValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;

/**
 * 正则校验注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexValidator.class)
public @interface RegexValid {

    String regexp();

    String message() default "未通过正则校验";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
