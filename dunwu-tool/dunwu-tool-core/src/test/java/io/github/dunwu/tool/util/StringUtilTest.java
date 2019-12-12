package io.github.dunwu.tool.util;

import io.github.dunwu.tool.lang.Dict;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 字符串工具类单元测试
 *
 * @author Looly
 */
public class StringUtilTest {

    @Test
    public void isBlankTest() {
        String blank = "	  　";
        Assertions.assertTrue(StringUtil.isBlank(blank));
    }

    @Test
    public void isBlankTest2() {
        String blank = "\u202a";
        Assertions.assertTrue(StringUtil.isBlank(blank));
    }

    @Test
    public void trimTest() {
        String blank = "	 哈哈 　";
        String trim = StringUtil.trim(blank);
        Assertions.assertEquals("哈哈", trim);
    }

    @Test
    public void cleanBlankTest() {
        // 包含：制表符、英文空格、不间断空白符、全角空格
        String str = "	 你 好　";
        String cleanBlank = StringUtil.cleanBlank(str);
        Assertions.assertEquals("你好", cleanBlank);
    }

    @Test
    public void cutTest() {
        String str = "aaabbbcccdddaadfdfsdfsdf0";
        String[] cut = StringUtil.cut(str, 4);
        Assertions.assertArrayEquals(new String[] { "aaab", "bbcc", "cddd", "aadf", "dfsd", "fsdf", "0" }, cut);
    }

    @Test
    public void splitTest() {
        String str = "a,b ,c,d,,e";
        List<String> split = StringUtil.split(str, ',', -1, true, true);
        // 测试空是否被去掉
        Assertions.assertEquals(5, split.size());
        // 测试去掉两边空白符是否生效
        Assertions.assertEquals("b", split.get(1));
    }

    @Test
    public void splitToLongTest() {
        String str = "1,2,3,4, 5";
        long[] longArray = StringUtil.splitToLong(str, ',');
        Assertions.assertArrayEquals(new long[] { 1, 2, 3, 4, 5 }, longArray);

        longArray = StringUtil.splitToLong(str, ",");
        Assertions.assertArrayEquals(new long[] { 1, 2, 3, 4, 5 }, longArray);
    }

    @Test
    public void splitToIntTest() {
        String str = "1,2,3,4, 5";
        int[] intArray = StringUtil.splitToInt(str, ',');
        Assertions.assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, intArray);

