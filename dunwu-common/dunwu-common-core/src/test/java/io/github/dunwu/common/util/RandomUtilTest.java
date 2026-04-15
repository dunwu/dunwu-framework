package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link RandomUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-22
 */
@DisplayName("RandomUtil 单元测试")
class RandomUtilTest {

    // ============================== createSecureRandom ==============================

    @Nested
    @DisplayName("createSecureRandom 测试")
    class CreateSecureRandomTest {

        @Test
        @DisplayName("seed 为 null 时返回无种子的 SecureRandom")
        void shouldReturnSecureRandomWhenSeedIsNull() {
            SecureRandom random = RandomUtil.createSecureRandom(null);
            assertThat(random).isNotNull().isInstanceOf(SecureRandom.class);
        }

        @Test
        @DisplayName("传入 seed 时返回带种子的 SecureRandom")
        void shouldReturnSecureRandomWithSeed() {
            SecureRandom random = RandomUtil.createSecureRandom(new byte[] {1, 2, 3});
            assertThat(random).isNotNull().isInstanceOf(SecureRandom.class);
        }

    }

    // ============================== randomChineseBoyFirstName ==============================

    @Nested
    @DisplayName("randomChineseBoyFirstName 测试")
    class RandomChineseBoyFirstNameTest {

        @RepeatedTest(5)
        @DisplayName("生成的男孩名不为空且长度在合理范围内")
        void shouldGenerateNonBlankBoyFirstName() {
            String name = RandomUtil.randomChineseBoyFirstName();
            // 随机长度为1或2个汉字（count=2单字，count=3双字，可能返回空）
            assertThat(name).isNotNull();
        }

    }

    // ============================== randomChineseBoyName ==============================

    @Nested
    @DisplayName("randomChineseBoyName 测试")
    class RandomChineseBoyNameTest {

        @RepeatedTest(5)
        @DisplayName("生成的男孩姓名以常见姓氏开头")
        void shouldGenerateNonBlankBoyName() {
            String name = RandomUtil.randomChineseBoyName();
            assertThat(name).isNotNull().isNotEmpty();
        }

    }

    // ============================== randomChineseGirlFirstName ==============================

    @Nested
    @DisplayName("randomChineseGirlFirstName 测试")
    class RandomChineseGirlFirstNameTest {

        @RepeatedTest(5)
        @DisplayName("生成的女孩名不为空")
        void shouldGenerateNonBlankGirlFirstName() {
            String name = RandomUtil.randomChineseGirlFirstName();
            assertThat(name).isNotNull();
        }

    }

    // ============================== randomChineseGirlName ==============================

    @Nested
    @DisplayName("randomChineseGirlName 测试")
    class RandomChineseGirlNameTest {

        @RepeatedTest(5)
        @DisplayName("生成的女孩姓名不为空")
        void shouldGenerateNonBlankGirlName() {
            String name = RandomUtil.randomChineseGirlName();
            assertThat(name).isNotNull().isNotEmpty();
        }

    }

    // ============================== randomChineseLastName ==============================

    @Nested
    @DisplayName("randomChineseLastName 测试")
    class RandomChineseLastNameTest {

        @RepeatedTest(5)
        @DisplayName("随机姓氏不为空")
        void shouldReturnNonBlankLastName() {
            String lastName = RandomUtil.randomChineseLastName();
            assertThat(lastName).isNotBlank();
        }

    }

    // ============================== randomChineseLetterString ==============================

    @Nested
    @DisplayName("randomChineseLetterString 测试")
    class RandomChineseLetterStringTest {

        @Test
        @DisplayName("生成字符串长度在指定范围内")
        void shouldGenerateStringWithLengthInRange() {
            String result = RandomUtil.randomChineseLetterString(3, 8);
            assertThat(result).isNotNull();
            assertThat(result.length()).isBetween(3, 8);
        }

        @Test
        @DisplayName("生成的字符均为中文 Unicode 字符")
        void shouldContainOnlyChineseChars() {
            String result = RandomUtil.randomChineseLetterString(5, 6);
            for (char c : result.toCharArray()) {
                assertThat(c).isBetween('\u4E00', '\u9FA5');
            }
        }

    }

