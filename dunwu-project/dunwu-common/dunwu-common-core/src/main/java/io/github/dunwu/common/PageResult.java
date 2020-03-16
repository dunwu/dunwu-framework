package io.github.dunwu.common;

import io.github.dunwu.common.constant.ErrorCode;
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
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    private Pagination<T> data;

    public PageResult() {}

    public PageResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public PageResult(ErrorCode appCode) {
        super(appCode);
    }

    public PageResult(Pagination<T> data, Boolean success, String code, String message) {
        super(success, code, message);
        this.data = data;
    }

    public PageResult(Pagination<T> data, Boolean success, String code, List<String> messages) {
        super(success, code, messages);
        this.data = data;
    }

    public PageResult(Pagination<T> data, Boolean success, String code, String message, Object... params) {
        super(success, code, message, params);
        this.data = data;
    }

}
