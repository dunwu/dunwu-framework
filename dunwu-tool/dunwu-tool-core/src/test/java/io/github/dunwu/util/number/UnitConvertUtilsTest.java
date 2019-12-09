package io.github.dunwu.util.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitConvertUtilsTest {

	@Test
	void convertDurationMillis() {
		assertThat(UnitConvertUtils.getDurationMillis("12345")).isEqualTo(12345);
		assertThat(UnitConvertUtils.getDurationMillis("12S")).isEqualTo(12000);
		assertThat(UnitConvertUtils.getDurationMillis("12s")).isEqualTo(12000);
		assertThat(UnitConvertUtils.getDurationMillis("12ms")).isEqualTo(12);
		assertThat(UnitConvertUtils.getDurationMillis("12m")).isEqualTo(12 * 60 * 1000);
		assertThat(UnitConvertUtils.getDurationMillis("12h")).isEqualTo(12L * 60 * 60 * 1000);
		assertThat(UnitConvertUtils.getDurationMillis("12d")).isEqualTo(12L * 24 * 60 * 60 * 1000);

		try {
			assertThat(UnitConvertUtils.getDurationMillis("12a")).isEqualTo(12 * 60 * 1000);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}

		try {
			assertThat(UnitConvertUtils.getDurationMillis("a12")).isEqualTo(12 * 60 * 1000);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	void getSizeBytes() {
		assertThat(UnitConvertUtils.getSizeBytes("12")).isEqualTo(12);
		assertThat(UnitConvertUtils.getSizeBytes("12b")).isEqualTo(12);
		assertThat(UnitConvertUtils.getSizeBytes("12k")).isEqualTo(12 * 1024);
		assertThat(UnitConvertUtils.getSizeBytes("12M")).isEqualTo(12 * 1024 * 1024);
		assertThat(UnitConvertUtils.getSizeBytes("12G")).isEqualTo(12L * 1024 * 1024 * 1024);
		assertThat(UnitConvertUtils.getSizeBytes("12T")).isEqualTo(12L * 1024 * 1024 * 1024 * 1024);

		try {
			UnitConvertUtils.getSizeBytes("12x");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}

		try {
			UnitConvertUtils.getSizeBytes("a12");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	void getSizeUnit() {
		assertThat(UnitConvertUtils.getSizeUnit(966L, 0)).isEqualTo("966");
		assertThat(UnitConvertUtils.getSizeUnit(1522L, 0)).isEqualTo("1k");
		assertThat(UnitConvertUtils.getSizeUnit(1522L, 1)).isEqualTo("1.5k");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 2 + 1024 * 200, 0)).isEqualTo("2m");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 2 + 1024 * 600, 0)).isEqualTo("3m");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 2 + 1024 * 140, 1)).isEqualTo("2.1m");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 2 + 1024 * 160, 1)).isEqualTo("2.2m");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 0)).isEqualTo("2g");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 1)).isEqualTo("2.2g");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 0))
			.isEqualTo("2t");
		assertThat(UnitConvertUtils.getSizeUnit(1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 1))
			.isEqualTo("2.2t");
	}

	@Test
	void getTimeWithUnit() {
		assertThat(UnitConvertUtils.getTimeWithUnit(1322L, 0)).isEqualTo("1s");
		assertThat(UnitConvertUtils.getTimeWithUnit(1322L, 1)).isEqualTo("1.3s");
		assertThat(UnitConvertUtils.getTimeWithUnit(1000L * 62, 0)).isEqualTo("1m");
		assertThat(UnitConvertUtils.getTimeWithUnit(1000L * 90, 0)).isEqualTo("2m");
		assertThat(UnitConvertUtils.getTimeWithUnit(1000L * 90, 1)).isEqualTo("1.5m");
		assertThat(UnitConvertUtils.getTimeWithUnit(1000L * 60 * 70, 1)).isEqualTo("1.2h");
		assertThat(UnitConvertUtils.getTimeWithUnit(1000L * 60 * 60 * 28, 1)).isEqualTo("1.2d");
	}

}
