package io.github.dunwu.tool.data.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.CodeMsgException;

/**
 * 数据异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public DataException() {
        super(ResultStatus.DATA_ERROR);
    }

    public DataException(Status status) {
        super(status.getCode(), status.getMsg());
    }

    public DataException(int code, String msg) {
        super(code, msg);
    }

    public DataException(String msg) {
        super(msg);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public DataException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DataException(String msg, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

}
