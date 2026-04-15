package io.github.dunwu.common.encode;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * AES 安全编码：对称加密算法，是 DES 的替代方案。
 * <p>
 * 支持 ECB、CBC 等多种模式。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2016-07-14
 */
public class AesEncode implements SymmetricEncode {

    /** AES 密钥算法 */
    public static final String KEY_ALGORITHM_AES = "AES";
    /** 默认算法/模式/补码方式 */
    public static final String CIPHER_AES_DEFAULT = "AES";
    /** ECB 模式，PKCS5Padding 补码 */
    public static final String CIPHER_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    /** CBC 模式，PKCS5Padding 补码 */
    public static final String CIPHER_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    /** CBC 模式，无补码 */
    public static final String CIPHER_AES_CBC_NOPADDING = "AES/CBC/NoPadding";
    /** 用于生成随机数的种子 */
    private static final String DEFAULT_SEED = "芝麻开门";
    /** 用于生成 Iv 参数 */
    private static final String DEFAULT_IV = "0123456789ABCDEF";

    private Key key;
    private Cipher cipher;
    private String type;
    private byte[] ivBytes;

    /**
     * 使用默认模式构造 AES 编码器
     *
     * @throws GeneralSecurityException 初始化过程中发生安全异常
     */
    public AesEncode() throws GeneralSecurityException {
        this(Type.DEFAULT.getKey());
    }

    /**
     * 使用指定枚举类型构造 AES 编码器
     *
     * @param type AES 加密类型枚举
     * @throws GeneralSecurityException 初始化过程中发生安全异常
     */
    public AesEncode(Type type) throws GeneralSecurityException {
        this(type.getKey());
    }

    /**
     * 使用指定算法类型字符串构造 AES 编码器
     *
     * @param type 算法/模式/补码方式字符串
     * @throws GeneralSecurityException 初始化过程中发生安全异常
     */
    public AesEncode(String type) throws GeneralSecurityException {
        this(type, DEFAULT_SEED, DEFAULT_IV);
    }

    /**
     * 使用指定枚举类型、随机数种子和 IV 构造 AES 编码器
     *
     * @param type AES 加密类型枚举
     * @param seed 随机数种子
     * @param iv   初始化向量（CBC 模式下必须为 16 字节）
     * @throws GeneralSecurityException 初始化过程中发生安全异常
     */
    public AesEncode(Type type, String seed, String iv) throws GeneralSecurityException {
        this(type.getKey(), seed, iv);
    }

    /**
     * 使用指定算法类型字符串、随机数种子和 IV 构造 AES 编码器
     *
     * @param type 算法/模式/补码方式字符串
     * @param seed 随机数种子
     * @param iv   初始化向量（CBC 模式下必须为 16 字节）
     * @throws GeneralSecurityException 初始化过程中发生安全异常
     */
    public AesEncode(String type, String seed, String iv) throws GeneralSecurityException {
        if (iv.length() != 16 && (type.equals(CIPHER_AES_CBC_PKCS5PADDING) || type.equals(CIPHER_AES_CBC_NOPADDING))) {
            throw new InvalidAlgorithmParameterException("Wrong IV length: must be 16 bytes long");
        }
        this.type = type;
        this.key = initKey(seed);
        this.ivBytes = iv.getBytes(StandardCharsets.UTF_8);
        this.cipher = Cipher.getInstance(type);
    }

    /**
     * 演示 AES 加密和解密
     *
     * @param args 命令行参数
     * @throws Exception 加解密过程中发生异常
     */
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
     * 根据随机数种子生成一个 AES 密钥
     *
     * @param seed 随机数种子
     * @return 生成的密钥
     * @throws NoSuchAlgorithmException 指定的算法不存在
     */
    private Key initKey(String seed) throws NoSuchAlgorithmException {
        // 根据种子生成一个安全的随机数
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
        keyGen.init(secureRandom);
        return keyGen.generateKey();
    }

    /**
     * AES 加密类型枚举
     */
    public enum Type {

        /** 默认模式 */
        DEFAULT(CIPHER_AES_DEFAULT),
        /** ECB 模式，PKCS5Padding 补码 */
        ECB_PKCS5PADDING(CIPHER_AES_ECB_PKCS5PADDING),
        /** CBC 模式，PKCS5Padding 补码 */
        CBC_PKCS5PADDING(CIPHER_AES_CBC_PKCS5PADDING),
        /** CBC 模式，无补码 */
        CBC_NOPADDING(CIPHER_AES_CBC_NOPADDING);

        private final String key;

        Type(String key) {
            this.key = key;
        }

        /**
         * 获取算法/模式/补码方式字符串
         *
         * @return 算法类型字符串
         */
        public String getKey() {
            return key;
        }
    }

}
