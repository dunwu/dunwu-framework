package io.github.dunwu.tool.text.csv;

import io.github.dunwu.tool.io.IoUtil;
import io.github.dunwu.tool.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

public class CsvParserTest {

    @Test
    public void parseTest1() {
        StringReader reader = StringUtil.getReader("aaa,b\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("b\"bba\"", row.getRawList().get(1));
        IoUtil.close(parser);
    }

    @Test
    public void parseTest2() {
        StringReader reader = StringUtil.getReader("aaa,\"bba\"bbb,ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("\"bba\"bbb", row.getRawList().get(1));
        IoUtil.close(parser);
    }

    @Test
    public void parseTest3() {
        StringReader reader = StringUtil.getReader("aaa,\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("bba", row.getRawList().get(1));
        IoUtil.close(parser);
    }

    @Test
    public void parseTest4() {
        StringReader reader = StringUtil.getReader("aaa,\"\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("", row.getRawList().get(1));
        IoUtil.close(parser);
    }

}
