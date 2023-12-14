package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.response.Order;
import io.github.dunwu.tool.data.response.PageImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    public static final int QUERY_FIRST_PAGE = 1;
    public static final int REQUEST_FIRST_PAGE = 0;
    public static final int SIZE = 10;
    public static final int TOTAL;
    private static final List<Integer> DATA = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {
            DATA.add(i + 1);
        }
        TOTAL = DATA.size();
    }

    @Nested
    @DisplayName("构造 PageImpl 测试")
    class NewInstanceTest {

        @Test
        @DisplayName("of()")
        public void test00() {

            PageImpl<?> page = PageImpl.of();

            Assertions.assertThat(page.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(page.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(page.getSort()).isEmpty();
            Assertions.assertThat(page.getTotal()).isEqualTo(0);
            Assertions.assertThat(page.getTotalElements()).isEqualTo(0);
            Assertions.assertThat(page.getTotalPages()).isEqualTo(0);

            Assertions.assertThat(page.getContent()).isEmpty();
            Assertions.assertThat(page.hasContent()).isFalse();
            Assertions.assertThat(page.getNumberOfElements()).isEqualTo(0);

            Assertions.assertThat(page.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("of(List<T> content, int page, int size, long total)")
        public void test01() {

            List<Integer> list = getDataByPage(QUERY_FIRST_PAGE);
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);

            PageImpl<Integer> pageImpl = PageImpl.of(list, QUERY_FIRST_PAGE, SIZE, TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("of(List<T> content, PageQuery pageQuery)")
        public void test02() {

            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);
            PageImpl<Integer> pageImpl = PageImpl.of(DATA, PageQuery.of(QUERY_FIRST_PAGE, SIZE));

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("of(List<T> content, PageQuery pageQuery, long total)")
        public void test03() {

            List<Integer> list = getDataByPage(QUERY_FIRST_PAGE);
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);

            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(QUERY_FIRST_PAGE, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("of(List<T> content, Pageable pageable)")
        public void test04() {

            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);
            PageImpl<Integer> pageImpl = PageImpl.of(DATA, PageRequest.of(REQUEST_FIRST_PAGE, SIZE));

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("of(List<T> content, Pageable pageable, long total)")
        public void test05() {

            List<Integer> list = getDataByPage(QUERY_FIRST_PAGE);
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);

            PageImpl<Integer> pageImpl = PageImpl.of(list, PageRequest.of(REQUEST_FIRST_PAGE, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(QUERY_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(REQUEST_FIRST_PAGE);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

    }

    @Nested
    @DisplayName("转换方法测试")
    class ConvertMethod {

        @Test
        @DisplayName("map 方法")
        public void testMap() {
            PageImpl<Integer> pageImpl = PageImpl.of(DATA, PageQuery.of(1, 10));

            Page<Long> page = pageImpl.map(value -> {
                if (value == null) {
                    return null;
                }
                return value.longValue();
            });

            Assertions.assertThat(page.getNumber()).isEqualTo(0);
            Assertions.assertThat(page.getSize()).isEqualTo(SIZE);
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
            Assertions.assertThat(page.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(page.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(page.getPageable().getSort()).isEmpty();
        }

    }

    @Nested
    @DisplayName("模拟翻页")
    class PagingTest {

        @Test
        @DisplayName("第一页")
        public void test01() {

            int page = 1;
            List<Integer> list = getDataByPage(page);
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);

            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(page);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(false);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(page - 1);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(0);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("最后一页")
        public void test02() {

            int page = 10;
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);
            int offset = (page - 1) * SIZE;
            List<Integer> list = getDataByPage(page);

            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(page);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(false);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(true);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(page - 1);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(offset);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("中间页")
        public void test03() {

            int page = 5;
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);
            int offset = (page - 1) * SIZE;
            List<Integer> list = getDataByPage(page);

            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(page);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isNotEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isTrue();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(10);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(true);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(true);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(page - 1);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(offset);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("不存在的页")
        public void test04() {

            int page = 11;
            int totalPages = TOTAL / SIZE + (TOTAL % SIZE == 0 ? 0 : 1);
            int offset = (page - 1) * SIZE;

            PageImpl<Integer> pageImpl = PageImpl.of(new ArrayList<>(), PageQuery.of(page, SIZE), TOTAL);

            Assertions.assertThat(pageImpl.getNumber()).isEqualTo(page);
            Assertions.assertThat(pageImpl.getSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getSort()).isEmpty();
            Assertions.assertThat(pageImpl.getTotal()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalElements()).isEqualTo(TOTAL);
            Assertions.assertThat(pageImpl.getTotalPages()).isEqualTo(totalPages);

            Assertions.assertThat(pageImpl.getContent()).isEmpty();
            Assertions.assertThat(pageImpl.hasContent()).isFalse();
            Assertions.assertThat(pageImpl.getNumberOfElements()).isEqualTo(0);
            Assertions.assertThat(pageImpl.hasNext()).isEqualTo(false);
            Assertions.assertThat(pageImpl.hasPrevious()).isEqualTo(true);

            Assertions.assertThat(pageImpl.getPageable().getPageNumber()).isEqualTo(page - 1);
            Assertions.assertThat(pageImpl.getPageable().getPageSize()).isEqualTo(SIZE);
            Assertions.assertThat(pageImpl.getPageable().getOffset()).isEqualTo(offset);
            Assertions.assertThat(pageImpl.getPageable().getSort()).isEmpty();
        }

        @Test
        @DisplayName("hasPrevious 和 hasNext 方法")
        public void testHasPreviousAndHasNext() {

            PageImpl<Integer> page1 = PageImpl.of(getDataByPage(1), 1, 10, 100);
            Assertions.assertThat(page1.hasPrevious()).isFalse();
            Assertions.assertThat(page1.hasNext()).isTrue();
            Assertions.assertThat(page1.hasContent()).isTrue();
            Assertions.assertThat(page1.isFirst()).isEqualTo(true);
            Assertions.assertThat(page1.isLast()).isEqualTo(false);

            PageImpl<Integer> page5 = PageImpl.of(getDataByPage(5), 5, 10, 100);
            Assertions.assertThat(page5.hasPrevious()).isTrue();
            Assertions.assertThat(page5.hasNext()).isTrue();
            Assertions.assertThat(page5.hasContent()).isTrue();
            Assertions.assertThat(page5.isFirst()).isEqualTo(false);
            Assertions.assertThat(page5.isLast()).isEqualTo(false);

            PageImpl<Integer> page9 = PageImpl.of(getDataByPage(9), 9, 10, 100);
            Assertions.assertThat(page9.hasPrevious()).isTrue();
            Assertions.assertThat(page9.hasNext()).isTrue();
            Assertions.assertThat(page9.hasContent()).isTrue();
            Assertions.assertThat(page9.isFirst()).isEqualTo(false);
            Assertions.assertThat(page9.isLast()).isEqualTo(false);

            PageImpl<Integer> page10 = PageImpl.of(getDataByPage(10), 10, 10, 100);
            Assertions.assertThat(page10.hasPrevious()).isTrue();
            Assertions.assertThat(page10.hasNext()).isFalse();
            Assertions.assertThat(page10.hasContent()).isTrue();
            Assertions.assertThat(page10.isFirst()).isEqualTo(false);
            Assertions.assertThat(page10.isLast()).isEqualTo(true);

            PageImpl<Integer> page11 = PageImpl.of(getDataByPage(11), 11, 10, 100);
            Assertions.assertThat(page11.hasPrevious()).isTrue();
            Assertions.assertThat(page11.hasNext()).isFalse();
            Assertions.assertThat(page11.hasContent()).isFalse();
            Assertions.assertThat(page11.isFirst()).isEqualTo(false);
            Assertions.assertThat(page11.isLast()).isEqualTo(true);

            PageImpl<Integer> page12 = PageImpl.of(getDataByPage(12), 12, 10, 100);
            Assertions.assertThat(page12.hasPrevious()).isFalse();
            Assertions.assertThat(page12.hasNext()).isFalse();
            Assertions.assertThat(page12.hasContent()).isFalse();
            Assertions.assertThat(page12.isFirst()).isEqualTo(false);
            Assertions.assertThat(page12.isLast()).isEqualTo(true);
        }

        @Test
        @DisplayName("模拟向前翻页")
        public void testPrevious() {
            int page = 11;
            List<Integer> list = getDataByPage(page);
            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), DATA.size());
            System.out.println(StrUtil.format("[{}] list: {}", page, list));

            while (pageImpl.hasPrevious()) {
                page -= 1;
                list = getDataByPage(page);
                pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), DATA.size());
                System.out.println(StrUtil.format("[{}] list: {}", page, list));
            }
        }

        @Test
        @DisplayName("模拟根据 hasNext 翻页")
        public void testNext() {
            int page = 1;
            List<Integer> list = getDataByPage(page);
            PageImpl<Integer> pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), DATA.size());
            System.out.println(StrUtil.format("[{}] list: {}", page, list));

            while (pageImpl.hasNext()) {
                page += 1;
                list = getDataByPage(page);
                pageImpl = PageImpl.of(list, PageQuery.of(page, SIZE), DATA.size());
                System.out.println(StrUtil.format("[{}] list: {}", page, list));
            }
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

    private static List<Integer> getDataByPage(int page) {
        List<List<Integer>> groupList = CollectionUtil.split(DATA, SIZE);
        int totalPages = groupList.size();
        if (page > totalPages || page <= 0) {
            return new ArrayList<>();
        }
        return groupList.get(page - 1);
    }

}
