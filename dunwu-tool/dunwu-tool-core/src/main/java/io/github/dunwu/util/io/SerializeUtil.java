package io.github.dunwu.util.io;

import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p> 二进制序列化/反序列化工具
 *
 * <p> 本工具类封装自 <a href="https://github.com/RuedigerMoeller/fast-serialization">FST</a>，使用本工具类需要引入 fst jar 包
 *
 * <p> 可用于替代性能很差的 JDK 默认序列化/反序列化机制。
 *
 * <p> 适用于不需要跨语言环境，且对性能有一定要求的序列化场景。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/RuedigerMoeller/fast-serialization">FST</a>
 * @since 2019-11-22
 */
public class SerializeUtil {

    private static FSTConfiguration DEFAULT_CONFIG = FSTConfiguration.createDefaultConfiguration();

    /**
     * 将对象序列化为 byte 数组后，再使用 Base64 编码
     *
     * @param obj 任意对象
     * @param <T> 对象的类型
     * @return 序列化后的字符串
     */
    public static <T> String writeToString(T obj) {
        byte[] bytes = writeToBytes(obj);
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * 将对象序列化为 byte 数组
     *
     * @param obj 任意对象
     * @param <T> 对象的类型
     * @return 序列化后的 byte 数组
     */
    public static <T> byte[] writeToBytes(T obj) {
        return DEFAULT_CONFIG.asByteArray(obj);
    }

    /**
     * 将字符串反序列化为原对象，先使用 Base64 解码
     *
     * @param str   {@link #writeToString} 方法序列化后的字符串
     * @param clazz 原对象的类型
     * @param <T>   原对象的类型
     * @return 原对象
     */
    public static <T> T readFromString(String str, Class<T> clazz) throws IOException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return readFromBytes(Base64.getDecoder().decode(bytes), clazz);
    }

    /**
     * 将 byte 数组反序列化为原对象
     *
     * @param bytes {@link #writeToBytes} 方法序列化后的 byte 数组
     * @param clazz 原对象的类型
     * @param <T>   原对象的类型
     * @return 原对象
     */
    public static <T> T readFromBytes(byte[] bytes, Class<T> clazz) throws IOException {
        Object obj = DEFAULT_CONFIG.asObject(bytes);
        if (clazz.isInstance(obj)) {
            return (T) obj;
        } else {
            throw new IOException("derialize failed");
        }
    }

}
