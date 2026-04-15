package io.github.dunwu.common.web.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
public class CookieUtil {

    /**
     * 添加 Cookie
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param key      Cookie 键
     * @param value    Cookie 值
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    /**
     * 获取 Cookie
     *
     * @param request HttpServletRequest
     * @param key     Cookie 中存储的关键字
     * @return Cookie 中存储的关键字对应的值
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtil.isEmpty(cookies) || StrUtil.isBlank(key)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * 删除 Cookie
     *
     * @param response HTTP 响应
     * @param key      Cookie 键
     * @param path     Cookie 路径
     * @param domain   Cookie 域名
     */
    public static void removeCookie(HttpServletResponse response, String key, String path, String domain) {
        Cookie cookie = new Cookie(key, null);

        if (StrUtil.isNotEmpty(path)) {
            cookie.setPath(path);
        }

        if (StrUtil.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }

        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
