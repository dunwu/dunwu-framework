package io.github.dunwu.tool.data.exception;

import io.github.dunwu.tool.core.constant.CodeMsg;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.CodeMsgException;

/**
 * 数据异常
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public DataException() {
        this(ResultCode.DATA_ERROR);
    }

    public DataException(CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    public DataException(CodeMsg codeMsg, String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    public DataException(CodeMsg codeMsg, String msg, String toast) {
        this(codeMsg.getCode(), msg, toast);
    }

    public DataException(String msg) {
        this(ResultCode.DATA_ERROR, msg);
    }

    public DataException(int code, String msg) {
        this(code, msg, msg);
    }

    public DataException(int code, String msg, String toast) {
        super(code, msg, toast);
    }

    public DataException(Throwable cause) {
        this(cause, ResultCode.DATA_ERROR);
    }

    public DataException(Throwable cause, String msg) {
        this(cause, ResultCode.DATA_ERROR, msg);
    }

    public DataException(Throwable cause, CodeMsg codeMsg) {
        this(cause, codeMsg.getCode(), codeMsg.getMsg());
    }

    public DataException(Throwable cause, CodeMsg codeMsg, String msg) {
        this(cause, codeMsg.getCode(), msg, null);
    }

    public DataException(Throwable cause, CodeMsg codeMsg, String msg, String toast) {
        this(cause, codeMsg.getCode(), msg, toast);
    }

    public DataException(Throwable cause, int code, String msg) {
        this(cause, code, msg, null);
    }

    public DataException(Throwable cause, int code, String msg, String toast) {
        super(cause, code, msg, toast);
    }
}
