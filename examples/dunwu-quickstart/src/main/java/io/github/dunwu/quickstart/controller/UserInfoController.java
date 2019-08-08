package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.entity.UserInfo;
import io.github.dunwu.quickstart.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 用户信息表 前端控制器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/user-info")
@Api(tags = "UserInfo", description = "用户信息表 CRUD Controller")
public class UserInfoController {

    private final UserInfoService service;

    public UserInfoController(UserInfoService service) {
        this.service = service;
    }

    @PostMapping("save")
    @ApiOperation(value = "插入一条 UserInfo 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<? extends Serializable> save(UserInfo entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 UserInfo 记录（选择字段，策略插入）")
    public BaseResult saveBatch(Collection<UserInfo> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 UserInfo 记录")
    public BaseResult removeById(Serializable id) {
        return service.removeById(id);
    }

    @PostMapping("removeByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 UserInfo 记录")
    public BaseResult removeByMap(Map<String, Object> columnMap) {
        return service.removeByMap(columnMap);
    }

    @PostMapping("remove")
    @ApiOperation(value = "根据 entity 条件，删除 UserInfo 记录")
    public BaseResult remove(UserInfo entity) {
        return service.remove(entity);
    }

    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 UserInfo 记录")
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return service.removeByIds(idList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 UserInfo 记录")
    public BaseResult updateById(UserInfo entity) {
        return service.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 UserInfo 记录")
    public BaseResult update(@RequestBody UserInfo target, @RequestParam UserInfo origin) {
        return service.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 UserInfo 记录")
    public BaseResult updateBatchById(Collection<UserInfo> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult saveOrUpdate(UserInfo entity) {
        return service.saveOrUpdate(entity);
    }

    @PostMapping("saveOrUpdateBatch")
    @ApiOperation(value = "批量添加或更新 UserInfo 记录")
    public BaseResult saveOrUpdateBatch(Collection<UserInfo> entityList) {
        return service.saveOrUpdateBatch(entityList);
    }

    @PostMapping("getById")
    @ApiOperation(value = "根据 ID 查询 UserInfo 记录")
    public DataResult<UserInfo> getById(Serializable id) {
        return service.getById(id);
    }

    @PostMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 UserInfo 记录")
    public DataListResult<UserInfo> listByIds(Collection<? extends Serializable> idList) {
        return service.listByIds(idList);
    }

    @PostMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 UserInfo 记录")
    public DataListResult<UserInfo> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @PostMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 UserInfo 记录")
    public DataResult<UserInfo> getOne(UserInfo entity) {
        return service.getOne(entity);
    }

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 UserInfo 总记录数")
    public DataResult<Integer> count(UserInfo entity) {
        return service.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 UserInfo 总记录数")
    public DataResult<Integer> countAll() {
        return service.count();
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 UserInfo 记录")
    public DataListResult<UserInfo> list(UserInfo entity) {
        return service.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 UserInfo 记录")
    public DataListResult<UserInfo> listAll() {
        return service.list();
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 条件，翻页查询 UserInfo 记录")
    public PageResult<UserInfo> page(Pagination<UserInfo> pagination, UserInfo entity) {
        return service.page(pagination, entity);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 UserInfo 记录")
    public PageResult<UserInfo> pageAll(Pagination<UserInfo> pagination) {
        return service.page(pagination);
    }
}
