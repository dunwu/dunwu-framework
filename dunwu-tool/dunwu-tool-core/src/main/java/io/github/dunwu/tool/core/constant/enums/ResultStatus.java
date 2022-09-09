package io.github.dunwu.tool.core.constant.enums;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.Status;

import java.util.stream.Stream;

/**
 * 系统级错误码
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://httpstatuses.com/">HTTP 状态码</a>
 * @see <a href="http://wiki.open.qq.com/wiki/%E9%94%99%E8%AF%AF%E7%A0%81">腾讯开放平台错误码</a>
 * @see <a href="https://open.weibo.com/wiki/Error_code">新浪开放平台错误码</a>
 * @see <a href= "https://docs.open.alipay.com/api_1/alipay.trade.order.settle/">支付宝开放平台API</a>
 * @see <a href= "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318634&token=&lang=zh_CN">微信开放平台错误码</a>
 * @since 2019-04-11
 */
public enum ResultStatus implements Status {

    OK(0, "成功"),

    FAIL(-1, "失败"),

    // -----------------------------------------------------
    // HTTP 错误
    // -----------------------------------------------------

    HTTP_OK(200, "HTTP 请求成功"),

    HTTP_REDIRECTION(300, "HTTP 重定向"),

    HTTP_BAD_REQUEST(400, "错误的请求"),

    HTTP_UNAUTHORIZED(401, "未授权访问资源"),

    HTTP_FORBIDDEN(403, "禁止访问"),

    HTTP_NOT_FOUND(404, "要访问的资源不存在"),

    HTTP_SERVER_ERROR(500, "HTTP 服务器错误"),

    // -----------------------------------------------------
    // 系统级错误码
    // -----------------------------------------------------

    SYSTEM_ERROR(1000, "系统错误"),

    TASK_ERROR(1001, "调度任务错误"),

    PARAMS_ERROR(1002, "参数错误"),

    REQUEST_ERROR(2000, "请求错误"),

    IO_ERROR(3000, "IO 错误"),

    AUTH_ERROR(4000, "权限错误"),

    DATA_ERROR(5000, "数据库错误"),

    RESOURCE_ERROR(6000, "资源错误");

    private final int code;

    private final String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static String getNameByCode(int code) {
        return Stream.of(ResultStatus.values()).filter(item -> item.getCode() == code).findFirst()
                     .map(ResultStatus::getMsg).orElse("");
    }

    public static ResultStatus getEnumByCode(int code) {
        return Stream.of(ResultStatus.values()).filter(item -> item.getCode() == code).findFirst().orElse(null);
    }

    public static String getTypeInfo() {
        StringBuilder sb = new StringBuilder();
        ResultStatus[] types = ResultStatus.values();
        for (ResultStatus type : types) {
            sb.append(StrUtil.format("{}:{}, ", type.getCode(), type.getMsg()));
        }
        return sb.toString();
    }
}
