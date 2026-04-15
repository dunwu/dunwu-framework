package io.github.dunwu.common.io;

import io.github.dunwu.common.exception.DefaultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link FileUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@DisplayName("FileUtil 单元测试")
class FileUtilTest {

    @TempDir
    Path tempDir;

    // ============================== getExtension ==============================

    @Nested
    @DisplayName("getExtension 测试")
    class GetExtensionTest {

        @Test
        @DisplayName("不含 dot：返回不带点的扩展名")
        void shouldReturnExtensionWithoutDot() {
            assertThat(FileUtil.getExtension("photo.jpg")).isEqualTo("jpg");
            assertThat(FileUtil.getExtension("document.PDF")).isEqualTo("PDF");
            assertThat(FileUtil.getExtension("archive.tar.gz")).isEqualTo("gz");
        }

        @Test
        @DisplayName("不含 dot：文件名无扩展名时原文返回")
        void shouldReturnOriginalWhenNoExtension() {
            assertThat(FileUtil.getExtension("README")).isEqualTo("README");
        }

        @Test
        @DisplayName("不含 dot：文件名为空时原文返回")
        void shouldReturnOriginalWhenBlank() {
            assertThat(FileUtil.getExtension("")).isEqualTo("");
            assertThat(FileUtil.getExtension(null)).isNull();
        }

        @Test
        @DisplayName("含 dot：返回带点的扩展名")
        void shouldReturnExtensionWithDot() {
            assertThat(FileUtil.getExtension("photo.jpg", true)).isEqualTo(".jpg");
            assertThat(FileUtil.getExtension("archive.tar.gz", true)).isEqualTo(".gz");
        }

        @Test
        @DisplayName("含 dot：文件名无扩展名时原文返回")
        void shouldReturnOriginalWhenNoExtensionWithDot() {
            assertThat(FileUtil.getExtension("README", true)).isEqualTo("README");
        }

        @Test
        @DisplayName("不含 dot（false）：与单参数重载结果一致")
        void shouldMatchSingleParamOverloadWhenWithDotIsFalse() {
            assertThat(FileUtil.getExtension("photo.png", false)).isEqualTo(FileUtil.getExtension("photo.png"));
        }

    }

    // ============================== getExtensionFromUrl ==============================

    @Nested
    @DisplayName("getExtensionFromUrl 测试")
    class GetExtensionFromUrlTest {

        @Test
        @DisplayName("普通 URL 不含 dot：返回扩展名")
        void shouldReturnExtensionFromSimpleUrl() throws DefaultException {
            assertThat(FileUtil.getExtensionFromUrl("http://example.com/image.jpg")).isEqualTo("jpg");
        }

        @Test
        @DisplayName("含查询参数的 URL：截掉 ? 后取扩展名")
        void shouldReturnExtensionFromUrlWithQueryParam() throws DefaultException {
            assertThat(FileUtil.getExtensionFromUrl("http://example.com/photo.png?v=1")).isEqualTo("png");
        }

        @Test
        @DisplayName("含 dot 参数：返回带点扩展名")
        void shouldReturnExtensionWithDotFromUrl() throws DefaultException {
            assertThat(FileUtil.getExtensionFromUrl("http://example.com/file.txt", true)).isEqualTo(".txt");
        }

        @Test
        @DisplayName("URL 为空时抛出 DefaultException")
        void shouldThrowWhenUrlIsBlank() {
            assertThatThrownBy(() -> FileUtil.getExtensionFromUrl("")).isInstanceOf(DefaultException.class);
            assertThatThrownBy(() -> FileUtil.getExtensionFromUrl(null)).isInstanceOf(DefaultException.class);
        }

        @Test
        @DisplayName("不含 dot（false）：与单参数重载结果一致")
        void shouldMatchSingleParamOverloadWhenWithDotIsFalse() throws DefaultException {
            String url = "http://example.com/doc.pdf";
            assertThat(FileUtil.getExtensionFromUrl(url, false)).isEqualTo(FileUtil.getExtensionFromUrl(url));
        }

    }

    // ============================== getFormatSize ==============================

    @Nested
    @DisplayName("getFormatSize 测试")
    class GetFormatSizeTest {

        @Test
        @DisplayName("字节级别：直接显示 B")
        void shouldFormatAsBytes() {
            assertThat(FileUtil.getFormatSize(512)).contains("B");
        }

        @Test
        @DisplayName("KB 级别：显示 KB")
        void shouldFormatAsKilobytes() {
            assertThat(FileUtil.getFormatSize(2048)).contains("KB");
        }

        @Test
        @DisplayName("MB 级别：显示 MB")
        void shouldFormatAsMegabytes() {
            assertThat(FileUtil.getFormatSize(2L * FileUtil.MB)).contains("MB");
        }

