package io.github.dunwu.core;

/**
 * 系统应答状态码
 * @author Zhang Peng
 * @since 2019-04-11
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;

    private Result result;

    public AppException(Result result) {
        this.result = new Result(result.getSuccess(), result.getCode(), result.getMsg());
    }

    public AppException(SystemCode appCodeEnum) {
        this.result = ResultUtil.fail(appCodeEnum);
    }

    public AppException(SystemCode appCodeEnum, String[] msgs) {
        this.result = ResultUtil.fail(appCodeEnum);
        StringBuilder sb = new StringBuilder(appCodeEnum.msg());
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AppException{" + "result=" + result + '}';
    }
}
