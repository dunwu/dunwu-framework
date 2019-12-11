package io.github.dunwu.util.clone;

/**
 * 克隆支持类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-09
 */
public class CloneSupport<T> implements Cloneable<T> {

    @Override
    public T clone() throws CloneNotSupportedException {
        return (T) super.clone();
    }

}
