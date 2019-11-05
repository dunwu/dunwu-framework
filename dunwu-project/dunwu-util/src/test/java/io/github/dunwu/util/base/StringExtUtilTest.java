package io.github.dunwu.util.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringExtUtilTest {

	@Test
	void replace() {
		Assertions.assertThat(StringExtUtil.replaceFirst("abbc", 'b', 'c'))
			.isEqualTo("acbc");
		assertThat(StringExtUtil.replaceFirst("abcc", 'c', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtil.replaceFirst("", 'c', 'c')).isEqualTo("");
		assertThat(StringExtUtil.replaceFirst(null, 'c', 'c')).isNull();

		assertThat(StringExtUtil.replaceLast("abbc", 'b', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtil.replaceLast("abcc", 'c', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtil.replaceLast("", 'c', 'c')).isEqualTo("");
		assertThat(StringExtUtil.replaceLast(null, 'c', 'c')).isNull();
	}

}
