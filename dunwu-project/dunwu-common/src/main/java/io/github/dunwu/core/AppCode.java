package io.github.dunwu.core;

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
public enum AppCode implements ErrorCode {

	// @formatter:off
	SUCCESS(ErrorCode.SUCCESS_CODE, "success", "成功"),
	FAIL("-1", "fail", "失败"),
	UNAVAILABLE_SERVICE("2", "服务暂停", "服务暂停"),
	SERVICE_TIMEOUT("3", "服务超时", "服务超时，已执行 (%s)，中断任务"),

	ERROR_SYSTEM("100", "系统内部错误", "系统内部错误"),
	ERROR_UNKNOWN("101", "系统未知错误", "系统未知错误"),
	ERROR_PARAMETER("102", "参数错误", "参数错误，期望为 (%s)，实际为 (%s)"),
	ERROR_REMOTE_SERVICE("103", "远程服务错误", "远程服务错误，调用接口 (%s) 失败"),
	ERROR_DB("104", "数据库错误", "数据库错误"),
	ERROR_IO("105", "IO 错误", "IO 错误"),
	ERROR_CONCURRENCE("106", "并发错误", "并发错误"),
	ERROR_AUTHENTICATION("107", "认证错误", "认证错误"),
	ERROR_SCHEDULER("108", "调度错误", "调度错误，调用 group=%, job=% 失败"),
	ERROR_ENCODE("109", "编码错误", "编码错误"),
	ERROR_DECODE("110", "解码错误", "解码错误"),
	ERROR_NOT_FOUND("111", "未找到", "未找到 %s"),

	LIMIT_PERMISSIONS("201", "权限限制", "没有权限执行 (%s)"),
	LIMIT_IP("202", "IP 限制，不能请求该资源", "IP = (%s) 被限制，不能请求该资源"),
	LIMIT_RATE("203", "请求频次超过上限", "(%s) 内请求超过 (%s) 次，请 (%s) 后再尝试"),
	LIMIT_SIZE("204", "大小超过上限", "大小超过上限 (%s)"),
	LIMIT_FILE_TYPE("205", "文件类型不支持", "文件类型 (%s) 不支持，支持的类型为：(%s)"),

	INVALID_REQUEST("301", "非法的请求", "非法的请求"),
	INVALID_ENCODING("302", "非法的编码", "非法的编码，有效编码格式为 (%s)"),
	INVALID_FORMAT("303", "非法的数据格式", "有效数据格式为 (%s)"),
	INVALID_KEY("304", "非法的 key", "key (%s) 无效"),
	INVALID_METHOD("305", "非法的方法名", "方法名 (%s) 不存在"),
	INVALID_PARAMETER("306", "非法的参数", "参数 (%s) 非法，请检查参数"),
	INVALID_SESSION("307", "非法的 session", "非法的 session"),
	INVALID_SIGNATURE("308", "非法的签名", "非法的签名"),
	INVALID_TIMESTAMP("309", "非法的时间戳", "非法的时间戳"),
	INVALID_TOKEN("310", "非法的令牌", "非法的令牌"),
	INVALID_VERSION("311", "非法的版本号", "非法的版本号 (%s)"),

	UNAUTHORIZED("401", "没有权限", "没有权限执行 (%s) 操作"),
	MISSING_METHOD("402", "缺失方法名参数", "缺失方法名参数"),
	MISSING_PARAMETER("403", "缺失参数", "缺失参数 (%s)"),
	MISSING_SESSION("404", "缺失 session", "缺失 session"),
	MISSING_SIGNATURE("405", "缺失签名", "缺失签名"),
	MISSING_TIMESTAMP("406", "缺失时间戳", "缺失时间戳"),
	MISSING_TOKEN("407", "缺失令牌", "缺失令牌"),
	MISSING_VERSION("408", "缺失版本号", "缺失版本号"),
	MISSING_KEY("409", "缺失 key", "缺失 key (%s)");
	// @formatter:on

	private final String code;

	private final String message;

	private final String template;

	AppCode(String code, String msg, String template) {
		this.code = code;
		this.message = msg;
		this.template = template;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getTemplate() {
		return template;
	}
}
