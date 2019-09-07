package io.github.dunwu.util.text;

import io.github.dunwu.util.parser.CsvUtil;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class CsvUtilTest {

	final String filepath = "D:\\Codes\\ZP\\Java\\dunwu\\dunwu-common\\src\\test\\resources\\data.csv";

	public enum Headers {

		ecifid, cnt_tran, cnt_lingchen_tran, cnt_is_10to5k_30min, cnt_trad_n_1h, cnt_recaccount, cnt_days, cnt_log_brand, ratio_log_lingchen, cnt_is_ip_diff, max_amt_diff, label

	}

	@Test
	public void test() throws IOException {
		System.out.println(CsvUtil.getHeaders(filepath));
	}

	@Test
	public void test2() throws IOException {

		Iterable<CSVRecord> records = CsvUtil.getRecords(filepath, Headers.class);
		List<Headers> enumList = EnumUtils.getEnumList(Headers.class);
		for (CSVRecord record : records) {
			enumList.forEach(element -> {
				String header = element.name();
				System.out.print(record.get(header) + ",");
			});
			System.out.println();
		}
	}

}
