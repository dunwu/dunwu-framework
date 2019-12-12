package io.github.dunwu.tool.text.csv;

import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.util.CharsetUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CsvUtilTest {

    @Test
    public void readTest() {
        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        CsvData data = reader.read(FileUtil.file("test.csv"));
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            Assertions.assertThat(csvRow.getRawList()).isNotEmpty();
        }
    }

    @Test
    public void readTest2() {
        CsvReader reader = CsvUtil.getReader();
        reader.read(FileUtil.getUtf8Reader("test.csv"), (csvRow) -> {
            Assertions.assertThat(csvRow.getRawList()).isNotEmpty();
        });
    }

    @Test
    public void writeTest() {
        CsvWriter writer = CsvUtil.getWriter("d:/testWrite.csv", CharsetUtil.CHARSET_UTF_8);
        writer.write(
            new String[] { "a1", "b1", "c1", "123345346456745756756785656" },
            new String[] { "a2", "b2", "c2" },
            new String[] { "a3", "b3", "c3" }
        );
    }

}
