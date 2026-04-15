package io.github.dunwu.common.dal.generator.config.rules;

/**
 * 前端表单校验类型枚举。
 * <p>
 * 用于定义代码生成器生成的前端表单中，各字段对应的校验规则类型（如必填、数字、邮箱等）。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021/3/23
 */
public enum ValidateType {
    /** 文本校验 */
    STRING("string", "文本校验"),
    /** 数字校验 */
    NUMBER("number", "数字校验"),
    /** 布尔校验 */
    BOOLEAN("boolean", "布尔校验"),
    /** 整数校验 */
    INTEGER("integer", "整型校验"),
    /** 浮点数校验 */
    FLOAT("float", "浮点数校验"),
    /** URL 地址校验 */
    URL("url", "URL校验"),
    /** 电子邮件校验 */
    EMAIL("email", "邮件校验");

    /**
     * 校验类型代码标识
     */
    private final String code;

    /**
     * 校验类型中文描述
     */
    private final String desc;

    /**
     * 构造方法
     *
     * @param code 类型代码标识
     * @param desc 类型中文描述
     */
    ValidateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取类型代码标识
     *
     * @return 类型代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取类型中文描述
     *
     * @return 类型描述
     */
    public String getDesc() {
        return desc;
    }
}
