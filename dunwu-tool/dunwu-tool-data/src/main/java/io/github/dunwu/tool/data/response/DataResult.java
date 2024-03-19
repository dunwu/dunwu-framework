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

/**
 * 数据类型为 <T> 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DataResult<T> extends Result implements CodeMsg, Serializable {

    private static final long serialVersionUID = 1L;

    private T data;

    /**
     * 默认构造方法
     */
    public DataResult() {
        init(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, null);
    }

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 数据实体
     */
    public DataResult(final T data) {
        init(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, data);
    }

    /**
     * 根据另一个 {@link DataResult} 构造 {@link DataResult}
     *
     * @param result {@link DataResult}
     */
    public DataResult(final DataResult<T> result) {
        if (result == null) {
            throw new DefaultException(ResultCode.PARAMS_ERROR, "参数不能为 null！");
        }
        init(result.getCode(), result.getMsg(), result.getToast(), result.getData());
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     */
    public DataResult(final CodeMsg codeMsg) {
        init(codeMsg.getCode(), codeMsg.getMsg(), null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     */
    public DataResult(final CodeMsg codeMsg, final String msg) {
        init(codeMsg.getCode(), msg, null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param toast   提示信息
     */
    public DataResult(final CodeMsg codeMsg, final String msg, final String toast) {
        init(codeMsg.getCode(), msg, toast, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     */
    public DataResult(final int code, final String msg) {
        init(code, msg, null, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public DataResult(final int code, final Collection<String> messages) {
        init(code, CollectionUtil.join(messages, ","), null, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public DataResult(final int code, final String msg, final String toast) {
        init(code, msg, toast, null);
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
        init(code, msg, toast, data);
    }

    private void init(final int code, final String msg, final String toast, final T data) {
        super.init(code, msg, toast);
        this.data = data;
    }

    /**
     * 返回失败的 {@link DataResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail() {
        return new DataResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link DataResult}（默认应答）
     *
     * @param msg 响应信息
     * @param <T> 数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> fail(String msg) {
        return new DataResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param <T> 数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok() {
        return new DataResult<>(ResultCode.OK);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data 数据实体
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data) {
        return new DataResult<>(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, data);
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
        return new DataResult<>(ResultCode.OK.getCode(), msg, null, data);
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
        return new DataResult<>(ResultCode.OK.getCode(), msg, toast, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param <T>     数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg) {
        return new DataResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param <T>     数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg, final String msg) {
        return new DataResult<>(codeMsg.getCode(), msg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param toast   提示信息
     * @param <T>     数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg, final String msg, final String toast) {
        return new DataResult<>(codeMsg.getCode(), msg, toast);
    }

    /**
     * 根据参数返回 {@link DataResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final int code, final String msg) {
        return new DataResult<>(code, msg);
    }

    /**
     * 根据参数返回 {@link DataResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final int code, final String msg, final String toast) {
        return new DataResult<>(code, msg, toast);
    }

    /**
     * 返回 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <T>      数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> build(final int code, final Collection<String> messages) {
        return new DataResult<>(code, messages);
    }

}
