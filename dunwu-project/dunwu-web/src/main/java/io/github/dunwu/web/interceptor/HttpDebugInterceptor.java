package io.github.dunwu.web.interceptor;

import io.github.dunwu.web.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP 请求拦截器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
public class HttpDebugInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String START_TIMESTAMP = "START_TIME";

	/**
	 * 拦截请求，打印请求信息 This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {
		log.debug("[{} \"{}\"] params = {}, host = {}, ip = {}, port = {}",
			request.getMethod(), request.getRequestURI(), request.getQueryString(),
			request.getRemoteHost(), ServletUtil.getRealRemoteAddr(request),
			request.getRemotePort());
		request.setAttribute(START_TIMESTAMP, System.currentTimeMillis());
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler, @Nullable ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 拦截应答，打印应答信息 This implementation is empty.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
		Object handler, @Nullable Exception ex) throws Exception {
		long currentTime = System.currentTimeMillis();
		long startTime = Long.valueOf(request.getAttribute(START_TIMESTAMP).toString());
		long time = (currentTime - startTime) / 1000;
		log.debug("[{} \"{}\"] completed. status = {}, time = {} ms", request.getMethod(),
			request.getRequestURI(), response.getStatus(), time);
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
	}

}
