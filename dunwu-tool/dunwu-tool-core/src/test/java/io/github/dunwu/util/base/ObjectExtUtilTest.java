package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectExtUtilTest {

    @Test
    void hashCodeTest() {
        assertThat(ObjectExtUtil.hashCode("a", "b") - ObjectExtUtil.hashCode("a", "a"))
            .isEqualTo(1);
    }

    @Test
    void toPrettyString() {
        assertThat(ObjectExtUtil.toPrettyString(null)).isEqualTo("null");
        assertThat(ObjectExtUtil.toPrettyString(1)).isEqualTo("1");

        assertThat(ObjectExtUtil.toPrettyString(new int[] { 1, 2 })).isEqualTo("[1, 2]");
        assertThat(ObjectExtUtil.toPrettyString(new long[] { 1, 2 }))
            .isEqualTo("[1, 2]");
        assertThat(ObjectExtUtil.toPrettyString(new double[] { 1.1d, 2.2d }))
            .isEqualTo("[1.1, 2.2]");
        assertThat(ObjectExtUtil.toPrettyString(new float[] { 1.1f, 2.2f }))
            .isEqualTo("[1.1, 2.2]");
        assertThat(ObjectExtUtil.toPrettyString(new boolean[] { true, false }))
            .isEqualTo("[true, false]");
        assertThat(ObjectExtUtil.toPrettyString(new short[] { 1, 2 }))
            .isEqualTo("[1, 2]");
        assertThat(ObjectExtUtil.toPrettyString(new byte[] { 1, 2 }))
            .isEqualTo("[1, 2]");

        assertThat(ObjectExtUtil.toPrettyString(new Integer[] { 1, 2 }))
            .isEqualTo("[1, 2]");
        assertThat(ObjectExtUtil.toPrettyString(ListUtil.newArrayList("1", "2")))
            .isEqualTo("{1,2}");

        System.out.println(Arrays.toString(new Integer[] { 1, 2 }));
    }

}
