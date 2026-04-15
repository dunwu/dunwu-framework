package io.github.dunwu.common.dal.desensitized.constant;

/**
 * 敏感数据脱敏类型枚举
 * <p>
 * 定义了常见的敏感信息脱敏规则，如姓名、身份证、手机号、邮箱等。
 * 用于配合 {@link io.github.dunwu.common.dal.desensitized.annotation.Desensitized} 注解进行数据序列化脱敏。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
public enum SensitiveTypeEnum {
    /**
     * 所有字符都脱敏（通常显示为全星号）
     */
    ALL,
    /**
     * 不进行脱敏，直接展示原值（若为 null 则展示为 null）
     */
    NONE,
    /**
     * 中文名脱敏
     * <p>
     * 例如：张三 -> 张*
     */
    CHINESE_NAME,
    /**
     * 身份证号脱敏
     * <p>
     * 保留前 1 位和后 1 位，中间用星号替换
     */
    ID_CARD,
    /**
     * 座机号脱敏
     * <p>
     * 保留前 4 位和后 2 位
     */
    FIXED_PHONE,
    /**
     * 手机号脱敏
     * <p>
     * 保留前 3 位和后 4 位，中间 4 位用星号替换
     */
    MOBILE_PHONE,
    /**
     * 地址脱敏
     * <p>
     * 保留前 6 位，后续地址用星号替换
     */
    ADDRESS,
    /**
     * 电子邮件脱敏
     * <p>
     * 保留邮箱前缀的前 3 位，后续字符用星号替换，直到 @ 符号
     */
    EMAIL,
    /**
     * 密码脱敏
     * <p>
     * 全部显示为星号
     */
    PASSWORD,
    /**
     * 车牌号脱敏
     * <p>
     * 保留前 2 位和后 1 位
     */
    CAR_LICENSE,
    /**
     * 银行卡脱敏
     * <p>
     * 保留前 4 位和后 4 位，中间用星号替换
     */
    BANK_CARD;
}
