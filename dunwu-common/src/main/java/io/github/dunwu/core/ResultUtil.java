package io.github.dunwu.core;

import java.io.Serializable;
import java.util.Collection;

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
        return new BaseResult(true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.message());
    }

    /**
     * 返回失败 Result 的默认应答
     *
     * @return 成功的 Result
     */
    public static BaseResult failBaseResult() {
        return new BaseResult(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回应答
     *
     * @param appCode IAppCode（系统应答状态码）
     * @return Result
     */
    public static BaseResult failBaseResult(IAppCode appCode) {
        return new BaseResult(appCode);
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code 错误码
     * @param messages 错误信息
     * @return Result
     */
    public static BaseResult failBaseResult(String code, String... messages) {
        return new BaseResult(false, code, messages);
    }

    /**
     * 根据枚举返回应答
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param messages 补充信息数组
     * @return Result
     */
    public static BaseResult failBaseResult(IAppCode appCode, String... messages) {
        return new BaseResult(false, appCode.code(), messages);
    }

    /**
     * 返回成功 DataResult 的默认应答
     *
     * @param data 数据对象
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> successDataResult(T data) {
        return new DataResult<>(data, true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.message());
    }

    /**
     * 返回失败 DataResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failDataResult() {
        return failDataResult(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(IAppCode appCode) {
        return new DataResult<>(appCode);
    }

    /**
     * 根据参数返回失败 DataResult
     *
     * @param code 错误码
     * @param messages 错误信息
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(String code, String... messages) {
        return new DataResult<>(null, false, code, messages);
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param messages 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failDataResult(IAppCode appCode, String... messages) {
        return new DataResult<>(null, false, appCode.code(), messages);
    }

    /**
     * 返回成功 Result 的默认应答
     *
     * @param list 数据对象列表
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> DataListResult<T> successDataListResult(Collection<T> list) {
        return new DataListResult<>(list, true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.message());
    }

    /**
     * 返回失败 DataListResult 的默认应答
     *
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult() {
        return failDataListResult(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataListResult<T> failDataListResult(IAppCode appCode) {
        return new DataListResult<>(appCode);
    }

    /**
     * 根据参数返回失败 DataListResult
     *
     * @param code 错误码
     * @param messages 错误信息
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(String code, String... messages) {
        return new DataListResult<>(null, false, code, messages);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param messages 信息数组
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(IAppCode appCode, String... messages) {
        return new DataListResult<>(null, false, appCode.code(), messages);
    }

    /**
     * 返回成功 PageResult 的默认应答
     *
     * @param list 数据对象列表
     * @param page 分页信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> successPageResult(Collection<T> list, PageResult.Page page) {
        return new PageResult<>(list, page, true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.message());
    }

    /**
     * 返回失败 PageResult 的默认应答
     *
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult() {
        return new PageResult<>(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(IAppCode appCode) {
        return new PageResult<>(null, null, false, appCode.code(), appCode.message());
    }

    /**
     * 根据参数返回失败 PageResult
     *
     * @param code 错误码
     * @param messages 错误信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(String code, String... messages) {
        return new PageResult<>(null, null, false, code, messages);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param messages 信息数组
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(IAppCode appCode, String... messages) {
        return new PageResult<>(null, null, false, appCode.code(), messages);
    }
}
