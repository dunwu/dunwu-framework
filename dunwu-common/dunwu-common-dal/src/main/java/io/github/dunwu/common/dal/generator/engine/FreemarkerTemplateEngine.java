package io.github.dunwu.common.dal.generator.engine;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.util.FreemarkerHelper;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker 模板引擎实现
 * <p>
 * 继承自 {@link AbstractTemplateEngine}，使用 Freemarker 作为底层模板引擎
 * 进行代码文件的渲染和输出。负责加载模板、合并数据模型并生成最终的 Java/Vue 代码文件。
 *
 * @author nieqiurong
 * @since 2018-01-11
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    /**
     * Freemarker 配置对象，用于管理模板加载和渲染设置
     */
    private Configuration configuration;

    /**
     * 初始化模板引擎及 Freemarker 配置
     * <p>
     * 调用父类初始化方法，并加载默认的 Freemarker 配置。
     *
     * @param configBuilder 配置构建器，包含代码生成的各项配置
     * @return 当前模板引擎实例
     */
    @Override
    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = FreemarkerHelper.getDefaultConfiguration();
        return this;
    }

    /**
     * 获取合并后的模板内容
     * <p>
     * 将数据模型与指定路径的模板进行合并，返回渲染后的字符串。
     * 主要用于代码预览功能。
     *
     * @param objectMap    渲染数据模型
     * @param templatePath 模板文件路径
     * @return 渲染后的字符串内容
     * @throws IOException       如果读取模板失败
     * @throws TemplateException 如果渲染过程发生错误
     */
    @Override
    public String getMergeContent(Map<String, Object> objectMap, String templatePath)
        throws IOException, TemplateException {
        return FreemarkerHelper.getMergeContent(configuration, objectMap, templatePath);
    }

    /**
     * 输出文件到指定路径
     * <p>
     * 将数据模型与模板合并后，直接写入到目标文件系统中。
     *
     * @param objectMap    渲染数据模型
     * @param templatePath 模板文件路径
     * @param outputFile   输出文件的完整路径
     * @throws IOException       如果写入文件失败
     * @throws TemplateException 如果渲染过程发生错误
     */
    @Override
    public void outputFile(Map<String, Object> objectMap, String templatePath, String outputFile)
        throws IOException, TemplateException {
        FreemarkerHelper.outputFile(configuration, objectMap, templatePath, outputFile);
        logger.debug(">>>> 创建文件：\n【模板】{}\n【文件】{}", templatePath, outputFile);
    }

    /**
     * 获取 Freemarker 模板文件的完整路径
     * <p>
     * Freemarker 模板通常以 ".ftl" 为后缀，此方法负责拼接后缀。
     *
     * @param filePath 模板文件基础路径
     * @return 添加了 ".ftl" 后缀的完整模板路径
     */
    @Override
    public String getTemplatePath(String filePath) {
        return filePath + ".ftl";
    }

}
