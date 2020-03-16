package io.github.dunwu.tool.exceptions;

import io.github.dunwu.tool.util.StringUtil;

/**
 * 未初始化异常
 *
 * @author xiaoleilu
 */
public class NotInitedException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public NotInitedException(Throwable e) {
        super(e);
    }

    public NotInitedException(String message) {
        super(message);
    }

    public NotInitedException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public NotInitedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NotInitedException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
