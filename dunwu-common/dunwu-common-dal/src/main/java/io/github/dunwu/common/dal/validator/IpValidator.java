package io.github.dunwu.common.dal.validator;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.dal.validator.annotation.IsIp;
import io.github.dunwu.common.util.ValidatorUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * IP 地址校验器实现
 * <p>
 * 配合 {@link IsIp} 注解使用，根据注解中指定的类型（IPv4, IPv6, Any）
 * 验证字符串是否为合法的 IP 地址。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
public class IpValidator implements ConstraintValidator<IsIp, String> {

    /**
     * 需要校验的 IP 地址类型
     */
    private IsIp.Type type;

    /**
     * 初始化校验器
     * <p>
     * 从 {@link IsIp} 注解中获取配置的 IP 类型。
     *
     * @param constraintAnnotation 注解实例
     */
    @Override
    public void initialize(IsIp constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    /**
     * 执行 IP 地址校验
     *
     * @param s                      待校验的字符串
     * @param constraintValidatorContext 校验上下文
     * @return 如果字符串符合指定的 IP 类型格式返回 true，否则返回 false
     */
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
