package io.github.dunwu.common.dal.generator.config.po;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;

/**
 * 表名匹配条件封装类。
 * <p>
 * 用于代码生成器中配置需要包含或排除的表名匹配规则，支持 SQL {@code LIKE} 语法。
 * 可指定匹配模式（前缀、后缀、全匹配等）。
 *
 * @author nieqiuqiu
 * @since 2019-11-26
 */
public class LikeTable {

    /**
     * 匹配表达式（表名模式）
     */
    private String value;

    /**
     * LIKE 匹配模式（如前缀匹配、后缀匹配等），默认为 {@link SqlLike#DEFAULT}
     */
    private SqlLike like = SqlLike.DEFAULT;

    /**
     * 构造方法
     *
     * @param value 匹配表达式
     */
    public LikeTable(String value) {
        this.value = value;
    }

    /**
     * 构造方法
     *
     * @param value 匹配表达式
     * @param like  LIKE 匹配模式
     */
    public LikeTable(String value, SqlLike like) {
        this.value = value;
        this.like = like;
    }

    /**
     * 获取匹配表达式字符串（同 {@link #getValue()}）
     *
     * @return 匹配表达式
     */
    @Override
    public String toString() {
        return getValue();
    }

    /**
     * 获取拼接后的 LIKE 表达式
     *
     * @return 格式化后的 LIKE 表达式字符串
     */
    public String getValue() {
        return SqlUtils.concatLike(this.value, like);
    }

}
