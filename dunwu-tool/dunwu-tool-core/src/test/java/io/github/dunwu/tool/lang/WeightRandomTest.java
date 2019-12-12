package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.collection.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeightRandomTest {

    @Test
    public void weightRandomTest() {
        WeightRandom<String> random = WeightRandom.create();
        random.add("A", 10);
        random.add("B", 50);
        random.add("C", 100);

        String result = random.next();
        Assertions.assertTrue(CollectionUtil.newArrayList("A", "B", "C").contains(result));
    }

}
