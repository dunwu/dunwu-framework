package io.github.dunwu.common.encode;

import cn.hutool.core.codec.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要工具类，支持 MD2、MD5、SHA1、SHA-256、SHA-384、SHA-512 等摘要算法。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-11
 */
public class MessageDigestUtil {

    private MessageDigestUtil() { }

    /**
     * 对输入数据进行消息摘要计算，并返回 Base64 编码的字符串
     *
     * @param type  摘要算法名称（如 "MD5"、"SHA-256"）
     * @param input 待摘要的数据
     * @param salt  盐值，可为 null
     * @return Base64 编码的摘要字符串
     * @throws NoSuchAlgorithmException 指定的摘要算法不存在
     */
    public static String encodeWithBase64(String type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return Base64.encodeUrlSafe(encode(type, input, salt));
    }

    /**
     * 对输入数据进行消息摘要计算
     *
     * @param type  摘要算法名称（如 "MD5"、"SHA-256"）
     * @param input 待摘要的数据
     * @param salt  盐值，可为 null
     * @return 摘要字节数组
     * @throws NoSuchAlgorithmException 指定的摘要算法不存在
     */
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

    /**
     * 对输入数据进行消息摘要计算，并返回 Base64 编码的字符串
     *
     * @param type  摘要算法类型枚举
     * @param input 待摘要的数据
     * @param salt  盐值，可为 null
     * @return Base64 编码的摘要字符串
     * @throws NoSuchAlgorithmException 指定的摘要算法不存在
     */
    public static String encodeWithBase64(Type type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return Base64.encodeUrlSafe(encode(type, input, salt));
    }

    /**
     * 对输入数据进行消息摘要计算
     *
     * @param type  摘要算法类型枚举
     * @param input 待摘要的数据
     * @param salt  盐值，可为 null
     * @return 摘要字节数组
     * @throws NoSuchAlgorithmException 指定的摘要算法不存在
     */
    public static byte[] encode(Type type, byte[] input, byte[] salt) throws NoSuchAlgorithmException {
        return encode(type.name, input, salt);
    }

    /**
     * 消息摘要算法类型枚举
     */
    public enum Type {

        /** MD2 摘要算法 */
        MD2("MD2"),
        /** MD5 摘要算法 */
        MD5("MD5"),
        /** SHA1 摘要算法 */
        SHA1("SHA1"),
        /** SHA-256 摘要算法 */
        SHA256("SHA-256"),
        /** SHA-384 摘要算法 */
        SHA384("SHA-384"),
        /** SHA-512 摘要算法 */
        SHA512("SHA-512");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        /**
         * 获取摘要算法名称
         *
         * @return 算法名称字符串
         */
        public String getName() {
            return this.name;
        }
    }

}
