package io.github.dunwu.common.dal.mybatis;

import org.springframework.core.convert.converter.Converter;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.common.bean.BeanUtil;
import io.github.dunwu.common.dal.mybatis.util.MybatisPlusUtil;
import io.github.dunwu.common.dal.domain.PageQuery;
import io.github.dunwu.common.dal.util.PageUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO 扩展接口
 * <p>
 * 继承自 {@link IDao}，提供了更多便捷的查询方法，包括：
 * <ul>
 * <li>基于查询对象（Query Object）的查询</li>
 * <li>实体到 POJO/DTO 的自动转换</li>
 * <li>支持 Spring Data 风格的分页查询</li>
 * </ul>
 *
 * @param <E> 实体类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public interface IExtDao<E> extends IDao<E> {

    /**
     * 根据查询对象统计符合条件的记录数
     *
     * @param query 查询条件对象
     * @return 符合条件的记录总数
     */
    Long countByQuery(Object query);

    /**
     * 根据主键 ID 查询并转换为指定的 POJO 类型
     *
     * @param id    主键 ID
     * @param clazz 目标 POJO 类型
     * @param <T>   目标类型
     * @return 转换后的 POJO 对象，如果未找到则返回 null
     */
    default <T> T pojoById(Serializable id, Class<T> clazz) {
        if (id == null) {
            return null;
        }
        E entity = getById(id);
        if (entity == null) {
            return null;
        }
        return BeanUtil.toBean(entity, clazz);
    }

    /**
     * 根据主键 ID 查询并使用自定义转换器转换为指定类型
     *
     * @param id        主键 ID
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的对象，如果未找到则返回 null
     */
    default <T> T pojoById(Serializable id, Converter<E, T> converter) {
        if (id == null) {
            return null;
        }
        E entity = getById(id);
        if (entity == null) {
            return null;
        }
        return converter.convert(entity);
    }

    /**
     * 根据 Wrapper 条件查询单条记录并转换为指定的 POJO 类型
     *
     * @param wrapper 查询条件包装器
     * @param clazz   目标 POJO 类型
     * @param <T>     目标类型
     * @return 转换后的 POJO 对象，如果未找到则返回 null
     */
    default <T> T pojoOne(Wrapper<E> wrapper, Class<T> clazz) {
        E entity = getOne(wrapper);
        if (entity == null) {
            return null;
        }
        return BeanUtil.toBean(entity, clazz);
    }

    /**
     * 根据 Wrapper 条件查询单条记录并使用自定义转换器转换
     *
     * @param wrapper   查询条件包装器
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的对象，如果未找到则返回 null
     */
    default <T> T pojoOne(Wrapper<E> wrapper, Converter<E, T> converter) {
        E entity = getOne(wrapper);
        if (entity == null) {
            return null;
        }
        return converter.convert(entity);
    }

    /**
     * 根据查询对象获取单条记录
     *
     * @param query 查询条件对象
     * @return 符合条件的实体对象，如果未找到则返回 null
     */
    E getByQuery(Object query);

    /**
     * 根据查询对象获取单条记录并转换为指定的 POJO 类型
     *
     * @param query 查询条件对象
     * @param clazz 目标 POJO 类型
     * @param <T>   目标类型
     * @return 转换后的 POJO 对象，如果未找到则返回 null
     */
    default <T> T pojoByQuery(Object query, Class<T> clazz) {
        E entity = getByQuery(query);
        if (entity == null) {
            return null;
        }
        return BeanUtil.toBean(entity, clazz);
    }

    /**
     * 根据查询对象获取单条记录并使用自定义转换器转换
     *
     * @param query     查询条件对象
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的对象，如果未找到则返回 null
     */
    default <T> T pojoByQuery(Object query, Converter<E, T> converter) {
        E entity = getByQuery(query);
        if (entity == null) {
            return null;
        }
        return converter.convert(entity);
    }

    /**
     * 查询所有记录并转换为指定的 POJO 类型列表
     *
     * @param clazz 目标 POJO 类型
     * @param <T>   目标类型
     * @return 转换后的 POJO 列表
     */
    default <T> List<T> pojoList(Class<T> clazz) {
        List<E> entities = list();
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    /**
     * 查询所有记录并使用自定义转换器转换
     *
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的列表
     */
    default <T> List<T> pojoList(Converter<E, T> converter) {
        List<E> entities = list();
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(converter::convert).collect(Collectors.toList());
    }

    /**
     * 根据 Wrapper 条件查询列表并转换为指定的 POJO 类型
     *
     * @param wrapper 查询条件包装器
     * @param clazz   目标 POJO 类型
     * @param <T>     目标类型
     * @return 转换后的 POJO 列表
     */
    default <T> List<T> pojoList(Wrapper<E> wrapper, Class<T> clazz) {
        List<E> entities = list(wrapper);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    /**
     * 根据 Wrapper 条件查询列表并使用自定义转换器转换
     *
     * @param wrapper   查询条件包装器
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的列表
     */
    default <T> List<T> pojoList(Wrapper<E> wrapper, Converter<E, T> converter) {
        List<E> entities = list(wrapper);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(converter::convert).collect(Collectors.toList());
    }

    /**
     * 根据 ID 列表批量查询并转换为指定的 POJO 类型
     *
     * @param ids   主键 ID 集合
     * @param clazz 目标 POJO 类型
     * @param <T>   目标类型
     * @return 转换后的 POJO 列表
     */
    default <T> List<T> pojoListByIds(Collection<? extends Serializable> ids, Class<T> clazz) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<E> entities = listByIds(ids);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    /**
     * 根据 ID 列表批量查询并使用自定义转换器转换
     *
     * @param ids       主键 ID 集合
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的列表
     */
    default <T> List<T> pojoListByIds(Collection<? extends Serializable> ids, Converter<E, T> converter) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<E> entities = listByIds(ids);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(converter::convert).collect(Collectors.toList());
    }

    /**
     * 根据查询对象获取符合条件的记录列表
     *
     * @param query 查询条件对象
     * @return 符合条件的实体列表
     */
    List<E> listByQuery(Object query);

    /**
     * 根据查询对象获取列表并转换为指定的 POJO 类型
     *
     * @param query 查询条件对象
     * @param clazz 目标 POJO 类型
     * @param <T>   目标类型
     * @return 转换后的 POJO 列表
     */
    default <T> List<T> pojoListByQuery(Object query, Class<T> clazz) {
        Collection<E> entities = listByQuery(query);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    /**
     * 根据查询对象获取列表并使用自定义转换器转换
     *
     * @param query     查询条件对象
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的列表
     */
    default <T> List<T> pojoListByQuery(Object query, Converter<E, T> converter) {
        Collection<E> entities = listByQuery(query);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(converter::convert).collect(Collectors.toList());
    }

    /**
     * 执行 Spring Data 风格的分页查询
     *
     * @param pageable Spring Data 分页参数
     * @param wrapper  查询条件包装器
     * @return 分页结果对象
     */
    org.springframework.data.domain.Page<E> springPage(Pageable pageable, Wrapper<E> wrapper);

    /**
     * 执行 Spring Data 风格的分页查询（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param wrapper   查询条件包装器
     * @return 分页结果对象
     */
    default org.springframework.data.domain.Page<E> springPage(PageQuery pageQuery, Wrapper<E> wrapper) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPage(pageable, wrapper);
    }

    /**
     * 执行 Spring Data 风格的分页查询（基于实体条件）
     *
     * @param pageable Spring Data 分页参数
     * @param entity   查询实体
     * @return 分页结果对象
     */
    default org.springframework.data.domain.Page<E> springPage(Pageable pageable, E entity) {
        return springPage(pageable, Wrappers.query(entity));
    }

    /**
     * 执行 Spring Data 风格的分页查询（使用自定义 {@link PageQuery} 和实体条件）
     *
     * @param pageQuery 自定义分页查询参数
     * @param entity    查询实体
     * @return 分页结果对象
     */
    default org.springframework.data.domain.Page<E> springPage(PageQuery pageQuery, E entity) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPage(pageable, Wrappers.query(entity));
    }

    /**
     * 执行分页查询并将结果转换为指定的 POJO 类型
     *
     * @param pageable Spring Data 分页参数
     * @param wrapper  查询条件包装器
     * @param clazz    目标 POJO 类型
     * @param <T>      目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(Pageable pageable, Wrapper<E> wrapper,
        Class<T> clazz) {
        org.springframework.data.domain.Page<E> page = springPage(pageable, wrapper);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = BeanUtil.toBeanList(page.getContent(), clazz);
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    /**
     * 执行分页查询并将结果转换为指定的 POJO 类型（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param wrapper   查询条件包装器
     * @param clazz     目标 POJO 类型
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(PageQuery pageQuery, Wrapper<E> wrapper,
        Class<T> clazz) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPage(pageable, wrapper, clazz);
    }

    /**
     * 执行分页查询并使用自定义转换器转换结果
     *
     * @param pageable  Spring Data 分页参数
     * @param wrapper   查询条件包装器
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(Pageable pageable, Wrapper<E> wrapper,
        Converter<E, T> converter) {
        org.springframework.data.domain.Page<E> page = springPage(pageable, wrapper);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = page.getContent().stream().map(converter::convert).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    /**
     * 执行分页查询并使用自定义转换器转换结果（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param wrapper   查询条件包装器
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(PageQuery pageQuery, Wrapper<E> wrapper,
        Converter<E, T> converter) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPage(pageable, wrapper, converter);
    }

    /**
     * 根据查询对象执行 Spring Data 风格的分页查询
     *
     * @param pageable Spring Data 分页参数
     * @param query    查询条件对象
     * @return 分页结果对象
     */
    default org.springframework.data.domain.Page<E> springPageByQuery(Pageable pageable, Object query) {
        return springPage(pageable, MybatisPlusUtil.buildQueryWrapper(query));
    }

    /**
     * 根据查询对象执行 Spring Data 风格的分页查询（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param query     查询条件对象
     * @return 分页结果对象
     */
    default org.springframework.data.domain.Page<E> springPageByQuery(PageQuery pageQuery, Object query) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPageByQuery(pageable, query);
    }

    /**
     * 根据查询对象执行分页查询并将结果转换为指定的 POJO 类型
     *
     * @param pageable Spring Data 分页参数
     * @param query    查询条件对象
     * @param clazz    目标 POJO 类型
     * @param <T>      目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(Pageable pageable, Object query,
        Class<T> clazz) {
        org.springframework.data.domain.Page<E> page = springPageByQuery(pageable, query);
        List<T> list;
        if (CollectionUtil.isEmpty(page.getContent())) {
            list = Collections.emptyList();
        } else {
            list = BeanUtil.toBeanList(page.getContent(), clazz);
        }
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    /**
     * 根据查询对象执行分页查询并将结果转换为指定的 POJO 类型（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param query     查询条件对象
     * @param clazz     目标 POJO 类型
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(PageQuery pageQuery, Object query,
        Class<T> clazz) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPageByQuery(pageable, query, clazz);
    }

    /**
     * 根据查询对象执行分页查询并使用自定义转换器转换结果
     *
     * @param pageable  Spring Data 分页参数
     * @param query     查询条件对象
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(Pageable pageable, Object query,
        Converter<E, T> converter) {
        org.springframework.data.domain.Page<E> page = springPageByQuery(pageable, query);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = page.getContent().stream().map(converter::convert).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    /**
     * 根据查询对象执行分页查询并使用自定义转换器转换结果（使用自定义 {@link PageQuery}）
     *
     * @param pageQuery 自定义分页查询参数
     * @param query     查询条件对象
     * @param converter 转换函数
     * @param <T>       目标类型
     * @return 转换后的分页结果
     */
    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(PageQuery pageQuery, Object query,
        Converter<E, T> converter) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPageByQuery(pageable, query, converter);
    }

}
