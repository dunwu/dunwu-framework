package io.github.dunwu.hehe.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.hehe.entity.UserInfo;
import io.github.dunwu.hehe.service.UserInfoService;
import io.github.dunwu.web.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用户信息表 前端控制器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-22
 */
@RestController
@RequestMapping("/user-info")
public class UserInfoController extends CrudController<UserInfo> {
    private final UserInfoService service;

    @Autowired
    public UserInfoController(UserInfoService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    public DataResult<Integer> count(UserInfo entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    public DataListResult<UserInfo> list(UserInfo entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("page")
    public PageResult<UserInfo> page(UserInfo entity, Page page) {
        return super.page(entity, page);
    }

    @Override
    @PostMapping("save")
    public BaseResult save(UserInfo entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    public BaseResult saveBatch(Collection<UserInfo> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    public BaseResult remove(UserInfo entity) {
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
    public BaseResult update(@RequestBody UserInfo target, UserInfo origin) {
        return super.update(target, origin);
    }

    @Override
    @PostMapping("updateById")
    public BaseResult updateById(UserInfo entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("updateBatchById")
    public BaseResult updateBatchById(Collection<UserInfo> entityList) {
        return super.updateBatchById(entityList);
    }
}
