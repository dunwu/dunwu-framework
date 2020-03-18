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
public class DataResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 数据对象。当 successBaseResult = true 才有值
     */
    private T data;

    public DataResult() {
    }

    public DataResult(Status status) {
        super(status);
    }

    public DataResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public DataResult(T data, int code, String message) {
        super(code, message);
        this.data = data;
    }

    public DataResult(T data, int code, String message, Object... params) {
        super(code, message, params);
        this.data = data;
    }

    public DataResult(T data, int code, List<String> messages) {
        super(code, messages);
        this.data = data;
    }

    /**
     * 返回失败 DataResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failData() {
        return failData(AppResulstStatus.FAIL);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param status 错误码 {@link Status}
     * @param <T>          数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> failData(Status status) {
        return new DataResult<>(status);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code    错误码 {@link Status}
     * @param message 信息数组
     * @param <T>     数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failData(int code, String message) {
        return new DataResult<>(null, code, message);
    }

    /**
     * 根据参数返回失败 DataResult
     *
     * @param code     错误码 {@link Status}
     * @param messages 错误信息
     * @return BaseResult
     */
    public static <T> DataResult<T> failData(int code, List<String> messages) {
        return new DataResult<>(null, code, messages);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code     错误码 {@link Status}
     * @param template 错误信息模板
     * @param params   错误信息参数
     * @return Result
     */
    public static <T> DataResult<T> failData(int code, String template, Object... params) {
        return new DataResult<>(null, code, template, params);
    }

    /**
     * 返回成功 DataResult 的默认应答
     *
     * @param data 数据对象
     * @param <T>  数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> success(T data) {
        return new DataResult<>(data, AppResulstStatus.OK.getCode(),
            AppResulstStatus.OK.getMessage());
    }

}
