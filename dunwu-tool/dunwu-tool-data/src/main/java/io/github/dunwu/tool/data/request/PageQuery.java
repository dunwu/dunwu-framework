package io.github.dunwu.tool.data.request;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import io.github.dunwu.tool.data.response.Order;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spring4 的 webmvc 无法直接数据绑定 {@link Pageable}
 * <p>
 * 所以使用本类进行数据适配，流程为：
 * <ol>
 * <li>web 层使用 {@link PageQuery} 作为请求参数，由 Spring Web 进行数据绑定</li>
 * <li>自行将 {@link PageQuery} 转化为 {@link Pageable}</li>
 * <li>最后由 Mybatis Plus 扩展工具类使用 {@link Pageable} 进行分页</li>
 * </ol>
 * <p>
 * 此外，需注意：{@link PageQuery} 页码从 1 开始；{@link Pageable} 页码从 0 开始
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FIRST_PAGE = 1;

    public static final int DEFAULT_SIZE = 10;

    /**
     * 当前页码
     */
    protected int page = FIRST_PAGE;

    /**
     * 每页记录数
     */
    protected int size = DEFAULT_SIZE;

    /**
     * 排序字符串表达式列表，形式如：id,asc
     */
    protected List<String> sort;

    /**
     * 排序字段列表
     */
    protected List<Order> orders;

    /**
     * 起始查询位置
     */
    private int start = getStart();

    /**
     * 默认构造器方法
     */
    public PageQuery() {
        init(FIRST_PAGE, DEFAULT_SIZE, null);
    }

    /**
     * 构造器方法
     *
     * @param page 页号，必须大于 0
     * @param size 每页数量，必须大于 0
     */
    public PageQuery(int page, int size) {
        init(page, size, null);
    }

    /**
     * 构造器方法
     *
     * @param page   页号，必须大于 0
     * @param size   每页数量，必须大于 0
     * @param orders 排序属性，可以为空
     */
    public PageQuery(int page, int size, Collection<Order> orders) {
        init(page, size, orders);
    }

    public int getStart() {
        if (page > FIRST_PAGE) {
            return (page - 1) * size;
        }
        return 0;
    }

    public PageQuery next() {
        return new PageQuery(this.getPage() + 1, this.getSize(), this.getOrders());
    }

    public PageQuery previous() {
        return this.getPage() <= FIRST_PAGE ? this : new PageQuery(this.getPage() - 1, this.getSize(), this.getOrders());
    }

    public Sort getSpringSort() {
        if (CollectionUtil.isEmpty(this.getSort())) {
            return null;
        }
        return getSpringSort(this.getSort());
    }

    public String getOrderClause() {
        if (CollectionUtil.isEmpty(this.getOrders())) {
            return null;
        }

        List<String> list = this.getOrders().stream().map(Order::getClause).collect(Collectors.toList());
        return CollectionUtil.join(list, ";");
    }

    public PageRequest toPageRequest() {
        return toPageRequest(this);
    }

    public void setSort(Collection<String> sort) {
        if (CollectionUtil.isNotEmpty(sort)) {
            this.sort = new ArrayList<>(sort);
            this.orders = sort.stream().map(Order::parse).collect(Collectors.toList());
        } else {
            this.sort = new ArrayList<>();
            this.orders = new ArrayList<>();
        }
    }

    public void setOrders(Collection<Order> orders) {
        if (CollectionUtil.isNotEmpty(orders)) {
            this.sort = orders.stream().map(Order::getClause).collect(Collectors.toList());
            this.orders = new ArrayList<>(orders);
        } else {
            this.sort = new ArrayList<>();
            this.orders = new ArrayList<>();
        }
    }

    private void init(int page, int size, Collection<Order> orders) {
        if (page < FIRST_PAGE) {
            throw new IllegalArgumentException("page 不能小于 1");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size 必须大于 0");
        }
        this.page = page;
        this.size = size;
        this.start = getStart();
        setOrders(orders);
    }

    public static PageQuery of(int page, int size) {
        return new PageQuery(page, size);
    }

    public static PageQuery of(int page, int size, Collection<Order> orders) {
        return new PageQuery(page, size, orders);
    }

    public static PageQuery of(int page, int size, String... sort) {
        if (ArrayUtil.isEmpty(sort)) {
            return new PageQuery(page, size);
        }
        List<Order> orders = Stream.of(sort).map(Order::parse).collect(Collectors.toList());
        return new PageQuery(page, size, orders);
    }

    public static PageQuery of(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return new PageQuery(pageable.getPageNumber() + 1, pageable.getPageSize());
        }
        List<Order> orders = pageable.getSort().stream().map(Order::parse).collect(Collectors.toList());
        return new PageQuery(pageable.getPageNumber() + 1, pageable.getPageSize(), orders);
    }

    /**
     * 通过字符串表达式解析为 {@link Sort.Order}
     *
     * @param expression 字符串表达式，形式如：id,asc
     * @return /
     */
    public static Sort.Order getSpringOrder(String expression) {
        List<String> list = StrUtil.split(expression, ',');
        if (CollectionUtil.isEmpty(list) || list.size() != 2) {
            throw new DefaultException(ResultStatus.PARAMS_ERROR);
        }

        String filed = list.get(0);
        Sort.Direction direction = Sort.Direction.fromString(list.get(1));
        return new Sort.Order(direction, filed);
    }

    /**
     * 通过字符串表达式数组解析为 {@link Sort.Order} 列表
     *
     * @param expressions 字符串表达式列表，形式如：id,asc
     * @return /
     */
    public static List<Sort.Order> getSpringOrderList(String... expressions) {
        if (ArrayUtil.isEmpty(expressions)) {
            return null;
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String expression : expressions) {
            Sort.Order order = getSpringOrder(expression);
            orders.add(order);
        }
        return orders;
    }

    /**
     * 通过字符串表达式数组解析为 {@link Sort}
     *
     * @param expressions 字符串表达式列表，形式如：id,asc
     * @return /
     */
    public static Sort getSpringSort(Collection<String> expressions) {
        List<Sort.Order> orders = getSpringOrderList(expressions.toArray(new String[0]));
        if (CollectionUtil.isEmpty(orders)) {
            return null;
        }
        return Sort.by(orders);
    }

    public static PageRequest toPageRequest(PageQuery query) {

        int page = 0;
        if (query.getPage() > FIRST_PAGE) {
            page = query.getPage() - 1;
        }

        Sort sort = null;
        if (ArrayUtil.isNotEmpty(query.getSort())) {
            sort = getSpringSort(query.getSort());
        }

        if (sort != null) {
            return PageRequest.of(page, query.getSize(), sort);
        } else {
            return PageRequest.of(page, query.getSize());
        }
    }

}
