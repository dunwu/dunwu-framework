package io.github.dunwu.tool.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.git.GitUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-11
 */
public class FileUtilTest {

    public static final String MARKDOWN_EXT = "md";
    // 忽略的目录
    public static final String[] IGNORED_DIR_LIST =
        { ".git", ".idea", ".temp", "node_modules", "assets", ".github", "scripts", "settings" };

    @ParameterizedTest
    @DisplayName("刷新指定项目路径下的md文档的创建时间")
    @ValueSource(strings = { "D:\\Codes\\zp\\ztutorial\\zp-cs\\db-tutorial\\" })
    public void method(String path) {

        List<File> files = FileUtil.getAllExtFiles(path, IGNORED_DIR_LIST, MARKDOWN_EXT);

        for (File f : files) {
            String filename = f.getAbsolutePath().replace(path, "");
            List<String> lines = FileUtil.readUtf8Lines(f);
            if (CollectionUtil.isEmpty(lines)) {
                continue;
            }

            String firstLine = lines.get(0);
            if (firstLine.equals("---")) {
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith("date:")) {
                        String dateStr = "date: " + GitUtil.getFileFirstGitPushTime(path, filename);
                        System.out.println(StrUtil.format("【{}】{}", filename, dateStr));
                        lines.set(i, dateStr);
                        break;
                    }
                }
                FileUtil.writeUtf8Lines(lines, f);
            }

        }
    }


}
