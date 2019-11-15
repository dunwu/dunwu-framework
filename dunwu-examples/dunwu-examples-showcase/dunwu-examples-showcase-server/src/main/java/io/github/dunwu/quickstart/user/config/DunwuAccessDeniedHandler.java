package io.github.dunwu.quickstart.user.config;

import com.alibaba.fastjson.JSON;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拒绝访问处理器（登录状态下，无权限会触发）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Component
public class DunwuAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException e) throws IOException, ServletException {
		BaseResult result = ResultUtils.failBaseResult(AppCode.UNAUTHORIZED);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.getWriter().write(JSON.toJSONString(result));
	}

}
