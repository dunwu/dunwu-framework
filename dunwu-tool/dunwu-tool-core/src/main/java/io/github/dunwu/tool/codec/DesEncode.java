package io.github.dunwu.tool.codec;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES安全编码：是经典的对称加密算法。密钥仅56位，且迭代次数偏少。已被视为并不安全的加密算法。
 *
 * @author Zhang Peng
 * @since 2016年7月14日
 */
public class DesEncode implements SymmetricEncode {

    public static final String KEY_ALGORITHM_DES = "DES";
    /** 算法/模式/补码方式 */
    public static final String CIPHER_DES_DEFAULT = "DES";
    public static final String CIPHER_DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
    public static final String CIPHER_DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
    public static final String CIPHER_DES_CBC_NOPADDING = "DES/CBC/NoPadding";
    /** 用于生成随机数的种子 */
    private static final String DEFAULT_SEED = "芝麻开门";
    /** 用于生成 Iv 参数 */
    private static final String DEFAULT_IV = "01234567";

    private Key key;
    private Cipher cipher;
    private String type;
    private byte[] ivBytes;

    public DesEncode() throws GeneralSecurityException {
        this(Type.DEFAULT.getKey());
    }

    public  DesEncode(Type type) throws GeneralSecurityException {
        this(type.getKey());
    }

    public DesEncode(String type) throws GeneralSecurityException {
        this(type, DEFAULT_SEED, DEFAULT_IV);
    }

    public DesEncode(String type, String seed, String iv) throws GeneralSecurityException {
        this.type = type;
        this.key = initKey(seed);
        this.ivBytes = iv.getBytes(StandardCharsets.UTF_8);
        this.cipher = Cipher.getInstance(type);
    }

    @Override
    public byte[] encode(byte[] plaintext) throws GeneralSecurityException {
        if (type.equals(CIPHER_DES_CBC_PKCS5PADDING) || type.equals(CIPHER_DES_CBC_NOPADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return cipher.doFinal(plaintext);
    }

    @Override
    public byte[] decode(byte[] ciphertext) throws GeneralSecurityException {
        if (type.equals(CIPHER_DES_CBC_PKCS5PADDING) || type.equals(CIPHER_DES_CBC_NOPADDING)) {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return cipher.doFinal(ciphertext);
    }

    /**
     * 根据随机数种子生成一个密钥
     *
     * @return seed 随机数种子
     * @throws GeneralSecurityException
     */
    private Key initKey(String seed) throws NoSuchAlgorithmException {
        // 根据种子生成一个安全的随机数
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM_DES);
        keyGen.init(secureRandom);
        return keyGen.generateKey();
    }

    public enum Type {

        DEFAULT(CIPHER_DES_DEFAULT),
        ECB_PKCS5PADDING(CIPHER_DES_ECB_PKCS5PADDING),
        CBC_PKCS5PADDING(CIPHER_DES_CBC_PKCS5PADDING),
        CBC_NOPADDING(CIPHER_DES_CBC_NOPADDING);

        private final String key;

        Type(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
