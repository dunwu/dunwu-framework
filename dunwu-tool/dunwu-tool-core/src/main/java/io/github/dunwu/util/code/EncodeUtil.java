package io.github.dunwu.util.code;

import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * string/url -> hex/base64 编解码工具集(via guava BaseEncoding)
 */
public class EncodeUtil {

    /**
     * 字符串编码转换的实现方法
     *
     * @param text       待转换编码的字符串
     * @param newCharset 目标编码
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String text, String newCharset)
        throws UnsupportedEncodingException {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        // 用默认字符编码解码字符串。
        byte[] bytes = text.getBytes();
        // 用新的字符编码生成字符串
        return new String(bytes, newCharset);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param text       待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String text, String oldCharset, String newCharset)
        throws UnsupportedEncodingException {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        // 用旧的字符编码解码字符串。解码可能会出现异常。
        byte[] bytes = text.getBytes(oldCharset);
        // 用新的字符编码生成字符串
        return new String(bytes, newCharset);
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
     * Base64 URL 安全的解码。将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548。
     * <p>
     * 如果字符不合法，抛出 IllegalArgumentException
     */
    public static byte[] decodeBase64UrlSafe(String input) {
        return Base64.getUrlDecoder().decode(input);
    }

    /**
     * Hex解码, 将String解码为byte[]. 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] decodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    /**
     * 产生 Base64 编码。
     *
     * @param input 输入
     * @return String
     */
    public static String encodeBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * Base64 URL 安全的编码。将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548。
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return Base64.getUrlEncoder().encodeToString(input);
    }

    /**
     * Hex编码, 将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

}
