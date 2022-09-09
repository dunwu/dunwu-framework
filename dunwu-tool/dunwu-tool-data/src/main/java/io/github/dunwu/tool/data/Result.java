package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * 应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Data
@ToString
@Accessors(chain = true)
public class Result implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
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
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     */
    public Result(final Status status) {
        this(status.getCode(), status.getMsg());
    }

    /**
     * 根据另一个 {@link Result} 构造 {@link Result}
     *
     * @param result {@link Result}
     */
    public Result(final Result result) {
        this(result.getData(), result.getCode(), result.getMsg());
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     */
    public Result(final int code, final String msg) {
        this(null, code, msg);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 响应状态错误码
     * @param messages 响应信息列表
     */
    public Result(final int code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","), null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     * @param toast 提示信息
     */
    public Result(final int code, final String msg, final String toast) {
        this(null, code, msg, toast);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param data 数据实体
     */
    public Result(final Object data) {
        this(data, ResultStatus.OK.getCode(), ResultStatus.OK.getMsg());
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param data 数据实体
     * @param msg 响应信息
     */
    public Result(final Object data, final String msg) {
        this(data, ResultStatus.OK.getCode(), msg);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param data 数据实体
     * @param msg 响应信息
     * @param toast 响应信息
     */
    public Result(final Object data, final String msg, final String toast) {
        this(data, ResultStatus.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link Result}
     *
     * @param data 数据实体
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     */
    public Result(final Object data, final int code, final String msg) {
        this(data, code, msg, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param data 数据实体
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     * @param toast 提示信息
     */
    public Result(final Object data, final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 返回失败的 {@link Result}（默认应答）
     *
     * @return {@link Result}
     */
    public static Result fail() {
        return new Result(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link Result}
     */
    public static Result fail(final Status status) {
        return new Result(status);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @param msg 响应信息
     * @return {@link Result}
     */
    public static Result fail(final Status status, final String msg) {
        return new Result(status.getCode(), msg);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @param msg 响应信息
     * @param toast 提示信息
     * @return {@link Result}
     */
    public static Result fail(final Status status, final String msg, final String toast) {
        return new Result(status.getCode(), msg, toast);
    }

    /**
     * 根据参数返回失败的 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     * @return {@link Result}
     */
    public static Result fail(final int code, final String msg) {
        return new Result(code, msg);
    }

    /**
     * 根据参数返回失败的 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg 响应信息
     * @param toast 提示信息
     * @return {@link Result}
     */
    public static Result fail(final int code, final String msg, final String toast) {
        return new Result(code, msg, toast);
    }

    /**
     * 返回失败的 {@link Result}
     *
     * @param code 响应状态错误码
     * @param messages 响应信息列表
     * @return {@link Result}
     */
    public static Result fail(final int code, final Collection<String> messages) {
        return new Result(code, messages);
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @return {@link Result}
     */
    public static Result ok() {
        return new Result(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @param data 数据对象
     * @return {@link Result}
     */
    public static Result ok(final Object data) {
        return new Result(data);
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @param data 数据对象
     * @param msg 响应信息
     * @return {@link Result}
     */
    public static Result ok(final Object data, final String msg) {
        return new Result(data, msg);
    }

    /**
     * 返回成功的 {@link Result}
     *
     * @param data 数据对象
     * @param msg 响应信息
     * @param toast 提示信息
     * @return {@link Result}
     */
    public static Result ok(final Object data, final String msg, final String toast) {
        return new Result(data, msg, toast);
    }

    /**
     * 结果是成功或失败
     *
     * @return true / false
     */
    public boolean isOk() {
        return this.code == ResultStatus.OK.getCode();
    }

}
