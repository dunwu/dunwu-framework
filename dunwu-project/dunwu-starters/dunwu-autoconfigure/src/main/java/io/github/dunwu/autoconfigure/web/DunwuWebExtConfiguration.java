package io.github.dunwu.autoconfigure.web;

import io.github.dunwu.web.converter.DateConverter;
import io.github.dunwu.web.filter.XssFilter;
import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.HashMap;
import java.util.Map;

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
@Import(DunwuSwaggerConfiguration.class)
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // ------------------------------------------------------------------------------------
    // 注册过滤器、拦截器
    // ------------------------------------------------------------------------------------

    /**
     * 根据配置，选择性注入应用所需的拦截器 {@link HandlerInterceptorAdapter}
     *
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (properties.isHttpDebugEnabled()) {
            // 使用日志 DEBUG 级别打印 HTTP 请求、应答信息的拦截器
            registry.addInterceptor(new HttpDebugInterceptor()).addPathPatterns("/**").order(2);
        }
    }

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

    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
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
