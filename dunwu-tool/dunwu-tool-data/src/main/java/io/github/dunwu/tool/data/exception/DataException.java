package io.github.dunwu.tool.data.exception;

import io.github.dunwu.tool.data.constant.Status;
import io.github.dunwu.tool.data.constant.enums.ResultStatus;

/**
 * 数据异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends CodeMessageException {

    private static final long serialVersionUID = -7027578114976830416L;

    public DataException() {
        super(ResultStatus.DATA_ERROR);
    }

    public DataException(Status status) {
        super(status.getCode(), status.getMessage());
    }

    public DataException(int code, String message) {
        super(code, message);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
