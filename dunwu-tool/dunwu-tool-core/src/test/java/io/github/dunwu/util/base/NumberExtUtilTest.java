package io.github.dunwu.util.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class NumberExtUtilTest {

    @Test
    void isNumber() {
        assertThat(NumberExtUtil.isCreatable("123")).isTrue();
        assertThat(NumberExtUtil.isCreatable("-123.1")).isTrue();
        assertThat(NumberExtUtil.isCreatable("-1a3.1")).isFalse();

        assertThat(NumberExtUtil.isHexNumber("0x12F")).isTrue();
        assertThat(NumberExtUtil.isHexNumber("-0x12A3")).isTrue();
        assertThat(NumberExtUtil.isHexNumber("12A3")).isFalse();
    }

    @Test
    void numberToEnWords() {
        Assertions.assertEquals("One Hundred Twenty Three",
            NumberExtUtil.intToEnWords(123));
        Assertions.assertEquals("Twelve Thousand Three Hundred Forty Five",
            NumberExtUtil.intToEnWords(12345));
        Assertions.assertEquals(
            "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven",
            NumberExtUtil.intToEnWords(1234567));
        Assertions.assertEquals(
            "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One",
            NumberExtUtil.intToEnWords(1234567891));
    }

    @Test
    void toBytes() {
        byte[] bytes = NumberExtUtil.toBytes(1);
        assertThat(bytes).hasSize(4).containsSequence((byte) 0, (byte) 0, (byte) 0,
            (byte) 1);
        assertThat(NumberExtUtil.parseInt(bytes)).isEqualTo(1);

        byte[] bytes2 = NumberExtUtil.toBytes(1024L);
        assertThat(bytes2).hasSize(8).containsSequence((byte) 0, (byte) 0, (byte) 0,
            (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 0);
        assertThat(NumberExtUtil.parseLong(bytes2)).isEqualTo(1024L);

        byte[] bytes3 = NumberExtUtil.toBytes(1.123d);
        assertThat(NumberExtUtil.parseDouble(bytes3)).isEqualTo(1.123d);

        byte[] bytes4 = NumberExtUtil.toBytes(0.05f);
        assertThat(NumberExtUtil.parseFloat(bytes4)).isEqualTo(0.05f);

        // toInt32
        assertThat(NumberExtUtil.toInt32(123L)).isEqualTo(123);

        try {
            NumberExtUtil.toInt32(Integer.MAX_VALUE + 1L);
            fail("should fail here");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void toNumber() {
        assertThat(NumberExtUtil.parseInt("122")).isEqualTo(122);
        try {
            NumberExtUtil.parseInt("12A");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        try {
            NumberExtUtil.parseInt((String) null);
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.toInt("12A", 123)).isEqualTo(123);

        assertThat(NumberExtUtil.parseLong("122")).isEqualTo(122L);
        try {
            NumberExtUtil.parseLong("12A");
            fail("shoud fail here");
        } catch (

            NumberFormatException e) {

        }
        try {
            NumberExtUtil.parseLong((String) null);
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.toLong("12A", 123)).isEqualTo(123L);

        assertThat(NumberExtUtil.parseDouble("122.1")).isEqualTo(122.1);

        try {
            NumberExtUtil.parseDouble("12A");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }

        try {
            NumberExtUtil.parseDouble((String) null);
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.toDouble("12A", 123.1)).isEqualTo(123.1);

        assertThat(NumberExtUtil.toIntObject("122")).isEqualTo(122);
        try {
            NumberExtUtil.toIntObject("12A");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }

        assertThat(NumberExtUtil.toIntObject("12A", 123)).isEqualTo(123);
        assertThat(NumberExtUtil.toIntObject(null, 123)).isEqualTo(123);
        assertThat(NumberExtUtil.toIntObject("", 123)).isEqualTo(123);

        assertThat(NumberExtUtil.toLongObject("122")).isEqualTo(122L);
        try {
            NumberExtUtil.toLongObject("12A");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.toLongObject("12A", 123L)).isEqualTo(123L);
        assertThat(NumberExtUtil.toLongObject(null, 123L)).isEqualTo(123L);

        assertThat(NumberExtUtil.toDoubleObject("122.1")).isEqualTo(122.1);
        try {
            NumberExtUtil.toDoubleObject("12A");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.toDoubleObject("12A", 123.1)).isEqualTo(123.1);

        assertThat(NumberExtUtil.hexToIntObject("0x10")).isEqualTo(16);
        assertThat(NumberExtUtil.hexToIntObject("0X100")).isEqualTo(256);
        assertThat(NumberExtUtil.hexToIntObject("-0x100")).isEqualTo(-256);
        try {
            NumberExtUtil.hexToIntObject("0xHI");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }

        try {
            NumberExtUtil.hexToIntObject(null);
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.hexToIntObject("0xHI", 123)).isEqualTo(123);

        assertThat(NumberExtUtil.hexToLongObject("0x10")).isEqualTo(16L);
        assertThat(NumberExtUtil.hexToLongObject("0X100")).isEqualTo(256L);
        assertThat(NumberExtUtil.hexToLongObject("-0x100")).isEqualTo(-256L);
        try {
            NumberExtUtil.hexToLongObject("0xHI");
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        try {
            NumberExtUtil.hexToLongObject(null);
            fail("shoud fail here");
        } catch (NumberFormatException e) {

        }
        assertThat(NumberExtUtil.hexToLongObject("0xHI", 123L)).isEqualTo(123L);
    }

    @Test
    void toStringTest() {
        assertThat(NumberExtUtil.toString(23)).isEqualTo("23");
        assertThat(NumberExtUtil.toString(new Integer(23))).isEqualTo("23");
        assertThat(NumberExtUtil.toString(23l)).isEqualTo("23");
        assertThat(NumberExtUtil.toString(new Long(23))).isEqualTo("23");
        assertThat(NumberExtUtil.toString(23l)).isEqualTo("23");

        assertThat(NumberExtUtil.toString(new Double(23.112d))).isEqualTo("23.112");
        assertThat(NumberExtUtil.toString(23.112d)).isEqualTo("23.112");
        assertThat(NumberExtUtil.to2DigitString(23.112d)).isEqualTo("23.11");
        assertThat(NumberExtUtil.to2DigitString(23.116d)).isEqualTo("23.12");
    }

}
