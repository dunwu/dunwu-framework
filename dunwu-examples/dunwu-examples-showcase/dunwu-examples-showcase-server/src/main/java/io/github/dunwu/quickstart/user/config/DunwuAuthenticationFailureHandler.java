package io.github.dunwu.quickstart.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Component
public class DunwuAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final ObjectMapper objectMapper;

	public DunwuAuthenticationFailureHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException exception) throws IOException, ServletException {
		BaseResult baseResult = ResultUtil.failBaseResult(AppCode.ERROR_AUTHENTICATION.getCode(),
				exception.getMessage());
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(baseResult));
	}

}
