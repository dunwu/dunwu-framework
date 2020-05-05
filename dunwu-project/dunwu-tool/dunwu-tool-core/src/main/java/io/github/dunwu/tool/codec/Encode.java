package io.github.dunwu.tool.codec;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

/**
 * 编码接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public interface Encode {

    /**
     * 对二进制数据加密
     *
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException
     */
    byte[] encode(byte[] plaintext) throws GeneralSecurityException;

    /**
     * 对字符串数据加密
     *
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException
     */
    default byte[] encode(String plaintext) throws GeneralSecurityException {
        return encode(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对二进制数据加密，并使用 Base64 编码为字符串
     *
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException
     */
    default String encodeWithBase64(byte[] plaintext) throws GeneralSecurityException {
        return Base64.getUrlEncoder().encodeToString(encode(plaintext));
    }

    /**
     * 对字符串数据加密，并使用 Base64 编码为字符串
     *
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException
     */
    default String encodeWithBase64(String plaintext) throws GeneralSecurityException {
        return Base64.getUrlEncoder().encodeToString(encode(plaintext));
    }

    /**
     * 判断二进制明文和密文是否匹配
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return true / false
     * @throws GeneralSecurityException
     */
    default boolean matches(byte[] plaintext, byte[] ciphertext) throws GeneralSecurityException {
        return Arrays.equals(encode(plaintext), ciphertext);
    }

    /**
     * 判断字符串明文和密文是否匹配
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return true / false
     * @throws GeneralSecurityException
     */
    default boolean matches(String plaintext, String ciphertext) throws GeneralSecurityException {
        return encodeWithBase64(plaintext).equals(ciphertext);
    }

}
