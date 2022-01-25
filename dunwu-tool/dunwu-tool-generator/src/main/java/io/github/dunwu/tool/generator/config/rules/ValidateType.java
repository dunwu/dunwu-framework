package io.github.dunwu.tool.generator.config.rules;

/**
 * 前端校验类型
 *
 * @author peng.zhang
 * @date 2021/3/23
 */
public enum ValidateType {
    /**
     * 文本校验
     */
    STRING("string", "文本校验"),
    /**
     * 数字校验
     */
    NUMBER("number", "数字校验"),
    /**
     * 布尔校验
     */
    BOOLEAN("boolean", "布尔校验"),
    /**
     * 整数校验
     */
    INTEGER("integer", "整型校验"),
    /**
     * 浮点数校验
     */
    FLOAT("float", "浮点数校验"),
    /**
     * URL校验
     */
    URL("url", "URL校验"),
    /**
     * 邮件校验
     */
    EMAIL("email", "邮件校验");

    private final String code;
    private final String desc;

    ValidateType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
