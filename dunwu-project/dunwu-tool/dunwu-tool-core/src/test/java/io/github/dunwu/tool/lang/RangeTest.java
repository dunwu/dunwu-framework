package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.date.DateField;
import io.github.dunwu.tool.date.DateTime;
import io.github.dunwu.tool.date.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link Range} 单元测试
 *
 * @author Looly
 */
public class RangeTest {

    @Test
    public void dateRangeTest() {
        DateTime start = DateUtil.parse("2017-01-01");
        DateTime end = DateUtil.parse("2017-01-02");

        final Range<DateTime> range = new Range<DateTime>(start, end, new Range.Steper<DateTime>() {

            @Override
            public DateTime step(DateTime current, DateTime end, int index) {
                if (current.isAfterOrEquals(end)) {
                    return null;
                }
                return current.offsetNew(DateField.DAY_OF_YEAR, 1);
            }
        });

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-01"));
        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-02"));
        Assertions.assertFalse(range.hasNext());
    }

    @Test
    public void intRangeTest() {
        final Range<Integer> range = new Range<Integer>(1, 1, new Range.Steper<Integer>() {

            @Override
            public Integer step(Integer current, Integer end, int index) {
                return current >= end ? null : current + 10;
            }
        });

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(Integer.valueOf(1), range.next());
        Assertions.assertFalse(range.hasNext());
    }

}
