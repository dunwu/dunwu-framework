package io.github.dunwu.util.text;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WildcardUtilTest {

    @Test
    public void match() {
        assertThat(WildcardUtil.match("abc", "*")).isTrue();
        assertThat(WildcardUtil.match("abc", "*c")).isTrue();
        assertThat(WildcardUtil.match("abc", "a*")).isTrue();
        assertThat(WildcardUtil.match("abc", "a*c")).isTrue();

        assertThat(WildcardUtil.match("abc", "a?c")).isTrue();
        assertThat(WildcardUtil.match("abcd", "a?c?")).isTrue();
        assertThat(WildcardUtil.match("abcd", "a??d")).isTrue();

        assertThat(WildcardUtil.match("abcde", "a*d?")).isTrue();

        assertThat(WildcardUtil.match("abcde", "a*d")).isFalse();
        assertThat(WildcardUtil.match("abcde", "a*x")).isFalse();
        assertThat(WildcardUtil.match("abcde", "a*df")).isFalse();

        assertThat(WildcardUtil.match("abcde", "?abcd")).isFalse();

        assertThat(WildcardUtil.match("ab\\\\*cde", "ab\\\\*c*")).isTrue();
        assertThat(WildcardUtil.match("ab\\\\*cde", "ab\\\\*?de")).isTrue();

        assertThat(WildcardUtil.matchOne("abcd", "a??d", "a?c")).isEqualTo(0);
    }
}
