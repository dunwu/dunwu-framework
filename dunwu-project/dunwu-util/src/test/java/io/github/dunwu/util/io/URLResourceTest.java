package io.github.dunwu.util.io;

import com.google.common.io.CharStreams;
import io.github.dunwu.util.base.StringExtUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class URLResourceTest {

	@Test
	void file() throws IOException {
		File file = FileExtUtils.createTempFile().toFile();
		FileExtUtils.write(file, "haha");

		File file2 = UrlResourceUtil.asFile("file://" + file.getAbsolutePath());
		assertThat(FileExtUtils.readAllLines(file2)).contains("haha");

		File file3 = UrlResourceUtil.asFile(file.getAbsolutePath());
		String result3 = StringExtUtils.join(FileExtUtils.readAllLines(file3));
		assertThat(result3).contains("haha");

		File file2NotExist = UrlResourceUtil.asFile("file://" + file.getAbsolutePath() + ".noexist");
		assertThat(file2NotExist.exists()).isFalse();

		File file3NotExist = UrlResourceUtil.asFile(file.getAbsolutePath() + ".noexist");
		assertThat(file3NotExist.exists()).isFalse();

		FileExtUtils.deleteQuietly(file);
	}

	@Test
	void resource() throws IOException {
		File file = UrlResourceUtil.asFile("classpath://application.properties");
		assertThat(FileExtUtils.readAllLines(file)).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

		InputStream is = UrlResourceUtil.asStream("classpath://application.properties");
		InputStreamReader reader = new InputStreamReader(is);
		List<String> lines = CharStreams.readLines(reader);
		assertThat(lines).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

		try {
			UrlResourceUtil.asFile("classpath://notexist.properties");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}

		try {
			UrlResourceUtil.asStream("classpath://notexist.properties");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

}
