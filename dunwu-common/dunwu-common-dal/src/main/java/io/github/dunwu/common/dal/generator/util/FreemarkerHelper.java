package io.github.dunwu.common.dal.generator.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.github.dunwu.common.dal.generator.config.ConstVal;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Freemarker 模板渲染工具类
 * <p>
 * 封装了 Freemarker 的常用操作，提供获取默认配置、渲染模板内容以及输出模板到文件的功能。
 * 主要用于代码生成器中将数据模型与模板文件合并生成最终代码。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
@Slf4j
public class FreemarkerHelper {

    /**
     * 私有构造方法，防止实例化
     */
    private FreemarkerHelper() {
    }

    /**
     * 获取默认的 Freemarker 配置
     * <p>
     * 设置默认编码为 UTF-8，并指定模板加载的类路径为当前类所在路径。
     *
     * @return 配置好的 {@link Configuration} 实例
     */
    public static Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        configuration.setClassForTemplateLoading(FreemarkerHelper.class, StringPool.SLASH);
        return configuration;
    }

    /**
     * 将数据模型与模板合并，返回渲染后的字符串内容
     *
     * @param configuration Freemarker 配置
     * @param objectMap     数据模型
     * @param templatePath  模板路径
     * @param <K>           键类型
     * @param <V>           值类型
     * @return 渲染后的字符串
     * @throws IOException       如果读取模板失败
     * @throws TemplateException 如果渲染过程出错
     */
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

    /**
     * 将数据模型与模板合并，并输出到指定文件
     *
     * @param configuration Freemarker 配置
     * @param objectMap     数据模型
     * @param templatePath  模板路径
     * @param outputFile    输出文件的完整路径
     * @param <K>           键类型
     * @param <V>           值类型
     * @throws IOException       如果文件写入失败
     * @throws TemplateException 如果渲染过程出错
     */
    public static <K, V> void outputFile(Configuration configuration, Map<K, V> objectMap, String templatePath,
        String outputFile) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        }
    }

}
