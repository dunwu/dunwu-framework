package io.github.dunwu.web.util;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import io.github.dunwu.web.constant.HttpHeaders;
import io.github.dunwu.web.constant.WebConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
public class ServletUtil {

    public static final String UNKNOWN = "unknown";

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request HttpServletRequest
     * @return 真实 IP 地址
     */
    public static String getRealRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletRequest request,
        HttpServletResponse response, String fileName, byte[] bytes) {
        response.reset();
        Collection<String> types = MimeUtil.getMimeTypes(bytes);
        response.setContentType(types.toArray(new MimeType[types.size()])[0].toString());
        response.setCharacterEncoding("utf-8");
        response.setContentLength(bytes.length);

        try {
            String agent = request.getHeader("User-Agent");
            String encodedfileName = null;
            if (null != agent) {
                agent = agent.toLowerCase();
                if (agent.contains(WebConstant.BrowserType.firefox.name())
                    || agent.contains(WebConstant.BrowserType.chrome.name())
                    || agent.contains(WebConstant.BrowserType.safari.name())) {
                    encodedfileName = "filename=\""
                        + new String(fileName.getBytes(), "ISO8859-1") + "\"";
                } else if (agent.contains(WebConstant.BrowserType.msie.name())) {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8")
                        + "\"";
                } else if (agent.contains(WebConstant.BrowserType.opera.name())) {
                    encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
                } else {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8")
                        + "\"";
                }
            }
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; " + encodedfileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void setFileShowHeader(HttpServletResponse response) {
        response.reset();
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
    }

}
