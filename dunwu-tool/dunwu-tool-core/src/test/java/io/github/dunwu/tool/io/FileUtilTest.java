package io.github.dunwu.tool.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.ext.git.GitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileUtil 测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-11
 */
public class FileUtilTest {

    public static final String MARKDOWN_EXT = "md";
    // 忽略的目录
    public static final String[] IGNORED_DIR_LIST =
        { ".git", ".idea", ".temp", "node_modules", "assets", ".github", "scripts", "settings", "scaffolds" };

    @ParameterizedTest
    @DisplayName("刷新指定项目路径下的md文档的创建时间")
    @ValueSource(strings = { "D:\\Codes\\zp\\ztutorial\\zp-cs\\design\\" })
    public void test(String path) {

        List<File> files = FileUtil.getAllExtFiles(path, IGNORED_DIR_LIST, MARKDOWN_EXT);

        for (File f : files) {
            String filename = f.getAbsolutePath().replace(path, "");
            List<String> lines = FileUtil.readUtf8Lines(f);
            if (CollectionUtil.isEmpty(lines)) {
                continue;
            }

            String firstLine = lines.get(0);
            if (!"---".equals(firstLine)) {
                continue;
            }

            if (CollectionUtil.isEmpty(lines)) {
                continue;
            }

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);

                // 遇到 front matter，结束
                if ("---".equals(line)) {
                    break;
                }

                if (line.startsWith("date: ")) {
                    line = line.replace("date: ", "");
                    String newDateStr = GitUtil.getFileFirstGitPushTime(path, filename);
                    if (StrUtil.isNotBlank(newDateStr)) {

                        DateTime oldDate = null;
                        try {
                            oldDate = DateUtil.parse(line, DatePattern.NORM_DATETIME_PATTERN);
                        } catch (DateException e) {
                            try {
                                oldDate = DateUtil.parse(line, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                            } catch (DateException e2) {
                                e2.printStackTrace();
                            }
                        }

                        String dateStr;
                        DateTime newDate = DateUtil.parse(newDateStr, DatePattern.NORM_DATETIME_PATTERN);
                        if (oldDate != null && newDate.after(oldDate)) {
                            dateStr = "date: " + DateUtil.format(oldDate, DatePattern.NORM_DATETIME_PATTERN);
                        } else {
                            dateStr = "date: " + newDateStr;
                        }
                        lines.set(i, dateStr);
                        // System.out.println(StrUtil.format("【{}】{}", filename, dateStr));
                        break;
                    } else {
                        System.err.println(StrUtil.format("【{}】未获得第一次git提交时间", filename));
                    }

                }
            }

            List<String> newLines =
                lines.stream().filter(i -> !i.startsWith("permalink: ")).collect(Collectors.toList());
            FileUtil.writeUtf8Lines(newLines, f);
            // System.out.println(newLines);

        }
    }

}
