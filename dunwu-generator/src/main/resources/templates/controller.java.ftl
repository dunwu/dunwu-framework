package ${package.Controller};

import io.github.dunwu.core.*;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.*;
<#else>
import org.springframework.stereotype.Controller;
</#if>

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * ${table.comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if swagger2>
@Api(tags = "${entity}", description = "${table.comment!} CRUD Controller")
</#if>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} service;

    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }

    @PostMapping("save")
    <#if swagger2>
    @ApiOperation(value = "插入一条 ${entity} 记录，插入成功返回 ID（选择字段，策略插入）")
    </#if>
    public DataResult<? extends Serializable> save(${entity} entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加 ${entity} 记录（选择字段，策略插入）")
    </#if>
    public BaseResult saveBatch(Collection<${entity}> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("removeById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 删除一条 ${entity} 记录")
    </#if>
    public BaseResult removeById(Serializable id) {
        return service.removeById(id);
    }

    @PostMapping("removeByMap")
    <#if swagger2>
    @ApiOperation(value = "根据 columnMap 条件，删除 ${entity} 记录")
    </#if>
    public BaseResult removeByMap(Map<String, Object> columnMap) {
        return service.removeByMap(columnMap);
    }

    @PostMapping("remove")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，删除 ${entity} 记录")
    </#if>
    public BaseResult remove(${entity} entity) {
        return service.remove(entity);
    }

    @PostMapping("removeByIds")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量删除 ${entity} 记录")
    </#if>
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return service.removeByIds(idList);
    }

    @PostMapping("updateById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 选择修改一条 ${entity} 记录")
    </#if>
    public BaseResult updateById(${entity} entity) {
        return service.updateById(entity);
    }

    @PostMapping("update")
    <#if swagger2>
    @ApiOperation(value = "根据 origin 条件，更新 ${entity} 记录")
    </#if>
    public BaseResult update(@RequestBody ${entity} target, @RequestParam ${entity} origin) {
        return service.update(target, origin);
    }

    @PostMapping("updateBatchById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量修改 ${entity} 记录")
    </#if>
    public BaseResult updateBatchById(Collection<${entity}> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("saveOrUpdate")
    <#if swagger2>
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    </#if>
    public BaseResult saveOrUpdate(${entity} entity) {
        return service.saveOrUpdate(entity);
    }

    @PostMapping("saveOrUpdateBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加或更新 ${entity} 记录")
    </#if>
    public BaseResult saveOrUpdateBatch(Collection<${entity}> entityList) {
        return service.saveOrUpdateBatch(entityList);
    }

    @GetMapping("getById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 查询 ${entity} 记录")
    </#if>
    public DataResult<${entity}> getById(Serializable id) {
        return service.getById(id);
    }

    @GetMapping("listByIds")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量查询 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> listByIds(Collection<? extends Serializable> idList) {
        return service.listByIds(idList);
    }

    @GetMapping("listByMap")
    <#if swagger2>
    @ApiOperation(value = "根据 columnMap 批量查询 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @GetMapping("getOne")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 查询一条 ${entity} 记录")
    </#if>
    public DataResult<${entity}> getOne(${entity} entity) {
        return service.getOne(entity);
    }

    @GetMapping("count")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，查询 ${entity} 总记录数")
    </#if>
    public DataResult<Integer> count(${entity} entity) {
        return service.count(entity);
    }

    @GetMapping("countAll")
    <#if swagger2>
    @ApiOperation(value = "查询 ${entity} 总记录数")
    </#if>
    public DataResult<Integer> countAll() {
        return service.count();
    }

    @GetMapping("list")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> list(${entity} entity) {
        return service.list(entity);
    }

    @GetMapping("listAll")
    <#if swagger2>
    @ApiOperation(value = "查询所有 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> listAll() {
        return service.list();
    }

    @GetMapping("page")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，翻页查询 UserInfo 记录")
    </#if>
    public PageResult<${entity}> page(Pagination<${entity}> pagination, ${entity} entity) {
        return service.page(pagination, entity);
    }

    @GetMapping("pageAll")
    <#if swagger2>
    @ApiOperation(value = "翻页查询所有 UserInfo 记录")
    </#if>
    public PageResult<${entity}> pageAll(Pagination<${entity}> pagination) {
        return service.page(pagination);
    }
}
