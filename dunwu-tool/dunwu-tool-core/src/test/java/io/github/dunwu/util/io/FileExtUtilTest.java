package io.github.dunwu.util.io;

import io.github.dunwu.util.RandomExtUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("all")
class FileExtUtilTest {

    @Test
    void changeFileNameToStandard() {
        boolean flag = FileExtUtil.changeFileNameToStandard(
            "D:\\Codes\\ZP\\Others\\images\\images\\design\\refactor\\Change Bidirectional Association to "
                + "Unidirectional - After.png");
        System.out.println(flag);
    }

    @Test
    void changeFileNameToStandardInFolder() {
        FileExtUtil.changeFileNameToStandardInFolder(
            new File("D:\\Codes\\ZP\\Others\\images\\images\\design\\refactor"));
    }

    @Test
    void copy() throws Exception {
        Path tempDir = FileExtUtil.createTempDir();
        assertThat(tempDir).exists();

        String srcFileName = "src";
        File srcFile = tempDir.resolve(srcFileName).toFile();
        FileExtUtil.touch(srcFile);
        assertThat(srcFile).exists();

        FileExtUtil.write(srcFile, "test");

        String destFileName = "dest";
        File destFile = new File(tempDir.toFile(), "parent1/parent2/" + destFileName);
        FileExtUtil.makesureParentDirExists(destFile);

        Files.copy(srcFile.toPath(), destFile.toPath());
        List<String> lines = FileExtUtil.readAllLines(destFile);
        assertThat(lines.get(0)).isEqualTo("test");
    }

    @Test
    void fileExist() throws IOException {
        assertThat(FileExtUtil.isDirectoryExists(SystemUtils.JAVA_IO_TMPDIR)).isTrue();
        assertThat(FileExtUtil
            .isDirectoryExists(SystemUtils.JAVA_IO_TMPDIR + RandomUtils.nextInt()))
            .isFalse();

        File tmpFile = null;
        try {
            tmpFile = FileExtUtil.createTempFile().toFile();
            assertThat(FileExtUtil.isFileExists(tmpFile)).isTrue();

            assertThat(FileExtUtil
                .isFileExists(tmpFile.getAbsolutePath() + RandomUtils.nextInt()))
                .isFalse();
        } finally {
            FileExtUtil.deleteQuietly(tmpFile);
        }
    }

    @Test
    void getJarPath() {
        System.out.println("the jar file contains Files.class"
            + FileExtUtil.getJarPath(com.google.common.io.Files.class));
        assertThat(FileExtUtil.getJarPath(com.google.common.io.Files.class))
            .endsWith("guava-28.1-jre.jar");
    }

    @Test
    void getName() {

        assertThat(FileExtUtil.getFileName(FileExtUtil.normalizePath("/a/d/b/abc.txt")))
            .isEqualTo("abc.txt");
        assertThat(FileExtUtil.getFileName("abc.txt")).isEqualTo("abc.txt");

        assertThat(FileExtUtil
            .getFileExtension(FileExtUtil.normalizePath("a/d/b/abc.txt")))
            .isEqualTo("txt");
        assertThat(
            FileExtUtil.getFileExtension(FileExtUtil.normalizePath("/a/d/b/abc")))
            .isEqualTo("");
        assertThat(
            FileExtUtil.getFileExtension(FileExtUtil.normalizePath("/a/d/b/abc.")))
            .isEqualTo("");
    }

