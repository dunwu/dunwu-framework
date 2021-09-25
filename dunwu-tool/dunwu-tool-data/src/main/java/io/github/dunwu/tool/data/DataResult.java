package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.data.constant.Status;
import io.github.dunwu.tool.data.constant.enums.ResultStatus;
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
public class DataResult<T> implements Status, Serializable {

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
     * 应答数据实体
     */
    protected T data;

    public DataResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link DataResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public DataResult(final Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @param data    应答数据实体
     */
    public DataResult(final int code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 应答数据实体
     */
    public DataResult(final T data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), data);
    }

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 应答数据实体
     */
    public DataResult(final T data, final String message) {
        this(ResultStatus.OK.getCode(), message, data);
    }

    /**
     * 根据另一个 {@link DataResult} 构造 {@link DataResult}
     *
     * @param result {@link DataResult}
     */
    public DataResult(final DataResult<T> result) {
        this(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public DataResult(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public DataResult(final int code, final Collection<String> messages) {
        this(code, JSONUtil.toJsonStr(messages), null);
    }

    /**
     * 返回失败的 {@link DataResult}（默认应答）
     *
     * @return {@link DataResult}
     */
    public static DataResult<?> fail() {
        return new DataResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataResult}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link DataResult}
     */
    public static DataResult<?> fail(final Status status) {
        return new DataResult<>(status);
    }

    /**
     * 根据参数返回失败的 {@link DataResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link DataResult}
     */
    public static DataResult<?> fail(final int code, final String message) {
        return new DataResult<>(code, message);
    }

    /**
     * 返回失败的 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息动态数组
     * @return {@link DataResult}
     */
    public static DataResult<?> fail(final int code, final String... messages) {
        return new DataResult<>(code, CollectionUtil.newArrayList(messages));
    }

    /**
     * 返回失败的 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link DataResult}
     */
    public static DataResult<?> fail(final int code, final Collection<String> messages) {
        return new DataResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @return {@link DataResult}
     */
    public static DataResult<?> ok() {
        return new DataResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data) {
        return new DataResult<>(data);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data, final String message) {
        return new DataResult<>(data, message);
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
