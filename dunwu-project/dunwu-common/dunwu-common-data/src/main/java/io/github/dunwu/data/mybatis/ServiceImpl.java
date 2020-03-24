package io.github.dunwu.data.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.data.QueryRequest;
import io.github.dunwu.data.mybatis.support.MybatisPlusUtil;
import io.github.dunwu.tool.util.StringUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public class ServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
    implements IService<T> {

    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    protected M baseMapper;

    @Override
    public DataResult<Integer> count(Wrapper<T> queryWrapper) {
        int count = SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
        return DataResult.success(count);
    }

    @Override
    public DataResult<T> getById(Serializable id) {
        return DataResult.success(baseMapper.selectById(id));
    }

    @Override
    public DataResult<T> getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (throwEx) {
            return DataResult.success(baseMapper.selectOne(queryWrapper));
        }
        T entity = SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
        return DataResult.success(entity);
    }

    @Override
    public <V> DataResult<V> getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        List<V> list = (List<V>) listObjs(queryWrapper, mapper).getData();
        V entity = SqlHelper.getObject(log, list);
        return DataResult.success(entity);
    }

    @Override
    public DataResult<Map<String, Object>> getMap(Wrapper<T> queryWrapper) {
        Map<String, Object> map = SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
        return DataResult.success(map);
    }

    @Override
    public DataListResult<T> list(Wrapper<T> queryWrapper) {
        return DataListResult.success(baseMapper.selectList(queryWrapper));
    }

    @Override
    public DataListResult<T> listByIds(Collection<? extends Serializable> idList) {
        return DataListResult.success(baseMapper.selectBatchIds(idList));
    }

    @Override
    public DataListResult<T> listByMap(Map<String, Object> columnMap) {
        return DataListResult.success(baseMapper.selectByMap(columnMap));
    }

    @Override
    public <V> DataListResult<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        List<V> list = baseMapper.selectObjs(queryWrapper).stream()
            .filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
        return DataListResult.success(list);
    }

    @Override
    public DataListResult<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return DataListResult.success(baseMapper.selectMaps(queryWrapper));
    }

    @Override
    public PageResult<T> page(QueryRequest<T> query) {
        QueryWrapper<T> queryWrapper = Wrappers.query(query.getEntity());
        Page<T> page = MybatisPlusUtil.transToMybatisPlusPage(query);
        Page<T> resultPage = baseMapper.selectPage(page, queryWrapper);
        return PageResult.success(MybatisPlusUtil.transToPagination(resultPage));
    }

    @Override
    public PageResult<Map<String, Object>> pageMaps(QueryRequest<T> query) {
        QueryWrapper<T> queryWrapper = Wrappers.query(query.getEntity());
        Page<Map<String, Object>> page = MybatisPlusUtil.transToMybatisPlusMapPage(query);
        Page<Map<String, Object>> resultPage = baseMapper.selectMapsPage(page, queryWrapper);
        return PageResult.success(MybatisPlusUtil.transToPagination(resultPage));
    }

    @Override
    public DataResult<Integer> insert(T entity) {
        boolean result = retBool(baseMapper.insert(entity));
        if (result) {
            return DataResult.success(entity.getId());
        } else {
            return DataResult.failData(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult insertBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }

        return BaseResult.success();
    }

    @Override
    public BaseResult update(T entity, Wrapper<T> updateWrapper) {
        boolean result = retBool(baseMapper.update(entity, updateWrapper));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult updateBatchById(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }

        return BaseResult.success();
    }

    @Override
    public BaseResult updateById(T entity) {
        boolean result = retBool(baseMapper.updateById(entity));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult save(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo,
                "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty,
                "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getMethodValue(cls, entity,
                tableInfo.getKeyProperty());
            return StringUtil.isEmptyIfStr(idVal)
                || Objects.isNull(getById((Serializable) idVal)) ? insert(entity)
                : updateById(entity);
        }

        return BaseResult.success();
    }

    @Override
    public BaseResult saveBatch(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        Class<?> cls = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        Assert.notNull(tableInfo,
            "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty,
            "error: can not execute. because can not find column for id from entity!");
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T entity : entityList) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
                if (StringUtil.isEmptyIfStr(idVal)
                    || Objects.isNull(getById((Serializable) idVal))) {
                    batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                } else {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, entity);
                    batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                }
                // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }

        return BaseResult.success();
    }

    @Override
    public BaseResult delete(Wrapper<T> wrapper) {
        boolean result = SqlHelper.retBool(baseMapper.delete(wrapper));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult deleteById(Serializable id) {
        boolean result = SqlHelper.retBool(baseMapper.deleteById(id));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult deleteBatchIds(Collection<? extends Serializable> idList) {
        boolean result = SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public BaseResult deleteByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        boolean result = SqlHelper.retBool(baseMapper.deleteByMap(columnMap));
        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession,
            GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

}
