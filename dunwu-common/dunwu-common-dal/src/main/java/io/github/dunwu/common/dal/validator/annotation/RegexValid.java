package io.github.dunwu.common.dal.validator.annotation;

import io.github.dunwu.common.dal.validator.RegexValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;

/**
 * 自定义正则表达式校验注解
 * <p>
 * 用于验证被修饰的字段或参数是否符合指定的正则表达式规则。
 * 具体的校验逻辑由 {@link RegexValidator} 实现。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexValidator.class)
public @interface RegexValid {

    /**
     * 用于校验的正则表达式
     *
     * @return 正则表达式字符串
     */
    String regexp();

    /**
     * 校验失败时的错误消息
     *
     * @return 错误消息模板
     */
    String message() default "未通过正则校验";

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
