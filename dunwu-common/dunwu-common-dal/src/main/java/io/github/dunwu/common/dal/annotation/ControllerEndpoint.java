package io.github.dunwu.common.dal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller 端点注解
 * <p>
 * 用于标记 Controller 方法，通常配合 AOP 进行日志记录或异常处理。
 *
 * @author MrBird
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerEndpoint {

    /**
     * 操作描述
     *
     * @return 操作名称或描述信息
     */
    String operation() default "";

    /**
     * 异常提示信息
     * <p>
     * 当方法抛出异常时，用于返回给用户的提示
     *
     * @return 异常提示信息
     */
    String exceptionMessage() default "系统内部异常";

}
