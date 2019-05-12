package io.github.dunwu.core;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 8329224751518896737L;

    /**
     * 处理结果，默认为处理成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述。
     */
    private String msg;

    /**
     * 数据对象。当 success = true 才有值
     */
    private T data;

    /**
     * 当前查询页的数据列表
     */
    private List<T> list;

    /**
     * 分页信息
     */
    private Page page;

    public Result() {}

    public Result(Boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public Result(Boolean success, String code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Boolean success, String code, String msg, List<T> list) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

    public Result(Boolean success, String code, String msg, List<T> list, Page page) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.list = list;
        this.page = page;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Result{" + "success=" + success + ", code=" + code + ", msg='" + msg + '\'' + ", data=" + data
            + ", list=" + list + ", page=" + page + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Result)) {
            return false;
        }
        Result<?> result = (Result<?>) o;
        return Objects.equals(success, result.success) && Objects.equals(code, result.code) && Objects
            .equals(msg, result.msg) && Objects.equals(data, result.data) && Objects.equals(list, result.list)
            && Objects.equals(page, result.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, code, msg, data, list, page);
    }
}
