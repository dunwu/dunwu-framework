package io.github.dunwu.data.elasticsearch;

import io.github.dunwu.data.OrderType;
import io.github.dunwu.tool.bean.support.NamingStrategy;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

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
