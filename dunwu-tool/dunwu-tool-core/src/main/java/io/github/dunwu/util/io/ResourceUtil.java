package io.github.dunwu.util.io;

import com.google.common.io.Resources;
import io.github.dunwu.util.ClassExtUtil;
import io.github.dunwu.util.collection.ListUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 针对Jar包内的文件的工具类.
 * <p>
 * 1.ClassLoader
 * <p>
 * 不指定contextClas时，优先使用Thread.getContextClassLoader()， 如果未设置则使用Guava Resources的ClassLoader
 * 指定contextClass，则直接使用该contextClass的ClassLoader.
 * <p>
 * 2.路径
 * <p>
 * 不指定contextClass时，按URLClassLoader的实现, 从jar file中查找resourceName， 所以resourceName无需以"/"打头即表示jar file中的根目录，带了"/"
 * 反而导致JarFile.getEntry(resouceName)时没有返回. 指定contextClass时，class.getResource() 会先对name进行处理再交给classLoader，打头的"/"的会被去除，不以"/"打头则表示与该contextClass
 * package的相对路径, 会先转为绝对路径.
 * <p>
 * 3.同名资源
 * <p>
 * 如果有多个同名资源，除非调用getResources()获取全部资源，否则在URLClassLoader中按ClassPath顺序打开第一个命中的Jar文件.
 */
public class ResourceUtil {

    private static final String CLASSPATH_PREFIX = "classpath://";

    private static final String URL_PROTOCOL_FILE = "file";

    private ResourceUtil() {}

    // 打开单个文件////

    /**
     * 读取规则见本类注释.
     */
    public static InputStream asStream(String resourceName) throws IOException {
        return Resources.getResource(resourceName).openStream();
    }

    /**
     * 读取文件的每一行，读取规则见本类注释.
     */
    public static InputStream asStream(Class<?> contextClass, String resourceName)
        throws IOException {
        return Resources.getResource(contextClass, resourceName).openStream();
    }

    /**
     * 读取规则见本类注释.
     */
    public static URL getResource(Class<?> contextClass, String resourceName) {
        return Resources.getResource(contextClass, resourceName);
    }

    public static List<URL> getResourcesQuietly(String resourceName) {
        return getResourcesQuietly(resourceName, ClassExtUtil.getDefaultClassLoader());
    }

    ////// 读取单个文件内容／／／／／

    public static List<URL> getResourcesQuietly(String resourceName,
        ClassLoader contextClassLoader) {
        try {
            Enumeration<URL> urls = contextClassLoader.getResources(resourceName);
            List<URL> list = new ArrayList<URL>(10);
            while (urls.hasMoreElements()) {
                list.add(urls.nextElement());
            }
            return list;
        } catch (IOException e) {// NOSONAR
            return ListUtil.emptyList();
        }
    }

    /**
     * 读取文件的每一行，读取规则见本类注释.
     */
    public static List<String> toLines(Class<?> contextClass, String resourceName)
        throws IOException {
        return Resources.readLines(Resources.getResource(contextClass, resourceName),
            StandardCharsets.UTF_8);
    }

    /**
     * 读取文件的每一行，读取规则见本类注释.
     */
    public static List<String> toLines(String resourceName) throws IOException {
        return Resources.readLines(Resources.getResource(resourceName),
            StandardCharsets.UTF_8);
    }

    /**
     * 读取文件的每一行，读取规则见本类注释.
     */
    public static String toString(Class<?> contextClass, String resourceName)
        throws IOException {
        return Resources.toString(Resources.getResource(contextClass, resourceName),
            StandardCharsets.UTF_8);
    }

    ///////////// 打开所有同名文件///////

    /**
     * 读取文件的每一行，读取规则见本类注释.
     */
    public static String toString(String resourceName) throws IOException {
        return Resources.toString(Resources.getResource(resourceName),
            StandardCharsets.UTF_8);
    }

    /**
     * 兼容无前缀, classpath://, file:// 的情况获取文件 如果以classpath:// 定义的文件不存在会抛出IllegalArgumentException异常，以file://定义的则不会
     */
    public static File toFile(String path) throws IOException {
        if (StringUtils.startsWith(path, CLASSPATH_PREFIX)) {
            String resourceName = StringUtils.substringAfter(path, CLASSPATH_PREFIX);
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

    private static File toFile(URL fileUrl) throws FileNotFoundException {
        Validate.notNull(fileUrl, "Resource URL must not be null");
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
     * 读取规则见本类注释.
     */
    public static URL getResource(String resource) {
        return Resources.getResource(resource);
    }

    public static URI toUri(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

    /**
     * 兼容file://与classpath://的情况的打开文件成Stream
     */
    public static InputStream toInputStream(String generalPath) throws IOException {
        if (StringUtils.startsWith(generalPath, CLASSPATH_PREFIX)) {
            String resourceName = StringUtils.substringAfter(generalPath, CLASSPATH_PREFIX);
            return Resources.getResource(resourceName).openStream();
        }

        try {
            // try URL
            return Files.newInputStream(toFile(new URL(generalPath)).toPath());
        } catch (MalformedURLException ex) {
            // no URL -> treat as file path
            return Files.newInputStream(new File(generalPath).toPath());
        }
    }

}
