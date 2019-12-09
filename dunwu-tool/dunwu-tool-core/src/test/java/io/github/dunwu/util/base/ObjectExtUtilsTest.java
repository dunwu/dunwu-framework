package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectExtUtilsTest {

	@Test
	void hashCodeTest() {
		assertThat(ObjectExtUtils.hashCode("a", "b") - ObjectExtUtils.hashCode("a", "a"))
			.isEqualTo(1);
	}

	@Test
	void toPrettyString() {
		assertThat(ObjectExtUtils.toPrettyString(null)).isEqualTo("null");
		assertThat(ObjectExtUtils.toPrettyString(1)).isEqualTo("1");

		assertThat(ObjectExtUtils.toPrettyString(new int[] { 1, 2 })).isEqualTo("[1, 2]");
		assertThat(ObjectExtUtils.toPrettyString(new long[] { 1, 2 }))
			.isEqualTo("[1, 2]");
		assertThat(ObjectExtUtils.toPrettyString(new double[] { 1.1d, 2.2d }))
			.isEqualTo("[1.1, 2.2]");
		assertThat(ObjectExtUtils.toPrettyString(new float[] { 1.1f, 2.2f }))
			.isEqualTo("[1.1, 2.2]");
		assertThat(ObjectExtUtils.toPrettyString(new boolean[] { true, false }))
			.isEqualTo("[true, false]");
		assertThat(ObjectExtUtils.toPrettyString(new short[] { 1, 2 }))
			.isEqualTo("[1, 2]");
		assertThat(ObjectExtUtils.toPrettyString(new byte[] { 1, 2 }))
			.isEqualTo("[1, 2]");

		assertThat(ObjectExtUtils.toPrettyString(new Integer[] { 1, 2 }))
			.isEqualTo("[1, 2]");
		assertThat(ObjectExtUtils.toPrettyString(ListUtil.newArrayList("1", "2")))
			.isEqualTo("{1,2}");

		System.out.println(Arrays.toString(new Integer[] { 1, 2 }));
	}

}
