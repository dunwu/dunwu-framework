package io.github.dunwu.core;

import java.io.Serializable;

/**
 * @author Zhang Peng
 * @since 2019-04-11
 */
public class DataResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = -8822425157312556902L;

    /**
     * 数据对象。当 success = true 才有值
     */
    private T data;

    public DataResult() { }

    public DataResult(Boolean success, Integer code, String msg, T data) {
        super(success, code, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResult{" + "data=" + data + ", success=" + success + ", code=" + code + ", msg='" + msg + '\''
            + '}';
    }
}
