package io.github.dunwu.common.dal.elasticsearch.annotation;

import io.github.dunwu.common.dal.constant.QueryJudgeType;

import java.lang.annotation.*;

/**
 * Elasticsearch 查询字段注解
 * <p>
 * 用于标记查询条件类中的字段，指定其在 Elasticsearch 查询中对应的字段名及判断类型（如等于、模糊匹配等）。
 * 需配合类级别的 {@link QueryDocument} 注解使用。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {

    /**
     * 获取 Elasticsearch 文档中的字段名
     * <p>
     * 如果为空，则根据 {@link QueryDocument} 中的命名策略自动转换 Java 字段名。
     *
     * @return 文档字段名
     */
    String value() default "";

    /**
     * 获取查询判断类型
     * <p>
     * 指定该字段在构建查询时使用的操作符，例如等于、模糊匹配等。
     *
     * @return 判断类型，默认为 {@link QueryJudgeType#EQUALS}
     */
    QueryJudgeType judgeType() default QueryJudgeType.EQUALS;

}
