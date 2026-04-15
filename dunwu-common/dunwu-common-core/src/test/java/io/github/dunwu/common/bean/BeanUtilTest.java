package io.github.dunwu.common.bean;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link BeanUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@DisplayName("BeanUtil 单元测试")
class BeanUtilTest {

    // ============================== 测试用内部类 ==============================

    @Getter
    @Setter
    static class Source {

        private String name;
        private Integer age;

        Source() { }

        Source(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

    }

    @Getter
    @Setter
    static class Target {

        private String name;
        private Integer age;

    }

    @Nested
    @DisplayName("copyProperties 测试")
    class CopyPropertiesTest {

        @Test
        @DisplayName("正常拷贝属性到目标类型")
        void shouldCopyPropertiesToTargetClass() {
            Source source = new Source("Alice", 30);

            Target target = BeanUtil.copyProperties(source, Target.class);

            assertThat(target).isNotNull();
            assertThat(target.getName()).isEqualTo("Alice");
            assertThat(target.getAge()).isEqualTo(30);
        }

        @Test
        @DisplayName("深拷贝应生成独立对象，修改源不影响目标")
        void shouldDeepCopyAndBeIndependentFromSource() {
            Source source = new Source("Bob", 25);

            Target target = BeanUtil.copyProperties(source, Target.class);
            source.setName("Changed");

            assertThat(target.getName()).isEqualTo("Bob");
        }

        @Test
        @DisplayName("拷贝到同类型")
        void shouldCopyToSameType() {
            Source source = new Source("Carol", 20);

            Source copy = BeanUtil.copyProperties(source, Source.class);

            assertThat(copy).isNotSameAs(source);
            assertThat(copy.getName()).isEqualTo("Carol");
            assertThat(copy.getAge()).isEqualTo(20);
        }

    }

    @Nested
    @DisplayName("toBeanCollection 测试")
    class ToBeanCollectionTest {

        @Test
        @DisplayName("正常转换集合元素到目标类型")
        void shouldConvertCollectionToTargetType() {
            List<Source> sources = Arrays.asList(new Source("Alice", 30), new Source("Bob", 25));

            Collection<Target> targets = BeanUtil.toBeanCollection(sources, Target.class);

            assertThat(targets).hasSize(2);
            List<Target> list = (List<Target>)targets;
            assertThat(list.get(0).getName()).isEqualTo("Alice");
            assertThat(list.get(1).getName()).isEqualTo("Bob");
        }

        @Test
        @DisplayName("空集合应返回空集合")
        void shouldReturnEmptyCollectionWhenSourceIsEmpty() {
            Collection<Target> targets = BeanUtil.toBeanCollection(List.of(), Target.class);

            assertThat(targets).isEmpty();
        }

        @Test
        @DisplayName("null 集合应返回空集合")
        void shouldReturnEmptyCollectionWhenSourceIsNull() {
            Collection<Target> targets = BeanUtil.toBeanCollection(null, Target.class);

            assertThat(targets).isEmpty();
        }

    }

    @Nested
    @DisplayName("toBeanList 测试")
    class ToBeanListTest {

        @Test
        @DisplayName("正常转换集合元素到目标类型 List")
        void shouldConvertCollectionToTargetTypeList() {
            List<Source> sources = Arrays.asList(new Source("Dave", 40), new Source("Eve", 35));

            List<Target> targets = BeanUtil.toBeanList(sources, Target.class);

            assertThat(targets).isInstanceOf(List.class).hasSize(2);
            assertThat(targets.get(0).getName()).isEqualTo("Dave");
            assertThat(targets.get(1).getName()).isEqualTo("Eve");
        }

        @Test
        @DisplayName("空集合应返回空 List")
        void shouldReturnEmptyListWhenSourceIsEmpty() {
            List<Target> targets = BeanUtil.toBeanList(List.of(), Target.class);

            assertThat(targets).isInstanceOf(List.class).isEmpty();
        }

        @Test
        @DisplayName("null 集合应返回空 List")
        void shouldReturnEmptyListWhenSourceIsNull() {
            List<Target> targets = BeanUtil.toBeanList(null, Target.class);

            assertThat(targets).isInstanceOf(List.class).isEmpty();
        }

    }

}
