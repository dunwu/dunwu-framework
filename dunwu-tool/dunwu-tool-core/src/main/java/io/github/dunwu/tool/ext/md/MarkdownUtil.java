package io.github.dunwu.tool.ext.md;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.io.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Markdown 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-12
 */
public class MarkdownUtil {

    public static List<String> getFrontMatterLines(File file) {
        List<String> lines = FileUtil.readUtf8Lines(file);
        if (CollectionUtil.isEmpty(lines)) {
            return null;
        }

        String firstLine = lines.get(0);
        if ("---".equals(firstLine)) {
            // 找到第二行 ---，即 front matter 的边界
            for (int i = 1; i < lines.size(); i++) {
                if ("---".equals(lines.get(i))) {
                    return lines.subList(1, i);
                }
            }
        }
        return null;
    }

}
