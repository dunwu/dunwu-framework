package io.github.dunwu.tool.text.csv;

import io.github.dunwu.tool.io.resource.ResourceUtil;
import io.github.dunwu.tool.util.CharsetUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    public void readTest() {
        CsvReader reader = new CsvReader();
        CsvData data = reader.read(ResourceUtil.getReader("test.csv", CharsetUtil.CHARSET_UTF_8));
        Assertions.assertEquals("关注\"对象\"", data.getRow(0).get(2));
    }

}
