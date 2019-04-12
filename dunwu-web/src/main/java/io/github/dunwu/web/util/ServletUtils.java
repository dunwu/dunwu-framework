package io.github.dunwu.web.util;

import com.google.common.net.HttpHeaders;
import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

public class ServletUtils {
    /**
     * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
     * @param fileName 下载后的文件名.
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
                if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {
                    encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
                } else if (agent.contains("msie")) {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                } else if (agent.contains("opera")) {
                    encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
                } else {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                }
            }
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + encodedfileName);
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
