package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ValidatorUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-09
 */
@DisplayName("ValidatorUtil 单元测试")
class ValidatorUtilTest {

    // ============================== 字符校验 ==============================

    @Nested
    @DisplayName("hasForbiddenChar 测试")
    class HasForbiddenCharTest {

        @Test
        @DisplayName("文本中包含禁止字符时返回 true")
        void shouldReturnTrueWhenTextContainsForbiddenChar() {
            assertThat(ValidatorUtil.hasForbiddenChar("hello<world>", "<", ">")).isTrue();
        }

        @Test
        @DisplayName("文本中不含禁止字符时返回 false")
        void shouldReturnFalseWhenTextNotContainsForbiddenChar() {
            assertThat(ValidatorUtil.hasForbiddenChar("hello world", "<", ">")).isFalse();
        }

        @Test
        @DisplayName("文本为空时返回 false")
        void shouldReturnFalseWhenTextIsBlank() {
            assertThat(ValidatorUtil.hasForbiddenChar("", "<")).isFalse();
        }

        @Test
        @DisplayName("forbiddenChars 为 null 时返回 false")
        void shouldReturnFalseWhenForbiddenCharsIsNull() {
            assertThat(ValidatorUtil.hasForbiddenChar("hello", (CharSequence[]) null)).isFalse();
        }
    }

    @Nested
    @DisplayName("hasChineseChar 测试")
    class HasChineseCharTest {

        @Test
        @DisplayName("包含中文字符时返回 true")
        void shouldReturnTrueWhenContainsChinese() {
            assertThat(ValidatorUtil.hasChineseChar("hello你好")).isTrue();
        }

