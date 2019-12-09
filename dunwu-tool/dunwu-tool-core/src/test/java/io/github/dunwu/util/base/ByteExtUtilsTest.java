package io.github.dunwu.util.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/4
 */
class ByteExtUtilsTest {

	@Test
	void convert() {
		String word = "Hello World!";
		Byte[] bytes = ByteExtUtils.convertToObjectArray(word.getBytes());
		assertThat(bytes).isNotNull();

		byte[] bytes2 = ByteExtUtils.convertToPrimitiveArray(bytes);
		assertThat(bytes2).isNotNull();
	}

	@Test
	void bytesToLong() {
		byte[] bytes = { 0x1, 0x1, 0x1, 0x1 };
		Long value = ByteExtUtils.bytesToLong(bytes);
		System.out.println(value);
	}

}