package io.github.dunwu.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.dunwu.core.Page;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.core.DefaultAppCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * CRUD 抽象 Controller
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
public abstract class CrudController<T> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IService<T> service;

    public CrudController() {}

    public CrudController(IService<T> service) {
        this.service = service;
    }

    /**
     * 返回符合查询条件的记录数，如果 entity 为 null，返回所有记录数
     * @param entity 查询条件
     * @return Result<Integer>
     */
    public BaseResult<Integer> count(T entity) {
        if (entity == null) {
            return ResultUtil.successBaseResult(service.count());
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return ResultUtil.successBaseResult(service.count(wrapper));
    }

    /**
     * 返回符合查询条件的记录，如果 entity 为 null，返回所有记录
     * @param entity 查询条件
     * @return Result<T>
     */
    public BaseResult<T> list(T entity) {
        if (entity == null) {
            return ResultUtil.successBaseResult(service.list());
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return ResultUtil.successBaseResult(service.list(wrapper));
    }

    /**
     * 分页查询符合条件的记录，如果 entity 为 null，返回所有记录的分页查询结果
     * @param entity 查询条件
     * @param page 分页查询条件
     * @return Result<T>
     */
    public BaseResult<T> page(T entity, Page page) {
        IPage<T> queryPage =
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getCurrent(), page.getSize());
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        IPage<T> resultPage = service.page(queryPage, wrapper);
        return ResultUtil.successBaseResult(resultPage.getRecords(),
            new Page(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal()));
    }

    /**
     * 插入一条记录（选择字段）
     * @param entity 待添加的记录，填充的字段作为查询条件，未填充的字段忽略
     * @return Result
     */
    public BaseResult save(T entity) {
        if (service.save(entity)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 批量添加记录（选择字段）
     * @param entityList 待添加的记录列表，填充的字段作为查询条件，未填充的字段忽略
     * @return Result
     */
    public BaseResult saveBatch(Collection<T> entityList) {
        if (service.saveBatch(entityList)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 删除符合条件的记录
     * @param entity 待删除的记录，填充的字段作为查询条件，未填充的字段忽略
     * @return Result
     */
    public BaseResult remove(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        if (service.remove(wrapper)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 根据 ID 删除一条记录
     * @return Result
     */
    public BaseResult removeById(Long id) {
        if (service.removeById(id)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 根据ID 批量删除
     * @return Result
     */
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        if (service.removeByIds(idList)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 根据 origin 条件，更新一条记录 target
     * @param target 要更新的记录内容
     * @param origin 查询条件
     * @return Result
     */
    public BaseResult update(T target, T origin) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>(origin);
        if (service.update(target, updateWrapper)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 根据 ID 选择修改
     * @param entity 实体对象
     */
    public BaseResult updateById(T entity) {
        if (service.updateById(entity)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }

    /**
     * 根据 ID 批量更新
     * @param entityList 实体对象集合
     */
    public BaseResult updateBatchById(Collection<T> entityList) {
        if (service.updateBatchById(entityList)) {
            return ResultUtil.successBaseResult();
        } else {
            return ResultUtil.fail(DefaultAppCode.ERROR_DB);
        }
    }
}
