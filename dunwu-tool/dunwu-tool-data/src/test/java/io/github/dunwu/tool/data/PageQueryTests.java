package io.github.dunwu.tool.data;

import cn.hutool.json.JSONUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link PageQuery} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-03-20
 */
public class PageQueryTests {

    @Nested
    @DisplayName("构造器方法测试")
    class ConstructMethod {

        @Test
        @DisplayName("默认构造方法 PageQuery")
        public void testPageQuery() {
            PageQuery query = new PageQuery();
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isEmpty();
            Assertions.assertThat(query.getSort()).isEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query));
        }

        @Test
        @DisplayName("两参数构造方法 PageQuery")
        public void testPageQuery2() {
            PageQuery query = new PageQuery(1, 10);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isEmpty();
            Assertions.assertThat(query.getSort()).isEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query));
        }

        @Test
        @DisplayName("两参数构造方法 PageQuery 失败案例")
        public void testPageQuery2Fail() {
            try {
                PageQuery query = new PageQuery(0, 10);
            } catch (Exception e) {
                Assertions.assertThat(e).isNotNull();
                System.err.println(e.getMessage());
            }

            try {
                PageQuery query = new PageQuery(1, 0);
            } catch (Exception e) {
                Assertions.assertThat(e).isNotNull();
                System.err.println(e.getMessage());
            }
        }

        @Test
        @DisplayName("三参数构造方法 PageQuery")
        public void testPageQuery3() {
            PageQuery query = new PageQuery(1, 10, new ArrayList<>());
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isEmpty();
            Assertions.assertThat(query.getSort()).isEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query));

            PageQuery query2 = new PageQuery(1, 10, null);
            Assertions.assertThat(query2.getPage()).isEqualTo(1);
            Assertions.assertThat(query2.getSize()).isEqualTo(10);
            Assertions.assertThat(query2.getStart()).isEqualTo(0);
            Assertions.assertThat(query2.getOrders()).isEmpty();
            Assertions.assertThat(query2.getSort()).isEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query2));

            Collection<Order> orders = new ArrayList<>();
            orders.add(Order.parse("id,asc"));
            orders.add(new Order("update_time", Order.Direction.desc));
            PageQuery query3 = new PageQuery(1, 10, orders);
            Assertions.assertThat(query3.getPage()).isEqualTo(1);
            Assertions.assertThat(query3.getSize()).isEqualTo(10);
            Assertions.assertThat(query3.getStart()).isEqualTo(0);
            Assertions.assertThat(query3.getOrders()).isNotEmpty();
            Assertions.assertThat(query3.getSort()).isNotEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query3));
        }

    }

    @Nested
    @DisplayName("静态构造方法测试")
    class OfMethod {

        @Test
        @DisplayName("PageQuery.of 测试一")
        public void testOf() {
            PageQuery query = PageQuery.of(1, 10);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isEmpty();
            Assertions.assertThat(query.getSort()).isEmpty();
            System.out.println(JSONUtil.toJsonPrettyStr(query));
        }

        @Test
        @DisplayName("PageQuery.of 测试二")
        public void testOf2() {
            Collection<Order> orders = new ArrayList<>();
            orders.add(Order.parse("id,asc"));
            orders.add(new Order("update_time", Order.Direction.desc));
            PageQuery query = PageQuery.of(1, 10, orders);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isNotEmpty();
            Assertions.assertThat(query.getSort()).isNotEmpty();
            Sort sort = query.getSpringSort();
            Assertions.assertThat(sort).isNotNull();
            for (Sort.Order order : sort) {
                Assertions.assertThat(order).isNotNull();
                System.out.println(JSONUtil.toJsonPrettyStr(order));
            }
        }

        @Test
        @DisplayName("PageQuery.of 测试三")
        public void testOf3() {
            PageQuery query = PageQuery.of(1, 10, "id,asc", "update_time,desc");
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Assertions.assertThat(query.getOrders()).isNotEmpty();
            Assertions.assertThat(query.getSort()).isNotEmpty();
            Sort sort = query.getSpringSort();
            Assertions.assertThat(sort).isNotNull();
            for (Sort.Order order : sort) {
                Assertions.assertThat(order).isNotNull();
                System.out.println(JSONUtil.toJsonPrettyStr(order));
            }
        }

        @Test
        @DisplayName("PageQuery.of 测试四")
        public void testOf4() {
            PageQuery query = PageQuery.of(1, 10, "id,asc", "update_time,desc");
            PageQuery query2 = PageQuery.of(query.toPageRequest());
            Assertions.assertThat(query2.getPage()).isEqualTo(1);
            Assertions.assertThat(query2.getSize()).isEqualTo(10);
            Assertions.assertThat(query2.getStart()).isEqualTo(0);
            Assertions.assertThat(query2.getOrders()).isNotEmpty();
            Assertions.assertThat(query2.getSort()).isNotEmpty();
            Sort sort = query2.getSpringSort();
            Assertions.assertThat(sort).isNotNull();
            for (Sort.Order order : sort) {
                Assertions.assertThat(order).isNotNull();
                System.out.println(JSONUtil.toJsonPrettyStr(order));
            }
        }

    }

    @Nested
    @DisplayName("转换方法测试")
    class ConvertMethod {

        @Test
        @DisplayName("获取 org.springframework.data.domain.Sort 方法")
        public void testGetSpringSort() {
            Collection<Order> orders = new ArrayList<>();
            orders.add(Order.parse("id,asc"));
            orders.add(new Order("update_time", Order.Direction.desc));
            PageQuery query = new PageQuery(1, 10, orders);
            Assertions.assertThat(query.getPage()).isEqualTo(1);
            Assertions.assertThat(query.getSize()).isEqualTo(10);
            Assertions.assertThat(query.getStart()).isEqualTo(0);
            Sort sort = query.getSpringSort();
            Assertions.assertThat(sort).isNotNull();
            for (Sort.Order order : sort) {
                Assertions.assertThat(order).isNotNull();
                System.out.println(JSONUtil.toJsonPrettyStr(order));
            }
        }

        @Test
        @DisplayName("PageQuery 转换 PageRequest 方法")
        public void testToPageRequest() {
            Collection<Order> orders = new ArrayList<>();
            orders.add(Order.parse("id,asc"));
            orders.add(new Order("update_time", Order.Direction.desc));
            PageQuery query = new PageQuery(1, 10, orders);
            PageRequest pageRequest = query.toPageRequest();
            Sort sort = query.getSpringSort();
            Assertions.assertThat(sort).isNotNull();
            for (Sort.Order order : sort) {
                Assertions.assertThat(order).isNotNull();
                System.out.println(JSONUtil.toJsonPrettyStr(order));
            }
            Assertions.assertThat(pageRequest.getPageNumber()).isEqualTo(0);
            Assertions.assertThat(pageRequest.getPageSize()).isEqualTo(10);
        }

    }

    @Test
    @DisplayName("next 方法")
    public void testNext() {
        PageQuery query = new PageQuery(1, 10);
        for (int i = 2; i < 10; i++) {
            query = query.next();
            Assertions.assertThat(query.getPage()).isEqualTo(i);
        }
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
