package io.github.dunwu.config;

import io.github.dunwu.web.interceptor.RequestInterceptor;
import io.github.dunwu.web.resolver.JsonHandlerExceptionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see
 * <a href="https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Configuration
@EnableWebMvc
@ServletComponentScan(basePackages = "io.github.dunwu.web")
public class DunwuWebConfiguration implements WebMvcConfigurer {

    private final MessageSource messageSource;
    private final DunwuWebProperties dunwuWebProperties;

    @Autowired
    public DunwuWebConfiguration(MessageSource messageSource, DunwuWebProperties dunwuWebProperties) {
        Locale.setDefault(Locale.ROOT);
        this.messageSource = messageSource;
        this.dunwuWebProperties = dunwuWebProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/lib/swagger-ui/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 注入拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (dunwuWebProperties.getRequest()) {
            registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
        }
    }

    @Bean
    public JsonHandlerExceptionResolver jsonHandlerExceptionResolver() {
        return new JsonHandlerExceptionResolver(messageSource);
    }
}
