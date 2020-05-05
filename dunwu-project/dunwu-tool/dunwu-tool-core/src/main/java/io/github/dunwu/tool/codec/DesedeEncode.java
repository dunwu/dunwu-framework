package io.github.dunwu.tool.codec;

import cn.hutool.core.codec.Base64;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * DESede安全编码，DES的升级版，支持更长的密钥，基本算法不变。
 *
 * @author Zhang Peng
 * @since 2016年7月20日
 */
public class DesedeEncode implements SymmetricEncode {

    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "DESede";

    /** 算法/模式/补码方式 */
    public static final String CIPHER_DESEDE_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
    public static final String CIPHER_DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";
    public static final String CIPHER_DESEDE_CBC_NOPADDING = "DESede/CBC/NoPadding";
    /** 用于生成随机数的种子 */
    private static final String DEFAULT_SEED = "芝麻开门";
    /** 用于生成 Iv 参数 */
    private static final String DEFAULT_IV = "01234567";

    private Key key;
    private Cipher cipher;
    private String type;
    private byte[] ivBytes;

    public DesedeEncode() throws GeneralSecurityException {
        this.key = initKey(DEFAULT_SEED);
        this.cipher = Cipher.getInstance(CIPHER_DESEDE_ECB_PKCS5PADDING);
    }

    public DesedeEncode(Type type) throws GeneralSecurityException {
        this(type.getKey());
    }

    public DesedeEncode(String type) throws GeneralSecurityException {
        this(type, DEFAULT_SEED, DEFAULT_IV);
    }

    public DesedeEncode(String type, String seed, String iv) throws GeneralSecurityException {
        this.type = type;
        this.key = initKey(seed);
        this.ivBytes = iv.getBytes(StandardCharsets.UTF_8);
        this.cipher = Cipher.getInstance(type);
    }

    public static void main(String[] args) throws Exception {
        DesedeEncode desede = new DesedeEncode();
        String message = "Hello World!";
        byte[] ciphertext = desede.encode(message.getBytes());
        System.out.println(Base64.encodeUrlSafe(ciphertext));

        byte[] plaintext = desede.decode(ciphertext);
        System.out.println(new String(plaintext));
    }

    @Override
    public byte[] encode(byte[] plaintext) throws GeneralSecurityException {
        if (type.equals(CIPHER_DESEDE_CBC_PKCS5PADDING) || type.equals(CIPHER_DESEDE_CBC_NOPADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return cipher.doFinal(plaintext);
    }

    @Override
    public byte[] decode(byte[] ciphertext) throws GeneralSecurityException {
        if (type.equals(CIPHER_DESEDE_CBC_PKCS5PADDING) || type.equals(CIPHER_DESEDE_CBC_NOPADDING)) {
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
    private Key initKey(String seed) throws GeneralSecurityException {
        // 根据种子生成一个安全的随机数
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());

        // 标准的密钥生成
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGen.init(112, secureRandom);

        // 标准的密钥生成不支持128位。如果要使用，需引入Bouncy Castle的加密算法，方法如下
        // Security.addProvider(new BouncyCastleProvider());
        // KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
        // keyGen.init(128, secureRandom);
        return keyGen.generateKey();
    }

    public enum Type {

        ECB_PKCS5PADDING(CIPHER_DESEDE_ECB_PKCS5PADDING),
        CBC_PKCS5PADDING(CIPHER_DESEDE_CBC_PKCS5PADDING),
        CBC_NOPADDING(CIPHER_DESEDE_CBC_NOPADDING);

        private final String key;

        Type(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
