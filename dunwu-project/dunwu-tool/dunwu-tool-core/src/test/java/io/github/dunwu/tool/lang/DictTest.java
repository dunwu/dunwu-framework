package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.date.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DictTest {

    @Test
    public void dictTest() {
        Dict dict = Dict.create()
            .set("key1", 1)//int
            .set("key2", 1000L)//long
            .set("key3", DateTime.now());//Date

        Long v2 = dict.getLong("key2");
        Assertions.assertEquals(Long.valueOf(1000L), v2);
    }

}
