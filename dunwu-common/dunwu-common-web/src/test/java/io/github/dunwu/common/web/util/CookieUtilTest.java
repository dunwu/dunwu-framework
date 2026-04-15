package io.github.dunwu.common.web.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * {@link CookieUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-13
 */
@DisplayName("CookieUtil 单元测试")
class CookieUtilTest {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("addCookie - 正常添加 Cookie")
    void addCookieNormal() {
        when(request.getContextPath()).thenReturn("/app");

        CookieUtil.addCookie(request, response, "token", "abc123");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());
        Cookie addedCookie = cookieCaptor.getValue();

        assertThat(addedCookie.getName()).isEqualTo("token");
        assertThat(addedCookie.getValue()).isEqualTo("abc123");
        assertThat(addedCookie.getPath()).isEqualTo("/app");
    }

    @Test
    @DisplayName("getCookie - 获取存在的 Cookie")
    void getCookieExists() {
        Cookie[] cookies = {new Cookie("user", "admin"), new Cookie("role", "superadmin")};
        when(request.getCookies()).thenReturn(cookies);

        String value = CookieUtil.getCookie(request, "user");

        assertThat(value).isEqualTo("admin");
    }

    @Test
    @DisplayName("getCookie - 获取不存在的 Cookie")
    void getCookieNotExists() {
        Cookie[] cookies = {new Cookie("user", "admin")};
        when(request.getCookies()).thenReturn(cookies);

        String value = CookieUtil.getCookie(request, "token");

        assertThat(value).isNull();
    }

    @Test
    @DisplayName("getCookie - 请求中没有 Cookie")
    void getCookieNoCookies() {
        when(request.getCookies()).thenReturn(null);

        String value = CookieUtil.getCookie(request, "token");

        assertThat(value).isNull();
    }

    @Test
    @DisplayName("getCookie - key 为空")
    void getCookieBlankKey() {
        Cookie[] cookies = {new Cookie("user", "admin")};
        when(request.getCookies()).thenReturn(cookies);

        String value = CookieUtil.getCookie(request, "");

        assertThat(value).isNull();
    }

    @Test
    @DisplayName("removeCookie - 正常删除 Cookie")
    void removeCookieNormal() {
        CookieUtil.removeCookie(response, "token", "/app", "example.com");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());
        Cookie removedCookie = cookieCaptor.getValue();

        assertThat(removedCookie.getName()).isEqualTo("token");
        assertThat(removedCookie.getValue()).isNull();
        assertThat(removedCookie.getPath()).isEqualTo("/app");
        assertThat(removedCookie.getDomain()).isEqualTo("example.com");
        assertThat(removedCookie.getMaxAge()).isZero();
    }

    @Test
    @DisplayName("removeCookie - path 和 domain 为空时删除 Cookie")
    void removeCookieWithoutPathAndDomain() {
        CookieUtil.removeCookie(response, "token", null, null);

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());
        Cookie removedCookie = cookieCaptor.getValue();

        assertThat(removedCookie.getName()).isEqualTo("token");
        assertThat(removedCookie.getPath()).isNull();
        assertThat(removedCookie.getDomain()).isNull();
        assertThat(removedCookie.getMaxAge()).isZero();
    }

}
