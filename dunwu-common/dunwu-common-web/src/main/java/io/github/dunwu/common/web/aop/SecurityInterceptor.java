package io.github.dunwu.common.web.aop;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.web.constant.WebConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 安全拦截器，用于校验请求的 Session 和 Token
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-13
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    /** Token 在 Session 中的键名 */
    private final String tokenKey;

    /**
     * 构造方法
     *
     * @param tokenKey Token 在 Session 中的键名
     */
    public SecurityInterceptor(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    /**
     * 预处理请求，校验 Session 和 Token
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return 是否继续执行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (WebConstant.HTTP_METHOD_OPTIONS.equals(request.getMethod())) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.warn("请求未携带有效 Session，URL: {}", request.getRequestURL());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        log.info("[{}] url = {}", request.getMethod(), request.getRequestURL());
        log.info("session id = {}", session.getId());
        String token = (String)session.getAttribute(tokenKey);
        log.info("interceptor {} = {}", tokenKey, token);
        if (StrUtil.isBlank(token)) {
            log.warn("Session 中未找到 token: {}", tokenKey);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

}
