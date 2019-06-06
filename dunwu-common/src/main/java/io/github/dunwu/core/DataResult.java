package io.github.dunwu.core;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public class DataResult<T> extends BaseResult {

    private static final long serialVersionUID = 5967600057844753703L;

    /**
     * 数据对象。当 successBaseResult = true 才有值
     */
    private T data;

    public DataResult(BaseResult result) {
        this.success = result.getSuccess();
        this.code = result.getCode();
        this.msg = result.getMsg();
        this.data = null;
    }

    public DataResult(Boolean success, String code, String msg, T data) {
        super(success, code, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
