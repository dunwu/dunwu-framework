package io.github.dunwu.tool.data.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.bean.TypeConvert;
import io.github.dunwu.tool.data.core.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public interface IExtDao<E> extends IDao<E> {

    /**
     * 翻页查询
     *
     * @param pageable {@link Pageable} 分页查询参数
     * @param wrapper  {@link QueryWrapper<E>} Mybatis Plus 实体 Wrapper，将查询参数包裹
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    Page<E> springPage(Pageable pageable, Wrapper<E> wrapper);

    /**
     * 翻页查询增加convert方法
     *
     * @param pageable {@link Pageable} 分页查询参数
     * @param wrapper  {@link QueryWrapper<T>} Mybatis Plus 实体 Wrapper，将查询参数包裹
     * @param convert  转换方法
     * @return {@link org.springframework.data.domain.Page<T>}
     */
    <T> Page<T> springPage(Pageable pageable, Wrapper<E> wrapper,
        TypeConvert<E, T> convert);

    /**
     * 翻页查询
     *
     * @param pageable {@link Pageable} 分页查询参数
     * @param entity   查询实体
     * @return {@link org.springframework.data.domain.Page<E>}
     */
    default Page<E> springPage(Pageable pageable, E entity) {
        return springPage(pageable, Wrappers.query(entity));
    }

    Integer countByQuery(Object query);

    E getByQuery(Object query);

    default <T> T pojoById(Serializable id, Class<T> clazz) {
        E entity = getById(id);
        if (entity == null) { return null; }
        return BeanUtil.toBean(entity, clazz);
    }

    default <T> T pojoById(Serializable id, TypeConvert<E, T> callback) {
        E entity = getById(id);
        if (entity == null) { return null; }
        return callback.transform(entity);
    }

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

    List<E> listByQuery(Object query);

    default <T> List<T> pojoListByIds(Collection<? extends Serializable> idList, Class<T> clazz) {
        List<E> entities = listByIds(idList);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return BeanUtil.toBeanList(entities, clazz);
    }

    default <T> List<T> pojoListByIds(Collection<? extends Serializable> idList, TypeConvert<E, T> convert) {
        List<E> entities = listByIds(idList);
        if (CollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

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
            return new ArrayList<>();
        }
        return entities.stream().map(convert::transform).collect(Collectors.toList());
    }

    default <T> Page<T> pojoPage(Pageable pageable, Wrapper<E> wrapper, Class<T> clazz) {
        Page<E> page = springPage(pageable, wrapper);
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new Pagination<>(new ArrayList<>(), pageable, page.getTotalElements());
        }
        List<T> list = BeanUtil.toBeanList(page.getContent(), clazz);
        return new Pagination<>(list, pageable, page.getTotalElements());
    }

    default <T> Page<T> pojoPage(Pageable pageable, Wrapper<E> wrapper, TypeConvert<E, T> convert) {
        Page<E> page = springPage(pageable, wrapper);
        List<T> list = new ArrayList<>();
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new Pagination<>(list, pageable, page.getTotalElements());
        }
        list.addAll(page.getContent().stream().map(convert::transform).collect(Collectors.toList()));
        return new Pagination<>(list, pageable, page.getTotalElements());
    }

    Page<E> pojoPageByQuery(Object query, Pageable pageable);

    default <T> Page<T> pojoPageByQuery(Object query, Pageable pageable, Class<T> clazz) {
        Page<E> page = pojoPageByQuery(query, pageable);
        List<T> list;
        if (CollectionUtil.isEmpty(page.getContent())) {
            list = new ArrayList<>();
        } else {
            list = BeanUtil.toBeanList(page.getContent(), clazz);
        }
        return new Pagination<>(list, pageable, page.getTotalElements());
    }

    default <T> Page<T> pojoPageByQuery(Object query, Pageable pageable, TypeConvert<E, T> convert) {
        Page<E> page = pojoPageByQuery(query, pageable);
        List<T> list = new ArrayList<>();
        if (CollectionUtil.isEmpty(page.getContent())) {
            return new Pagination<>(list, pageable, page.getTotalElements());
        }
        list.addAll(page.getContent().stream().map(convert::transform).collect(Collectors.toList()));
        return new Pagination<>(list, pageable, page.getTotalElements());
    }

}
