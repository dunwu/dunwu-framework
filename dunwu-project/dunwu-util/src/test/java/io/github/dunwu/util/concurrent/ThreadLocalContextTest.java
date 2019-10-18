package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.concurrent.type.ThreadLocalContext;
import io.github.dunwu.util.number.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class ThreadLocalContextTest {

	@Test
	public void test() throws InterruptedException {

		final CountDownLatch countdown = Concurrents.countDownLatch(10);
		final CyclicBarrier barrier = Concurrents.cyclicBarrier(10);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					barrier.await();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ThreadLocalContext.put("myname", Thread.currentThread().getName());
				ThreadUtil.sleep(RandomUtil.nextLong(100, 300));
				System.out.println((char[]) ThreadLocalContext.get("myname"));
				ThreadLocalContext.reset();
				System.out.println(
						"shoud null for " + Thread.currentThread().getName() + ":" + ThreadLocalContext.get("myname"));
				countdown.countDown();
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}

		countdown.await();
	}

}
