package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link SerializableUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-05
 */
@DisplayName("SerializableUtil 单元测试")
class SerializableUtilTest {

    // ============================== getLong ==============================

    @Nested
    @DisplayName("getLong 测试")
    class GetLongTest {

        @Test
        @DisplayName("Long 值直接返回")
        void shouldReturnLongWhenValueIsLong() {
            Long result = SerializableUtil.getLong(100L);

            assertThat(result).isEqualTo(100L);
        }

        @Test
        @DisplayName("Integer 值转换为 Long")
        void shouldConvertIntegerToLong() {
            Long result = SerializableUtil.getLong(42);

            assertThat(result).isEqualTo(42L);
        }

        @Test
        @DisplayName("其他类型返回 null")
        void shouldReturnNullForUnsupportedType() {
            Long result = SerializableUtil.getLong("not a number");

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("null 值返回 null")
        void shouldReturnNullForNullValue() {
            Long result = SerializableUtil.getLong(null);

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("Integer.MAX_VALUE 转换为 Long 正确")
        void shouldConvertMaxIntegerToLong() {
            Long result = SerializableUtil.getLong(Integer.MAX_VALUE);

            assertThat(result).isEqualTo((long) Integer.MAX_VALUE);
        }
    }

    // ============================== getInteger ==============================

    @Nested
    @DisplayName("getInteger 测试")
    class GetIntegerTest {

        @Test
        @DisplayName("Integer 值直接返回")
        void shouldReturnIntegerWhenValueIsInteger() {
            Integer result = SerializableUtil.getInteger(99);

            assertThat(result).isEqualTo(99);
        }

        @Test
        @DisplayName("Long 值转换为 Integer")
        void shouldConvertLongToInteger() {
            Integer result = SerializableUtil.getInteger(50L);

            assertThat(result).isEqualTo(50);
        }

        @Test
        @DisplayName("其他类型返回 null")
        void shouldReturnNullForUnsupportedType() {
            Integer result = SerializableUtil.getInteger("not a number");

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("null 值返回 null")
        void shouldReturnNullForNullValue() {
            Integer result = SerializableUtil.getInteger(null);

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("Long 值在 Integer 范围内时转换正确")
        void shouldConvertLongWithinIntRangeProperly() {
            Long longVal = 12345L;
            Integer result = SerializableUtil.getInteger(longVal);

            assertThat(result).isEqualTo(12345);
        }
    }

}
