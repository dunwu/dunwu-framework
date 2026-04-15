package io.github.dunwu.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ClassUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-20
 */
@DisplayName("ClassUtil 单元测试")
class ClassUtilTest {

    // ============================== 测试用内部类 ==============================

    static class GrandParent {
        private String grandField;

    }

    static class Parent extends GrandParent {
        private String parentField;
        private int parentAge;

    }

    static class Child extends Parent {
        private String childField;

    }

    static class Standalone {
        private String field1;
        private int field2;

    }

    @Nested
    @DisplayName("getAllFields 测试")
    class GetAllFieldsTest {

        @Test
        @DisplayName("无父类时应只返回本类字段")
        void shouldReturnOnlyOwnFieldsWhenNoParent() {
            Field[] fields = ClassUtil.getAllFields(Standalone.class);

            Set<String> fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());

            assertThat(fieldNames).contains("field1", "field2");
        }

        @Test
        @DisplayName("有单层父类时应包含本类及父类字段")
        void shouldIncludeParentFieldsWithSingleInheritance() {
            Field[] fields = ClassUtil.getAllFields(Parent.class);

            Set<String> fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());

            assertThat(fieldNames).contains("parentField", "parentAge", "grandField");
        }

        @Test
        @DisplayName("多层继承时应包含所有父类字段")
        void shouldIncludeAllAncestorFields() {
            Field[] fields = ClassUtil.getAllFields(Child.class);

            Set<String> fieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet());

            assertThat(fieldNames).contains("childField", "parentField", "parentAge", "grandField");
        }

        @Test
        @DisplayName("字段数量应等于所有层级字段之和")
        void shouldHaveCorrectFieldCount() {
            Field[] fields = ClassUtil.getAllFields(Child.class);

            // childField(1) + parentField + parentAge(2) + grandField(1) = 4
            assertThat(fields).hasSize(4);
        }

        @Test
        @DisplayName("对 null 类传入不应抛出异常，返回空数组")
        void shouldReturnEmptyArrayForNullClass() {
            Field[] fields = ClassUtil.getAllFields(null);

            assertThat(fields).isEmpty();
        }

    }

}
