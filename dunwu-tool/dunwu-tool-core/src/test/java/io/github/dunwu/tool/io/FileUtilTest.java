package io.github.dunwu.tool.io;

import io.github.dunwu.tool.io.file.LineSeparator;
import io.github.dunwu.tool.lang.Console;
import io.github.dunwu.tool.util.CharsetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * {@link FileUtil} 单元测试类
 *
 * @author Looly
 */
public class FileUtilTest {

    @Test
    public void fileTest() {
        File file = FileUtil.file("d:/aaa", "bbb");
        Assertions.assertNotNull(file);

        // 构建目录中出现非子目录抛出异常
        FileUtil.file(file, "../ccc");

        FileUtil.file("E:/");
    }

    @Test
    public void getAbsolutePathTest() {
        String absolutePath = FileUtil.getAbsolutePath("LICENSE-junit.txt");
        Assertions.assertNotNull(absolutePath);
        String absolutePath2 = FileUtil.getAbsolutePath(absolutePath);
        Assertions.assertNotNull(absolutePath2);
        Assertions.assertEquals(absolutePath, absolutePath2);

        String path = FileUtil.getAbsolutePath("中文.xml");
        Assertions.assertTrue(path.contains("中文.xml"));

        path = FileUtil.getAbsolutePath("d:");
        Assertions.assertEquals("d:", path);
    }

    @Test
    @Disabled
    public void touchTest() {
        FileUtil.touch("d:\\tea\\a.jpg");
    }

