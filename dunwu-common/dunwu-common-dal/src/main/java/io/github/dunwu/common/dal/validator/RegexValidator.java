package io.github.dunwu.common.dal.validator;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.dal.validator.annotation.RegexValid;
import io.github.dunwu.common.util.RegexUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 正则表达式校验器实现
 * <p>
 * 配合 {@link RegexValid} 注解使用，验证字符串是否符合注解中指定的正则表达式。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
public class RegexValidator implements ConstraintValidator<RegexValid, String> {

    /**
     * 从注解中获取的正则表达式
     */
    private String regexp;

    /**
     * 初始化校验器
     * <p>
     * 从 {@link RegexValid} 注解中提取配置的正则表达式。
     *
     * @param constraintAnnotation 注解实例
     */
    @Override
    public void initialize(RegexValid constraintAnnotation) {
        regexp = constraintAnnotation.regexp();
    }

    /**
     * 执行正则表达式校验
     *
     * @param s                      待校验的字符串
     * @param constraintValidatorContext 校验上下文
     * @return 如果字符串匹配正则表达式返回 true，否则返回 false
     * @throws IllegalArgumentException 如果正则表达式为空
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (StrUtil.isBlank(s)) {
            return false;
        }
        if (StrUtil.isBlank(regexp)) {
            throw new IllegalArgumentException("regexp in @RegexValid must not be blank");
        }

        return RegexUtil.matches(s, regexp);
    }

}
