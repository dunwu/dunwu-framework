package io.github.dunwu.common.dal.validator.annotation;

import io.github.dunwu.common.dal.validator.MobileValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号校验注解
 * <p>
 * 用于验证被修饰的字段或参数是否为合法的手机号码。
 * 具体的校验逻辑由 {@link MobileValidator} 实现。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsMobile {

    /**
     * 校验失败时的错误消息
     *
     * @return 错误消息模板
     */
    String message();

    /**
     * 校验分组
     *
     * @return 分组类数组
     */
    Class<?>[] groups() default {};

    /**
     * 校验负载
     *
     * @return 负载类数组
     */
    Class<? extends Payload>[] payload() default {};

}
