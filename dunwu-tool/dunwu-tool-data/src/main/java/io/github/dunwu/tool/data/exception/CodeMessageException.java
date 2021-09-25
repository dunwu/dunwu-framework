package io.github.dunwu.tool.data.exception;

import io.github.dunwu.tool.data.constant.Status;
import io.github.dunwu.tool.data.constant.enums.ResultStatus;

/**
 * 基础异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
public class CodeMessageException extends RuntimeException {

    /**
     * 状态码
     */
    protected final int code;

    /**
     * 响应信息
     */
    protected final String message;

    public CodeMessageException() {
        this(ResultStatus.FAIL);
    }

    public CodeMessageException(Status status) {
        this(status.getCode(), status.getMessage());
    }

    public CodeMessageException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CodeMessageException(String message) {
        super(message);
        this.code = ResultStatus.FAIL.getCode();
        this.message = message;
    }

    public CodeMessageException(Throwable cause) {
        this(ResultStatus.FAIL.getCode(), cause.getMessage(), cause);
    }

    public CodeMessageException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public CodeMessageException(String message, Throwable cause) {
        this(ResultStatus.FAIL.getCode(), message, cause);
    }

    public CodeMessageException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ResultStatus.FAIL.getCode();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 覆盖原方法，解决抓取堆性能开销
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return code;
    }

}
