package io.github.dunwu.tool.data.core;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.data.core.constant.ResultStatus;
import io.github.dunwu.tool.data.core.constant.Status;
import lombok.Data;
import lombok.ToString;

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
public class BaseResult implements Status, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    public BaseResult() {
        this(ResultStatus.OK);
    }

    /**
     * 根据 {@link Status} 构造 {@link BaseResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public BaseResult(final Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    /**
     * 根据另一个 {@link BaseResult} 构造 {@link BaseResult}
     *
     * @param result {@link BaseResult}
     */
    public BaseResult(final BaseResult result) {
        this.code = result.getCode();
        this.message = result.getMessage();
    }

    /**
     * 构造 {@link BaseResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public BaseResult(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，构造 {@link BaseResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     */
    public BaseResult(final int code, final String template, final Object... params) {
        this.code = code;
        this.message = String.format(template, params);
    }

    /**
     * 构造 {@link BaseResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public BaseResult(final int code, final Collection<String> messages) {
        this.code = code;
        this.message = StrUtil.join("\n", messages.toArray());
    }

    /**
     * 结果是成功或失败
     *
     * @return true / false
     */
    public boolean isOk() {
        return this.code == ResultStatus.OK.getCode();
    }

    /**
     * 返回失败的 {@link BaseResult}（默认应答）
     *
     * @return {@link BaseResult}
     */
    public static BaseResult fail() {
        return new BaseResult(ResultStatus.FAIL);
    }

    /**
     * 根据 {@link Status} 返回失败的 {@link BaseResult}
     *
     * @param status {@link Status}（应答状态）
     * @return {@link BaseResult}
     */
    public static BaseResult fail(final Status status) {
        return new BaseResult(status);
    }

    /**
     * 根据参数返回失败的 {@link BaseResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     * @return {@link BaseResult}
     */
    public static BaseResult fail(final int code, final String message) {
        return new BaseResult(code, message);
    }

    /**
     * 返回失败的 {@link BaseResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     * @return {@link BaseResult}
     */
    public static BaseResult fail(final int code, final Collection<String> messages) {
        return new BaseResult(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回失败的 {@link BaseResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     * @return {@link BaseResult}
     */
    public static BaseResult fail(final int code, final String template, final Object... params) {
        return new BaseResult(code, template, params);
    }

    /**
     * 返回成功的 {@link BaseResult}
     *
     * @return {@link BaseResult}
     */
    public static BaseResult ok() {
        return new BaseResult(ResultStatus.OK);
    }

}
