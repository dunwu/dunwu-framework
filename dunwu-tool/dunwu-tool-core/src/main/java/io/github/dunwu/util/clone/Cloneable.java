package io.github.dunwu.util.clone;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-09
 */
public interface Cloneable<T> extends java.lang.Cloneable {

	/**
	 * 克隆当前对象，浅拷贝
	 *
	 * @return 克隆后的对象
	 */
	T clone() throws CloneNotSupportedException;

}