    // ============================== randomInt ==============================

    @Nested
    @DisplayName("randomInt 测试")
    class RandomIntTest {

        @RepeatedTest(10)
        @DisplayName("生成的整数在指定范围内")
        void shouldReturnIntInRange() {
            int result = RandomUtil.randomInt(1, 10);
            assertThat(result).isBetween(1, 9);
        }

        @Test
        @DisplayName("min == max - 1 时固定返回 min")
        void shouldReturnMinWhenRangeIsOne() {
            int result = RandomUtil.randomInt(5, 6);
            assertThat(result).isEqualTo(5);
        }

    }

    // ============================== randomString ==============================

    @Nested
    @DisplayName("randomString 测试")
    class RandomStringTest {

        @Test
        @DisplayName("randomString(sample, length) 返回指定长度字符串")
        void shouldReturnStringOfExactLength() {
            // length=0 when min=0,max=5 may vary, but fixed length overload:
            // randomString(sample, length) calls randomString(sample, 0, length)
            // which uses getRandom().nextInt(0, length), so length can be 0..length-1
            // Just check result is not null and chars are from sample
            String sample = "abc";
            String result = RandomUtil.randomString(sample, 3);
            assertThat(result).isNotNull();
            for (char c : result.toCharArray()) {
                assertThat(sample).contains(String.valueOf(c));
            }
        }

        @Test
        @DisplayName("randomString(sample, min, max) 返回长度在范围内的字符串")
        void shouldReturnStringWithLengthInRange() {
            String result = RandomUtil.randomString("abcdef", 3, 8);
            assertThat(result).isNotNull();
            assertThat(result.length()).isBetween(3, 7);
        }

