package io.github.dunwu.tool.bean;

import io.github.dunwu.tool.exceptions.ExceptionUtil;
import io.github.dunwu.tool.util.StringUtil;

/**
 * Bean异常
 *
 * @author xiaoleilu
 */
public class BeanException extends RuntimeException {

    private static final long serialVersionUID = -8096998667745023423L;

    public BeanException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public BeanException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BeanException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
