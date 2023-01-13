package io.github.dunwu.tool.web.log.annotation;

import io.github.dunwu.tool.web.log.constant.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataLockLog {

    /**
     * 锁定的表名
     */
    String tableName();

    /**
     * 操作类型
     */
    OperationType operation() default OperationType.OTHER;

    /**
     * 实体（SpEL表达式）
     */
    String entity();

    /**
     * 主键关键字名
     */
    String id() default "id";

    /**
     * 根据 ID 批量查询数据的方法名，例如：io.github.dunwu.tool.data.mybatis.IDao#listByIds
     */
    String queryMethod();

}
