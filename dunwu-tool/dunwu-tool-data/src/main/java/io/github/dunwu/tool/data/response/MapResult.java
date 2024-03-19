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
import java.util.Map;

/**
 * 数据类型为 {@link java.util.Map<K, V>} 的响应实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MapResult<K, V> extends Result implements CodeMsg, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<K, V> data;

    /**
     * 默认构造方法
     */
    public MapResult() {
        init(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, null);
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 数据实体
     */
    public MapResult(final Map<K, V> data) {
        init(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, data);
    }

    /**
     * 根据另一个 {@link MapResult} 构造 {@link MapResult}
     *
     * @param result {@link MapResult}
     */
    public MapResult(final MapResult<K, V> result) {
        if (result == null) {
            throw new DefaultException(ResultCode.PARAMS_ERROR, "参数不能为 null！");
        }
        init(result.getCode(), result.getMsg(), result.getToast(), result.getData());
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     */
    public MapResult(final CodeMsg codeMsg) {
        init(codeMsg.getCode(), codeMsg.getMsg(), null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     */
    public MapResult(final CodeMsg codeMsg, final String msg) {
        init(codeMsg.getCode(), msg, null, null);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param toast   提示信息
     */
    public MapResult(final CodeMsg codeMsg, final String msg, final String toast) {
        init(codeMsg.getCode(), msg, toast, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     */
    public MapResult(final int code, final String msg) {
        init(code, msg, null, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public MapResult(final int code, final Collection<String> messages) {
        init(code, CollectionUtil.join(messages, ","), null, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public MapResult(final int code, final String msg, final String toast) {
        init(code, msg, toast, null);
    }

    /**
     * 构造 {@link MapResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param data  数据实体
     */
    public MapResult(final int code, final String msg, final String toast, final Map<K, V> data) {
        init(code, msg, toast, data);
    }

    private void init(final int code, final String msg, final String toast, final Map<K, V> data) {
        super.init(code, msg, toast);
        this.data = data;
    }

    /**
     * 返回 {@link MapResult}（默认应答）
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail() {
        return new MapResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link MapResult}（默认应答）
     *
     * @param msg 响应信息
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> fail(String msg) {
        return new MapResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> ok() {
        return new MapResult<>(ResultCode.OK);
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
        return new MapResult<>(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), null, data);
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
        return new MapResult<>(ResultCode.OK.getCode(), msg, null, data);
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
        return new MapResult<>(ResultCode.OK.getCode(), msg, toast, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg) {
        return new MapResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg, final String msg) {
        return new MapResult<>(codeMsg.getCode(), msg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link MapResult}
     *
     * @param codeMsg {@link CodeMsg}（应答状态）
     * @param msg     响应信息
     * @param toast   提示信息
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg, final String msg, final String toast) {
        return new MapResult<>(codeMsg.getCode(), msg, toast);
    }

    /**
     * 根据参数返回 {@link MapResult}
     *
     * @param code 状态码 {@link CodeMsg}
     * @param msg  响应信息
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final int code, final String msg) {
        return new MapResult<>(code, msg);
    }

    /**
     * 根据参数返回 {@link MapResult}
     *
     * @param code  状态码 {@link CodeMsg}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <K>   Key 数据类型
     * @param <V>   Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final int code, final String msg, final String toast) {
        return new MapResult<>(code, msg, toast);
    }

    /**
     * 返回 {@link MapResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <K>      Key 数据类型
     * @param <V>      Value 数据类型
     * @return {@link MapResult}
     */
    public static <K, V> MapResult<K, V> build(final int code, final Collection<String> messages) {
        return new MapResult<>(code, messages);
    }

}
