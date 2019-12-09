package io.github.dunwu.util;

import org.junit.jupiter.api.Test;

import static io.github.dunwu.util.SystemExtUtils.addShutdownHook;
import static org.assertj.core.api.Assertions.assertThat;

class SystemExtUtilsTest {

	// Java Version
	// -------------------------------------------------------------------------------------------------
	@Test
	void testJavaVersion() {
		String javaVersion = SystemExtUtils.getJavaVersion();
		assertThat(javaVersion).isEqualTo("1.8");
		assertThat(SystemExtUtils.isJava8()).isTrue();
	}

	@Test
	void testRuntime() {
		System.out.println("PID：" + SystemExtUtils.getPid());
		assertThat(SystemExtUtils.getPid()).isNotEqualTo(-1);

		System.out.println("JVM Args：" + SystemExtUtils.getVmArguments());
		addShutdownHook(() -> System.out.println("shutdown"));
		assertThat(SystemExtUtils.getCores()).isGreaterThan(1);

		System.out.println("Uptime：" + SystemExtUtils.getUpTime());
	}

}
