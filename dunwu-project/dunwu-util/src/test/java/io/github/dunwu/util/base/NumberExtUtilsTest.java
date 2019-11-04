package io.github.dunwu.util.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class NumberExtUtilsTest {

	@Test
	void toBytes() {
		byte[] bytes = NumberExtUtils.toBytes(1);
		assertThat(bytes).hasSize(4).containsSequence((byte) 0, (byte) 0, (byte) 0, (byte) 1);
		assertThat(NumberExtUtils.parseInt(bytes)).isEqualTo(1);

		byte[] bytes2 = NumberExtUtils.toBytes(1024L);
		assertThat(bytes2).hasSize(8)
			.containsSequence((byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 0);
		assertThat(NumberExtUtils.parseLong(bytes2)).isEqualTo(1024L);

		byte[] bytes3 = NumberExtUtils.toBytes(1.123d);
		assertThat(NumberExtUtils.parseDouble(bytes3)).isEqualTo(1.123d);

		byte[] bytes4 = NumberExtUtils.toBytes(0.05f);
		assertThat(NumberExtUtils.parseFloat(bytes4)).isEqualTo(0.05f);

		// toInt32
		assertThat(NumberExtUtils.toInt32(123L)).isEqualTo(123);

		try {
			NumberExtUtils.toInt32(Integer.MAX_VALUE + 1L);
			fail("should fail here");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	void isNumber() {
		assertThat(NumberExtUtils.isCreatable("123")).isTrue();
		assertThat(NumberExtUtils.isCreatable("-123.1")).isTrue();
		assertThat(NumberExtUtils.isCreatable("-1a3.1")).isFalse();

		assertThat(NumberExtUtils.isHexNumber("0x12F")).isTrue();
		assertThat(NumberExtUtils.isHexNumber("-0x12A3")).isTrue();
		assertThat(NumberExtUtils.isHexNumber("12A3")).isFalse();
	}

	@Test
	void toNumber() {
		assertThat(NumberExtUtils.parseInt("122")).isEqualTo(122);
		try {
			NumberExtUtils.parseInt("12A");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		try {
			NumberExtUtils.parseInt((String) null);
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.toInt("12A", 123)).isEqualTo(123);

		assertThat(NumberExtUtils.parseLong("122")).isEqualTo(122L);
		try {
			NumberExtUtils.parseLong("12A");
			fail("shoud fail here");
		} catch (

			NumberFormatException e) {

		}
		try {
			NumberExtUtils.parseLong((String) null);
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.toLong("12A", 123)).isEqualTo(123L);

		assertThat(NumberExtUtils.parseDouble("122.1")).isEqualTo(122.1);

		try {
			NumberExtUtils.parseDouble("12A");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}

		try {
			NumberExtUtils.parseDouble((String) null);
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.toDouble("12A", 123.1)).isEqualTo(123.1);

		assertThat(NumberExtUtils.toIntObject("122")).isEqualTo(122);
		try {
			NumberExtUtils.toIntObject("12A");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}

		assertThat(NumberExtUtils.toIntObject("12A", 123)).isEqualTo(123);
		assertThat(NumberExtUtils.toIntObject(null, 123)).isEqualTo(123);
		assertThat(NumberExtUtils.toIntObject("", 123)).isEqualTo(123);

		assertThat(NumberExtUtils.toLongObject("122")).isEqualTo(122L);
		try {
			NumberExtUtils.toLongObject("12A");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.toLongObject("12A", 123L)).isEqualTo(123L);
		assertThat(NumberExtUtils.toLongObject(null, 123L)).isEqualTo(123L);

		assertThat(NumberExtUtils.toDoubleObject("122.1")).isEqualTo(122.1);
		try {
			NumberExtUtils.toDoubleObject("12A");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.toDoubleObject("12A", 123.1)).isEqualTo(123.1);

		assertThat(NumberExtUtils.hexToIntObject("0x10")).isEqualTo(16);
		assertThat(NumberExtUtils.hexToIntObject("0X100")).isEqualTo(256);
		assertThat(NumberExtUtils.hexToIntObject("-0x100")).isEqualTo(-256);
		try {
			NumberExtUtils.hexToIntObject("0xHI");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}

		try {
			NumberExtUtils.hexToIntObject(null);
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.hexToIntObject("0xHI", 123)).isEqualTo(123);

		assertThat(NumberExtUtils.hexToLongObject("0x10")).isEqualTo(16L);
		assertThat(NumberExtUtils.hexToLongObject("0X100")).isEqualTo(256L);
		assertThat(NumberExtUtils.hexToLongObject("-0x100")).isEqualTo(-256L);
		try {
			NumberExtUtils.hexToLongObject("0xHI");
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		try {
			NumberExtUtils.hexToLongObject(null);
			fail("shoud fail here");
		} catch (NumberFormatException e) {

		}
		assertThat(NumberExtUtils.hexToLongObject("0xHI", 123L)).isEqualTo(123L);
	}

	@Test
	void toStringTest() {
		assertThat(NumberExtUtils.toString(23)).isEqualTo("23");
		assertThat(NumberExtUtils.toString(new Integer(23))).isEqualTo("23");
		assertThat(NumberExtUtils.toString(23l)).isEqualTo("23");
		assertThat(NumberExtUtils.toString(new Long(23))).isEqualTo("23");
		assertThat(NumberExtUtils.toString(23l)).isEqualTo("23");

		assertThat(NumberExtUtils.toString(new Double(23.112d))).isEqualTo("23.112");
		assertThat(NumberExtUtils.toString(23.112d)).isEqualTo("23.112");
		assertThat(NumberExtUtils.to2DigitString(23.112d)).isEqualTo("23.11");
		assertThat(NumberExtUtils.to2DigitString(23.116d)).isEqualTo("23.12");
	}

	@Test
	void numberToEnWords() {
		Assertions.assertEquals("One Hundred Twenty Three", NumberExtUtils.intToEnWords(123));
		Assertions.assertEquals("Twelve Thousand Three Hundred Forty Five",
			NumberExtUtils.intToEnWords(12345));
		Assertions.assertEquals(
			"One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven",
			NumberExtUtils.intToEnWords(1234567));
		Assertions.assertEquals(
			"One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One",
			NumberExtUtils.intToEnWords(1234567891));
	}

}
