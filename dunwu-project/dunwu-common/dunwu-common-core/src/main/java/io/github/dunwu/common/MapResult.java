package io.github.dunwu.common;

import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.common.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MapResult<K, V> extends BaseResult {

    private static final long serialVersionUID = 4812047024509186421L;

    private Map<K, V> data;

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 应答数据实体
     */
    public MapResult(Map<K, V> data) {
        super(ResultStatus.OK);
        this.data = data;
    }

    /**
     * 根据 {@link Status} 构造 {@link MapResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public MapResult(final Status status) {
        super(status);
    }

    /**
     * 根据另一个 {@link MapResult} 构造 {@link MapResult}
     *
     * @param result {@link MapResult}
     */
    public MapResult(final MapResult<K, V> result) {
        super(result);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public MapResult(final int code, final String message) {
        super(code, message);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，构造 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     */
    public MapResult(final int code, final String template, Object... params) {
        super(code, template, params);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public MapResult(final int code, final List<String> messages) {
        super(code, messages);
    }

    /**
     * 返回失败的 {@link MapResult} （默认应答）
     *
     * @param <K> key 数据类型
     * @param <V> value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> failDataMap() {
        return failDataMap(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link MapResult}
     *
     * @param status {@link Status} 响应状态
     * @param <K>    key 数据类型
     * @param <V>    value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> failDataMap(final Status status) {
        return new MapResult<>(status);
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param code    响应状态错误码
     * @param message 响应状态消息
     * @param <K>     key 数据类型
     * @param <V>     value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> failDataMap(final int code, final String message) {
        return new MapResult<>(code, message);
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @param <K>      key 数据类型
     * @param <V>      value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> failDataMap(final int code, final List<String> messages) {
        return new MapResult<>(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回失败的 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     * @param <K>      key 数据类型
     * @param <V>      value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> failDataMap(final int code, final String template, final Object... params) {
        return new MapResult<>(code, template, params);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回成功的 {@link MapResult}
     *
     * @param data 数据对象
     * @param <K>  key 数据类型
     * @param <V>  value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> success(final Map<K, V> data) {
        return new MapResult<>(data);
    }

}
