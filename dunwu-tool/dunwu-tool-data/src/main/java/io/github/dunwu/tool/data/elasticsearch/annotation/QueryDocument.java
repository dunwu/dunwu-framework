package io.github.dunwu.tool.data.elasticsearch.annotation;

import io.github.dunwu.tool.bean.support.NamingStrategy;
import io.github.dunwu.tool.data.constant.OrderType;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ElasticSearch 查询注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface QueryDocument {

    NamingStrategy namingStrategy() default NamingStrategy.LOWER_UNDERLINE;

    String[] orderItem() default {};

    OrderType orderType() default OrderType.ASC;

}
