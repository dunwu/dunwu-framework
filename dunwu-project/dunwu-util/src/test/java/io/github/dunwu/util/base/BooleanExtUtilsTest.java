package io.github.dunwu.util.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanExtUtilsTest {

	@Test
	void toBooleanObject() {
		assertThat(BooleanExtUtils.toBooleanObject(0)).isFalse();
		assertThat(BooleanExtUtils.toBooleanObject(1)).isTrue();
		assertThat(BooleanExtUtils.toBooleanObject(2)).isTrue();

		assertThat(BooleanExtUtils.toBooleanObject("True")).isTrue();
		assertThat(BooleanExtUtils.toBooleanObject("False")).isFalse();
		assertThat(BooleanExtUtils.toBooleanObject("tre")).isNull();

		assertThat(BooleanExtUtils.toBooleanObject("y")).isTrue();
		assertThat(BooleanExtUtils.toBooleanObject("x")).isNull();
	}

	@Test
	void toBooleanDefaultIfNull() {
		assertThat(BooleanExtUtils.toBooleanDefaultIfNull("1", false)).isFalse();
		assertThat(BooleanExtUtils.toBooleanDefaultIfNull("y", false)).isTrue();
	}

	@Test
	void negate() {
		assertThat(BooleanExtUtils.negate(Boolean.TRUE)).isFalse();
		assertThat(BooleanExtUtils.negate(Boolean.FALSE)).isTrue();
		assertThat(BooleanExtUtils.negate(true)).isFalse();
		assertThat(BooleanExtUtils.negate(false)).isTrue();
	}

}
