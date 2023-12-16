package io.github.dunwu.tool.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.github.dunwu.tool.io.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * FreeMarker 模板引擎工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-12-16
 */
@Slf4j
public class FreemarkerUtil {

    private static final Configuration CONFIG = getDefaultConfiguration();

    public static Configuration getDefaultConfiguration() {

        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        File folder;
        try {
            folder = ResourceUtil.toFile("classpath://templates");
            configuration.setDirectoryForTemplateLoading(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return configuration;
    }

    public static <K, V> File getMergedFile(Map<K, V> objectMap, String tmplFilePath, String outputFilePath,
        Configuration configuration) throws IOException {
        String content = getMergedContent(objectMap, tmplFilePath, configuration);
        FileUtil.writeString(content, outputFilePath, StandardCharsets.UTF_8.name());
        return new File(outputFilePath);
    }

    public static <K, V> File getMergedFile(Map<K, V> objectMap, String tmplFilePath, String outputFilePath)
        throws IOException {
        return getMergedFile(objectMap, tmplFilePath, outputFilePath, CONFIG);
    }

    public static <K, V> String getMergedContent(Map<K, V> objectMap, String tmplFilePath, Configuration configuration)
        throws IOException {

        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = configuration.getTemplate(tmplFilePath);
            // 合并数据模型与模板
            // 将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(objectMap, out);
            out.flush();
            return out.toString();
        } catch (TemplateException e) {
            log.error("freemarker 模板引擎渲染失败！", e);
            throw new IOException(e);
        } finally {
            IoUtil.close(out);
        }
    }

    public static <K, V> String getMergedContent(Map<K, V> objectMap, String tmplFilePath) throws IOException {
        return getMergedContent(objectMap, tmplFilePath, CONFIG);
    }

}