    @Test
    @Disabled
    public void delTest() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileUtil.del("e:/Hutool_test_3434543533409843.txt");
        Assertions.assertTrue(result);
    }

    @Test
    @Disabled
    public void delTest2() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileUtil.del(Paths.get("e:/Hutool_test_3434543533409843.txt"));
        Assertions.assertTrue(result);
    }

    @Test
    @Disabled
    public void renameTest() {
        FileUtil.rename(FileUtil.file("hutool.jpg"), "b.png", false, false);
    }

    @Test
    public void copyTest() {
        File srcFile = FileUtil.file("hutool.jpg");
        File destFile = FileUtil.file("hutool.copy.jpg");

        FileUtil.copy(srcFile, destFile, true);

        Assertions.assertTrue(destFile.exists());
        Assertions.assertEquals(srcFile.length(), destFile.length());
    }

    @Test
    @Disabled
    public void copyFilesFromDir() {
        File srcFile = FileUtil.file("D:\\驱动");
        File destFile = FileUtil.file("d:\\驱动备份");

        FileUtil.copyFilesFromDir(srcFile, destFile, true);
    }

    @Test
    public void equlasTest() {
        // 源文件和目标文件都不存在
        File srcFile = FileUtil.file("d:/hutool.jpg");
        File destFile = FileUtil.file("d:/hutool.jpg");

        boolean equals = FileUtil.equals(srcFile, destFile);
        Assertions.assertTrue(equals);

        // 源文件存在，目标文件不存在
        File srcFile1 = FileUtil.file("hutool.jpg");
        File destFile1 = FileUtil.file("d:/hutool.jpg");

        boolean notEquals = FileUtil.equals(srcFile1, destFile1);
        Assertions.assertFalse(notEquals);
    }

    @Test
    @Disabled
    public void convertLineSeparatorTest() {
        FileUtil.convertLineSeparator(FileUtil.file("d:/aaa.txt"), CharsetUtil.CHARSET_UTF_8, LineSeparator.WINDOWS);
    }

    @Test
    public void normalizeTest() {
        Assertions.assertEquals("/foo/", FileUtil.normalize("/foo//"));
        Assertions.assertEquals("/foo/", FileUtil.normalize("/foo/./"));
        Assertions.assertEquals("/bar", FileUtil.normalize("/foo/../bar"));
        Assertions.assertEquals("/bar/", FileUtil.normalize("/foo/../bar/"));
        Assertions.assertEquals("/baz", FileUtil.normalize("/foo/../bar/../baz"));
        Assertions.assertEquals("/", FileUtil.normalize("/../"));
        Assertions.assertEquals("foo", FileUtil.normalize("foo/bar/.."));
        Assertions.assertEquals("bar", FileUtil.normalize("foo/../../bar"));
        Assertions.assertEquals("bar", FileUtil.normalize("foo/../bar"));
        Assertions.assertEquals("/server/bar", FileUtil.normalize("//server/foo/../bar"));
        Assertions.assertEquals("/bar", FileUtil.normalize("//server/../bar"));
        Assertions.assertEquals("C:/bar", FileUtil.normalize("C:\\foo\\..\\bar"));
        Assertions.assertEquals("C:/bar", FileUtil.normalize("C:\\..\\bar"));
        Assertions.assertEquals("bar", FileUtil.normalize("../../bar"));
        Assertions.assertEquals("C:/bar", FileUtil.normalize("/C:/bar"));
        Assertions.assertEquals("C:", FileUtil.normalize("C:"));

        Assertions.assertEquals("\\/192.168.1.1/Share/", FileUtil.normalize("\\\\192.168.1.1\\Share\\"));
    }

    @Test
    public void normalizeHomePathTest() {
        String home = FileUtil.getUserHomePath().replace('\\', '/');
        Assertions.assertEquals(home + "/bar/", FileUtil.normalize("~/foo/../bar/"));
    }

    @Test
    public void normalizeClassPathTest() {
        Assertions.assertEquals("", FileUtil.normalize("classpath:"));
    }

    @Test
    public void doubleNormalizeTest() {
        String normalize = FileUtil.normalize("/aa/b:/c");
        String normalize2 = FileUtil.normalize(normalize);
        Assertions.assertEquals("/aa/b:/c", normalize);
        Assertions.assertEquals(normalize, normalize2);
    }

    @Test
    public void subPathTest() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path subPath = FileUtil.subPath(path, 5, 4);
        Assertions.assertEquals("eee", subPath.toString());
        subPath = FileUtil.subPath(path, 0, 1);
        Assertions.assertEquals("aaa", subPath.toString());
        subPath = FileUtil.subPath(path, 1, 0);
        Assertions.assertEquals("aaa", subPath.toString());

        // 负数
        subPath = FileUtil.subPath(path, -1, 0);
        Assertions.assertEquals("aaa/bbb/ccc/ddd/eee", subPath.toString().replace('\\', '/'));
        subPath = FileUtil.subPath(path, -1, Integer.MAX_VALUE);
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileUtil.subPath(path, -1, path.getNameCount());
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileUtil.subPath(path, -2, -3);
        Assertions.assertEquals("ddd", subPath.toString());
    }

    @Test
    public void subPathTest2() {
        String subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc/test.txt");
        Assertions.assertEquals("ccc/test.txt", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb");
        Assertions.assertEquals("", subPath);

        subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb");
        Assertions.assertEquals("", subPath);
    }

    @Test
    public void getPathEle() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path ele = FileUtil.getPathEle(path, -1);
        Assertions.assertEquals("fff", ele.toString());
        ele = FileUtil.getPathEle(path, 0);
        Assertions.assertEquals("aaa", ele.toString());
        ele = FileUtil.getPathEle(path, -5);
        Assertions.assertEquals("bbb", ele.toString());
        ele = FileUtil.getPathEle(path, -6);
        Assertions.assertEquals("aaa", ele.toString());
    }

    @Test
    public void listFileNamesTest() {
        List<String> names = FileUtil.listFileNames("classpath:");
        Assertions.assertTrue(names.contains("hutool.jpg"));

        names = FileUtil.listFileNames("");
        Assertions.assertTrue(names.contains("hutool.jpg"));

        names = FileUtil.listFileNames(".");
        Assertions.assertTrue(names.contains("hutool.jpg"));
    }

    @Test
    @Disabled
    public void listFileNamesTest2() {
        List<String> names = FileUtil.listFileNames(
            "D:\\m2_repo\\commons-cli\\commons-cli\\1.0\\commons-cli-1.0.jar!org/apache/commons/cli/");
        for (String string : names) {
            Console.log(string);
        }
    }

    @Test
    @Disabled
    public void loopFilesTest() {
        List<File> files = FileUtil.loopFiles("d:/");
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    @Disabled
    public void loopFilesWithDepthTest() {
        List<File> files = FileUtil.loopFiles(FileUtil.file("d:/m2_repo"), 2, null);
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void getParentTest() {
        // 只在Windows下测试
        if (FileUtil.isWindows()) {
            File parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 0);
            Assertions.assertEquals(FileUtil.file("d:\\aaa\\bbb\\cc\\ddd"), parent);

            parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 1);
            Assertions.assertEquals(FileUtil.file("d:\\aaa\\bbb\\cc"), parent);

            parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 2);
            Assertions.assertEquals(FileUtil.file("d:\\aaa\\bbb"), parent);

            parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 4);
            Assertions.assertEquals(FileUtil.file("d:\\"), parent);

            parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 5);
            Assertions.assertNull(parent);

            parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 10);
            Assertions.assertNull(parent);
        }
    }

    @Test
    public void lastIndexOfSeparatorTest() {
        String dir = "d:\\aaa\\bbb\\cc\\ddd";
        int index = FileUtil.lastIndexOfSeparator(dir);
        Assertions.assertEquals(13, index);

        String file = "ddd.jpg";
        int index2 = FileUtil.lastIndexOfSeparator(file);
        Assertions.assertEquals(-1, index2);
    }

    @Test
    public void getNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String name = FileUtil.getName(path);
        Assertions.assertEquals("ddd", name);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        name = FileUtil.getName(path);
        Assertions.assertEquals("ddd.jpg", name);
    }

    @Test
    public void mainNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileUtil.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileUtil.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileUtil.mainName(path);
        Assertions.assertEquals("ddd", mainName);
    }

    @Test
    public void extNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileUtil.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileUtil.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileUtil.extName(path);
        Assertions.assertEquals("jpg", mainName);
    }

    @Test
    public void getWebRootTest() {
        File webRoot = FileUtil.getWebRoot();
        Assertions.assertNotNull(webRoot);
        Assertions.assertEquals("hutool-core", webRoot.getName());
    }

    @Test
    public void getMimeTypeTest() {
        String mimeType = FileUtil.getMimeType("test2Write.jpg");
        Assertions.assertEquals("image/jpeg", mimeType);
    }

}
