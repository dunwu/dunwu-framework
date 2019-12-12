package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.text.StrFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrFormatterTest {

    @Test
    public void formatTest() {
        //通常使用
        String result1 = StrFormatter.format("this is {} for {}", "a", "b");
        Assertions.assertEquals("this is a for b", result1);

        //转义{}
        String result2 = StrFormatter.format("this is \\{} for {}", "a", "b");
        Assertions.assertEquals("this is {} for a", result2);

        //转义\
        String result3 = StrFormatter.format("this is \\\\{} for {}", "a", "b");
        Assertions.assertEquals("this is \\a for b", result3);
    }

}
