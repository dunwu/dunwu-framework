package io.github.dunwu.tool.date;

import io.github.dunwu.tool.date.format.FastDateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

public class TimeZoneTest {

    @Test
    public void timeZoneConvertTest() {
        DateTime dt = DateUtil.parse("2018-07-10 21:44:32", //
            FastDateFormat.getInstance(DatePattern.NORM_DATETIME_PATTERN, TimeZone.getTimeZone("GMT+8:00")));
        Assertions.assertEquals("2018-07-10 21:44:32", dt.toString());

        dt.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        int hour = dt.getField(DateField.HOUR_OF_DAY);
        Assertions.assertEquals(14, hour);
        Assertions.assertEquals("2018-07-10 14:44:32", dt.toString());
    }

}
