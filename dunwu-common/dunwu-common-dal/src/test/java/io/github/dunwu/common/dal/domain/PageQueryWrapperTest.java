package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.exception.DefaultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * {@link PageQueryWrapper} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class PageQueryWrapperTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            PageQueryWrapper<String> wrapper = new PageQueryWrapper<>();
            Assertions.assertThat(wrapper.isDoPage()).isTrue();
            Assertions.assertThat(wrapper.getEntity()).isNull();
            Assertions.assertThat(wrapper.getEntityClass()).isNull();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("build(entity) - 默认参数")
        public void testBuildWithEntity() {
            PageQueryWrapper<String> wrapper = PageQueryWrapper.build("test");
            Assertions.assertThat(wrapper.getEntity()).isEqualTo("test");
            Assertions.assertThat(wrapper.getPage()).isEqualTo(1);
            Assertions.assertThat(wrapper.getSize()).isEqualTo(10);
            Assertions.assertThat(wrapper.isDoPage()).isFalse();
        }

        @Test
        @DisplayName("build(entity) - null entity 抛异常")
        public void testBuildWithNullEntity() {
            Assertions.assertThatThrownBy(() -> PageQueryWrapper.build((String)null))
                .isInstanceOf(DefaultException.class).hasMessageContaining("entity 不能为 null");
        }

        @Test
        @DisplayName("build(page, size, sort, entity)")
        public void testBuildWithEntityAndParams() {
            PageQueryWrapper<String> wrapper = PageQueryWrapper.build(2, 20, Arrays.asList("id,asc"), "test");
            Assertions.assertThat(wrapper.getPage()).isEqualTo(2);
            Assertions.assertThat(wrapper.getSize()).isEqualTo(20);
            Assertions.assertThat(wrapper.getEntity()).isEqualTo("test");
            Assertions.assertThat(wrapper.isDoPage()).isFalse();
        }

        @Test
        @DisplayName("build(page, size, sort, entity, doPage)")
        public void testBuildWithEntityAndDoPage() {
            PageQueryWrapper<String> wrapper =
                PageQueryWrapper.build(1, 10, Collections.singletonList("id,asc"), "test", true);
            Assertions.assertThat(wrapper.getPage()).isEqualTo(1);
            Assertions.assertThat(wrapper.isDoPage()).isTrue();
        }

        @Test
        @DisplayName("build(page, size, sort, clazz)")
        public void testBuildWithClass() {
            PageQueryWrapper<String> wrapper = PageQueryWrapper.build(1, 10, Arrays.asList("id,asc"), String.class);
            Assertions.assertThat(wrapper.getEntityClass()).isEqualTo(String.class);
            Assertions.assertThat(wrapper.isDoPage()).isTrue();
        }

        @Test
        @DisplayName("build(page, size, sort, clazz) - null clazz 抛异常")
        public void testBuildWithNullClass() {
            Assertions.assertThatThrownBy(() -> PageQueryWrapper.build(1, 10, null, null))
                .isInstanceOf(DefaultException.class).hasMessageContaining("clazz 不能为 null");
        }

        @Test
        @DisplayName("build(page, size, sort, clazz, doPage)")
        public void testBuildWithClassAndDoPage() {
            PageQueryWrapper<String> wrapper = PageQueryWrapper.build(3, 30, null, String.class, false);
            Assertions.assertThat(wrapper.getPage()).isEqualTo(3);
            Assertions.assertThat(wrapper.isDoPage()).isFalse();
        }

        @Test
        @DisplayName("全参 build")
        public void testFullBuild() {
            PageQueryWrapper<String> wrapper =
                PageQueryWrapper.build(true, 5, 50, Arrays.asList("name,desc"), "entity", String.class);
            Assertions.assertThat(wrapper.isDoPage()).isTrue();
            Assertions.assertThat(wrapper.getPage()).isEqualTo(5);
            Assertions.assertThat(wrapper.getSize()).isEqualTo(50);
            Assertions.assertThat(wrapper.getEntity()).isEqualTo("entity");
            Assertions.assertThat(wrapper.getEntityClass()).isEqualTo(String.class);
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setDoPage")
        public void testSetDoPage() {
            PageQueryWrapper<String> wrapper = new PageQueryWrapper<>();
            wrapper.setDoPage(false);
            Assertions.assertThat(wrapper.isDoPage()).isFalse();
        }

        @Test
        @DisplayName("setEntity")
        public void testSetEntity() {
            PageQueryWrapper<String> wrapper = new PageQueryWrapper<>();
            wrapper.setEntity("new entity");
            Assertions.assertThat(wrapper.getEntity()).isEqualTo("new entity");
        }

        @Test
        @DisplayName("setEntityClass")
        public void testSetEntityClass() {
            PageQueryWrapper<String> wrapper = new PageQueryWrapper<>();
            wrapper.setEntityClass(String.class);
            Assertions.assertThat(wrapper.getEntityClass()).isEqualTo(String.class);
        }

    }

}
