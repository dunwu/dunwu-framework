package io.github.dunwu.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataListResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 当前查询页的数据列表
     */
    protected Collection<T> data;

    public DataListResult() {}

    public DataListResult(IAppCode appCode) {
        super(appCode);
    }

    public DataListResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public DataListResult(Collection<T> data, Boolean success, String code, String... messages) {
        super(success, code, messages);
        this.data = data;
    }
}
