package ${package.Controller};

import io.github.dunwu.common.*;
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

import java.util.Collection;
import java.util.Map;

/**
 * ${table.comment!}
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
@Api(tags = "${table.entityPath}")
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

    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

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

    @GetMapping("getById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 查询 ${entity} 记录")
    </#if>
    public DataResult<${entity}> getById(String id) {
        return service.getById(id);
    }

    @GetMapping("getOne")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 查询一条 ${entity} 记录")
    </#if>
    public DataResult<${entity}> getOne(${entity} entity) {
        return service.getOne(entity);
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

    @GetMapping("listByIds")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量查询 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> listByIds(@RequestParam Collection<String> idList) {
        return service.listByIds(idList);
    }

    @GetMapping("listByMap")
    <#if swagger2>
    @ApiOperation(value = "根据 columnMap 批量查询 ${entity} 记录")
    </#if>
    public DataListResult<${entity}> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @GetMapping("page")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，翻页查询 ${entity} 记录")
    </#if>
    public PageResult<${entity}> page(Pagination<${entity}> pagination, ${entity} entity) {
        return service.page(pagination, entity);
    }

    @GetMapping("pageAll")
    <#if swagger2>
    @ApiOperation(value = "翻页查询所有 ${entity} 记录")
    </#if>
    public PageResult<${entity}> pageAll(Pagination<${entity}> pagination) {
        return service.page(pagination);
    }

    @PostMapping("insert")
    <#if swagger2>
    @ApiOperation(value = "插入一条 ${entity} 记录，插入成功返回 ID（选择字段，策略插入）")
    </#if>
    public DataResult<Integer> insert(@RequestBody ${entity} entity) {
        return service.insert(entity);
    }

    @PostMapping("insertBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加 ${entity} 记录（选择字段，策略插入）")
    </#if>
    public BaseResult insertBatch(@RequestBody Collection<${entity}> entityList) {
        return service.insertBatch(entityList);
    }

    @PostMapping("updateById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 选择修改一条 ${entity} 记录")
    </#if>
    public BaseResult updateById(@RequestBody ${entity} entity) {
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
    public BaseResult updateBatchById(@RequestBody Collection<${entity}> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("save")
    <#if swagger2>
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    </#if>
    public BaseResult save(@RequestBody ${entity} entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加或更新 ${entity} 记录")
    </#if>
    public BaseResult saveBatch(@RequestBody Collection<${entity}> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("deleteById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 删除一条 ${entity} 记录")
    </#if>
    public BaseResult deleteById(@RequestBody String id) {
        return service.deleteById(id);
    }

    @PostMapping("delete")
    <#if swagger2>
    @ApiOperation(value = "根据 entity 条件，删除 ${entity} 记录")
    </#if>
    public BaseResult delete(@RequestBody ${entity} entity) {
        return service.delete(entity);
    }

    @PostMapping("deleteBatchIds")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量删除 ${entity} 记录")
    </#if>
    public BaseResult deleteBatchIds(@RequestBody Collection<String> idList) {
        return service.deleteBatchIds(idList);
    }

    @PostMapping("deleteByMap")
    <#if swagger2>
    @ApiOperation(value = "根据 columnMap 条件，删除 ${entity} 记录")
    </#if>
    public BaseResult deleteByMap(@RequestBody Map<String, Object> columnMap) {
        return service.deleteByMap(columnMap);
    }

}
