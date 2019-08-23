package io.github.dunwu.util.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

/**
 * CSV 文件读写工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="http://commons.apache.org/proper/commons-csv/user-guide.html">Apache Common Csv</a>
 */
public class CsvUtil {

    public static String[] getHeaders(String filepath) throws IOException {
        return getHeaders(new File(filepath));
    }

    public static String[] getHeaders(File file) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();
        line = line.replaceAll("\"", "");
        return line.split(",");
    }

    public static Iterable<CSVRecord> getRecords(String filepath) throws IOException {
        return getRecords(new File(filepath));
    }

    public static Iterable<CSVRecord> getRecords(File file) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        Reader in = new FileReader(file);
        return CSVFormat.RFC4180.withFirstRecordAsHeader()
                                .parse(in);
    }

    public static Iterable<CSVRecord> getRecords(String filepath, Class<? extends Enum<?>> headerEnum)
        throws IOException {
        return getRecords(new File(filepath), headerEnum);
    }

    public static Iterable<CSVRecord> getRecords(File file, Class<? extends Enum<?>> headerEnum) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        Reader in = new FileReader(file);
        return CSVFormat.RFC4180.withHeader(headerEnum)
                                .parse(in);
    }
}
