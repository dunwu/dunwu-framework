package io.github.dunwu.util.number;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilTest {

    @Test
    void ceilingPowerOfTwo() {
        assertThat(MathUtil.ceilingPowerOfTwo(5)).isEqualTo(8);
        assertThat(MathUtil.ceilingPowerOfTwo(99)).isEqualTo(128);
        assertThat(MathUtil.ceilingPowerOfTwo(5L)).isEqualTo(8L);
        assertThat(MathUtil.ceilingPowerOfTwo(99L)).isEqualTo(128L);

        try {
            assertThat(MathUtil.ceilingPowerOfTwo(-5)).isEqualTo(8);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void divide() {
        assertThat(11 / 4).isEqualTo(2);
        assertThat(10L / 4).isEqualTo(2);
        assertThat(MathUtil.divide(10, 4, RoundingMode.HALF_UP)).isEqualTo(3);
        assertThat(MathUtil.divide(10L, 4L, RoundingMode.HALF_DOWN)).isEqualTo(2);
    }

    @Test
    void floorPowerOfTwo() {
        assertThat(MathUtil.floorPowerOfTwo(5)).isEqualTo(4);
        assertThat(MathUtil.floorPowerOfTwo(99)).isEqualTo(64);
        assertThat(MathUtil.floorPowerOfTwo(5L)).isEqualTo(4L);
        assertThat(MathUtil.floorPowerOfTwo(99L)).isEqualTo(64L);
    }

    @Test
    void isPowerOfTwo() {
        assertThat(MathUtil.isPowerOfTwo(32)).isTrue();
        assertThat(MathUtil.isPowerOfTwo(31)).isFalse();
        assertThat(MathUtil.isPowerOfTwo(32L)).isTrue();
        assertThat(MathUtil.isPowerOfTwo(31L)).isFalse();
        assertThat(MathUtil.isPowerOfTwo(-2)).isFalse();
    }

    @Test
    void mod() {
        assertThat(MathUtil.mod(15, 10)).isEqualTo(5);
        assertThat(MathUtil.mod(-15, 10)).isEqualTo(5);
        assertThat(MathUtil.mod(-5, 3)).isEqualTo(1);

        assertThat(MathUtil.mod(15L, 10L)).isEqualTo(5);
        assertThat(MathUtil.mod(-15L, 10L)).isEqualTo(5);
        assertThat(MathUtil.mod(-5L, 3L)).isEqualTo(1);

        assertThat(MathUtil.mod(15L, 10)).isEqualTo(5);
        assertThat(MathUtil.mod(-15L, 10)).isEqualTo(5);
        assertThat(MathUtil.mod(-5L, 3)).isEqualTo(1);
    }

    @Test
    void modByPowerOfTwo() {
        assertThat(MathUtil.modByPowerOfTwo(0, 16)).isEqualTo(0);
        assertThat(MathUtil.modByPowerOfTwo(1, 16)).isEqualTo(1);
        assertThat(MathUtil.modByPowerOfTwo(31, 16)).isEqualTo(15);
        assertThat(MathUtil.modByPowerOfTwo(32, 16)).isEqualTo(0);
        assertThat(MathUtil.modByPowerOfTwo(65, 16)).isEqualTo(1);
        assertThat(MathUtil.modByPowerOfTwo(-1, 16)).isEqualTo(15);
    }

    @Test
    void pow() {
        assertThat(MathUtil.pow(2, 3)).isEqualTo(8);
        assertThat(MathUtil.pow(2, 0)).isEqualTo(1);
        assertThat(MathUtil.pow(2L, 3)).isEqualTo(8);
        assertThat(MathUtil.pow(2L, 0)).isEqualTo(1);
    }

    @Test
    void sqrt() {
        assertThat(MathUtil.sqrt(15, RoundingMode.HALF_UP)).isEqualTo(4);
        assertThat(MathUtil.sqrt(16, RoundingMode.HALF_UP)).isEqualTo(4);
        assertThat(MathUtil.sqrt(10L, RoundingMode.HALF_UP)).isEqualTo(3);
    }

}
