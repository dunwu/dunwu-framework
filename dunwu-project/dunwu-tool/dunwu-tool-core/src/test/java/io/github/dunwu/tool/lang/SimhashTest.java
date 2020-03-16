package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.text.Simhash;
import io.github.dunwu.tool.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimhashTest {

    @Test
    public void simTest() {
        String text1 = "我是 一个 普通 字符串";
        String text2 = "我是 一个 普通 字符串";

        Simhash simhash = new Simhash();
        long hash = simhash.hash(StringUtil.split(text1, ' '));
        Assertions.assertTrue(hash != 0);

        simhash.store(hash);
        boolean duplicate = simhash.equals(StringUtil.split(text2, ' '));
        Assertions.assertTrue(duplicate);
    }

}
