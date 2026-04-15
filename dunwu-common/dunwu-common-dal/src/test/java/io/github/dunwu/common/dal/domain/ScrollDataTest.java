package io.github.dunwu.common.dal.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link ScrollData} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class ScrollDataTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            ScrollData<String> data = new ScrollData<>();
            Assertions.assertThat(data.getScrollId()).isNull();
            Assertions.assertThat(data.getSize()).isEqualTo(10);
            Assertions.assertThat(data.getTotal()).isEqualTo(0L);
            Assertions.assertThat(data.getContent()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            List<String> content = Arrays.asList("a", "b", "c");
            ScrollData<String> data = new ScrollData<>("scroll-1", 20, 100L, content);
            Assertions.assertThat(data.getScrollId()).isEqualTo("scroll-1");
            Assertions.assertThat(data.getSize()).isEqualTo(20);
            Assertions.assertThat(data.getTotal()).isEqualTo(100L);
            Assertions.assertThat(data.getContent()).isEqualTo(content);
        }

        @Test
        @DisplayName("Builder 构造方法")
        public void testBuilderConstructor() {
            List<String> content = Arrays.asList("x", "y");
            ScrollData<String> data =
                ScrollData.<String>builder().scrollId("scroll-2").size(50).total(500L).content(content).build();
            Assertions.assertThat(data.getScrollId()).isEqualTo("scroll-2");
            Assertions.assertThat(data.getSize()).isEqualTo(50);
            Assertions.assertThat(data.getTotal()).isEqualTo(500L);
            Assertions.assertThat(data.getContent()).isEqualTo(content);
        }

    }

    @Nested
    @DisplayName("业务方法测试")
    class BusinessMethodTest {

        @Test
        @DisplayName("hasContent - 有数据")
        public void testHasContent() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            Assertions.assertThat(data.hasContent()).isTrue();
        }

        @Test
        @DisplayName("hasContent - 空列表")
        public void testHasContentEmpty() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Collections.emptyList());
            Assertions.assertThat(data.hasContent()).isFalse();
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setScrollId")
        public void testSetScrollId() {
            ScrollData<String> data = new ScrollData<>();
            data.setScrollId("new-scroll-id");
            Assertions.assertThat(data.getScrollId()).isEqualTo("new-scroll-id");
        }

        @Test
        @DisplayName("setSize")
        public void testSetSize() {
            ScrollData<String> data = new ScrollData<>();
            data.setSize(50);
            Assertions.assertThat(data.getSize()).isEqualTo(50);
        }

        @Test
        @DisplayName("setTotal")
        public void testSetTotal() {
            ScrollData<String> data = new ScrollData<>();
            data.setTotal(200L);
            Assertions.assertThat(data.getTotal()).isEqualTo(200L);
        }

        @Test
        @DisplayName("setContent")
        public void testSetContent() {
            ScrollData<String> data = new ScrollData<>();
            List<String> newContent = Arrays.asList("new");
            data.setContent(newContent);
            Assertions.assertThat(data.getContent()).isEqualTo(newContent);
        }

    }

}
