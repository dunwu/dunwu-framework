package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring4 的 webmvc 无法直接数据绑定 {@link org.springframework.data.domain.Pageable}
 * <p>
 * 所以使用本类进行数据适配，流程为：
 * <ol>
 * <li>web 层使用 {@link PageQuery} 作为请求参数，由 Spring Web 进行数据绑定</li>
 * <li>自行将 {@link PageQuery} 转化为 {@link org.springframework.data.domain.Pageable}</li>
 * <li>最后由 Mybatis Plus 扩展工具类使用 {@link org.springframework.data.domain.Pageable} 进行分页</li>
 * </ol>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
@Accessors(chain = true)
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 7704551086334591374L;

    /**
     * 当前查询页
     */
    protected int page = 1;

    /**
     * 每页展示记录数
     */
    protected int size = 10;

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

    public PageQuery() { }

    public PageQuery(int page, int size) {
        this(page, size, new ArrayList<>());
    }

    public PageQuery(int page, int size, Collection<Order> orders) {
        this.page = page;
        this.size = size;
        this.start = getStart();
        setOrders(orders);
    }

    public int getStart() {
        if (page > 1) {
            return (page - 1) * size;
        }
        return 0;
    }

    public PageQuery next() {
        return new PageQuery(this.getPage() + 1, this.getSize(), this.getOrders());
    }

    public PageQuery previous() {
        return this.getPage() <= 1 ? this : new PageQuery(this.getPage() - 1, this.getSize(), this.getOrders());
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
        return CollectionUtil.join(list, ", ");
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
            this.sort = orders.stream().map(Order::toString).collect(Collectors.toList());
            this.orders = new ArrayList<>(orders);
        } else {
            this.sort = new ArrayList<>();
            this.orders = new ArrayList<>();
        }
    }

    public static PageQuery by(int page, int size) {
        return by(page, size);
    }

    public static PageQuery by(int page, int size, Collection<Order> orders) {
        return new PageQuery(page, size, orders);
    }

    public static PageQuery bySort(int page, int size, Collection<String> sort) {
        if (CollectionUtil.isEmpty(sort)) {
            return new PageQuery(page, size);
        }
        List<Order> orders = sort.stream().map(Order::parse).collect(Collectors.toList());
        return new PageQuery(page, size, orders);
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
        if (query.getPage() > 1) {
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
