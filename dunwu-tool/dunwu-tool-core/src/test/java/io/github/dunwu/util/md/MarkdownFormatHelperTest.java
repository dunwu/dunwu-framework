package io.github.dunwu.util.md;

import io.github.dunwu.util.io.FileExtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-02-25
 */
@Disabled
public class MarkdownFormatHelperTest {

    public static final String SRC_DIR_PATH = "D:\\Codes\\ZP\\Java\\spring-tutorial\\docs";

    public static final String DEST_DIR_PATH = "D:\\Codes\\ZP\\Java\\spring-tutorial\\docs";

    public static final String SRC_FILE_PATH = "D:\\Codes\\ZP\\Java\\javaweb\\docs\\theory\\load-balance.md";

    public static final String DEST_FILE_PATH = "D:\\Codes\\ZP\\Java\\javaweb\\docs\\theory\\load-balance.md";

    public void convertMdFilesInDir(File root) {
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                if (f.getName().contains("node_modules")) {
                    continue;
                }
                convertMdFilesInDir(f);
            } else {
                String fileExtension = FileExtUtil.getFileExtension(f);
                if (fileExtension.equals("md")) {
                    String srcPath = f.getAbsolutePath();
                    String destPath = f.getAbsolutePath().replace(SRC_DIR_PATH,
                        DEST_DIR_PATH);
                    System.out.println(srcPath);
                    System.out.println(destPath);
                    System.out.println();
                    MarkdownFormatHelper.convertToGfm(srcPath, destPath);
                }
            }
        }
    }

    @Test
    public void testConvertAllDir() {
        convertMdFilesInDir(new File(SRC_DIR_PATH));
    }

    @Test
    public void testConvertOneFile() {
        MarkdownFormatHelper.convertToGfm(SRC_FILE_PATH, DEST_FILE_PATH);
    }

}