        @Test
        @DisplayName("GB 级别：显示 GB")
        void shouldFormatAsGigabytes() {
            assertThat(FileUtil.getFormatSize(2L * FileUtil.GB)).contains("GB");
        }

        @Test
        @DisplayName("精确 1 KB：格式化值为 1.00KB")
        void shouldFormatExactlyOneKb() {
            String result = FileUtil.getFormatSize(FileUtil.KB);
            assertThat(result).contains("1.00").contains("KB");
        }

        @Test
        @DisplayName("精确 1 MB：格式化值为 1.00MB")
        void shouldFormatExactlyOneMb() {
            String result = FileUtil.getFormatSize(FileUtil.MB);
            assertThat(result).contains("1.00").contains("MB");
        }

        @Test
        @DisplayName("精确 1 GB：格式化值为 1.00GB")
        void shouldFormatExactlyOneGb() {
            String result = FileUtil.getFormatSize(FileUtil.GB);
            assertThat(result).contains("1.00").contains("GB");
        }

        @Test
        @DisplayName("0 字节：显示 0B")
        void shouldFormatZeroBytes() {
            assertThat(FileUtil.getFormatSize(0)).contains("0B");
        }

    }

    // ============================== getFiles ==============================

    @Nested
    @DisplayName("getFiles 测试")
    class GetFilesTest {

        private Path subDir;
        private Path ignoredDir;

        @BeforeEach
        void setUp() throws IOException {
            // 创建目录结构：
            // tempDir/
            //   a.java
            //   b.txt
            //   sub/
            //     c.java
            //   ignored/
            //     d.java
            Files.createFile(tempDir.resolve("a.java"));
            Files.createFile(tempDir.resolve("b.txt"));
            subDir = Files.createDirectory(tempDir.resolve("sub"));
            Files.createFile(subDir.resolve("c.java"));
            ignoredDir = Files.createDirectory(tempDir.resolve("ignored"));
            Files.createFile(ignoredDir.resolve("d.java"));
        }

        @Test
        @DisplayName("递归查找所有 java 文件（不忽略任何目录）")
        void shouldFindAllJavaFilesRecursively() {
            List<File> files = FileUtil.getFiles(tempDir.toString(), "java");
            assertThat(files).hasSize(3);
            assertThat(files).extracting(File::getName).containsExactlyInAnyOrder("a.java", "c.java", "d.java");
        }

        @Test
        @DisplayName("忽略指定目录后不返回该目录下的文件")
        void shouldExcludeFilesInIgnoredDir() {
            List<File> files = FileUtil.getFiles(tempDir.toString(), "java", "ignored");
            assertThat(files).hasSize(2);
            assertThat(files).extracting(File::getName).containsExactlyInAnyOrder("a.java", "c.java");
        }

        @Test
        @DisplayName("后缀不存在时返回空列表")
        void shouldReturnEmptyWhenNoMatchingExt() {
            List<File> files = FileUtil.getFiles(tempDir.toString(), "xml");
            assertThat(files).isEmpty();
        }

        @Test
        @DisplayName("ext 大小写不敏感")
        void shouldMatchExtCaseInsensitively() {
            List<File> files = FileUtil.getFiles(tempDir.toString(), "JAVA");
            assertThat(files).hasSize(3);
        }

    }

    // ============================== binaryToText / textToBinary ==============================

    @Nested
    @DisplayName("binaryToText / textToBinary 互转测试")
    class BinaryTextConversionTest {

        private String binaryPath;
        private String textPath;
        private String restoredBinaryPath;

        @BeforeEach
        void setUp() throws IOException {
            binaryPath = tempDir.resolve("source.bin").toString();
            textPath = tempDir.resolve("converted.txt").toString();
            restoredBinaryPath = tempDir.resolve("restored.bin").toString();

            // 写入测试二进制内容
            byte[] data = {72, 101, 108, 108, 111}; // "Hello"
            Files.write(Path.of(binaryPath), data);
        }

        @Test
        @DisplayName("binaryToText：二进制文件转文本文件成功")
        void shouldConvertBinaryToTextSuccessfully() {
            FileUtil.binaryToText(binaryPath, textPath);
            assertThat(new File(textPath)).exists();
            String content = cn.hutool.core.io.FileUtil.readUtf8String(textPath);
            assertThat(content).isNotBlank();
            // 内容应为逗号分隔的数字
            assertThat(content).contains("72").contains("101");
        }

