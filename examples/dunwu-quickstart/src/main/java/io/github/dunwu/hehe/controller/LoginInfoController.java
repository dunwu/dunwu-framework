package io.github.dunwu.hehe.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.hehe.entity.LoginInfo;
import io.github.dunwu.hehe.service.LoginInfoService;
import io.github.dunwu.web.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * 登录信息表 前端控制器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-22
 */
@RestController
@RequestMapping("/login-info")
public class LoginInfoController extends CrudController<LoginInfo> {
    private final LoginInfoService service;

    @Autowired
    public LoginInfoController(LoginInfoService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    public DataResult<Integer> count(LoginInfo entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    public DataListResult<LoginInfo> list(LoginInfo entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("page")
    public PageResult<LoginInfo> page(LoginInfo entity, Page page) {
        return super.page(entity, page);
    }

    @Override
    @PostMapping("save")
    public BaseResult save(LoginInfo entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    public BaseResult saveBatch(Collection<LoginInfo> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    public BaseResult remove(LoginInfo entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("removeById")
    public BaseResult removeById(Long id) {
        return super.removeById(id);
    }

    @Override
    @PostMapping("removeByIds")
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @PostMapping("update")
    public BaseResult update(@RequestBody LoginInfo target, LoginInfo origin) {
        return super.update(target, origin);
    }

    @Override
    @PostMapping("updateById")
    public BaseResult updateById(LoginInfo entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("updateBatchById")
    public BaseResult updateBatchById(Collection<LoginInfo> entityList) {
        return super.updateBatchById(entityList);
    }
}
