package io.github.dunwu.common.dal.mybatis.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.common.dal.annotation.QueryField;
import io.github.dunwu.common.dal.mybatis.BaseExtDaoImpl;
import io.github.dunwu.common.dal.mybatis.DaoInfo;
import io.github.dunwu.common.util.ReflectUtil;
import io.github.dunwu.common.util.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MyBatis-Plus 辅助工具类
 * <p>
 * 提供以下核心功能：
 * <ul>
 * <li>基于 {@link QueryField} 注解自动构建 {@link QueryWrapper} 查询条件。</li>
 * <li>Spring Data {@link Pageable} 与 MyBatis-Plus {@link Page} 之间的相互转换。</li>
 * <li>自动扫描并收集 DAO 元数据信息（{@link DaoInfo}）。</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Slf4j
public class MybatisPlusUtil {

    /**
     * 系统中所有继承自 {@link BaseExtDaoImpl} 的 DAO 类集合
     */
    private static final Set<Class<? extends BaseExtDaoImpl>> DAO_SET;

    /**
     * 表名到 DAO 信息的映射缓存
     */
    private static final Map<String, DaoInfo> DAO_INFO_MAP;

    static {
        DAO_SET = ReflectUtil.getSubTypesOf(BaseExtDaoImpl.class);
        DAO_INFO_MAP = getDaoInfoMap();
    }

    private MybatisPlusUtil() {
    }

