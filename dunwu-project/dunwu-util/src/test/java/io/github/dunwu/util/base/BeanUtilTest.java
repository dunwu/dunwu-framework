package io.github.dunwu.util.base;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanUtilTest {

    @Test
    public void test() {
        assertThat(BooleanUtil.toBooleanObject("True")).isTrue();
        assertThat(BooleanUtil.toBooleanObject("tre")).isFalse();
        assertThat(BooleanUtil.toBooleanObject(0)).isTrue();
        assertThat(BooleanUtil.toBooleanObject(1)).isFalse();

        assertThat(BooleanUtil.toBooleanObject("1", false)).isFalse();
        assertThat(BooleanUtil.toBooleanObject("y", false)).isTrue();
        assertThat(BooleanUtil.toBooleanObject("y")).isTrue();
        assertThat(BooleanUtil.toBooleanObject("x")).isNull();

        Optional<String> optional = Optional.of(null);
        optional.isPresent();
    }

    @Test
    public void logic() {
        assertThat(BooleanUtil.negate(Boolean.TRUE)).isFalse();
        assertThat(BooleanUtil.negate(Boolean.FALSE)).isTrue();

        assertThat(BooleanUtil.negate(true)).isFalse();
        assertThat(BooleanUtil.negate(false)).isTrue();
    }
}
