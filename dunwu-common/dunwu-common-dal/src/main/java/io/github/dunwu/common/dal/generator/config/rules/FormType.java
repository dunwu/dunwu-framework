package io.github.dunwu.common.dal.generator.config.rules;

/**
 * 表单组件类型枚举。
 * <p>
 * 定义了代码生成器支持生成的表单控件类型，用于前端页面生成时决定使用哪种 UI 组件。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022/1/25
 */
public enum FormType {
    /**
     * 输入框（单行文本）
     */
    Input("Input", "输入框"),

    /**
     * 文本域输入框（多行文本）
     */
    Textarea("Textarea", "文本域输入框"),

    /**
     * 计数器（数字输入框）
     */
    InputNumber("InputNumber", "计数器"),

    /**
     * 单选框组
     */
    Radio("Radio", "单选框"),

    /**
     * 选择器（下拉框）
     */
    Select("Select", "选择器"),

    /**
     * 开关（Switch 切换）
     */
    Switch("Switch", "开关"),

    /**
     * 日期时间选择器
     */
    DateTimePicker("DateTimePicker", "日期时间选择器");

    /**
     * 类型代码标识
     */
    private final String code;

    /**
     * 类型中文名称
     */
    private final String name;

    /**
     * 构造方法
     *
     * @param code 类型代码标识
     * @param name 类型中文名称
     */
    FormType(String code, String name) {
        this.code = code;
        this.name = name;
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
     * 获取类型中文名称
     *
     * @return 类型名称
     */
    public String getName() {
        return name;
    }
}
