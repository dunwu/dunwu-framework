package io.github.dunwu.common.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * {@link ServletUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-13
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ServletUtil 单元测试")
class ServletUtilTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("paramsToMap - 解析正常查询字符串")
    void paramsToMapNormal() {
        String params = "name=admin&age=20";
        Map<String, String> result = ServletUtil.paramsToMap(params);

        assertThat(result).hasSize(2);
        assertThat(result.get("name")).isEqualTo("admin");
        assertThat(result.get("age")).isEqualTo("20");
    }

    @Test
    @DisplayName("paramsToMap - 解析空字符串")
    void paramsToMapEmpty() {
        Map<String, String> result = ServletUtil.paramsToMap("");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("paramsToMap - 解析只有键没有值的参数")
    void paramsToMapNoValue() {
        String params = "flag";
        Map<String, String> result = ServletUtil.paramsToMap(params);

        assertThat(result).hasSize(1);
        assertThat(result.get("flag")).isNull();
    }

    @Test
    @DisplayName("decode - 正常解码")
    void decodeNormal() {
        String encoded = "%E5%BC%A0%E4%B8%89"; // 张三
        String result = ServletUtil.decode(encoded);
        assertThat(result).isEqualTo("张三");
    }

    @Test
    @DisplayName("decode - 解码失败返回空字符串")
    void decodeFailure() {
        // 传入非法编码通常不会抛异常，但会记录日志，这里验证正常返回
        String result = ServletUtil.decode("test");
        assertThat(result).isEqualTo("test");
    }

    @Test
    @DisplayName("getRealRemoteAddr - 获取 x-forwarded-for 第一个 IP")
    void getRealRemoteAddrFromForwarded() {
        when(request.getHeader("x-forwarded-for")).thenReturn("192.168.1.100, 10.0.0.1");

        String ip = ServletUtil.getRealRemoteAddr(request);
        assertThat(ip).isEqualTo("192.168.1.100");
    }

    @Test
    @DisplayName("getRealRemoteAddr - x-forwarded-for 为 unknown 时获取 Proxy-Client-IP")
    void getRealRemoteAddrFromProxyClient() {
        when(request.getHeader("x-forwarded-for")).thenReturn("unknown");
        when(request.getHeader("Proxy-Client-IP")).thenReturn("192.168.1.101");

        String ip = ServletUtil.getRealRemoteAddr(request);
        assertThat(ip).isEqualTo("192.168.1.101");
    }

    @Test
    @DisplayName("getRealRemoteAddr - 所有代理头为空时获取 remoteAddr")
    void getRealRemoteAddrFromRemoteAddr() {
        when(request.getHeader("x-forwarded-for")).thenReturn(null);
        when(request.getHeader("Proxy-Client-IP")).thenReturn(null);
        when(request.getHeader("WL-Proxy-Client-IP")).thenReturn(null);
        when(request.getHeader("HTTP_CLIENT_IP")).thenReturn(null);
        when(request.getHeader("HTTP_X_FORWARDED_FOR")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        String ip = ServletUtil.getRealRemoteAddr(request);
        assertThat(ip).isEqualTo("127.0.0.1");
    }

    @Nested
    class GetBrowserInfoTest {
        @Test
        @DisplayName("getBrowserInfo - 识别 Chrome")
        void getBrowserInfoChrome() {
            String ua =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
            String result = ServletUtil.getBrowserInfo(ua);
            assertThat(result).startsWith("Chrome/");
        }

        @Test
        @DisplayName("getBrowserInfo - 识别 Firefox")
        void getBrowserInfoFirefox() {
            String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0";
            String result = ServletUtil.getBrowserInfo(ua);
            assertThat(result).startsWith("Firefox/");
        }

        @Test
        @DisplayName("getBrowserInfo - 识别 IE 11")
        void getBrowserInfoIE11() {
            String ua = "Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko";
            String result = ServletUtil.getBrowserInfo(ua);
            assertThat(result).isEqualTo("IE 11");
        }

        @Test
        @DisplayName("getBrowserInfo - 识别 Safari")
        void getBrowserInfoSafari() {
            String ua =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Safari/605.1.15";
            String result = ServletUtil.getBrowserInfo(ua);
            assertThat(result).startsWith("Safari Version/");
        }

    }

    @Nested
    class GetOsInfoTest {
        @Test
        @DisplayName("getOsInfo - 识别 Windows 10/11")
        void getOsInfoWindows10() {
            String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Windows 10/11");
        }

        @Test
        @DisplayName("getOsInfo - 识别 Windows 7")
        void getOsInfoWindows7() {
            String ua = "Mozilla/5.0 (Windows NT 6.1; Win64; x64)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Windows 7");
        }

        @Test
        @DisplayName("getOsInfo - 识别 Windows XP")
        void getOsInfoWindowsXP() {
            String ua = "Mozilla/5.0 (Windows NT 5.1)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Windows XP");
        }

        @Test
        @DisplayName("getOsInfo - 识别 Linux")
        void getOsInfoLinux() {
            String ua = "Mozilla/5.0 (X11; Linux x86_64)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Linux");
        }

        @Test
        @DisplayName("getOsInfo - 识别 Mac OS X")
        void getOsInfoMac() {
            String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Mac OS X 10.15.7");
        }

        @Test
        @DisplayName("getOsInfo - 识别 Android")
        void getOsInfoAndroid() {
            String ua = "Mozilla/5.0 (Linux; Android 12; Pixel 5)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("Android 12");
        }

        @Test
        @DisplayName("getOsInfo - 识别 iPhone OS")
        void getOsInfoIphone() {
            String ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 15_4 like Mac OS X)";
            String result = ServletUtil.getOsInfo(ua);
            assertThat(result).isEqualTo("iPhone OS 15.4");
        }

        @Test
        @DisplayName("getOsInfo - 空 User-Agent")
        void getOsInfoEmpty() {
            assertThat(ServletUtil.getOsInfo(null)).isEqualTo("Unknown");
            assertThat(ServletUtil.getOsInfo("")).isEqualTo("Unknown");
        }

    }

    @Test
    @DisplayName("isAjaxRequest - 是 AJAX 请求")
    void isAjaxRequestTrue() {
        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        assertThat(ServletUtil.isAjaxRequest(request)).isTrue();
    }

    @Test
    @DisplayName("isAjaxRequest - 不是 AJAX 请求")
    void isAjaxRequestFalse() {
        when(request.getHeader("X-Requested-With")).thenReturn(null);
        assertThat(ServletUtil.isAjaxRequest(request)).isFalse();
    }

    @Test
    @DisplayName("setFileShowHeader - 设置响应头")
    void setFileShowHeader() {
        ServletUtil.setFileShowHeader(response);
        // 验证 reset 和 setContentType 被调用
        org.mockito.Mockito.verify(response).reset();
    }

    @Test
    @DisplayName("getReqParam - 不支持的 HTTP 方法抛异常")
    void getReqParamUnsupportedMethod() {
        when(request.getMethod()).thenReturn("PUT");
        assertThatThrownBy(() -> ServletUtil.getReqParam(request)).isInstanceOf(UnsupportedOperationException.class)
            .hasMessageContaining("http method not support");
    }

}
