package io.github.dunwu.util.text;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 文本工具类单元测试
 */
public class TextUtilsTest {

	@Test
	public void match() {
		assertThat(TextUtils.match("abc", "*")).isTrue();
		assertThat(TextUtils.match("abc", "*c")).isTrue();
		assertThat(TextUtils.match("abc", "a*")).isTrue();
		assertThat(TextUtils.match("abc", "a*c")).isTrue();

		assertThat(TextUtils.match("abc", "a?c")).isTrue();
		assertThat(TextUtils.match("abcd", "a?c?")).isTrue();
		assertThat(TextUtils.match("abcd", "a??d")).isTrue();

		assertThat(TextUtils.match("abcde", "a*d?")).isTrue();

		assertThat(TextUtils.match("abcde", "a*d")).isFalse();
		assertThat(TextUtils.match("abcde", "a*x")).isFalse();
		assertThat(TextUtils.match("abcde", "a*df")).isFalse();

		assertThat(TextUtils.match("abcde", "?abcd")).isFalse();

		assertThat(TextUtils.match("ab\\\\*cde", "ab\\\\*c*")).isTrue();
		assertThat(TextUtils.match("ab\\\\*cde", "ab\\\\*?de")).isTrue();

		assertThat(TextUtils.matchOne("abcd", "a??d", "a?c")).isEqualTo(0);
	}

	@Test
	public void test() {
		System.out.println(TextUtils.maskText("name1@name2.name3"));
		System.out.println(TextUtils.maskText("11170998762"));
	}

}
