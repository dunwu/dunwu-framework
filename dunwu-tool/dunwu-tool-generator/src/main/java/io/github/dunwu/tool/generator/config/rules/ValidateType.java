package io.github.dunwu.tool.generator.config.rules;

/**
 * 前端校验类型
 *
 * @author peng.zhang
 * @date 2021/3/23
 */
public enum ValidateType {
    STRING("string", "文本校验"),
    NUMBER("number", "数字校验"),
    BOOLEAN("boolean", "布尔校验"),
    INTEGER("integer", "整型校验"),
    FLOAT("float", "选择器"),
    URL("url", "开关"),
    EMAIL("email", "日期时间选择器");

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
