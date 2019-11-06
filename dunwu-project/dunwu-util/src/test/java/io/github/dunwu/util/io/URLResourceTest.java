package io.github.dunwu.util.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class URLResourceTest {

	@Test
	public void file() throws IOException {
		File file = FileExtUtils.createTempFile().toFile();
		FileExtUtils.write(file, "haha", StandardCharsets.UTF_8);
		try {
			File file2 = UrlResourceUtil.asFile("file://" + file.getAbsolutePath());
			assertThat(FileExtUtils.readFileToString(file2, StandardCharsets.UTF_8))
				.isEqualTo("haha");

			File file2NotExist = UrlResourceUtil
				.asFile("file://" + file.getAbsolutePath() + ".noexist");

			File file3 = UrlResourceUtil.asFile(file.getAbsolutePath());
			assertThat(FileExtUtils.readFileToString(file3, StandardCharsets.UTF_8))
				.isEqualTo("haha");
			File file3NotExist = UrlResourceUtil
				.asFile(file.getAbsolutePath() + ".noexist");
		} finally {
			FileExtUtils.deleteFile(file);
		}
	}

	@Test
	public void resource() throws IOException {
		File file = UrlResourceUtil.asFile("classpath://application.properties");
		assertThat(FileExtUtils.readFileToString(file, StandardCharsets.UTF_8))
			.isEqualTo("springside.min=1\nspringside.max=10");

		InputStream is = UrlResourceUtil.asStream("classpath://application.properties");
		assertThat(IOExtUtils.readString(is))
			.isEqualTo("springside.min=1\nspringside.max=10");
		IOExtUtils.closeQuietly(is);

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

}
