package io.github.dunwu.tool.data.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.bean.TypeConvert;
import io.github.dunwu.tool.data.mybatis.util.MybatisPlusUtil;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.util.PageUtil;
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
 * Dao 扩展
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public interface IExtDao<E> extends IDao<E> {

    Integer countByQuery(Object query);

    default <T> T pojoById(Serializable id, Class<T> clazz) {
        if (id == null) {
            return null;
        }
        E entity = getById(id);
        if (entity == null) { return null; }
        return BeanUtil.toBean(entity, clazz);
    }

    default <T> T pojoById(Serializable id, TypeConvert<E, T> callback) {
        if (id == null) {
            return null;
        }
        E entity = getById(id);
        if (entity == null) { return null; }
        return callback.transform(entity);
    }

    default <T> T pojoOne(Wrapper<E> wrapper, Class<T> clazz) {
        E entity = getOne(wrapper);
        if (entity == null) { return null; }
        return BeanUtil.toBean(entity, clazz);
    }

    default <T> T pojoOne(Wrapper<E> wrapper, TypeConvert<E, T> convert) {
        E entity = getOne(wrapper);
        if (entity == null) { return null; }
        return convert.transform(entity);
    }

    E getByQuery(Object query);

    default <T> T pojoByQuery(Object query, Class<T> clazz) {
        E entity = getByQuery(query);
        if (entity == null) { return null; }
        return BeanUtil.toBean(entity, clazz);
    }

    default <T> T pojoByQuery(Object query, TypeConvert<E, T> convert) {
        E entity = getByQuery(query);
        if (entity == null) { return null; }
        return convert.transform(entity);
    }

    default <T> List<T> pojoList(Class<T> clazz) {
        List<E> entities = list();
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    default <T> List<T> pojoList(TypeConvert<E, T> convert) {
        List<E> entities = list();
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

    default <T> List<T> pojoList(Wrapper<E> wrapper, Class<T> clazz) {
        List<E> entities = list(wrapper);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    default <T> List<T> pojoList(Wrapper<E> wrapper, TypeConvert<E, T> convert) {
        List<E> entities = list(wrapper);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

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

    default <T> List<T> pojoListByIds(Collection<? extends Serializable> ids, TypeConvert<E, T> convert) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<E> entities = listByIds(ids);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

    List<E> listByQuery(Object query);

    default <T> List<T> pojoListByQuery(Object query, Class<T> clazz) {
        Collection<E> entities = listByQuery(query);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    default <T> List<T> pojoListByQuery(Object query, TypeConvert<E, T> convert) {
        Collection<E> entities = listByQuery(query);
        if (CollectionUtil.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

    /**
     * 分页查询
     *
     * @param pageable {@link Pageable} 分页查询参数
     * @param wrapper  {@link Wrapper<E>} Mybatis Plus 实体 Wrapper，将查询参数包裹
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    org.springframework.data.domain.Page<E> springPage(Pageable pageable, Wrapper<E> wrapper);

    /**
     * 分页查询
     *
     * @param pageQuery {@link PageQuery} 分页查询参数
     * @param wrapper   {@link Wrapper<E>} Mybatis Plus 实体 Wrapper，将查询参数包裹
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    default org.springframework.data.domain.Page<E> springPage(PageQuery pageQuery, Wrapper<E> wrapper) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPage(pageable, wrapper);
    }

    /**
     * 分页查询
     *
     * @param pageable {@link Pageable} 分页查询参数
     * @param entity   查询实体
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    default org.springframework.data.domain.Page<E> springPage(Pageable pageable, E entity) {
        return springPage(pageable, Wrappers.query(entity));
    }

    /**
     * 分页查询
     *
     * @param pageQuery {@link PageQuery} 分页查询参数
     * @param entity    查询实体
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    default org.springframework.data.domain.Page<E> springPage(PageQuery pageQuery, E entity) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPage(pageable, Wrappers.query(entity));
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(Pageable pageable, Wrapper<E> wrapper,
        Class<T> clazz) {
        org.springframework.data.domain.Page<E> page = springPage(pageable, wrapper);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = BeanUtil.toBeanList(page.getContent(), clazz);
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(PageQuery pageQuery, Wrapper<E> wrapper,
        Class<T> clazz) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPage(pageable, wrapper, clazz);
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(Pageable pageable, Wrapper<E> wrapper,
        TypeConvert<E, T> convert) {
        org.springframework.data.domain.Page<E> page = springPage(pageable, wrapper);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = page.getContent().stream().map(convert::transform).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPage(PageQuery pageQuery, Wrapper<E> wrapper,
        TypeConvert<E, T> convert) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPage(pageable, wrapper, convert);
    }

    default org.springframework.data.domain.Page<E> springPageByQuery(Pageable pageable, Object query) {
        return springPage(pageable, MybatisPlusUtil.buildQueryWrapper(query));
    }

    default org.springframework.data.domain.Page<E> springPageByQuery(PageQuery pageQuery, Object query) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return springPageByQuery(pageable, query);
    }

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

    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(PageQuery pageQuery, Object query,
        Class<T> clazz) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPageByQuery(pageable, query, clazz);
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(Pageable pageable, Object query,
        TypeConvert<E, T> convert) {
        org.springframework.data.domain.Page<E> page = springPageByQuery(pageable, query);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }
        List<T> list = page.getContent().stream().map(convert::transform).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    default <T> org.springframework.data.domain.Page<T> pojoSpringPageByQuery(PageQuery pageQuery, Object query,
        TypeConvert<E, T> convert) {
        PageRequest pageable = PageUtil.toPageRequest(pageQuery);
        return pojoSpringPageByQuery(pageable, query, convert);
    }

}
