package io.github.dunwu.util.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitConvertUtilTest {

    @Test
    void convertDurationMillis() {
        assertThat(UnitConvertUtil.getDurationMillis("12345")).isEqualTo(12345);
        assertThat(UnitConvertUtil.getDurationMillis("12S")).isEqualTo(12000);
        assertThat(UnitConvertUtil.getDurationMillis("12s")).isEqualTo(12000);
        assertThat(UnitConvertUtil.getDurationMillis("12ms")).isEqualTo(12);
        assertThat(UnitConvertUtil.getDurationMillis("12m")).isEqualTo(12 * 60 * 1000);
        assertThat(UnitConvertUtil.getDurationMillis("12h")).isEqualTo(12L * 60 * 60 * 1000);
        assertThat(UnitConvertUtil.getDurationMillis("12d")).isEqualTo(12L * 24 * 60 * 60 * 1000);

        try {
            assertThat(UnitConvertUtil.getDurationMillis("12a")).isEqualTo(12 * 60 * 1000);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }

        try {
            assertThat(UnitConvertUtil.getDurationMillis("a12")).isEqualTo(12 * 60 * 1000);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void getSizeBytes() {
        assertThat(UnitConvertUtil.getSizeBytes("12")).isEqualTo(12);
        assertThat(UnitConvertUtil.getSizeBytes("12b")).isEqualTo(12);
        assertThat(UnitConvertUtil.getSizeBytes("12k")).isEqualTo(12 * 1024);
        assertThat(UnitConvertUtil.getSizeBytes("12M")).isEqualTo(12 * 1024 * 1024);
        assertThat(UnitConvertUtil.getSizeBytes("12G")).isEqualTo(12L * 1024 * 1024 * 1024);
        assertThat(UnitConvertUtil.getSizeBytes("12T")).isEqualTo(12L * 1024 * 1024 * 1024 * 1024);

        try {
            UnitConvertUtil.getSizeBytes("12x");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }

        try {
            UnitConvertUtil.getSizeBytes("a12");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void getSizeUnit() {
        assertThat(UnitConvertUtil.getSizeUnit(966L, 0)).isEqualTo("966");
        assertThat(UnitConvertUtil.getSizeUnit(1522L, 0)).isEqualTo("1k");
        assertThat(UnitConvertUtil.getSizeUnit(1522L, 1)).isEqualTo("1.5k");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 2 + 1024 * 200, 0)).isEqualTo("2m");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 2 + 1024 * 600, 0)).isEqualTo("3m");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 2 + 1024 * 140, 1)).isEqualTo("2.1m");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 2 + 1024 * 160, 1)).isEqualTo("2.2m");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 0)).isEqualTo("2g");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 1024 * 2 + 1024 * 1024 * 200, 1)).isEqualTo("2.2g");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 0))
            .isEqualTo("2t");
        assertThat(UnitConvertUtil.getSizeUnit(1024L * 1024 * 1024 * 1024 * 2 + 1024L * 1024 * 1024 * 200, 1))
            .isEqualTo("2.2t");
    }

    @Test
    void getTimeWithUnit() {
        assertThat(UnitConvertUtil.getTimeWithUnit(1322L, 0)).isEqualTo("1s");
        assertThat(UnitConvertUtil.getTimeWithUnit(1322L, 1)).isEqualTo("1.3s");
        assertThat(UnitConvertUtil.getTimeWithUnit(1000L * 62, 0)).isEqualTo("1m");
        assertThat(UnitConvertUtil.getTimeWithUnit(1000L * 90, 0)).isEqualTo("2m");
        assertThat(UnitConvertUtil.getTimeWithUnit(1000L * 90, 1)).isEqualTo("1.5m");
        assertThat(UnitConvertUtil.getTimeWithUnit(1000L * 60 * 70, 1)).isEqualTo("1.2h");
        assertThat(UnitConvertUtil.getTimeWithUnit(1000L * 60 * 60 * 28, 1)).isEqualTo("1.2d");
    }

}
