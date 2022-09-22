package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
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
public class PageResult<T> extends Result implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据实体
     */
    protected Page<T> data;

    /**
     * 默认构造方法
     */
    public PageResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public PageResult(final Status status) {
        this(null, status.getCode(), status.getMsg());
    }

    /**
     * 根据另一个 {@link PageResult} 构造 {@link PageResult}
     *
     * @param result {@link PageResult}
     */
    public PageResult(final PageResult<T> result) {
        this(result.getData(), result.getCode(), result.getMsg());
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public PageResult(final int code, final String msg) {
        this(null, code, msg);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public PageResult(final int code, final Collection<String> messages) {
        this(null, code, CollectionUtil.join(messages, ","));
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public PageResult(final int code, final String msg, final String toast) {
        this(null, code, msg, toast);
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 数据实体
     */
    public PageResult(final Page<T> data) {
        this(data, ResultStatus.OK.getCode(), ResultStatus.OK.getMsg());
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     */
    public PageResult(final Page<T> data, final String msg) {
        this(data, ResultStatus.OK.getCode(), msg);
    }

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 响应信息
     */
    public PageResult(final Page<T> data, final String msg, final String toast) {
        this(data, ResultStatus.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param data 数据实体
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public PageResult(final Page<T> data, final int code, final String msg) {
        this(data, code, msg, null);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param data  数据实体
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public PageResult(final Page<T> data, final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
        this.data = data;
    }

    /**
     * 返回失败的 {@link PageResult}（默认应答）
     *
     * @param <T> 数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail() {
        return new PageResult<>(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     * @param <T>    数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final Status status) {
        return new PageResult<>(status);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param <T>    数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final Status status, final String msg) {
        return new PageResult<>(status.getCode(), msg);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     * @param toast  提示信息
     * @param <T>    数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final Status status, final String msg, final String toast) {
        return new PageResult<>(status.getCode(), msg, toast);
    }

    /**
     * 根据参数返回失败的 {@link PageResult}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final int code, final String msg) {
        return new PageResult<>(code, msg);
    }

    /**
     * 根据参数返回失败的 {@link PageResult}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final int code, final String msg, final String toast) {
        return new PageResult<>(code, msg, toast);
    }

    /**
     * 返回失败的 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     * @param <T>      数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> fail(final int code, final Collection<String> messages) {
        return new PageResult<>(code, messages);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param <T> 数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok() {
        return new PageResult<>(ResultStatus.OK);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param data 数据实体
     * @param <T>  数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(final Page<T> data) {
        return new PageResult<>(data);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param data 数据实体
     * @param msg  响应信息
     * @param <T>  数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(final Page<T> data, final String msg) {
        return new PageResult<>(data, msg);
    }

    /**
     * 返回成功的 {@link PageResult}
     *
     * @param data  数据实体
     * @param msg   响应信息
     * @param toast 提示信息
     * @param <T>   数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(final Page<T> data, final String msg, final String toast) {
        return new PageResult<>(data, msg, toast);
    }

}
