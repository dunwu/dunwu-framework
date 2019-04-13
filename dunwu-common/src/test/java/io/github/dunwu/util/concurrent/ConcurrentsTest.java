package io.github.dunwu.util.concurrent;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import io.github.dunwu.util.concurrent.jsr166e.LongAdder;

public class ConcurrentsTest {

    @Test
    public void longAdder() {
        LongAdder counter = Concurrents.longAdder();
        counter.increment();
        counter.add(2);
        assertThat(counter.longValue()).isEqualTo(3L);
    }

}
