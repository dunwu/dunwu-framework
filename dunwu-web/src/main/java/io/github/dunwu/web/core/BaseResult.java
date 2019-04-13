package io.github.dunwu.web.core;

import java.io.Serializable;

/**
 * @author Zhang Peng
 * @since 2019-04-11
 */
public class BaseResult implements Serializable, Cloneable {

    private static final long serialVersionUID = 8329224751518896737L;

    /**
     * 处理结果，默认为处理成功
     */
    protected Boolean success;

    /**
     * 错误码
     */
    protected Integer code;

    /**
     * 错误描述。
     */
    protected String msg;

    public BaseResult() {}

    public BaseResult(Boolean success, Integer code, String msg) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        BaseResult baseResult = (BaseResult)super.clone();
        baseResult.setSuccess(this.success);
        baseResult.setCode(this.code);
        baseResult.setMsg(this.msg);
        return baseResult;
    }

    @Override
    public String toString() {
        return "BaseResponseDTO{" + "success=" + success + ", code=" + code + ", msg='" + msg + '\'' + '}';
    }
}
