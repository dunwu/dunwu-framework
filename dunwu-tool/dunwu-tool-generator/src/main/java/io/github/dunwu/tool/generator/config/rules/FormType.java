package io.github.dunwu.tool.generator.config.rules;

/**
 * @author peng.zhang
 * @date 2021/3/23
 */
public enum FormType {
    Input("Input", "输入框"),
    Textarea("Textarea", "文本域输入框"),
    InputNumber("InputNumber", "计数器"),
    Radio("Radio", "单选框"),
    Select("Select", "选择器"),
    Switch("Switch", "开关"),
    DateTimePicker("DateTimePicker", "日期时间选择器");

    private final String code;
    private final String desc;

    FormType(String code, String desc) {
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
