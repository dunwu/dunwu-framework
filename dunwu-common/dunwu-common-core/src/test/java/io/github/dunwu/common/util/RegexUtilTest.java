package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link RegexUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2016/10/27
 */
@DisplayName("RegexUtil 单元测试")
class RegexUtilTest {

    // ============================== contains ==============================

    @Nested
    @DisplayName("contains 测试")
    class ContainsTest {

        @Test
        @DisplayName("文本包含匹配内容时返回 true（String regex）")
        void shouldReturnTrueWhenTextContainsMatch() {
            assertThat(RegexUtil.contains("hello world", "\\bworld\\b")).isTrue();
        }

        @Test
        @DisplayName("文本不包含匹配内容时返回 false（String regex）")
        void shouldReturnFalseWhenTextNotContainsMatch() {
            assertThat(RegexUtil.contains("hello world", "\\bjava\\b")).isFalse();
        }

        @Test
        @DisplayName("文本为空时返回 false（String regex）")
        void shouldReturnFalseWhenTextIsBlank() {
            assertThat(RegexUtil.contains("", "\\w+")).isFalse();
        }

        @Test
        @DisplayName("regex 为空时返回 false（String regex）")
        void shouldReturnFalseWhenRegexIsBlank() {
            assertThat(RegexUtil.contains("hello", "")).isFalse();
        }

        @Test
        @DisplayName("文本包含匹配内容时返回 true（Pattern）")
        void shouldReturnTrueWhenTextContainsMatchWithPattern() {
            assertThat(RegexUtil.contains("abc123", Pattern.compile("\\d+"))).isTrue();
        }

        @Test
        @DisplayName("Pattern 为 null 时返回 false")
        void shouldReturnFalseWhenPatternIsNull() {
            assertThat(RegexUtil.contains("hello", (Pattern) null)).isFalse();
        }
    }

    // ============================== count ==============================

    @Nested
    @DisplayName("count 测试")
    class CountTest {

        @Test
        @DisplayName("正确统计匹配次数（String regex）")
        void shouldCountMatchesCorrectly() {
            assertThat(RegexUtil.count("aababc", "a")).isEqualTo(3);
        }

        @Test
        @DisplayName("无匹配时返回 0（String regex）")
        void shouldReturnZeroWhenNoMatch() {
            assertThat(RegexUtil.count("hello", "\\d")).isEqualTo(0);
        }

        @Test
        @DisplayName("文本为空时返回 0（String regex）")
        void shouldReturnZeroWhenTextIsBlank() {
            assertThat(RegexUtil.count("", "\\w+")).isEqualTo(0);
        }

        @Test
        @DisplayName("正确统计匹配次数（Pattern）")
        void shouldCountMatchesCorrectlyWithPattern() {
            assertThat(RegexUtil.count("abc123def456", Pattern.compile("\\d+"))).isEqualTo(2);
        }
    }

    // ============================== escape ==============================

    @Nested
    @DisplayName("escape 测试")
    class EscapeTest {

        @Test
        @DisplayName("转义正则关键字符（CharSequence）")
        void shouldEscapeRegexSpecialCharsInString() {
            String result = RegexUtil.escape("a.b*c");
            assertThat(result).isEqualTo("a\\.b\\*c");
        }

        @Test
        @DisplayName("无需转义的文���原样返回")
        void shouldReturnUnchangedTextWithNoSpecialChars() {
            String result = RegexUtil.escape("hello");
            assertThat(result).isEqualTo("hello");
        }

        @Test
        @DisplayName("转义正则关键字符（char）")
        void shouldEscapeRegexSpecialChar() {
            String result = RegexUtil.escape('.');
            assertThat(result).isEqualTo("\\.");
        }

        @Test
        @DisplayName("普通字符原样返回（char）")
        void shouldReturnNormalCharUnchanged() {
            String result = RegexUtil.escape('a');
            assertThat(result).isEqualTo("a");
        }
    }

    // ============================== extractMulti ==============================

    @Nested
    @DisplayName("extractMulti 测试")
    class ExtractMultiTest {

        @Test
        @DisplayName("从文本中提取多个分组并按模板生成字符串（String regex）")
        void shouldExtractAndFormatMultiGroups() {
            String result = RegexUtil.extractMulti("2013年5月", "(.*?)年(.*?)月", "$1-$2");
            assertThat(result).isEqualTo("2013-5");
        }

