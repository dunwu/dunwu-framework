package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumExtUtilsTest {

	@Test
	void testBits() {
		assertThat(EnumExtUtils.generateBitVector(Options.class, Options.A)).isEqualTo(1);
		assertThat(EnumExtUtils.generateBitVector(Options.class, Options.A, Options.B))
			.isEqualTo(3);

		assertThat(EnumExtUtils.generateBitVector(Options.class,
			ListUtil.newArrayList(Options.A))).isEqualTo(1);
		assertThat(EnumExtUtils.generateBitVector(Options.class,
			ListUtil.newArrayList(Options.A, Options.B))).isEqualTo(3);

		assertThat(EnumExtUtils.processBitVector(Options.class, 3)).hasSize(2)
			.containsExactly(Options.A, Options.B);

		long value = EnumExtUtils.generateBitVector(Options.class, Options.A, Options.C,
			Options.D);
		assertThat(EnumExtUtils.processBitVector(Options.class, value)).hasSize(3)
			.containsExactly(Options.A, Options.C, Options.D);
	}

	@Test
	void getEnum() {
		assertThat(EnumExtUtils.getEnum(Options.class, "B")).isEqualTo(Options.B);
	}

	@Test
	void testGetMap() {
		Map<String, Options> enumMap = EnumExtUtils.getEnumMap(Options.class);
		assertThat(enumMap).hasSize(4);
	}

	@Test
	void getEnumSet() {
		EnumSet<Options> set = EnumExtUtils.getEnumSet(Options.class);
		assertThat(set).hasSize(4);
	}

	public enum Options {

		A,
		B,
		C,
		D;
	}

}
