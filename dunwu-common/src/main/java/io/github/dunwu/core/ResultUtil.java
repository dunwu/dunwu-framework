package io.github.dunwu.core;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 应答工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class ResultUtil {

    /**
     * 返回成功 Result 的默认应答
     *
     * @return Result
     */
    public static BaseResult successBaseResult() {
        return new BaseResult(true, AppCode.SUCCESS.getCode(), AppCode.SUCCESS.getMessage());
    }

    /**
     * 返回失败 Result 的默认应答
     *
     * @return 成功的 Result
     */
    public static BaseResult failBaseResult() {
        return new BaseResult(AppCode.FAIL);
    }

    /**
     * 根据枚举返回应答
     *
     * @param errorCode ErrorCode（系统应答状态码）
     * @return Result
     */
    public static BaseResult failBaseResult(ErrorCode errorCode) {
        return new BaseResult(errorCode);
    }

    /**
     * 返回 BaseResult
     *
     * @param errorCode ErrorCode（系统应答状态码）
     * @param params 补充信息数组
     * @return Result
     */
    public static BaseResult failBaseResult(ErrorCode errorCode, Object... params) {
        return new BaseResult(false, errorCode.getCode(), errorCode.getTemplate(), params);
    }

    /**
     * 返回 BaseResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param messages 错误信息 List<String>
     * @return BaseResult
     */
    public static BaseResult failBaseResult(String code, List<String> messages) {
        return new BaseResult(false, code, messages);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code 错误码 {@link ErrorCode}
     * @param message 错误信息
     * @return Result
     */
    public static BaseResult failBaseResult(String code, String message) {
        return new BaseResult(false, code, message);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code 错误码 {@link ErrorCode}
     * @param template 错误信息模板
     * @param params 错误信息参数
     * @return Result
     */
    public static BaseResult failBaseResult(String code, String template, Object... params) {
        return new BaseResult(false, code, template, params);
    }

    /**
     * 返回成功 DataResult 的默认应答
     *
     * @param data 数据对象
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> successDataResult(T data) {
        return new DataResult<>(data, true, AppCode.SUCCESS.getCode(), AppCode.SUCCESS.getMessage());
    }

    /**
     * 返回失败 DataResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failDataResult() {
        return failDataResult(AppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param errorCode 错误码 {@link ErrorCode}
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(ErrorCode errorCode) {
        return new DataResult<>(errorCode);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param message 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failDataResult(String code, String message) {
        return new DataResult<>(null, false, code, message);
    }

    /**
     * 根据参数返回失败 DataResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param messages 错误信息
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(String code, List<String> messages) {
        return new DataResult<>(null, false, code, messages);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code 错误码 {@link ErrorCode}
     * @param template 错误信息模板
     * @param params 错误信息参数
     * @return Result
     */
    public static <T> DataResult<T> failDataResult(String code, String template, Object... params) {
        return new DataResult<>(null, false, code, template, params);
    }

    /**
     * 返回成功 Result 的默认应答
     *
     * @param list 数据对象列表
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> DataListResult<T> successDataListResult(Collection<T> list) {
        return new DataListResult<>(list, true, AppCode.SUCCESS.getCode(), AppCode.SUCCESS.getMessage());
    }

    /**
     * 返回失败 DataListResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult() {
        return failDataListResult(AppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param errorCode ErrorCode（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataListResult<T> failDataListResult(ErrorCode errorCode) {
        return new DataListResult<>(errorCode);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param message 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataListResult<T> failDataListResult(String code, String message) {
        return new DataListResult<>(null, false, code, message);
    }

    /**
     * 根据参数返回失败 DataListResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param messages 错误信息
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(String code, List<String> messages) {
        return new DataListResult<>(null, false, code, messages);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param template 信息模板
     * @param params 信息参数
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(String code, String template, Object... params) {
        return new DataListResult<>(null, false, code, template, params);
    }

    /**
     * 返回成功 PageResult 的默认应答
     *
     * @param page 分页信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> successPageResult(Pagination<T> page) {
        return new PageResult<>(page, true, AppCode.SUCCESS.getCode(), AppCode.SUCCESS.getMessage());
    }

    /**
     * 返回失败 PageResult 的默认应答
     *
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult() {
        return new PageResult<>(AppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param errorCode 错误码 {@link ErrorCode}
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(ErrorCode errorCode) {
        return new PageResult<>(null, false, errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param message 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> PageResult<T> failPageResult(String code, String message) {
        return new PageResult<>(null, false, code, message);
    }

    /**
     * 根据参数返回失败 PageResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param messages 错误信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(String code, List<String> messages) {
        return new PageResult<>(null, false, code, messages);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param code 错误码 {@link ErrorCode}
     * @param template 信息模板
     * @param params 信息参数
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(String code, String template, Object... params) {
        return new PageResult<>(null, false, code, template, params);
    }

    public static boolean isValidResult(BaseResult baseResult) {
        if (baseResult == null) {
            return false;
        }

        return baseResult.getSuccess();
    }

    public static <T> boolean isValidResult(DataResult<T> dataResult) {
        if (dataResult == null || !dataResult.getSuccess()) {
            return false;
        }

        return dataResult.getData() != null;
    }

    public static <T> boolean isValidResult(DataListResult<T> dataListResult) {
        if (dataListResult == null || !dataListResult.getSuccess()) {
            return false;
        }

        return !CollectionUtils.isEmpty(dataListResult.getData());
    }

    public static <T> boolean isValidResult(PageResult<T> pageResult) {
        if (pageResult == null || !pageResult.getSuccess()) {
            return false;
        }

        Pagination<T> pagination = pageResult.getData();
        return pagination != null && !CollectionUtils.isEmpty(pagination.getList());
    }

    public static boolean isNotValidResult(BaseResult baseResult) {
        return !isValidResult(baseResult);
    }

    public static <T> boolean isNotValidResult(DataResult<T> dataResult) {
        return !isValidResult(dataResult);
    }

    public static <T> boolean isNotValidResult(DataListResult<T> dataListResult) {
        return !isValidResult(dataListResult);
    }

    public static <T> boolean isNotValidResult(PageResult<T> pageResult) {
        return !isValidResult(pageResult);
    }
}
