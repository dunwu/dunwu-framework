package io.github.dunwu.util.base;

import io.github.dunwu.util.collection.ListUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

public class EnumUtilTest {

    public enum Options {
        A,
        B,
        C,
        D;
    }

    @Test
    public void testBits() {
        Assertions.assertThat(EnumUtil.generateBits(Options.class, Options.A))
                  .isEqualTo(1);
        Assertions.assertThat(EnumUtil.generateBits(Options.class, Options.A, Options.B))
                  .isEqualTo(3);

        Assertions.assertThat(EnumUtil.generateBits(Options.class, ListUtil.newArrayList(Options.A)))
                  .isEqualTo(1);
        Assertions.assertThat(EnumUtil.generateBits(Options.class, ListUtil.newArrayList(Options.A, Options.B)))
                  .isEqualTo(3);

        Assertions.assertThat(EnumUtil.processBits(Options.class, 3))
                  .hasSize(2)
                  .containsExactly(Options.A, Options.B);

        long value = EnumUtil.generateBits(Options.class, Options.A, Options.C, Options.D);
        Assertions.assertThat(EnumUtil.processBits(Options.class, value))
                  .hasSize(3)
                  .containsExactly(Options.A, Options.C, Options.D);
    }

    @Test
    public void testString() {
        Assertions.assertThat(EnumUtil.toString(Options.A))
                  .isEqualTo("A");
        Assertions.assertThat(EnumUtil.fromString(Options.class, "B"))
                  .isEqualTo(Options.B);
    }

    @Test
    public void test() {
        EnumSet<Options> set = EnumUtil.allOf(Options.class);
        for (Options item : set) {
            System.out.println(item.name() + " : " + item.ordinal());
        }
    }
}
