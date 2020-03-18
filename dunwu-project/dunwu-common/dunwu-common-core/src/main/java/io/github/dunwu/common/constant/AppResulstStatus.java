package io.github.dunwu.common.constant;

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
public enum AppResulstStatus implements Status {

    // @formatter:off
	OK(0, "success" ),
	FAIL(-1, "fail" ),
	UNAVAILABLE_SERVICE(2, "服务暂停" ),
	SERVICE_TIMEOUT(3, "服务超时" ),

	ERROR_SYSTEM(100, "系统内部错误" ),
	ERROR_UNKNOWN(101, "系统未知错误" ),
	ERROR_PARAMETER(102, "参数错误" ),
	ERROR_REMOTE_SERVICE(103, "远程服务错误" ),
	ERROR_DB(104, "数据库错误" ),
	ERROR_IO(105, "IO 错误" ),
	ERROR_CONCURRENCE(106, "并发错误" ),
	ERROR_AUTHENTICATION(107, "认证错误" ),
	ERROR_SCHEDULER(108, "调度错误" ),
	ERROR_ENCODE(109, "编码错误" ),
	ERROR_DECODE(110, "解码错误" ),
	ERROR_NOT_FOUND(111, "未找到" ),

	LIMIT_PERMISSIONS(201, "权限限制" ),
	LIMIT_IP(202, "IP 限制，不能请求该资源" ),
	LIMIT_RATE(203, "请求频次超过上限" ),
	LIMIT_SIZE(204, "大小超过上限" ),
	LIMIT_FILE_TYPE(205, "文件类型不支持" ),

	INVALID_REQUEST(301, "非法的请求" ),
	INVALID_ENCODING(302, "非法的编码" ),
	INVALID_FORMAT(303, "非法的数据格式" ),
	INVALID_KEY(304, "非法的 key" ),
	INVALID_METHOD(305, "非法的方法名" ),
	INVALID_PARAMETER(306, "非法的参数" ),
	INVALID_SESSION(307, "非法的 session" ),
	INVALID_SIGNATURE(308, "非法的签名" ),
	INVALID_TIMESTAMP(309, "非法的时间戳" ),
	INVALID_TOKEN(310, "非法的令牌" ),
	INVALID_VERSION(311, "非法的版本号" ),

	UNAUTHORIZED(401, "没有权限" ),
	MISSING_METHOD(402, "缺失方法名参数" ),
	MISSING_PARAMETER(403, "缺失参数" ),
	MISSING_SESSION(404, "缺失 session" ),
	MISSING_SIGNATURE(405, "缺失签名" ),
	MISSING_TIMESTAMP(406, "缺失时间戳" ),
	MISSING_TOKEN(407, "缺失令牌" ),
	MISSING_VERSION(408, "缺失版本号" ),
	MISSING_KEY(409, "缺失 key" );
	// @formatter:on

    private final int code;

    private final String message;

    AppResulstStatus(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
