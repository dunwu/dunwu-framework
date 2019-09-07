package io.github.dunwu.core;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public interface ErrorCode {

	String SUCCESS_CODE = "0";

	String getCode();

	String getMessage();

	String getTemplate();

}
