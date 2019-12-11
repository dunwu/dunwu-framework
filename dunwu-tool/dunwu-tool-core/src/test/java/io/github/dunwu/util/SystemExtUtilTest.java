package io.github.dunwu.util;

import org.junit.jupiter.api.Test;

import static io.github.dunwu.util.SystemExtUtil.addShutdownHook;
import static org.assertj.core.api.Assertions.assertThat;

class SystemExtUtilTest {

    // Java Version
    // -------------------------------------------------------------------------------------------------
    @Test
    void testJavaVersion() {
        String javaVersion = SystemExtUtil.getJavaVersion();
        assertThat(javaVersion).isEqualTo("1.8");
        assertThat(SystemExtUtil.isJava8()).isTrue();
    }

    @Test
    void testRuntime() {
        System.out.println("PID：" + SystemExtUtil.getPid());
        assertThat(SystemExtUtil.getPid()).isNotEqualTo(-1);

        System.out.println("JVM Args：" + SystemExtUtil.getVmArguments());
        addShutdownHook(() -> System.out.println("shutdown"));
        assertThat(SystemExtUtil.getCores()).isGreaterThan(1);

        System.out.println("Uptime：" + SystemExtUtil.getUpTime());
    }

}
