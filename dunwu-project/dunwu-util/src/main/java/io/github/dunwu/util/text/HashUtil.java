package io.github.dunwu.util.text;

import com.google.common.hash.Hashing;
import io.github.dunwu.util.base.annotation.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/**
 * 封装各种Hash算法的工具类 1.SHA-1, 安全性较高, 返回byte[](可用Encodes进一步被编码为Hex, Base64)
 * 性能优化，使用ThreadLocal的MessageDigest(from ElasticSearch) 支持带salt并且进行迭代达到更高的安全性. MD5的安全性较低,
 * 只在文件Checksum时支持. 2.crc32, murmur32这些不追求安全性, 性能较高, 返回int. 其中crc32基于JDK,
 * murmurhash基于guava
 */
public class HashUtil {

	public static final int MURMUR_SEED = 1_318_007_700;

	////////////////// 基于JDK的CRC32 ///////////////////

	/**
	 * 对输入字符串进行crc32散列返回int, 返回值有可能是负数. Guava也有crc32实现, 但返回值无法返回long，所以统一使用JDK默认实现
	 */
	public static int crc32AsInt(@NotNull String input) {
		return crc32AsInt(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 对输入字符串进行crc32散列返回int, 返回值有可能是负数. Guava也有crc32实现, 但返回值无法返回long，所以统一使用JDK默认实现
	 */
	public static int crc32AsInt(@NotNull byte[] input) {
		CRC32 crc32 = new CRC32();
		crc32.update(input);
		// CRC32 只是 32bit int，为了CheckSum接口强转成long，此处再次转回来
		return (int) crc32.getValue();
	}

	/**
	 * 对输入字符串进行crc32散列，与php兼容，在64bit系统下返回永远是正数的long Guava也有crc32实现,
	 * 但返回值无法返回long，所以统一使用JDK默认实现
	 */
	public static long crc32AsLong(@NotNull String input) {
		return crc32AsLong(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 对输入字符串进行crc32散列，与php兼容，在64bit系统下返回永远是正数的long Guava也有crc32实现,
	 * 但返回值无法返回long，所以统一使用JDK默认实现
	 */
	public static long crc32AsLong(@NotNull byte[] input) {
		CRC32 crc32 = new CRC32();
		crc32.update(input);
		return crc32.getValue();
	}

	////////////////// 基于Guava的MurMurHash ///////////////////

	/**
	 * 对输入字符串进行murmur32散列, 返回值可能是负数
	 */
	public static int murmur32AsInt(@NotNull byte[] input) {
		return Hashing.murmur3_32(MURMUR_SEED).hashBytes(input).asInt();
	}

	/**
	 * 对输入字符串进行murmur32散列, 返回值可能是负数
	 */
	public static int murmur32AsInt(@NotNull String input) {
		return Hashing.murmur3_32(MURMUR_SEED).hashString(input, StandardCharsets.UTF_8)
				.asInt();
	}

	/**
	 * 对输入字符串进行murmur128散列, 返回值可能是负数
	 */
	public static long murmur128AsLong(@NotNull byte[] input) {
		return Hashing.murmur3_128(MURMUR_SEED).hashBytes(input).asLong();
	}

	/**
	 * 对输入字符串进行murmur128散列, 返回值可能是负数
	 */
	public static long murmur128AsLong(@NotNull String input) {
		return Hashing.murmur3_128(MURMUR_SEED).hashString(input, StandardCharsets.UTF_8)
				.asLong();
	}

}
