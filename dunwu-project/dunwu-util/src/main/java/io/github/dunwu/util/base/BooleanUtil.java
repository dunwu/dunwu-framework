package io.github.dunwu.util.base;

import org.apache.commons.lang3.BooleanUtils;

/**
 * Boolean 工具类
 * <p>
 * 1. 封装 {@link org.apache.commons.lang3.BooleanUtils}
 * <p>
 * 2. 扩展部分方法
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-08-22
 */
public class BooleanUtil extends BooleanUtils {

	/**
	 * 支持true/false,on/off, y/n, yes/no的转换, str为空或无法分析时返回defaultValue
	 */
	public static Boolean toBooleanObject(String str, boolean defaultValue) {
		return BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBooleanObject(str),
			defaultValue);
	}

}
