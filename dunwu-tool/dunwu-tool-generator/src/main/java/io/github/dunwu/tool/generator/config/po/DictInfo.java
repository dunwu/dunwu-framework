package io.github.dunwu.tool.generator.config.po;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * 字典信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public class DictInfo {

    /** 字典编码 */
    private String code;
    /** 字典名称 */
    private String name;
    /** 字典备注 */
    private String note;
    /** 格式化字典编码 */
    private String formatCode;
    /** 属于字典的字典选项 */
    private List<DictOptionInfo> options;

    public DictInfo() {}

    public DictInfo(String code, String name, String note) {
        this.code = code;
        this.name = name;
        this.note = note;
        this.formatCode = getFormatCode(code);
    }

    public String getCode() {
        return code;
    }

    public DictInfo setCode(String code) {
        this.code = code;
        this.formatCode = getFormatCode(code);
        return this;
    }

    public String getName() {
        return name;
    }

    public DictInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DictInfo setNote(String note) {
        this.note = note;
        return this;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public List<DictOptionInfo> getOptions() {
        return options;
    }

    public DictInfo setOptions(List<DictOptionInfo> options) {
        this.options = options;
        return this;
    }

    public static String getFormatCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        // 字典名称驼峰命名，首字母大写
        String formatCode = StrUtil.toCamelCase(code);
        return StrUtil.upperFirst(formatCode);
    }

}
