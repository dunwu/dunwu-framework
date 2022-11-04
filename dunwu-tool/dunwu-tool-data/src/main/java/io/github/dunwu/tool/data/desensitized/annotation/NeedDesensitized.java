package io.github.dunwu.tool.data.desensitized.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏标记注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-04
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedDesensitized {
}
