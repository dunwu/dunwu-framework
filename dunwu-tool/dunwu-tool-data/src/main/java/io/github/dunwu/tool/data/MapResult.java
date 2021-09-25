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
import java.util.Map;

/**
 * 数据类型为 {@link Map<K, V>} 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
@Accessors(chain = true)
public class MapResult<K, V> implements Status, Serializable {

    private static final long serialVersionUID = 4812047024509186421L;

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
    private Map<K, V> data;

    public MapResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public MapResult(final Status status) {
        this(status.getCode(), status.getMessage(), null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @param data    应答数据实体
     */
    public MapResult(final int code, final String message, final Map<K, V> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 应答数据实体
     */
    public MapResult(final Map<K, V> data) {
        this(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), data);
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 应答数据实体
     */
    public MapResult(final Map<K, V> data, final String message) {
        this(ResultStatus.OK.getCode(), message, data);
    }

    /**
     * 根据另一个 {@link MapResult} 构造 {@link MapResult}
     *
     * @param result {@link MapResult}
     */
    public MapResult(final MapResult<K, V> result) {
        this(result.getCode(), result.getMessage(), result.getData());
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public MapResult(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public MapResult(final int code, final Collection<String> messages) {
        this(code, JSONUtil.toJsonStr(messages), null);
    }

    /**
     * 返回失败的 {@link MapResult}（默认应答）
     *
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> fail() {
        return new MapResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> fail(final Status status) {
        return new MapResult<>(status);
    }

    /**
     * 根据参数返回失败的 {@link MapResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> fail(final int code, final String message) {
        return new MapResult<>(code, message);
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息动态数组
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> fail(final int code, final String... messages) {
        return new MapResult<>(code, CollectionUtil.newArrayList(messages));
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> fail(final int code, final Collection<String> messages) {
        return new MapResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @return {@link MapResult}
     */
    public static MapResult<?, ?> ok() {
        return new MapResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data) {
        return new MapResult<>(data);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data, final String message) {
        return new MapResult<>(data, message);
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
