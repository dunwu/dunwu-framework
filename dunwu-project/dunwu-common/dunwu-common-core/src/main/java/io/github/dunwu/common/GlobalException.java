package io.github.dunwu.common;

import io.github.dunwu.common.constant.Status;

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

    public GlobalException(BaseResult result) {
        this.result = new BaseResult(result.getCode(), result.message());
    }

    public GlobalException(Status status) {
        this.result = BaseResult.fail(status);
    }

    @Override
    public String toString() {
        return "GlobalException{" + "result=" + result + '}';
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
