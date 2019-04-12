package io.github.dunwu.web.core;

import java.util.List;

/**
 * 应答工具类
 * @author Zhang Peng
 * @date 2019-04-11
 */
public class ResultUtil {

    /**
     * 返回成功 BaseResult 的默认应答
     * @return BaseResult
     */
    public static BaseResult newSuccessBaseResult() {
        return new BaseResult(true, AppCodeEnum.SUCCESS.code(), AppCodeEnum.SUCCESS.msg());
    }

    /**
     * 返回失败 BaseResult 的默认应答
     * @return 成功的 BaseResult
     */
    public static BaseResult newFailBaseResult() {
        return newFailBaseResult(AppCodeEnum.FAIL);
    }

    /**
     * 根据枚举返回应答
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @return BaseResult
     */
    public static BaseResult newFailBaseResult(AppCodeEnum appCodeEnum) {
        return newFailBaseResult(appCodeEnum.code(), appCodeEnum.msg());
    }

    /**
     * 根据枚举返回应答
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param msgs 补充信息数组
     * @return BaseResult
     */
    public static BaseResult newFailBaseResult(AppCodeEnum appCodeEnum, String... msgs) {
        StringBuilder sb = new StringBuilder(appCodeEnum.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return newFailBaseResult(appCodeEnum.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 BaseResult
     * @param code 错误码
     * @param msg 错误信息
     * @return BaseResult
     */
    public static BaseResult newFailBaseResult(Integer code, String msg) {
        return new BaseResult(false, code, msg);
    }

    /**
     * 返回成功 DataResult 的默认应答
     * @param data 数据对象
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> newSuccessDataResult(T data) {
        return new DataResult<>(true, AppCodeEnum.SUCCESS.code(), AppCodeEnum.SUCCESS.msg(), data);
    }

    /**
     * 返回失败 DataResult 的默认应答
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> newFailDataResult() {
        return newFailDataResult(AppCodeEnum.FAIL);
    }

    /**
     * 根据枚举返回失败 DataResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> newFailDataResult(AppCodeEnum appCodeEnum) {
        return newFailDataResult(appCodeEnum.code(), appCodeEnum.msg());
    }

    /**
     * 根据枚举返回失败 DataResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataResult<T> newFailDataResult(AppCodeEnum appCodeEnum, String... msgs) {
        StringBuilder sb = new StringBuilder(appCodeEnum.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return newFailDataResult(appCodeEnum.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 DataResult
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataResult<T> newFailDataResult(Integer code, String msg) {
        return new DataResult<>(false, code, msg, null);
    }

    /**
     * 返回成功 DataListResult 的默认应答
     * @param data 数据对象列表
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> newSuccessDataListResult(List<T> data) {
        return new DataListResult<>(true, AppCodeEnum.SUCCESS.code(), AppCodeEnum.SUCCESS.msg(), data);
    }

    /**
     * 返回失败 DataResult 的默认应答
     * @param <T> 数据类型
     * @return DataResult
     */
    public static <T> DataListResult<T> newFailDataListResult() {
        return newFailDataListResult(AppCodeEnum.FAIL);
    }

    /**
     * 根据枚举返回失败 DataResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param <T> 数据类型
     * @return BaseResult
     */
    public static <T> DataListResult<T> newFailDataListResult(AppCodeEnum appCodeEnum) {
        return newFailDataListResult(appCodeEnum, null);
    }

    /**
     * 根据枚举返回失败 DataListResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> newFailDataListResult(AppCodeEnum appCodeEnum, String... msgs) {
        StringBuilder sb = new StringBuilder(appCodeEnum.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return newFailDataListResult(appCodeEnum.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 DataListResult
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return DataListResult
     */
    public static <T> DataListResult<T> newFailDataListResult(Integer code, String msg) {
        return new DataListResult<>(false, code, msg, null);
    }

    /**
     * 返回成功 PageResult 的默认应答
     * @param data 数据对象列表
     * @param pagination 分页信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> newSuccessPageResult(List<T> data, PageResult.Pagination pagination) {
        return new PageResult<>(true, AppCodeEnum.SUCCESS.code(), AppCodeEnum.SUCCESS.msg(), data, pagination);
    }

    /**
     * 返回失败 PageResult 的默认应答
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> newFailPageResult() {
        return newFailPageResult(AppCodeEnum.FAIL);
    }

    /**
     * 根据枚举返回失败 PageResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> newFailPageResult(AppCodeEnum appCodeEnum) {
        return newFailPageResult(appCodeEnum, null);
    }

    /**
     * 根据枚举返回失败 PageResult
     * @param appCodeEnum AppCodeEnum（系统应答状态码）
     * @param msgs 信息数组
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> newFailPageResult(AppCodeEnum appCodeEnum, String... msgs) {
        StringBuilder sb = new StringBuilder(appCodeEnum.msg());
        for (String str : msgs) {
            sb.append("\r\n").append(str);
        }
        return newFailPageResult(appCodeEnum.code(), sb.toString());
    }

    /**
     * 根据参数返回失败 PageResult
     * @param code 错误码
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return PageResult
     */
    public static <T> PageResult<T> newFailPageResult(Integer code, String msg) {
        return new PageResult<>(false, code, msg, null, null);
    }
}
