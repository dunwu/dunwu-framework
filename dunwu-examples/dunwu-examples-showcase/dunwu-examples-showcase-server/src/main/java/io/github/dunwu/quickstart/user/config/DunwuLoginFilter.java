package io.github.dunwu.quickstart.user.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.quickstart.user.dto.RoleDTO;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.web.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义登录处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Slf4j
public class DunwuLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final UserManager userManager;

	DunwuLoginFilter(String defaultFilterProcessesUrl, UserManager userManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
		this.userManager = userManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
		String requestBody = getRequestBody(request);
		Map<String, String> map = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>() {
		});
		String username = map.get("username");
		String password = map.get("password");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new AuthenticationServiceException("用户名或密码为空");
		}

		UserDTO userDTO = userManager.getByUsername(username);
		if (userDTO == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}

		if (!userDTO.getPassword().equals(password)) {
			throw new AuthenticationServiceException("密码错误");
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (RoleDTO role : userDTO.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	/**
	 * 获取请求体
	 */
	private String getRequestBody(HttpServletRequest request) throws AuthenticationException {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			InputStream inputStream = request.getInputStream();
			byte[] bs = new byte[StreamUtils.BUFFER_SIZE];
			int len;
			while ((len = inputStream.read(bs)) != -1) {
				stringBuilder.append(new String(bs, 0, len));
			}
			return stringBuilder.toString();
		}
		catch (IOException e) {
			log.error("get request body error.");
		}
		throw new AuthenticationServiceException("invalid request body");
	}

}
