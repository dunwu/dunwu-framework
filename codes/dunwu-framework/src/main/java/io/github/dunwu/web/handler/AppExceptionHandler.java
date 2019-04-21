package io.github.dunwu.web.handler;

import io.github.dunwu.core.AppException;
import io.github.dunwu.core.Result;
import io.github.dunwu.core.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zhang Peng
 * @since 2019-04-21
 */
@ResponseBody
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        if (e instanceof AppException) {
            return ((AppException) e).getResult();
        }
        return ResultUtil.fail();
    }
}
