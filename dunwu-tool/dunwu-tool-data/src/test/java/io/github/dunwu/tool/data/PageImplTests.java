package io.github.dunwu.tool.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link PageImpl} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-20
 */
public class PageImplTests {

    private static final List<Integer> data = new ArrayList<>();

    @BeforeAll
    public static void init() {
        for (int i = 0; i < 100; i++) {
            data.add(i);
        }
    }

    @Nested
    @DisplayName("构造器方法测试")
    class ConstructMethod {

        @Test
        @DisplayName("默认构造方法")
        public void testPageImpl() {

            PageImpl<?> page = new PageImpl<>();

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(10);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(0);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(0);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(0);

            Assertions.assertThat(page.getContent()).isEmpty();
            Assertions.assertThat(page.hasContent()).isFalse();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(0);

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("构造方法二")
        public void testPageImpl2() {

            PageImpl<Integer> page = new PageImpl<>(data, PageQuery.of(1, 10));

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(10);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("构造方法三")
        public void testPageImpl3() {

            // 数据总量刚好一页
            PageImpl<Integer> page = new PageImpl<>(data, PageRequest.of(0, 100));

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = new PageImpl<>(data, PageRequest.of(0, 101));

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(101);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(101);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = new PageImpl<>(data, PageRequest.of(0, 10));

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("构造方法四")
        public void testPageImpl4() {

            // 数据总量刚好一页
            PageImpl<Integer> page = new PageImpl<>(data, PageQuery.of(1, 100), 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = new PageImpl<>(data, PageQuery.of(1, 100), 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为第一页
            PageImpl<Integer> page3 = new PageImpl<>(data, PageQuery.of(1, 10), 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = new PageImpl<>(data, PageQuery.of(10, 10), 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("构造方法五")
        public void testPageImpl5() {

            // 数据总量刚好一页
            PageImpl<Integer> page = new PageImpl<>(data, PageRequest.of(0, 100), 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = new PageImpl<>(data, PageRequest.of(0, 100), 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = new PageImpl<>(data, PageRequest.of(0, 10), 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = new PageImpl<>(data, PageRequest.of(9, 10), 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("构造方法六")
        public void testPageImpl6() {

            // 数据总量刚好一页
            PageImpl<Integer> page = new PageImpl<>(data, 1, 100, 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = new PageImpl<>(data, 1, 100, 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = new PageImpl<>(data, 1, 10, 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = new PageImpl<>(data, 10, 10, 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

    }

    @Nested
    @DisplayName("静态构造方法测试")
    class OfMethod {

        @Test
        @DisplayName("PageImpl.of 测试一")
        public void testOf1() {

            PageImpl<?> page = PageImpl.of();

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(10);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(0);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(0);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(0);

            Assertions.assertThat(page.getContent()).isEmpty();
            Assertions.assertThat(page.hasContent()).isFalse();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(0);

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("PageImpl.of 测试二")
        public void testOf2() {

            PageImpl<Integer> page = PageImpl.of(data, PageQuery.of(1, 10));

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(10);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("PageImpl.of 测试三")
        public void testOf3() {

            // 数据总量刚好一页
            PageImpl<Integer> page = PageImpl.of(data, PageRequest.of(0, 100));

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = PageImpl.of(data, PageRequest.of(0, 101));

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(101);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(101);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = PageImpl.of(data, PageRequest.of(0, 10));

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("PageImpl.of 测试四")
        public void testOf4() {

            // 数据总量刚好一页
            PageImpl<Integer> page = PageImpl.of(data, PageQuery.of(1, 100), 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = PageImpl.of(data, PageQuery.of(1, 100), 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为第一页
            PageImpl<Integer> page3 = PageImpl.of(data, PageQuery.of(1, 10), 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = PageImpl.of(data, PageQuery.of(10, 10), 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("PageImpl.of 测试五")
        public void testOf5() {

            // 数据总量刚好一页
            PageImpl<Integer> page = PageImpl.of(data, PageRequest.of(0, 100), 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = PageImpl.of(data, PageRequest.of(0, 100), 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = PageImpl.of(data, PageRequest.of(0, 10), 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = PageImpl.of(data, PageRequest.of(9, 10), 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("PageImpl.of 测试六")
        public void testOf6() {

            // 数据总量刚好一页
            PageImpl<Integer> page = PageImpl.of(data, 1, 100, 100);

            Assertions.assertThat(page.getNumber()).isEqualTo(1);
            Assertions.assertThat(page.getSize()).isEqualTo(100);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(1);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isFalse();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isTrue();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();

            // 数据总量不足一页
            PageImpl<Integer> page2 = PageImpl.of(data, 1, 100, 101);

            Assertions.assertThat(page2.getNumber()).isEqualTo(1);
            Assertions.assertThat(page2.getSize()).isEqualTo(100);
            Assertions.assertThat(page2.getSort()).isEmpty();
            Assertions.assertThat(page2.getTotal()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalElements()).isEqualTo(101L);
            Assertions.assertThat(page2.getTotalPages()).isEqualTo(2);

            Assertions.assertThat(page2.getContent()).isNotEmpty();
            Assertions.assertThat(page2.hasContent()).isTrue();
            Assertions.assertThat(page2.getNumberOfElements()).isEqualTo(100);

            Assertions.assertThat(page2.hasPrevious()).isFalse();
            Assertions.assertThat(page2.hasNext()).isFalse();
            Assertions.assertThat(page2.isFirst()).isTrue();
            Assertions.assertThat(page2.isLast()).isTrue();

            Assertions.assertThat(page2.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getPageSize()).isEqualTo(100);
            Assertions.assertThat(page2.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page2.getPageable().getSort()).isEmpty();

            // 数据总量超过一页
            PageImpl<Integer> page3 = PageImpl.of(data, 1, 10, 100);

            Assertions.assertThat(page3.getNumber()).isEqualTo(1);
            Assertions.assertThat(page3.getSize()).isEqualTo(10);
            Assertions.assertThat(page3.getSort()).isEmpty();
            Assertions.assertThat(page3.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page3.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page3.getContent()).isNotEmpty();
            Assertions.assertThat(page3.hasContent()).isTrue();
            Assertions.assertThat(page3.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page3.hasPrevious()).isFalse();
            Assertions.assertThat(page3.hasNext()).isTrue();
            Assertions.assertThat(page3.isFirst()).isTrue();
            Assertions.assertThat(page3.isLast()).isFalse();

            Assertions.assertThat(page3.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page3.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page3.getPageable().getSort()).isEmpty();

            // 数据总量超过一页，并且为最后一页
            PageImpl<Integer> page4 = new PageImpl<>(data, 10, 10, 100);

            Assertions.assertThat(page4.getNumber()).isEqualTo(10);
            Assertions.assertThat(page4.getSize()).isEqualTo(10);
            Assertions.assertThat(page4.getSort()).isEmpty();
            Assertions.assertThat(page4.getTotal()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page4.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page4.getContent()).isNotEmpty();
            Assertions.assertThat(page4.hasContent()).isTrue();
            Assertions.assertThat(page4.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page4.hasPrevious()).isTrue();
            Assertions.assertThat(page4.hasNext()).isFalse();
            Assertions.assertThat(page4.isFirst()).isFalse();
            Assertions.assertThat(page4.isLast()).isTrue();

            Assertions.assertThat(page4.getPageable().getPageNumber()).isEqualTo(9);
            Assertions.assertThat(page4.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page4.getPageable().getOffset()).isEqualTo(90);
            Assertions.assertThat(page4.getPageable().getSort()).isEmpty();
        }

    }

    @Nested
    @DisplayName("转换方法测试")
    class ConvertMethod {

        @Test
        @DisplayName("map 方法")
        public void testMap() {
            PageImpl<Integer> pageImpl = PageImpl.of(data, PageQuery.of(1, 10));

            Page<Long> page = pageImpl.map(value -> {
                if (value == null) {
                    return null;
                }
                return value.longValue();
            });

            Assertions.assertThat(page.getNumber()).isEqualTo(0);
            Assertions.assertThat(page.getSize()).isEqualTo(10);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotalElements()).isEqualTo(100L);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(10);

            Assertions.assertThat(page.getContent()).isNotEmpty();
            Assertions.assertThat(page.hasContent()).isTrue();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(10);

            Assertions.assertThat(page.hasPrevious()).isFalse();
            Assertions.assertThat(page.hasNext()).isTrue();
            Assertions.assertThat(page.isFirst()).isTrue();
            Assertions.assertThat(page.isLast()).isFalse();

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(10);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("getPageable 方法")
        public void testGetPageable() {
            PageImpl<Integer> page1 = PageImpl.of(data, PageQuery.of(1, 10), 100);
            Pageable previousPageable1 = page1.previousPageable();
            Assertions.assertThat(previousPageable1).isEqualTo(Pageable.unpaged());
            Pageable nextPageable1 = page1.nextPageable();
            Assertions.assertThat(nextPageable1.getPageNumber()).isEqualTo(1);
            Assertions.assertThat(nextPageable1.getPageNumber()).isEqualTo(1);
            Assertions.assertThat(nextPageable1.getPageSize()).isEqualTo(10);
            Assertions.assertThat(nextPageable1.getOffset()).isEqualTo(10);
            Assertions.assertThat(nextPageable1.getSort()).isEmpty();

            PageImpl<Integer> page2 = PageImpl.of(data, PageQuery.of(10, 10), 100);
            Pageable nextPageable2 = page2.nextPageable();
            Assertions.assertThat(nextPageable2).isEqualTo(Pageable.unpaged());
            Pageable pageable2 = page2.getPageable();
            Assertions.assertThat(pageable2.getPageNumber()).isEqualTo(9);
            Assertions.assertThat(pageable2.getPageSize()).isEqualTo(10);
            Assertions.assertThat(pageable2.getOffset()).isEqualTo(90);
            Assertions.assertThat(pageable2.getSort()).isEmpty();
        }

    }

    @Test
    @DisplayName("hasPrevious 和 hasNext 方法")
    public void testHasPreviousAndHasNext() {
        PageImpl<Integer> page = PageImpl.of(data, 1, 10, 100);
        Assertions.assertThat(page.hasPrevious()).isFalse();
        Assertions.assertThat(page.hasNext()).isTrue();

        PageImpl<Integer> page2 = PageImpl.of(data, 5, 10, 100);
        Assertions.assertThat(page2.hasPrevious()).isTrue();
        Assertions.assertThat(page2.hasNext()).isTrue();

        PageImpl<Integer> page3 = PageImpl.of(data, 10, 10, 100);
        Assertions.assertThat(page3.hasPrevious()).isTrue();
        Assertions.assertThat(page3.hasNext()).isFalse();
    }

    @Test
    @DisplayName("previous 方法")
    public void testPrevious() {
        PageQuery query = new PageQuery(10, 10);
        for (int i = 9; i > 1; i--) {
            query = query.previous();
            Assertions.assertThat(query.getPage()).isEqualTo(i);
        }
    }

    @Test
    @DisplayName("getOrderClause 方法")
    public void testGetOrderClause() {
        Collection<Order> orders = new ArrayList<>();
        orders.add(Order.parse("id,asc"));
        orders.add(new Order("update_time", Order.Direction.desc));
        PageQuery query = new PageQuery(1, 10, orders);
        Assertions.assertThat(query.getOrderClause()).isEqualTo("id,asc;update_time,desc");
        System.out.println(query.getOrderClause());
    }

}
