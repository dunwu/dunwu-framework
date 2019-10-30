package io.github.dunwu.util.io;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * IO Stream/Reader相关工具集. 固定encoding为UTF8. 建议使用Apache Commons IO和Guava关于IO的工具类(com.google.common.io.*), 在未引入Commons
 * IO时可以用本类做最基本的事情.
 * <p>
 * 1. 安静关闭Closeable对象
 * <p>
 * 2. 读出InputStream/Reader内容到String 或 List<String>
 * <p>
 * 3. 将String写到OutputStream/Writer
 * <p>
 * 4. InputStream/Reader与OutputStream/Writer之间复制的copy
 */
public class ReadWriteUtil {

	private static final String CLOSE_ERROR_MESSAGE = "IOException thrown while closing Closeable.";

	private static final Logger logger = LoggerFactory.getLogger(ReadWriteUtil.class);

	/**
	 * 在final中安静的关闭, 不再往外抛出异常避免影响原有异常，最常用函数. 同时兼容Closeable为空未实际创建的情况.
	 *
	 * @see {@link Closeables#close}
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (IOException e) {
			logger.warn(CLOSE_ERROR_MESSAGE, e);
		}
	}

	/**
	 * 简单读取InputStream到String.
	 */
	public static String toString(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
		return toString(reader);
	}

	/**
	 * 简单读取Reader到String
	 *
	 * @see {@link CharStreams#toString}
	 */
	public static String toString(Reader input) throws IOException {
		return CharStreams.toString(input);
	}

	/**
	 * 简单读取Reader的每行内容到List<String>
	 */
	public static List<String> toLines(final InputStream input) throws IOException {
		return toLines(new InputStreamReader(input, StandardCharsets.UTF_8));
	}

	/**
	 * 简单读取Reader的每行内容到List<String>
	 *
	 * @see {@link CharStreams#readLines}
	 */
	public static List<String> toLines(final Reader input) throws IOException {
		return CharStreams.readLines(toBufferedReader(input));
	}

	public static BufferedReader toBufferedReader(final Reader reader) {
		return reader instanceof BufferedReader ? (BufferedReader) reader
			: new BufferedReader(reader);
	}

	/**
	 * 简单写入String到OutputStream.
	 */
	public static void write(final String data, final OutputStream output)
		throws IOException {
		if (data != null) {
			output.write(data.getBytes(StandardCharsets.UTF_8));
		}
	}

	/**
	 * 简单写入String到Writer.
	 */
	public static void write(final String data, final Writer output) throws IOException {
		if (data != null) {
			output.write(data);
		}
	}

	/**
	 * 在Reader与Writer间复制内容
	 *
	 * @see {@link CharStreams#copy}
	 */
	public static long copy(final Reader input, final Writer output) throws IOException {
		return CharStreams.copy(input, output);
	}

	/**
	 * 在InputStream与OutputStream间复制内容
	 *
	 * @see {@link ByteStreams#copy}
	 */
	public static long copy(final InputStream input, final OutputStream output)
		throws IOException {
		return ByteStreams.copy(input, output);
	}

	public static final InputStream bytes2InputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

}
