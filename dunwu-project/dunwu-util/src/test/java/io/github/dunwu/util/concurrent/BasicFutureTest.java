package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.base.ExceptionUtil;
import io.github.dunwu.util.concurrent.type.BaseFuture;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BasicFutureTest {

	@Test
	public void test() throws InterruptedException, ExecutionException {
		MyFuture<String> future = new MyFuture<String>();
		Tasks.success(future);
		String result = future.get();
		assertThat(result).isEqualTo("haha");

		// 无人设置返回值
		try {
			MyFuture<String> future2 = new MyFuture<String>();
			future2.get(10, TimeUnit.MILLISECONDS);
			fail("should fail before");
		} catch (TimeoutException e) {
			assertThat(e).isInstanceOf(TimeoutException.class);
		}

		// 失败
		try {
			MyFuture<String> future3 = new MyFuture<String>();
			Tasks.fail(future3);
			future3.get();
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(ExceptionUtil.unwrap(t)).hasMessage("wuwu");
		}

		// 取消
		MyFuture<String> future4 = new MyFuture<String>();
		Tasks.cancel(future4);
		assertThat(future4.isCancelled()).isTrue();
		try {
			String result4 = future4.get();
			fail("should fail here");
		} catch (CancellationException cae) {

		}
	}

	public static class MyFuture<T> extends BaseFuture<T> {

		@Override
		protected void onCompleted(T result) {
			System.out.println("onCompleted:" + result);
		}

		@Override
		protected void onFailed(Exception ex) {
			System.out.println("onFailed:" + ex.getMessage());
		}

		@Override
		protected void onCancelled() {
			System.out.println("onCancelled");
		}

	}

	private static class Tasks {

		public static void success(MyFuture<String> future) {
			future.completed("haha");
		}

		public static void fail(MyFuture<String> future) {
			future.failed(new RuntimeException("wuwu"));
		}

		public static void cancel(MyFuture<String> future) {
			future.cancel(true);
		}

	}

}
