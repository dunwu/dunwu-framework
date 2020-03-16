package io.github.dunwu.tool.io.resource;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.collection.EnumerationIter;
import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.io.IORuntimeException;
import io.github.dunwu.tool.util.CharsetUtil;
import io.github.dunwu.tool.util.ClassLoaderUtil;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.tool.util.URLUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;

/**
 * ClassPath资源工具类
 *
 * @author Looly
 */
public class ResourceUtil {

    private static final String CLASSPATH_PREFIX = "classpath://";

    private static final String URL_PROTOCOL_FILE = "file";

    /**
     * 从ClassPath资源中获取{@link BufferedReader}
     *
     * @param resurce ClassPath资源
     * @param charset 编码
     * @return {@link InputStream}
     * @since 3.1.2
     */
    public static BufferedReader getReader(String resurce, Charset charset) {
        return getResourceObj(resurce).getReader(charset);
    }

    /**
     * 获得资源的URL<br> 路径用/分隔，例如:
     *
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getResource(String resource) throws IORuntimeException {
        return getResource(resource, null);
    }

    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource  资源相对路径
     * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
     * @return {@link URL}
     */
    public static URL getResource(String resource, Class<?> baseClass) {
        return (null != baseClass) ? baseClass.getResource(resource)
            : ClassLoaderUtil.getClassLoader().getResource(resource);
    }

    /**
     * 获取指定路径下的资源Iterator<br> 路径格式必须为目录格式,用/分隔，例如:
     *
     * <pre>
     * config/a
     * spring/xml
     * </pre>
     *
     * @param resource 资源路径
     * @return 资源列表
     * @since 4.1.5
     */
    public static EnumerationIter<URL> getResourceIter(String resource) {
        final Enumeration<URL> resources;
        try {
            resources = ClassLoaderUtil.getClassLoader().getResources(resource);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return new EnumerationIter<>(resources);
    }

    /**
     * 获取指定路径下的资源列表<br> 路径格式必须为目录格式,用/分隔，例如:
     *
     * <pre>
     * config/a
     * spring/xml
     * </pre>
     *
     * @param resource 资源路径
     * @return 资源列表
     */
    public static List<URL> getResources(String resource) {
        final Enumeration<URL> resources;
        try {
            resources = ClassLoaderUtil.getClassLoader().getResources(resource);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return CollectionUtil.newArrayList(resources);
    }

    /**
     * 从ClassPath资源中获取{@link InputStream}
     *
     * @param resurce ClassPath资源
     * @return {@link InputStream}
     * @throws NoResourceException 资源不存在异常
     * @since 3.1.2
     */
    public static InputStream getStream(String resurce) throws NoResourceException {
        return getResourceObj(resurce).getStream();
    }

    /**
     * 从ClassPath资源中获取{@link InputStream}，当资源不存在时返回null
     *
     * @param resurce ClassPath资源
     * @return {@link InputStream}
     * @since 4.0.3
     */
    public static InputStream getStreamSafe(String resurce) {
        try {
            return getResourceObj(resurce).getStream();
        } catch (NoResourceException e) {
            // ignore
        }
        return null;
    }

    /**
     * 读取Classpath下的资源为byte[]
     *
     * @param resource 可以是绝对路径，也可以是相对路径（相对ClassPath）
     * @return 资源内容
     * @since 4.5.19
     */
    public static byte[] readBytes(String resource) {
        return getResourceObj(resource).readBytes();
    }

    /**
     * 读取Classpath下的资源为字符串，使用UTF-8编码
     *
     * @param resource 资源路径，使用相对ClassPath的路径
     * @return 资源内容
     * @since 3.1.1
     */
    public static String readUtf8Str(String resource) {
        return readStr(resource, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 读取Classpath下的资源为字符串
     *
     * @param resource 可以是绝对路径，也可以是相对路径（相对ClassPath）
     * @param charset  编码
     * @return 资源内容
     * @since 3.1.1
     */
    public static String readStr(String resource, Charset charset) {
        return getResourceObj(resource).readStr(charset);
    }

    /**
     * 获取{@link Resource} 资源对象<br> 如果提供路径为绝对路径或路径以file:开头，返回{@link FileResource}，否则返回{@link ClassPathResource}
     *
     * @param path 路径，可以是绝对路径，也可以是相对路径（相对ClassPath）
     * @return {@link Resource} 资源对象
     * @since 3.2.1
     */
    public static Resource getResourceObj(String path) {
        if (StringUtil.isNotBlank(path)) {
            if (path.startsWith(URLUtil.FILE_URL_PREFIX) || FileUtil.isAbsolutePath(path)) {
                return new FileResource(path);
            }
        }
        return new ClassPathResource(path);
    }

    /**
     * 兼容无前缀, classpath://, file:// 的情况获取文件 如果以classpath:// 定义的文件不存在会抛出IllegalArgumentException异常，以file://定义的则不会
     */
    public static File toFile(String path) throws IOException {
        if (StringUtil.startWith(path, CLASSPATH_PREFIX)) {
            String resourceName = StringUtil.subAfter(path, CLASSPATH_PREFIX, false);
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

    public static URI toUri(String location) throws URISyntaxException {
        return new URI(StringUtil.replace(location, " ", "%20"));
    }

}
