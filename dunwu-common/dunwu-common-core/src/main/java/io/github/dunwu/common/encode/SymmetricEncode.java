package io.github.dunwu.common.encode;

import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * 对称加密接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public interface SymmetricEncode extends Encode {

    /**
     * 对二进制密文解密，返回二进制
     *
     * @param ciphertext 密文
     * @return 解密后的明文字节数组
     * @throws GeneralSecurityException 解密过程中发生安全异常
     */
    byte[] decode(byte[] ciphertext) throws GeneralSecurityException;

    /**
     * 对二进制密文解密，返回字符串
     *
     * @param ciphertext 密文
     * @return 解密后的明文字符串
     * @throws GeneralSecurityException 解密过程中发生安全异常
     */
    default String decodeStr(byte[] ciphertext) throws GeneralSecurityException {
        return new String(decode(ciphertext));
    }

    /**
     * 对 Base64 二次编码后的密文进行解密
     *
     * @param ciphertext Base64 编码的密文
     * @return 解密后的明文字节数组
     * @throws GeneralSecurityException 解密过程中发生安全异常
     */
    default byte[] decodeFromBase64(String ciphertext) throws GeneralSecurityException {
        return decode(Base64.getUrlDecoder().decode(ciphertext));
    }

    /**
     * 对 Base64 二次编码后的密文进行解密，返回字符串
     *
     * @param ciphertext Base64 编码的密文
     * @return 解密后的明文字符串
     * @throws GeneralSecurityException 解密过程中发生安全异常
     */
    default String decodeStrFromBase64(String ciphertext) throws GeneralSecurityException {
        return new String(decodeFromBase64(ciphertext));
    }

}
