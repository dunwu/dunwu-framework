package io.github.dunwu.common.dal.generator;

import cn.hutool.core.collection.CollectionUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.dunwu.common.dal.generator.config.ConstVal;
import io.github.dunwu.common.dal.generator.config.po.DictInfo;
import io.github.dunwu.common.dal.generator.config.po.DictOptionInfo;
import io.github.dunwu.common.dal.generator.util.FreemarkerHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器扩展工具类
 * <p>
 * 提供针对特定业务场景（如数据字典）的代码预览和生成功能。
 * 基于 Freemarker 模板引擎，将 {@link DictInfo} 对象渲染为对应的 Java 枚举或常量代码。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
@Slf4j
public class CodeGeneratorExt {

    /**
     * Freemarker 配置实例，用于模板渲染
     */
    private static final Configuration configuration = FreemarkerHelper.getDefaultConfiguration();

    /**
     * 私有构造方法，防止实例化
     */
    private CodeGeneratorExt() {
    }

    /**
     * 预览数据字典生成的代码内容
     *
     * @param dictInfo 数据字典信息
     * @return 渲染后的代码字符串，如果发生异常则返回 null
     */
    public static String previewDict(DictInfo dictInfo) {
        Map<String, Object> objectMap = getObjectMap(dictInfo);
        try {
            return FreemarkerHelper.getMergeContent(configuration, objectMap, ConstVal.DICT_TEMPLATE_PATH);
        } catch (IOException | TemplateException e) {
            log.error("预览数据字典代码失败", e);
            return null;
        }
    }

    /**
     * 生成数据字典代码并输出到指定文件
     *
     * @param dictInfo       数据字典信息
     * @param outputFilePath 输出文件的完整路径
     */
    public static void generateDict(DictInfo dictInfo, String outputFilePath) {
        Map<String, Object> objectMap = getObjectMap(dictInfo);
        try {
            FreemarkerHelper.outputFile(configuration, objectMap, ConstVal.DICT_TEMPLATE_PATH, outputFilePath);
            log.info("数据字典代码生成成功: {}", outputFilePath);
        } catch (IOException | TemplateException e) {
            log.error("生成数据字典代码失败", e);
        }
    }

    /**
     * 构建用于模板渲染的数据模型
     * <p>
     * 同时处理字典选项的格式化编码，确保生成的代码符合命名规范。
     *
     * @param dictInfo 数据字典信息
     * @return 包含模板变量的 Map
     */
    private static Map<String, Object> getObjectMap(DictInfo dictInfo) {
        if (CollectionUtil.isNotEmpty(dictInfo.getOptions())) {
            dictInfo.getOptions().forEach(option -> {
                String formatCode = DictOptionInfo.getFormatCode(dictInfo.getCode(), option.getCode());
                option.setFormatCode(formatCode);
            });
        }
        Map<String, Object> objectMap = new HashMap<>(1);
        objectMap.put("dict", dictInfo);
        return objectMap;
    }

    /**
     * 程序入口，用于测试字典生成功能
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        DictInfo dictInfo = getDictInfo();
        String content = CodeGeneratorExt.previewDict(dictInfo);
        if (content != null) {
            log.debug("文件内容：\n{}", content);
        }
    }

    /**
     * 获取测试用的数据字典信息
     *
     * @return 包含示例数据的 {@link DictInfo} 实例
     */
    private static DictInfo getDictInfo() {
        DictOptionInfo entry1 = new DictOptionInfo("ENABLE", "启用", "false", "启用");
        DictOptionInfo entry2 = new DictOptionInfo("DISABLE", "禁用", "true", "禁用");
        List<DictOptionInfo> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);
        DictInfo dictInfo = new DictInfo();
        dictInfo.setCode("disabled_status");
        dictInfo.setName("disabled_status");
        dictInfo.setNote("状态");
        dictInfo.setOptions(entries);
        return dictInfo;
    }

}