        @Test
        @DisplayName("不含中文字符时返回 false")
        void shouldReturnFalseWhenNotContainsChinese() {
            assertThat(ValidatorUtil.hasChineseChar("hello world")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllChineseChar 测试")
    class IsAllChineseCharTest {

        @Test
        @DisplayName("全是汉字时返回 true")
        void shouldReturnTrueForAllChineseChars() {
            assertThat(ValidatorUtil.isAllChineseChar("春眠不觉晓")).isTrue();
        }

        @Test
        @DisplayName("含有非汉字字符时返回 false")
        void shouldReturnFalseWhenContainsNonChinese() {
            assertThat(ValidatorUtil.isAllChineseChar("hello你好")).isFalse();
        }

        @Test
        @DisplayName("空字符串时返回 false")
        void shouldReturnFalseForEmptyString() {
            assertThat(ValidatorUtil.isAllChineseChar("")).isFalse();
        }
    }

    @Nested
    @DisplayName("hasEnglishChar 测试")
    class HasEnglishCharTest {

        @Test
        @DisplayName("包含英文字符时返回 true")
        void shouldReturnTrueWhenContainsEnglish() {
            assertThat(ValidatorUtil.hasEnglishChar("hello123")).isTrue();
        }

        @Test
        @DisplayName("不含英文字符时返回 false")
        void shouldReturnFalseWhenNotContainsEnglish() {
            assertThat(ValidatorUtil.hasEnglishChar("12345")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllEnglishChar 测试")
    class IsAllEnglishCharTest {

        @Test
        @DisplayName("全是英文字母时返回 true")
        void shouldReturnTrueForAllEnglishChars() {
            assertThat(ValidatorUtil.isAllEnglishChar("Hello")).isTrue();
        }

        @Test
        @DisplayName("含有数字时返回 false")
        void shouldReturnFalseWhenContainsDigit() {
            assertThat(ValidatorUtil.isAllEnglishChar("Hello1")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllGeneralAndChineseChar 测试")
    class IsAllGeneralAndChineseCharTest {

        @Test
        @DisplayName("由字母、数字、下划线和汉字组成时返回 true")
        void shouldReturnTrueForGeneralAndChineseChars() {
            assertThat(ValidatorUtil.isAllGeneralAndChineseChar("abc_123你好")).isTrue();
        }

        @Test
        @DisplayName("含有特殊字符时返回 false")
        void shouldReturnFalseWhenContainsSpecialChars() {
            assertThat(ValidatorUtil.isAllGeneralAndChineseChar("abc!@#")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllGeneralChar 测试")
    class IsAllGeneralCharTest {

        @Test
        @DisplayName("全是单词字符（字母、数字、下划线）时返回 true")
        void shouldReturnTrueForGeneralChars() {
            assertThat(ValidatorUtil.isAllGeneralChar("abc_123")).isTrue();
        }

        @Test
        @DisplayName("含有中文时返回 false")
        void shouldReturnFalseWhenContainsChinese() {
            assertThat(ValidatorUtil.isAllGeneralChar("abc_123你好")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllLowerEnglishChar 测试")
    class IsAllLowerEnglishCharTest {

        @Test
        @DisplayName("全是小写字母时返回 true")
        void shouldReturnTrueForAllLowerCase() {
            assertThat(ValidatorUtil.isAllLowerEnglishChar("hello")).isTrue();
        }

        @Test
        @DisplayName("含有大写字母时返回 false")
        void shouldReturnFalseWhenContainsUpperCase() {
            assertThat(ValidatorUtil.isAllLowerEnglishChar("Hello")).isFalse();
        }
    }

    @Nested
    @DisplayName("isAllUpperEnglishChar 测试")
    class IsAllUpperEnglishCharTest {

        @Test
        @DisplayName("全是大写字母时返回 true")
        void shouldReturnTrueForAllUpperCase() {
            assertThat(ValidatorUtil.isAllUpperEnglishChar("HELLO")).isTrue();
        }

        @Test
        @DisplayName("含有小写字母时返回 false")
        void shouldReturnFalseWhenContainsLowerCase() {
            assertThat(ValidatorUtil.isAllUpperEnglishChar("Hello")).isFalse();
        }
    }

    @Nested
    @DisplayName("isCitizenId15 测试")
    class IsCitizenId15Test {

        @Test
        @DisplayName("有效15位身份证号返回 true")
        void shouldReturnTrueForValid15DigitId() {
            assertThat(ValidatorUtil.isCitizenId15("110001700101031")).isTrue();
        }

        @Test
        @DisplayName("月份超出范围时返回 false")
        void shouldReturnFalseWhenMonthOutOfRange() {
            assertThat(ValidatorUtil.isCitizenId15("110001701501031")).isFalse();
        }

        @Test
        @DisplayName("位数不足时返回 false")
        void shouldReturnFalseForInsufficientDigits() {
            assertThat(ValidatorUtil.isCitizenId15("11000170010103")).isFalse();
        }
    }

    @Nested
    @DisplayName("isCitizenId18 测试")
    class IsCitizenId18Test {

        @Test
        @DisplayName("有效18位身份证号返回 true")
        void shouldReturnTrueForValid18DigitId() {
            assertThat(ValidatorUtil.isCitizenId18("110001199001010310")).isTrue();
        }

        @Test
        @DisplayName("末尾为 X 的有效身份证号返回 true")
        void shouldReturnTrueForIdEndingWithX() {
            assertThat(ValidatorUtil.isCitizenId18("11000119900101015X")).isTrue();
        }

        @Test
        @DisplayName("非法地区码时返回 false")
        void shouldReturnFalseForInvalidAreaCode() {
            assertThat(ValidatorUtil.isCitizenId18("990000199001010310")).isFalse();
        }

        @Test
        @DisplayName("非法月份时返回 false")
        void shouldReturnFalseForInvalidMonth() {
            assertThat(ValidatorUtil.isCitizenId18("110001199013010310")).isFalse();
        }
    }

    // ============================== 数字校验 ==============================

    @Nested
    @DisplayName("isDate 测试")
    class IsDateTest {

        @Test
        @DisplayName("yyyy-MM-dd 格式有效日期返回 true")
        void shouldReturnTrueForValidDate() {
            assertThat(ValidatorUtil.isDate("2023-06-15")).isTrue();
        }

        @Test
        @DisplayName("yyyy/MM/dd 格式有效日期返回 true")
        void shouldReturnTrueForSlashDelimitedDate() {
            assertThat(ValidatorUtil.isDate("2016/01/01")).isTrue();
        }

        @Test
        @DisplayName("闰年2月29日返回 true")
        void shouldReturnTrueForLeapYearDate() {
            assertThat(ValidatorUtil.isDate("2000-02-29")).isTrue();
        }

        @Test
        @DisplayName("平年2月29日返回 false")
        void shouldReturnFalseForNonLeapYearFeb29() {
            assertThat(ValidatorUtil.isDate("2001-02-29")).isFalse();
        }

        @Test
        @DisplayName("月份超出范围时返回 false")
        void shouldReturnFalseWhenMonthOutOfRange() {
            assertThat(ValidatorUtil.isDate("2016/13/1")).isFalse();
        }

        @Test
        @DisplayName("日期超出范围时返回 false")
        void shouldReturnFalseWhenDayOutOfRange() {
            assertThat(ValidatorUtil.isDate("2016/12/32")).isFalse();
        }
    }

    @Nested
    @DisplayName("isDigitNumber 测试")
    class IsDigitNumberTest {

        @Test
        @DisplayName("文本为指定位数的数字时返回 true")
        void shouldReturnTrueForNDigitNumber() {
            assertThat(ValidatorUtil.isDigitNumber("12345", 5)).isTrue();
        }

        @Test
        @DisplayName("位数不匹配时返回 false")
        void shouldReturnFalseWhenDigitCountMismatch() {
            assertThat(ValidatorUtil.isDigitNumber("1234", 5)).isFalse();
        }

        @Test
        @DisplayName("含有非数字字符时返回 false")
        void shouldReturnFalseWhenContainsNonDigit() {
            assertThat(ValidatorUtil.isDigitNumber("1234a", 5)).isFalse();
        }
    }

    @Nested
    @DisplayName("isEmail 测试")
    class IsEmailTest {

        @Test
        @DisplayName("有效邮箱地址返回 true")
        void shouldReturnTrueForValidEmail() {
            assertThat(ValidatorUtil.isEmail("user@example.com")).isTrue();
        }

        @Test
        @DisplayName("含下划线和点的邮箱返回 true")
        void shouldReturnTrueForEmailWithUnderscoreAndDot() {
            assertThat(ValidatorUtil.isEmail("he_llo@worl.d.com")).isTrue();
        }

        @Test
        @DisplayName("无效邮箱格式返回 false")
        void shouldReturnFalseForInvalidEmail() {
            assertThat(ValidatorUtil.isEmail("invalid-email")).isFalse();
        }

        @Test
        @DisplayName("域名含下划线的邮箱返回 false")
        void shouldReturnFalseForEmailWithUnderscoreInDomain() {
            assertThat(ValidatorUtil.isEmail("hello@worl_d.com")).isFalse();
        }
    }

    @Nested
    @DisplayName("isFloat 测试")
    class IsFloatTest {

        @Test
        @DisplayName("正浮点数返回 true")
        void shouldReturnTrueForPositiveFloat() {
            assertThat(ValidatorUtil.isFloat("3.14")).isTrue();
        }

        @Test
        @DisplayName("负浮点数返回 true")
        void shouldReturnTrueForNegativeFloat() {
            assertThat(ValidatorUtil.isFloat("-3.14")).isTrue();
        }

        @Test
        @DisplayName("整数返回 false")
        void shouldReturnFalseForInteger() {
            assertThat(ValidatorUtil.isFloat("100")).isFalse();
        }

        @Test
        @DisplayName("非数字字符返回 false")
        void shouldReturnFalseForNonNumericString() {
            assertThat(ValidatorUtil.isFloat("abc")).isFalse();
        }
    }

    @Nested
    @DisplayName("isHttpUrl 测试")
    class IsHttpUrlTest {

        @Test
        @DisplayName("http URL 返回 true")
        void shouldReturnTrueForHttpUrl() {
            assertThat(ValidatorUtil.isHttpUrl("http://www.example.com")).isTrue();
        }

        @Test
        @DisplayName("https URL 返回 true")
        void shouldReturnTrueForHttpsUrl() {
            assertThat(ValidatorUtil.isHttpUrl("https://www.example.com/path")).isTrue();
        }

        @Test
        @DisplayName("非 URL 字符串返回 false")
        void shouldReturnFalseForNonUrl() {
            assertThat(ValidatorUtil.isHttpUrl("not-a-url")).isFalse();
        }

        @Test
        @DisplayName("无顶级域名的 URL 返回 false")
        void shouldReturnFalseForUrlWithoutTld() {
            assertThat(ValidatorUtil.isHttpUrl("http://www.foufos")).isFalse();
        }
    }

    @Nested
    @DisplayName("isInteger 测试")
    class IsIntegerTest {

        @Test
        @DisplayName("正整数返回 true")
        void shouldReturnTrueForPositiveInteger() {
            assertThat(ValidatorUtil.isInteger("12345")).isTrue();
        }

        @Test
        @DisplayName("负整数返回 true")
        void shouldReturnTrueForNegativeInteger() {
            assertThat(ValidatorUtil.isInteger("-42")).isTrue();
        }

        @Test
        @DisplayName("浮点数返回 false")
        void shouldReturnFalseForFloat() {
            assertThat(ValidatorUtil.isInteger("3.14")).isFalse();
        }

        @Test
        @DisplayName("字母字符串返回 false")
        void shouldReturnFalseForAlphabetic() {
            assertThat(ValidatorUtil.isInteger("abc")).isFalse();
        }
    }

    @Nested
    @DisplayName("isIpv4 测试")
    class IsIpv4Test {

        @Test
        @DisplayName("有效 IPv4 地址返回 true")
        void shouldReturnTrueForValidIpv4() {
            assertThat(ValidatorUtil.isIpv4("192.168.1.1")).isTrue();
            assertThat(ValidatorUtil.isIpv4("0.0.0.0")).isTrue();
            assertThat(ValidatorUtil.isIpv4("255.255.255.255")).isTrue();
        }

        @Test
        @DisplayName("超出范围的地址返回 false")
        void shouldReturnFalseForOutOfRangeIpv4() {
            assertThat(ValidatorUtil.isIpv4("256.0.0.1")).isFalse();
        }

        @Test
        @DisplayName("不足四段的地址返回 false")
        void shouldReturnFalseForInsufficientSegments() {
            assertThat(ValidatorUtil.isIpv4("10.10.10")).isFalse();
        }
    }

    @Nested
    @DisplayName("isIpv6 测试")
    class IsIpv6Test {

        @Test
        @DisplayName("标准 IPv6 地址返回 true")
        void shouldReturnTrueForStandardIpv6() {
            assertThat(ValidatorUtil.isIpv6("1:2:3:4:5:6:7:8")).isTrue();
        }

        @Test
        @DisplayName("压缩形式 IPv6 地址返回 true")
        void shouldReturnTrueForCompressedIpv6() {
            assertThat(ValidatorUtil.isIpv6("::1")).isTrue();
            assertThat(ValidatorUtil.isIpv6("fe80::1")).isTrue();
        }

        @Test
        @DisplayName("非法格式返回 false")
        void shouldReturnFalseForInvalidIpv6() {
            assertThat(ValidatorUtil.isIpv6("1.2.3.4.5.6.7.8")).isFalse();
            assertThat(ValidatorUtil.isIpv6("1::2::3")).isFalse();
        }
    }

    @Nested
    @DisplayName("isLeastDigitNumber 测试")
    class IsLeastDigitNumberTest {

        @Test
        @DisplayName("位数大于等于 N 时返回 true")
        void shouldReturnTrueWhenDigitsAtLeastN() {
            assertThat(ValidatorUtil.isLeastDigitNumber("123456", 5)).isTrue();
            assertThat(ValidatorUtil.isLeastDigitNumber("12345", 5)).isTrue();
        }

        @Test
        @DisplayName("位数不足 N 时返回 false")
        void shouldReturnFalseWhenDigitsLessThanN() {
            assertThat(ValidatorUtil.isLeastDigitNumber("1234", 5)).isFalse();
        }
    }

    @Nested
    @DisplayName("isLengthInRange 测试")
    class IsLengthInRangeTest {

        @Test
        @DisplayName("长度在范围内时返回 true")
        void shouldReturnTrueWhenLengthInRange() {
            assertThat(ValidatorUtil.isLengthInRange("hello", 3, 10)).isTrue();
        }

        @Test
        @DisplayName("长度等于最小值时返回 true")
        void shouldReturnTrueWhenLengthEqualsMin() {
            assertThat(ValidatorUtil.isLengthInRange("abc", 3, 10)).isTrue();
        }

        @Test
        @DisplayName("长度超出范围时返回 false")
        void shouldReturnFalseWhenLengthOutOfRange() {
            assertThat(ValidatorUtil.isLengthInRange("hello world!", 3, 10)).isFalse();
        }

        @Test
        @DisplayName("长度不足最小值时返回 false")
        void shouldReturnFalseWhenLengthBelowMin() {
            assertThat(ValidatorUtil.isLengthInRange("ab", 3, 10)).isFalse();
        }
    }

    @Nested
    @DisplayName("isMac 测试")
    class IsMacTest {

        @Test
        @DisplayName("有效 MAC 地址返回 true")
        void shouldReturnTrueForValidMac() {
            assertThat(ValidatorUtil.isMac("00:1A:2B:3C:4D:5E")).isTrue();
        }

        @Test
        @DisplayName("无效 MAC 地址返回 false")
        void shouldReturnFalseForInvalidMac() {
            assertThat(ValidatorUtil.isMac("00:1A:2B:3C:4D")).isFalse();
        }
    }

    @Nested
    @DisplayName("isMarkdownImageTag 测试")
    class IsMarkdownImageTagTest {

        @Test
        @DisplayName("有效 Markdown 图片标签返回 true")
        void shouldReturnTrueForValidMarkdownImageTag() {
            assertThat(ValidatorUtil.isMarkdownImageTag("![alt](http://example.com/img.png)")).isTrue();
        }

        @Test
        @DisplayName("普通文本返回 false")
        void shouldReturnFalseForPlainText() {
            assertThat(ValidatorUtil.isMarkdownImageTag("hello world")).isFalse();
        }
    }

    @Nested
    @DisplayName("isMobile 测试")
    class IsMobileTest {

        @Test
        @DisplayName("有效手机号码返回 true")
        void shouldReturnTrueForValidMobile() {
            assertThat(ValidatorUtil.isMobile("13812345678")).isTrue();
            assertThat(ValidatorUtil.isMobile("15912345678")).isTrue();
        }

        @Test
        @DisplayName("非法手机号码返回 false")
        void shouldReturnFalseForInvalidMobile() {
            assertThat(ValidatorUtil.isMobile("12312345678")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNegativeFloat 测试")
    class IsNegativeFloatTest {

        @Test
        @DisplayName("负浮点数返回 true")
        void shouldReturnTrueForNegativeFloat() {
            assertThat(ValidatorUtil.isNegativeFloat("-3.14")).isTrue();
        }

        @Test
        @DisplayName("正浮点数返回 false")
        void shouldReturnFalseForPositiveFloat() {
            assertThat(ValidatorUtil.isNegativeFloat("3.14")).isFalse();
        }

        @Test
        @DisplayName("零返回 false")
        void shouldReturnFalseForZero() {
            assertThat(ValidatorUtil.isNegativeFloat("0.0")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNegativeInteger 测试")
    class IsNegativeIntegerTest {

        @Test
        @DisplayName("负整数返回 true")
        void shouldReturnTrueForNegativeInteger() {
            assertThat(ValidatorUtil.isNegativeInteger("-42")).isTrue();
        }

        @Test
        @DisplayName("正整数返回 false")
        void shouldReturnFalseForPositiveInteger() {
            assertThat(ValidatorUtil.isNegativeInteger("42")).isFalse();
        }

        @Test
        @DisplayName("零返回 false")
        void shouldReturnFalseForZero() {
            assertThat(ValidatorUtil.isNegativeInteger("0")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNoneGeneralChar 测试")
    class IsNoneGeneralCharTest {

        @Test
        @DisplayName("全是非单词字符时返回 true")
        void shouldReturnTrueForNoneGeneralChars() {
            assertThat(ValidatorUtil.isNoneGeneralChar("!@#$%")).isTrue();
        }

        @Test
        @DisplayName("含有字母时返回 false")
        void shouldReturnFalseWhenContainsLetter() {
            assertThat(ValidatorUtil.isNoneGeneralChar("!@a#")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNoneNumberChar 测试")
    class IsNoneNumberCharTest {

        @Test
        @DisplayName("不含数字时返回 true")
        void shouldReturnTrueForNoneNumberChars() {
            assertThat(ValidatorUtil.isNoneNumberChar("abcDEF")).isTrue();
        }

        @Test
        @DisplayName("含有数字时返回 false")
        void shouldReturnFalseWhenContainsDigit() {
            assertThat(ValidatorUtil.isNoneNumberChar("abc1")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNotPositiveFloat 测试")
    class IsNotPositiveFloatTest {

        @Test
        @DisplayName("负浮点数时返回 true（非正）")
        void shouldReturnTrueForNegativeFloat() {
            assertThat(ValidatorUtil.isNotPositiveFloat("-3.14")).isTrue();
        }

        @Test
        @DisplayName("正浮点数时返回 false（非正为 false）")
        void shouldReturnFalseForPositiveFloat() {
            assertThat(ValidatorUtil.isNotPositiveFloat("3.14")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNotNegativeFloat 测试")
    class IsNotNegativeFloatTest {

        @Test
        @DisplayName("正浮点数时返回 true（非负）")
        void shouldReturnTrueForPositiveFloat() {
            assertThat(ValidatorUtil.isNotNegativeFloat("3.14")).isTrue();
        }

        @Test
        @DisplayName("零时返回 true（非负）")
        void shouldReturnTrueForZero() {
            assertThat(ValidatorUtil.isNotNegativeFloat("0.0")).isTrue();
        }

        @Test
        @DisplayName("负浮点数时返回 false")
        void shouldReturnFalseForNegativeFloat() {
            assertThat(ValidatorUtil.isNotNegativeFloat("-3.14")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNotPositiveInteger 测试")
    class IsNotPositiveIntegerTest {

        @Test
        @DisplayName("负整数时返回 true（非正）")
        void shouldReturnTrueForNegativeInteger() {
            assertThat(ValidatorUtil.isNotPositiveInteger("-10")).isTrue();
        }

        @Test
        @DisplayName("正整数时返回 false（非正为 false）")
        void shouldReturnFalseForPositiveInteger() {
            assertThat(ValidatorUtil.isNotPositiveInteger("10")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNotNegativeInteger 测试")
    class IsNotNegativeIntegerTest {

        @Test
        @DisplayName("正整数时返回 true（非负）")
        void shouldReturnTrueForPositiveInteger() {
            assertThat(ValidatorUtil.isNotNegativeInteger("10")).isTrue();
        }

        @Test
        @DisplayName("零时返回 true（非负）")
        void shouldReturnTrueForZero() {
            assertThat(ValidatorUtil.isNotNegativeInteger("0")).isTrue();
        }

        @Test
        @DisplayName("负整数时返回 false")
        void shouldReturnFalseForNegativeInteger() {
            assertThat(ValidatorUtil.isNotNegativeInteger("-10")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNumber 测试")
    class IsNumberTest {

        @Test
        @DisplayName("纯数字字符串返回 true")
        void shouldReturnTrueForPureNumber() {
            assertThat(ValidatorUtil.isNumber("12345")).isTrue();
        }

        @Test
        @DisplayName("含字母的字符串返回 false")
        void shouldReturnFalseWhenContainsLetter() {
            assertThat(ValidatorUtil.isNumber("123a5")).isFalse();
        }
    }

    @Nested
    @DisplayName("isPhone 测试")
    class IsPhoneTest {

        @Test
        @DisplayName("有效大陆固话号码返回 true（带区号）")
        void shouldReturnTrueForValidPhone() {
            assertThat(ValidatorUtil.isPhone("01012345678")).isTrue();
        }

        @Test
        @DisplayName("不带区号的固话返回 false")
        void shouldReturnFalseForPhoneWithoutAreaCode() {
            assertThat(ValidatorUtil.isPhone("12345678")).isFalse();
        }
    }

    @Nested
    @DisplayName("isPlateNumber 测试")
    class IsPlateNumberTest {

        @Test
        @DisplayName("有效车牌号返回 true")
        void shouldReturnTrueForValidPlateNumber() {
            assertThat(ValidatorUtil.isPlateNumber("京A12345")).isTrue();
        }

        @Test
        @DisplayName("无效车牌号返回 false")
        void shouldReturnFalseForInvalidPlateNumber() {
            assertThat(ValidatorUtil.isPlateNumber("123456")).isFalse();
        }
    }

    @Nested
    @DisplayName("isPositiveFloat 测试")
    class IsPositiveFloatTest {

        @Test
        @DisplayName("正浮点数返回 true")
        void shouldReturnTrueForPositiveFloat() {
            assertThat(ValidatorUtil.isPositiveFloat("3.14")).isTrue();
        }

        @Test
        @DisplayName("负浮点数返回 false")
        void shouldReturnFalseForNegativeFloat() {
            assertThat(ValidatorUtil.isPositiveFloat("-3.14")).isFalse();
        }

        @Test
        @DisplayName("零返回 false")
        void shouldReturnFalseForZero() {
            assertThat(ValidatorUtil.isPositiveFloat("0.0")).isFalse();
        }
    }

    @Nested
    @DisplayName("isPositiveInteger 测试")
    class IsPositiveIntegerTest {

        @Test
        @DisplayName("正整数返回 true")
        void shouldReturnTrueForPositiveInteger() {
            assertThat(ValidatorUtil.isPositiveInteger("42")).isTrue();
        }

        @Test
        @DisplayName("负整数返回 false")
        void shouldReturnFalseForNegativeInteger() {
            assertThat(ValidatorUtil.isPositiveInteger("-42")).isFalse();
        }

        @Test
        @DisplayName("零返回 false")
        void shouldReturnFalseForZero() {
            assertThat(ValidatorUtil.isPositiveInteger("0")).isFalse();
        }
    }

    @Nested
    @DisplayName("isRangeDigitNumber 测试")
    class IsRangeDigitNumberTest {

        @Test
        @DisplayName("位数在范围内时返回 true")
        void shouldReturnTrueWhenDigitsInRange() {
            assertThat(ValidatorUtil.isRangeDigitNumber("1234", 3, 6)).isTrue();
        }

        @Test
        @DisplayName("位数等于最小值时返回 true")
        void shouldReturnTrueWhenDigitsEqualsMin() {
            assertThat(ValidatorUtil.isRangeDigitNumber("123", 3, 6)).isTrue();
        }

        @Test
        @DisplayName("位数不足时返回 false")
        void shouldReturnFalseWhenDigitsBelowMin() {
            assertThat(ValidatorUtil.isRangeDigitNumber("12", 3, 6)).isFalse();
        }

        @Test
        @DisplayName("位数超出时返回 false")
        void shouldReturnFalseWhenDigitsAboveMax() {
            assertThat(ValidatorUtil.isRangeDigitNumber("1234567", 3, 6)).isFalse();
        }
    }

    @Nested
    @DisplayName("isTime 测试")
    class IsTimeTest {

        @Test
        @DisplayName("有效时间返回 true")
        void shouldReturnTrueForValidTime() {
            assertThat(ValidatorUtil.isTime("17:06:30")).isTrue();
            assertThat(ValidatorUtil.isTime("00:00:00")).isTrue();
            assertThat(ValidatorUtil.isTime("23:59:59")).isTrue();
        }

        @Test
        @DisplayName("小时超出范围时返回 false")
        void shouldReturnFalseWhenHourOutOfRange() {
            assertThat(ValidatorUtil.isTime("24:00:00")).isFalse();
        }

        @Test
        @DisplayName("个位数未补零时返回 false")
        void shouldReturnFalseWhenNotZeroPadded() {
            assertThat(ValidatorUtil.isTime("17:6:30")).isFalse();
        }
    }

    @Nested
    @DisplayName("isUrl 测试")
    class IsUrlTest {

        @Test
        @DisplayName("http URL 返回 true")
        void shouldReturnTrueForHttpUrl() {
            assertThat(ValidatorUtil.isUrl("http://google.com/help/me")).isTrue();
        }

        @Test
        @DisplayName("ftp URL 返回 true")
        void shouldReturnTrueForFtpUrl() {
            assertThat(ValidatorUtil.isUrl("ftp://www.google.com")).isTrue();
        }

        @Test
        @DisplayName("非法 URL 返回 false")
        void shouldReturnFalseForInvalidUrl() {
            assertThat(ValidatorUtil.isUrl("http://un/www.google.com/index.asp")).isFalse();
        }
    }

    @Nested
    @DisplayName("isUuid 测试")
    class IsUuidTest {

        @Test
        @DisplayName("有效 UUID 返回 true")
        void shouldReturnTrueForValidUuid() {
            assertThat(ValidatorUtil.isUuid("550e8400-e29b-41d4-a716-446655440000")).isTrue();
        }

        @Test
        @DisplayName("非 UUID 格式返回 false")
        void shouldReturnFalseForInvalidUuid() {
            assertThat(ValidatorUtil.isUuid("not-a-uuid")).isFalse();
        }
    }

    @Nested
    @DisplayName("isZipCode 测试")
    class IsZipCodeTest {

        @Test
        @DisplayName("有效邮政编码返回 true")
        void shouldReturnTrueForValidZipCode() {
            assertThat(ValidatorUtil.isZipCode("100000")).isTrue();
        }

        @Test
        @DisplayName("位数不足时返回 false")
        void shouldReturnFalseForInsufficientDigits() {
            assertThat(ValidatorUtil.isZipCode("10000")).isFalse();
        }

        @Test
        @DisplayName("含字母时返回 false")
        void shouldReturnFalseWhenContainsLetter() {
            assertThat(ValidatorUtil.isZipCode("10000A")).isFalse();
        }
    }

}
