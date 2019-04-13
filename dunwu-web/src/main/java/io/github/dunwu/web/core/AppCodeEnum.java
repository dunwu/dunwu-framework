package io.github.dunwu.web.core;

/**
 * 系统应答状态码
 * @author Zhang Peng
 * @since 2019-04-11
 */
public enum AppCodeEnum {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 系统内部错误
     */
    FAIL(-1, "系统内部错误"),
    /**
     * 认证失败
     */
    AUTHEN_FAIL(1001, "认证失败"),
    /**
     * 授权不足
     */
    AUTHOR_FAIL(1002, "权限不足"),
    /**
     * 参数校验失败,错误信息交由业务逻辑处理
     */
    PARAM_MISSING(1101, "请求参数缺失"),
    /**
     * 请求参数校验错误
     */
    PARAM_CHECK_FAIL(1102, "请求参数校验错误"),
    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_FAIL(1103, "请求参数类型错误"),
    /**
     * 请求资源不存在
     */
    RESOURCE_NOT_EXIST(1104, "请求资源不存在"),
    /**
     * IO 操作失败
     */
    IO_FAIL(2001, "IO 操作失败"),
    /**
     * DB 操作失败
     */
    DB_FAIL(2002, "DB 操作失败"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(9999, "未知错误");

    private final int code;

    private final String msg;

    AppCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
