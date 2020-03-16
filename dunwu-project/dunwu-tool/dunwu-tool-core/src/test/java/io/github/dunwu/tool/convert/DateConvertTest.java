package io.github.dunwu.tool.convert;

import io.github.dunwu.tool.date.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvertTest {

    @Test
    public void toDateTest() {
        String a = "2017-05-06";
        Date value = Convert.toDate(a);
        Assertions.assertEquals(a, DateUtil.formatDate(value));

        long timeLong = DateUtil.toDateTime().getTime();
        Date value2 = Convert.toDate(timeLong);
        Assertions.assertEquals(timeLong, value2.getTime());
    }

    @Test
    public void toDateFromLocalDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Date value = Convert.toDate(localDateTime);
        Assertions.assertNotNull(value);
        Assertions.assertEquals("2017-05-06", DateUtil.formatDate(value));
    }

    @Test
    public void toSqlDateTest() {
        String a = "2017-05-06";
        java.sql.Date value = Convert.convert(java.sql.Date.class, a);
        Assertions.assertEquals("2017-05-06", value.toString());

        long timeLong = DateUtil.toDateTime().getTime();
        java.sql.Date value2 = Convert.convert(java.sql.Date.class, timeLong);
        Assertions.assertEquals(timeLong, value2.getTime());
    }

}
