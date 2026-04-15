package io.github.dunwu.common.web.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import io.github.dunwu.common.net.ip.IpUtil;
import io.github.dunwu.common.util.SystemUtil;
import io.github.dunwu.common.web.constant.HttpHeaders;
import io.github.dunwu.common.web.constant.WebConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Servlet 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-15
 */
@Slf4j
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    public static final String UNKNOWN = "unknown";

    /**
     * 下载文件到客户端
     *
     * @param request      HTTP 请求
     * @param response     HTTP 响应
     * @param file         待下载的文件
     * @param deleteOnExit 下载完成后是否删除文件
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file,
        boolean deleteOnExit) {
        // 设置响应头
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new BufferedOutputStream(response.getOutputStream());
            IoUtil.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("下载文件失败", e);
        } finally {
            IoUtil.close(inputStream);
            if (outputStream != null) {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    log.error("刷新输出流失败", e);
                }
            }
            IoUtil.close(outputStream);
            if (deleteOnExit) {
                FileUtil.del(file);
            }
        }
    }

    /**
     * 获取当前 HTTP 请求对象
     *
     * @return HttpServletRequest 实例
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求参数，支持 GET 和 POST 方法
     *
     * @param request HTTP 请求
     * @return 参数键值对映射
     * @throws IOException 如果读取请求流失败
     */
    public static Map<String, String> getReqParam(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        Map<String, String> reqMap;
        if (WebContentGenerator.METHOD_GET.equals(method)) {
            reqMap = doGet(request);
        } else if (WebContentGenerator.METHOD_POST.equals(method)) {
            reqMap = doPost(request);
        } else {
            throw new UnsupportedOperationException("http method not support");
        }
        return reqMap;
    }

    /**
     * 解析 GET 请求参数
     *
     * @param request HTTP 请求
     * @return 参数键值对映射
     */
    private static Map<String, String> doGet(HttpServletRequest request) {
        String param = request.getQueryString();
        return paramsToMap(param);
    }

    /**
     * 将查询字符串解析为 Map
     *
     * @param params 查询字符串（如 "a=1&b=2"）
     * @return 参数键值对映射
     */
    public static Map<String, String> paramsToMap(String params) {
        Map<String, String> map = new LinkedHashMap<>();
        if (StrUtil.isNotBlank(params)) {
            String[] array = params.split("&");
            for (String pair : array) {
                if ("=".equals(pair.trim())) {
                    continue;
                }
                String[] entity = pair.split("=");
                if (entity.length == 1) {
                    map.put(decode(entity[0]), null);
                } else {
                    map.put(decode(entity[0]), decode(entity[1]));
                }
            }
        }
        return map;
    }

    /**
     * URL 解码
     *
     * @param content 待解码内容
     * @return 解码后的字符串
     */
    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("解码内容失败: {}", content, e);
            return "";
        }
    }

    /**
     * 解析 POST 请求 Body（仅支持 JSON 格式）
     *
     * @param request HTTP 请求
     * @return 参数键值对映射
     * @throws IOException 如果读取请求流失败
     */
    public static Map<String, String> doPost(HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                StringBuilder sb = new StringBuilder();
                inputStream = request.getInputStream();
                int len;
                byte[] bytes = new byte[StreamUtils.BUFFER_SIZE];
                while ((len = inputStream.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, len));
                }
                String str = sb.toString();
                ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
                map = objectMapper.readValue(str, new TypeReference<Map<String, String>>() { });
            } else {
                throw new UnsupportedOperationException("http media type not support");
            }
        } catch (IOException e) {
            log.error("解析POST请求Body失败", e);
        } finally {
            if (inputStream != null) {
                IoUtil.close(inputStream);
            }
        }
        return map;
    }

    /**
     * 获取请求身份识别信息（IP、地理位置、系统、浏览器）
     *
     * @param request HTTP 请求
     * @return 请求身份信息
     */
    public static RequestIdentityInfo getRequestIdentityInfo(HttpServletRequest request) {
        RequestIdentityInfo requestIdentityInfo = new RequestIdentityInfo();
        try {
            requestIdentityInfo.setIp(getRealRemoteAddr(request));
            requestIdentityInfo.setLocation(IpUtil.getRegion(requestIdentityInfo.getIp()));
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
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StrUtil.isNotBlank(ip)) {
            String[] array = ip.split(",");
            return StrUtil.trim(array[0]);
        }
        return ip;
    }

    /**
     * 从 User-Agent 中提取浏览器信息
     *
     * @param userAgent User-Agent 字符串
     * @return 浏览器名称及版本
     */
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
            browser = "Safari " + userAgent.substring(indexOfSafariVersion,
                indexOfSafariVersion + "Version/x.x.x.x".length());
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

    /**
     * 从 User-Agent 中提取操作系统信息
     *
     * @param userAgent User-Agent 字符串
     * @return 操作系统名称及版本
     */
    public static String getOsInfo(String userAgent) {
        if (StrUtil.isBlank(userAgent)) {
            return "Unknown";
        }

        // 1. Windows 识别
        int winIndex = userAgent.indexOf("Windows NT");
        if (winIndex >= 0) {
            int start = winIndex + "Windows NT".length();
            int end = userAgent.indexOf(';', start);
            if (end == -1) end = userAgent.indexOf(')', start);
            if (end == -1) end = userAgent.length();
            
            String version = userAgent.substring(start, end).trim();
            return switch (version) {
                case "3.51" -> "Windows NT 3.51";
                case "4.0" -> "Windows NT 4.0";
                case "5.0" -> "Windows 2000";
                case "5.1" -> "Windows XP";
                case "5.2" -> "Windows Server 2003";
                case "6.0" -> "Windows Vista";
                case "6.1" -> "Windows 7";
                case "6.2" -> "Windows 8";
                case "6.3" -> "Windows 8.1";
                case "10.0" -> "Windows 10/11";
                default -> "Windows " + version;
            };
        }

        // 2. iOS 识别 (必须在 Mac OS 之前，因为 iPhone UA 包含 "like Mac OS X")
        if (userAgent.contains("iPhone OS") || userAgent.contains("iPad")) {
            String keyword = userAgent.contains("iPhone OS") ? "iPhone OS" : "iPad";
            int start = userAgent.indexOf(keyword);
            int end = userAgent.indexOf(" like", start);
            if (end == -1) end = userAgent.indexOf(';', start);
            if (end == -1) end = userAgent.indexOf(')', start);
            if (end == -1) end = userAgent.length();
            return userAgent.substring(start, end).trim().replace('_', '.');
        }

        // 3. Mac OS 识别
        int macIndex = userAgent.indexOf("Mac OS X");
        if (macIndex >= 0) {
            int end = userAgent.indexOf(')', macIndex);
            if (end == -1) end = userAgent.indexOf(';', macIndex);
            if (end == -1) end = userAgent.length();
            String macVersion = userAgent.substring(macIndex, end).trim();
            // 将下划线替换为点号，符合常规阅读习惯 (如 10_15_7 -> 10.15.7)
            return macVersion.replace('_', '.');
        }

        // 4. Linux 识别 (排除 Android)
        if (userAgent.contains("Linux") && !userAgent.contains("Android")) {
            return "Linux";
        }

        // 5. Android 识别
        int androidIndex = userAgent.indexOf("Android");
        if (androidIndex >= 0) {
            int end = userAgent.indexOf(';', androidIndex);
            if (end == -1) end = userAgent.indexOf(')', androidIndex);
            if (end == -1) end = userAgent.length();
            return userAgent.substring(androidIndex, end).trim();
        }

        return "Unknown";
    }

    /**
     * 判断请求是否为 AJAX 请求
     *
     * @param request HTTP 请求
     * @return 如果是 AJAX 请求返回 true，否则返回 false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(
            request.getHeader("X-Requested-With")));
    }

    /**
     * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
     *
     * @param fileName 下载后的文件名
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName,
        byte[] bytes) {
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
                if (agent.contains(WebConstant.BrowserType.FIREFOX.name()) || agent.contains(
                    WebConstant.BrowserType.CHROME.name()) || agent.contains(WebConstant.BrowserType.SAFARI.name())) {
                    encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
                } else if (agent.contains(WebConstant.BrowserType.MSIE.name())) {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                } else if (agent.contains(WebConstant.BrowserType.OPERA.name())) {
                    encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
                } else {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                }
            }
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + encodedfileName);
        } catch (UnsupportedEncodingException e) {
            log.error("设置文件下载头编码失败: {}", fileName, e);
        }
    }

    /**
     * 设置文件在线预览响应头
     *
     * @param response HTTP 响应
     */
    public static void setFileShowHeader(HttpServletResponse response) {
        response.reset();
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
    }

    @Getter
    @Setter
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

    }

}
