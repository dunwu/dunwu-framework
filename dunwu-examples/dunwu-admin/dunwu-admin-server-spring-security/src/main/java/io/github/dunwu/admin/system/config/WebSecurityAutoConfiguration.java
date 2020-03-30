package io.github.dunwu.admin.system.config;

import io.github.dunwu.admin.system.filter.DunwuAuthenticationFilter;
import io.github.dunwu.admin.system.handler.*;
import io.github.dunwu.admin.system.service.UserManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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
    private final DataSource dataSource;
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
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .cors()  // 允许跨域
            .and().csrf().disable() // 关闭 CSRF

            // 认证管理
            .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginProcessingUrl(properties.getLoginUrl())
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)

            // RememberMe 管理
            .and().rememberMe()
            .rememberMeServices(rememberMeServices())

            // 授权管理
            .and().authorizeRequests()
            .antMatchers("/user/register", "/checkcode/image").permitAll() // 无需认证的请求路径
            .anyRequest().authenticated() // 所有请求都需要认证

            // 登出管理
            .and().logout()
            .logoutUrl(properties.getLogoutUrl()) // 登出 URL
            .logoutSuccessHandler(logoutSuccessHandler) // 注入登出成功处理器
            .deleteCookies("JESSSIONID")
            .clearAuthentication(true) // 登出成功后清理认证信息
            .permitAll() // 关闭 CSRFRequestGlobalHandler

            // 会话管理
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

            // 异常管理
            .and().exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint) // 注入认证入口（未登录状态）
            .accessDeniedHandler(accessDeniedHandler); // 访问拒绝处理
    }

    /**
     * 自定义认证过滤器
     */
    private DunwuAuthenticationFilter authenticationFilter() {
        DunwuAuthenticationFilter filter = new DunwuAuthenticationFilter(properties.getLoginUrl(),
            userManager, passwordEncoder());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler); // 注入认证失败处理器
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler); // 注入认证成功处理器
        return filter;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
        PersistentTokenBasedRememberMeServices rememberMeServices =
            new PersistentTokenBasedRememberMeServices("rememberme", userManager, persistentTokenRepository());
        // rememberMeServices.setCookieName("rememberme");
        rememberMeServices.setParameter("rememberme");
        rememberMeServices.setTokenValiditySeconds(60);
        return rememberMeServices;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
