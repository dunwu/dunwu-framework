package io.github.dunwu.tool.data.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.tool.data.mybatis.util.MybatisPlusUtil;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link IExtDao} 基本实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
public abstract class BaseExtDaoImpl<M extends BaseMapper<E>, E> extends DaoImpl<M, E>
    implements IExtDao<E> {

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
    public org.springframework.data.domain.Page<E> springPage(Pageable pageable, Wrapper<E> wrapper) {
        Page<E> queryPage = MybatisPlusUtil.toMybatisPlusPage(pageable);
        Page<E> page = page(queryPage, wrapper);
        return MybatisPlusUtil.toSpringPage(page);
    }

}
