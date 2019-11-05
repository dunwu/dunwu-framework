package io.github.dunwu.util.io;

import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * IO 工具类
 */
public class IOExtUtils extends IOUtils {

	/**
	 * 使用 InputStream 读取字符串
	 */
	public static String readString(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
		return readString(reader);
	}

	/**
	 * 使用 Reader 读取字符串
	 */
	@SuppressWarnings("all")
	public static String readString(Reader input) throws IOException {
		return CharStreams.toString(input);
	}

}
