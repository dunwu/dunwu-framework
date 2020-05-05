package io.github.dunwu.system.filter;

import io.github.dunwu.exception.CheckCodeException;
import io.github.dunwu.security.service.AuthService;
import io.github.dunwu.system.dto.UserDTO;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.web.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义认证处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
public class DunwuAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public DunwuAuthenticationFilter(String processesUrl, AuthService authService,
        PasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(processesUrl, HttpMethod.POST.name()));
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                "Authentication method not supported: " + request.getMethod());
        }

        Map<String, String> map = ServletUtil.getReqParam(request);
        String username = map.get("username");
        String password = map.get("password");
        String checkCode = map.get("checkCode");

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        LocalDateTime expireTime = (LocalDateTime) session.getAttribute("expireTime");
        validateCheckCode(checkCode, code, expireTime);

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            log.error("认证失败，用户名或密码为空");
            throw new AuthenticationServiceException("用户名或密码为空");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        username = username.trim();
        UserDTO userDTO = authService.loadUserByUniqueKey(username);
        if (userDTO == null) {
            log.error("认证失败，用户 {} 不存在", username);
            throw new UsernameNotFoundException("用户名不存在");
        }

        if (!passwordEncoder.matches(password, userDTO.getPassword())) {
            log.error("认证失败，密码不匹配用户 {} ", username);
            throw new AuthenticationServiceException("密码不匹配");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDTO.getAuthorities());
    }

    private void validateCheckCode(String checkCode, String actualCheckCode, LocalDateTime expireTime)
        throws CheckCodeException {

        if (StrUtil.isBlank(checkCode)) {
            throw new CheckCodeException("验证码不能为空！");
        }

        if (StrUtil.isBlank(actualCheckCode)) {
            throw new CheckCodeException("验证码不存在！");
        }

        if (expireTime == null || expireTime.isBefore(LocalDateTime.now())) {
            throw new CheckCodeException("验证码已过期！");
        }

        if (!actualCheckCode.equalsIgnoreCase(checkCode)) {
            throw new CheckCodeException("验证码不正确！");
        }
    }

    protected void setDetails(HttpServletRequest request,
        UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
