package io.github.dunwu.common.constant;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 系统级错误码
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://httpstatuses.com/">HTTP 状态码</a>
 * @see <a href="http://wiki.open.qq.com/wiki/%E9%94%99%E8%AF%AF%E7%A0%81">腾讯开放平台错误码</a>
 * @see <a href="https://open.weibo.com/wiki/Error_code">新浪开放平台错误码</a>
 * @see <a href= "https://docs.open.alipay.com/api_1/alipay.trade.order.settle/">支付宝开放平台API</a>
 * @see <a href=
 *     "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318634&token=&lang=zh_CN">微信开放平台错误码</a>
 * @since 2019-04-11
 */
@Getter
public enum ResultCode implements CodeMsg {

    SUCCESS("SUCCESS", "成功"),

    // -----------------------------------------------------
    // 系统内部错误
    // -----------------------------------------------------

    FAIL("FAIL", "业务执行失败"),

    ERROR("ERROR", "服务器内部异常"),

    PARAM_ERROR("PARAM_ERROR", "参数错误"),

    NOT_SUPPORT("NOT_SUPPORT", "不支持"),

    REQUEST_ERROR("REQUEST_ERROR", "请求错误"),

    REQUEST_REFUSE("REQUEST_REFUSE", "请求拒绝"),

    REQUEST_DUPLICATION("REQUEST_DUPLICATION", "重复请求"),

    REQUEST_BUSY("REQUEST_BUSY", "服务器繁忙"),

    IO_ERROR("IO_ERROR", "IO 错误"),

    // -----------------------------------------------------
    // 数据错误
    // -----------------------------------------------------

    DATA_ERROR("DATA_ERROR", "数据错误"),

    // =====================================================
    // 三方错误码
    // =====================================================

    THIRD_PART_ERROR("THIRD_PART_ERROR", "三方系统错误"),

    // =====================================================
    // 安全、认证、鉴权相关错误码
    // =====================================================

    AUTH_ERROR("AUTH_ERROR", "认证错误");

    /** 状态码 */
    private final String code;
    /** 响应信息 */
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据错误码获取错误信息
     *
     * @param code 状态码
     * @return 错误信息，若未找到则返回空字符串
     */
    public static String getNameByCode(String code) {
        ResultCode resultCode = getEnumByCode(code);
        return resultCode != null ? resultCode.name() : null;
    }

    /**
     * 根据错误码获取枚举实例
     *
     * @param code 子状态码
     * @return 枚举实例，若未找到则返回 null
     */
    public static ResultCode getEnumByCode(String code) {
        return Stream.of(ResultCode.values()).filter(item -> item.getCode().equals(code)).findFirst().orElse(null);
    }

    /**
     * 根据枚举名称获取枚举实例
     *
     * @param name 枚举名称
     * @return 枚举实例，若未找到则返回 null
     */
    public static ResultCode getEnumByName(String name) {
        return Stream.of(ResultCode.values()).filter(item -> item.name().equals(name)).findFirst().orElse(null);
    }

    /**
     * 获取所有枚举类型的描述信息
     *
     * @return 所有枚举类型的 code:msg 拼接字符串
     */
    public static String getTypeInfo() {
        StringBuilder sb = new StringBuilder();
        ResultCode[] types = ResultCode.values();
        for (ResultCode type : types) {
            sb.append(StrUtil.format("{}:{}, ", type.getCode(), type.getMsg()));
        }
        return sb.toString();
    }
}
