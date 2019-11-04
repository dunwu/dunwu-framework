package io.github.dunwu.util.base;

import org.apache.commons.lang3.BooleanUtils;

/**
 * Boolean 扩展工具类
 * <p>
 * 1. 封装 {@link org.apache.commons.lang3.BooleanUtils}
 * <p>
 * 2. 扩展部分方法
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-08-22
 */
public class BooleanExtUtils extends BooleanUtils {

	public static Boolean toBooleanDefaultIfNull(final String str, final boolean defaultValue) {
		return BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBooleanObject(str), defaultValue);
	}

	private BooleanExtUtils() {}

}
