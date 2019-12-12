package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetUtilTest {

    @Test
    public void testGbk() {
        Assertions.assertEquals(Charset.forName("GBK"), CharsetUtil.CHARSET_GBK);
    }

    @Test
    public void testUtf8() {
        Assertions.assertEquals(StandardCharsets.UTF_8, CharsetUtil.CHARSET_UTF_8);
    }

    @Test
    public void testIso88591() {
        Assertions.assertEquals(StandardCharsets.ISO_8859_1, CharsetUtil.CHARSET_ISO_8859_1);
    }

}
