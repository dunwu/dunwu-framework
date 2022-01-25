package io.github.dunwu.tool.generator.config.rules;

/**
 * 列表控件类型
 *
 * @author peng.zhang
 * @date 2022/1/25
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
    private final String name;

    FormType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
