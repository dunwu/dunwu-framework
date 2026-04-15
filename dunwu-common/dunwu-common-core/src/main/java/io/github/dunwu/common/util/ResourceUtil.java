package io.github.dunwu.common.util;

import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 资源工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class ResourceUtil extends cn.hutool.core.io.resource.ResourceUtil {

    private static final String CLASSPATH_PREFIX = "classpath://";

    private static final String URL_PROTOCOL_FILE = "file";

    /**
     * 兼容无前缀, classpath://, file:// 的情况获取文件 如果以classpath:// 定义的文件不存在会抛出IllegalArgumentException异常，以file://定义的则不会
     */
    public static File toFile(String path) throws IOException {
        if (StrUtil.startWith(path, CLASSPATH_PREFIX)) {
            String resourceName = StrUtil.subAfter(path, CLASSPATH_PREFIX, false);
            return toFile(ResourceUtil.getResource(resourceName));
        }

        try {
            // try URL
            return toFile(new URL(path));
        } catch (MalformedURLException ex) {
            // no URL -> treat as file path
            return new File(path);
        }
    }

    /**
     * 将 URL 转换为 File 对象
     *
     * @param fileUrl 文件 URL
     * @return 文件对象
     * @throws FileNotFoundException 如果 URL 不是文件协议则抛出异常
     */
    public static File toFile(URL fileUrl) throws FileNotFoundException {
        if (fileUrl == null) {
            throw new NullPointerException("URL must not be null");
        }
        if (!URL_PROTOCOL_FILE.equals(fileUrl.getProtocol())) {
            throw new FileNotFoundException(
                "URL cannot be resolved to absolute file path "
                    + "because it does not reside in the file system: "
                    + fileUrl);
        }
        try {
            return new File(toUri(fileUrl.toString()).getSchemeSpecificPart());
        } catch (URISyntaxException ex) { // NOSONAR
            // Fallback for URLs that are not valid URIs (should hardly ever happen).
            return new File(fileUrl.getFile());
        }
    }

    /**
     * 将路径字符串转换为 URI，自动处理空格编码
     *
     * @param location 路径字符串
     * @return URI 对象
     * @throws URISyntaxException URI 语法异常
     */
    public static URI toUri(String location) throws URISyntaxException {
        return new URI(StrUtil.replace(location, " ", "%20"));
    }

}
