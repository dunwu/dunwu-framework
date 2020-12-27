package io.github.dunwu.data.core;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.data.core.constant.Status;

import java.io.Serializable;
import java.util.List;

/**
 * 应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
public class Result implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    protected int code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 数据
     */
    private Object data;

    /**
     * 结果是成功或失败
     *
     * @return true / false
     */
    public boolean isOk() {
        return this.code == ResultStatus.OK.getCode();
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     */
    public Result(final Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    /**
     * 根据另一个 {@link Result} 构造 {@link Result}
     *
     * @param result {@link Result}
     */
    public Result(final Result result) {
        this(result.getCode(), result.getMessage(), null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public Result(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public Result(final int code, final List<String> messages) {
        this(code, CollectionUtil.join(messages, System.lineSeparator()), null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @param data    响应数据
     */
    public Result(final int code, final String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @return {@link Result}
     */
    public static Result ok() {
        return ok(null);
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @return {@link Result}
     */
    public static Result ok(final Object data) {
        return new Result(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), data);
    }

    /**
     * 返回失败的 {@link Result}（默认应答）
     *
     * @return {@link Result}
     */
    public static Result fail() {
        return fail(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link Result}
     */
    public static Result fail(final Status status) {
        return new Result(status.getCode(), status.getMessage());
    }

    /**
     * 根据参数返回失败的 {@link Result}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link Result}
     */
    public static Result fail(final int code, final String message) {
        return new Result(code, message);
    }

    /**
     * 返回失败的 {@link Result}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link Result}
     */
    public static Result fail(final int code, final List<String> messages) {
        return new Result(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回失败的 {@link Result}
     *
     * @param code    响应状态错误码
     * @param message 响应状态消息
     * @return {@link Result}
     */
    public static Result getInstance(final int code, final String message, final Object data) {
        return new Result(code, message, data);
    }

    @Override
    public String toString() {
        return "Result{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", data=" + data +
            '}';
    }

    @Override
    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    @SuppressWarnings("all")
    public <T> T getData(Class<T> clazz) {
        return (T) data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

}
