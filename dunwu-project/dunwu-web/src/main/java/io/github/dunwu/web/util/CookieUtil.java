package io.github.dunwu.web.util;

import io.github.dunwu.util.collection.ArrayUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
public class CookieUtil {

	/**
	 * 添加 Cookie
	 * @param request
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response,
			String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}

	/**
	 * 获取 Cookie
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (ArrayUtil.isEmpty(cookies) || StringUtils.isBlank(key)) {
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
	 * @param response
	 * @param key
	 * @param path
	 * @param domain
	 */
	public static void removeCookie(HttpServletResponse response, String key, String path,
			String domain) {
		Cookie cookie = new Cookie(key, null);

		if (StringUtils.isEmpty(path)) {
			cookie.setPath(path);
		}

		if (StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(-1000);
		response.addCookie(cookie);
	}

}
