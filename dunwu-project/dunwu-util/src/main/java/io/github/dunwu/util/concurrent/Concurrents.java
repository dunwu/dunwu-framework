package io.github.dunwu.util.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 并发常用工具类
 */
public class Concurrents {

	/**
	 * 返回CountDownLatch, 每条线程减1，减到0时正在latch.wait()的进程继续进行
	 */
	public static CountDownLatch countDownLatch(int count) {
		return new CountDownLatch(count);
	}

	/**
	 * 返回CyclicBarrier，每条线程减1并等待，减到0时，所有线程继续运行
	 */
	public static CyclicBarrier cyclicBarrier(int count) {
		return new CyclicBarrier(count);
	}

	/**
	 * 返回默认的非公平信号量，先请求的线程不一定先拿到信号量
	 */
	public static Semaphore nonFairSemaphore(int permits) {
		return new Semaphore(permits);
	}

	/**
	 * 返回公平的信号量，先请求的线程先拿到信号量
	 */
	public static Semaphore fairSemaphore(int permits) {
		return new Semaphore(permits, true);
	}

	/////////// 限流采样 //////

	/**
	 * 返回漏桶算法的RateLimiter
	 *
	 * @permitsPerSecond 期望的QPS, RateLimiter将QPS平滑到毫秒级别上，但有蓄水的能力.
	 */
	public static RateLimiter rateLimiter(int permitsPerSecond) {
		return RateLimiter.create(permitsPerSecond);
	}

	/**
	 * 返回采样器.
	 *
	 * @param selectPercent 采样率，在0-100 之间，可以有小数位
	 */
	public static Sampler sampler(double selectPercent) {
		return Sampler.create(selectPercent);
	}

}
