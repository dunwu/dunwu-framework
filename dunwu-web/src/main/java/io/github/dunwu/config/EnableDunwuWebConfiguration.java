package io.github.dunwu.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * dunwu-web 自动化配置开关
 * <p>
 * 在项目中使用 @EnableDunwuWebConfiguration 即可开启 dunwu-web 功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see DunwuWebAutoConfiguration
 * @since 2019-06-15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DunwuWebAutoConfiguration.class})
public @interface EnableDunwuWebConfiguration {}
