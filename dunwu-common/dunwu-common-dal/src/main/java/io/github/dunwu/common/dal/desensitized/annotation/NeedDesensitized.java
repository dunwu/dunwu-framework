package io.github.dunwu.common.dal.desensitized.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏标记注解
 * <p>
 * 用于标记需要进行数据脱敏处理的类或方法。
 * 只有被该注解标记的类，其内部被 {@link Desensitized} 标记的字段才会执行脱敏序列化逻辑。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedDesensitized {
}
