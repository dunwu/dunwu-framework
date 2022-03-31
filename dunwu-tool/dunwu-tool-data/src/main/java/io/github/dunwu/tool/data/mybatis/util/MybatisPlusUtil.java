package io.github.dunwu.tool.data.mybatis.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.tool.data.Pagination;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Mybatis Plus 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Slf4j
public class MybatisPlusUtil {

    private MybatisPlusUtil() { }

    public static <T> QueryWrapper<T> buildQueryWrapper(final Object queryBean) {

        if (queryBean == null) { return null; }

        QueryWrapper<T> wrapper = null;

        // 遍历查询实体所有字段
        Field[] fields = queryBean.getClass().getDeclaredFields();
        for (Field f : fields) {

            f.setAccessible(true);

            // 如果字段没有被 QueryField 修饰，视为非自动查询字段，直接跳过
            QueryField queryField = f.getAnnotation(QueryField.class);
            if (null == queryField) { continue; }

            Object value = null;
            try {
                value = f.get(queryBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null && !queryField.nullable()) { continue; }

            if (wrapper == null) { wrapper = new QueryWrapper<>(); }

            // 添加混合模糊查询条件
            String[] blurry = queryField.blurry();
            if (ArrayUtil.isNotEmpty(blurry)) {
                for (String k : blurry) {
                    orConditionToQueryWrapper(wrapper, queryField.type(), k, value);
                }
                continue;
            }

            // 添加非混合模糊查询条件
            String key = getKeyByStrategy(f.getName(), queryField);
            andConditionToQueryWrapper(wrapper, queryField.type(), key, value);
        }
        return wrapper;
    }

    public static String getKeyByStrategy(String key, QueryField queryField) {
        String finalKey;
        if (StrUtil.isNotBlank(queryField.value())) {
            finalKey = queryField.value();
        } else {
            finalKey = StrUtil.toUnderlineCase(key);
        }

        switch (queryField.namingStrategy()) {
            case CAMEL:
                return StrUtil.toCamelCase(finalKey);
            case LOWER_UNDERLINE:
                return StrUtil.toUnderlineCase(finalKey).toLowerCase();
            case UPPER_UNDERLINE:
                return StrUtil.toUnderlineCase(finalKey).toUpperCase();
            case DEFAULT:
            default:
                return finalKey;
        }
    }

    private static <T> void andConditionToQueryWrapper(QueryWrapper<T> wrapper, QueryField.QueryType type,
        String key, Object value) {
        switch (type) {
            case EQUALS:
                wrapper.and(w -> w.eq(key, value));
                break;
            case LIKE:
                wrapper.and(w -> w.like(key, value));
                break;
            case NOT_LIKE:
                wrapper.and(w -> w.notLike(key, value));
                break;
            case LIKE_LEFT:
                wrapper.and(w -> w.likeLeft(key, value));
                break;
            case LIKE_RIGHT:
                wrapper.and(w -> w.likeRight(key, value));
                break;
            case LT:
                wrapper.and(w -> w.lt(key, value));
                break;
            case LE:
                wrapper.and(w -> w.le(key, value));
                break;
            case GT:
                wrapper.and(w -> w.gt(key, value));
                break;
            case GE:
                wrapper.and(w -> w.ge(key, value));
                break;
            case NE:
                wrapper.and(w -> w.ne(key, value));
                break;
            case IN:
                if (value instanceof Collection) {
                    Collection<?> list = (Collection<?>) value;
                    Object[] array = list.toArray();
                    wrapper.and(w -> w.in(key, array));
                } else if (ArrayUtil.isArray(value)) {
                    wrapper.and(w -> w.in(key, value));
                } else {
                    throw new IllegalArgumentException("IN 请求参数必须是数组或 Collection");
                }
                break;
            case NOT_IN:
                if (value instanceof Collection) {
                    Collection<?> list = (Collection<?>) value;
                    Object[] array = list.toArray();
                    wrapper.and(w -> w.notIn(key, array));
                } else if (ArrayUtil.isArray(value)) {
                    wrapper.and(w -> w.notIn(key, value));
                } else {
                    throw new IllegalArgumentException("NOT_IN 请求参数必须是数组或 Collection");
                }
                wrapper.and(w -> w.notIn(key, value));
                break;
            case NOT_NULL:
                wrapper.and(w -> w.isNotNull(key));
                break;
            case BETWEEN:
                List<?> between = new ArrayList<>((List<?>) value);
                if (CollectionUtil.isEmpty(between) || between.size() != 2) {
                    throw new IllegalArgumentException("BETWEEN 请求参数必须为2个");
                }
                if (between.get(0) instanceof Date) {
                    Date begin = (Date) between.get(0);
                    Date end = (Date) between.get(1);
                    wrapper.and(w -> w.between(key, DateUtil.format(begin, DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(end, DatePattern.NORM_DATETIME_PATTERN)));
                } else if (between.get(0) instanceof LocalDateTime) {
                    LocalDateTime begin = (LocalDateTime) between.get(0);
                    LocalDateTime end = (LocalDateTime) between.get(1);
                    wrapper.and(w -> w.between(key, DateUtil.format(begin, DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(end, DatePattern.NORM_DATETIME_PATTERN)));
                } else {
                    wrapper.and(w -> w.between(key, between.get(0), between.get(1)));
                }
                break;
            default:
                break;
        }
    }

    private static <T> void orConditionToQueryWrapper(QueryWrapper<T> wrapper, QueryField.QueryType type,
        String key, Object value) {
        switch (type) {
            case EQUALS:
                wrapper.or(w -> w.eq(key, value));
                break;
            case LIKE:
                wrapper.or(w -> w.like(key, value));
                break;
            case NOT_LIKE:
                wrapper.or(w -> w.notLike(key, value));
                break;
            case LIKE_LEFT:
                wrapper.or(w -> w.likeLeft(key, value));
                break;
            case LIKE_RIGHT:
                wrapper.or(w -> w.likeRight(key, value));
                break;
            case IN:
                wrapper.or(w -> w.in(key, value));
                break;
            case NOT_IN:
                wrapper.or(w -> w.notIn(key, value));
                break;
            case NOT_NULL:
                wrapper.or(w -> w.isNotNull(key));
                break;
            case BETWEEN:
                List<Object> between = new ArrayList<>((List<Object>) value);
                if (CollectionUtil.isEmpty(between) || between.size() != 2) {
                    throw new IllegalArgumentException("BETWEEN 请求参数必须为2个");
                }
                if (between.get(0) instanceof Date) {
                    Date begin = (Date) between.get(0);
                    Date end = (Date) between.get(1);
                    wrapper.or(w -> w.between(key, DateUtil.format(begin, DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(end, DatePattern.NORM_DATETIME_PATTERN)));
                } else if (between.get(0) instanceof LocalDateTime) {
                    LocalDateTime begin = (LocalDateTime) between.get(0);
                    LocalDateTime end = (LocalDateTime) between.get(1);
                    wrapper.or(w -> w.between(key, DateUtil.format(begin, DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(end, DatePattern.NORM_DATETIME_PATTERN)));
                } else {
                    wrapper.or(w -> w.between(key, between.get(0), between.get(1)));
                }
                break;
            default:
                break;
        }
    }

    public static Page<Map<String, Object>> toMybatisPlusMapPage(Pageable pageable) {
        Page<Map<String, Object>> page = new Page<>((pageable.getPageNumber() + 1), pageable.getPageSize(), true);
        List<OrderItem> orderItems = toMybatisPlusOrderItems(pageable.getSort());
        if (CollectionUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    public static List<OrderItem> toMybatisPlusOrderItems(Sort sort) {
        List<OrderItem> orderItems = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(sort)) {
            sort.get().forEach(i -> {
                if (Sort.Direction.ASC.equals(i.getDirection())) {
                    orderItems.add(OrderItem.asc(i.getProperty()));
                } else {
                    orderItems.add(OrderItem.desc(i.getProperty()));
                }
            });
        }
        return orderItems;
    }

    public static <T> Page<T> toMybatisPlusPage(Pageable pageable) {
        Page<T> page = new Page<>((pageable.getPageNumber() + 1), pageable.getPageSize(), true);
        List<OrderItem> orderItems = toMybatisPlusOrderItems(pageable.getSort());
        if (CollectionUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    public static <T> Page<T> toMybatisPlusPage(org.springframework.data.domain.Page<T> page) {
        Page<T> mybatisPlusPage = new Page<>((page.getNumber() + 1), page.getSize(), page.getTotalElements(), true);
        mybatisPlusPage.setRecords(page.getContent());
        List<OrderItem> orderItems = toMybatisPlusOrderItems(page.getSort());
        if (CollectionUtil.isNotEmpty(orderItems)) {
            mybatisPlusPage.addOrder(orderItems);
        }
        return mybatisPlusPage;
    }

    /**
     * 将 Mybatis Plus 分页信息转为 Spring Data 分页信息
     *
     * @param page {@link Page<T>}
     * @param <T>  数据类型
     * @return {@link Pagination <T>}
     */
    public static <T> org.springframework.data.domain.Page<T> toSpringPage(Page<T> page) {
        return new Pagination<>(page.getRecords(), (int) (page.getCurrent() - 1), (int) page.getSize(),
            page.getTotal());
    }

}
