package io.github.dunwu.admin.exception;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.constant.Status;
import org.springframework.security.core.AuthenticationException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
public class CheckCodeException extends AuthenticationException {

    private static final long serialVersionUID = 461900083319901168L;

    private BaseResult result;

    public CheckCodeException(String message) {
        super(message);
    }

    public CheckCodeException(BaseResult result) {
        super(result.getMessage());
    }

    public CheckCodeException(Status status) {
        super(status.getMessage());
    }

}
