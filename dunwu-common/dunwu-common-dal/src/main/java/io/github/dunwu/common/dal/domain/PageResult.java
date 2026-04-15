package io.github.dunwu.common.dal.domain;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.util.Collection;

/**
 * 携带分页数据 {@code PageImpl<T>} 的统一应答消息实体
 * <p>
 * 继承自 {@link Result}，专门用于在接口返回中携带分页查询的结果数据。 包含总记录数、当前页码、每页大小等分页元数据。
 *
 * @param <T> 分页列表中元素的类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class PageResult<T> extends Result {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应分页数据实体
     * <p>
     * 包含当前页的数据列表以及总记录数、总页数等分页信息
     */
    private PageImpl<T> data;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态，数据为 null
     */
    public PageResult() {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 分页数据实体
     */
    public PageResult(final PageImpl<T> data) {
        this(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 根据另一个 {@link PageResult} 构造
     *
     * @param result 源 PageResult 对象
     */
    public PageResult(final PageResult<T> result) {
        this(result.code, result.msg, result.data);
    }

    /**
     * 根据 {@link CodeMsg} 构造
     *
     * @param codeMsg 应答状态详情
     */
    public PageResult(final CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    /**
     * 根据 {@link CodeMsg} 及响应信息构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     */
    public PageResult(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg, null);
    }

    /**
     * 根据 {@link CodeMsg} 及数据构造
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    分页数据实体
     */
    public PageResult(final CodeMsg codeMsg, final String msg, final PageImpl<T> data) {
        this(codeMsg.getCode(), msg, data);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public PageResult(final String code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","));
    }

    /**
     * 根据状态码及响应信息构造
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public PageResult(final String code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 分页数据实体
     */
    public PageResult(final String code, final String msg, final PageImpl<T> data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 获取响应分页数据
     *
     * @return 响应分页数据实体
     */
    @Override
    public PageImpl<T> getData() {
        return this.data;
    }

    /**
     * 判断是否包含响应数据
     *
     * @return 如果分页数据不为 null 且当前页数据列表非空返回 true，否则返回 false
     */
    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
    }

    // ==================== Static Factory Methods ====================

    /**
     * 返回失败的 {@link PageResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return 失败的 PageResult 实例
     */
    public static <T> PageResult<T> fail() {
        return new PageResult<>(ResultCode.FAIL);
    }

    /**
     * 返回失败的 {@link PageResult}
     *
     * @param msg 响应信息
     * @param <T> 数据类型
     * @return 失败的 PageResult 实例
     */
    public static <T> PageResult<T> fail(String msg) {
        return new PageResult<>(ResultCode.FAIL, msg);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param <T> 数据类型
     * @return 成功的 PageResult 实例
     */
    public static <T> PageResult<T> ok() {
        return new PageResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param data 分页数据实体
     * @param <T>  数据类型
     * @return 成功的 PageResult 实例
     */
    public static <T> PageResult<T> ok(final Page<T> data) {
        return new PageResult<>(PageImpl.of(data));
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param data 分页数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return 成功的 PageResult 实例
     */
    public static <T> PageResult<T> ok(final Page<T> data, final String msg) {
        return new PageResult<>(ResultCode.SUCCESS, msg, PageImpl.of(data));
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link PageResult}
     *
     * @param codeMsg 应答状态详情
     * @param <T>     数据类型
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final CodeMsg codeMsg) {
        return new PageResult<>(codeMsg);
    }

    /**
     * 根据 {@link CodeMsg} 返回 {@link PageResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param <T>     数据类型
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final CodeMsg codeMsg, final String msg) {
        return new PageResult<>(codeMsg, msg);
    }

    /**
     * 根据 {@link CodeMsg} 及数据返回 {@link PageResult}
     *
     * @param codeMsg 应答状态详情
     * @param msg     响应信息
     * @param data    分页数据实体
     * @param <T>     数据类型
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final CodeMsg codeMsg, final String msg, final PageImpl<T> data) {
        return new PageResult<>(codeMsg, msg, data);
    }

    /**
     * 根据参数返回 {@link PageResult}
     *
     * @param <T>  数据类型
     * @param code 状态码
     * @param msg  响应信息
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final String code, final String msg) {
        return new PageResult<>(code, msg);
    }

    /**
     * 根据状态码、响应信息及数据返回 {@link PageResult}
     *
     * @param code 状态码
     * @param msg  响应信息
     * @param data 分页数据实体
     * @param <T>  数据类型
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final String code, final String msg, final PageImpl<T> data) {
        return new PageResult<>(code, msg, data);
    }

    /**
     * 根据参数返回 {@link PageResult}
     *
     * @param <T>      数据类型
     * @param code     状态码
     * @param messages 消息集合
     * @return PageResult 实例
     */
    public static <T> PageResult<T> build(final String code, final Collection<String> messages) {
        return new PageResult<>(code, messages);
    }

}
