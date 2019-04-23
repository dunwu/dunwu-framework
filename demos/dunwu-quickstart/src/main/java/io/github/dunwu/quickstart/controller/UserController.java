package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.quickstart.entity.User;
import io.github.dunwu.quickstart.service.UserService;
import io.github.dunwu.web.controller.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-23
 */
@RestController
@RequestMapping("/user")
@Api(tags = "User", description = "用户表 CRUD Controller")
public class UserController extends CrudController<User> {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    @ApiOperation(value = "查询 User 记录数")
    public Result<Integer> count(User entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    @ApiOperation(value = "查询 User 记录列表")
    public Result<User> list(User entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    @ApiOperation(value = "查询 User 记录列表分页")
    public Result<User> listWithPage(User entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    @ApiOperation(value = "添加一条 User 记录")
    public Result save(User entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 User 记录")
    public Result saveBatch(List<User> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    @ApiOperation(value = "删除一条 User 记录")
    public Result remove(User entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("update")
    @ApiOperation(value = "更新一条 User 记录")
    public Result update(User origin, User target) {
        return super.update(origin, target);
    }
}
