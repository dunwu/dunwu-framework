package io.github.dunwu.common.dal.validator.annotation;

import io.github.dunwu.common.dal.validator.MobileValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * IP 地址校验注解
 * <p>
 * 用于验证被修饰的字段或参数是否为合法的 IP 地址。
 * 支持指定校验 IPv4、IPv6 或任意类型。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface IsIp {

    /**
     * 校验失败时的错误消息
     *
     * @return 错误消息模板
     */
    String message() default "不是合法 IP 地址";

    /**
     * 指定需要校验的 IP 地址类型
     *
     * @return IP 类型，默认为 {@link Type#Any}
     */
    Type type() default Type.Any;

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

    /**
     * IP 地址类型枚举
     */
    enum Type {
        /**
         * IPv4 地址
         */
        IPv4,
        /**
         * IPv6 地址
         */
        IPv6,
        /**
         * 任意 IP 地址（IPv4 或 IPv6）
         */
        Any
    }

}
