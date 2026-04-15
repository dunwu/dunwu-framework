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
 * 携带滚动分页数据实体 {@code <T>} 的统一应答消息实体。
 * <p>
 * 继承自 {@link Result}，专门用于在接口返回中携带 {@link ScrollData} 类型的数据。 适用于 Elasticsearch 等大数据量分批查询场景的滚动结果封装。
 *
 * @param <T> 数据类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2024-04-09
 */
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class ScrollResult<T> extends Result {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应滚动分页数据实体
     */
    private ScrollData<T> data;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态，数据为 null。
     */
    public ScrollResult() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 构造成功的 {@link ScrollResult}
     *
     * @param data 滚动分页数据实体
     */
    public ScrollResult(final ScrollData<T> data) {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 根据另一个 {@link ScrollResult} 构造
     *
     * @param result 源 ScrollResult 对象
     */
    public ScrollResult(final ScrollResult<T> result) {
        this(result.code, result.msg, result.data);
    }

    /**
     * 根据 {@link CodeMsg} 构造
     *
     * @param codeMsg 应答状态详情
     */
    public ScrollResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 根据 {@link CodeMsg} 及响应信息构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     */
    public ScrollResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 及数据构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    滚动分页数据实体
     */
    public ScrollResult(final CodeMsg codeMsg, final String msg, final ScrollData<T> data) {
        this(codeMsg.getCode(), msg, data);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public ScrollResult(final String code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","));
    }

    /**
     * 根据状态码及响应信息构造
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public ScrollResult(final String code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 滚动分页数据实体
     */
    public ScrollResult(final String code, final String msg, final ScrollData<T> data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 获取响应滚动分页数据
     *
     * @return 响应滚动分页数据实体
     */
    @Override
    public ScrollData<T> getData() {
        return this.data;
    }

    /**
     * 判断是否包含响应数据
     *
     * @return 如果滚动分页数据不为 null 且数据列表非空返回 true，否则返回 false
     */
    public boolean hasData() {
        return this.data != null && this.data.hasContent();
    }

    // ==================== Static Factory Methods ====================

    /**
     * 返回失败的 {@link ScrollResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return 失败的 ScrollResult 实例
     */
    public static <T> ScrollResult<T> fail() {
        return new ScrollResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link ScrollResult}
     *
     * @param msg 响应信息
     * @param <T> 数据类型
     * @return 失败的 ScrollResult 实例
     */
    public static <T> ScrollResult<T> fail(final String msg) {
        return new ScrollResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link ScrollResult}
     *
     * @param <T> 数据类型
     * @return 成功的 ScrollResult 实例
     */
    public static <T> ScrollResult<T> ok() {
        return new ScrollResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回成功的 {@link ScrollResult}
     *
     * @param data 滚动分页数据实体
     * @param <T>  数据类型
     * @return 成功的 ScrollResult 实例
     */
    public static <T> ScrollResult<T> ok(final ScrollData<T> data) {
        return new ScrollResult<>(data);
    }

    /**
     * 返回成功的 {@link ScrollResult}
     *
     * @param data 滚动分页数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return 成功的 ScrollResult 实例
     */
    public static <T> ScrollResult<T> ok(final ScrollData<T> data, final String msg) {
        return new ScrollResult<>(ResultCode.SUCCESS, msg, data);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link ScrollResult}
     *
     * @param codeMsg 应答状态详情
     * @param <T>     数据类型
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final CodeMsg codeMsg) {
        return new ScrollResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link ScrollResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param <T>     数据类型
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final CodeMsg codeMsg, final String msg) {
        return new ScrollResult<>(codeMsg, msg);
    }

    /**
     * 根据 {@link CodeMsg} 及数据返回 {@link ScrollResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    滚动分页数据实体
     * @param <T>     数据类型
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final CodeMsg codeMsg, final String msg, final ScrollData<T> data) {
        return new ScrollResult<>(codeMsg, msg, data);
    }

    /**
     * 根据参数返回 {@link ScrollResult}
     *
     * @param <T>  数据类型
     * @param code 状态码
     * @param msg  响应信息
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final String code, final String msg) {
        return new ScrollResult<>(code, msg);
    }

    /**
     * 根据状态码、响应信息及数据返回 {@link ScrollResult}
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 滚动分页数据实体
     * @param <T>  数据类型
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final String code, final String msg, final ScrollData<T> data) {
        return new ScrollResult<>(code, msg, data);
    }

    /**
     * 根据参数返回 {@link ScrollResult}
     *
     * @param <T>      数据类型
     * @param code     状态码
     * @param messages 消息集合
     * @return ScrollResult 实例
     */
    public static <T> ScrollResult<T> build(final String code, final Collection<String> messages) {
        return new ScrollResult<>(code, messages);
    }

}