    @Test
    void listFile() throws IOException {
        File tmpDir = FileExtUtil.createTempDir().toFile();

        List<File> all = FileExtUtil.listAll(tmpDir);
        assertThat(all).hasSize(1);

        List<File> files = FileExtUtil.listFile(tmpDir);
        assertThat(files).hasSize(0);

        FileExtUtil.touch(FileExtUtil.concat(tmpDir.getAbsolutePath(),
            "tmp-" + RandomExtUtil.nextInt()) + ".tmp");
        FileExtUtil.touch(FileExtUtil.concat(tmpDir.getAbsolutePath(),
            "tmp-" + RandomExtUtil.nextInt()) + ".abc");

        String childDir = FileExtUtil.concat(tmpDir.getAbsolutePath(),
            "tmp-" + RandomExtUtil.nextInt());
        FileExtUtil.makesureDirExists(childDir);

        FileExtUtil.touch(FileExtUtil.concat(childDir, "tmp-" + RandomExtUtil.nextInt()) + ".tmp");

        all = FileExtUtil.listAll(tmpDir);
        assertThat(all).hasSize(5);

        files = FileExtUtil.listFile(tmpDir);
        assertThat(files).hasSize(3);

        // extension
        files = FileExtUtil.listFileWithExtension(tmpDir, "tmp");
        assertThat(files).hasSize(2);

        files = FileExtUtil.listFileWithExtension(tmpDir, "tp");
        assertThat(files).hasSize(0);

        // wildcard
        files = FileExtUtil.listFileWithWildcardFileName(tmpDir, "*.tmp");
        assertThat(files).hasSize(2);
        files = FileExtUtil.listFileWithWildcardFileName(tmpDir, "*.tp");
        assertThat(files).hasSize(0);

        // regex
        files = FileExtUtil.listFileWithRegexFileName(tmpDir, ".*\\.tmp");
        assertThat(files).hasSize(2);
        files = FileExtUtil.listFileWithRegexFileName(tmpDir, ".*\\.tp");
        assertThat(files).hasSize(0);

        FileExtUtil.deleteDirectory(tmpDir);
        tmpDir.delete();
        assertThat(tmpDir.exists()).isFalse();
    }

    @Test
    void newBufferedReader() throws Exception {
        Path path = FileExtUtil.createTempFile();
        assertThat(Files.newBufferedReader(path)).isNotNull();
    }

    @Test
    void newBufferedWriter() throws Exception {
        Path path = FileExtUtil.createTempFile();
        assertThat(Files.newBufferedWriter(path)).isNotNull();
    }

    @Test
    void newInputStream() throws Exception {
        Path path = FileExtUtil.createTempFile();
        assertThat(Files.newInputStream(path)).isNotNull();
    }

    @Test
    void newOutputStream() throws Exception {
        Path path = FileExtUtil.createTempFile();
        assertThat(Files.newOutputStream(path)).isNotNull();
    }

    @Test
    void opDir() throws IOException {
        String fileName = "testFile" + RandomUtils.nextInt();
        File dir = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir"));
        File file = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir", fileName));
        String content = "haha\nhehe";
        FileExtUtil.makesureDirExists(dir);
        FileExtUtil.write(file, content);

        File dir2 = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir2"));
        FileExtUtil.copyDirectory(dir, dir2);
        File file2 = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir2", fileName));
        assertThat(FileExtUtil.readAllLines(file2)).contains("haha", "hehe");

        File dir3 = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir3"));
        FileExtUtil.deleteQuietly(dir3);
        FileExtUtil.moveDirectory(dir, dir3);
        File file3 = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir3", fileName));
        assertThat(FileExtUtil.readString(file3)).isEqualTo("haha\nhehe");
        assertThat(FileExtUtil.isDirectoryExists(dir)).isFalse();
    }

