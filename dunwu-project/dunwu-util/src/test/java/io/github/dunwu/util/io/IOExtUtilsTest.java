package io.github.dunwu.util.io;

import io.github.dunwu.util.io.type.StringBuilderWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class IOExtUtilsTest {

	@Test
	void read() throws IOException {
		assertThat(IOExtUtils.readString(ResourceUtil.asStream("test.txt")))
			.isEqualTo("ABCDEFG\nABC");
		assertThat(IOExtUtils.readLines(ResourceUtil.asStream("test.txt"),
			StandardCharsets.UTF_8)).hasSize(2).containsExactly("ABCDEFG", "ABC");
	}

	@Test
	void write() throws IOException {
		String word = "Hello World";
		StringBuilderWriter sbw = new StringBuilderWriter();
		IOExtUtils.write(word, sbw);
		assertThat(sbw.toString()).isEqualTo(word);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOExtUtils.write(word, baos, StandardCharsets.UTF_8);
		assertThat(new String(baos.toByteArray(), StandardCharsets.UTF_8))
			.isEqualTo(word);
		baos.close();
	}

}
