package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.core.constant.Status;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * 应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Data
@ToString
@Accessors(chain = true)
public class Result implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码，0 为成功，其他均为失败
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
     * 默认构造方法
     */
    public Result() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     */
    public Result(final Status status) {
        this(status.getCode(), status.getMsg());
    }

    /**
     * 根据 {@link Status} 构造 {@link Result}
     *
     * @param status {@link Status}（应答状态）
     * @param msg    响应信息
     */
    public Result(final Status status, final String msg) {
        this(status.getCode(), msg);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param msg 响应信息
     */
    public Result(final String msg) {
        this(ResultStatus.OK.getCode(), msg);
    }

    /**
     * 构造成功的 {@link Result}
     *
     * @param msg   响应信息
     * @param toast 响应信息
     */
    public Result(final String msg, final String toast) {
        this(ResultStatus.OK.getCode(), msg, toast);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code     响应状态错误码
     * @param messages 响应信息列表
     */
    public Result(final int code, final Collection<String> messages) {
        this(code, CollectionUtil.join(messages, ","), null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code 状态码 {@link Status}
     * @param msg  响应信息
     */
    public Result(final int code, final String msg) {
        this(code, msg, null);
    }

    /**
     * 构造 {@link Result}
     *
     * @param code  状态码 {@link Status}
     * @param msg   响应信息
     * @param toast 提示信息
     */
    public Result(final int code, final String msg, final String toast) {
        this.code = code;
        this.msg = msg;
        this.toast = toast;
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
