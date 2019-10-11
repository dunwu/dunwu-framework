package io.github.dunwu.quickstart.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 拒绝访问处理器（登录状态下，无权限会触发）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Component
public class DunwuAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	public DunwuAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {
		BaseResult result = ResultUtil.failBaseResult(AppCode.UNAUTHORIZED);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}

}
