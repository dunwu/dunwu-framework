package io.github.dunwu.quickstart.template.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.template.entity.TemplateConfig;
import io.github.dunwu.quickstart.template.service.TemplateConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 模板配置表 前端控制器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-29
 */
@RestController
@RequestMapping("template")
@Api(tags = "template", description = "TemplateController")
public class TemplateController {

    private final TemplateConfigService service;

    public TemplateController(TemplateConfigService service) {
        this.service = service;
    }

    @PostMapping("save")
    @ApiOperation(value = "插入一条 TemplateConfig 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<? extends Serializable> save(@RequestBody TemplateConfig entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 TemplateConfig 记录（选择字段，策略插入）")
    public BaseResult saveBatch(Collection<TemplateConfig> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 TemplateConfig 记录")
    public BaseResult removeById(String id) {
        return service.removeById(id);
    }

    @PostMapping("removeByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 TemplateConfig 记录")
    public BaseResult removeByMap(Map<String, Object> columnMap) {
        return service.removeByMap(columnMap);
    }

    @PostMapping("remove")
    @ApiOperation(value = "根据 entity 条件，删除 TemplateConfig 记录")
    public BaseResult remove(@RequestBody TemplateConfig entity) {
        return service.remove(entity);
    }

    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 TemplateConfig 记录")
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return service.removeByIds(idList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 TemplateConfig 记录")
    public BaseResult updateById(@RequestBody TemplateConfig entity) {
        return service.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 TemplateConfig 记录")
    public BaseResult update(@RequestBody TemplateConfig target, @RequestParam TemplateConfig origin) {
        return service.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 TemplateConfig 记录")
    public BaseResult updateBatchById(Collection<TemplateConfig> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult saveOrUpdate(@RequestBody TemplateConfig entity) {
        return service.saveOrUpdate(entity);
    }

    @PostMapping("saveOrUpdateBatch")
    @ApiOperation(value = "批量添加或更新 TemplateConfig 记录")
    public BaseResult saveOrUpdateBatch(Collection<TemplateConfig> entityList) {
        return service.saveOrUpdateBatch(entityList);
    }

    @GetMapping("getById")
    @ApiOperation(value = "根据 ID 查询 TemplateConfig 记录")
    public DataResult<TemplateConfig> getById(String id) {
        return service.getById(id);
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 TemplateConfig 记录")
    public DataListResult<TemplateConfig> listByIds(Collection<? extends Serializable> idList) {
        return service.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 TemplateConfig 记录")
    public DataListResult<TemplateConfig> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @GetMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 TemplateConfig 记录")
    public DataResult<TemplateConfig> getOne(TemplateConfig entity) {
        return service.getOne(entity);
    }

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 TemplateConfig 总记录数")
    public DataResult<Integer> count(TemplateConfig entity) {
        return service.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 TemplateConfig 总记录数")
    public DataResult<Integer> countAll() {
        return service.count();
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 TemplateConfig 记录")
    public DataListResult<TemplateConfig> list(TemplateConfig entity) {
        return service.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 TemplateConfig 记录")
    public DataListResult<TemplateConfig> listAll() {
        return service.list();
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 条件，翻页查询 UserInfo 记录")
    public PageResult<TemplateConfig> page(Pagination<TemplateConfig> pagination, TemplateConfig entity) {
        return service.page(pagination, entity);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 UserInfo 记录")
    public PageResult<TemplateConfig> pageAll(Pagination<TemplateConfig> pagination) {
        return service.page(pagination);
    }
}
