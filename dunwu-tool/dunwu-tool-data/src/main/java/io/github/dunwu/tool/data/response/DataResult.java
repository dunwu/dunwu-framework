package io.github.dunwu.tool.data.response;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.CodeMsg;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * 数据类型为 <T> 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
public class DataResult<T> extends Result implements CodeMsg, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认构造方法
     */
    public DataResult() {
        this(ResultStatus.OK);
    }

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 数据实体
     */
    public DataResult(final T data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMsg(), null, data);
    }

    /**
     * 根据另一个 {@link DataResult} 构造 {@link DataResult}
     *
     * @param result {@link DataResult}
     */
    public DataResult(final DataResult<T> result) {
        if (result == null) {
            throw new DefaultException(ResultStatus.PARAMS_ERROR, "参数不能为 null！");
        }
        init(result.getCode(), result.getMsg(), result.getToast(), result.getData());
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     */
    public DataResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg(), null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg    响应信息
     */
    public DataResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     */
    public DataResult(final CodeMsg codeMsg, final String msg, final String toast) {
        this(codeMsg.getCode(), msg, toast, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     */
    public DataResult(final int code, final String msg) {
        this(code, msg, null, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public DataResult(final int code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","), null, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public DataResult(final int code, final String msg, final String toast) {
        this(code, msg, toast, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param data  数据实体
     */
    public DataResult(final int code, final String msg, final String toast, final T data) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 返回失败的 {@link DataResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail() {
        return new DataResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link CodeMsg} 返回失败的 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param <T>    数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final CodeMsg codeMsg) {
        return new DataResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回失败的 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg    响应信息
     * @param <T>    数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final CodeMsg codeMsg, final String msg) {
        return new DataResult<>(codeMsg.getCode(), msg);
    }

    /**
     * 根据 {@link CodeMsg} 返回失败的 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     * @param <T>    数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final CodeMsg codeMsg, final String msg, final String toast) {
        return new DataResult<>(codeMsg.getCode(), msg, toast);
    }

    /**
     * 根据参数返回失败的 {@link DataResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final int code, final String msg) {
        return new DataResult<>(code, msg);
    }

    /**
     * 根据参数返回失败的 {@link DataResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final int code, final String msg, final String toast) {
        return new DataResult<>(code, msg, toast);
    }

    /**
     * 返回失败的 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <T>      数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(final int code, final Collection<String> messages) {
        return new DataResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param <T> 数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok() {
        return new DataResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data 数据实体
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data) {
        return new DataResult<>(ResultStatus.OK.getCode(), ResultStatus.OK.getMsg(), null, data);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data, final String msg) {
        return new DataResult<>(ResultStatus.OK.getCode(), msg, null, data);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data, final String msg, final String toast) {
        return new DataResult<>(ResultStatus.OK.getCode(), msg, toast, data);
    }

    @Override
    @SuppressWarnings("all")
    public T getData() {
        return (T) data;
    }

}