        @Test
        @DisplayName("binaryToText：源文件不存在时不抛出异常且不创建目标文件")
        void shouldNotThrowWhenSourceBinaryNotExists() {
            String nonExistentPath = tempDir.resolve("not_exist.bin").toString();
            // 应静默处理（内部打印错误并返回）
            FileUtil.binaryToText(nonExistentPath, textPath);
            assertThat(new File(textPath)).doesNotExist();
        }

        @Test
        @DisplayName("binaryToText：源文件不存在时不抛出异常且不创建目标文件")
        void demoTest() {
            FileUtil.binaryToText("E:\\Temp\\dunwu.part3.rar", "E:\\Temp\\Output.txt");
        }

        @Test
        @DisplayName("textToBinary：文本文件转二进制文件成功")
        void shouldConvertTextToBinarySuccessfully() {
            // 先生成文本文件
            FileUtil.binaryToText(binaryPath, textPath);
            // 再还原为二进制
            FileUtil.textToBinary(textPath, restoredBinaryPath);
            assertThat(new File(restoredBinaryPath)).exists();
        }

        @Test
        @DisplayName("binaryToText + textToBinary：往返转换内容一致")
        void shouldRestoreOriginalBinaryAfterRoundTrip() throws IOException {
            FileUtil.binaryToText(binaryPath, textPath);
            FileUtil.textToBinary(textPath, restoredBinaryPath);

            byte[] original = Files.readAllBytes(Path.of(binaryPath));
            byte[] restored = Files.readAllBytes(Path.of(restoredBinaryPath));
            assertThat(restored).isEqualTo(original);
        }

        @Test
        @DisplayName("textToBinary：源文件不存在时不抛出异常且不创建目标文件")
        void shouldNotThrowWhenSourceTextNotExists() {
            String nonExistentPath = tempDir.resolve("not_exist.txt").toString();
            FileUtil.textToBinary(nonExistentPath, restoredBinaryPath);
            assertThat(new File(restoredBinaryPath)).doesNotExist();
        }

    }

    // ============================== getValidPath ==============================

    @Nested
    @DisplayName("getValidPath 测试")
    class GetValidPathTest {

        @Test
        @DisplayName("合法图片后缀（jpg）：返回规范化路径")
        void shouldReturnCanonicalPathForAllowedJpg() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "avatar.jpg");
            assertThat(result).endsWith("avatar.jpg");
            assertThat(new File(result).isAbsolute()).isTrue();
        }

        @Test
        @DisplayName("合法图片后缀（png）：返回规范化路径")
        void shouldReturnCanonicalPathForAllowedPng() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "logo.png");
            assertThat(result).endsWith("logo.png");
        }

        @Test
        @DisplayName("合法图片后缀（gif）：返回规范化路径")
        void shouldReturnCanonicalPathForAllowedGif() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "anim.gif");
            assertThat(result).endsWith("anim.gif");
        }

        @Test
        @DisplayName("合法图片后缀（bmp）：返回规范化路径")
        void shouldReturnCanonicalPathForAllowedBmp() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "icon.bmp");
            assertThat(result).endsWith("icon.bmp");
        }

        @Test
        @DisplayName("合法图片后缀（jpeg）：返回规范化路径")
        void shouldReturnCanonicalPathForAllowedJpeg() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "photo.jpeg");
            assertThat(result).endsWith("photo.jpeg");
        }

        @Test
        @DisplayName("大写后缀（JPG）：大小写不敏感，返回路径")
        void shouldAcceptUpperCaseExtension() throws Exception {
            String dirPath = tempDir.toString() + File.separator;
            String result = FileUtil.getValidPath(dirPath, "PHOTO.JPG");
            assertThat(result).isNotBlank();
        }

        @Test
        @DisplayName("非法后缀（txt）：抛出 IllegalAccessException")
        void shouldThrowForDisallowedTxtExtension() {
            String dirPath = tempDir.toString() + File.separator;
            assertThatThrownBy(() -> FileUtil.getValidPath(dirPath, "readme.txt")).isInstanceOf(
                IllegalAccessException.class).hasMessageContaining("文件后缀不合法");
        }

        @Test
        @DisplayName("非法后缀（exe）：抛出 IllegalAccessException")
        void shouldThrowForDisallowedExeExtension() {
            String dirPath = tempDir.toString() + File.separator;
            assertThatThrownBy(() -> FileUtil.getValidPath(dirPath, "setup.exe")).isInstanceOf(
                IllegalAccessException.class);
        }

        @Test
        @DisplayName("dirPath 为空、fileName 含完整路径：仍可正常解析")
        void shouldHandleBlankDirPath() throws Exception {
            String fullPath = tempDir.toString() + File.separator + "test.jpg";
            String result = FileUtil.getValidPath("", fullPath);
            assertThat(result).endsWith("test.jpg");
        }

    }

}
