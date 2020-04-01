package io.github.dunwu.admin.system.controller;

import io.github.dunwu.admin.system.entity.Role;
import io.github.dunwu.admin.system.service.RoleService;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.data.PageQuery;
import io.github.dunwu.data.QueryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.annotation.security.RolesAllowed;

/**
 * 角色信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@RolesAllowed("admin")
@RestController
@RequestMapping("/role")
@Api(tags = "role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 Role 总记录数")
    public DataResult<Integer> count(Role entity) {
        return service.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 Role 总记录数")
    public DataResult<Integer> countAll() {
        return service.count();
    }

    @GetMapping("getById")
    @ApiOperation(value = "根据 ID 查询 Role 记录")
    public DataResult<Role> getById(String id) {
        return service.getById(id);
    }

    @GetMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 Role 记录")
    public DataResult<Role> getOne(Role entity) {
        return service.getOne(entity);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 Role 记录")
    public DataListResult<Role> list(Role entity) {
        return service.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 Role 记录")
    public DataListResult<Role> listAll() {
        return service.list();
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 Role 记录")
    public DataListResult<Role> listByIds(@RequestParam Collection<String> idList) {
        return service.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 Role 记录")
    public DataListResult<Role> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 和 page 条件，翻页查询 Role 记录")
    public PageResult<Role> page(PageQuery page, Role entity) {
        QueryRequest<Role> request = QueryRequest.build(entity, page, Collections.emptyList());
        return service.page(request);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 Role 记录")
    public PageResult<Role> pageAll(PageQuery page) {
        QueryRequest<Role> request = QueryRequest.build(null, page, Collections.emptyList());
        return service.page(request);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 Role 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<Integer> insert(@RequestBody Role entity) {
        return service.insert(entity);
    }

    @PostMapping("insertBatch")
    @ApiOperation(value = "批量添加 Role 记录（选择字段，策略插入）")
    public BaseResult insertBatch(@RequestBody Collection<Role> entityList) {
        return service.insertBatch(entityList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 Role 记录")
    public BaseResult updateById(@RequestBody Role entity) {
        return service.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 Role 记录")
    public BaseResult update(@RequestBody Role target, @RequestParam Role origin) {
        return service.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 Role 记录")
    public BaseResult updateBatchById(@RequestBody Collection<Role> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("save")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult save(@RequestBody Role entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加或更新 Role 记录")
    public BaseResult saveBatch(@RequestBody Collection<Role> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("deleteById")
    @ApiOperation(value = "根据 ID 删除一条 Role 记录")
    public BaseResult deleteById(@RequestBody String id) {
        return service.deleteById(id);
    }

    @PostMapping("delete")
    @ApiOperation(value = "根据 entity 条件，删除 Role 记录")
    public BaseResult delete(@RequestBody Role entity) {
        return service.delete(entity);
    }

    @PostMapping("deleteBatchIds")
    @ApiOperation(value = "根据 ID 批量删除 Role 记录")
    public BaseResult deleteBatchIds(@RequestBody Collection<String> idList) {
        return service.deleteBatchIds(idList);
    }

    @PostMapping("deleteByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 Role 记录")
    public BaseResult deleteByMap(@RequestBody Map<String, Object> columnMap) {
        return service.deleteByMap(columnMap);
    }

}
