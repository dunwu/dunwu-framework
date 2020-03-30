package io.github.dunwu.admin.system.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.admin.exception.CheckCodeException;
import io.github.dunwu.admin.system.dto.UserDTO;
import io.github.dunwu.admin.system.service.UserManager;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.web.util.ServletUtil;
import io.github.dunwu.web.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
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
    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;

    public DunwuAuthenticationFilter(String processesUrl, UserManager userManager,
        PasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(processesUrl, HttpMethod.POST.name()));
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        String requestBody = ServletUtil.getRequestBody(request);
        Map<String, String> map = objectMapper.readValue(requestBody,
            new TypeReference<Map<String, String>>() {
            });
        String username = map.get("username");
        String password = map.get("password");
        String checkCode = map.get("checkCode");

        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        LocalDateTime expireTime = (LocalDateTime) session.getAttribute("expireTime");
        validateCheckCode(checkCode, code, expireTime);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            log.error("认证失败，用户名或密码为空");
            throw new AuthenticationServiceException("用户名或密码为空");
        }

        username = username.trim();
        UserDTO userDTO = userManager.loadUserByUniqueKey(username);
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

        if (StringUtil.isBlank(checkCode)) {
            throw new CheckCodeException("验证码不能为空！");
        }

        if (StringUtil.isBlank(actualCheckCode)) {
            throw new CheckCodeException("验证码不存在！");
        }

        if (expireTime == null || expireTime.isBefore(LocalDateTime.now())) {
            throw new CheckCodeException("验证码已过期！");
        }

        if (!actualCheckCode.equalsIgnoreCase(checkCode)) {
            throw new CheckCodeException("验证码不正确！");
        }
    }

}
