package io.github.dunwu.common.dal.generator.config.rules;

/**
 * 列表展示控件类型枚举。
 * <p>
 * 用于定义代码生成器中列表页（List View）各字段的展示组件类型。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021/3/23
 */
public enum ListType {
    /** 纯文本展示 */
    Text("Text", "文本"),
    /** 日期格式展示 */
    Date("Date", "日期"),
    /** 图片展示 */
    Image("Image", "图片");

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
    ListType(String code, String name) {
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
