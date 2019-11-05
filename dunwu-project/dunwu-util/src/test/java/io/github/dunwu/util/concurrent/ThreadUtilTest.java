package io.github.dunwu.util.concurrent;

import io.github.dunwu.util.base.ObjectExtUtils;
import io.github.dunwu.util.SystemExtUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadUtilTest {

	@Test
	public void testCaller() {
		hello();
		new MyClass().hello();
		assertThat(SystemExtUtils.getCurrentClass())
				.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
		assertThat(SystemExtUtils.getCurrentMethod()).isEqualTo(
				"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
	}

	private void hello() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		System.out.println(ObjectExtUtils.toPrettyString(stacktrace));

		assertThat(SystemExtUtils.getCallerClass())
				.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
		assertThat(SystemExtUtils.getCallerMethod()).isEqualTo(
				"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
	}

	public static class MyClass {

		public void hello() {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			System.out.println(ObjectExtUtils.toPrettyString(stacktrace));

			assertThat(SystemExtUtils.getCallerClass())
					.isEqualTo("io.github.dunwu.utils.concurrent.ThreadUtilTest");
			assertThat(SystemExtUtils.getCallerMethod()).isEqualTo(
					"io.github.dunwu.utils.concurrent.ThreadUtilTest.testCaller()");
		}

	}

}
