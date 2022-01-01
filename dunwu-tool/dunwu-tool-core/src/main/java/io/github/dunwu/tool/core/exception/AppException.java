package io.github.dunwu.tool.core.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 应用异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class AppException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public AppException() {
        super(ResultStatus.AUTH_ERROR);
    }

    public AppException(Status status) {
        super(status.getCode(), status.getMsg());
    }

    public AppException(int code, String msg) {
        super(code, msg);
    }

    public AppException(String msg) {
        super(msg);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AppException(String msg, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

}
