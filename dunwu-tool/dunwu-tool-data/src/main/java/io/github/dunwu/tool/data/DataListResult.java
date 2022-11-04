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
 * 数据类型为 {@link Collection<T>} 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
public class DataListResult<T> extends Result implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认构造方法
     */
    public DataListResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public DataListResult(final Status status) {
        this(status.getCode(), status.getMsg());
    }

    /**
     * 根据另一个 {@link DataListResult} 构造 {@link DataListResult}
     *
     * @param result {@link DataListResult}
     */
    public DataListResult(final DataListResult<T> result) {
        this(result.getData(), result.getCode(), result.getMsg());
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public DataListResult(final int code, final String msg) {
        this(null, code, msg);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public DataListResult(final int code, final Collection<String> messages) {
        this(null, code, CollectionUtil.join(messages, ","));
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public DataListResult(final int code, final String msg, final String toast) {
        this(null, code, msg, toast);
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 数据实体
     */
    public DataListResult(final Collection<T> data) {
        this(data, ResultStatus.OK.getCode(), ResultStatus.OK.getMsg());
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     */
    public DataListResult(final Collection<T> data, final String msg) {
        this(data, ResultStatus.OK.getCode(), msg);
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 响应信息
     */
    public DataListResult(final Collection<T> data, final String msg, final String toast) {
        this(data, ResultStatus.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param data 数据实体
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public DataListResult(final Collection<T> data, final int code, final String msg) {
        this(data, code, msg, null);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param data  数据实体
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public DataListResult(final Collection<T> data, final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 返回失败的 {@link DataListResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail() {
        return new DataListResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     * @param <T>    数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final Status status) {
        return new DataListResult<>(status);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param <T>    数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final Status status, final String msg) {
        return new DataListResult<>(status.getCode(), msg);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     * @param <T>    数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final Status status, final String msg, final String toast) {
        return new DataListResult<>(status.getCode(), msg, toast);
    }

    /**
     * 根据参数返回失败的 {@link DataListResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final int code, final String msg) {
        return new DataListResult<>(code, msg);
    }

    /**
     * 根据参数返回失败的 {@link DataListResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final int code, final String msg, final String toast) {
        return new DataListResult<>(code, msg, toast);
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <T>      数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> fail(final int code, final Collection<String> messages) {
        return new DataListResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param <T> 数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok() {
        return new DataListResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param data 数据实体
     * @param <T>  数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok(final Collection<T> data) {
        return new DataListResult<>(data);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok(final Collection<T> data, final String msg) {
        return new DataListResult<>(data, msg);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok(final Collection<T> data, final String msg, final String toast) {
        return new DataListResult<>(data, msg, toast);
    }

    @Override
    @SuppressWarnings("all")
    public Collection<T> getData() {
        return (Collection<T>) data;
    }

}
