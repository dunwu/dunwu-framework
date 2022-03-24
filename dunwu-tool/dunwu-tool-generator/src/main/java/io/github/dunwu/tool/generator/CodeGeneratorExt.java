package io.github.dunwu.tool.generator;

import cn.hutool.core.collection.CollectionUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.dunwu.tool.generator.config.ConstVal;
import io.github.dunwu.tool.generator.config.po.DictInfo;
import io.github.dunwu.tool.generator.config.po.DictOptionInfo;
import io.github.dunwu.tool.generator.util.FreemarkerHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
@Slf4j
public class CodeGeneratorExt {

    private static final Configuration configuration = FreemarkerHelper.getDefaultConfiguration();

    public static String previewDict(DictInfo dictInfo) {
        Map<String, Object> objectMap = getObjectMap(dictInfo);
        try {
            return FreemarkerHelper.getMergeContent(configuration, objectMap, ConstVal.DICT_TEMPLATE_PATH);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void generateDict(DictInfo dictInfo, String outputFilePath) {
        Map<String, Object> objectMap = getObjectMap(dictInfo);
        try {
            FreemarkerHelper.outputFile(configuration, objectMap, ConstVal.DICT_TEMPLATE_PATH, outputFilePath);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

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

    public static void main(String[] args) {
        DictInfo dictInfo = getDictInfo();
        log.debug("文件内容：{}", CodeGeneratorExt.previewDict(dictInfo));
    }

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
