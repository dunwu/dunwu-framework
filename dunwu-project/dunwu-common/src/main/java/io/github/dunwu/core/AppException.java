package io.github.dunwu.core;

/**
 * 系统应答状态码
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = -7027578114976830416L;

	private BaseResult result;

	public AppException(BaseResult result) {
		this.result = new BaseResult(result.getSuccess(), result.getCode(), result.getMessage());
	}

	public AppException(ErrorCode appCode) {
		this.result = ResultUtil.failBaseResult(appCode);
	}

	/**
	 * 覆盖原方法，解决抓取堆性能开销
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "AppException{" + "result=" + result + '}';
	}

}
