package io.github.dunwu.config;

import io.github.dunwu.web.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see
 * <a href="https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Configuration
@EnableWebMvc
public class AbstractWebConfig implements WebMvcConfigurer {

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // @formatter:off
        registry.addMapping("/api/**")
            .allowedOrigins("https://domain2.com")
            .allowedMethods("PUT", "DELETE")
            .allowedHeaders("header1", "header2", "header3")
            .exposedHeaders("header1", "header2")
            .allowCredentials(true).maxAge(3600);
        // @formatter:on
        // Add more mappings...
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }
}
