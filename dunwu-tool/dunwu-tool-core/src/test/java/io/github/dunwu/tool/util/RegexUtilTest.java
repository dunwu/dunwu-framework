package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see RegexUtil
 * @since 2019-01-16
 */
@DisplayName("正则校验测试例集")
public class RegexUtilTest {

    final String text = "ZZZaaabbbccc中文1234";

    @Test
    @DisplayName("查找匹配的文本")
    public void get() {
        String resultGet = RegexUtil.get(text, RegexUtil.NUMBERS, 0);
        Assertions.assertEquals("1234", resultGet);
    }

    @Test
    @DisplayName("查找所有匹配的文本")
    public void getAll() {
        List<String> actualList = RegexUtil.getAll(text, "\\w{2}", 0, new ArrayList<>());
        ArrayList<String> expectedList = CollectionUtil.newArrayList("ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34");
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("找到匹配的第一个数字")
    public void getFirst() {
        String result = RegexUtil.getFirst(text, RegexUtil.NUMBERS);
        Integer value = Integer.valueOf(result);
        Assertions.assertEquals(Integer.valueOf(1234), value);
    }

    @Test
    public void extractMultiTest() {
        // 抽取多个分组然后把它们拼接起来
        String resultExtractMulti = RegexUtil.extractMulti(text, "(\\w)aa(\\w)", "$1-$2");
        Assertions.assertEquals("Z-a", resultExtractMulti);
    }

    @Test
    public void extractMultiTest2() {
        // 抽取多个分组然后把它们拼接起来
        String resultExtractMulti = RegexUtil.extractMulti(text, "(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)",
            "$1-$2-$3-$4-$5-$6-$7-$8-$9-$10");
        Assertions.assertEquals("Z-Z-Z-a-a-a-b-b-b-c", resultExtractMulti);
    }

    @Test
    public void removeFirst() {
        // 删除第一个匹配到的内容
        String resultDelFirst = RegexUtil.removeFirst(text, "(\\w)aa(\\w)");
        Assertions.assertEquals("ZZbbbccc中文1234", resultDelFirst);
    }

    @Test
    public void removeAll() {
        // 删除所有匹配到的内容
        String content = "发东方大厦eee![images]http://abc.com/2.gpg]好机会eee![images]http://abc.com/2.gpg]好机会";
        String resultDelAll = RegexUtil.removeAll(content, "!\\[images\\][^\\u4e00-\\u9fa5\\\\s]*");
        Assertions.assertEquals("发东方大厦eee好机会eee好机会", resultDelAll);
    }

    @Test
    public void matches() {
        // 给定字符串是否匹配给定正则
        boolean isMatch = RegexUtil.matches(text, "\\w+[\u4E00-\u9FFF]+\\d+");
        Assertions.assertTrue(isMatch);
    }

    @Test
    public void replace() {
        String str = "AAABBCCCBBDDDBB";
        String replace = StringUtil.replace(str, 0, "BB", "22", false);
        Assertions.assertEquals("AAA22CCC22DDD22", replace);

        replace = StringUtil.replace(str, 3, "BB", "22", false);
        Assertions.assertEquals("AAA22CCC22DDD22", replace);

        replace = StringUtil.replace(str, 4, "BB", "22", false);
        Assertions.assertEquals("AAABBCCC22DDD22", replace);

        replace = StringUtil.replace(str, 4, "bb", "22", true);
        Assertions.assertEquals("AAABBCCC22DDD22", replace);

        replace = StringUtil.replace(str, 4, "bb", "", true);
        Assertions.assertEquals("AAABBCCCDDD", replace);

        replace = StringUtil.replace(str, 4, "bb", null, true);
        Assertions.assertEquals("AAABBCCCDDD", replace);
    }

    @Test
    public void replaceAll() {
        //通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串
        //此处把1234替换为 ->1234<-
        String result1 = RegexUtil.replaceAll(text, "(\\d+)", "->$1<-");
        Assertions.assertEquals("ZZZaaabbbccc中文->1234<-", result1);

        //此处把1234替换为 ->1234<-
        String result2 = RegexUtil.replaceAll(this.text, "(\\d+)",
            parameters -> "->" + parameters.group(1) + "<-");
        Assertions.assertEquals("ZZZaaabbbccc中文->1234<-", result2);
    }

    @Test
    public void escape() {
        //转义给定字符串，为正则相关的特殊符号转义
        String escape = RegexUtil.escape("我有个$符号{}");
        System.out.println("escape = " + escape);
        Assertions.assertEquals("我有个\\$符号\\{\\}", escape);
    }

    @Test
    public void getAllGroups() {
        //转义给定字符串，为正则相关的特殊符号转义
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
        List<String> allGroups = RegexUtil.getAllGroups("192-168-1-1", pattern);
        Assertions.assertEquals("192-168-1", allGroups.get(0));
        Assertions.assertEquals("192", allGroups.get(1));
        Assertions.assertEquals("168", allGroups.get(2));
        Assertions.assertEquals("1", allGroups.get(3));

        allGroups = RegexUtil.getAllGroups("192-168-1-1", pattern, false);
        Assertions.assertEquals("192", allGroups.get(0));
        Assertions.assertEquals("168", allGroups.get(1));
        Assertions.assertEquals("1", allGroups.get(2));
    }

    @Test
    public void findAllInJson() {
        String content = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":\"hello\"},\"allowed\":false,\"version\":0}";
        String content2 = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":hello},\"allowed\":false,\"version\":0}";
        List<String> matchValues = RegexUtil.getAllInJson(content, "judgementId");
        if (CollectionUtil.isNotEmpty(matchValues)) {
            matchValues.forEach(System.out::println);
        }

        List<String> matchValues2 = RegexUtil.getAllInJson(content2,
            "judgementId");
        if (CollectionUtil.isNotEmpty(matchValues2)) {
            matchValues2.forEach(System.out::println);
        }
    }

    @Nested
    @DisplayName("校验Markdonw")
    class CheckMarkdown {

        @Test
        @DisplayName("校验含有 Markdonw ![]() ")
        void replaceAll() {
            String str = RegexUtil.replaceAll(
                "![asgad](http://www.baidu.com/test.png)",
                RegexUtil.REGEX_MARKDOWN_IMAGE_TAG, "![](");
            System.out.println(str);
        }

    }

}
