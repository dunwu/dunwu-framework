package io.github.dunwu.data.core;

import io.github.dunwu.data.core.constant.Status;

/**
 * 系统应答状态码
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private BaseResult result;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(BaseResult result) {
        super(result.getMessage());
        this.result = new BaseResult(result.getCode(), result.getMessage());
    }

    public GlobalException(Status status) {
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
