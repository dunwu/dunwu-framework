package io.github.dunwu.tool.data.validator.annotation;

import io.github.dunwu.tool.data.validator.MobileValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证被修饰字段、参数字符串是否为 Ip 地址
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsIp {

    String message() default "不是合法 IP 地址";

    Type type() default Type.Any;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum Type {
        IPv4,
        IPv6,
        Any
    }

}
