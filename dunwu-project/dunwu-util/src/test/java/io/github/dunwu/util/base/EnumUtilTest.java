package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

public class EnumUtilTest {

	@Test
	void testBits() {
		Assertions.assertThat(EnumUtil.generateBitVector(Options.class, Options.A)).isEqualTo(1);
		Assertions.assertThat(EnumUtil.generateBitVector(Options.class, Options.A, Options.B)).isEqualTo(3);

		Assertions.assertThat(EnumUtil.generateBitVector(Options.class, ListUtil.newArrayList(Options.A))).isEqualTo(1);
		Assertions.assertThat(EnumUtil.generateBitVector(Options.class, ListUtil.newArrayList(Options.A, Options.B)))
				.isEqualTo(3);

		Assertions.assertThat(EnumUtil.processBitVector(Options.class, 3)).hasSize(2).containsExactly(Options.A,
				Options.B);

		long value = EnumUtil.generateBitVector(Options.class, Options.A, Options.C, Options.D);
		Assertions.assertThat(EnumUtil.processBitVector(Options.class, value)).hasSize(3).containsExactly(Options.A,
				Options.C, Options.D);
	}

	@Test
	void testString() {
		Assertions.assertThat(EnumUtil.toString(Options.A)).isEqualTo("A");
		Assertions.assertThat(EnumUtil.valueOf(Options.class, "B")).isEqualTo(Options.B);
	}

	@Test
	void testGetMap() {
		Map<String, Options> enumMap = EnumUtil.getEnumMap(Options.class);
		Assertions.assertThat(enumMap).hasSize(4);
	}

	@Test
	void testEnumSet() {
		EnumSet<Options> set = EnumUtil.enumSet(Options.class);
		Assertions.assertThat(set).isNotEmpty();
	}

	public enum Options {

		A, B, C, D;

	}

}
