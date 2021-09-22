package io.github.dunwu.tool.data.core;

import io.github.dunwu.tool.data.core.constant.ResultStatus;
import io.github.dunwu.tool.data.core.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * 含有响应数据的应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DataResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 应答数据实体
     */
    private T data;

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 应答数据实体
     */
    public DataResult(T data) {
        super(ResultStatus.OK);
        this.data = data;
    }

    /**
     * 根据 {@link Status} 构造 {@link DataResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public DataResult(final Status status) {
        super(status);
    }

    /**
     * 根据另一个 {@link DataResult} 构造 {@link DataResult}
     *
     * @param result {@link DataResult}
     */
    public DataResult(final DataResult<T> result) {
        super(result);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public DataResult(final int code, final String message) {
        super(code, message);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，构造 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     */
    public DataResult(final int code, final String template, Object... params) {
        super(code, template, params);
    }

    /**
     * 构造 {@link DataResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public DataResult(final int code, final Collection<String> messages) {
        super(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回成功的 {@link DataResult}
     *
     * @param data 数据对象
     * @param <T>  数据类型
     * @return {@link DataResult}
     */
    public static <T> DataResult<T> ok(final T data) {
        return new DataResult<>(data);
    }

}
