package io.github.dunwu.tool.data.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.github.dunwu.tool.bean.TypeConvert;
import io.github.dunwu.tool.data.Pagination;
import io.github.dunwu.tool.data.mybatis.util.MybatisPlusUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link IExtDao} 基本实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
public abstract class BaseExtDaoImpl<M extends BaseMapper<E>, E> extends DaoImpl<M, E>
    implements IExtDao<E> {

    @Override
    public <T> org.springframework.data.domain.Page<T> springPage(Pageable pageable, Wrapper<E> wrapper,
        TypeConvert<E, T> convert) {
        List<T> list = Lists.newArrayList();
        Page<E> queryPage = MybatisPlusUtil.toMybatisPlusPage(pageable);
        Page<E> page = page(queryPage, wrapper);
        if (page.getTotal() == 0) {
            return new PageImpl(list, pageable, 0L);
        }
        list.addAll(page.getRecords().stream().map(convert::transform).collect(Collectors.toList()));
        return new PageImpl<>(list, pageable, page.getTotal());
    }

    @Override
    public org.springframework.data.domain.Page<E> springPage(Pageable pageable, Wrapper<E> wrapper) {
        Page<E> queryPage = MybatisPlusUtil.toMybatisPlusPage(pageable);
        Page<E> page = page(queryPage, wrapper);
        return MybatisPlusUtil.toSpringPage(page);
    }

    @Override
    public Integer countByQuery(Object query) {
        return baseMapper.selectCount(MybatisPlusUtil.buildQueryWrapper(query));
    }

    @Override
    public E getByQuery(Object query) {
        return baseMapper.selectOne(MybatisPlusUtil.buildQueryWrapper(query));
    }

    @Override
    public List<E> listByQuery(Object query) {
        return baseMapper.selectList(MybatisPlusUtil.buildQueryWrapper(query));
    }

    @Override
    public <T> org.springframework.data.domain.Page<T> pojoPage(Pageable pageable, Wrapper<E> wrapper,
        TypeConvert<E, T> convert) {
        org.springframework.data.domain.Page<E> springPage = springPage(pageable, wrapper);
        List<T> list = new ArrayList<>();
        if (CollectionUtil.isEmpty(springPage.getContent())) {
            return new Pagination<>(list, pageable, springPage.getTotalElements());
        }
        list.addAll(springPage.getContent().stream().map(convert::transform).collect(Collectors.toList()));
        return new Pagination<>(list, pageable, springPage.getTotalElements());
    }

    @Override
    public org.springframework.data.domain.Page<E> pojoPageByQuery(Object query, Pageable pageable) {
        return springPage(pageable, MybatisPlusUtil.buildQueryWrapper(query));
    }

}
