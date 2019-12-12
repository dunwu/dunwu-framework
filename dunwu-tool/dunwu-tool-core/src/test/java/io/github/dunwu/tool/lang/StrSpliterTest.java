package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.text.StrSpliter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@link StrSpliter} 单元测试
 *
 * @author Looly
 */
public class StrSpliterTest {

    @Test
    public void splitByCharTest() {
        String str1 = "a, ,efedsfs,   ddf";
        List<String> split = StrSpliter.split(str1, ',', 0, true, true);
        Assertions.assertEquals("ddf", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitByStrTest() {
        String str1 = "aabbccaaddaaee";
        List<String> split = StrSpliter.split(str1, "aa", 0, true, true);
        Assertions.assertEquals("ee", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitByBlankTest() {
        String str1 = "aa bbccaa     ddaaee";
        List<String> split = StrSpliter.split(str1, 0);
        Assertions.assertEquals("ddaaee", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitPathTest() {
        String str1 = "/use/local/bin";
        List<String> split = StrSpliter.splitPath(str1, 0);
        Assertions.assertEquals("bin", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

}
