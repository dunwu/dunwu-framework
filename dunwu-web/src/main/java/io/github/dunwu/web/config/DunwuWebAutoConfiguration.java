package io.github.dunwu.web.config;

import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Web Mvc 配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see
 * <a href="https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Configuration
@EnableWebMvc
@ConditionalOnProperty(prefix = "dunwu.web", value = "enabled", matchIfMissing = true)
@ServletComponentScan(basePackages = "io.github.dunwu.web")
@EnableConfigurationProperties({DunwuWebProperties.class, DunwuWebSecurityProperties.class})
@Import(value = {DunwuMailExtAutoConfiguration.class})
public class DunwuWebAutoConfiguration implements WebMvcConfigurer {

    private final DunwuWebProperties dunwuWebProperties;
    private final DunwuWebSecurityProperties dunwuSecurityProperties;

    public DunwuWebAutoConfiguration(DunwuWebProperties dunwuWebProperties,
                                     DunwuWebSecurityProperties dunwuSecurityProperties) {
        this.dunwuWebProperties = dunwuWebProperties;
        this.dunwuSecurityProperties = dunwuSecurityProperties;
    }

    @Override
    @Profile({"dev", "test"})
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/lib/swagger-ui/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 设置跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (dunwuWebProperties.getCorsEnable()) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600L);
        }
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        if (dunwuWebProperties.getFormatEnable()) {
            registry.addConverter(new DateConverter());
        }
    }

    /**
     * 注入拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (dunwuWebProperties.getHttpDebugEnable()) {
            registry.addInterceptor(new HttpDebugInterceptor())
                    .addPathPatterns("/**")
                    .order(1);
        }

        //        if (dunwuSecurityProperties.getEnable()) {
        //            registry.addInterceptor(new SecurityInterceptor(dunwuSecurityProperties))
        //                    .excludePathPatterns("/user/login")
        //                    .excludePathPatterns("/user/logout")
        //                    .addPathPatterns("/**");
        //        }
    }
}
