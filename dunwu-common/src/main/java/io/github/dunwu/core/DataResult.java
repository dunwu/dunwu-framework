package io.github.dunwu.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 数据对象。当 successBaseResult = true 才有值
     */
    private T data;

    public DataResult() { }

    public DataResult(IAppCode appCode) {
        super(appCode);
    }

    public DataResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public DataResult(T data, Boolean success, String code, String... messages) {
        super(success, code, messages);
        this.data = data;
    }

    public DataResult(T data, Boolean success, String code, List<String> messages) {
        super(success, code, messages);
        this.data = data;
    }
}
