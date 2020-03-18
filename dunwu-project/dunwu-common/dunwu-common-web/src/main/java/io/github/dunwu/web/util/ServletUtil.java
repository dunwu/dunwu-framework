package io.github.dunwu.web.util;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import io.github.dunwu.tool.util.SystemUtil;
import io.github.dunwu.util.net.IpUtils;
import io.github.dunwu.web.constant.HttpHeaders;
import io.github.dunwu.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Objects;
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

    private static final Logger log = LoggerFactory.getLogger(ServletUtil.class);

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static RequestIdentityInfo getRequestIdentityInfo(HttpServletRequest request) {
        RequestIdentityInfo requestIdentityInfo = new RequestIdentityInfo();
        try {
            requestIdentityInfo.setIp(getRealRemoteAddr(request));
            requestIdentityInfo.setLocation(IpUtils.getRegionName(requestIdentityInfo.getIp()));
            StringBuilder userAgent = new StringBuilder("[");
            userAgent.append(request.getHeader("User-Agent"));
            userAgent.append("]");
            requestIdentityInfo.setSystem(getOsInfo(userAgent.toString()));
            String browser = getBrowserInfo(userAgent.toString());
            requestIdentityInfo.setBrowser(browser.replace("/", " "));
        } catch (Exception e) {
            log.error("获取登录信息失败：{}", e.getMessage());
            requestIdentityInfo.setSystem("");
            requestIdentityInfo.setBrowser("");
        }
        return requestIdentityInfo;
    }

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

    public static String getBrowserInfo(String userAgent) {

        int indexOfIe = userAgent.indexOf(SystemUtil.BROWSER_MSIE_NAME_PREFIX);
        int indexOfIe11 = userAgent.indexOf(SystemUtil.BROWSER_IE11_NAME_PREFIX);
        int indexOfFirefox = userAgent.indexOf(SystemUtil.BROWSER_FIREFOX_NAME_PREFIX);
        int indexOfSogou = userAgent.indexOf(SystemUtil.BROWSER_SOGOU_NAME_PREFIX);
        int indexOfChrome = userAgent.indexOf(SystemUtil.BROWSER_CHROME_NAME_PREFIX);
        int indexOfSafari = userAgent.indexOf(SystemUtil.BROWSER_SAFARI_NAME_PREFIX);

        boolean containIe = (indexOfIe > 0 || indexOfIe11 > 0);
        boolean containFirefox = indexOfFirefox > 0;
        boolean containSogou = indexOfSogou > 0;
        boolean containChrome = indexOfChrome > 0;
        boolean containSafari = indexOfSafari > 0;
        String browser = "";
        if (containSogou) {
            if (containIe) {
                browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
            } else if (containChrome) {
                browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            }
        } else if (containChrome) {
            browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
        } else if (containSafari) {
            int indexOfSafariVersion = userAgent.indexOf("Version");
            browser = "Safari "
                + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
        } else if (containFirefox) {
            browser = userAgent.substring(indexOfFirefox, indexOfFirefox + "Firefox/xx".length());
        } else if (containIe) {
            if (indexOfIe11 > 0) {
                browser = "IE 11";
            } else {
                browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
            }
        }

        return browser;
    }

    public static String getOsInfo(String userAgent) {
        int indexOfMac = userAgent.indexOf(SystemUtil.OS_NAME_MAC_PREFIX);
        int indexOfWindows = userAgent.indexOf(SystemUtil.OS_NAME_WINDOWS_PREFIX);

        boolean isMac = indexOfMac > 0;
        boolean isWindows = indexOfWindows > 0;
        boolean isLinux = userAgent.indexOf("Linux") > 0;

        String os = "";
        if (isMac) {
            os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
        } else if (isLinux) {
            os = "Linux";
        } else if (isWindows) {
            os = "Windows ";
            String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                + "Windows NTx.x".length());
            switch (version.trim()) {
                case "5.0":
                    os += "2000";
                    break;
                case "5.1":
                    os += "XP";
                    break;
                case "5.2":
                    os += "2003";
                    break;
                case "6.0":
                    os += "Vista";
                    break;
                case "6.1":
                    os += "7";
                    break;
                case "6.2":
                    os += "8";
                    break;
                case "6.3":
                    os += "8.1";
                    break;
                case "10":
                    os += "10";
                default:
                    break;
            }
        }
        return os;
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

    public static class RequestIdentityInfo {

        /**
         * 登录 IP
         */
        private String ip;

        /**
         * 登录地理位置
         */
        private String location;

        /**
         * 操作系统
         */
        private String system;

        /**
         * 登录浏览器
         */
        private String browser;

        public String getIp() {
            return ip;
        }

        public RequestIdentityInfo setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public String getLocation() {
            return location;
        }

        public RequestIdentityInfo setLocation(String location) {
            this.location = location;
            return this;
        }

        public String getSystem() {
            return system;
        }

        public RequestIdentityInfo setSystem(String system) {
            this.system = system;
            return this;
        }

        public String getBrowser() {
            return browser;
        }

        public RequestIdentityInfo setBrowser(String browser) {
            this.browser = browser;
            return this;
        }

    }

}
