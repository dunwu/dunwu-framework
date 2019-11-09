package io.github.dunwu.util.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringExtUtilsTest {

	@Test
	void replace() {
		Assertions.assertThat(StringExtUtils.replaceFirst("abbc", 'b', 'c'))
			.isEqualTo("acbc");
		assertThat(StringExtUtils.replaceFirst("abcc", 'c', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtils.replaceFirst("", 'c', 'c')).isEqualTo("");
		assertThat(StringExtUtils.replaceFirst(null, 'c', 'c')).isNull();

		assertThat(StringExtUtils.replaceLast("abbc", 'b', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtils.replaceLast("abcc", 'c', 'c')).isEqualTo("abcc");
		assertThat(StringExtUtils.replaceLast("", 'c', 'c')).isEqualTo("");
		assertThat(StringExtUtils.replaceLast(null, 'c', 'c')).isNull();
	}

}
