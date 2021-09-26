package io.github.dunwu.tool.core.exception;

import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 基础异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
public class CodeMsgException extends RuntimeException {

    /**
     * 状态码
     */
    protected final int code;

    /**
     * 响应信息
     */
    protected final String msg;

    public CodeMsgException() {
        this(ResultStatus.FAIL);
    }

    public CodeMsgException(Status status) {
        this(status.getCode(), status.getMsg());
    }

    public CodeMsgException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CodeMsgException(String msg) {
        super(msg);
        this.code = ResultStatus.FAIL.getCode();
        this.msg = msg;
    }

    public CodeMsgException(Throwable cause) {
        this(ResultStatus.FAIL.getCode(), cause.getMessage(), cause);
    }

    public CodeMsgException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public CodeMsgException(String msg, Throwable cause) {
        this(ResultStatus.FAIL.getCode(), msg, cause);
    }

    public CodeMsgException(String msg, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
        this.code = ResultStatus.FAIL.getCode();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
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
