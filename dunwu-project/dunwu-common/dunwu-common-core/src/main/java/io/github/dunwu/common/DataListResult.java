package io.github.dunwu.common;

import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.common.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataListResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 当前查询页的数据列表
     */
    protected Collection<T> data;

    public DataListResult() {
    }

    public DataListResult(Status status) {
        super(status);
    }

    public DataListResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public DataListResult(Collection<T> data, int code, String message) {
        super(code, message);
        this.data = data;
    }

    public DataListResult(Collection<T> data, int code, String message, Object... params) {
        super(code, message, params);
        this.data = data;
    }

    public DataListResult(Collection<T> data, int code, List<String> messages) {
        super(code, messages);
        this.data = data;
    }

    /**
     * 返回失败 DataListResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataList() {
        return failDataList(AppResulstStatus.FAIL);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param status ErrorCode（系统应答状态码）
     * @param <T>          数据类型
     * @return BaseResult
     */
    public static <T> DataListResult<T> failDataList(Status status) {
        return new DataListResult<>(status);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code    错误码 {@link Status}
     * @param message 信息数组
     * @param <T>     数据类型
     * @return DataResult
     */
    public static <T> DataListResult<T> failDataList(int code, String message) {
        return new DataListResult<>(null, code, message);
    }

    /**
     * 根据参数返回失败 DataListResult
     *
     * @param code     错误码 {@link Status}
     * @param messages 错误信息
     * @param <T>      数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataList(int code, List<String> messages) {
        return new DataListResult<>(null, code, messages);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param code     错误码 {@link Status}
     * @param template 信息模板
     * @param params   信息参数
     * @param <T>      数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataList(int code, String template, Object... params) {
        return new DataListResult<>(null, code, template, params);
    }

    /**
     * 返回成功 Result 的默认应答
     *
     * @param list 数据对象列表
     * @param <T>  数据类型
     * @return Result
     */
    public static <T> DataListResult<T> success(Collection<T> list) {
        return new DataListResult<>(list, AppResulstStatus.OK.getCode(),
            AppResulstStatus.OK.getMessage());
    }

}