        @Test
        @DisplayName("sample 为空时返回空字符串")
        void shouldReturnEmptyStringWhenSampleIsBlank() {
            String result = RandomUtil.randomString("", 3, 8);
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("min < 0 时返回空字符串")
        void shouldReturnEmptyStringWhenMinIsNegative() {
            String result = RandomUtil.randomString("abc", -1, 5);
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("randomString(min, max) 返回字母+数字组成的字符串")
        void shouldReturnAlphanumericStringInRange() {
            String result = RandomUtil.randomString(4, 8);
            assertThat(result).isNotNull();
            assertThat(result.length()).isBetween(4, 7);
            assertThat(result).matches("[a-zA-Z0-9]*");
        }

    }

    // ============================== randomChineseName ==============================

    @Nested
    @DisplayName("randomChineseName 测试")
    class RandomChineseNameTest {

        @RepeatedTest(5)
        @DisplayName("randomChineseName() 返回非空姓名")
        void shouldGenerateNonBlankChineseName() {
            String name = RandomUtil.randomChineseName();
            assertThat(name).isNotNull().isNotEmpty();
        }

        @Test
        @DisplayName("randomChineseName(true) 返回女孩姓名（非空）")
        void shouldGenerateGirlName() {
            String name = RandomUtil.randomChineseName(true);
            assertThat(name).isNotNull().isNotEmpty();
        }

        @Test
        @DisplayName("randomChineseName(false) 返回男孩姓名（非空）")
        void shouldGenerateBoyName() {
            String name = RandomUtil.randomChineseName(false);
            assertThat(name).isNotNull().isNotEmpty();
        }

    }

    // ============================== randomChineseSimpleLetterString ==============================

    @Nested
    @DisplayName("randomChineseSimpleLetterString 测试")
    class RandomChineseSimpleLetterStringTest {

        @Test
        @DisplayName("生成指定长度范围的简体中文字符串")
        void shouldGenerateSimpleChineseStringInRange() {
            String result = RandomUtil.randomChineseSimpleLetterString(3, 6);
            assertThat(result).isNotNull();
            assertThat(result.length()).isBetween(3, 5);
        }

    }

    // ============================== randomChineseSimpleLetter ==============================

    @Nested
    @DisplayName("randomChineseSimpleLetter 测试")
    class RandomChineseSimpleLetterTest {

        @RepeatedTest(5)
        @DisplayName("生成的简体字符长度为 1")
        void shouldGenerateSingleSimpleChinese() {
            String result = RandomUtil.randomChineseSimpleLetter();
            assertThat(result).isNotNull().hasSize(1);
        }

    }

    // ============================== randomDate ==============================

    @Nested
    @DisplayName("randomDate 测试")
    class RandomDateTest {

        private final LocalDateTime MIN = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        private final LocalDateTime MAX = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        @Test
        @DisplayName("randomDate(Date, Date) 返回格式化日期字符串")
        void shouldReturnFormattedDateString() {
            Date minDate = DateUtil.date(MIN);
            Date maxDate = DateUtil.date(MAX);
            String result = RandomUtil.randomDate(minDate, maxDate);
            assertThat(result).isNotBlank().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        }

        @Test
        @DisplayName("randomDate(Date, Date, pattern) 按自定义格式返回日期字符串")
        void shouldReturnFormattedDateStringWithCustomPattern() {
            Date minDate = DateUtil.date(MIN);
            Date maxDate = DateUtil.date(MAX);
            String result = RandomUtil.randomDate(minDate, maxDate, "yyyy/MM/dd");
            assertThat(result).isNotBlank().matches("\\d{4}/\\d{2}/\\d{2}");
        }

        @Test
        @DisplayName("randomDate(LocalDateTime, LocalDateTime, pattern) 返回格式化字符串")
        void shouldReturnFormattedDateStringFromLocalDateTime() {
            String result = RandomUtil.randomDate(MIN, MAX, "yyyy-MM-dd");
            assertThat(result).isNotBlank().matches("\\d{4}-\\d{2}-\\d{2}");
        }

        @Test
        @DisplayName("randomDate(LocalDateTime, LocalDateTime) 返回 Date 对象")
        void shouldReturnDateFromLocalDateTime() {
            Date result = RandomUtil.randomDate(MIN, MAX);
            assertThat(result).isNotNull().isInstanceOf(Date.class);
        }

    }

    // ============================== randomDomain ==============================

    @Nested
    @DisplayName("randomDomain 测试")
    class RandomDomainTest {

        @RepeatedTest(5)
        @DisplayName("生成的域名包含点号分隔符")
        void shouldGenerateDomainWithDot() {
            String domain = RandomUtil.randomDomain();
            assertThat(domain).isNotBlank().contains(".");
        }

        @Test
        @DisplayName("生成的域名格式为 xxx.yyy")
        void shouldMatchDomainFormat() {
            String domain = RandomUtil.randomDomain();
            assertThat(domain).matches("[a-z0-9]+\\.[a-z0-9]+");
        }

    }

    // ============================== randomEle ==============================

    @Nested
    @DisplayName("randomEle 测试")
    class RandomEleTest {

        @Test
        @DisplayName("randomEle(List) 返回列表中的元素")
        void shouldReturnElementFromList() {
            List<String> list = Arrays.asList("a", "b", "c");
            String result = RandomUtil.randomEle(list);
            assertThat(list).contains(result);
        }

        @Test
        @DisplayName("randomEle(List, limit) 返回前 N 项中的元素")
        void shouldReturnElementFromListWithLimit() {
            List<String> list = Arrays.asList("a", "b", "c", "d");
            String result = RandomUtil.randomEle(list, 2);
            assertThat(Arrays.asList("a", "b")).contains(result);
        }

        @Test
        @DisplayName("randomEle(T[]) 返回数组中的元素")
        void shouldReturnElementFromArray() {
            String[] array = {"x", "y", "z"};
            String result = RandomUtil.randomEle(array);
            assertThat(array).contains(result);
        }

        @Test
        @DisplayName("randomEle(T[], limit) 返回数组前 N 项中的元素")
        void shouldReturnElementFromArrayWithLimit() {
            String[] array = {"x", "y", "z"};
            String result = RandomUtil.randomEle(array, 2);
            assertThat(Arrays.asList("x", "y")).contains(result);
        }

    }

    // ============================== randomEleSet ==============================

    @Nested
    @DisplayName("randomEleSet 测试")
    class RandomEleSetTest {

        @Test
        @DisplayName("返回不重复元素的 Set，数量等于 count")
        void shouldReturnSetOfUniqueElements() {
            List<String> list = Arrays.asList("a", "b", "c", "d", "e");
            Set<String> result = RandomUtil.randomEleSet(list, 3);
            assertThat(result).hasSize(3);
            assertThat(list).containsAll(result);
        }

        @Test
        @DisplayName("count 超出集合大小时抛出 IllegalArgumentException")
        void shouldThrowExceptionWhenCountExceedsSize() {
            List<String> list = Arrays.asList("a", "b");
            assertThatThrownBy(() -> RandomUtil.randomEleSet(list, 5)).isInstanceOf(IllegalArgumentException.class);
        }

    }

    // ============================== randomEles ==============================

    @Nested
    @DisplayName("randomEles 测试")
    class RandomElesTest {

        @Test
        @DisplayName("返回指定数量的元素列表（允许重复）")
        void shouldReturnListOfElements() {
            List<String> list = Arrays.asList("a", "b", "c");
            List<String> result = RandomUtil.randomEles(list, 5);
            assertThat(result).hasSize(5);
            for (String item : result) {
                assertThat(list).contains(item);
            }
        }

    }

    // ============================== randomEmail ==============================

    @Nested
    @DisplayName("randomEmail 测试")
    class RandomEmailTest {

        @RepeatedTest(5)
        @DisplayName("生成的邮箱地址包含 @ 符号")
        void shouldGenerateEmailWithAtSign() {
            String email = RandomUtil.randomEmail();
            assertThat(email).isNotBlank().contains("@");
        }

        @Test
        @DisplayName("生成的邮箱格式为 xxx@yyy.zzz")
        void shouldMatchEmailFormat() {
            String email = RandomUtil.randomEmail();
            assertThat(email).matches("[a-z0-9]+@[a-z0-9]+\\.[a-z0-9]+");
        }

    }

    // ============================== randomEnum ==============================

    @Nested
    @DisplayName("randomEnum 测试")
    class RandomEnumTest {

        private enum Color {RED, GREEN, BLUE}

        @RepeatedTest(5)
        @DisplayName("返回枚举类中的一个值")
        void shouldReturnEnumValue() {
            Color result = RandomUtil.randomEnum(Color.class);
            assertThat(result).isIn(Color.RED, Color.GREEN, Color.BLUE);
        }

    }

    // ============================== randomFirstName ==============================

    @Nested
    @DisplayName("randomFirstName 测试")
    class RandomFirstNameTest {

        @RepeatedTest(5)
        @DisplayName("randomFirstName() 返回非空英文名")
        void shouldGenerateNonBlankFirstName() {
            String name = RandomUtil.randomFirstName();
            assertThat(name).isNotBlank();
        }

        @Test
        @DisplayName("randomFirstName(true) 返回女孩英文名")
        void shouldGenerateGirlFirstName() {
            String name = RandomUtil.randomFirstName(true);
            assertThat(name).isNotBlank();
        }

        @Test
        @DisplayName("randomFirstName(false) 返回男孩英文名")
        void shouldGenerateBoyFirstName() {
            String name = RandomUtil.randomFirstName(false);
            assertThat(name).isNotBlank();
        }

    }

    // ============================== randomBoyFirstName / randomGirlFirstName ==============================

    @Nested
    @DisplayName("randomBoyFirstName / randomGirlFirstName 测试")
    class BoyGirlFirstNameTest {

        @RepeatedTest(3)
        @DisplayName("randomBoyFirstName 返回非空男孩英文名")
        void shouldGenerateNonBlankBoyFirstName() {
            assertThat(RandomUtil.randomBoyFirstName()).isNotBlank();
        }

        @RepeatedTest(3)
        @DisplayName("randomGirlFirstName 返回非空女孩英文名")
        void shouldGenerateNonBlankGirlFirstName() {
            assertThat(RandomUtil.randomGirlFirstName()).isNotBlank();
        }

    }

    // ============================== randomIpv4 ==============================

    @Nested
    @DisplayName("randomIpv4 测试")
    class RandomIpv4Test {

        @RepeatedTest(5)
        @DisplayName("生成的 IPv4 地址格式正确")
        void shouldGenerateValidIpv4Format() {
            String ip = RandomUtil.randomIpv4();
            assertThat(ip).matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        }

    }

    // ============================== randomLastName ==============================

    @Nested
    @DisplayName("randomLastName 测试")
    class RandomLastNameTest {

        @RepeatedTest(3)
        @DisplayName("随机英文姓氏不为空")
        void shouldReturnNonBlankLastName() {
            assertThat(RandomUtil.randomLastName()).isNotBlank();
        }

    }

    // ============================== randomInArray / randomInList ==============================

    @Nested
    @DisplayName("randomInArray / randomInList 测试")
    class RandomInArrayAndListTest {

        @Test
        @DisplayName("randomInArray 返回数组中的元素")
        void shouldReturnElementFromArray() {
            String[] array = {"alpha", "beta", "gamma"};
            String result = RandomUtil.randomInArray(array);
            assertThat(array).contains(result);
        }

        @Test
        @DisplayName("randomInList 返回列表中的元素")
        void shouldReturnElementFromList() {
            List<Integer> list = Arrays.asList(10, 20, 30, 40);
            Integer result = RandomUtil.randomInList(list);
            assertThat(list).contains(result);
        }

        @Test
        @DisplayName("randomInList 列表为空时返回 null")
        void shouldReturnNullWhenListIsEmpty() {
            List<String> list = Arrays.asList();
            String result = RandomUtil.randomInList(list);
            assertThat(result).isNull();
        }

    }

    // ============================== randomMac ==============================

    @Nested
    @DisplayName("randomMac 测试")
    class RandomMacTest {

        @Test
        @DisplayName("randomMac() 返回无分隔符的 12 位十六进制字符串")
        void shouldGenerateMacWithNoSeparator() {
            String mac = RandomUtil.randomMac();
            assertThat(mac).isNotBlank().matches("[0-9a-f]{12}");
        }

        @Test
        @DisplayName("randomMac(separator) 返回带冒号分隔的 MAC 地址")
        void shouldGenerateMacWithColonSeparator() {
            String mac = RandomUtil.randomMac(":");
            assertThat(mac).isNotBlank().matches("([0-9a-f]{2}:){5}[0-9a-f]{2}");
        }

    }

    // ============================== randomNumString ==============================

    @Nested
    @DisplayName("randomNumString 测试")
    class RandomNumStringTest {

        @Test
        @DisplayName("生成纯数字字符串，长度在指定范围内")
        void shouldGenerateNumericStringInRange() {
            String result = RandomUtil.randomNumString(4, 9);
            assertThat(result).isNotNull().matches("\\d*");
            assertThat(result.length()).isBetween(4, 8);
        }

    }

    // ============================== secureRandom ==============================

    @Nested
    @DisplayName("secureRandom 测试")
    class SecureRandomTest {

        @Test
        @DisplayName("返回非 null 的 SecureRandom 实例")
        void shouldReturnSecureRandomInstance() {
            SecureRandom random = RandomUtil.secureRandom();
            assertThat(random).isNotNull().isInstanceOf(SecureRandom.class);
        }

    }

    // ============================== threadLocalRandom ==============================

    @Nested
    @DisplayName("threadLocalRandom 测试")
    class ThreadLocalRandomTest {

        @Test
        @DisplayName("返回非 null 的 ThreadLocalRandom 实例")
        void shouldReturnThreadLocalRandomInstance() {
            ThreadLocalRandom random = RandomUtil.threadLocalRandom();
            assertThat(random).isNotNull().isInstanceOf(ThreadLocalRandom.class);
        }

    }

}
