package io.github.dunwu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * web 服务自动化配置集合
 * <p>
 * 在项目中使用 @EnableDunwuWebConfiguration 后，会自动加载 DunwuWebConfiguration
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see EnableDunwuWebConfiguration
 * @since 2019-06-16
 */
@Configuration
@Import({DunwuWebMvcConfiguration.class})
public class DunwuWebAutoConfiguration {}
