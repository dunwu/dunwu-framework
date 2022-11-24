package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Data
@Builder
@ToString
public class Result implements Status, Serializable {

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
     * 数据实体
     */
    protected Object data;

    /**
     * 默认构造方法
     */
    public Result() {
        this(ResultStatus.OK);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param data 数据实体
     */
    public Result(final Object data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMsg(), null, data);
    }

    /**
     * 根据另一个 {@link Result} 构造 {@link Result}
     *
     * @param result {@link Result}
     */
    public Result(final Result result) {
        if (result == null) {
            throw new DefaultException(ResultStatus.PARAMS_ERROR, "参数不能为 null！");
        }
        init(result.getCode(), result.getMsg(), result.getToast(), result.getData());
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     */
    public Result(final Status status) {
        this(status.getCode(), status.getMsg());
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     */
    public Result(final Status status, final String msg) {
        this(status.getCode(), msg);
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     */
    public Result(final Status status, final String msg, final String toast) {
        this(status.getCode(), msg, toast);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public Result(final int code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public Result(final int code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","), null, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public Result(final int code, final String msg, final String toast) {
        this(code, msg, toast, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param data  数据实体
     */
    public Result(final int code, final String msg, final String toast, final Object data) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 结果是成功或失败
     *
     * @return true / false
     */
    public boolean isOk() {
        return this.code == ResultStatus.OK.getCode();
    }

    protected void init(final int code, final String msg, final String toast, final Object data) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return code == result.code && Objects.equals(msg, result.msg) && Objects.equals(toast,
            result.toast) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, toast, data);
    }

}
