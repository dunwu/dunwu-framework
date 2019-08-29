package io.github.dunwu.util.io;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class URLResourceTest {

    @Test
    public void resource() throws IOException {
        File file = UrlResourceUtil.asFile("classpath://application.properties");
        assertThat(FileUtil.toString(file)).isEqualTo("springside.min=1\nspringside.max=10");

        InputStream is = UrlResourceUtil.asStream("classpath://application.properties");
        assertThat(ReadWriteUtil.toString(is)).isEqualTo("springside.min=1\nspringside.max=10");
        ReadWriteUtil.closeQuietly(is);

        try {
            UrlResourceUtil.asFile("classpath://notexist.properties");
            fail("should fail");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
        }

        try {
            UrlResourceUtil.asStream("classpath://notexist.properties");
            fail("should fail");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Test
    public void file() throws IOException {
        File file = FileUtil.createTempFile().toFile();
        FileUtil.write("haha", file);
        try {
            File file2 = UrlResourceUtil.asFile("file://" + file.getAbsolutePath());
            assertThat(FileUtil.toString(file2)).isEqualTo("haha");

            File file2NotExist = UrlResourceUtil.asFile("file://" + file.getAbsolutePath() + ".noexist");

            File file3 = UrlResourceUtil.asFile(file.getAbsolutePath());
            assertThat(FileUtil.toString(file3)).isEqualTo("haha");
            File file3NotExist = UrlResourceUtil.asFile(file.getAbsolutePath() + ".noexist");

        } finally {
            FileUtil.deleteFile(file);
        }

    }

}
