package io.github.dunwu.core;

/**
 * 系统应答状态码
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private BaseResult result;

    public AppException(BaseResult result) {
        this.result = new BaseResult(result.getSuccess(), result.getCode(), result.getMsg());
    }

    public AppException(IAppCode appCode) {
        this.result = ResultUtil.failBaseResult(appCode);
    }

    public AppException(IAppCode appCode, String[] msgs) {
        this.result = ResultUtil.failBaseResult(appCode);
        StringBuilder sb = new StringBuilder(appCode.msg());
        for (String s : msgs) {
            sb.append("\r\n").append(s);
        }
        this.result.setMsg(sb.toString());
    }

    /**
     * 覆盖原方法，解决抓取堆性能开销
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String getMessage() {
        return null == result ? null : result.getMsg();
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
