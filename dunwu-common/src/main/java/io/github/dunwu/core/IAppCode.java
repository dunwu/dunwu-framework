package io.github.dunwu.core;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public interface IAppCode {
    String SUCCESS_VALUE = "0";

    String code();
    String message();
    String detail();
}
