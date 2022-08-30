package io.github.dunwu.tool.core.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 默认异常
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class DefaultException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public DefaultException() {
        this(ResultStatus.FAIL);
    }

    public DefaultException(Status status) {
        this(status.getCode(), status.getMsg());
    }

    public DefaultException(Status status, String msg) {
        this(status.getCode(), msg, null);
    }

    public DefaultException(Status status, String msg, String toast) {
        this(status.getCode(), msg, toast);
    }

    public DefaultException(String msg) {
        this(ResultStatus.FAIL, msg);
    }

    public DefaultException(int code, String msg) {
        this(code, msg, msg);
    }

    public DefaultException(int code, String msg, String toast) {
        super(code, msg, toast);
    }

    public DefaultException(Throwable cause) {
        this(cause, ResultStatus.FAIL);
    }

    public DefaultException(Throwable cause, String msg) {
        this(cause, ResultStatus.FAIL, msg);
    }

    public DefaultException(Throwable cause, Status status) {
        this(cause, status.getCode(), status.getMsg());
    }

    public DefaultException(Throwable cause, Status status, String msg) {
        this(cause, status.getCode(), msg, null);
    }

    public DefaultException(Throwable cause, Status status, String msg, String toast) {
        this(cause, status.getCode(), msg, toast);
    }

    public DefaultException(Throwable cause, int code, String msg) {
        this(cause, code, msg, null);
    }

    public DefaultException(Throwable cause, int code, String msg, String toast) {
        super(cause, code, msg, toast);
    }

}
