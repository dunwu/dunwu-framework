package io.github.dunwu.common.dal.generator.config.po;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * 字典信息实体类。
 * <p>
 * 用于封装代码生成过程中使用的字典元数据，包含字典编码、名称、备注及其关联的字典选项列表。
 * 支持自动将字典编码格式化为驼峰命名（首字母大写），便于代码生成模板使用。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public class DictInfo {

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典备注/描述
     */
    private String note;

    /**
     * 格式化后的字典编码（通常为首字母大写的驼峰命名）
     */
    private String formatCode;

    /**
     * 该字典下的选项列表
     */
    private List<DictOptionInfo> options;

    /**
     * 默认构造方法
     */
    public DictInfo() { }

    /**
     * 构造方法
     *
     * @param code 字典编码
     * @param name 字典名称
     * @param note 字典备注
     */
    public DictInfo(String code, String name, String note) {
        this.code = code;
        this.name = name;
        this.note = note;
        this.formatCode = getFormatCode(code);
    }

    /**
     * 获取字典编码
     *
     * @return 字典编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字典编码
     * <p>
     * 同时会自动更新 {@link #formatCode}
     *
     * @param code 字典编码
     * @return 当前 DictInfo 实例
     */
    public DictInfo setCode(String code) {
        this.code = code;
        this.formatCode = getFormatCode(code);
        return this;
    }

    /**
     * 获取字典名称
     *
     * @return 字典名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字典名称
     *
     * @param name 字典名称
     * @return 当前 DictInfo 实例
     */
    public DictInfo setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 获取字典备注
     *
     * @return 字典备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置字典备注
     *
     * @param note 字典备注
     * @return 当前 DictInfo 实例
     */
    public DictInfo setNote(String note) {
        this.note = note;
        return this;
    }

    /**
     * 获取格式化后的字典编码
     *
     * @return 格式化后的编码
     */
    public String getFormatCode() {
        return formatCode;
    }

    /**
     * 获取字典选项列表
     *
     * @return 选项列表
     */
    public List<DictOptionInfo> getOptions() {
        return options;
    }

    /**
     * 设置字典选项列表
     *
     * @param options 选项列表
     * @return 当前 DictInfo 实例
     */
    public DictInfo setOptions(List<DictOptionInfo> options) {
        this.options = options;
        return this;
    }

    /**
     * 格式化字典编码
     * <p>
     * 将原始编码转换为驼峰命名，并将首字母大写。
     *
     * @param code 原始编码
     * @return 格式化后的编码，如果输入为空则返回 null
     */
    public static String getFormatCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        // 字典名称驼峰命名，首字母大写
        String formatCode = StrUtil.toCamelCase(code);
        return StrUtil.upperFirst(formatCode);
    }

}
