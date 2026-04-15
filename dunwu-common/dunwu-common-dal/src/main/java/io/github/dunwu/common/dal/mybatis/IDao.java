/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.dunwu.common.dal.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.github.dunwu.common.dal.domain.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 顶级 DAO 接口
 * <p>
 * 定义了通用的 CRUD 操作、批量操作、分页查询以及链式查询等核心数据访问方法。
 * 实现类通常继承自 {@link DaoImpl} 并配合 MyBatis-Plus 的 {@link BaseMapper} 使用。
 *
 * @param <E> 实体类型
 * @author hubin
 * @since 2018-06-23
 */
public interface IDao<E> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 批量插入实体
     *
     * @param entityList 实体对象集合
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean insertBatch(Collection<E> entityList) {
        return insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量插入实体
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次大小
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    boolean insertBatch(Collection<E> entityList, int batchSize);

    /**
     * 批量修改或插入（根据 ID 判断）
     *
     * @param entityList 实体对象集合
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<E> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量修改或插入（根据 ID 判断）
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次处理的数量
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveBatch(Collection<E> entityList, int batchSize);

    /**
     * 根据主键 ID 删除记录
     *
     * @param id 主键 ID
     * @return 如果删除成功返回 true，否则返回 false
     */
    default boolean deleteById(Serializable id) {
        return SqlHelper.retBool(getBaseMapper().deleteById(id));
    }

    /**
     * 获取当前实体对应的 BaseMapper
     *
     * @return BaseMapper 实例
     */
    BaseMapper<E> getBaseMapper();

    /**
     * 根据 columnMap 条件删除记录
     *
     * @param columnMap 表字段 map 对象（key 为列名，value 为值）
     * @return 如果删除成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(getBaseMapper().deleteByMap(columnMap));
    }

    /**
     * 根据 Wrapper 条件删除记录
     *
     * @param queryWrapper 查询条件包装器 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return 如果删除成功返回 true，否则返回 false
     */
    default boolean delete(Wrapper<E> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 根据 ID 批量删除记录
     *
     * @param idList 主键 ID 列表
     * @return 如果删除成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteBatchByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(getBaseMapper().deleteBatchIds(idList));
    }

    /**
     * 根据 ID 选择性更新记录（只更新非空字段）
     *
     * @param entity 实体对象
     * @return 如果更新成功返回 true，否则返回 false
     */
    default boolean updateById(E entity) {
        return SqlHelper.retBool(getBaseMapper().updateById(entity));
    }

    /**
     * 根据 UpdateWrapper 条件更新记录（需要设置 sqlset）
     *
     * @param updateWrapper 更新条件包装器 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     * @return 如果更新成功返回 true，否则返回 false
     */
    default boolean update(Wrapper<E> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * 根据 whereEntity 条件更新记录
     *
     * @param entity        实体对象（包含要更新的字段）
     * @param updateWrapper 更新条件包装器
     * @return 如果更新成功返回 true，否则返回 false
     */
    default boolean update(E entity, Wrapper<E> updateWrapper) {
        return SqlHelper.retBool(getBaseMapper().update(entity, updateWrapper));
    }

    /**
     * 根据 ID 批量更新记录
     *
     * @param entityList 实体对象集合
     * @return 如果更新成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean updateBatchById(Collection<E> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 根据 ID 批量更新记录
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次大小
     * @return 如果更新成功返回 true，否则返回 false
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateBatchById(Collection<E> entityList, int batchSize);

    /**
     * 保存实体：如果 TableId 注解存在且 ID 不为空则更新，否则插入
     *
     * @param entity 实体对象
     * @return 如果执行成功返回 true，否则返回 false
     */
    boolean save(E entity);

    /**
     * 根据主键 ID 查询单条记录
     *
     * @param id 主键 ID
     * @return 实体对象，如果未找到返回 null
     */
    default E getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 根据 ID 批量查询记录
     *
     * @param idList 主键 ID 列表
     * @return 实体列表
     */
    default List<E> listByIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectBatchIds(idList);
    }

    /**
     * 根据 columnMap 条件查询记录列表
     *
     * @param columnMap 表字段 map 对象
     * @return 实体列表
     */
    default List<E> listByMap(Map<String, Object> columnMap) {
        return getBaseMapper().selectByMap(columnMap);
    }

    /**
     * 根据实体对象条件查询一条记录
     * <p>
     * 如果结果集有多个，会抛出异常；可通过 wrapper.last("LIMIT 1") 限制。
     *
     * @param entity 实体对象（作为查询条件）
     * @return 实体对象
     */
    default E getOne(E entity) {
        QueryWrapper<E> queryWrapper = Wrappers.query(entity);
        return getOne(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件查询一条记录
     * <p>
     * 如果结果集有多个，会抛出异常；可通过 wrapper.last("LIMIT 1") 限制。
     *
     * @param queryWrapper 查询条件包装器
     * @return 实体对象
     */
    default E getOne(Wrapper<E> queryWrapper) {
        return getOne(queryWrapper, true);
    }

    /**
     * 根据 Wrapper 条件查询一条记录
     *
     * @param queryWrapper 查询条件包装器
     * @param throwEx      如果有多个结果是否抛出异常
     * @return 实体对象
     */
    E getOne(Wrapper<E> queryWrapper, boolean throwEx);

    /**
     * 根据实体对象条件查询一条记录并返回 Map
     *
     * @param entity 实体对象
     * @return Map 对象
     */
    default Map<String, Object> getMap(E entity) {
        QueryWrapper<E> queryWrapper = Wrappers.query(entity);
        return getMap(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件查询一条记录并返回 Map
     *
     * @param queryWrapper 查询条件包装器
     * @return Map 对象
     */
    Map<String, Object> getMap(Wrapper<E> queryWrapper);

    /**
     * 根据 Wrapper 条件查询一条记录并应用转换函数
     *
     * @param queryWrapper 查询条件包装器
     * @param mapper       转换函数
     * @param <V>          返回值类型
     * @return 转换后的结果
     */
    <V> V getObj(Wrapper<E> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 查询总记录数（无条件）
     *
     * @return 总记录数
     */
    default long count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件查询总记录数
     *
     * @param queryWrapper 查询条件包装器
     * @return 符合条件的记录总数
     */
    default long count(Wrapper<E> queryWrapper) {
        return SqlHelper.retCount(getBaseMapper().selectCount(queryWrapper));
    }

    /**
     * 根据实体对象条件查询总记录数
     *
     * @param entity 查询实体
     * @return 符合条件的记录总数
     */
    default long count(E entity) {
        QueryWrapper<E> queryWrapper = Wrappers.query(entity);
        return count(queryWrapper);
    }

    /**
     * 根据实体对象条件查询列表
     *
     * @param entity 查询实体
     * @return 实体列表
     */
    default List<E> list(E entity) {
        QueryWrapper<E> queryWrapper = Wrappers.query(entity);
        return list(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件查询列表
     *
     * @param queryWrapper 查询条件包装器
     * @return 实体列表
     */
    default List<E> list(Wrapper<E> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 查询所有记录（无条件）
     *
     * @return 实体列表
     */
    default List<E> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param page   分页对象
     * @param entity 查询实体
     * @param <P>    分页对象类型
     * @return 分页结果
     */
    default <P extends IPage<E>> P page(P page, E entity) {
        return page(page, Wrappers.query(entity));
    }

    /**
     * 翻页查询
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件包装器
     * @param <P>          分页对象类型
     * @return 分页结果
     */
    default <P extends IPage<E>> P page(P page, Wrapper<E> queryWrapper) {
        return getBaseMapper().selectPage(page, queryWrapper);
    }

    /**
     * 无条件翻页查询
     *
     * @param page 分页对象
     * @param <P>  分页对象类型
     * @return 分页结果
     */
    default <P extends IPage<E>> P page(P page) {
        return page(page, Wrappers.emptyWrapper());
    }

    /**
     * 查询所有列表并返回 Map 形式（无条件）
     *
     * @return Map 列表
     */
    default List<Map<String, Object>> listMaps() {
        return listMaps(Wrappers.emptyWrapper());
    }

    /**
     * 根据 Wrapper 条件查询列表并返回 Map 形式
     *
     * @param queryWrapper 查询条件包装器
     * @return Map 列表
     */
    default List<Map<String, Object>> listMaps(Wrapper<E> queryWrapper) {
        return getBaseMapper().selectMaps(queryWrapper);
    }

    /**
     * 查询全部记录并返回 Object 列表
     *
     * @return Object 列表
     */
    default List<Object> listObjs() {
        return listObjs(Function.identity());
    }

    /**
     * 查询全部记录并应用转换函数
     *
     * @param mapper 转换函数
     * @param <V>    返回值类型
     * @return 转换后的列表
     */
    default <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件查询全部记录并应用转换函数
     *
     * @param queryWrapper 查询条件包装器
     * @param mapper       转换函数
     * @param <V>          返回值类型
     * @return 转换后的列表
     */
    default <V> List<V> listObjs(Wrapper<E> queryWrapper, Function<? super Object, V> mapper) {
        return getBaseMapper().selectObjs(queryWrapper)
                              .stream()
                              .filter(Objects::nonNull)
                              .map(mapper)
                              .collect(Collectors.toList());
    }

    /**
     * 根据 Wrapper 条件查询全部记录
     *
     * @param queryWrapper 查询条件包装器
     * @return Object 列表
     */
    default List<Object> listObjs(Wrapper<E> queryWrapper) {
        return listObjs(queryWrapper, Function.identity());
    }

    /**
     * 无条件翻页查询并返回 Map 形式
     *
     * @param page 分页对象
     * @param <P>  分页对象类型
     * @return 分页结果
     */
    default <P extends IPage<Map<String, Object>>> P pageMaps(P page) {
        return pageMaps(page, Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询并返回 Map 形式
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件包装器
     * @param <P>          分页对象类型
     * @return 分页结果
     */
    default <P extends IPage<Map<String, Object>>> P pageMaps(P page, Wrapper<E> queryWrapper) {
        return getBaseMapper().selectMapsPage(page, queryWrapper);
    }

    /**
     * 链式查询（普通）
     *
     * @return QueryChainWrapper 包装类
     */
    default QueryChainWrapper<E> query() {
        return ChainWrappers.queryChain(getBaseMapper());
    }

    /**
     * 链式查询（Lambda 式）
     * <p>注意：不支持 Kotlin</p>
     *
     * @return LambdaQueryChainWrapper 包装类
     */
    default LambdaQueryChainWrapper<E> lambdaQuery() {
        return ChainWrappers.lambdaQueryChain(getBaseMapper());
    }

    /**
     * 链式更新（普通）
     *
     * @return UpdateChainWrapper 包装类
     */
    default UpdateChainWrapper<E> update() {
        return ChainWrappers.updateChain(getBaseMapper());
    }

    /**
     * 链式更新（Lambda 式）
     * <p>注意：不支持 Kotlin</p>
     *
     * @return LambdaUpdateChainWrapper 包装类
     */
    default LambdaUpdateChainWrapper<E> lambdaUpdate() {
        return ChainWrappers.lambdaUpdateChain(getBaseMapper());
    }

    /**
     * 根据 updateWrapper 尝试更新，如果未更新成功则执行插入操作
     * <p>
     * 适用于存在性验证之后的 save 操作，减少代码量。
     *
     * @param entity        实体对象
     * @param updateWrapper 更新条件包装器
     * @return 如果执行成功返回 true，否则返回 false
     */
    default boolean save(E entity, Wrapper<E> updateWrapper) {
        return update(entity, updateWrapper) || insert(entity);
    }

    /**
     * 插入一条记录（选择性字段，策略插入）
     *
     * @param entity 实体对象
     * @return 如果插入成功返回 true，否则返回 false
     */
    default boolean insert(E entity) {
        return SqlHelper.retBool(getBaseMapper().insert(entity));
    }

}
