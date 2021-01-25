package io.github.dunwu.autoconfigure.web;

import cn.hutool.core.map.MapUtil;
import io.github.dunwu.web.converter.*;
import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import io.github.dunwu.web.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
@EnableConfigurationProperties(DunwuWebProperties.class)
@ServletComponentScan(basePackages = "io.github.dunwu")
public class DunwuWebMvcConfiguration implements WebMvcConfigurer {

    private final DunwuWebProperties properties;

    public DunwuWebMvcConfiguration(DunwuWebProperties properties) {
        this.properties = properties;
    }

    /**
     * http 数据自动格式化
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        if (properties.isFormatEnabled()) {
            registry.addConverter(new DateConverter());
            registry.addConverter(new DateTimeConverter());
            registry.addConverter(new LocalDateConverter());
            registry.addConverter(new LocalDateTimeConverter());
        }
    }

    /**
     * 根据配置，选择性注入应用所需的拦截器 {@link HandlerInterceptorAdapter}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (properties.isHttpDebugEnabled()) {
            // 使用日志 DEBUG 级别打印 HTTP 请求、应答信息的拦截器
            registry.addInterceptor(new HttpDebugInterceptor())
                .addPathPatterns(properties.getDebugPath())
                .order(2);
        }
    }

    /**
     * 资源处理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<String, String> resources = properties.getResources();
        if (MapUtil.isEmpty(resources)) {
            return;
        }

        resources.forEach((k, v) -> {
            registry.addResourceHandler(k).addResourceLocations(v).setCachePeriod(0);
        });
    }

    @Bean
    @ConditionalOnProperty(name = "dunwu.web.websocketEnabled", havingValue = "true")
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    // ------------------------------------------------------------------------------------
    // Web 常用工具
    // ------------------------------------------------------------------------------------

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
