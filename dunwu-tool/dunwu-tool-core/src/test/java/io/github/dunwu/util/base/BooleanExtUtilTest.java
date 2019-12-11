package io.github.dunwu.util.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanExtUtilTest {

    @Test
    void negate() {
        assertThat(BooleanExtUtil.negate(Boolean.TRUE)).isFalse();
        assertThat(BooleanExtUtil.negate(Boolean.FALSE)).isTrue();
        assertThat(BooleanExtUtil.negate(true)).isFalse();
        assertThat(BooleanExtUtil.negate(false)).isTrue();
    }

    @Test
    void toBooleanDefaultIfNull() {
        assertThat(BooleanExtUtil.toBooleanDefaultIfNull("1", false)).isFalse();
        assertThat(BooleanExtUtil.toBooleanDefaultIfNull("y", false)).isTrue();
    }

    @Test
    void toBooleanObject() {
        assertThat(BooleanExtUtil.toBooleanObject(0)).isFalse();
        assertThat(BooleanExtUtil.toBooleanObject(1)).isTrue();
        assertThat(BooleanExtUtil.toBooleanObject(2)).isTrue();

        assertThat(BooleanExtUtil.toBooleanObject("True")).isTrue();
        assertThat(BooleanExtUtil.toBooleanObject("False")).isFalse();
        assertThat(BooleanExtUtil.toBooleanObject("tre")).isNull();

        assertThat(BooleanExtUtil.toBooleanObject("y")).isTrue();
        assertThat(BooleanExtUtil.toBooleanObject("x")).isNull();
    }

}
