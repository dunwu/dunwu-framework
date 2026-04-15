package io.github.dunwu.common.function;

import java.io.Serializable;

/**
 * 可抛出受检异常的函数式接口
 *
 * @param <P> 参数类型
 * @param <R> 返回值类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
@FunctionalInterface
public interface CallFunction<P, R> extends Serializable {

    /**
     * 执行调用
     *
     * @param var1 输入参数
     * @return 调用结果
     * @throws Exception 调用过程中可能抛出的异常
     */
    R call(P var1) throws Exception;

    /**
     * 执行调用，将受检异常包装为 {@link RuntimeException} 抛出
     *
     * @param parameter 输入参数
     * @return 调用结果
     */
    default R callWithRuntimeException(P parameter) {
        try {
            return this.call(parameter);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

}
