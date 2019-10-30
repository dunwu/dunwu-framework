package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.base.ObjectUtil;
import io.github.dunwu.util.base.RuntimeUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadUtilTest {

	@Test
	public void testCaller() {
		hello();
		new MyClass().hello();
		assertThat(RuntimeUtil.getCurrentClass())
			.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
		assertThat(RuntimeUtil.getCurrentMethod()).isEqualTo(
			"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
	}

	private void hello() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		System.out.println(ObjectUtil.toPrettyString(stacktrace));

		assertThat(RuntimeUtil.getCallerClass())
			.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
		assertThat(RuntimeUtil.getCallerMethod()).isEqualTo(
			"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
	}

	public static class MyClass {

		public void hello() {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			System.out.println(ObjectUtil.toPrettyString(stacktrace));

			assertThat(RuntimeUtil.getCallerClass())
				.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
			assertThat(RuntimeUtil.getCallerMethod()).isEqualTo(
				"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
		}

	}

}
