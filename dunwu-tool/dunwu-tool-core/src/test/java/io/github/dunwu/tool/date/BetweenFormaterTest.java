package io.github.dunwu.tool.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BetweenFormaterTest {

    @Test
    public void formatTest() {
        long betweenMs =
            DateUtil.betweenMs(DateUtil.parse("2017-01-01 22:59:59"), DateUtil.parse("2017-01-02 23:59:58"));
        BetweenFormater formater = new BetweenFormater(betweenMs, BetweenFormater.Level.MILLSECOND, 1);
        Assertions.assertEquals(formater.toString(), "1天");
    }

    @Test
    public void formatBetweenTest() {
        long betweenMs =
            DateUtil.betweenMs(DateUtil.parse("2018-07-16 11:23:19"), DateUtil.parse("2018-07-16 11:23:20"));
        BetweenFormater formater = new BetweenFormater(betweenMs, BetweenFormater.Level.SECOND, 1);
        Assertions.assertEquals(formater.toString(), "1秒");
    }

    @Test
    public void formatTest2() {
        BetweenFormater formater = new BetweenFormater(584, BetweenFormater.Level.SECOND, 1);
        Assertions.assertEquals(formater.toString(), "0秒");
    }

}
