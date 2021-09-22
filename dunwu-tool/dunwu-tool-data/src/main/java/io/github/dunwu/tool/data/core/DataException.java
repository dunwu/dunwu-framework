package io.github.dunwu.tool.data.core;

import io.github.dunwu.tool.data.core.constant.Status;

/**
 * 数据异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private BaseResult result;

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(BaseResult result) {
        super(result.getMessage());
        this.result = new BaseResult(result.getCode(), result.getMessage());
    }

    public DataException(Status status) {
        super(status.getMessage());
        this.result = BaseResult.fail(status);
    }

    /**
     * 覆盖原方法，解决抓取堆性能开销
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public BaseResult getResult() {
        return result;
    }

}
