package io.github.dunwu.common.web.aop;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.web.constant.WebConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Pattern;

/**
 * CORS 跨域拦截器
 * <p>
 * 根据配置的白名单正则表达式，对跨域请求进行校验和放行
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
@Slf4j
public class CorsInterceptor implements HandlerInterceptor {

    /**
     * 跨域白名单，正则表达式形式
     */
    private static final String WHITELIST_REGEX = "((http://)|(https://))?(\\w*\\.)*(\\S)*";

    private static final String HEADER_KEY = "Host";

    private static final String PROTOCOL = "http";

    private static final String ORIGIN = "Origin";

    /**
     * 预处理请求，校验跨域白名单并设置 CORS 响应头
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return 是否继续执行
     * @throws Exception 如果配置参数缺失
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (StrUtil.isBlank(WHITELIST_REGEX) || StrUtil.isBlank(HEADER_KEY)) {
            throw new ServletException("读取跨域拦截器的配置参数失败");
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
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "客户端域不在跨域白名单中");
            throw new ServletException("客户端域不在跨域白名单中");
        }

        log.debug("对 origin = {} 放开跨域限制", origin);
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Headers",
            "Content-Type, X-Requested-With, Keep-Alive, User-Agent, " + "Authorization, X-Token, Session, SessionId");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if (WebConstant.HTTP_METHOD_OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return true;
    }

}
