package io.github.dunwu.common.dal.constant;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.CodeMsgException;

import java.io.Serial;

/**
 * 数据异常
 * <p>
 * 用于在数据访问层（DAL）发生业务逻辑错误或数据校验失败时抛出。
 * 继承自 {@link CodeMsgException}，支持携带状态码、错误信息。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class DataException extends CodeMsgException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认构造方法
     * <p>
     * 使用默认的错误码 {@link ResultCode#DATA_ERROR}
     */
    public DataException() {
        this(ResultCode.DATA_ERROR);
    }

    /**
     * 根据 {@link CodeMsg} 构造异常
     *
     * @param codeMsg 状态码及消息接口
     */
    public DataException(CodeMsg codeMsg) {
        super(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 根据 {@link CodeMsg} 和自定义错误信息构造异常
     *
     * @param codeMsg 状态码及消息接口
     * @param msg     自定义错误信息
     */
    public DataException(CodeMsg codeMsg, String msg) {
        super(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据自定义错误信息构造异常
     * <p>
     * 使用默认的错误码 {@link ResultCode#DATA_ERROR}
     *
     * @param msg 自定义错误信息
     */
    public DataException(String msg) {
        super(ResultCode.DATA_ERROR.getCode(), msg, null);
    }

    /**
     * 根据状态码和错误信息构造异常
     *
     * @param code 状态码
     * @param msg  错误信息
     */
    public DataException(String code, String msg) {
        super(code, msg, null);
    }

    /**
     * 根据异常原因构造异常
     * <p>
     * 使用默认的错误码 {@link ResultCode#DATA_ERROR}
     *
     * @param cause 异常原因
     */
    public DataException(Throwable cause) {
        super(ResultCode.DATA_ERROR.getCode(), ResultCode.DATA_ERROR.getMsg(), cause);
    }

    /**
     * 根据异常原因和自定义错误信息构造异常
     * <p>
     * 使用默认的错误码 {@link ResultCode#DATA_ERROR}
     *
     * @param cause 异常原因
     * @param msg   自定义错误信息
     */
    public DataException(Throwable cause, String msg) {
        super(ResultCode.DATA_ERROR.getCode(), msg, cause);
    }

    /**
     * 根据异常原因和 {@link CodeMsg} 构造异常
     *
     * @param cause   异常原因
     * @param codeMsg 状态码及消息接口
     */
    public DataException(Throwable cause, CodeMsg codeMsg) {
        super(codeMsg.getCode(), codeMsg.getMsg(), cause);
    }

    /**
     * 根据异常原因、{@link CodeMsg} 和自定义错误信息构造异常
     *
     * @param cause   异常原因
     * @param codeMsg 状态码及消息接口
     * @param msg     自定义错误信息
     */
    public DataException(Throwable cause, CodeMsg codeMsg, String msg) {
        super(codeMsg.getCode(), msg, cause);
    }

    /**
     * 根据异常原因、状态码和错误信息构造异常
     *
     * @param cause 异常原因
     * @param code  状态码
     * @param msg   错误信息
     */
    public DataException(Throwable cause, String code, String msg) {
        super(code, msg, cause);
    }

}
