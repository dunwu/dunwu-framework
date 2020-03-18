package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanUtilTest {

    @Test
    public void toBoolean() {
        Assertions.assertTrue(BooleanUtil.toBoolean("true"));
        Assertions.assertTrue(BooleanUtil.toBoolean("yes"));
        Assertions.assertTrue(BooleanUtil.toBoolean("t"));
        Assertions.assertTrue(BooleanUtil.toBoolean("OK"));
        Assertions.assertTrue(BooleanUtil.toBoolean("1"));
        Assertions.assertTrue(BooleanUtil.toBoolean("On"));
        Assertions.assertTrue(BooleanUtil.toBoolean("是"));
        Assertions.assertTrue(BooleanUtil.toBoolean("对"));
        Assertions.assertTrue(BooleanUtil.toBoolean("真"));

        Assertions.assertFalse(BooleanUtil.toBoolean("false"));
        Assertions.assertFalse(BooleanUtil.toBoolean("6455434"));
        Assertions.assertFalse(BooleanUtil.toBoolean(""));
    }

    @Test
    @SuppressWarnings("all")
    void negate() {
        Assertions.assertFalse(BooleanUtil.negate(Boolean.TRUE));
        Assertions.assertTrue(BooleanUtil.negate(Boolean.FALSE));
        Assertions.assertFalse(BooleanUtil.negate(true));
        Assertions.assertTrue(BooleanUtil.negate(false));
    }

}
