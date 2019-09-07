package io.github.dunwu.core;

import io.github.dunwu.util.text.MoreStringUtil;
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
public class BaseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 响应结果
	 */
	protected Boolean success;

	/**
	 * 错误码
	 */
	protected String code;

	/**
	 * 响应信息
	 */
	protected String message;

	public BaseResult() {
	}

	public BaseResult(ErrorCode appCode) {
		this.success = ErrorCode.SUCCESS_CODE.equals(appCode.getCode());
		this.code = appCode.getCode();
		this.message = appCode.getMessage();
	}

	public BaseResult(BaseResult result) {
		this.success = result.getSuccess();
		this.code = result.getCode();
		this.message = result.getMessage();
	}

	public BaseResult(Boolean success, String code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public BaseResult(Boolean success, String code, String message, Object... params) {
		this.success = success;
		this.code = code;
		this.message = String.format(message, params);
	}

	public BaseResult(Boolean success, String code, List<String> messages) {
		this.success = success;
		this.code = code;
		this.message = MoreStringUtil.mergeInLines(messages);
	}

}
