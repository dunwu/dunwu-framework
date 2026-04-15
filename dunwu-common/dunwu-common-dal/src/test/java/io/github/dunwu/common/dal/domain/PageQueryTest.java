package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.exception.DefaultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link PageQuery} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class PageQueryTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            PageQuery query = new PageQuery();
            Assertions.assertThat(query.getPage()).isEqualTo(PageQuery.FIRST_PAGE);
            Assertions.assertThat(query.getSize()).isEqualTo(PageQuery.DEFAULT_SIZE);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
        }

        @Test
        @DisplayName("page + size 构造方法")
        public void testPageAndSizeConstructor() {
            PageQuery query = new PageQuery(2, 20);
            Assertions.assertThat(query.getPage()).isEqualTo(2);
            Assertions.assertThat(query.getSize()).isEqualTo(20);
            Assertions.assertThat(query.getStart()).isEqualTo(20);
        }

        @Test
        @DisplayName("page + size + orders 构造方法")
        public void testPageSizeOrdersConstructor() {
            List<Order> orders = Arrays.asList(new Order("id", Order.Direction.asc));
            PageQuery query = new PageQuery(1, 10, orders);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getOrders()).hasSize(1);
        }

        @Test
        @DisplayName("page 小于 1 抛异常")
        public void testPageLessThanOne() {
            Assertions.assertThatThrownBy(() -> new PageQuery(0, 10)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("page 不能小于 1");
        }

        @Test
        @DisplayName("size 小于等于 0 抛异常")
        public void testSizeLessThanOrEqualZero() {
            Assertions.assertThatThrownBy(() -> new PageQuery(1, 0)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("size 必须大于 0");
        }

    }

    @Nested
    @DisplayName("业务方法测试")
    class BusinessMethodTest {

        @Test
        @DisplayName("getStart - 第一项")
        public void testGetStartFirstPage() {
            PageQuery query = new PageQuery(1, 10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
        }

        @Test
        @DisplayName("getStart - 第二项")
        public void testGetStartSecondPage() {
            PageQuery query = new PageQuery(2, 10);
            Assertions.assertThat(query.getStart()).isEqualTo(10);
        }

        @Test
        @DisplayName("next - 下一页")
        public void testNext() {
            PageQuery query = new PageQuery(1, 10);
            PageQuery next = query.next();
            Assertions.assertThat(next.getPage()).isEqualTo(2);
            Assertions.assertThat(next.getSize()).isEqualTo(10);
        }

        @Test
        @DisplayName("previous - 第一页返回自身")
        public void testPreviousFirstPage() {
            PageQuery query = new PageQuery(1, 10);
            PageQuery previous = query.previous();
            Assertions.assertThat(previous.getPage()).isEqualTo(1);
            Assertions.assertThat(previous).isSameAs(query);
        }

        @Test
        @DisplayName("previous - 第二页返回上一页")
        public void testPreviousSecondPage() {
            PageQuery query = new PageQuery(2, 10);
            PageQuery previous = query.previous();
            Assertions.assertThat(previous.getPage()).isEqualTo(1);
        }

        @Test
        @DisplayName("getOrderClause - 有排序")
        public void testGetOrderClause() {
            List<Order> orders =
                Arrays.asList(new Order("id", Order.Direction.asc), new Order("name", Order.Direction.desc));
            PageQuery query = new PageQuery(1, 10, orders);
            Assertions.assertThat(query.getOrderClause()).isEqualTo("id,asc;name,desc");
        }

        @Test
        @DisplayName("getOrderClause - 无排序")
        public void testGetOrderClauseEmpty() {
            PageQuery query = new PageQuery(1, 10);
            Assertions.assertThat(query.getOrderClause()).isNull();
        }

        @Test
        @DisplayName("toPageRequest")
        public void testToPageRequest() {
            PageQuery query = new PageQuery(2, 20);
            PageRequest request = query.toPageRequest();
            Assertions.assertThat(request.getPageNumber()).isEqualTo(1);
            Assertions.assertThat(request.getPageSize()).isEqualTo(20);
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("of(page, size)")
        public void testOfPageAndSize() {
            PageQuery query = PageQuery.of(3, 15);
            Assertions.assertThat(query.getPage()).isEqualTo(3);
            Assertions.assertThat(query.getSize()).isEqualTo(15);
        }

        @Test
        @DisplayName("of(page, size, orders)")
        public void testOfPageSizeOrders() {
            List<Order> orders = Arrays.asList(new Order("id", Order.Direction.asc));
            PageQuery query = PageQuery.of(1, 10, orders);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getOrders()).hasSize(1);
        }

        @Test
        @DisplayName("of(page, size, sort...)")
        public void testOfPageSizeSort() {
            PageQuery query = PageQuery.of(1, 10, "id,asc", "name,desc");
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getOrders()).hasSize(2);
        }

        @Test
        @DisplayName("of(page, size, sort...) - 空 sort")
        public void testOfPageSizeSortEmpty() {
            PageQuery query = PageQuery.of(1, 10);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
        }

        @Test
        @DisplayName("of(Pageable)")
        public void testOfPageable() {
            Pageable pageable = PageRequest.of(1, 20);
            PageQuery query = PageQuery.of(pageable);
            Assertions.assertThat(query.getPage()).isEqualTo(2);
            Assertions.assertThat(query.getSize()).isEqualTo(20);
        }

        @Test
        @DisplayName("of(Pageable) - 带排序")
        public void testOfPageableWithSort() {
            Sort sort = Sort.by(Sort.Direction.ASC, "id");
            Pageable pageable = PageRequest.of(0, 10, sort);
            PageQuery query = PageQuery.of(pageable);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getOrders()).hasSize(1);
        }

    }

    @Nested
    @DisplayName("静态工具方法测试")
    class StaticUtilityTest {

        @Test
        @DisplayName("getSpringOrder - 正常")
        public void testGetSpringOrder() {
            Sort.Order order = PageQuery.getSpringOrder("id,asc");
            Assertions.assertThat(order.getProperty()).isEqualTo("id");
            Assertions.assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
        }

        @Test
        @DisplayName("getSpringOrder - 格式错误抛异常")
        public void testGetSpringOrderInvalid() {
            Assertions.assertThatThrownBy(() -> PageQuery.getSpringOrder("id")).isInstanceOf(DefaultException.class);
        }

        @Test
        @DisplayName("getSpringOrderList - 正常")
        public void testGetSpringOrderList() {
            List<Sort.Order> orders = PageQuery.getSpringOrderList("id,asc", "name,desc");
            Assertions.assertThat(orders).hasSize(2);
        }

        @Test
        @DisplayName("getSpringOrderList - null 返回 null")
        public void testGetSpringOrderListNull() {
            List<Sort.Order> orders = PageQuery.getSpringOrderList((String[])null);
            Assertions.assertThat(orders).isNull();
        }

        @Test
        @DisplayName("getSpringSort - 正常")
        public void testGetSpringSort() {
            Sort sort = PageQuery.getSpringSort(Arrays.asList("id,asc"));
            Assertions.assertThat(sort).isNotNull();
            Assertions.assertThat(sort.getOrderFor("id")).isNotNull();
        }

        @Test
        @DisplayName("getSpringSort - 空集合返回 null")
        public void testGetSpringSortEmpty() {
            Sort sort = PageQuery.getSpringSort(Collections.emptyList());
            Assertions.assertThat(sort).isNull();
        }

        @Test
        @DisplayName("toPageRequest - 正常")
        public void testToPageRequest() {
            PageQuery query = new PageQuery(2, 20);
            PageRequest request = PageQuery.toPageRequest(query);
            Assertions.assertThat(request.getPageNumber()).isEqualTo(1);
            Assertions.assertThat(request.getPageSize()).isEqualTo(20);
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setSort")
        public void testSetSort() {
            PageQuery query = new PageQuery();
            query.setSort(Arrays.asList("id,asc"));
            Assertions.assertThat(query.getSort()).hasSize(1);
            Assertions.assertThat(query.getOrders()).hasSize(1);
        }

        @Test
        @DisplayName("setOrders")
        public void testSetOrders() {
            PageQuery query = new PageQuery();
            List<Order> orders = Arrays.asList(new Order("id", Order.Direction.asc));
            query.setOrders(orders);
            Assertions.assertThat(query.getOrders()).hasSize(1);
            Assertions.assertThat(query.getSort()).hasSize(1);
        }

    }

}
