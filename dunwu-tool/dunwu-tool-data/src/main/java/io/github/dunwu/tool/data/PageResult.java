package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.data.constant.Status;
import io.github.dunwu.tool.data.constant.enums.ResultStatus;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;

/**
 * 数据类型为 {@link Page<T>} 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
public class PageResult<T> implements Status, Serializable {

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
    protected Page<T> data;

    public PageResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public PageResult(final Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @param data    应答数据实体
     */
    public PageResult(final int code, final String message, final Page<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 应答数据实体
     */
    public PageResult(final Page<T> data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), data);
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 应答数据实体
     */
    public PageResult(final Page<T> data, final String message) {
        this(ResultStatus.OK.getCode(), message, data);
    }

    /**
     * 根据另一个 {@link PageResult} 构造 {@link PageResult}
     *
     * @param result {@link PageResult}
     */
    public PageResult(final PageResult<T> result) {
        this(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public PageResult(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public PageResult(final int code, final Collection<String> messages) {
        this(code, JSONUtil.toJsonStr(messages), null);
    }

    /**
     * 返回失败的 {@link PageResult}（默认应答）
     *
     * @return {@link PageResult}
     */
    public static PageResult<?> fail() {
        return new PageResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link PageResult}
     */
    public static PageResult<?> fail(final Status status) {
        return new PageResult<>(status);
    }

    /**
     * 根据参数返回失败的 {@link PageResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link PageResult}
     */
    public static PageResult<?> fail(final int code, final String message) {
        return new PageResult<>(code, message);
    }

    /**
     * 返回失败的 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息动态数组
     * @return {@link PageResult}
     */
    public static PageResult<?> fail(final int code, final String... messages) {
        return new PageResult<>(code, CollectionUtil.newArrayList(messages));
    }

    /**
     * 返回失败的 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link PageResult}
     */
    public static PageResult<?> fail(final int code, final Collection<String> messages) {
        return new PageResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @return {@link PageResult}
     */
    public static PageResult<?> ok() {
        return new PageResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(final Page<T> data) {
        return new PageResult<>(data);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(final Page<T> data, final String message) {
        return new PageResult<>(data, message);
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
