package io.github.dunwu.util.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 尽量使用Charsets.UTF8而不是"UTF-8"，减少JDK里的Charset查找消耗. 使用JDK7的StandardCharsets，同时留了标准名称的字符串
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 */
public interface Charsets {

	Charset UTF_8 = StandardCharsets.UTF_8;

	Charset US_ASCII = StandardCharsets.US_ASCII;

	Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

	String UTF_8_NAME = StandardCharsets.UTF_8.name();

	String ASCII_NAME = StandardCharsets.US_ASCII.name();

	String ISO_8859_1_NAME = StandardCharsets.ISO_8859_1.name();

}
