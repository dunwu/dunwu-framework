package io.github.dunwu.common.dal.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.DefaultException;
import io.github.dunwu.common.dal.domain.PageQuery;
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
 * <p>
 * 继承自 Hutool 的 {@link cn.hutool.core.util.PageUtil}，并扩展了针对 Spring Data 分页的支持，
 * 包括分页数据转换、排序表达式解析等功能。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * 私有构造方法，防止实例化
     */
    private PageUtil() {}

    /**
     * List 分页处理
     * <p>
     * 根据页码和每页大小，从原始 List 中截取对应分页的数据。
     *
     * @param page 页码（从 0 开始）
     * @param size 每页记录数
     * @param list 原始数据列表
     * @param <T>  数据类型
     * @return 分页后的数据子列表
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
     * 将 Spring Data 的 {@link Page} 对象转换为 Map
     * <p>
     * 主要用于防止某些场景下（如 Redis 反序列化）直接返回 Page 对象导致的报错。
     *
     * @param page 分页对象
     * @return 包含 content（数据列表）和 totalElements（总记录数）的 Map
     */
    public static Map<String, Object> toMap(Page<?> page) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    /**
     * 自定义分页数据转换为 Map
     *
     * @param object        分页数据内容
     * @param totalElements 总记录数
     * @return 包含 content 和 totalElements 的 Map
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
     * @param expression 字符串表达式，形式如："id,asc"
     * @return 排序对象
     * @throws DefaultException 如果表达式格式不正确
     */
    public static Sort.Order getOrder(String expression) {
        List<String> list = StrUtil.split(expression, ',');
        if (CollectionUtil.isEmpty(list) || list.size() != 2) {
            throw new DefaultException(ResultCode.REQUEST_ERROR);
        }

        String filed = list.get(0);
        Sort.Direction direction = Sort.Direction.fromString(list.get(1));
        return new Sort.Order(direction, filed);
    }

    /**
     * 通过字符串表达式数组解析为 {@link Sort.Order} 列表
     *
     * @param expressions 字符串表达式数组，形式如："id,asc"
     * @return 排序对象列表
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
     * @param expressions 字符串表达式数组，形式如："id,asc"
     * @return Sort 对象
     */
    public static Sort getSort(String... expressions) {
        List<Sort.Order> orders = getOrders(expressions);
        if (CollectionUtil.isEmpty(orders)) {
            return null;
        }
        return Sort.by(orders);
    }

    /**
     * 通过字符串表达式集合解析为 {@link Sort}
     *
     * @param expressions 字符串表达式集合，形式如："id,asc"
     * @return Sort 对象
     */
    public static Sort getSort(Collection<String> expressions) {
        List<Sort.Order> orders = getOrders(expressions.toArray(new String[0]));
        if (CollectionUtil.isEmpty(orders)) {
            return null;
        }
        return Sort.by(orders);
    }

    /**
     * 将 {@link PageQuery} 转换为 Spring Data 的 {@link PageRequest}
     * <p>
     * 处理页码从 1 开始到 0 开始的转换，并解析排序条件。
     *
     * @param query 分页查询参数
     * @return PageRequest 实例
     */
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
