package io.github.dunwu.quickstart.user.config;

import io.github.dunwu.quickstart.user.service.UserManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@ConditionalOnProperty(name = "dunwu.web.security.enable", havingValue = "true")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({ DunwuWebSecurityProperties.class })
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

	private final DunwuWebSecurityProperties properties;

	private final UserManager userManager;

	private final DunwuAccessDeniedHandler accessDeniedHandler;

	private final DunwuAuthenticationEntryPoint authenticationEntryPoint;

	private final DunwuAuthenticationFailureHandler authenticationFailureHandler;

	private final DunwuAuthenticationSuccessHandler authenticationSuccessHandler;

	private final DunwuLogoutSuccessHandler logoutSuccessHandler;

	/**
	 * 匹配 "/register" 路径，不需要权限即可访问 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限 登录地址为
	 * "/login"，登录成功返回响应状态码 退出登录的地址为 "/logout"，退出成功返回响应状态码 禁用 CSRF
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();

		http.authorizeRequests().anyRequest().authenticated().antMatchers(HttpMethod.OPTIONS, "/user/**").permitAll()
				.antMatchers(properties.getRegisterUrl(), properties.getLoginUrl(), properties.getLogoutUrl())
				.permitAll();

		http.logout().logoutUrl(properties.getLogoutUrl()).logoutSuccessHandler(logoutSuccessHandler)
				.clearAuthentication(true).permitAll();

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);

		http.addFilterAt(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * 自定义认证过滤器
	 */
	private DunwuLoginFilter customLoginFilter() {
		DunwuLoginFilter customLoginFilter = new DunwuLoginFilter(properties.getLoginUrl(), userManager);
		customLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		customLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		return customLoginFilter;
	}

}
