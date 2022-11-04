package io.github.dunwu.tool.data.desensitized.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.dunwu.tool.data.desensitized.DesensitizedSerializer;
import io.github.dunwu.tool.data.desensitized.constant.SensitiveTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏注解
 * <p>
 * 注：需要配合 {@link NeedDesensitized} 使用，只有被标记 {@link NeedDesensitized} 的类，脱敏工具 {@link DesensitizedSerializer}
 * 才会去扫描是否存在被标记为 {@link Desensitized} 的字段
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-04
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // 表示自定义自己的注解PrivacyEncrypt
@JsonSerialize(using = DesensitizedSerializer.class) // 该注解使用序列化的方式
public @interface Desensitized {

    /**
     * 脱敏类型(规则)
     */
    SensitiveTypeEnum type() default SensitiveTypeEnum.NONE;

}
