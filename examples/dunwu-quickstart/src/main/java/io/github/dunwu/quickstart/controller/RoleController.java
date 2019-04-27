package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.quickstart.entity.Role;
import io.github.dunwu.quickstart.service.RoleService;
import io.github.dunwu.web.controller.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
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
    @ApiOperation(value = "返回符合查询条件的 Role 记录数，如果 entity 为 null，返回所有记录数")
    public Result<Integer> count(Role entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    @ApiOperation(value = "返回符合查询条件的 Role 记录，如果 entity 为 null，返回所有记录")
    public Result<Role> list(Role entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("page")
    @ApiOperation(value = "分页查询符合条件的 Role 记录，如果 entity 为 null，返回所有记录的分页查询结果")
    public Result<Role> page(Role entity, Page page) {
        return super.page(entity, page);
    }

    @Override
    @PostMapping("save")
    @ApiOperation(value = "插入一条 Role 记录（选择字段）")
    public Result save(Role entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 Role 记录（选择字段）")
    public Result saveBatch(Collection<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    @ApiOperation(value = "删除符合条件的 Role 记录")
    public Result remove(Role entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 Role 记录")
    public Result removeById(Long id) {
        return super.removeById(id);
    }

    @Override
    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 Role 记录")
    public Result removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新一条 Role 记录 target")
    public Result update(
        @RequestBody @ApiParam(name = "target", value = "Role 对象（json 格式）", required = true) Role target, Role origin) {
        return super.update(target, origin);
    }

    @Override
    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 Role 记录")
    public Result updateById(Role entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 Role 记录")
    public Result updateBatchById(Collection<Role> entityList) {
        return super.updateBatchById(entityList);
    }
}
