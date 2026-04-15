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
 * 携带集合数据 {@code Collection<T>} 的统一应答消息实体
 * <p>
 * 继承自 {@link Result}，用于在接口返回中携带集合类型的数据。 提供了多种静态工厂方法（如 {@link #ok(Collection)}, {@link #fail()}）以方便构建响应。
 *
 * @param <T> 集合中元素的类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class DataListResult<T> extends Result {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应数据集合
     */
    private Collection<T> data;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态，数据为 null
     */
    public DataListResult() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 构造成功的 {@link DataListResult}
     *
     * @param data 响应数据集合
     */
    public DataListResult(final Collection<T> data) {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 根据另一个 {@link DataListResult} 构造
     *
     * @param result 源 DataListResult 对象
     */
    public DataListResult(final DataListResult<T> result) {
        this(result.code, result.msg, result.data);
    }

    /**
     * 根据 {@link CodeMsg} 构造
     *
     * @param codeMsg 应答状态详情
     */
    public DataListResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 根据 {@link CodeMsg} 及响应信息构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     */
    public DataListResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 及数据构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    响应数据集合
     */
    public DataListResult(final CodeMsg codeMsg, final String msg, final Collection<T> data) {
        this(codeMsg.getCode(), msg, data);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public DataListResult(final String code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","));
    }

    /**
     * 根据状态码及响应信息构造
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public DataListResult(final String code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 响应数据集合
     */
    public DataListResult(final String code, final String msg, final Collection<T> data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 获取响应数据集合
     *
     * @return 响应数据集合
     */
    @Override
    public Collection<T> getData() {
        return this.data;
    }

    /**
     * 判断是否包含响应数据
     *
     * @return 如果数据集合不为 null 且非 empty 返回 true，否则返回 false
     */
    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
    }

    // ==================== Static Factory Methods ====================

    /**
     * 返回失败的 {@link DataListResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return 失败的 DataListResult 实例
     */
    public static <T> DataListResult<T> fail() {
        return new DataListResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link DataListResult}
     *
     * @param msg 响应信息
     * @param <T> 数据类型
     * @return 失败的 DataListResult 实例
     */
    public static <T> DataListResult<T> fail(String msg) {
        return new DataListResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param <T> 数据类型
     * @return 成功的 DataListResult 实例
     */
    public static <T> DataListResult<T> ok() {
        return new DataListResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param data 响应数据集合
     * @param <T>  数据类型
     * @return 成功的 DataListResult 实例
     */
    public static <T> DataListResult<T> ok(final Collection<T> data) {
        return new DataListResult<>(data);
    }

    /**
     * 返回成功的 {@link DataListResult}
     *
     * @param data 响应数据集合
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return 成功的 DataListResult 实例
     */
    public static <T> DataListResult<T> ok(final Collection<T> data, final String msg) {
        return new DataListResult<>(ResultCode.SUCCESS, msg, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataListResult}
     *
     * @param codeMsg 应答状态详情
     * @param <T>     数据类型
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final CodeMsg codeMsg) {
        return new DataListResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link DataListResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param <T>     数据类型
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final CodeMsg codeMsg, final String msg) {
        return new DataListResult<>(codeMsg, msg);
    }

    /**
     * 根据 {@link CodeMsg} 及数据返回 {@link DataListResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    响应数据集合
     * @param <T>     数据类型
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final CodeMsg codeMsg, final String msg, final Collection<T> data) {
        return new DataListResult<>(codeMsg, msg, data);
    }

    /**
     * 根据参数返回 {@link DataListResult}
     *
     * @param <T>  数据类型
     * @param code 状态码
     * @param msg  响应信息
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final String code, final String msg) {
        return new DataListResult<>(code, msg);
    }

    /**
     * 根据状态码、响应信息及数据返回 {@link DataListResult}
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 响应数据集合
     * @param <T>  数据类型
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final String code, final String msg, final Collection<T> data) {
        return new DataListResult<>(code, msg, data);
    }

    /**
     * 根据参数返回 {@link DataListResult}
     *
     * @param <T>      数据类型
     * @param code     状态码
     * @param messages 消息集合
     * @return DataListResult 实例
     */
    public static <T> DataListResult<T> build(final String code, final Collection<String> messages) {
        return new DataListResult<>(code, messages);
    }

}
