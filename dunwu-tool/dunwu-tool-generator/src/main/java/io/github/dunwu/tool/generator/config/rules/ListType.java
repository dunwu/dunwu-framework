package io.github.dunwu.tool.generator.config.rules;

/**
 * 表单控件类型
 *
 * @author peng.zhang
 * @date 2021/3/23
 */
public enum ListType {
    Text("Text", "文本"),
    Date("Date", "日期"),
    Image("Image", "图片");

    private final String code;
    private final String name;

    ListType(String code, String name) {
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
