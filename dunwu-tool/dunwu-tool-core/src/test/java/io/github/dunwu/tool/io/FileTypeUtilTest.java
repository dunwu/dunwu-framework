package io.github.dunwu.tool.io;

import io.github.dunwu.tool.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 文件类型判断单元测试
 *
 * @author Looly
 */
public class FileTypeUtilTest {

    @Test
    @Disabled
    public void fileTypeUtilTest() {
        File file = FileUtil.file("hutool.jpg");
        String type = FileTypeUtil.getType(file);
        Assertions.assertEquals("jpg", type);

        FileTypeUtil.putFileType("ffd8ffe000104a464946", "new_jpg");
        String newType = FileTypeUtil.getType(file);
        Assertions.assertEquals("new_jpg", newType);
    }

    @Test
    @Disabled
    public void emptyTest() {
        File file = FileUtil.file("d:/empty.txt");
        String type = FileTypeUtil.getType(file);
        Console.log(type);
    }

    @Test
    @Disabled
    public void docTest() {
        File file = FileUtil.file("f:/test/test.doc");
        String type = FileTypeUtil.getType(file);
        Console.log(type);
    }

}