        intArray = StringUtil.splitToInt(str, ",");
        Assertions.assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, intArray);
    }

    @Test
    public void formatTest() {
        String template = "你好，我是{name}，我的电话是：{phone}";
        String result = StringUtil.format(template, Dict.create().set("name", "张三").set("phone", "13888881111"));
        Assertions.assertEquals("你好，我是张三，我的电话是：13888881111", result);

        String result2 = StringUtil.format(template, Dict.create().set("name", "张三").set("phone", null));
        Assertions.assertEquals("你好，我是张三，我的电话是：{phone}", result2);
    }

    @Test
    public void stripTest() {
        String str = "abcd123";
        String strip = StringUtil.strip(str, "ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringUtil.strip(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtil.strip(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringUtil.strip(str, null, "567");
        Assertions.assertEquals("abcd123", strip);

        Assertions.assertEquals("", StringUtil.strip("a", "a"));
        Assertions.assertEquals("", StringUtil.strip("a", "a", "b"));
    }

    @Test
    public void stripIgnoreCaseTest() {
        String str = "abcd123";
        String strip = StringUtil.stripIgnoreCase(str, "Ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringUtil.stripIgnoreCase(str, "AB", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtil.stripIgnoreCase(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtil.stripIgnoreCase(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringUtil.stripIgnoreCase(str, null, "567");
        Assertions.assertEquals("abcd123", strip);
    }

    @Test
    public void indexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringUtil.indexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringUtil.indexOfIgnoreCase("balabala", null, 0));
        Assertions.assertEquals(0, StringUtil.indexOfIgnoreCase("", "", 0));
        Assertions.assertEquals(0, StringUtil.indexOfIgnoreCase("aabaabaa", "A", 0));
        Assertions.assertEquals(2, StringUtil.indexOfIgnoreCase("aabaabaa", "B", 0));
        Assertions.assertEquals(1, StringUtil.indexOfIgnoreCase("aabaabaa", "AB", 0));
        Assertions.assertEquals(5, StringUtil.indexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(-1, StringUtil.indexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(2, StringUtil.indexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(2, StringUtil.indexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(-1, StringUtil.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void lastIndexOfTest() {
        String a = "aabbccddcc";
        int lastIndexOf = StringUtil.lastIndexOf(a, "c", 0, false);
        Assertions.assertEquals(-1, lastIndexOf);
    }

    @Test
    public void lastIndexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringUtil.lastIndexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("balabala", null));
        Assertions.assertEquals(0, StringUtil.lastIndexOfIgnoreCase("", ""));
        Assertions.assertEquals(7, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "A"));
        Assertions.assertEquals(5, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "B"));
        Assertions.assertEquals(4, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "AB"));
        Assertions.assertEquals(2, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(5, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(2, StringUtil.lastIndexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(3, StringUtil.lastIndexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void replaceTest() {
        String string = StringUtil.replace("aabbccdd", 2, 6, '*');
        Assertions.assertEquals("aa****dd", string);
        string = StringUtil.replace("aabbccdd", 2, 12, '*');
        Assertions.assertEquals("aa******", string);
    }

    @Test
    public void replaceTest2() {
        String result = StringUtil.replace("123", "2", "3");
        Assertions.assertEquals("133", result);
    }

    @Test
    public void replaceTest3() {
        String result = StringUtil.replace(",abcdef,", ",", "|");
        Assertions.assertEquals("|abcdef|", result);
    }

    @Test
    public void replaceTest4() {
        String a = "1039";
        String result = StringUtil.padPre(a, 8, "0"); //在字符串1039前补4个0
        Assertions.assertEquals("00001039", result);
    }

    @Test
    public void replaceFirst() {
        Assertions.assertEquals("acbc", StringUtil.replaceFirst("abbc", 'b', 'c'));
        Assertions.assertEquals("abcc", StringUtil.replaceFirst("abcc", 'c', 'c'));
        Assertions.assertEquals("", StringUtil.replaceFirst("", 'c', 'c'));
        Assertions.assertNull(StringUtil.replaceFirst(null, 'c', 'c'));
    }

    @Test
    public void replaceLast() {
        Assertions.assertEquals("abcc", StringUtil.replaceLast("abbc", 'b', 'c'));
        Assertions.assertEquals("abcc", StringUtil.replaceLast("abcc", 'c', 'c'));
        Assertions.assertEquals("", StringUtil.replaceLast("", 'c', 'c'));
        Assertions.assertNull(StringUtil.replaceLast(null, 'c', 'c'));
    }

    @Test
    public void upperFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringUtil.upperFirst(sb);
        Assertions.assertEquals(s, sb.toString());
    }

    @Test
    public void lowerFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringUtil.lowerFirst(sb);
        Assertions.assertEquals("kEY", s);
    }

    @Test
    public void subTest() {
        String a = "abcderghigh";
        String pre = StringUtil.sub(a, -5, a.length());
        Assertions.assertEquals("ghigh", pre);
    }

    @Test
    public void subBeforeTest() {
        String a = "abcderghigh";
        String pre = StringUtil.subBefore(a, "d", false);
        Assertions.assertEquals("abc", pre);
        pre = StringUtil.subBefore(a, 'd', false);
        Assertions.assertEquals("abc", pre);
        pre = StringUtil.subBefore(a, 'a', false);
        Assertions.assertEquals("", pre);

        //找不到返回原串
        pre = StringUtil.subBefore(a, 'k', false);
        Assertions.assertEquals(a, pre);
        pre = StringUtil.subBefore(a, 'k', true);
        Assertions.assertEquals(a, pre);
    }

    @Test
    public void subAfterTest() {
        String a = "abcderghigh";
        String pre = StringUtil.subAfter(a, "d", false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringUtil.subAfter(a, 'd', false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringUtil.subAfter(a, 'h', true);
        Assertions.assertEquals("", pre);

        //找不到字符返回空串
        pre = StringUtil.subAfter(a, 'k', false);
        Assertions.assertEquals("", pre);
        pre = StringUtil.subAfter(a, 'k', true);
        Assertions.assertEquals("", pre);
    }

    @Test
    public void subSufByLengthTest() {
        Assertions.assertEquals("cde", StringUtil.subSufByLength("abcde", 3));
        Assertions.assertEquals("", StringUtil.subSufByLength("abcde", -1));
        Assertions.assertEquals("", StringUtil.subSufByLength("abcde", 0));
        Assertions.assertEquals("abcde", StringUtil.subSufByLength("abcde", 5));
        Assertions.assertEquals("abcde", StringUtil.subSufByLength("abcde", 10));
    }

    @Test
    public void repeatAndJoinTest() {
        String repeatAndJoin = StringUtil.repeatAndJoin("?", 5, ",");
        Assertions.assertEquals("?,?,?,?,?", repeatAndJoin);

        repeatAndJoin = StringUtil.repeatAndJoin("?", 0, ",");
        Assertions.assertEquals("", repeatAndJoin);

        repeatAndJoin = StringUtil.repeatAndJoin("?", 5, null);
        Assertions.assertEquals("?????", repeatAndJoin);
    }

    @Test
    public void moveTest() {
        String str = "aaaaaaa22222bbbbbbb";
        String result = StringUtil.move(str, 7, 12, -3);
        Assertions.assertEquals("aaaa22222aaabbbbbbb", result);
        result = StringUtil.move(str, 7, 12, -4);
        Assertions.assertEquals("aaa22222aaaabbbbbbb", result);
        result = StringUtil.move(str, 7, 12, -7);
        Assertions.assertEquals("22222aaaaaaabbbbbbb", result);
        result = StringUtil.move(str, 7, 12, -20);
        Assertions.assertEquals("aaaaaa22222abbbbbbb", result);

        result = StringUtil.move(str, 7, 12, 3);
        Assertions.assertEquals("aaaaaaabbb22222bbbb", result);
        result = StringUtil.move(str, 7, 12, 7);
        Assertions.assertEquals("aaaaaaabbbbbbb22222", result);
        result = StringUtil.move(str, 7, 12, 20);
        Assertions.assertEquals("aaaaaaab22222bbbbbb", result);

        result = StringUtil.move(str, 7, 12, 0);
        Assertions.assertEquals("aaaaaaa22222bbbbbbb", result);
    }

    @Test
    public void removePrefixIgnorecaseTest() {
        String a = "aaabbb";
        String prefix = "aaa";
        Assertions.assertEquals("bbb", StringUtil.removePrefixIgnoreCase(a, prefix));

        prefix = "AAA";
        Assertions.assertEquals("bbb", StringUtil.removePrefixIgnoreCase(a, prefix));

        prefix = "AAABBB";
        Assertions.assertEquals("", StringUtil.removePrefixIgnoreCase(a, prefix));
    }

    @Test
    public void maxLengthTest() {
        String text = "我是一段正文，很长的正文，需要截取的正文";
        String str = StringUtil.maxLength(text, 5);
        Assertions.assertEquals("我是一段正...", str);
        str = StringUtil.maxLength(text, 21);
        Assertions.assertEquals(text, str);
        str = StringUtil.maxLength(text, 50);
        Assertions.assertEquals(text, str);
    }

    @Test
    public void toCamelCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringUtil.toCamelCase(str);
        Assertions.assertEquals("tableTestOfDay", result);

        String str1 = "TableTestOfDay";
        String result1 = StringUtil.toCamelCase(str1);
        Assertions.assertEquals("TableTestOfDay", result1);
    }

    @Test
    public void toUnderLineCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringUtil.toUnderlineCase(str);
        Assertions.assertEquals("table_test_of_day", result);

        String str1 = "_Table_Test_Of_day_";
        String result1 = StringUtil.toUnderlineCase(str1);
        Assertions.assertEquals("_table_test_of_day_", result1);

        String str2 = "_Table_Test_Of_DAY_";
        String result2 = StringUtil.toUnderlineCase(str2);
        Assertions.assertEquals("_table_test_of_DAY_", result2);

        String str3 = "_TableTestOfDAYtoday";
        String result3 = StringUtil.toUnderlineCase(str3);
        Assertions.assertEquals("_table_test_of_DAY_today", result3);

        String str4 = "HelloWorld_test";
        String result4 = StringUtil.toUnderlineCase(str4);
        Assertions.assertEquals("hello_world_test", result4);
    }

    @Test
    public void containsAnyTest() {
        //字符
        boolean containsAny = StringUtil.containsAny("aaabbbccc", 'a', 'd');
        Assertions.assertTrue(containsAny);
        containsAny = StringUtil.containsAny("aaabbbccc", 'e', 'd');
        Assertions.assertFalse(containsAny);
        containsAny = StringUtil.containsAny("aaabbbccc", 'd', 'c');
        Assertions.assertTrue(containsAny);

        //字符串
        containsAny = StringUtil.containsAny("aaabbbccc", "a", "d");
        Assertions.assertTrue(containsAny);
        containsAny = StringUtil.containsAny("aaabbbccc", "e", "d");
        Assertions.assertFalse(containsAny);
        containsAny = StringUtil.containsAny("aaabbbccc", "d", "c");
        Assertions.assertTrue(containsAny);
    }

    @Test
    public void centerTest() {
        Assertions.assertNull(StringUtil.center(null, 10));
        Assertions.assertEquals("    ", StringUtil.center("", 4));
        Assertions.assertEquals("ab", StringUtil.center("ab", -1));
        Assertions.assertEquals(" ab ", StringUtil.center("ab", 4));
        Assertions.assertEquals("abcd", StringUtil.center("abcd", 2));
        Assertions.assertEquals(" a  ", StringUtil.center("a", 4));
    }

    @Test
    public void padPreTest() {
        Assertions.assertNull(StringUtil.padPre(null, 10, ' '));
        Assertions.assertEquals("001", StringUtil.padPre("1", 3, '0'));
        Assertions.assertEquals("12", StringUtil.padPre("123", 2, '0'));

        Assertions.assertNull(StringUtil.padPre(null, 10, "AA"));
        Assertions.assertEquals("AB1", StringUtil.padPre("1", 3, "ABC"));
        Assertions.assertEquals("12", StringUtil.padPre("123", 2, "ABC"));
    }

    @Test
    public void padAfterTest() {
        Assertions.assertNull(StringUtil.padAfter(null, 10, ' '));
        Assertions.assertEquals("100", StringUtil.padAfter("1", 3, '0'));
        Assertions.assertEquals("23", StringUtil.padAfter("123", 2, '0'));

        Assertions.assertNull(StringUtil.padAfter(null, 10, "ABC"));
        Assertions.assertEquals("1AB", StringUtil.padAfter("1", 3, "ABC"));
        Assertions.assertEquals("23", StringUtil.padAfter("123", 2, "ABC"));
    }

}
