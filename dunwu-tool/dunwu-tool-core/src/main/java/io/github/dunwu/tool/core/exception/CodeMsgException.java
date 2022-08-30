package io.github.dunwu.tool.core.exception;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;

/**
 * 基础异常
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
public class CodeMsgException extends RuntimeException implements Status {

    private static final long serialVersionUID = 6146660782281445735L;

    /**
     * 状态码
     */
    protected int code;

    /**
     * 响应信息
     */
    protected String msg;

    /**
     * 提示信息
     */
    protected String toast;

    public CodeMsgException() {
        this(ResultStatus.FAIL);
    }

    public CodeMsgException(Status status) {
        this(status.getCode(), status.getMsg());
    }

    public CodeMsgException(Status status, String msg) {
        this(status.getCode(), msg, null);
    }

    public CodeMsgException(Status status, String msg, String toast) {
        this(status.getCode(), msg, toast);
    }

    public CodeMsgException(String msg) {
        this(ResultStatus.FAIL, msg);
    }

    public CodeMsgException(int code, String msg) {
        this(code, msg, msg);
    }

    public CodeMsgException(int code, String msg, String toast) {
        super(msg);
        setCode(code);
        setMsg(msg);
        setToast(toast);
    }

    public CodeMsgException(Throwable cause) {
        this(cause, ResultStatus.FAIL);
    }

    public CodeMsgException(Throwable cause, String msg) {
        this(cause, ResultStatus.FAIL, msg);
    }

    public CodeMsgException(Throwable cause, Status status) {
        this(cause, status.getCode(), status.getMsg());
    }

    public CodeMsgException(Throwable cause, Status status, String msg) {
        this(cause, status.getCode(), msg, null);
    }

    public CodeMsgException(Throwable cause, Status status, String msg, String toast) {
        this(cause, status.getCode(), msg, toast);
    }

    public CodeMsgException(Throwable cause, int code, String msg) {
        this(cause, code, msg, null);
    }

    public CodeMsgException(Throwable cause, int code, String msg, String toast) {
        super(msg, cause);
        setCode(code);
        setMsg(msg);
        setToast(toast);
    }

    @Override
    public String getMessage() {
        if (StrUtil.isNotBlank(msg)) {
            return StrUtil.format("[{}]{}", code, msg);
        }
        return super.getMessage();
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

}
