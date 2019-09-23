package io.github.dunwu.autoconfigure.web;

import io.github.dunwu.web.converter.DateConverter;
import io.github.dunwu.web.interceptor.HttpDebugInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Mvc 配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href=
 * "https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config">mvc-config</a>
 * @since 2019-04-21
 */
@Configuration
@EnableWebMvc
@ServletComponentScan(basePackages = "io.github.dunwu.web")
@EnableConfigurationProperties({ DunwuWebProperties.class,
		DunwuWebSecurityProperties.class })
public class DunwuWebAutoConfiguration implements WebMvcConfigurer {

	private final DunwuWebProperties dunwuWebProperties;

	private final DunwuWebSecurityProperties dunwuSecurityProperties;

	public DunwuWebAutoConfiguration(DunwuWebProperties dunwuWebProperties,
			DunwuWebSecurityProperties dunwuSecurityProperties) {
		this.dunwuWebProperties = dunwuWebProperties;
		this.dunwuSecurityProperties = dunwuSecurityProperties;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * 设置跨域过滤器
	 */
	@Bean
	@ConditionalOnProperty(name = "dunwu.web.corsEnable", havingValue = "true")
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(source);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		if (dunwuWebProperties.getFormatEnable()) {
			registry.addConverter(new DateConverter());
		}
	}

	/**
	 * 注入拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (dunwuWebProperties.getHttpDebugEnable()) {
			registry.addInterceptor(new HttpDebugInterceptor()).addPathPatterns("/**")
					.order(1);
		}
	}

}
