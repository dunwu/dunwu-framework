package io.github.dunwu.web.interceptor;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
public class CorsInceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 跨域白名单，正则形式
     */
    private final String WHITELIST_REGEX = "((http://)|(https://))?(\\w*\\.)*(\\S)*";

    private final String HEADER_KEY = "Host";

    private final String PROTOCOL = "http";

    private final String ORIGIN = "Origin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        if (StrUtil.isBlank(WHITELIST_REGEX) || StrUtil.isBlank(HEADER_KEY)) {
            throw new ServletException("读取跨域过滤器的配置参数失败");
        }

        // 读取请求地址的域
        String domain = request.getHeader(HEADER_KEY);
        String origin = request.getHeader(ORIGIN);

        if (StrUtil.isBlank(origin) || StrUtil.isBlank(domain)) {
            log.debug("origin = {}, domain = {}, 跳过检查", origin, domain);
            return true;
        }

        if (origin.toLowerCase().contains(domain.toLowerCase())) {
            // 判断请求方和应答方是否同为 http 或 https
            // 如果相同，这里视为同源；否则，视为跨域
            if (origin.startsWith(PROTOCOL)) {
                log.debug("domain={}, origin={}, 二者协议相同，且域名同源，跳过检查", domain, origin);
                return true;
            }
        }

        Pattern pattern = Pattern.compile(WHITELIST_REGEX);
        if (!pattern.matcher(origin).matches()) {
            log.warn("客户端域 origin = {} 不在跨域白名单中", origin);
            response.sendError(403, "客户端域不在跨域白名单中");
            throw new ServletException("客户端域不在跨域白名单中");
        }

        log.debug("对 origin = {} 放开跨域限制", origin);
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Headers",
            "Content-Type, X-Requested-With, Keep-Alive, User-Agent"
                + "Authorization, X-Token, Session, SessionId");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if (WebConstant.HTTP_METHOD_OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        return true;
    }

}
