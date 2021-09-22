package io.github.dunwu.tool.bean;

/**
 * Bean 类型转换接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-25
 */
public interface TypeConvert<S, T> {

    /**
     * 将类型 S 转换为 T
     *
     * @param origin 要转换的对象
     * @return /
     */
    T transform(S origin);

}
