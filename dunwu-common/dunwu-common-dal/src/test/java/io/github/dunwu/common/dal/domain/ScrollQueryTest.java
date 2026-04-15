package io.github.dunwu.common.dal.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ScrollQuery} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class ScrollQueryTest {

    @Test
    @DisplayName("默认构造方法 - 默认 size 为 100")
    public void testDefaultConstructor() {
        ScrollQuery query = new ScrollQuery();
        Assertions.assertThat(query.getScrollId()).isNull();
        Assertions.assertThat(query.getSize()).isEqualTo(ScrollQuery.DEFAULT_SIZE);
    }

    @Test
    @DisplayName("setScrollId")
    public void testSetScrollId() {
        ScrollQuery query = new ScrollQuery();
        query.setScrollId("scroll-123");
        Assertions.assertThat(query.getScrollId()).isEqualTo("scroll-123");
    }

    @Test
    @DisplayName("setSize")
    public void testSetSize() {
        ScrollQuery query = new ScrollQuery();
        query.setSize(50);
        Assertions.assertThat(query.getSize()).isEqualTo(50);
    }

    @Test
    @DisplayName("DEFAULT_SIZE 常量")
    public void testDefaultSizeConstant() {
        Assertions.assertThat(ScrollQuery.DEFAULT_SIZE).isEqualTo(100);
    }

}
