package io.github.dunwu.exception;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.constant.Status;

/**
 * 数据校验异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 461900083319901168L;

    private BaseResult result;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(BaseResult result) {
        super(result.getMessage());
    }

    public ValidateException(Status status) {
        super(status.getMessage());
    }

}
