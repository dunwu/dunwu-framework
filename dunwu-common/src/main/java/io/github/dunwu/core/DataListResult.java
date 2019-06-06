package io.github.dunwu.core;

import java.util.Collection;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public class DataListResult<T> extends BaseResult {

    private static final long serialVersionUID = 143903331365808445L;

    /**
     * 当前查询页的数据列表
     */
    protected Collection<T> data;

    public DataListResult(BaseResult result) {
        this.success = result.getSuccess();
        this.code = result.getCode();
        this.msg = result.getMsg();
        this.data = null;
    }

    public DataListResult(Boolean success, String code, String msg, Collection<T> data) {
        super(success, code, msg);
        this.data = data;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }
}
