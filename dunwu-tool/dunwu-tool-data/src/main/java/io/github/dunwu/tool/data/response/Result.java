package io.github.dunwu.tool.data.response;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.CodeMsg;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.DefaultException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Result implements CodeMsg, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码，0 为成功，其他均为失败
     */
    protected int code;

    /**
     * 响应信息
     */
    protected String msg;

    /**
     * 提示信息
     */
    protected String toast;

    /**
     * 默认构造方法
     */
    public Result() {
        init(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null);
    }

    /**
     * 根据另一个 {@link Result} 构造 {@link Result}
     *
     * @param result {@link Result}
     */
    public Result(final Result result) {
        if (result == null) {
            throw new DefaultException(ResultCode.PARAMS_ERROR, "参数不能为 null！");
        }
        init(result.getCode(), result.getMsg(), result.getToast());
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link Result}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     */
    public Result(final CodeMsg codeMsg) {
        init(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link Result}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     */
    public Result(final CodeMsg codeMsg, final String msg) {
        init(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link Result}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param toast   提示信息
     */
    public Result(final CodeMsg codeMsg, final String msg, final String toast) {
        init(codeMsg.getCode(), msg, toast);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param msg   响应信息
     * @param toast 响应信息
     */
    public Result(final String msg, final String toast) {
        init(ResultCode.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     */
    public Result(final int code, final String msg) {
        init(code, msg, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public Result(final int code, final Collection<String> messages) {
        init(code, CollectionUtil.join(messages, ","), toast);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public Result(final int code, final String msg, final String toast) {
        init(code, msg, toast);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Result)) {
            return false;
        }
        Result result = (Result) o;
        return code == result.code && Objects.equals(msg, result.msg) && Objects.equals(toast, result.toast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, toast);
    }

    /**
     * 结果是成功或失败
     *
     * @return true / false
     */
    public boolean isOk() {
        return this.code == ResultCode.OK.getCode();
    }

    public Object getData() {
        return null;
    }

    protected void init(final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
    }

}
