package io.github.dunwu.tool.core.exception;

import io.github.dunwu.tool.core.constant.CodeMsg;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 认证异常
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AuthException extends CodeMsgException {

    private static final long serialVersionUID = -7027578114976830416L;

    public AuthException() {
        this(ResultStatus.AUTH_ERROR);
    }

    public AuthException(CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    public AuthException(CodeMsg codeMsg, String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    public AuthException(CodeMsg codeMsg, String msg, String toast) {
        this(codeMsg.getCode(), msg, toast);
    }

    public AuthException(String msg) {
        this(ResultStatus.AUTH_ERROR, msg);
    }

    public AuthException(int code, String msg) {
        this(code, msg, msg);
    }

    public AuthException(int code, String msg, String toast) {
        super(code, msg, toast);
    }

    public AuthException(Throwable cause) {
        this(cause, ResultStatus.AUTH_ERROR);
    }

    public AuthException(Throwable cause, String msg) {
        this(cause, ResultStatus.AUTH_ERROR, msg);
    }

    public AuthException(Throwable cause, CodeMsg codeMsg) {
        this(cause, codeMsg.getCode(), codeMsg.getMsg());
    }

    public AuthException(Throwable cause, CodeMsg codeMsg, String msg) {
        this(cause, codeMsg.getCode(), msg, null);
    }

    public AuthException(Throwable cause, CodeMsg codeMsg, String msg, String toast) {
        this(cause, codeMsg.getCode(), msg, toast);
    }

    public AuthException(Throwable cause, int code, String msg) {
        this(cause, code, msg, null);
    }

    public AuthException(Throwable cause, int code, String msg, String toast) {
        super(cause, code, msg, toast);
    }
}
