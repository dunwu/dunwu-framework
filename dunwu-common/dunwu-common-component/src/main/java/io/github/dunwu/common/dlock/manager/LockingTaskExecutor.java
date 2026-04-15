package io.github.dunwu.common.dlock.manager;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * 加锁任务执行器接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public interface LockingTaskExecutor {

    /**
     * 使用分布式锁执行 Runnable 任务，若任务已在运行则跳过
     *
     * @param task       待执行的任务
     * @param lockConfig 锁配置
     */
    void executeWithLock(@NotNull Runnable task, @NotNull LockConfiguration lockConfig);

    /**
     * 使用分布式锁执行 Task 任务
     *
     * @param task       待执行的任务
     * @param lockConfig 锁配置
     * @throws Throwable 任务执行过程中可能抛出的异常
     */
    void executeWithLock(@NotNull Task task, @NotNull LockConfiguration lockConfig) throws Throwable;

    /**
     * 使用分布式锁执行带返回值的任务
     *
     * @param task       待执行的任务
     * @param lockConfig 锁配置
     * @param <T>        返回值类型
     * @return 任务执行结果
     * @throws Throwable 任务执行过程中可能抛出的异常
     */
    @NotNull
    default <T> TaskResult<T> executeWithLock(@NotNull TaskWithResult<T> task, @NotNull LockConfiguration lockConfig)
        throws Throwable {
        throw new UnsupportedOperationException();
    }

    /**
     * 可抛出异常的任务接口
     */
    @FunctionalInterface
    interface Task {

        /**
         * 执行任务
         *
         * @throws Throwable 任务执行过程中可能抛出的异常
         */
        void call() throws Throwable;

    }

    /**
     * 可抛出异常的带返回值任务接口
     *
     * @param <T> 返回值类型
     */
    @FunctionalInterface
    interface TaskWithResult<T> {

        /**
         * 执行任务并返回结果
         *
         * @return 任务执行结果
         * @throws Throwable 任务执行过程中可能抛出的异常
         */
        T call() throws Throwable;

    }

    /**
     * 任务执行结果包装类
     *
     * @param <T> 结果类型
     */
    final class TaskResult<T> {

        private final boolean executed;

        private final T result;

        private TaskResult(boolean executed, @Nullable T result) {
            this.executed = executed;
            this.result = result;
        }

        /**
         * 创建已执行的结果
         *
         * @param result 执行结果
         * @param <T>    结果类型
         * @return 任务执行结果
         */
        static <T> TaskResult<T> result(@Nullable T result) {
            return new TaskResult<>(true, result);
        }

        /**
         * 创建未执行的结果
         *
         * @param <T> 结果类型
         * @return 未执行的任务结果
         */
        static <T> TaskResult<T> notExecuted() {
            return new TaskResult<>(false, null);
        }

        /**
         * 判断任务是否已执行
         *
         * @return 如果已执行返回 true，否则返回 false
         */
        public boolean wasExecuted() {
            return executed;
        }

        /**
         * 获取任务执行结果
         *
         * @return 执行结果，可能为 null
         */
        @Nullable
        public T getResult() {
            return result;
        }

    }

}
