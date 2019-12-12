package io.github.dunwu.tool.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberWordFormatTest {

    @Test
    public void formatTest() {
        String format = NumberWordFormater.format(100.23);
        Assertions.assertEquals("ONE HUNDRED AND CENTS TWENTY THREE ONLY", format);

        String format2 = NumberWordFormater.format("2100.00");
        Assertions.assertEquals("TWO THOUSAND ONE HUNDRED AND CENTS  ONLY", format2);
    }

}
