package io.github.dunwu.autoconfigure.web;

import io.github.dunwu.web.converter.DateConverter;
import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Servlet;

/**
 * Web Mvc 配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ DunwuWebProperties.class })
// @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
// @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
// @ConditionalOnProperty(value = "dunwu.web.enable", havingValue = "true", matchIfMissing = true)
// @AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ServletComponentScan(basePackages = "io.github.dunwu")
public class DunwuWebExtConfiguration implements WebMvcConfigurer {

    private final DunwuWebProperties properties;

    public DunwuWebExtConfiguration(DunwuWebProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        if (properties.isFormatEnabled()) {
            registry.addConverter(new DateConverter());
        }
    }

    /**
     * 注入拦截器
     *
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (properties.isHttpDebugEnabled()) {
            registry.addInterceptor(new HttpDebugInterceptor()).addPathPatterns("/**").order(1);
        }
    }

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("swagger-ui.html")
    //         .addResourceLocations("classpath:/META-INF/resources/");
    //     registry.addResourceHandler("/webjars/**")
    //         .addResourceLocations("classpath:/META-INF/resources/webjars/");
    // }

    /**
     * 设置跨域过滤器
     */
    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.corsEnabled", havingValue = "true")
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(properties.getSecurity().getCorsPath(), corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 实例化 RestTemplate
     *
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
