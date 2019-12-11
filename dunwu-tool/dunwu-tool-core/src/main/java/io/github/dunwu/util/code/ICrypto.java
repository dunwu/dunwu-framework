package io.github.dunwu.util.code;

import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * 对称加密/解密接口
 */
public interface ICrypto {

    default String decryptToString(byte[] ciphertext)
        throws BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = decryptToBytes(ciphertext);
        Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(bytes);
    }

    byte[] decryptToBytes(byte[] ciphertext)
        throws BadPaddingException, IllegalBlockSizeException;

    default String decryptToString(String ciphertext)
        throws BadPaddingException, IllegalBlockSizeException {
        byte[] bytes = decryptToBytes(ciphertext);
        return new String(bytes);
    }

    byte[] decryptToBytes(String ciphertext)
        throws BadPaddingException, IllegalBlockSizeException;

    default byte[] encryptToBytes(String plaintext)
        throws BadPaddingException, IllegalBlockSizeException {
        return encryptToBytes(plaintext.getBytes());
    }

    byte[] encryptToBytes(byte[] plaintext)
        throws BadPaddingException, IllegalBlockSizeException;

    default String encryptToString(String plaintext)
        throws BadPaddingException, IllegalBlockSizeException {
        return encryptToString(plaintext.getBytes());
    }

    String encryptToString(byte[] plaintext)
        throws BadPaddingException, IllegalBlockSizeException;

}
