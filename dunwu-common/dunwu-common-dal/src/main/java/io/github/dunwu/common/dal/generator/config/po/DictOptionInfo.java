package io.github.dunwu.common.dal.generator.config.po;

import cn.hutool.core.util.StrUtil;

/**
 * 字典选项信息实体类。
 * <p>
 * 用于封装字典中单个选项的详细信息，包括选项编码、名称、存储值及备注。
 * 支持自动生成格式化的选项编码（大写下划线格式），便于代码生成模板使用。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public class DictOptionInfo {

    /**
     * 字典选项编码
     */
    private String code;

    /**
     * 字典选项名称
     */
    private String name;

    /**
     * 字典选项值（通常用于存储到数据库的实际值）
     */
    private String value;

    /**
     * 字典选项备注/描述
     */
    private String note;

    /**
     * 格式化后的字典选项编码
     * <p>
     * 通常由字典编码和选项编码组合生成的大写下划线格式
     */
    private String formatCode;

    /**
     * 默认构造方法
     */
    public DictOptionInfo() { }

    /**
     * 构造方法
     *
     * @param code 选项编码
     * @param name 选项名称
     * @param value 选项值
     * @param note 选项备注
     */
    public DictOptionInfo(String code, String name, String value, String note) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.note = note;
    }

    /**
     * 获取选项编码
     *
     * @return 选项编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置选项编码
     *
     * @param code 选项编码
     * @return 当前 DictOptionInfo 实例
     */
    public DictOptionInfo setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * 获取选项名称
     *
     * @return 选项名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置选项名称
     *
     * @param name 选项名称
     * @return 当前 DictOptionInfo 实例
     */
    public DictOptionInfo setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 获取选项值
     *
     * @return 选项值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置选项值
     *
     * @param value 选项值
     * @return 当前 DictOptionInfo 实例
     */
    public DictOptionInfo setValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * 获取选项备注
     *
     * @return 选项备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置选项备注
     *
     * @param note 选项备注
     * @return 当前 DictOptionInfo 实例
     */
    public DictOptionInfo setNote(String note) {
        this.note = note;
        return this;
    }

    /**
     * 获取格式化后的选项编码
     *
     * @return 格式化编码
     */
    public String getFormatCode() {
        return formatCode;
    }

    /**
     * 设置格式化后的选项编码
     *
     * @param formatCode 格式化编码
     * @return 当前 DictOptionInfo 实例
     */
    public DictOptionInfo setFormatCode(String formatCode) {
        this.formatCode = formatCode;
        return this;
    }

    /**
     * 生成格式化的字典选项编码
     * <p>
     * 将字典编码和选项编码转换为大写下划线格式并拼接。
     * 例如：dictCode="userType", optionCode="admin" -> "USER_TYPE_ADMIN"
     *
     * @param dictCode 字典编码
     * @param dictOptionCode 选项编码
     * @return 格式化后的完整选项编码
     */
    public static String getFormatCode(String dictCode, String dictOptionCode) {
        String dictUnderlineName = StrUtil.toUnderlineCase(dictCode).toUpperCase();
        String dictOptionUnderlineName = StrUtil.toUnderlineCase(dictOptionCode).toUpperCase();
        if (!dictOptionUnderlineName.contains(dictUnderlineName)) {
            dictOptionUnderlineName = dictUnderlineName + "_" + dictOptionUnderlineName;
        }
        return dictOptionUnderlineName;
    }

}
