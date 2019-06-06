package io.github.dunwu.core;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 8329224751518896737L;

    /**
     * 处理结果，默认为处理成功
     */
    protected Boolean success;

    /**
     * 错误码
     */
    protected String code;

    /**
     * 错误描述。
     */
    protected String msg;

    public BaseResult() {}

    public BaseResult(Boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