        @Test
        @DisplayName("无匹配时返回 null（String regex）")
        void shouldReturnNullWhenNoMatch() {
            String result = RegexUtil.extractMulti("hello", "(\\d+)", "$1");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("content 为 null 时返回 null（String regex）")
        void shouldReturnNullWhenContentIsNull() {
            String result = RegexUtil.extractMulti((Pattern)null, "(.*)", "$1");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("从文本中提取多个分组并按模板生成字符串（Pattern）")
        void shouldExtractAndFormatMultiGroupsWithPattern() {
            Pattern pattern = Pattern.compile("(.*?)年(.*?)月", Pattern.DOTALL);
            String result = RegexUtil.extractMulti(pattern, "2020年8月", "$1-$2");
            assertThat(result).isEqualTo("2020-8");
        }
    }

    // ============================== getPattern ==============================

    @Nested
    @DisplayName("getPattern 测试")
    class GetPatternTest {

        @Test
        @DisplayName("正常返回编译后的 Pattern")
        void shouldReturnCompiledPattern() {
            Pattern pattern = RegexUtil.getPattern("\\d+", 0);
            assertThat(pattern).isNotNull();
            assertThat(pattern.pattern()).isEqualTo("\\d+");
        }

        @Test
        @DisplayName("regex 为空时返回 null")
        void shouldReturnNullWhenRegexIsBlank() {
            Pattern pattern = RegexUtil.getPattern("", 0);
            assertThat(pattern).isNull();
        }

        @Test
        @DisplayName("flag 生效（CASE_INSENSITIVE）")
        void shouldRespectCaseInsensitiveFlag() {
            Pattern pattern = RegexUtil.getPattern("[a-z]+", Pattern.CASE_INSENSITIVE);
            assertThat(pattern.matcher("HELLO").matches()).isTrue();
        }
    }

    // ============================== getAll ==============================

    @Nested
    @DisplayName("getAll 测试")
    class GetAllTest {

        @Test
        @DisplayName("提取指定分组的所有匹配结果（String regex + group）")
        void shouldGetAllMatchesByGroup() {
            List<String> result = RegexUtil.getAll("abc123def456", "\\d+", 0, new ArrayList<>());
            assertThat(result).containsExactly("123", "456");
        }

        @Test
        @DisplayName("提取所有分组匹配结果（String regex，无 group 参数）")
        void shouldGetAllGroupsFromText() {
            List<String> result = RegexUtil.getAll("name:Alice,age:30", "name:(\\w+),age:(\\d+)");
            assertThat(result).containsExactly("Alice", "30");
        }

        @Test
        @DisplayName("无匹配时返回 null（Pattern + group）")
        void shouldReturnNullWhenNoMatchWithPattern() {
            List<String> result = RegexUtil.getAll("hello", Pattern.compile("\\d+"), 0, new ArrayList<>());
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("getAllGroups 包含 group0 时返回完整匹配")
        void shouldIncludeGroup0WhenWithGroup0IsTrue() {
            List<String> result = RegexUtil.getAllGroups("2023-06", Pattern.compile("(\\d{4})-(\\d{2})"), true);
            assertThat(result).contains("2023-06", "2023", "06");
        }

        @Test
        @DisplayName("getAllGroups 不含 group0 时只返回子分组")
        void shouldExcludeGroup0WhenWithGroup0IsFalse() {
            List<String> result = RegexUtil.getAllGroups("2023-06", Pattern.compile("(\\d{4})-(\\d{2})"), false);
            assertThat(result).containsExactly("2023", "06");
        }

        @Test
        @DisplayName("getAllInJson 提取 JSON key 对应的所有值")
        void shouldExtractAllValuesFromJson() {
            List<String> result = RegexUtil.getAllInJson("{\"name\":\"Alice\"}", "name");
            assertThat(result).contains("Alice");
        }
    }

    // ============================== getFirst / get ==============================

    @Nested
    @DisplayName("getFirst / get 测试")
    class GetFirstAndGetTest {

        @Test
        @DisplayName("getFirst 返回第一个匹配结果")
        void shouldGetFirstMatch() {
            String result = RegexUtil.getFirst("abc123def", "\\d+");
            assertThat(result).isEqualTo("123");
        }

        @Test
        @DisplayName("getFirst 无匹配时返回 null")
        void shouldReturnNullWhenNoFirstMatch() {
            String result = RegexUtil.getFirst("hello", "\\d+");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("get 返回指定分组内容（String regex）")
        void shouldGetSpecifiedGroup() {
            String result = RegexUtil.get("2023-06-15", "(\\d{4})-(\\d{2})-(\\d{2})", 2);
            assertThat(result).isEqualTo("06");
        }

        @Test
        @DisplayName("get 文本为空时返回 null（String regex）")
        void shouldReturnNullWhenTextIsBlank() {
            String result = RegexUtil.get("", "\\d+", 0);
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("get 返回指定分组内容（Pattern）")
        void shouldGetSpecifiedGroupWithPattern() {
            String result = RegexUtil.get("hello world", Pattern.compile("(\\w+)\\s(\\w+)"), 2);
            assertThat(result).isEqualTo("world");
        }
    }

    // ============================== matches ==============================

    @Nested
    @DisplayName("matches 测试")
    class MatchesTest {

        @Test
        @DisplayName("完全匹配时返回 true（String regex）")
        void shouldReturnTrueWhenFullyMatches() {
            assertThat(RegexUtil.matches("12345", "\\d+")).isTrue();
        }

        @Test
        @DisplayName("部分匹配时返回 false（String regex）")
        void shouldReturnFalseWhenPartialMatch() {
            assertThat(RegexUtil.matches("123abc", "\\d+")).isFalse();
        }

        @Test
        @DisplayName("文本为空时返回 false")
        void shouldReturnFalseWhenTextIsBlank() {
            assertThat(RegexUtil.matches("", "\\d+")).isFalse();
        }

        @Test
        @DisplayName("带 flag 时匹配忽略大小写（String regex + flag）")
        void shouldMatchCaseInsensitivelyWithFlag() {
            assertThat(RegexUtil.matches("HELLO", "[a-z]+", Pattern.CASE_INSENSITIVE)).isTrue();
        }

        @Test
        @DisplayName("Pattern 为 null 时返回 false")
        void shouldReturnFalseWhenPatternIsNull() {
            assertThat(RegexUtil.matches("hello", (Pattern) null)).isFalse();
        }
    }

    // ============================== removeAll ==============================

    @Nested
    @DisplayName("removeAll 测试")
    class RemoveAllTest {

        @Test
        @DisplayName("移除所有匹配子串（String regex）")
        void shouldRemoveAllMatchingSubstrings() {
            String result = RegexUtil.removeAll("ABCabc123", "[a-z]");
            assertThat(result).isEqualTo("ABC123");
        }

        @Test
        @DisplayName("移除所有匹配子串（Pattern）")
        void shouldRemoveAllMatchingSubstringsWithPattern() {
            String result = RegexUtil.removeAll("ABCabc123", Pattern.compile("[a-z]"));
            assertThat(result).isEqualTo("ABC123");
        }

        @Test
        @DisplayName("无匹配时原文返回（String regex）")
        void shouldReturnOriginalTextWhenNoMatch() {
            String result = RegexUtil.removeAll("HELLO", "[a-z]");
            assertThat(result).isEqualTo("HELLO");
        }
    }

    // ============================== removeFirst ==============================

    @Nested
    @DisplayName("removeFirst 测试")
    class RemoveFirstTest {

        @Test
        @DisplayName("移除第一个匹配子串（String regex）")
        void shouldRemoveFirstMatchingSubstring() {
            String result = RegexUtil.removeFirst("ABCabc123abc", "[a-z]+");
            assertThat(result).isEqualTo("ABC123abc");
        }

        @Test
        @DisplayName("移除第一个匹配子串（Pattern）")
        void shouldRemoveFirstMatchingSubstringWithPattern() {
            String result = RegexUtil.removeFirst("ABCabc123abc", Pattern.compile("[a-z]+"));
            assertThat(result).isEqualTo("ABC123abc");
        }
    }

    // ============================== replaceAll ==============================

    @Nested
    @DisplayName("replaceAll 测试")
    class ReplaceAllTest {

        @Test
        @DisplayName("替换所有匹配子串（String regex）")
        void shouldReplaceAllMatchingSubstrings() {
            String result = RegexUtil.replaceAll("ABCabc123", "[a-z]", "_");
            assertThat(result).isEqualTo("ABC___123");
        }

        @Test
        @DisplayName("替换所有匹配子串（Pattern）")
        void shouldReplaceAllMatchingSubstringsWithPattern() {
            String result = RegexUtil.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_");
            assertThat(result).isEqualTo("ABC___123");
        }

        @Test
        @DisplayName("文本为空时原文返回（String regex）")
        void shouldReturnOriginalTextWhenTextIsBlank() {
            String result = RegexUtil.replaceAll("", "[a-z]", "_");
            assertThat(result).isEqualTo("");
        }

        @Test
        @DisplayName("按顺序替换所有匹配子串（String regex + List）")
        void shouldReplaceAllInOrderWithList() {
            List<String> values = Arrays.asList("X", "Y", "Z");
            String result = RegexUtil.replaceAll("a1b2c3", "\\d", values);
            assertThat(result).isEqualTo("aXbYcZ");
        }

        @Test
        @DisplayName("按顺序替换所有匹配子串（Pattern + List）")
        void shouldReplaceAllInOrderWithPatternAndList() {
            List<String> values = Arrays.asList("ONE", "TWO");
            String result = RegexUtil.replaceAll("num1num2", Pattern.compile("\\d"), values);
            assertThat(result).isEqualTo("numONEnumTWO");
        }
    }

    // ============================== replaceFirst ==============================

    @Nested
    @DisplayName("replaceFirst 测试")
    class ReplaceFirstTest {

        @Test
        @DisplayName("替换第一个匹配子串（String regex）")
        void shouldReplaceFirstMatchingSubstring() {
            String result = RegexUtil.replaceFirst("ABCabc123abc", "[a-z]+", "_");
            assertThat(result).isEqualTo("ABC_123abc");
        }

        @Test
        @DisplayName("替换第一个匹配子串（Pattern）")
        void shouldReplaceFirstMatchingSubstringWithPattern() {
            String result = RegexUtil.replaceFirst("ABCabc123abc", Pattern.compile("[a-z]+"), "_");
            assertThat(result).isEqualTo("ABC_123abc");
        }

        @Test
        @DisplayName("文本为空时原文返回（String regex）")
        void shouldReturnOriginalTextWhenTextIsBlank() {
            String result = RegexUtil.replaceFirst("", "[a-z]", "_");
            assertThat(result).isEqualTo("");
        }
    }

    // ============================== removePre ==============================

    @Nested
    @DisplayName("removePre 测试")
    class RemovePreTest {

        @Test
        @DisplayName("删除匹配位置之前的内容")
        void shouldRemoveContentBeforeMatch() {
            String result = RegexUtil.removePre("prefix--content", "--");
            assertThat(result).isEqualTo("content");
        }

        @Test
        @DisplayName("无匹配时原文返回")
        void shouldReturnOriginalTextWhenNoMatch() {
            String result = RegexUtil.removePre("hello world", "\\d+");
            assertThat(result).isEqualTo("hello world");
        }

        @Test
        @DisplayName("文本为空时原文返回")
        void shouldReturnOriginalTextWhenTextIsBlank() {
            String result = RegexUtil.removePre("", "--");
            assertThat(result).isEqualTo("");
        }
    }

    // ============================== isAllChineseChar 等便捷校验方法 ==============================

    @Nested
    @DisplayName("常用校验方法测试")
    class ConvenienceValidationTest {

        @Test
        @DisplayName("isAllChineseChar: 全是汉字返回 true")
        void shouldReturnTrueForAllChineseChars() {
            assertThat(RegexUtil.isAllChineseChar("春眠不觉晓")).isTrue();
        }

        @Test
        @DisplayName("isAllChineseChar: 含标点返回 false")
        void shouldReturnFalseForChineseWithPunctuation() {
            assertThat(RegexUtil.isAllChineseChar("春眠不觉晓，")).isFalse();
        }

        @Test
        @DisplayName("isAllEnglishChar: 全是英文字母返回 true")
        void shouldReturnTrueForAllEnglishChars() {
            assertThat(RegexUtil.isAllEnglishChar("Hello")).isTrue();
        }

        @Test
        @DisplayName("isAllEnglishChar: 含数字返回 false")
        void shouldReturnFalseWhenContainsDigits() {
            assertThat(RegexUtil.isAllEnglishChar("Hello1")).isFalse();
        }

        @Test
        @DisplayName("isAllLowerEnglishChar: 全小写返回 true")
        void shouldReturnTrueForAllLowerEnglishChars() {
            assertThat(RegexUtil.isAllLowerEnglishChar("hello")).isTrue();
        }

        @Test
        @DisplayName("isAllUpperEnglishChar: 全大写返回 true")
        void shouldReturnTrueForAllUpperEnglishChars() {
            assertThat(RegexUtil.isAllUpperEnglishChar("HELLO")).isTrue();
        }

        @Test
        @DisplayName("isAllNumber: 全数字返回 true")
        void shouldReturnTrueForAllNumbers() {
            assertThat(RegexUtil.isAllNumber("12345")).isTrue();
        }

        @Test
        @DisplayName("isAllNumber: 含字母返回 false")
        void shouldReturnFalseWhenContainsLetters() {
            assertThat(RegexUtil.isAllNumber("123a5")).isFalse();
        }

        @Test
        @DisplayName("isAllWordChar: 字母数字下划线返回 true")
        void shouldReturnTrueForAllWordChars() {
            assertThat(RegexUtil.isAllWordChar("abc_123")).isTrue();
        }

        @Test
        @DisplayName("isNoneNumber: 无数字时返回 true")
        void shouldReturnTrueForNoNumbers() {
            assertThat(RegexUtil.isNoneNumber("abcDEF")).isTrue();
        }

        @Test
        @DisplayName("isNoneWordChar: 全是非单词字符返回 true")
        void shouldReturnTrueForNoneWordChars() {
            assertThat(RegexUtil.isNoneWordChar("!@#$")).isTrue();
        }

        @Test
        @DisplayName("isNDigitNumber: N位数字校验")
        void shouldValidateNDigitNumber() {
            assertThat(RegexUtil.isNDigitNumber("12345", 5)).isTrue();
            assertThat(RegexUtil.isNDigitNumber("1234", 5)).isFalse();
        }

        @Test
        @DisplayName("isLeastNDigitNumber: 至少N位数字校验")
        void shouldValidateLeastNDigitNumber() {
            assertThat(RegexUtil.isLeastNDigitNumber("123456", 5)).isTrue();
            assertThat(RegexUtil.isLeastNDigitNumber("1234", 5)).isFalse();
        }

        @Test
        @DisplayName("isMToNDigitNumber: M到N位数字校验")
        void shouldValidateMToNDigitNumber() {
            assertThat(RegexUtil.isMToNDigitNumber("1234", 3, 6)).isTrue();
            assertThat(RegexUtil.isMToNDigitNumber("12", 3, 6)).isFalse();
        }

        @Test
        @DisplayName("isValidTime: 有效时间校验")
        void shouldValidateTime() {
            assertThat(RegexUtil.isValidTime("17:06:30")).isTrue();
            assertThat(RegexUtil.isValidTime("25:00:00")).isFalse();
        }

        @Test
        @DisplayName("isValidateDate: 有效日期校验")
        void shouldValidateDate() {
            assertThat(RegexUtil.isValidateDate("2023-06-15")).isTrue();
            assertThat(RegexUtil.isValidateDate("2023-13-01")).isFalse();
        }

        @Test
        @DisplayName("isValidateEmail: 有效邮箱校验")
        void shouldValidateEmail() {
            assertThat(RegexUtil.isValidateEmail("user@example.com")).isTrue();
            assertThat(RegexUtil.isValidateEmail("invalid-email")).isFalse();
        }

        @Test
        @DisplayName("isValidateIdCard15: 15位身份证校验")
        void shouldValidateIdCard15() {
            assertThat(RegexUtil.isValidateIdCard15("110001700101031")).isTrue();
            assertThat(RegexUtil.isValidateIdCard15("110001701501031")).isFalse();
        }

        @Test
        @DisplayName("isValidateIdCard18: 18位身份证校验")
        void shouldValidateIdCard18() {
            assertThat(RegexUtil.isValidateIdCard18("110001199001010310")).isTrue();
            assertThat(RegexUtil.isValidateIdCard18("990000199001010310")).isFalse();
        }

        @Test
        @DisplayName("isValidateIpv4: IPv4 校验")
        void shouldValidateIpv4() {
            assertThat(RegexUtil.isValidateIpv4("192.168.1.1")).isTrue();
            assertThat(RegexUtil.isValidateIpv4("256.0.0.1")).isFalse();
        }

        @Test
        @DisplayName("isValidateMobile: 手机号码校验")
        void shouldValidateMobile() {
            assertThat(RegexUtil.isValidateMobile("13812345678")).isTrue();
            assertThat(RegexUtil.isValidateMobile("12312345678")).isFalse();
        }

        @Test
        @DisplayName("isValidateUrl: URL 校验")
        void shouldValidateUrl() {
            assertThat(RegexUtil.isValidateUrl("http://www.example.com")).isTrue();
            assertThat(RegexUtil.isValidateUrl("not-a-url")).isFalse();
        }

        @Test
        @DisplayName("isValidateUsername: 用户名校验")
        void shouldValidateUsername() {
            assertThat(RegexUtil.isValidateUsername("aUser123")).isTrue();
            assertThat(RegexUtil.isValidateUsername("123user")).isFalse();
        }

        @Test
        @DisplayName("checkMatches(String, String): 正则匹配校验")
        void shouldCheckMatchesWithStringRegex() {
            assertThat(RegexUtil.checkMatches("\\d+", "12345")).isTrue();
            assertThat(RegexUtil.checkMatches("\\d+", "abc")).isFalse();
        }
    }

}
