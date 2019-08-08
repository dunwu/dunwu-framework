package io.github.dunwu.data.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.core.*;
import io.github.dunwu.data.dao.IDao;
import io.github.dunwu.data.entity.BaseEntity;
import io.github.dunwu.data.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public abstract class BaseService<T extends BaseEntity> implements IService<T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IDao<T> dao;

    public IDao<T> getDao() {
        return dao;
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return dao.getBaseMapper();
    }

    @Override
    public DataResult<Serializable> save(T entity) {
        if (dao.save(entity)) {
            return ResultUtil.successDataResult(entity.getId());
        } else {
            return ResultUtil.failDataResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult saveBatch(Collection<T> entityList, int batchSize) {
        if (dao.saveBatch(entityList, batchSize)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (dao.saveOrUpdateBatch(entityList, batchSize)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult removeById(Serializable id) {
        if (dao.removeById(id)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult removeByMap(Map<String, Object> columnMap) {
        if (dao.removeByMap(columnMap)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult remove(Wrapper<T> queryWrapper) {
        if (dao.remove(queryWrapper)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult remove(T entity) {
        if (dao.remove(entity)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        if (dao.removeByIds(idList)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult updateById(T entity) {
        if (dao.updateById(entity)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult update(T entity, Wrapper<T> updateWrapper) {
        if (dao.update(entity, updateWrapper)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult update(T target, T origin) {
        if (dao.update(target, origin)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult updateBatchById(Collection<T> entityList, int batchSize) {
        if (dao.updateBatchById(entityList, batchSize)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public BaseResult saveOrUpdate(T entity) {
        if (dao.saveOrUpdate(entity)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB);
        }
    }

    @Override
    public DataResult<T> getById(Serializable id) {
        return ResultUtil.successDataResult(dao.getById(id));
    }

    @Override
    public DataListResult<T> listByIds(Collection<? extends Serializable> idList) {
        return ResultUtil.successDataListResult(dao.listByIds(idList));
    }

    @Override
    public DataListResult<T> listByMap(Map<String, Object> columnMap) {
        return ResultUtil.successDataListResult(dao.listByMap(columnMap));
    }

    @Override
    public DataResult<T> getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return ResultUtil.successDataResult(dao.getOne(queryWrapper, throwEx));
    }

    @Override
    public DataResult<T> getOne(T entity, boolean throwEx) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return ResultUtil.successDataResult(dao.getOne(queryWrapper, throwEx));
    }

    @Override
    public DataResult<Map<String, Object>> getMap(Wrapper<T> queryWrapper) {
        return ResultUtil.successDataResult(dao.getMap(queryWrapper));
    }

    @Override
    public DataResult<Map<String, Object>> getMap(T entity) {
        return ResultUtil.successDataResult(dao.getMap(entity));
    }

    @Override
    public <V> DataResult<V> getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return ResultUtil.successDataResult(dao.getObj(queryWrapper, mapper));
    }

    @Override
    public <V> DataResult<V> getObj(T entity, Function<? super Object, V> mapper) {
        return ResultUtil.successDataResult(dao.getObj(entity, mapper));
    }

    @Override
    public DataResult<Integer> count(Wrapper<T> queryWrapper) {
        return ResultUtil.successDataResult(dao.count(queryWrapper));
    }

    @Override
    public DataResult<Integer> count(T entity) {
        return ResultUtil.successDataResult(dao.count(entity));
    }

    @Override
    public DataListResult<T> list(Wrapper<T> queryWrapper) {
        return ResultUtil.successDataListResult(dao.list(queryWrapper));
    }

    @Override
    public DataListResult<T> list(T entity) {
        return ResultUtil.successDataListResult(dao.list(entity));
    }

    @Override
    public PageResult<T> page(Pagination<T> pagination, Wrapper<T> queryWrapper) {
        IPage<T> resultPage = dao.page(PageUtil.transToMybatisPlusPage(pagination), queryWrapper);
        return ResultUtil.successPageResult(PageUtil.transToPagination(resultPage));
    }

    @Override
    public DataListResult<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return ResultUtil.successDataListResult(dao.listMaps(queryWrapper));
    }

    @Override
    public <V> DataListResult<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return ResultUtil.successDataListResult(dao.listObjs(queryWrapper, mapper));
    }

    @Override
    public PageResult<Map<String, Object>> pageMaps(Pagination<T> pagination, Wrapper<T> queryWrapper) {
        IPage<Map<String, Object>> resultPage = dao.pageMaps(PageUtil.transToMybatisPlusPage(pagination), queryWrapper);
        return ResultUtil.successPageResult(PageUtil.transToPagination(resultPage));
    }
}
