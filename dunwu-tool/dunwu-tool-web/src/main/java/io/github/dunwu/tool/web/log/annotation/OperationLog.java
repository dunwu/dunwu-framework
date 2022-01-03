package io.github.dunwu.tool.web.log.annotation;

import io.github.dunwu.tool.web.log.constant.OperationType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperationLog {

    /** 业务 NO */
    String bizNo() default "";

    /** 业务类型 */
    String bizType() default "";

    /** 操作类型 */
    OperationType operation() default OperationType.OTHER;

    /** 成功信息 SpEl 模板 */
    String success() default "";

    /** 失败信息 SpEl 模板 */
    String fail() default "";

    /** 详情信息 SpEl 模板 */
    String detail() default "";

    /** 操作者ID SpEl 模板 */
    String operatorId() default "";

    /** 操作者名 SpEl 模板 */
    String operatorName() default "";

    /** 日志记录条件 SpEl 模板 */
    String condition() default "";

}
