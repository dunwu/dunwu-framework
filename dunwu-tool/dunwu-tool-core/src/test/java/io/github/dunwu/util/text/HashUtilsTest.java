package io.github.dunwu.util.text;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HashUtilsTest {

	@Test
	public void crc32() {
		assertThat(HashUtils.crc32AsInt("hahhha1")).isEqualTo(-625925593);
		assertThat(HashUtils.crc32AsInt("hahhha1".getBytes())).isEqualTo(-625925593);
		assertThat(HashUtils.crc32AsInt("hahhha2")).isEqualTo(1136161693);

		assertThat(HashUtils.crc32AsLong("hahhha1")).isEqualTo(3669041703L);
		assertThat(HashUtils.crc32AsLong("hahhha1".getBytes())).isEqualTo(3669041703L);
		assertThat(HashUtils.crc32AsLong("hahhha2")).isEqualTo(1136161693L);
	}

	@Test
	public void murmurhash() {
		assertThat(HashUtils.murmur32AsInt("hahhha1")).isEqualTo(-1920794701);
		assertThat(HashUtils.murmur32AsInt("hahhha1".getBytes())).isEqualTo(-1920794701);
		assertThat(HashUtils.murmur32AsInt("hahhha2")).isEqualTo(2065789419);
		assertThat(HashUtils.murmur32AsInt("hahhha3")).isEqualTo(-293065542);
		assertThat(HashUtils.murmur32AsInt("hahhha4")).isEqualTo(-2003559207);
		assertThat(HashUtils.murmur32AsInt("hahhha5")).isEqualTo(-3887993);
		assertThat(HashUtils.murmur32AsInt("hahhha6")).isEqualTo(-446760132);

		assertThat(HashUtils.murmur128AsLong("hahhha6")).isEqualTo(-5203515929515563680L);
		assertThat(HashUtils.murmur128AsLong("hahhha6".getBytes(StandardCharsets.UTF_8)))
			.isEqualTo(-5203515929515563680L);
	}

}
