package io.github.dunwu.tool.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-11
 */
public class MessageDigestUtil {

    private MessageDigestUtil() {}

    public static byte[] encode(String type, byte[] input) throws Exception {
        // 根据类型，初始化消息摘要对象
        MessageDigest md5Digest = MessageDigest.getInstance(type);

        // 更新要计算的内容
        md5Digest.update(input);

        // 完成哈希计算，返回摘要
        return md5Digest.digest();
    }

    public static byte[] encode(String type, String input) throws Exception {
        return encode(type, input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encode(MessageDigestType type, byte[] input) throws Exception {
        return encode(type.name, input);
    }

    public static byte[] encode(MessageDigestType type, String input) throws Exception {
        return encode(type, input.getBytes(StandardCharsets.UTF_8));
    }

    enum MessageDigestType {

        MD2("MD2"),
        MD5("MD5"),
        SHA1("SHA1"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");

        private String name;

        MessageDigestType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
