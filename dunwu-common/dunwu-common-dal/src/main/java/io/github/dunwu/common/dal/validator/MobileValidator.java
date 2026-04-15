package io.github.dunwu.common.dal.validator;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.dal.validator.annotation.IsMobile;
import io.github.dunwu.common.util.ValidatorUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 手机号校验器实现
 * <p>
 * 配合 {@link IsMobile} 注解使用，验证字符串是否为合法的手机号码。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    /**
     * 初始化校验器
     *
     * @param constraintAnnotation 注解实例
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) { }

    /**
     * 执行手机号校验
     *
     * @param s                      待校验的字符串
     * @param constraintValidatorContext 校验上下文
     * @return 如果字符串是合法的手机号返回 true，否则返回 false
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(s)) {
            return false;
        } else {
            return ValidatorUtil.isMobile(s);
        }
    }

}
