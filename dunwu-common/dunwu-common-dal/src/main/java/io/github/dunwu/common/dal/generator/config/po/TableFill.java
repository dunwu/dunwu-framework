package io.github.dunwu.common.dal.generator.config.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Data;

/**
 * 字段自动填充配置实体类。
 * <p>
 * 用于配置 MyBatis-Plus 的字段自动填充功能，例如自动填充创建时间、更新时间等。
 * 配合 {@link com.baomidou.mybatisplus.annotation.FieldFill} 枚举使用。
 *
 * @author hubin
 * @since 2017-06-26
 */
@Data
public class TableFill {

    /**
     * 需要自动填充的字段名称
     */
    private String fieldName;

    /**
     * 字段填充策略
     * <p>
     * 例如：{@link FieldFill#INSERT}（插入时填充）、{@link FieldFill#UPDATE}（更新时填充）、{@link FieldFill#INSERT_UPDATE}（插入和更新时填充）
     */
    private FieldFill fieldFill;

    /**
     * 构造方法
     *
     * @param fieldName 字段名称
     * @param fieldFill 填充策略
     */
    public TableFill(String fieldName, FieldFill fieldFill) {
        this.fieldName = fieldName;
        this.fieldFill = fieldFill;
    }

}
