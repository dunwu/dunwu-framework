package io.github.dunwu.common.dal.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.common.dal.mybatis.util.MybatisPlusUtil;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link IExtDao} 基础实现类
 * <p>
 * 继承自 {@link DaoImpl}，实现了扩展的 DAO 接口 {@link IExtDao}。
 * 提供了基于查询对象（Query Object）的计数、单条查询、列表查询以及 Spring Data 分页查询的支持。
 *
 * @param <M> Mapper 类型
 * @param <E> 实体类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
public abstract class BaseExtDaoImpl<M extends BaseMapper<E>, E> extends DaoImpl<M, E>
    implements IExtDao<E> {

    /**
     * 根据查询对象统计符合条件的记录数
     *
     * @param query 查询条件对象
     * @return 符合条件的记录总数
     */
    @Override
    public Long countByQuery(Object query) {
        return baseMapper.selectCount(MybatisPlusUtil.buildQueryWrapper(query));
    }

    /**
     * 根据查询对象获取单条记录
     * <p>
     * 如果查询结果多于一条，可能会抛出异常或返回第一条（取决于 MyBatis-Plus 配置）。
     *
     * @param query 查询条件对象
     * @return 符合条件的实体对象，如果未找到则返回 null
     */
    @Override
    public E getByQuery(Object query) {
        return baseMapper.selectOne(MybatisPlusUtil.buildQueryWrapper(query));
    }

    /**
     * 根据查询对象获取所有符合条件的记录列表
     *
     * @param query 查询条件对象
     * @return 符合条件的实体列表
     */
    @Override
    public List<E> listByQuery(Object query) {
        return baseMapper.selectList(MybatisPlusUtil.buildQueryWrapper(query));
    }

    /**
     * 执行 Spring Data 风格的分页查询
     * <p>
     * 将 Spring Data 的 {@link Pageable} 转换为 MyBatis-Plus 的分页对象，执行查询后再转换回 Spring Data 的 {@link org.springframework.data.domain.Page}。
     *
     * @param pageable Spring Data 分页参数
     * @param wrapper  查询条件包装器
     * @return 分页结果对象
     */
    @Override
    public org.springframework.data.domain.Page<E> springPage(Pageable pageable, Wrapper<E> wrapper) {
        Page<E> queryPage = MybatisPlusUtil.toMybatisPlusPage(pageable);
        Page<E> page = page(queryPage, wrapper);
        return MybatisPlusUtil.toSpringPage(page);
    }

}
