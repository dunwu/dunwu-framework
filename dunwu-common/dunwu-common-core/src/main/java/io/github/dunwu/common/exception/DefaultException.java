package io.github.dunwu.common.exception;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;

import java.io.Serial;
import java.util.Objects;

/**
 * 默认业务异常
 * <p>
 * 适用于未明确分类的通用业务错误场景。默认使用 {@link ResultCode#FAIL} 作为错误码。
 * 遵循生产级异常设计规范：
 * <ul>
 *   <li>保留全量构造器重载，确保存量代码 100% 兼容。</li>
 *   <li>提供静态工厂方法 {@code of()}，推荐在新代码中使用以统一实例化入口。</li>
 *   <li>支持 {@code noStackTrace()} 轻量级异常，适用于高频校验场景。</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class DefaultException extends CodeMsgException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 默认构造方法，使用 {@link ResultCode#FAIL} 作为错误码 */
    public DefaultException() {
        super(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), null);
    }

    /** 根据自定义消息构造，使用 {@link ResultCode#FAIL} 作为错误码 */
    public DefaultException(String msg) {
        super(ResultCode.FAIL.getCode(), msg, null);
    }

    /** 根据 {@link CodeMsg} 构造 */
    public DefaultException(CodeMsg codeMsg) {
        super(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /** 根据 {@link CodeMsg} 和自定义消息构造 */
    public DefaultException(CodeMsg codeMsg, String msg) {
        super(codeMsg.getCode(), msg, null);
    }

    /** 根据错误码和消息构造 */
    public DefaultException(String code, String msg) {
        super(code, msg, null);
    }

    /** 内部构造方法，供无堆栈子类使用 */
    protected DefaultException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    /** 根据原始异常构造，使用 {@link ResultCode#FAIL} 作为错误码 */
    public DefaultException(Throwable cause) {
        super(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), cause);
    }

    /** 根据原始异常和自定义消息构造 */
    public DefaultException(Throwable cause, String msg) {
        super(ResultCode.FAIL.getCode(), msg, cause);
    }

    /** 根据原始异常和 {@link CodeMsg} 构造 */
    public DefaultException(Throwable cause, CodeMsg codeMsg) {
        super(codeMsg.getCode(), codeMsg.getMsg(), cause);
    }

    /** 根据原始异常、{@link CodeMsg} 和自定义消息构造 */
    public DefaultException(Throwable cause, CodeMsg codeMsg, String msg) {
        super(codeMsg.getCode(), msg, cause);
    }

    /** 根据原始异常、错误码和消息构造 */
    public DefaultException(Throwable cause, String code, String msg) {
        super(code, msg, cause);
    }

    // ==================== 静态工厂方法 ====================

    /** 创建指定 CodeMsg 的异常 */
    public static DefaultException of(CodeMsg codeMsg) {
        return new DefaultException(codeMsg);
    }

    /** 创建指定 CodeMsg 并覆盖消息的异常 */
    public static DefaultException of(CodeMsg codeMsg, String msg) {
        return new DefaultException(codeMsg, msg);
    }

    /** 创建带原始异常的异常实例 */
    public static DefaultException of(Throwable cause, CodeMsg codeMsg) {
        return new DefaultException(cause, codeMsg);
    }

    /** 创建带原始异常并覆盖消息的异常实例 */
    public static DefaultException of(Throwable cause, CodeMsg codeMsg, String msg) {
        return new DefaultException(cause, codeMsg, msg);
    }

    /** 创建不打印堆栈的轻量级异常（适用于高频参数校验） */
    public static DefaultException noStackTrace(CodeMsg codeMsg) {
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new NoStackTraceDefaultException(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /** 创建不打印堆栈但保留原始异常的异常实例 */
    public static DefaultException noStackTrace(Throwable cause, CodeMsg codeMsg) {
        Objects.requireNonNull(cause, "cause must not be null");
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new NoStackTraceDefaultException(cause, codeMsg.getCode(), codeMsg.getMsg());
    }

    /** 无堆栈异常内部实现类 */
    private static class NoStackTraceDefaultException extends DefaultException {
        @Serial
        private static final long serialVersionUID = 1L;

        public NoStackTraceDefaultException(String code, String msg, Throwable cause) {
            super(code, msg, cause);
        }

        public NoStackTraceDefaultException(Throwable cause, String code, String msg) {
            super(code, msg, cause);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
