package io.github.dunwu.common.log.annotation;

import io.github.dunwu.common.log.constant.OperationType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperationLog {

    /** 业务编号，支持 SpEL 表达式 */
    String bizNo() default "";

    /** 业务类型 */
    String bizType() default "";

    /** 更新实体，支持 SpEL 表达式 */
    String entity() default "";

    /** 操作类型 */
    OperationType operation() default OperationType.OTHER;

    /** 成功信息 SpEL 模板 */
    String success() default "";

    /** 失败信息 SpEL 模板 */
    String fail() default "";

    /** 详情信息 SpEL 模板 */
    String detail() default "";

    /** 操作者 ID SpEL 模板 */
    String operatorId() default "";

    /** 操作者名 SpEL 模板 */
    String operatorName() default "";

    /** 日志记录条件 SpEL 模板 */
    String condition() default "";

}
