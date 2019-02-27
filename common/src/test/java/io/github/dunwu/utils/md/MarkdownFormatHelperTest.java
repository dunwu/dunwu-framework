package io.github.dunwu.utils.md;

import io.github.dunwu.utils.io.FileUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author Zhang Peng
 * @date 2019-02-25
 */
@Disabled
public class MarkdownFormatHelperTest {

    public static final String SRC_DIR_PATH = "D:\\Codes\\ZP\\Others\\notes";
    public static final String DEST_DIR_PATH = "D:\\Codes\\ZP\\Others\\notes";

    public static final String SRC_FILE_PATH = "D:\\Codes\\ZP\\Others\\notes\\docs\\network\\physical\\README.md";
    public static final String DEST_FILE_PATH = "D:\\Codes\\ZP\\Others\\notes\\docs\\network\\physical\\README.md";

    public void convertMdFilesInDir(File root) {
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                convertMdFilesInDir(f);
            } else {
                String fileExtension = FileUtil.getFileExtension(f);
                if (fileExtension.equals("md")) {
                    String srcPath = f.getAbsolutePath();
                    String destPath = f.getAbsolutePath().replace(SRC_DIR_PATH, DEST_DIR_PATH);
                    System.out.println(srcPath);
                    System.out.println(destPath);
                    System.out.println();
                    MarkdownFormatHelper.convertToGFM(srcPath, destPath);
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
        MarkdownFormatHelper.convertToGFM(SRC_FILE_PATH, DEST_FILE_PATH);
    }
}
