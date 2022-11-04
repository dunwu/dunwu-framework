package io.github.dunwu.tool.data.desensitized.constant;

/**
 * 敏感数据脱敏类型
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-04
 */
public enum SensitiveTypeEnum {
    /**
     * 所有字符都脱敏
     */
    ALL,
    /**
     * 直接展示为 null
     */
    NONE,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 车牌号
     */
    CAR_LICENSE,
    /**
     * 银行卡
     */
    BANK_CARD;
}
