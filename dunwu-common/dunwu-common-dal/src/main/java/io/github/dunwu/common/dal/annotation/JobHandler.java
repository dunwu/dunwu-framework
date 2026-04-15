package io.github.dunwu.common.dal.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 任务处理器标记注解
 * <p>
 * 用于标记定时任务或后台任务的处理器类，使其能被 Spring 容器扫描并注册为 Bean。
 * 通常配合任务调度框架使用，通过指定执行方法和参数类型来动态调用任务逻辑。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface JobHandler {

    /**
     * Bean 名称
     * <p>
     * 默认为空，Spring 将使用类名首字母小写作为 Bean 名称
     *
     * @return Bean 名称
     */
    @AliasFor("beanName")
    String value() default "";

    /**
     * Bean 名称
     * <p>
     * 与 {@link #value()} 互为别名
     *
     * @return Bean 名称
     */
    @AliasFor("value")
    String beanName() default "";

    /**
     * 执行方法名
     * <p>
     * 任务触发时调用的目标方法名称
     *
     * @return 方法名，默认为 "execute"
     */
    String executeMethod() default "execute";

    /**
     * 执行方法参数类型
     * <p>
     * 用于匹配目标方法的参数签名
     *
     * @return 参数类型数组
     */
    Class<?>[] paramTypes() default {};

}
