package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.text.TextSimilarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 文本相似度计算工具类单元测试
 *
 * @author looly
 */
public class TextSimilarityTest {

    @Test
    public void similarDegreeTest() {
        String a = "我是一个文本，独一无二的文本";
        String b = "一个文本，独一无二的文本";

        double degree = TextSimilarity.similar(a, b);
        Assertions.assertEquals(0.8571428571428571D, degree, 16);

        String similarPercent = TextSimilarity.similar(a, b, 2);
        Assertions.assertEquals("85.71%", similarPercent);
    }

}
