package io.github.dunwu.util.code;

import io.github.dunwu.util.code.support.SnowFlakeId;

import java.util.UUID;

/**
 * 生成唯一性 ID 算法的工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-12
 */
public class IdUtil {

	/**
	 * 生成随机 UUID，含 - 字符
	 * @return String
	 */
	public static String randomUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成随机 UUID，不含 - 字符
	 * @return String
	 */
	public static String randomUuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 新建一个 SnowFlakeId 实例
	 * @return SnowFlakeId
	 * @see SnowFlakeId
	 */
	public static SnowFlakeId newSnowFlakeId(long dataCenterId, long machineId) {
		return new SnowFlakeId(dataCenterId, machineId);
	}

}
