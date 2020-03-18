package io.github.dunwu.tool.io;

import io.github.dunwu.tool.util.CharsetUtil;
import io.github.dunwu.tool.util.RegexUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-16
 */
public class MarkdownFileFormatTest {

    public static final String MD_DIR_PATH = "D:\\Codes\\ZPTutorial\\javacore\\docs\\basics";

    public static final String MD_FILE_PATH = "D:\\Codes\\ZPTutorial\\javacore\\docs\\basics\\Java反射.md";

    @Test
    public void test() {
        List<File> files = FileUtil.loopFiles(MD_DIR_PATH, MarkdownFileFormatTest::isMarkdownFile);
        files.forEach(file -> {
            System.out.println(file);
            String context = FileUtil.readString(file, CharsetUtil.UTF_8);
            System.out.println(context);
        });
    }

    private static boolean isMarkdownFile(File file) {
        if (!FileUtil.isFile(file)) {
            return false;
        }
        return file.getPath().toLowerCase().endsWith(".md");
    }

    @Test
    public void test2() {
        File file = new File(MD_FILE_PATH);
        String context = FileUtil.readString(file, CharsetUtil.UTF_8);

        RegexUtil.replaceAll(context, "<div.*><img src=\".*\"/></div>",
            parameters -> "->" + parameters.group(0) + "<-");
        System.out.println(context);
    }

}
