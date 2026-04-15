package io.github.dunwu.common.dal.domain;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;

/**
 * 携带 Map 数据实体 {@code <K, V>} 的统一应答消息实体。
 * <p>
 * 继承自 {@link Result}，专门用于在接口返回中携带 {@link Map} 类型的数据。 提供了丰富的构造方法和静态工厂方法，便于快速构建成功或失败的响应。
 *
 * @param <K> Key 数据类型
 * @param <V> Value 数据类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class MapResult<K, V> extends Result {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应数据实体
     */
    private Map<K, V> data;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态，数据为 null。
     */
    public MapResult() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 构造成功的 {@link MapResult}
     *
     * @param data 数据实体
     */
    public MapResult(final Map<K, V> data) {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 根据另一个 {@link MapResult} 构造
     *
     * @param result 源 MapResult 对象
     */
    public MapResult(final MapResult<K, V> result) {
        this(result.code, result.msg, result.data);
    }

    /**
     * 根据 {@link CodeMsg} 构造
     *
     * @param codeMsg 应答状态详情
     */
    public MapResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 根据 {@link CodeMsg} 及响应信息构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     */
    public MapResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 及数据构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    数据实体
     */
    public MapResult(final CodeMsg codeMsg, final String msg, final Map<K, V> data) {
        this(codeMsg.getCode(), msg, data);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public MapResult(final String code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","));
    }

    /**
     * 根据状态码及响应信息构造
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public MapResult(final String code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 数据实体
     */
    public MapResult(final String code, final String msg, final Map<K, V> data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 获取响应数据
     *
     * @return 响应数据实体
     */
    @Override
    public Map<K, V> getData() {
        return this.data;
    }

    /**
     * 判断是否包含响应数据
     *
     * @return 如果数据不为 null 且非 empty 返回 true，否则返回 false
     */
    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
    }

    // ==================== Static Factory Methods ====================

    /**
     * 返回失败的 {@link MapResult}（默认应答）
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return 失败的 MapResult 实例
     */
    public static <K, V> MapResult<K, V> fail() {
        return new MapResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link MapResult}
     *
     * @param msg 响应信息
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return 失败的 MapResult 实例
     */
    public static <K, V> MapResult<K, V> fail(String msg) {
        return new MapResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param <K> Key 数据类型
     * @param <V> Value 数据类型
     * @return 成功的 MapResult 实例
     */
    public static <K, V> MapResult<K, V> ok() {
        return new MapResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param data 数据实体
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return 成功的 MapResult 实例
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data) {
        return new MapResult<>(data);
    }

    /**
     * 返回成功的 {@link MapResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return 成功的 MapResult 实例
     */
    public static <K, V> MapResult<K, V> ok(final Map<K, V> data, final String msg) {
        return new MapResult<>(ResultCode.SUCCESS, msg, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link MapResult}
     *
     * @param codeMsg 应答状态详情
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg) {
        return new MapResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link MapResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg, final String msg) {
        return new MapResult<>(codeMsg, msg);
    }

    /**
     * 根据 {@link CodeMsg} 及数据返回 {@link MapResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    数据实体
     * @param <K>     Key 数据类型
     * @param <V>     Value 数据类型
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final CodeMsg codeMsg, final String msg, final Map<K, V> data) {
        return new MapResult<>(codeMsg, msg, data);
    }

    /**
     * 根据参数返回 {@link MapResult}
     *
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @param code 状态码
     * @param msg  响应信息
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final String code, final String msg) {
        return new MapResult<>(code, msg);
    }

    /**
     * 根据状态码、响应信息及数据返回 {@link MapResult}
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 数据实体
     * @param <K>  Key 数据类型
     * @param <V>  Value 数据类型
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final String code, final String msg, final Map<K, V> data) {
        return new MapResult<>(code, msg, data);
    }

    /**
     * 根据参数返回 {@link MapResult}
     *
     * @param <K>      Key 数据类型
     * @param <V>      Value 数据类型
     * @param code     状态码
     * @param messages 消息集合
     * @return MapResult 实例
     */
    public static <K, V> MapResult<K, V> build(final String code, final Collection<String> messages) {
        return new MapResult<>(code, messages);
    }

}
