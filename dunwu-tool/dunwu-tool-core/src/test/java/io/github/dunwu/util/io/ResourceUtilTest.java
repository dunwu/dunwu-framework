package io.github.dunwu.util.io;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import io.github.dunwu.util.base.StringExtUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceUtilTest {

    @Test
    public void resourceNameTest() throws IOException {
        JarFile guavaFile = new JarFile(FileExtUtil.getJarPath(Files.class));
        assertThat(guavaFile.getEntry("META-INF/MANIFEST.MF")).isNotNull();
        assertThat(guavaFile.getEntry("/META-INF/MANIFEST.MF")).isNull();
        guavaFile.close();
    }

    @Test
    public void test() throws IOException {
        // getResoruce
        assertThat(ResourceUtil.toString("test.txt")).contains("ABCDEFG");
        assertThat(ResourceUtil.toString(ResourceUtilTest.class, "/test.txt"))
            .contains("ABCDEFG");
        assertThat(ResourceUtil.toLines("test.txt")).containsExactly("ABCDEFG", "ABC");
        assertThat(ResourceUtil.toLines(ResourceUtilTest.class, "/test.txt"))
            .containsExactly("ABCDEFG", "ABC");

        // getResoruce 处理重复的资源
        System.out.println(ResourceUtil.getResource("META-INF/MANIFEST.MF"));
        assertThat(ResourceUtil.toString("META-INF/MANIFEST.MF")).contains("Manifest");

        // getResources
        assertThat(ResourceUtil.getResourcesQuietly("META-INF/MANIFEST.MF").size())
            .isGreaterThan(1);

        System.out.println(ResourceUtil.getResourcesQuietly("META-INF/MANIFEST.MF"));

        assertThat(ResourceUtil.getResourcesQuietly("META-INF/MANIFEST.MF",
            ResourceUtilTest.class.getClassLoader()).size()).isGreaterThan(1);
    }

    @Test
    void file() throws IOException {
        File file = FileExtUtil.createTempFile().toFile();
        FileExtUtil.write(file, "haha");

        File file2 = ResourceUtil.toFile("file://" + file.getAbsolutePath());
        assertThat(FileExtUtil.readAllLines(file2)).contains("haha");

        File file3 = ResourceUtil.toFile(file.getAbsolutePath());
        String result3 = StringExtUtil.join(FileExtUtil.readAllLines(file3));
        assertThat(result3).contains("haha");

        File file2NotExist = ResourceUtil.toFile("file://" + file.getAbsolutePath() + ".noexist");
        assertThat(file2NotExist.exists()).isFalse();

        File file3NotExist = ResourceUtil.toFile(file.getAbsolutePath() + ".noexist");
        assertThat(file3NotExist.exists()).isFalse();

        FileExtUtil.deleteQuietly(file);
    }

    @Test
    void resource() throws IOException {
        File file = ResourceUtil.toFile("classpath://application.properties");
        assertThat(FileExtUtil.readAllLines(file)).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

        InputStream is = ResourceUtil.toInputStream("classpath://application.properties");
        InputStreamReader reader = new InputStreamReader(is);
        List<String> lines = CharStreams.readLines(reader);
        assertThat(lines).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

        try {
            ResourceUtil.toFile("classpath://notexist.properties");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }

        try {
            ResourceUtil.toInputStream("classpath://notexist.properties");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

}
