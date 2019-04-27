package io.github.dunwu.core;

import java.util.List;

/**
 * 应答工具类
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class ResultUtil {

    /**
     * 返回成功 Result 的默认应答
     * @return Result
     */
    public static Result success() {
        return new Result(true, SystemCode.SUCCESS.code(), SystemCode.SUCCESS.msg());
    }

    /**
     * 返回成功 Result 的默认应答
     * @param data 数据对象
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, SystemCode.SUCCESS.code(), SystemCode.SUCCESS.msg(), data);
    }

    /**
     * 返回成功 Result 的默认应答
     * @param data 数据对象列表
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> success(List<T> data) {
        return new Result<>(true, SystemCode.SUCCESS.code(), SystemCode.SUCCESS.msg(), data);
    }

    /**
     * 返回成功 Result 的默认应答
     * @param list 数据对象列表
     * @param page 分页信息
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> success(List<T> list, Page page) {
        return new Result<>(true, SystemCode.SUCCESS.code(), SystemCode.SUCCESS.msg(), list, page);
    }

    /**
     * 返回失败 Result 的默认应答
     * @return 成功的 Result
     */
    public static Result fail() {
        return fail(SystemCode.FAIL);
    }

    /**
     * 根据枚举返回应答
     * @param systemCode SystemCode（系统应答状态码）
     * @return Result
     */
    public static Result fail(SystemCode systemCode) {
        return fail(systemCode.code(), systemCode.msg());
    }

    /**
     * 根据枚举返回应答
     * @param systemCode SystemCode（系统应答状态码）
     * @param msgs 补充信息数组
     * @return Result
     */
    public static Result fail(SystemCode systemCode, String... msgs) {
        StringBuilder sb = new StringBuilder(systemCode.msg());
        if (msgs != null && msgs.length > 0) {
            for (String str : msgs) {
                sb.append("\r\n").append(str);
            }
        }

        return fail(systemCode.code(), sb.toString());
    }

    /**
     * 根据枚举的 detail 和 objs 拼接应答信息并返回 Result
     * @param systemCode
     * @param objs
     * @return
     */
    public static Result fail(SystemCode systemCode, Object... objs) {
        return fail(systemCode.code(), String.format(systemCode.detail(), objs));
    }

    /**
     * 根据参数返回失败 Result
     * @param code 错误码
     * @param msg 错误信息
     * @return Result
     */
    public static Result fail(Integer code, String msg) {
        return new Result(false, code, msg);
    }

}
