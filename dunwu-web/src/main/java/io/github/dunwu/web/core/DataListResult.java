package io.github.dunwu.web.core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Zhang Peng
 * @date 2019-04-11
 */
public class DataListResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = 5215932734667041302L;

    /**
     * 数据对象列表。当 success = true 才有值
     */
    private List<T> data;

    public DataListResult() {
        data = new LinkedList<>();
    }

    public DataListResult(Boolean success, Integer code, String msg, List<T> data) {
        super(success, code, msg);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataListResult{" + "data=" + data + ", success=" + success + ", code=" + code + ", msg='" + msg + '\''
            + '}';
    }
}
