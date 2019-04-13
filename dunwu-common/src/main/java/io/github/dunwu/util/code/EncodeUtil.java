package io.github.dunwu.util.code;

import com.google.common.io.BaseEncoding;

import java.util.Base64;

/**
 * string/url -> hex/base64 编解码工具集(via guava BaseEncoding)
 */
public class EncodeUtil {

    /**
     * Hex编码, 将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

    /**
     * Hex解码, 将String解码为byte[]. 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] decodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    /**
     * 产生 Base64 编码。
     * @param input 输入
     * @return String
     */
    public static String encodeBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * Base64 解码。
     * <p>
     * 如果字符不合法，抛出 IllegalArgumentException
     */
    public static byte[] decodeBase64(String input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * Base64 URL 安全的编码。将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548。
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return Base64.getUrlEncoder().encodeToString(input);
    }

    /**
     * Base64 URL 安全的解码。将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548。
     * <p>
     * 如果字符不合法，抛出 IllegalArgumentException
     */
    public static byte[] decodeBase64UrlSafe(String input) {
        return Base64.getUrlDecoder().decode(input);
    }
}
