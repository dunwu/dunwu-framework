package io.github.dunwu.common.dal.annotation;

import io.github.dunwu.common.dal.constant.NamingStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 查询字段注解
 * <p>
 * 用于标记实体类中的字段，指示其在动态查询中的行为，如查询类型、字段名映射等。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryField {

    /**
     * 数据库字段名
     * <p>
     * 默认为空，表示使用 Java 字段名并根据 {@link #namingStrategy()} 进行转换
     *
     * @return 数据库字段名
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 数据库字段名
     * <p>
     * 与 {@link #value()} 互为别名
     *
     * @return 数据库字段名
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 模糊查询字段列表
     * <p>
     * 当 {@link #type()} 为 LIKE 时，可以指定多个字段进行 OR 模糊匹配
     *
     * @return 模糊查询字段名数组
     */
    String[] blurry() default {};

    /**
     * 查询类型
     * <p>
     * 指定该字段在查询时的操作符，如等于、大于、模糊匹配等
     *
     * @return 查询类型，默认为 {@link QueryType#EQUALS}
     */
    QueryType type() default QueryType.EQUALS;

    /**
     * 关联表名
     * <p>
     * 用于多表关联查询时指定字段所属的表别名
     *
     * @return 关联表名
     */
    String joinName() default "";

    /**
     * 是否允许为空
     * <p>
     * 如果为 false，当该字段值为 null 时将不参与查询条件构建
     *
     * @return 是否允许为空，默认为 false
     */
    boolean nullable() default false;

    /**
     * 命名策略
     * <p>
     * 用于将 Java 字段名转换为数据库字段名
     *
     * @return 命名策略，默认为 {@link NamingStrategy#LOWER_UNDERLINE}
     */
    NamingStrategy namingStrategy() default NamingStrategy.LOWER_UNDERLINE;

    /**
     * 查询类型枚举
     */
    enum QueryType {
        /** 等于 */
        EQUALS,
        /** 模糊匹配 */
        LIKE,
        /** 不匹配 */
        NOT_LIKE,
        /** 左模糊匹配 */
        LIKE_LEFT,
        /** 右模糊匹配 */
        LIKE_RIGHT,
        /** 小于 */
        LT,
        /** 小于等于 */
        LE,
        /** 大于 */
        GT,
        /** 大于等于 */
        GE,
        /** 不等于 */
        NE,
        /** 在集合中 */
        IN,
        /** 不在集合中 */
        NOT_IN,
        /** 区间查询 */
        BETWEEN,
        /** 不为空 */
        NOT_NULL
    }

}
