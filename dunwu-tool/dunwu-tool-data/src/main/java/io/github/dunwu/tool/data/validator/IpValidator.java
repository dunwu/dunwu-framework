package io.github.dunwu.tool.data.validator;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.data.validator.annotation.IsIp;
import io.github.dunwu.tool.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
public class IpValidator implements ConstraintValidator<IsIp, String> {

    private IsIp.Type type;

    @Override
    public void initialize(IsIp constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(s)) {
            return false;
        } else {
            switch (type) {
                case IPv4:
                    return ValidatorUtil.isIpv4(s);
                case IPv6:
                    return ValidatorUtil.isIpv6(s);
                case Any:
                    return ValidatorUtil.isIpv4(s) || ValidatorUtil.isIpv6(s);
                default:
                    throw new IllegalArgumentException("type must not be null");
            }
        }
    }

}
