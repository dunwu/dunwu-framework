package io.github.dunwu.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 运行时工具类 1.取得当前进程PID, JVM参数 2.注册JVM关闭钩子, 获得CPU核数 3.通过StackTrace 获得当前方法的类名方法名，调用者的类名方法名(获取StackTrace有消耗，不要滥用)
 */
public class SystemExtUtils extends SystemUtils {

	public static final int SEPARATE_ARRAY_LENGTH = 2;

	public static final int MAX_STACKTRACE_LENGTH = 4;

	public static final String WINDOWS_FILE_PATH_SEPARATOR = "\\";

	public static final String LINUX_FILE_PATH_SEPARATOR = "/";

	// Java Version
	// -------------------------------------------------------------------------------------------------
	private static final float JAVA_VERSION_VALUE = Float
		.parseFloat(JAVA_SPECIFICATION_VERSION);

	// ManagementFactory 信息
	// -------------------------------------------------------------------------------------------------

	private static AtomicInteger shutdownHookThreadIndex = new AtomicInteger(0);

	// private constructor
	// -------------------------------------------------------------------------------------------------
	private SystemExtUtils() {
	}

	/**
	 * 注册JVM关闭时的钩子程序
	 */
	public static void addShutdownHook(Runnable runnable) {
		Runtime.getRuntime().addShutdownHook(new Thread(runnable,
			"Thread-ShutDownHook-" + shutdownHookThreadIndex.incrementAndGet()));
	}

	// Runtime 信息
	// -------------------------------------------------------------------------------------------------

	/**
	 * 通过StackTrace，获得调用者的类名. 获取StackTrace有消耗，不要滥用
	 */
	public static String getCallerClass() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= MAX_STACKTRACE_LENGTH) {
			StackTraceElement element = stacktrace[3];
			return element.getClassName();
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 通过StackTrace，获得调用者的"类名.方法名()" 获取StackTrace有消耗，不要滥用
	 */
	public static String getCallerMethod() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= MAX_STACKTRACE_LENGTH) {
			StackTraceElement element = stacktrace[3];
			return element.getClassName() + '.' + element.getMethodName() + "()";
		} else {
			return StringUtils.EMPTY;
		}
	}

	// StackTrace 信息
	// -------------------------------------------------------------------------------------------------

	/**
	 * 获取 CPU 核数
	 */
	public static int getCores() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 通过StackTrace，获得当前方法的类名. 获取StackTrace有消耗，不要滥用
	 */
	public static String getCurrentClass() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= MAX_STACKTRACE_LENGTH) {
			StackTraceElement element = stacktrace[2];
			return element.getClassName();
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 通过StackTrace，获得当前方法的"类名.方法名()" 获取StackTrace有消耗，不要滥用
	 */
	public static String getCurrentMethod() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		if (stacktrace.length >= MAX_STACKTRACE_LENGTH) {
			StackTraceElement element = stacktrace[2];
			return element.getClassName() + '.' + element.getMethodName() + "()";
		} else {
			return StringUtils.EMPTY;
		}
	}

	public static String getJavaVersion() {
		return JAVA_SPECIFICATION_VERSION;
	}

	/**
	 * 获得当前进程的 PID，若失败时返回-1
	 */
	public static int getPid() {
		// format: "pid@hostname"
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		String[] split = jvmName.split("@");
		if (split.length != SEPARATE_ARRAY_LENGTH) {
			return -1;
		}

		try {
			return Integer.parseInt(split[0]);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 返回应用启动到现在的毫秒数
	 */
	public static long getUpTime() {
		return ManagementFactory.getRuntimeMXBean().getUptime();
	}

	/**
	 * 返回输入的 JVM 参数列表
	 */
	public static String getVmArguments() {
		List<String> vmArguments = ManagementFactory.getRuntimeMXBean()
			.getInputArguments();
		return StringUtils.join(vmArguments, " ");
	}

	public static boolean isGreaterThanJava8() {
		return JAVA_VERSION_VALUE > 1.8f;
	}

	public static boolean isJava6() {
		return "1.6".equals(JAVA_SPECIFICATION_VERSION);
	}

	public static boolean isJava7() {
		return "1.7".equals(JAVA_SPECIFICATION_VERSION);
	}

	public static boolean isJava8() {
		return "1.8".equals(JAVA_SPECIFICATION_VERSION);
	}

	public static boolean isJava9() {
		return "9".equals(JAVA_SPECIFICATION_VERSION);
	}

}
