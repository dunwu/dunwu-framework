package io.github.dunwu.web.interceptor;

import io.github.dunwu.config.DunwuSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-13
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DunwuSecurityProperties dunwuSecurityProperties;

    public SecurityInterceptor(DunwuSecurityProperties dunwuSecurityProperties) {
        this.dunwuSecurityProperties = dunwuSecurityProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod()
                   .equals("OPTIONS")) {
            return true;
        }

        HttpSession session = request.getSession();
        log.info("[{}] url = {}", request.getMethod(), request.getRequestURL());
        log.info("session id = {}", session.getId());
        String token = (String) session.getAttribute(dunwuSecurityProperties.getAuthTokenKey());
        log.info("inceptor {} = {}", dunwuSecurityProperties.getAuthTokenKey(), token);
        if (StringUtils.isNoneBlank(token)) {
            return true;
        }
        log.error("没有 session");
        return true;
    }
}
