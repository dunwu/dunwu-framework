package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.quickstart.entity.Role;
import io.github.dunwu.quickstart.service.RoleService;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-23
 */
@RestController
@RequestMapping("/role")
@Api(tags = "Role", description = "角色表 CRUD Controller")
public class RoleController extends CrudController<Role> {
    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    @ApiOperation(value = "查询 Role 记录数")
    public Result<Integer> count(Role entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    @ApiOperation(value = "查询 Role 记录列表")
    public Result<Role> list(Role entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    @ApiOperation(value = "查询 Role 记录列表分页")
    public Result<Role> listWithPage(Role entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    @ApiOperation(value = "添加一条 Role 记录")
    public Result save(Role entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 Role 记录")
    public Result saveBatch(List<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    @ApiOperation(value = "删除一条 Role 记录")
    public Result remove(Role entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("update")
    @ApiOperation(value = "更新一条 Role 记录")
    public Result update(Role origin, Role target) {
        return super.update(origin, target);
    }
}
