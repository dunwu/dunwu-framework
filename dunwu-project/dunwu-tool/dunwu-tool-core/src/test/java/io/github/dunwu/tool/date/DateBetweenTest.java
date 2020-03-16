package io.github.dunwu.tool.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateBetweenTest {

    @Test
    public void betweenYearTest() {
        Date start = DateUtil.parse("2017-02-01 12:23:46");
        Date end = DateUtil.parse("2018-02-01 12:23:46");
        long betweenYear = new DateBetween(start, end).betweenYear(false);
        Assertions.assertEquals(1, betweenYear);

        Date start1 = DateUtil.parse("2017-02-01 12:23:46");
        Date end1 = DateUtil.parse("2018-03-01 12:23:46");
        long betweenYear1 = new DateBetween(start1, end1).betweenYear(false);
        Assertions.assertEquals(1, betweenYear1);

        // 不足1年
        Date start2 = DateUtil.parse("2017-02-01 12:23:46");
        Date end2 = DateUtil.parse("2018-02-01 11:23:46");
        long betweenYear2 = new DateBetween(start2, end2).betweenYear(false);
        Assertions.assertEquals(0, betweenYear2);
    }

    @Test
    public void betweenYearTest2() {
        Date start = DateUtil.parse("2000-02-29");
        Date end = DateUtil.parse("2018-02-28");
        long betweenYear = new DateBetween(start, end).betweenYear(false);
        Assertions.assertEquals(18, betweenYear);
    }

    @Test
    public void betweenMonthTest() {
        Date start = DateUtil.parse("2017-02-01 12:23:46");
        Date end = DateUtil.parse("2018-02-01 12:23:46");
        long betweenMonth = new DateBetween(start, end).betweenMonth(false);
        Assertions.assertEquals(12, betweenMonth);

        Date start1 = DateUtil.parse("2017-02-01 12:23:46");
        Date end1 = DateUtil.parse("2018-03-01 12:23:46");
        long betweenMonth1 = new DateBetween(start1, end1).betweenMonth(false);
        Assertions.assertEquals(13, betweenMonth1);

        // 不足
        Date start2 = DateUtil.parse("2017-02-01 12:23:46");
        Date end2 = DateUtil.parse("2018-02-01 11:23:46");
        long betweenMonth2 = new DateBetween(start2, end2).betweenMonth(false);
        Assertions.assertEquals(11, betweenMonth2);
    }

    @Test
    public void betweenMinuteTest() {
        Date date1 = DateUtil.parse("2017-03-01 20:33:23");
        Date date2 = DateUtil.parse("2017-03-01 23:33:23");
        String formatBetween = DateUtil.formatBetween(date1, date2, BetweenFormater.Level.SECOND);
        Assertions.assertEquals("3小时", formatBetween);
    }

}