    @Test
    void opFiles() throws IOException {
        File file = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR,
            "testFile" + RandomUtils.nextInt()));
        FileExtUtil.touch(file);
        assertThat(FileExtUtil.isFileExists(file)).isTrue();
        FileExtUtil.touch(file);

        String content = "haha\nhehe";
        FileExtUtil.write(file, content);
        assertThat(FileExtUtil.readString(file))
            .isEqualTo(content);

        File newFile = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR,
            "testFile" + RandomUtils.nextInt()));
        File newFile2 = new File(FileExtUtil.concat(SystemUtils.JAVA_IO_TMPDIR,
            "testFile" + RandomUtils.nextInt()));

        Files.copy(file.toPath(), newFile.toPath());
        assertThat(FileExtUtil.isFileExists(newFile)).isTrue();
        assertThat(FileExtUtil.readString(newFile)).isEqualTo(content);

        com.google.common.io.Files.move(newFile, newFile2);
        assertThat(FileExtUtil.readString(newFile2)).isEqualTo("haha\nhehe");
    }

    @Test
    void pathName() {
        String filePath = FileExtUtil.concat(File.separatorChar + "abc", "ef");
        assertThat(filePath).isEqualTo(FileExtUtil.normalizePath("/abc/ef"));

        String filePath2 = FileExtUtil
            .concat(File.separatorChar + "stuv" + File.separatorChar, "xy");
        assertThat(filePath2).isEqualTo(FileExtUtil.normalizePath("/stuv/xy"));

        assertThat(com.google.common.io.Files.simplifyPath("../dd/../abc"))
            .isEqualTo("../abc");
        assertThat(com.google.common.io.Files.simplifyPath("../../dd/../abc"))
            .isEqualTo("../../abc");
        assertThat(com.google.common.io.Files.simplifyPath("./abc")).isEqualTo("abc");

        assertThat(FileExtUtil.getParentPath(FileExtUtil.normalizePath("/abc/dd/efg/")))
            .isEqualTo(FileExtUtil.normalizePath("/abc/dd/"));

        assertThat(
            FileExtUtil.getParentPath(FileExtUtil.normalizePath("/abc/dd/efg.txt")))
            .isEqualTo(FileExtUtil.normalizePath("/abc/dd/"));
    }

    @Test
    void readWrite() throws IOException {
        File file = FileExtUtil.createTempFile("abc", ".tmp").toFile();
        try {
            String content = "haha\nhehe";
            FileExtUtil.write(file, content);

            String result = FileExtUtil.readString(file);
            assertThat(result).isEqualTo(content);
            List<String> lines = FileExtUtil.readAllLines(file);
            assertThat(lines).containsExactly("haha", "hehe");

            FileExtUtil.append("kaka", file);
            assertThat(
                new String(Files.readAllBytes(file.toPath())))
                .isEqualTo("haha\nhehekaka");
        } finally {
            FileExtUtil.deleteQuietly(file);
        }
    }

    @Test
    void testGetFileExtension() throws Exception {
        Path path = FileExtUtil.createTempFile("aaa", ".txt");

        assertThat(FileExtUtil.getFileExtension(path.toFile())).isEqualTo("txt");
        assertThat(FileExtUtil.getFileExtension(path.toString())).isEqualTo("txt");
    }

    @Test
    void testIsDirExists() throws Exception {
        try {
            FileExtUtil.isDirectoryExists((String) null);
            FileExtUtil.isDirectoryExists((File) null);
            FileExtUtil.isDirectoryExists((Path) null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }

        // assertThat(FileExtUtils.isDirectoryExists((String) null)).isFalse();
        // assertThat(FileExtUtils.isDirectoryExists((File) null)).isFalse();
        // assertThat(FileExtUtils.isDirectoryExists((Path) null)).isFalse();

        Path dir = FileExtUtil.createTempDir();

        assertThat(FileExtUtil.isDirectoryExists(dir)).isTrue();
        assertThat(FileExtUtil.isDirectoryExists(dir.toString())).isTrue();
        assertThat(FileExtUtil.isDirectoryExists(dir.toFile())).isTrue();
    }

    @Test
    void testIsFileExists() throws Exception {
        try {
            FileExtUtil.isFileExists((String) null);
            FileExtUtil.isFileExists((File) null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }

        Path dir = FileExtUtil.createTempDir();
        FileExtUtil.touch(dir + "/" + "test");

        assertThat(FileExtUtil.isFileExists(dir + "/" + "test")).isTrue();
        assertThat(FileExtUtil.isFileExists(dir.resolve("test").toFile())).isTrue();
    }

    @Test
    void testMakesureDirExists() throws Exception {
        Path dir = FileExtUtil.createTempDir();
        String child1 = "child1";

        Path child1Dir = dir.resolve(child1);
        FileExtUtil.makesureDirExists(child1Dir.toString());
        assertThat(child1Dir).exists();

        String child2 = "child2";
        Path child2Dir = dir.resolve(child2);
        FileExtUtil.makesureDirExists(child2Dir);
        assertThat(child2Dir).exists();

        String child3 = "child3";
        Path child3Dir = dir.resolve(child3);
        FileExtUtil.makesureDirExists(child3Dir.toFile());
        assertThat(child3Dir).exists();
    }

}
