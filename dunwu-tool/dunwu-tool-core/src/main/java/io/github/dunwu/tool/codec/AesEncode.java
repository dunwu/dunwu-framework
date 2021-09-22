package io.github.dunwu.tool.codec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * AES安全编码：对称加密算法。DES的替代方案。
 *
 * @author Zhang Peng
 * @since 2016年7月14日
 */
public class AesEncode implements SymmetricEncode {

    public static final String KEY_ALGORITHM_AES = "AES";
    /** 算法/模式/补码方式 */
    public static final String CIPHER_AES_DEFAULT = "AES";
    public static final String CIPHER_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    public static final String CIPHER_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    public static final String CIPHER_AES_CBC_NOPADDING = "AES/CBC/NoPadding";
    /** 用于生成随机数的种子 */
    private static final String DEFAULT_SEED = "芝麻开门";
    /** 用于生成 Iv 参数 */
    private static final String DEFAULT_IV = "0123456789ABCDEF";

    private Key key;
    private Cipher cipher;
    private String type;
    private byte[] ivBytes;

    public AesEncode() throws GeneralSecurityException {
        this(Type.DEFAULT.getKey());
    }

    public AesEncode(Type type) throws GeneralSecurityException {
        this(type.getKey());
    }

    public AesEncode(String type) throws GeneralSecurityException {
        this(type, DEFAULT_SEED, DEFAULT_IV);
    }

    public AesEncode(Type type, String seed, String iv) throws GeneralSecurityException {
        this(type.getKey(), seed, iv);
    }

    public AesEncode(String type, String seed, String iv) throws GeneralSecurityException {
        if (iv.length() != 16 && (type.equals(CIPHER_AES_CBC_PKCS5PADDING) || type.equals(CIPHER_AES_CBC_NOPADDING))) {
            throw new InvalidAlgorithmParameterException("Wrong IV length: must be 16 bytes long");
        }
        this.type = type;
        this.key = initKey(seed);
        this.ivBytes = iv.getBytes(StandardCharsets.UTF_8);
        this.cipher = Cipher.getInstance(type);
    }

    public static void main(String[] args) throws Exception {
        AesEncode aesEncode = new AesEncode(CIPHER_AES_CBC_PKCS5PADDING);

        String msg = "Hello World!";
        System.out.println("[AES加密、解密]");
        System.out.println("message: " + msg);
        byte[] encoded = aesEncode.encode(msg.getBytes("UTF8"));
        String encodedBase64 = Base64.getUrlEncoder().encodeToString(encoded);
        System.out.println("encoded: " + encodedBase64);

        byte[] decodedBase64 = Base64.getUrlDecoder().decode(encodedBase64);
        byte[] decoded = aesEncode.decode(decodedBase64);
        System.out.println("decoded: " + new String(decoded));
    }

    @Override
    public byte[] encode(byte[] plaintext) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
        InvalidAlgorithmParameterException {
        if (type.equals(CIPHER_AES_CBC_PKCS5PADDING) || type.equals(CIPHER_AES_CBC_NOPADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return cipher.doFinal(plaintext);
    }

    @Override
    public byte[] decode(byte[] ciphertext) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
        InvalidAlgorithmParameterException {
        if (type.equals(CIPHER_AES_CBC_PKCS5PADDING) || type.equals(CIPHER_AES_CBC_NOPADDING)) {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return cipher.doFinal(ciphertext);
    }

    /**
     * 根据随机数种子生成一个密钥
     *
     * @return Key
     * @throws NoSuchAlgorithmException
     * @author Zhang Peng
     * @since 2016年7月14日
     */
    private Key initKey(String seed) throws NoSuchAlgorithmException {
        // 根据种子生成一个安全的随机数
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
        keyGen.init(secureRandom);
        return keyGen.generateKey();
    }

    public enum Type {

        DEFAULT(CIPHER_AES_DEFAULT),
        ECB_PKCS5PADDING(CIPHER_AES_ECB_PKCS5PADDING),
        CBC_PKCS5PADDING(CIPHER_AES_CBC_PKCS5PADDING),
        CBC_NOPADDING(CIPHER_AES_CBC_NOPADDING);

        private final String key;

        Type(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
