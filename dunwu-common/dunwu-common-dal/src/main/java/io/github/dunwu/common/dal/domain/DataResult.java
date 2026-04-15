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

/**
 * 携带单个数据实体 {@code <T>} 的统一应答消息实体
 * <p>
 * 继承自 {@link Result}，用于在接口返回中携带具体的单个业务对象数据。 提供了多种静态工厂方法（如 {@link #ok(Object)}, {@link #fail()}）以方便构建响应。
 *
 * @param <T> 响应数据的类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class DataResult<T> extends Result {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应数据实体
     */
    private T data;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态，数据为 null
     */
    public DataResult() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 构造成功的 {@link DataResult}
     *
     * @param data 响应数据实体
     */
    public DataResult(final T data) {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 根据另一个 {@link DataResult} 构造
     *
     * @param result 源 DataResult 对象
     */
    public DataResult(final DataResult<T> result) {
        this(result.code, result.msg, result.data);
    }

    /**
     * 根据 {@link CodeMsg} 构造
     *
     * @param codeMsg 应答状态详情
     */
    public DataResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 根据 {@link CodeMsg} 及响应信息构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     */
    public DataResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 及数据构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    响应数据实体
     */
    public DataResult(final CodeMsg codeMsg, final String msg, final T data) {
        this(codeMsg.getCode(), msg, data);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public DataResult(final String code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","));
    }

    /**
     * 根据状态码及响应信息构造
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public DataResult(final String code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 响应数据实体
     */
    public DataResult(final String code, final String msg, final T data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 获取响应数据
     *
     * @return 响应数据实体
     */
    @Override
    public T getData() {
        return this.data;
    }

    /**
     * 判断是否包含响应数据
     *
     * @return 如果数据不为 null 返回 true，否则返回 false
     */
    public boolean hasData() {
        return this.data != null;
    }

    // ==================== Static Factory Methods ====================

    /**
     * 返回失败的 {@link DataResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return 失败的 DataResult 实例
     */
    public static <T> DataResult<T> fail() {
        return new DataResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link DataResult}
     *
     * @param msg 响应信息
     * @param <T> 数据类型
     * @return 失败的 DataResult 实例
     */
    public static <T> DataResult<T> fail(String msg) {
        return new DataResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param <T> 数据类型
     * @return 成功的 DataResult 实例
     */
    public static <T> DataResult<T> ok() {
        return new DataResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data 响应数据实体
     * @param <T>  数据类型
     * @return 成功的 DataResult 实例
     */
    public static <T> DataResult<T> ok(final T data) {
        return new DataResult<>(data);
    }

    /**
     * 返回成功的 {@link DataResult}
     *
     * @param data 响应数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return 成功的 DataResult 实例
     */
    public static <T> DataResult<T> ok(final T data, final String msg) {
        return new DataResult<>(ResultCode.SUCCESS, msg, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataResult}
     *
     * @param codeMsg 应答状态详情
     * @param <T>     数据类型
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg) {
        return new DataResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param <T>     数据类型
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg, final String msg) {
        return new DataResult<>(codeMsg, msg);
    }

    /**
     * 根据 {@link CodeMsg} 及数据返回 {@link DataResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    响应数据实体
     * @param <T>     数据类型
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final CodeMsg codeMsg, final String msg, final T data) {
        return new DataResult<>(codeMsg, msg, data);
    }

    /**
     * 根据参数返回 {@link DataResult}
     *
     * @param <T>  数据类型
     * @param code 状态码
     * @param msg  响应信息
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final String code, final String msg) {
        return new DataResult<>(code, msg);
    }

    /**
     * 根据状态码、响应信息及数据返回 {@link DataResult}
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 响应数据实体
     * @param <T>  数据类型
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final String code, final String msg, final T data) {
        return new DataResult<>(code, msg, data);
    }

    /**
     * 根据参数返回 {@link DataResult}
     *
     * @param <T>      数据类型
     * @param code     状态码
     * @param messages 消息集合
     * @return DataResult 实例
     */
    public static <T> DataResult<T> build(final String code, final Collection<String> messages) {
        return new DataResult<>(code, messages);
    }

}
