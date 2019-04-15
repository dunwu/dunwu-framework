package io.github.dunwu.core;

/**
 * 系统应答状态码
 * @author Zhang Peng
 * @since 2019-04-11
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -7027578114976830416L;
    
    private BaseResult result;

    public AppException(BaseResult result) {
        try {
            this.result = (BaseResult)result.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public AppException(AppCodeEnum appCodeEnum) {
        this.result = ResultUtil.newFailBaseResult(appCodeEnum);
    }

    public AppException(AppCodeEnum appCodeEnum, String[] msgs) {
        this.result = ResultUtil.newFailBaseResult(appCodeEnum);
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
