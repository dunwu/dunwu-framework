package io.github.dunwu.common.dal.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Dao 标记注解
 * <p>
 * 用于标记数据访问层（DAO）组件，使其能被 Spring 容器扫描并注册为 Bean。
 * 该注解是 {@link Component} 的特化形式。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Dao {

    /**
     * Bean 名称
     * <p>
     * 默认为空，Spring 将使用类名首字母小写作为 Bean 名称
     *
     * @return Bean 名称
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

}
