package io.github.dunwu.tool.codec;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * DESede安全编码，DES的升级版，支持更长的密钥，基本算法不变。
 *
 * @author Zhang Peng
 * @since 2016年7月20日
 */
public class DESede {

    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 算法名称/加密模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 密钥
     */
    private Key key;

    public DESede() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.key = initKey();
    }

    private Key initKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        // 标准的密钥生成
        // KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        // keyGen.init(112);

        // 标准的密钥生成不支持128位。如果要使用，需引入Bouncy Castle的加密算法，方法如下
        // Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static void main(String[] args) throws Exception {
        DESede desede = new DESede();
        String message = "Hello World!";
        byte[] ciphertext = desede.encrypt(message.getBytes());
        System.out.println(Base64.encodeUrlSafe(ciphertext));

        byte[] plaintext = desede.decrypt(ciphertext);
        System.out.println(new String(plaintext));
    }

    public byte[] encrypt(byte[] plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public byte[] decrypt(byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

}
