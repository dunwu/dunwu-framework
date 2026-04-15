package io.github.dunwu.common.dal.elasticsearch.annotation;

import io.github.dunwu.common.dal.constant.NamingStrategy;
import io.github.dunwu.common.dal.constant.OrderType;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Elasticsearch 查询文档注解
 * <p>
 * 用于标记 Elasticsearch 查询条件类，指定字段命名策略及默认排序规则。
 * 配合 {@link QueryField} 使用，可自动将 Java 对象转换为 ES 查询条件。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface QueryDocument {

    /**
     * 获取字段命名策略
     * <p>
     * 用于将 Java 字段名转换为 Elasticsearch 文档中的字段名。
     *
     * @return 命名策略，默认为 {@link NamingStrategy#LOWER_UNDERLINE}
     */
    NamingStrategy namingStrategy() default NamingStrategy.LOWER_UNDERLINE;

    /**
     * 获取排序字段列表
     * <p>
     * 指定查询结果的排序字段。
     *
     * @return 排序字段名数组
     */
    String[] orderItem() default {};

    /**
     * 获取排序方向
     * <p>
     * 指定查询结果的排序方向（升序或降序）。
     *
     * @return 排序方向，默认为 {@link OrderType#ASC}
     */
    OrderType orderType() default OrderType.ASC;

}
