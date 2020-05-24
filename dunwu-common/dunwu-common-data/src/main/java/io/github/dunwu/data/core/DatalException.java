package io.github.dunwu.data.core;

import io.github.dunwu.data.core.constant.Status;

/**
 * 数据异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DatalException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private BaseResult result;

    public DatalException(String message) {
        super(message);
    }

    public DatalException(Throwable cause) {
        super(cause);
    }

    public DatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatalException(BaseResult result) {
        super(result.getMessage());
        this.result = new BaseResult(result.getCode(), result.getMessage());
    }

    public DatalException(Status status) {
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
