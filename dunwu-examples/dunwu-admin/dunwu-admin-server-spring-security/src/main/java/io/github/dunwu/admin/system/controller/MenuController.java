package io.github.dunwu.admin.system.controller;

import io.github.dunwu.admin.system.dto.MenuDTO;
import io.github.dunwu.admin.system.entity.Menu;
import io.github.dunwu.admin.system.service.MenuManager;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.data.PageQuery;
import io.github.dunwu.data.QueryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "menu")
public class MenuController {

    private final MenuManager manager;

    public MenuController(MenuManager manager) {
        this.manager = manager;
    }

    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "根据 entity 条件，查询 Menu 总记录数")
    public DataResult<Integer> count(Menu entity) {
        return manager.count(entity);
    }

    @GetMapping("countAll")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "查询 Menu 总记录数")
    public DataResult<Integer> countAll() {
        return manager.count();
    }

    @GetMapping("getById")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "根据 ID 查询 Menu 记录")
    public DataResult<Menu> getById(String id) {
        return manager.getById(id);
    }

    @GetMapping("getOne")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "根据 entity 查询一条 Menu 记录")
    public DataResult<Menu> getOne(Menu entity) {
        return manager.getOne(entity);
    }

    @GetMapping("list")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 Menu 记录")
    public DataListResult<Menu> list(Menu entity) {
        return manager.list(entity);
    }

    @GetMapping("listAll")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "查询所有 Menu 记录")
    public DataListResult<Menu> listAll() {
        return manager.list();
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 Menu 记录")
    public DataListResult<Menu> listByIds(@RequestParam Collection<String> idList) {
        return manager.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 Menu 记录")
    public DataListResult<Menu> listByMap(Map<String, Object> columnMap) {
        return manager.listByMap(columnMap);
    }

    @GetMapping("page")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "根据 entity 和 page 条件，翻页查询 Menu 记录")
    public PageResult<Menu> page(PageQuery page, Menu entity) {
        QueryRequest<Menu> request = QueryRequest.build(entity, page, Collections.emptyList());
        return manager.page(request);
    }

    @GetMapping("pageAll")
    @PreAuthorize("hasPermission('menu:view')")
    @ApiOperation(value = "翻页查询所有 Menu 记录")
    public PageResult<Menu> pageAll(PageQuery page) {
        QueryRequest<Menu> request = QueryRequest.build(null, page, Collections.emptyList());
        return manager.page(request);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 Menu 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<Integer> insert(@RequestBody Menu entity) {
        return manager.insert(entity);
    }

    @PostMapping("insertBatch")
    @ApiOperation(value = "批量添加 Menu 记录（选择字段，策略插入）")
    public BaseResult insertBatch(@RequestBody Collection<Menu> entityList) {
        return manager.insertBatch(entityList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 Menu 记录")
    public BaseResult updateById(@RequestBody Menu entity) {
        return manager.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 Menu 记录")
    public BaseResult update(@RequestBody Menu target, @RequestParam Menu origin) {
        return manager.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 Menu 记录")
    public BaseResult updateBatchById(@RequestBody Collection<Menu> entityList) {
        return manager.updateBatchById(entityList);
    }

    @PostMapping("save")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult save(@RequestBody Menu entity) {
        return manager.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加或更新 Menu 记录")
    public BaseResult saveBatch(@RequestBody Collection<Menu> entityList) {
        return manager.saveBatch(entityList);
    }

    @PostMapping("deleteById")
    @ApiOperation(value = "根据 ID 删除一条 Menu 记录")
    public BaseResult deleteById(@RequestBody String id) {
        return manager.deleteById(id);
    }

    @PostMapping("delete")
    @ApiOperation(value = "根据 entity 条件，删除 Menu 记录")
    public BaseResult delete(@RequestBody Menu entity) {
        return manager.delete(entity);
    }

    @PostMapping("deleteBatchIds")
    @ApiOperation(value = "根据 ID 批量删除 Menu 记录")
    public BaseResult deleteBatchIds(@RequestBody Collection<String> idList) {
        return manager.deleteBatchIds(idList);
    }

    @PostMapping("deleteByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 Menu 记录")
    public BaseResult deleteByMap(@RequestBody Map<String, Object> columnMap) {
        return manager.deleteByMap(columnMap);
    }

    // ------------------------------------------------------------------------------
    // 订制接口
    // ------------------------------------------------------------------------------

    @GetMapping("treeList")
    @ApiOperation(value = "添加或更新记录时进行检查")
    public DataListResult<MenuDTO> treeList() {
        return manager.menuTree();
    }

    @PostMapping("check")
    @ApiOperation(value = "添加或更新记录时进行检查")
    public BaseResult checkBeforeInsert(@RequestBody Menu entity) {
        return manager.checkBeforeInsert(entity);
    }

    @PostMapping("relatedDeleteById")
    @ApiOperation(value = "根据 ID 关联删除 Menu 记录")
    public DataResult<Integer> relatedDeleteById(@RequestBody Menu entity) {
        return manager.relatedDeleteById(entity.getId(), entity.getParentId());
    }

}
