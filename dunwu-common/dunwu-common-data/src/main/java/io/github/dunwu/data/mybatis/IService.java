package io.github.dunwu.data.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.core.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public interface IService<T> {

    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default DataResult<Integer> count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    DataResult<Integer> count(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param entity 查询实体
     */
    default DataResult<Integer> count(T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return count(queryWrapper);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    DataResult<T> getById(Serializable id);

    /**
     * 查询一条记录
     *
     * @param entity 查询实体
     */
    default DataResult<Map<String, Object>> getMap(T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return getMap(queryWrapper);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    DataResult<Map<String, Object>> getMap(Wrapper<T> queryWrapper);

    /**
     * 查询一条记录
     *
     * @param entity 查询实体
     * @param mapper 转换函数
     */
    default <V> DataResult<V> getObj(T entity, Function<? super Object, V> mapper) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return getObj(queryWrapper, mapper);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    <V> DataResult<V> getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 查询一条记录
     *
     * @param entity  查询实体
     * @param throwEx 有多个 result 是否抛出异常
     */
    default DataResult<T> getOne(T entity, boolean throwEx) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return getOne(queryWrapper, throwEx);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    DataResult<T> getOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 entity，查询一条记录 <br/>
     * <p>
     * 结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
     * </p>
     *
     * @param entity 实体对象
     */
    default DataResult<T> getOne(T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return getOne(queryWrapper);
    }

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>
     * 结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
     * </p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default DataResult<T> getOne(Wrapper<T> queryWrapper) {
        return getOne(queryWrapper, true);
    }

    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default DataListResult<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    DataListResult<T> list(Wrapper<T> queryWrapper);

    /**
     * 查询列表
     *
     * @param entity 查询实体
     */
    default DataListResult<T> list(T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return list(queryWrapper);
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    DataListResult<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    DataListResult<T> listByMap(Map<String, Object> columnMap);

    /**
     * 查询所有列表
     *
     * @see Wrappers#emptyWrapper()
     */
    default DataListResult<Map<String, Object>> listMaps() {
        return listMaps(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    DataListResult<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);

    /**
     * 查询列表
     *
     * @param entity 查询实体对象
     */
    default DataListResult<Map<String, Object>> listMaps(T entity) {
        UpdateWrapper<T> updateWrapper = Wrappers.update(entity);
        return listMaps(updateWrapper);
    }

    /**
     * 查询全部记录
     */
    default DataListResult<Object> listObjs() {
        return listObjs(Function.identity());
    }

    /**
     * 查询全部记录
     *
     * @param mapper 转换函数
     */
    default <V> DataListResult<V> listObjs(Function<? super Object, V> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    <V> DataListResult<V> listObjs(Wrapper<T> queryWrapper,
        Function<? super Object, V> mapper);

    /**
     * 查询全部记录
     *
     * @param entity 查询实体对象
     */
    default DataListResult<Object> listObjs(T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return listObjs(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default DataListResult<Object> listObjs(Wrapper<T> queryWrapper) {
        return listObjs(queryWrapper, Function.identity());
    }

    /**
     * 无条件翻页查询
     *
     * @param pagination 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default PageResult<T> page(Pagination<T> pagination) {
        return page(pagination, Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param pagination   翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    PageResult<T> page(Pagination<T> pagination, Wrapper<T> queryWrapper);

    /**
     * 翻页查询
     *
     * @param pagination 翻页对象
     * @param entity     查询实体
     */
    default PageResult<T> page(Pagination<T> pagination, T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return page(pagination, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param pagination 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default PageResult<Map<String, Object>> pageMaps(Pagination<T> pagination) {
        return pageMaps(pagination, Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param pagination   翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    PageResult<Map<String, Object>> pageMaps(Pagination<T> pagination,
        Wrapper<T> queryWrapper);

    /**
     * 翻页查询
     *
     * @param pagination 翻页对象
     * @param entity     查询实体
     */
    default PageResult<Map<String, Object>> pageMaps(Pagination<T> pagination, T entity) {
        QueryWrapper<T> queryWrapper = Wrappers.query(entity);
        return pageMaps(pagination, queryWrapper);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param entity 待删除实体
     */
    default BaseResult remove(T entity) {
        UpdateWrapper<T> updateWrapper = Wrappers.update(entity);
        return remove(updateWrapper);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    BaseResult remove(Wrapper<T> queryWrapper);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    BaseResult removeById(Serializable id);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    BaseResult removeByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    BaseResult removeByMap(Map<String, Object> columnMap);

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    DataResult<Integer> save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default BaseResult saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 1000);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    BaseResult saveBatch(Collection<T> entityList, int batchSize);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    BaseResult saveOrUpdate(T entity);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    default BaseResult saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, 1000);
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     */
    @Transactional(rollbackFor = Exception.class)
    BaseResult saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据 origin 条件，更新记录
     *
     * @param entity 实体对象
     * @param origin 查询实体对象
     */
    default BaseResult update(T entity, T origin) {
        UpdateWrapper<T> updateWrapper = Wrappers.update(origin);
        return update(entity, updateWrapper);
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    BaseResult update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default BaseResult update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default BaseResult updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 1000);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    BaseResult updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    BaseResult updateById(T entity);

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

}
