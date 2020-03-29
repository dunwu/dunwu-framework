package io.github.dunwu.admin.system.config;

import io.github.dunwu.admin.system.service.UserManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Configuration
@ConditionalOnProperty(name = "dunwu.web.security.enable", havingValue = "true")
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
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
     * 匹配 "/register" 路径，不需要权限即可访问
     * <p>
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * <p>
     * 登录地址为 "/login"，登录成功返回响应状态码
     * <p>
     * 退出登录的地址为"/logout"，退出成功返回响应状态码
     * <p>
     * 禁用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 注入自定义认证处理器
        http.addFilterAt(authenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginProcessingUrl(properties.getLoginUrl()) // 登录 URL
            .permitAll()
            .and()
            .authorizeRequests() // 授权（权限控制）配置
            .anyRequest()
            .permitAll()
            .and()
            .exceptionHandling()  // 异常处理配置
            .authenticationEntryPoint(authenticationEntryPoint) // 注入认证入口（未登录状态）
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout() // 登出配置
            .logoutUrl(properties.getLogoutUrl()) // 登出 URL
            .logoutSuccessHandler(logoutSuccessHandler) // 注入登出成功处理器
            .clearAuthentication(true) // 登出成功后清理认证信息
            .permitAll()
            .and().cors()  // 允许跨域
            .and().csrf().disable(); // 关闭 CSRFRequestGlobalHandler
    }

    /**
     * 自定义认证过滤器
     */
    private DunwuAuthenticationProcessingFilter authenticationProcessingFilter() {
        DunwuAuthenticationProcessingFilter filter = new DunwuAuthenticationProcessingFilter(properties.getLoginUrl(),
            userManager, new BCryptPasswordEncoder());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler); // 注入认证失败处理器
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler); // 注入认证成功处理器
        return filter;
    }

}
