package io.github.dunwu.tool.codec;

import cn.hutool.core.codec.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-11
 */
public class MessageDigestUtil {

    private MessageDigestUtil() { }

    public static String encodeWithBase64(String type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return Base64.encodeUrlSafe(encode(type, input, salt));
    }

    public static byte[] encode(String type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        // 根据类型，初始化消息摘要对象
        MessageDigest digest = MessageDigest.getInstance(type);

        // 更新要计算的内容
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }

        // 完成哈希计算，返回摘要
        return digest.digest(input);
    }

    public static String encodeWithBase64(Type type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return Base64.encodeUrlSafe(encode(type, input, salt));
    }

    public static byte[] encode(Type type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return encode(type.name, input, salt);
    }

    enum Type {

        MD2("MD2"),
        MD5("MD5"),
        SHA1("SHA1"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
