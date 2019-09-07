package io.github.dunwu.util.text;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 文本工具类单元测试
 */
public class TextUtilTest {

	@Test
	public void match() {
		assertThat(TextUtil.match("abc", "*")).isTrue();
		assertThat(TextUtil.match("abc", "*c")).isTrue();
		assertThat(TextUtil.match("abc", "a*")).isTrue();
		assertThat(TextUtil.match("abc", "a*c")).isTrue();

		assertThat(TextUtil.match("abc", "a?c")).isTrue();
		assertThat(TextUtil.match("abcd", "a?c?")).isTrue();
		assertThat(TextUtil.match("abcd", "a??d")).isTrue();

		assertThat(TextUtil.match("abcde", "a*d?")).isTrue();

		assertThat(TextUtil.match("abcde", "a*d")).isFalse();
		assertThat(TextUtil.match("abcde", "a*x")).isFalse();
		assertThat(TextUtil.match("abcde", "a*df")).isFalse();

		assertThat(TextUtil.match("abcde", "?abcd")).isFalse();

		assertThat(TextUtil.match("ab\\\\*cde", "ab\\\\*c*")).isTrue();
		assertThat(TextUtil.match("ab\\\\*cde", "ab\\\\*?de")).isTrue();

		assertThat(TextUtil.matchOne("abcd", "a??d", "a?c")).isEqualTo(0);
	}

	@Test
	public void test() {
		System.out.println(TextUtil.maskText("name1@name2.name3"));
		System.out.println(TextUtil.maskText("11170998762"));
	}

}
