package io.github.dunwu.core;

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
        return new BaseResult(true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.msg());
    }

    /**
     * 返回失败 Result 的默认应答
     *
     * @return 成功的 Result
     */
    public static BaseResult failBaseResult() {
        return failBaseResult(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回应答
     *
     * @param appCode IAppCode（系统应答状态码）
     * @return Result
     */
    public static BaseResult failBaseResult(IAppCode appCode) {
        return failBaseResult(appCode.code(), appCode.msg());
    }

    /**
     * 根据枚举返回应答
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param msgs 补充信息数组
     * @return Result
     */
    public static BaseResult failBaseResult(IAppCode appCode, String... msgs) {
        StringBuilder sb = new StringBuilder(appCode.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return failBaseResult(appCode.code(), sb.toString());
    }

    /**
     * 根据枚举的 detail 和 objs 拼接应答信息并返回 Result
     *
     * @param appCode IAppCode
     * @param objs
     * @return
     */
    public static BaseResult failBaseResult(IAppCode appCode, Object... objs) {
        return failBaseResult(appCode.code(), String.format(appCode.detail(), objs));
    }

    /**
     * 根据参数返回失败 Result
     *
     * @param code 错误码
     * @param msg 错误信息
     * @return Result
     */
    public static BaseResult failBaseResult(String code, String msg) {
        return new BaseResult(false, code, msg);
    }

    /**
     * 返回成功 DataResult 的默认应答
     *
     * @param data 数据对象
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> successDataResult(T data) {
        return new DataResult<>(true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.msg(), data);
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
     * @param appCodeEnum IAppCode（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(IAppCode appCodeEnum) {
        return failDataResult(appCodeEnum.code(), appCodeEnum.msg());
    }

    /**
     * 根据枚举返回失败 DataResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> failDataResult(IAppCode appCode, String... msgs) {
        StringBuilder sb = new StringBuilder(appCode.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return failDataResult(appCode.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 DataResult
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> failDataResult(String code, String msg) {
        return new DataResult<>(false, code, msg, null);
    }

    /**
     * 返回成功 Result 的默认应答
     *
     * @param list 数据对象列表
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> DataListResult<T> successDataListResult(Collection<T> list) {
        return new DataListResult<>(true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.msg(), list);
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
        return failDataListResult(appCode, null);
    }

    /**
     * 根据枚举返回失败 DataListResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(IAppCode appCode, String... msgs) {
        StringBuilder sb = new StringBuilder(appCode.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return failDataListResult(appCode.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 DataListResult
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> failDataListResult(String code, String msg) {
        return new DataListResult<>(false, code, msg, null);
    }

    /**
     * 返回成功 PageResult 的默认应答
     *
     * @param list 数据对象列表
     * @param page 分页信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> successPageResult(Collection<T> list, Page page) {
        return new PageResult<>(true, DefaultAppCode.SUCCESS.code(), DefaultAppCode.SUCCESS.msg(), list, page);
    }

    /**
     * 返回失败 PageResult 的默认应答
     *
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult() {
        return failPageResult(DefaultAppCode.FAIL);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(IAppCode appCode) {
        return failPageResult(appCode, null);
    }

    /**
     * 根据枚举返回失败 PageResult
     *
     * @param appCode IAppCode（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(IAppCode appCode, String... msgs) {
        StringBuilder sb = new StringBuilder(appCode.msg());
        for (String str : msgs) {
            sb.append("\r\n").append(str);
        }
        return failPageResult(appCode.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 PageResult
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> failPageResult(String code, String msg) {
        return new PageResult<>(false, code, msg, null, null);
    }
}
