package io.github.dunwu.config;

import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import io.github.dunwu.web.interceptor.SecurityInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
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
@AllArgsConstructor
@ServletComponentScan(basePackages = "io.github.dunwu.web")
@EnableConfigurationProperties({DunwuWebProperties.class, DunwuWebSecurityProperties.class})
public class DunwuWebMvcConfiguration implements WebMvcConfigurer {

    private final DunwuWebProperties dunwuWebProperties;
    private final DunwuWebSecurityProperties dunwuSecurityProperties;

    @Override
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

        //        if (dunwuWebProperties.getCorsEnable()) {
        //            registry.addInterceptor(new CorsInceptor())
        //                    .addPathPatterns("/**")
        //                    .order(2);
        //        }

        if (dunwuSecurityProperties.getEnable()) {
            registry.addInterceptor(new SecurityInterceptor(dunwuSecurityProperties))
                    .excludePathPatterns("/user/login")
                    .excludePathPatterns("/user/logout")
                    .addPathPatterns("/**");
        }
    }
}
