package io.github.dunwu.util.text;

import io.github.dunwu.util.collection.CollectionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see RegexUtil
 * @since 2019-01-16
 */
@DisplayName("正则校验测试例集")
class RegexUtilTest {

    @Test
    void test2() {
        String content = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":\"hello\"},\"allowed\":false,\"version\":0}";
        String content2 = "rulename,{\"m_login_ip\":{\"login_account_cnt\":3.0,\"login_ip\":\"127.0.0.1\","
            + "\"window_time\":1547605830000,\"judgementId\":hello},\"allowed\":false,\"version\":0}";
        List<String> matchValues = RegexUtil.getMatchValuesInJson(content, "judgementId");
        if (CollectionUtil.isNotEmpty(matchValues)) {
            matchValues.forEach(System.out::println);
        }

        List<String> matchValues2 = RegexUtil.getMatchValuesInJson(content2,
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
        void isMarkdownImageTag() {
            String str = RegexUtil.replaceAll(
                "![asgad](http://www.baidu.com/test.png)",
                RegexUtil.REGEX_MARKDOWN_IMAGE_TAG, "![](");
            System.out.println(str);
        }

    }

}
