package io.github.dunwu.common;

import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.common.constant.Status;
import io.github.dunwu.tool.util.StringUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
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
    protected int code;

    /**
     * 响应信息
     */
    protected String message;

    public BaseResult() {}

    public BaseResult(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseResult(BaseResult result) {
        this.code = result.getCode();
        this.message = result.message();
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(int code, String message, Object... params) {
        this.code = code;
        this.message = String.format(message, params);
    }

    public BaseResult(int code, List<String> messages) {
        this.code = code;
        this.message = StringUtil.join("\n", messages.toArray());
    }

    /**
     * 返回失败 Result 的默认应答
     *
     * @return 成功的 Result
     */
    public static BaseResult fail() {
        return new BaseResult(AppResulstStatus.FAIL);
    }

    /**
     * 根据枚举返回应答
     *
     * @param status ErrorCode（系统应答状态码）
     * @return Result
     */
    public static BaseResult fail(final Status status) {
        return new BaseResult(status);
    }

    /**
     * 返回 BaseResult
     *
     * @param code     状态码 {@link Status}
     * @param messages 状态信息 List<String>
     * @return BaseResult
     */
    public static BaseResult fail(int code, List<String> messages) {
        return new BaseResult(code, messages);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code    状态码 {@link Status}
     * @param message 状态信息
     * @return Result
     */
    public static BaseResult fail(int code, String message) {
        return new BaseResult(code, message);
    }

    /**
     * 返回成功 Result 的默认应答
     *
     * @return Result
     */
    public static BaseResult success() {
        return new BaseResult(AppResulstStatus.OK);
    }

    public boolean isNotOk() {
        return !isOk();
    }

    public boolean isOk() {
        return this.code == AppResulstStatus.OK.getCode();
    }

}
