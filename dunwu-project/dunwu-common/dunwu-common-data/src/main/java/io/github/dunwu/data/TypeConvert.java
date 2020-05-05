package io.github.dunwu.data;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-25
 */
public interface TypeConvert<S, T> {

    T transform(S origin);

}
