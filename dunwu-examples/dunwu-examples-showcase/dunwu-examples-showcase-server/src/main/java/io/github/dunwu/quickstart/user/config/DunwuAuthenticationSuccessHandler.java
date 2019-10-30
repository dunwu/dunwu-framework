package io.github.dunwu.quickstart.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证成功处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Component
public class DunwuAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper objectMapper;

	public DunwuAuthenticationSuccessHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Authentication authentication)
		throws IOException, ServletException {
		List<String> roles = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		DataListResult result = ResultUtil.successDataListResult(roles);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
	}

}
