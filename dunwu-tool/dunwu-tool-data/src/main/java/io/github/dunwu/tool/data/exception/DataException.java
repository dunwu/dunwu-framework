package io.github.dunwu.tool.data.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.CodeMsgException;

/**
 * 数据异常
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public DataException() {
        this(ResultStatus.DATA_ERROR);
    }

    public DataException(Status status) {
        this(status.getCode(), status.getMsg());
    }

    public DataException(Status status, String msg) {
        this(status.getCode(), msg, null);
    }

    public DataException(Status status, String msg, String toast) {
        this(status.getCode(), msg, toast);
    }

    public DataException(String msg) {
        this(ResultStatus.DATA_ERROR, msg);
    }

    public DataException(int code, String msg) {
        this(code, msg, msg);
    }

    public DataException(int code, String msg, String toast) {
        super(code, msg, toast);
    }

    public DataException(Throwable cause) {
        this(cause, ResultStatus.DATA_ERROR);
    }

    public DataException(Throwable cause, String msg) {
        this(cause, ResultStatus.DATA_ERROR, msg);
    }

    public DataException(Throwable cause, Status status) {
        this(cause, status.getCode(), status.getMsg());
    }

    public DataException(Throwable cause, Status status, String msg) {
        this(cause, status.getCode(), msg, null);
    }

    public DataException(Throwable cause, Status status, String msg, String toast) {
        this(cause, status.getCode(), msg, toast);
    }

    public DataException(Throwable cause, int code, String msg) {
        this(cause, code, msg, null);
    }

    public DataException(Throwable cause, int code, String msg, String toast) {
        super(cause, code, msg, toast);
    }
}
