package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.base.annotation.NotNull;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 线程相关工具类. 1. 处理了InterruptedException的sleep 2. 正确的InterruptedException处理方法
 */
public class ThreadUtil {

	/**
	 * sleep等待, 单位为毫秒, 已捕捉并处理InterruptedException.
	 */
	public static void sleep(long durationMillis) {
		try {
			Thread.sleep(durationMillis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * sleep等待，已捕捉并处理InterruptedException.
	 */
	public static void sleep(long duration, TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(duration));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 纯粹为了提醒下处理InterruptedException的正确方式，除非你是在写不可中断的任务.
	 */
	public static void handleInterruptedException() {
		Thread.currentThread().interrupt();
	}

	/**
	 * 防止用户没有捕捉异常导致中断了线程池中的线程, 使得SchedulerService无法继续执行. 在无法控制第三方包的Runnable实现时，调用本函数进行包裹.
	 */
	public static Runnable safeRunnable(@NotNull Runnable runnable) {
		return new SafeRunnable(runnable);
	}

	/**
	 * 保证不会有Exception抛出到线程池的Runnable包裹类，防止用户没有捕捉异常导致中断了线程池中的线程, 使得SchedulerService无法执行. 在无法控制第三方包的Runnalbe实现时，使用本类进行包裹.
	 */
	private static class SafeRunnable implements Runnable {

		private static Logger logger = LoggerFactory.getLogger(SafeRunnable.class);

		private Runnable runnable;

		public SafeRunnable(Runnable runnable) {
			Validate.notNull(runnable);
			this.runnable = runnable;
		}

		@Override
		public void run() {
			try {
				runnable.run();
			} catch (Throwable e) {
				logger.error("Unexpected error occurred in task", e);
			}
		}

	}

}
