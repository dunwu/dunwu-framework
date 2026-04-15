package io.github.dunwu.common.dal.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 新增校验组注解
 * <p>
 * 用于标识在新增操作时需要进行的校验规则分组。
 * 通常配合 Bean Validation (如 Hibernate Validator) 使用，
 * 通过 {@code @Validated(AddCheck.class)} 来触发特定于新增场景的校验逻辑。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AddCheck {
}
