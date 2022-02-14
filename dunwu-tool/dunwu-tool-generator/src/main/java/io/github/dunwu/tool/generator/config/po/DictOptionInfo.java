package io.github.dunwu.tool.generator.config.po;

import cn.hutool.core.util.StrUtil;

/**
 * 字典选项信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public class DictOptionInfo {

    /** 字典选项编码 */
    private String code;
    /** 字典选项名称 */
    private String name;
    /** 字典选项备注 */
    private String note;
    /** 字典选项格式化编码 */
    private String formatCode;

    public DictOptionInfo() { }

    public DictOptionInfo(String code, String name, String note) {
        this.code = code;
        this.name = name;
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public DictOptionInfo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public DictOptionInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DictOptionInfo setNote(String note) {
        this.note = note;
        return this;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public DictOptionInfo setFormatCode(String formatCode) {
        this.formatCode = formatCode;
        return this;
    }

    public static String getFormatCode(String dictCode, String dictOptionCode) {
        String dictUnderlineName = StrUtil.toUnderlineCase(dictCode).toUpperCase();
        String dictOptionUnderlineName = StrUtil.toUnderlineCase(dictOptionCode).toUpperCase();
        if (!dictOptionUnderlineName.contains(dictUnderlineName)) {
            dictOptionUnderlineName = dictUnderlineName + "_" + dictOptionUnderlineName;
        }
        return dictOptionUnderlineName;
    }

}
