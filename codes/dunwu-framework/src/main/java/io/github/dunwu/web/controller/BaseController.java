package io.github.dunwu.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.core.SystemCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 基础 Controller
 * @author Zhang Peng
 * @since 2019-04-15
 */
public abstract class BaseController<T> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IService<T> service;

    public BaseController() {}

    public BaseController(IService<T> service) {
        this.service = service;
    }

    public Result<Integer> count(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return ResultUtil.success(service.count(wrapper));
    }

    public Result<T> list(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        return ResultUtil.success(service.list(wrapper));
    }

    public Result<T> listWithPage(T entity, Page page) {
        IPage<T> queryPage =
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getCurrent(), page.getSize());
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        IPage<T> resultPage = service.page(queryPage, wrapper);
        return ResultUtil.success(resultPage.getRecords(),
            new Page(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal()));
    }

    public Result save(T entity) {
        if (service.save(entity)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(SystemCode.ERROR_DB);
        }
    }

    public Result saveBatch(List<T> entityList) {
        if (service.saveBatch(entityList)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(SystemCode.ERROR_DB);
        }
    }

    public Result remove(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(entity);
        if (service.remove(wrapper)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(SystemCode.ERROR_DB);
        }
    }

    public Result update(T origin, T target) {
        QueryWrapper<T> wrapper = new QueryWrapper<>(origin);
        if (service.update(target, wrapper)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.fail(SystemCode.ERROR_DB);
        }
    }
}
