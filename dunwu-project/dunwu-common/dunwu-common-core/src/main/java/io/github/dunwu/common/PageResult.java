package io.github.dunwu.common;

import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.common.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    private Pagination<T> data;

    public PageResult() {}

    public PageResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public PageResult(Status status) {
        super(status);
    }

    public PageResult(Pagination<T> data, int code, String message) {
        super(code, message);
        this.data = data;
    }

    public PageResult(Pagination<T> data, int code, List<String> messages) {
        super(code, messages);
        this.data = data;
    }

    public PageResult(Pagination<T> data, int code, String message, Object... params) {
        super(code, message, params);
        this.data = data;
    }

    /**
     * 返回失败 PageResult 的默认应答
     *
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPage() {
        return new PageResult<>(AppResulstStatus.FAIL);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param status 错误码 {@link Status}
     * @param <T>          数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPage(Status status) {
        return new PageResult<>(null, status.getCode(), status.getMessage());
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code    错误码 {@link Status}
     * @param message 信息数组
     * @param <T>     数据类型
     * @return DataResult
     */
    public static <T> PageResult<T> failPage(int code, String message) {
        return new PageResult<>(null, code, message);
    }

    /**
     * 根据参数返回失败 PageResult
     *
     * @param code     错误码 {@link Status}
     * @param messages 错误信息
     * @param <T>      数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPage(int code, List<String> messages) {
        return new PageResult<>(null, code, messages);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param code     错误码 {@link Status}
     * @param template 信息模板
     * @param params   信息参数
     * @return PageResult
     */
    public static <T> PageResult<T> failPage(int code, String template, Object... params) {
        return new PageResult<>(null, code, template, params);
    }

    /**
     * 返回成功 PageResult 的默认应答
     *
     * @param page 分页信息
     * @param <T>  数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> success(Pagination<T> page) {
        return new PageResult<>(page, AppResulstStatus.OK.getCode(),
            AppResulstStatus.OK.getMessage());
    }

}
