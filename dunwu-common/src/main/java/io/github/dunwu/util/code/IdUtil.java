package io.github.dunwu.util.code;

import io.github.dunwu.util.number.RandomUtil;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 生成唯一性 ID 算法的工具类
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-12
 */
public class IdUtil {
    private static SecureRandom random = new SecureRandom();

    /**
     * 生成 UUID，含 - 字符
     * @return String
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成 UUID，不含 - 字符
     * @return String
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用 ThreadLocalRandom 随机生成 Long.
     */
    public static long randomLong() {
        return RandomUtil.nextLong();
    }

    /**
     * 基于 encodeBase64UrlSafe 随机生成bytes.
     */
    public static String randomBase64(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtil.encodeBase64UrlSafe(randomBytes);
    }
}
