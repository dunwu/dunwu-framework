package io.github.dunwu.tool.core.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 认证异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AuthException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public AuthException() {
        super(ResultStatus.AUTH_ERROR);
    }

    public AuthException(Status status) {
        super(status.getCode(), status.getMsg());
    }

    public AuthException(int code, String msg) {
        super(code, msg);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthException(String msg, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

}
