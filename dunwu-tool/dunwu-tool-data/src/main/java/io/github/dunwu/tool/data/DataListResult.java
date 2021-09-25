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
 * 数据类型为 {@link Collection<T>} 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
public class DataListResult<T> implements Status, Serializable {

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
    private Collection<T> data;

    public DataListResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public DataListResult(final Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @param data    应答数据实体
     */
    public DataListResult(final int code, final String message, final Collection<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 应答数据实体
     */
    public DataListResult(final Collection<T> data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), data);
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 应答数据实体
     */
    public DataListResult(final Collection<T> data, String message) {
        this(ResultStatus.OK.getCode(), message, data);
    }

    /**
     * 根据另一个 {@link DataListResult} 构造 {@link DataListResult}
     *
     * @param result {@link DataListResult}
     */
    public DataListResult(final DataListResult<T> result) {
        this(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public DataListResult(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public DataListResult(final int code, final Collection<String> messages) {
        this(code, JSONUtil.toJsonStr(messages), null);
    }

    /**
     * 返回失败的 {@link DataListResult}（默认应答）
     *
     * @return {@link DataListResult}
     */
    public static DataListResult<?> fail() {
        return new DataListResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link DataListResult}
     */
    public static DataListResult<?> fail(final Status status) {
        return new DataListResult<>(status);
    }

    /**
     * 根据参数返回失败的 {@link DataListResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link DataListResult}
     */
    public static DataListResult<?> fail(final int code, final String message) {
        return new DataListResult<>(code, message);
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息动态数组
     * @return {@link DataListResult}
     */
    public static DataListResult<?> fail(final int code, final String... messages) {
        return new DataListResult<>(code, CollectionUtil.newArrayList(messages));
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link DataListResult}
     */
    public static DataListResult<?> fail(final int code, final Collection<String> messages) {
        return new DataListResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @return {@link DataListResult}
     */
    public static DataListResult<?> ok() {
        return new DataListResult(ResultStatus.OK);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回成功的 {@link DataListResult}
     *
     * @param data 数据对象列表
     * @param <T>  数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok(final Collection<T> data) {
        return new DataListResult<>(data);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> ok(final Collection<T> data, final String message) {
        return new DataListResult<>(data, message);
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
