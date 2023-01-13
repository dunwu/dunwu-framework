package io.github.dunwu.tool.web.aop;

import io.github.dunwu.tool.web.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用日志 DEBUG 级别打印 HTTP 请求、应答信息的拦截器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
public class HttpDebugInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String BEGIN_TIME = "BEGIN_TIME";

    /**
     * 拦截请求，打印请求信息，并记录访问时间
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {
            log.debug("[{} \"{}\"] params = {}, host = {}, ip = {}, port = {}",
                request.getMethod(), request.getRequestURI(), request.getQueryString(),
                request.getRemoteHost(), ServletUtil.getRealRemoteAddr(request),
                request.getRemotePort());
        }
        String requestURI = request.getRequestURI();
        request.setAttribute(BEGIN_TIME, System.nanoTime());
        return true;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler, @Nullable ModelAndView modelAndView) {
    }

    /**
     * 拦截应答，打印应答信息，计算并打印请求耗时
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, @Nullable Exception ex) {
        long endTime = System.nanoTime();
        long beginTime = Long.parseLong(request.getAttribute(BEGIN_TIME).toString());
        long time = endTime - beginTime;
        if (log.isDebugEnabled()) {
            log.debug("[{} \"{}\"] completed. status = {}, time = {} ms", request.getMethod(),
                request.getRequestURI(), response.getStatus(), TimeUnit.NANOSECONDS.toMillis(time));
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request,
        HttpServletResponse response, Object handler) {
    }

}
