package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumExtUtilTest {

    @Test
    void getEnum() {
        assertThat(EnumExtUtil.getEnum(Options.class, "B")).isEqualTo(Options.B);
    }

    @Test
    void getEnumSet() {
        EnumSet<Options> set = EnumExtUtil.getEnumSet(Options.class);
        assertThat(set).hasSize(4);
    }

    @Test
    void testBits() {
        assertThat(EnumExtUtil.generateBitVector(Options.class, Options.A)).isEqualTo(1);
        assertThat(EnumExtUtil.generateBitVector(Options.class, Options.A, Options.B))
            .isEqualTo(3);

        assertThat(EnumExtUtil.generateBitVector(Options.class,
            ListUtil.newArrayList(Options.A))).isEqualTo(1);
        assertThat(EnumExtUtil.generateBitVector(Options.class,
            ListUtil.newArrayList(Options.A, Options.B))).isEqualTo(3);

        assertThat(EnumExtUtil.processBitVector(Options.class, 3)).hasSize(2)
            .containsExactly(Options.A, Options.B);

        long value = EnumExtUtil.generateBitVector(Options.class, Options.A, Options.C,
            Options.D);
        assertThat(EnumExtUtil.processBitVector(Options.class, value)).hasSize(3)
            .containsExactly(Options.A, Options.C, Options.D);
    }

    @Test
    void testGetMap() {
        Map<String, Options> enumMap = EnumExtUtil.getEnumMap(Options.class);
        assertThat(enumMap).hasSize(4);
    }

    public enum Options {

        A,
        B,
        C,
        D;
    }

}
