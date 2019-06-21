package ${package.Controller};

<#if superControllerClassPackage??>
import io.github.dunwu.core.*;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${superControllerClassPackage};
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>
<#if superControllerClassPackage??>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
</#if>
<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>

import java.io.Serializable;
import java.util.Collection;
</#if>

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
<#if swagger2>@Api(tags = "${entity}", description = "${table.comment!} CRUD Controller")</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>
    private final ${table.serviceName} service;

    @Autowired
    public ${table.controllerName}(${entity}Service service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    <#if swagger2>
    @ApiOperation(value = "返回符合查询条件的 ${entity} 记录数，如果 entity 为 null，返回所有记录数")
    </#if>
    public DataResult<Integer> count(${entity} entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    <#if swagger2>
    @ApiOperation(value = "返回符合查询条件的 ${entity} 记录，如果 entity 为 null，返回所有记录")
    </#if>
    public DataListResult<${entity}> list(${entity} entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("page")
    <#if swagger2>
    @ApiOperation(value = "分页查询符合条件的 ${entity} 记录，如果 entity 为 null，返回所有记录的分页查询结果")
    </#if>
    public PageResult<${entity}> page(${entity} entity, Page page) {
        return super.page(entity, page);
    }

    @Override
    @PostMapping("save")
    <#if swagger2>
    @ApiOperation(value = "插入一条 ${entity} 记录（选择字段）")
    </#if>
    public BaseResult save(${entity} entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加 ${entity} 记录（选择字段）")
    </#if>
    public BaseResult saveBatch(Collection<${entity}> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    <#if swagger2>
    @ApiOperation(value = "删除符合条件的 ${entity} 记录")
    </#if>
    public BaseResult remove(${entity} entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("removeById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 删除一条 ${entity} 记录")
    </#if>
    public BaseResult removeById(Long id) {
        return super.removeById(id);
    }

    @Override
    @PostMapping("removeByIds")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量删除 ${entity} 记录")
    </#if>
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @PostMapping("update")
    <#if swagger2>
    @ApiOperation(value = "根据 origin 条件，更新一条 ${entity} 记录 target")
    </#if>
    public BaseResult update(
        @RequestBody @ApiParam(name = "target", value = "${entity} 对象（json 格式）", required = true) ${entity} target, ${entity} origin) {
        return super.update(target, origin);
    }

    @Override
    @PostMapping("updateById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 选择修改一条 ${entity} 记录")
    </#if>
    public BaseResult updateById(${entity} entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("updateBatchById")
    <#if swagger2>
    @ApiOperation(value = "根据 ID 批量修改 ${entity} 记录")
    </#if>
    public BaseResult updateBatchById(Collection<${entity}> entityList) {
        return super.updateBatchById(entityList);
    }
}
</#if>
