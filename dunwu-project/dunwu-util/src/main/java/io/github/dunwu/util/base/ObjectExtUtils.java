package io.github.dunwu.util.base;

import com.google.common.base.Objects;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;

/**
 * 1. Object打印优化，主要解决数组的打印 2. 多个对象的HashCode串联
 */
public class ObjectExtUtils extends ObjectUtils {

	/**
	 * JDK7 引入的Null安全的equals
	 */
	public static boolean equals(final Object object1, final Object object2) {
		return Objects.equal(object1, object2);
	}

	/**
	 * 多个对象的 HashCode 串联, 组成新的 HashCode
	 */
	public static int hashCode(final Object... objects) {
		return Arrays.hashCode(objects);
	}

	/**
	 * 对象的toString(), 处理了对象为数组的情况，JDK的默认toString()只打数组的地址如 "[Ljava.lang.Integer;@490d6c15.
	 */
	public static String toPrettyString(final Object value) {
		if (value == null) {
			return "null";
		}

		Class<?> type = value.getClass();
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();
			if (componentType.isPrimitive()) {
				return primitiveArrayToString(value, componentType);
			}
			else {
				return objectArrayToString(value);
			}
		}
		else if (value instanceof Iterable) {
			// 因为Collection的处理也是默认调用元素的toString(),
			// 为了处理元素是数组的情况，同样需要重载
			return collectionToString((Iterable) value);
		}

		return value.toString();
	}

	private static String primitiveArrayToString(final Object value, final Class componentType) {
		StringBuilder sb = new StringBuilder();

		if (componentType == int.class) {
			sb.append(Arrays.toString((int[]) value));
		}
		else if (componentType == long.class) {
			sb.append(Arrays.toString((long[]) value));
		}
		else if (componentType == double.class) {
			sb.append(Arrays.toString((double[]) value));
		}
		else if (componentType == float.class) {
			sb.append(Arrays.toString((float[]) value));
		}
		else if (componentType == boolean.class) {
			sb.append(Arrays.toString((boolean[]) value));
		}
		else if (componentType == short.class) {
			sb.append(Arrays.toString((short[]) value));
		}
		else if (componentType == byte.class) {
			sb.append(Arrays.toString((byte[]) value));
		}
		else if (componentType == char.class) {
			sb.append(Arrays.toString((char[]) value));
		}
		else {
			throw new IllegalArgumentException("invalid array type");
		}

		return sb.toString();
	}

	private static String objectArrayToString(final Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');

		Object[] array = (Object[]) value;
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(toPrettyString(array[i]));
		}
		sb.append(']');
		return sb.toString();
	}

	private static String collectionToString(final Iterable iterable) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		int i = 0;
		for (Object o : iterable) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(toPrettyString(o));
			i++;
		}
		sb.append('}');
		return sb.toString();
	}

}
