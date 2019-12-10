package io.github.dunwu.util.io;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import io.github.dunwu.util.base.StringExtUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceUtilsTest {

	@Test
	public void resourceNameTest() throws IOException {
		JarFile guavaFile = new JarFile(FileExtUtils.getJarPath(Files.class));
		assertThat(guavaFile.getEntry("META-INF/MANIFEST.MF")).isNotNull();
		assertThat(guavaFile.getEntry("/META-INF/MANIFEST.MF")).isNull();
		guavaFile.close();
	}

	@Test
	public void test() throws IOException {
		// getResoruce
		assertThat(ResourceUtils.toString("test.txt")).contains("ABCDEFG");
		assertThat(ResourceUtils.toString(ResourceUtilsTest.class, "/test.txt"))
			.contains("ABCDEFG");
		assertThat(ResourceUtils.toLines("test.txt")).containsExactly("ABCDEFG", "ABC");
		assertThat(ResourceUtils.toLines(ResourceUtilsTest.class, "/test.txt"))
			.containsExactly("ABCDEFG", "ABC");

		// getResoruce 处理重复的资源
		System.out.println(ResourceUtils.getResource("META-INF/MANIFEST.MF"));
		assertThat(ResourceUtils.toString("META-INF/MANIFEST.MF")).contains("Manifest");

		// getResources
		assertThat(ResourceUtils.getResourcesQuietly("META-INF/MANIFEST.MF").size())
			.isGreaterThan(1);

		System.out.println(ResourceUtils.getResourcesQuietly("META-INF/MANIFEST.MF"));

		assertThat(ResourceUtils.getResourcesQuietly("META-INF/MANIFEST.MF",
			ResourceUtilsTest.class.getClassLoader()).size()).isGreaterThan(1);
	}

	@Test
	void file() throws IOException {
		File file = FileExtUtils.createTempFile().toFile();
		FileExtUtils.write(file, "haha");

		File file2 = ResourceUtils.toFile("file://" + file.getAbsolutePath());
		assertThat(FileExtUtils.readAllLines(file2)).contains("haha");

		File file3 = ResourceUtils.toFile(file.getAbsolutePath());
		String result3 = StringExtUtils.join(FileExtUtils.readAllLines(file3));
		assertThat(result3).contains("haha");

		File file2NotExist = ResourceUtils.toFile("file://" + file.getAbsolutePath() + ".noexist");
		assertThat(file2NotExist.exists()).isFalse();

		File file3NotExist = ResourceUtils.toFile(file.getAbsolutePath() + ".noexist");
		assertThat(file3NotExist.exists()).isFalse();

		FileExtUtils.deleteQuietly(file);
	}

	@Test
	void resource() throws IOException {
		File file = ResourceUtils.toFile("classpath://application.properties");
		assertThat(FileExtUtils.readAllLines(file)).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

		InputStream is = ResourceUtils.toInputStream("classpath://application.properties");
		InputStreamReader reader = new InputStreamReader(is);
		List<String> lines = CharStreams.readLines(reader);
		assertThat(lines).contains("dunwu.timezone = GMT+8", "dunwu.encoding = utf-8");

		try {
			ResourceUtils.toFile("classpath://notexist.properties");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}

		try {
			ResourceUtils.toInputStream("classpath://notexist.properties");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

}
