package io.github.dunwu.tool.generator.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.github.dunwu.tool.generator.config.ConstVal;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Freemarker 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
@Slf4j
public class FreemarkerHelper {

    public static Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        configuration.setClassForTemplateLoading(FreemarkerHelper.class, StringPool.SLASH);
        return configuration;
    }

    public static <K, V> String getMergeContent(Configuration configuration, Map<K, V> objectMap,
        final String templatePath) throws IOException, TemplateException {
        /* Get the template (uses cache internally) */
        Template template = configuration.getTemplate(templatePath);

        /* Merge data-model with template */
        StringWriter stringWriter = new StringWriter();
        template.process(objectMap, stringWriter);
        String content = stringWriter.toString();
        stringWriter.close();
        return content;
    }

    public static <K, V> void outputFile(Configuration configuration, Map<K, V> objectMap, String templatePath,
        String outputFile) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        }
    }

}
