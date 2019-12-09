package io.github.dunwu.util.number;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

	@Test
	void ceilingPowerOfTwo() {
		assertThat(MathUtils.ceilingPowerOfTwo(5)).isEqualTo(8);
		assertThat(MathUtils.ceilingPowerOfTwo(99)).isEqualTo(128);
		assertThat(MathUtils.ceilingPowerOfTwo(5L)).isEqualTo(8L);
		assertThat(MathUtils.ceilingPowerOfTwo(99L)).isEqualTo(128L);

		try {
			assertThat(MathUtils.ceilingPowerOfTwo(-5)).isEqualTo(8);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	void divide() {
		assertThat(11 / 4).isEqualTo(2);
		assertThat(10L / 4).isEqualTo(2);
		assertThat(MathUtils.divide(10, 4, RoundingMode.HALF_UP)).isEqualTo(3);
		assertThat(MathUtils.divide(10L, 4L, RoundingMode.HALF_DOWN)).isEqualTo(2);
	}

	@Test
	void floorPowerOfTwo() {
		assertThat(MathUtils.floorPowerOfTwo(5)).isEqualTo(4);
		assertThat(MathUtils.floorPowerOfTwo(99)).isEqualTo(64);
		assertThat(MathUtils.floorPowerOfTwo(5L)).isEqualTo(4L);
		assertThat(MathUtils.floorPowerOfTwo(99L)).isEqualTo(64L);
	}

	@Test
	void isPowerOfTwo() {
		assertThat(MathUtils.isPowerOfTwo(32)).isTrue();
		assertThat(MathUtils.isPowerOfTwo(31)).isFalse();
		assertThat(MathUtils.isPowerOfTwo(32L)).isTrue();
		assertThat(MathUtils.isPowerOfTwo(31L)).isFalse();
		assertThat(MathUtils.isPowerOfTwo(-2)).isFalse();
	}

	@Test
	void mod() {
		assertThat(MathUtils.mod(15, 10)).isEqualTo(5);
		assertThat(MathUtils.mod(-15, 10)).isEqualTo(5);
		assertThat(MathUtils.mod(-5, 3)).isEqualTo(1);

		assertThat(MathUtils.mod(15L, 10L)).isEqualTo(5);
		assertThat(MathUtils.mod(-15L, 10L)).isEqualTo(5);
		assertThat(MathUtils.mod(-5L, 3L)).isEqualTo(1);

		assertThat(MathUtils.mod(15L, 10)).isEqualTo(5);
		assertThat(MathUtils.mod(-15L, 10)).isEqualTo(5);
		assertThat(MathUtils.mod(-5L, 3)).isEqualTo(1);
	}

	@Test
	void modByPowerOfTwo() {
		assertThat(MathUtils.modByPowerOfTwo(0, 16)).isEqualTo(0);
		assertThat(MathUtils.modByPowerOfTwo(1, 16)).isEqualTo(1);
		assertThat(MathUtils.modByPowerOfTwo(31, 16)).isEqualTo(15);
		assertThat(MathUtils.modByPowerOfTwo(32, 16)).isEqualTo(0);
		assertThat(MathUtils.modByPowerOfTwo(65, 16)).isEqualTo(1);
		assertThat(MathUtils.modByPowerOfTwo(-1, 16)).isEqualTo(15);
	}

	@Test
	void pow() {
		assertThat(MathUtils.pow(2, 3)).isEqualTo(8);
		assertThat(MathUtils.pow(2, 0)).isEqualTo(1);
		assertThat(MathUtils.pow(2L, 3)).isEqualTo(8);
		assertThat(MathUtils.pow(2L, 0)).isEqualTo(1);
	}

	@Test
	void sqrt() {
		assertThat(MathUtils.sqrt(15, RoundingMode.HALF_UP)).isEqualTo(4);
		assertThat(MathUtils.sqrt(16, RoundingMode.HALF_UP)).isEqualTo(4);
		assertThat(MathUtils.sqrt(10L, RoundingMode.HALF_UP)).isEqualTo(3);
	}

}
