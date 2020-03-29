package io.github.dunwu.admin.system.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.admin.system.dto.UserDTO;
import io.github.dunwu.admin.system.service.UserManager;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义认证处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
public class DunwuAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserManager userManager;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    DunwuAuthenticationProcessingFilter(String defaultFilterProcessesUrl, UserManager userManager,
        BCryptPasswordEncoder cryptPasswordEncoder) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
        this.userManager = userManager;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        String requestBody = getRequestBody(request);
        Map<String, String> map = objectMapper.readValue(requestBody,
            new TypeReference<Map<String, String>>() {
            });
        String username = map.get("username");
        String password = map.get("password");

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

        if (!cryptPasswordEncoder.matches(password, userDTO.getPassword())) {
            log.error("认证失败，密码不匹配用户 {} ", username);
            throw new AuthenticationServiceException("密码不匹配");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDTO.getAuthorities());
    }
    
    /**
     * 获取请求体
     */
    private String getRequestBody(HttpServletRequest request)
        throws AuthenticationException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = request.getInputStream();
            byte[] bs = new byte[StreamUtils.BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                stringBuilder.append(new String(bs, 0, len));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error("get request body error.");
        }
        throw new AuthenticationServiceException("invalid request body");
    }

}
