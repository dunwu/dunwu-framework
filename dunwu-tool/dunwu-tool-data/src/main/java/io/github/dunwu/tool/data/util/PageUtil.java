package io.github.dunwu.tool.data.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import io.github.dunwu.tool.data.request.PageQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

    private PageUtil() {}

    /**
     * List 分页
     */
    public static <T> List<T> toList(int page, int size, List<T> list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if (fromIndex > list.size()) {
            return new ArrayList<>();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     */
    public static Map<String, Object> toMap(Page<?> page) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    /**
     * 自定义分页
     */
    public static Map<String, Object> toMap(Object object, Object totalElements) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", object);
        map.put("totalElements", totalElements);
        return map;
    }

    /**
     * 通过字符串表达式解析为 {@link Sort.Order}
     *
     * @param expression 字符串表达式，形式如：id,asc
     * @return /
     */
    public static Sort.Order getOrder(String expression) {
        List<String> list = StrUtil.split(expression, ',');
        if (CollectionUtil.isEmpty(list) || list.size() != 2) {
            throw new DefaultException(ResultStatus.REQUEST_ERROR);
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
    public static List<Sort.Order> getOrders(String... expressions) {
        if (ArrayUtil.isEmpty(expressions)) {
            return null;
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String expression : expressions) {
            Sort.Order order = getOrder(expression);
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
    public static Sort getSort(String... expressions) {
        List<Sort.Order> orders = getOrders(expressions);
        if (CollectionUtil.isEmpty(orders)) {
            return null;
        }
        return Sort.by(orders);
    }

    /**
     * 通过字符串表达式数组解析为 {@link Sort}
     *
     * @param expressions 字符串表达式列表，形式如：id,asc
     * @return /
     */
    public static Sort getSort(Collection<String> expressions) {
        List<Sort.Order> orders = getOrders(expressions.toArray(new String[0]));
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
            sort = PageUtil.getSort(query.getSort());
        }

        if (sort != null) {
            return PageRequest.of(page, query.getSize(), sort);
        } else {
            return PageRequest.of(page, query.getSize());
        }
    }

}
