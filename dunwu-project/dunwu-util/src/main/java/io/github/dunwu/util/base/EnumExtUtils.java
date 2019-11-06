package io.github.dunwu.util.base;

import org.apache.commons.lang3.EnumUtils;

import java.util.Collection;
import java.util.EnumSet;

/**
 * 枚举工具类
 * <p>
 * 1. 继承 {@link org.apache.commons.lang3.EnumUtils} 所有方法
 * <p>
 * 2. 扩展部分功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-08-22
 */
public class EnumExtUtils extends EnumUtils {

	private EnumExtUtils() {}

	public static <E extends Enum<E>> EnumSet<E> copyOf(final EnumSet<E> s) {
		return EnumSet.copyOf(s);
	}

	public static <E extends Enum<E>> EnumSet<E> copyOf(final Collection<E> c) {
		return EnumSet.copyOf(c);
	}

	public static <E extends Enum<E>> EnumSet<E> getEnumSet(final Class<E> enumClass) {
		return EnumSet.allOf(enumClass);
	}

}
