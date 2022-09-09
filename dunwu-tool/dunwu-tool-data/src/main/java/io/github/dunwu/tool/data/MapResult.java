package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
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
    protected String msg;

    /**
     * 提示信息
     */
    protected String toast;

    /**
     * 数据实体
     */
    private Map<K, V> data;

    /**
     * 默认构造方法
     */
    public MapResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public MapResult(final Status status) {
        this(status.getCode(), status.getMsg());
    }

    /**
     * 根据另一个 {@link MapResult} 构造 {@link MapResult}
     *
     * @param result {@link MapResult}
     */
    public MapResult(final MapResult<K, V> result) {
        this(result.getData(), result.getCode(), result.getMsg());
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public MapResult(final int code, final String msg) {
        this(null, code, msg);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public MapResult(final int code, final Collection<String> messages) {
        this(null, code, CollectionUtil.join(messages, ","));
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public MapResult(final int code, final String msg, final String toast) {
        this(null, code, msg, toast);
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 数据实体
     */
    public MapResult(final Map<K, V> data) {
        this(data, ResultStatus.OK.getCode(), ResultStatus.OK.getMsg());
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     */
    public MapResult(final Map<K, V> data, final String msg) {
        this(data, ResultStatus.OK.getCode(), msg);
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 响应信息
     */
    public MapResult(final Map<K, V> data, final String msg, final String toast) {
        this(data, ResultStatus.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param data 数据实体
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public MapResult(final Map<K, V> data, final int code, final String msg) {
        this(data, code, msg, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param data  数据实体
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public MapResult(final Map<K, V> data, final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 返回失败的 {@link MapResult}（默认应答）
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail() {
        return new MapResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     * @param <K>    Key 数据类型
     * @param <V>    Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final Status status) {
        return new MapResult<>(status);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param <K>    Key 数据类型
     * @param <V>    Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final Status status, final String msg) {
        return new MapResult<>(status.getCode(), msg);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     * @param <K>    Key 数据类型
     * @param <V>    Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final Status status, final String msg, final String toast) {
        return new MapResult<>(status.getCode(), msg, toast);
    }

    /**
     * 根据参数返回失败的 {@link MapResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final int code, final String msg) {
        return new MapResult<>(code, msg);
    }

    /**
     * 根据参数返回失败的 {@link MapResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <K>   Key 数据类型
     * @param <V>   Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final int code, final String msg, final String toast) {
        return new MapResult<>(code, msg, toast);
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <K>      Key 数据类型
     * @param <V>      Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(final int code, final Collection<String> messages) {
        return new MapResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok() {
        return new MapResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param data 数据对象 Map
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data) {
        return new MapResult<>(data);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param data 数据对象 Map
     * @param msg  响应信息
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data, final String msg) {
        return new MapResult<>(data, msg);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <K>   Key 数据类型
     * @param <V>   Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data, final String msg, final String toast) {
        return new MapResult<>(data, msg, toast);
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