    /**
     * 根据查询对象中的 {@link QueryField} 注解自动构建查询条件
     * <p>
     * 遍历查询对象的所有字段，如果字段被 {@link QueryField} 修饰，则根据注解配置的类型、字段名、
     * 命名策略等构建相应的 SQL 条件（如 EQ, LIKE, BETWEEN 等）。
     * 支持 "模糊查询"（Blurry），即一个值匹配多个字段（OR 关系）。
     *
     * @param queryBean 包含查询条件的对象
     * @param <T>       实体类型
     * @return 构建好的 {@link QueryWrapper}，如果 queryBean 为 null 则返回 null
     */
    public static <T> QueryWrapper<T> buildQueryWrapper(final Object queryBean) {

        if (queryBean == null) {
            return null;
        }

        QueryWrapper<T> wrapper = null;

        // 遍历查询实体所有字段
        Field[] fields = queryBean.getClass().getDeclaredFields();
        for (Field f : fields) {

            f.setAccessible(true);

            // 如果字段没有被 QueryField 修饰，视为非自动查询字段，直接跳过
            QueryField queryField = f.getAnnotation(QueryField.class);
            if (null == queryField) {
                continue;
            }

            Object value = null;
            try {
                value = f.get(queryBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null && !queryField.nullable()) {
                continue;
            }

            if (wrapper == null) {
                wrapper = new QueryWrapper<>();
            }

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

    /**
     * 根据命名策略获取最终的数据库字段名
     *
     * @param key        Java 属性名
     * @param queryField 查询字段注解
     * @return 转换后的数据库字段名
     */
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

    /**
     * 向 QueryWrapper 添加 AND 条件
     *
     * @param wrapper 查询包装器
     * @param type    查询类型（如 EQUALS, LIKE, GT 等）
     * @param key     数据库字段名
     * @param value   查询值
     * @param <T>     实体类型
     */
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

    /**
     * 向 QueryWrapper 添加 OR 条件
     *
     * @param wrapper 查询包装器
     * @param type    查询类型
     * @param key     数据库字段名
     * @param value   查询值
     * @param <T>     实体类型
     */
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

    /**
     * 将 Spring Data 的 {@link Pageable} 转换为 MyBatis-Plus 的 {@link Page}（返回 Map 类型）
     *
     * @param pageable Spring Data 分页参数
     * @return MyBatis-Plus 分页对象
     */
    public static Page<Map<String, Object>> toMybatisPlusMapPage(Pageable pageable) {
        Page<Map<String, Object>> page = new Page<>((pageable.getPageNumber() + 1), pageable.getPageSize(), true);
        List<OrderItem> orderItems = toMybatisPlusOrderItems(pageable.getSort());
        if (CollectionUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    /**
     * 将 Spring Data 的 {@link Sort} 转换为 MyBatis-Plus 的 {@link OrderItem} 列表
     *
     * @param sort Spring Data 排序对象
     * @return 排序项列表
     */
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

    /**
     * 将 Spring Data 的 {@link Pageable} 转换为 MyBatis-Plus 的 {@link Page}
     *
     * @param pageable Spring Data 分页参数
     * @param <T>      实体类型
     * @return MyBatis-Plus 分页对象
     */
    public static <T> Page<T> toMybatisPlusPage(Pageable pageable) {
        Page<T> page = new Page<>((pageable.getPageNumber() + 1), pageable.getPageSize(), true);
        List<OrderItem> orderItems = toMybatisPlusOrderItems(pageable.getSort());
        if (CollectionUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    /**
     * 将 Spring Data 的 {@link org.springframework.data.domain.Page} 转换为 MyBatis-Plus 的 {@link Page}
     *
     * @param page Spring Data 分页结果
     * @param <T>  实体类型
     * @return MyBatis-Plus 分页对象
     */
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
     * 根据表名获取对应的 DAO 信息
     *
     * @param tableName 数据库表名
     * @return DAO 信息对象，如果未找到则返回 null
     */
    public static DaoInfo getDaoInfo(String tableName) {
        return DAO_INFO_MAP.get(tableName);
    }

    /**
     * 将 MyBatis-Plus 的分页信息转换为 Spring Data 的 {@link PageImpl}
     *
     * @param page MyBatis-Plus 分页对象
     * @param <T>  实体类型
     * @return Spring Data 分页结果对象
     */
    public static <T> org.springframework.data.domain.Page<T> toSpringPage(Page<T> page) {
        PageRequest pageRequest = PageRequest.of((int) (page.getCurrent() - 1), (int) page.getSize());
        return new PageImpl<>(page.getRecords(), pageRequest, page.getTotal());
    }

    /**
     * 扫描系统中所有继承自 {@link BaseExtDaoImpl} 的类，提取并构建 DAO 信息映射
     * <p>
     * 提取的信息包括：DAO 类名、Mapper 类名、实体类名、表名（通过 {@link TableName} 注解）
     * 以及表描述（通过 {@link Schema} 注解）。
     *
     * @return 表名到 {@link DaoInfo} 的映射 Map
     */
    private static Map<String, DaoInfo> getDaoInfoMap() {

        if (CollectionUtil.isEmpty(DAO_SET)) {
            return new HashMap<>(0);
        }

        Map<String, DaoInfo> map = new HashMap<>(DAO_SET.size());
        for (Class<? extends BaseExtDaoImpl> clazz : DAO_SET) {
            Type[] types = TypeUtil.getTypeArguments(clazz);
            if (ArrayUtil.isEmpty(types) || types.length != 2) {
                continue;
            }

            Type mapperType = types[0];
            Type entityType = types[1];

            DaoInfo daoInfo = new DaoInfo();

            try {
                Class<?> mapperClass = Class.forName(mapperType.getTypeName());
                Class<?> entityClass = Class.forName(entityType.getTypeName());
                daoInfo.setDaoClass(clazz);
                daoInfo.setDaoClassName(clazz.getName());
                daoInfo.setMapperClass(mapperClass);
                daoInfo.setMapperClassName(mapperClass.getName());
                daoInfo.setEntityClass(entityClass);
                daoInfo.setEntityClassName(entityClass.getName());

                TableName[] tableNames = entityClass.getAnnotationsByType(TableName.class);
                if (ArrayUtil.isNotEmpty(tableNames)) {
                    String tableName = tableNames[0].value();
                    daoInfo.setTableName(tableName);
                    map.put(tableName, daoInfo);
                }

                Schema[] schemas = entityClass.getAnnotationsByType(Schema.class);
                if (ArrayUtil.isNotEmpty(schemas)) {
                    daoInfo.setDescription(schemas[0].description());
                }

                // AnsiColorUtil.BOLD_RED.println("daoInfo：" + JSONUtil.toJsonStr(daoInfo));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

}
