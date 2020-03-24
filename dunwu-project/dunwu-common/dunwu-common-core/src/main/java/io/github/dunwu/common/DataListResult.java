package io.github.dunwu.common;

import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.common.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

/**
 * 含有响应数据列表的应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataListResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 应答数据实体
     */
    protected Collection<T> data;

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 应答数据实体
     */
    public DataListResult(Collection<T> data) {
        super(ResultStatus.OK);
        this.data = data;
    }

    /**
     * 根据 {@link Status} 构造 {@link DataListResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public DataListResult(final Status status) {
        super(status);
    }

    /**
     * 根据另一个 {@link DataListResult} 构造 {@link DataListResult}
     *
     * @param result {@link DataListResult}
     */
    public DataListResult(final DataListResult<T> result) {
        super(result);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public DataListResult(final int code, final String message) {
        super(code, message);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，构造 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     */
    public DataListResult(final int code, final String template, Object... params) {
        super(code, template, params);
    }

    /**
     * 构造 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public DataListResult(final int code, final List<String> messages) {
        super(code, messages);
    }

    /**
     * 返回失败的 {@link DataListResult} （默认应答）
     *
     * @param <T> 数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> failDataList() {
        return failDataList(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link DataListResult}
     *
     * @param status {@link Status} 响应状态
     * @param <T>    数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> failDataList(final Status status) {
        return new DataListResult<>(status);
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param code    响应状态错误码
     * @param message 响应状态消息
     * @param <T>     数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> failDataList(final int code, final String message) {
        return new DataListResult<>(code, message);
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @param <T>      数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> failDataList(final int code, final List<String> messages) {
        return new DataListResult<>(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回失败的 {@link DataListResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     * @param <T>      数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> failDataList(final int code, final String template, final Object... params) {
        return new DataListResult<>(code, template, params);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回成功的 {@link DataListResult}
     *
     * @param data 数据对象列表
     * @param <T>  数据类型
     * @return {@link DataListResult}
     */
    public static <T> DataListResult<T> success(final Collection<T> data) {
        return new DataListResult<>(data);
    }

}
