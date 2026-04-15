package io.github.dunwu.common.web.aop;

import io.github.dunwu.common.web.util.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

/**
 * 使用日志 DEBUG 级别打印 HTTP 请求、应答信息的拦截器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Slf4j
public class HttpDebugInterceptor implements HandlerInterceptor {

    /** 记录请求开始时间的属性名 */
    private static final String BEGIN_TIME = "BEGIN_TIME";

    /**
     * 拦截请求，打印请求信息，并记录访问时间
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return true 表示继续处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {
            log.debug("[{} \"{}\"] params = {}, host = {}, ip = {}, port = {}", request.getMethod(),
                request.getRequestURI(), request.getQueryString(), request.getRemoteHost(),
                ServletUtil.getRealRemoteAddr(request), request.getRemotePort());
        }
        String requestURI = request.getRequestURI();
        request.setAttribute(BEGIN_TIME, System.nanoTime());
        return true;
    }

    /**
     * 请求处理完成后调用（渲染视图前）
     *
     * @param request      HTTP 请求
     * @param response     HTTP 响应
     * @param handler      处理器
     * @param modelAndView 模型和视图
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable ModelAndView modelAndView) {
    }

    /**
     * 拦截应答，打印应答信息，计算并打印请求耗时
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @param ex       异常信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) {
        long endTime = System.nanoTime();
        long beginTime = Long.parseLong(request.getAttribute(BEGIN_TIME).toString());
        long time = endTime - beginTime;
        if (log.isDebugEnabled()) {
            log.debug("[{} \"{}\"] completed. status = {}, time = {} ms", request.getMethod(), request.getRequestURI(),
                response.getStatus(), TimeUnit.NANOSECONDS.toMillis(time));
        }
    }

}
