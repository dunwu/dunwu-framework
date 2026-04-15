package io.github.dunwu.common.dal.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 查询表注解
 * <p>
 * 用于标记查询 DTO 或实体类，指定其关联的数据库实体类型。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryTable {

    /**
     * 关联的实体类型
     * <p>
     * 用于指定该查询类对应的数据库实体
     *
     * @return 实体 Class 对象
     */
    @AliasFor("entity")
    Class<?> value() default Object.class;

    /**
     * 关联的实体类型
     * <p>
     * 与 {@link #value()} 互为别名
     *
     * @return 实体 Class 对象
     */
    @AliasFor("value")
    Class<?> entity() default Object.class;

}
