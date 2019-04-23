package ${package.Controller};

<#if superControllerClassPackage??>
import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${superControllerClassPackage};
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if superControllerClassPackage??>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>

import java.util.List;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
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
    @ApiOperation(value = "查询 ${entity} 记录数")
    </#if>
    public Result<Integer> count(${entity} entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    <#if swagger2>
    @ApiOperation(value = "查询 ${entity} 记录列表")
    </#if>
    public Result<${entity}> list(${entity} entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    <#if swagger2>
    @ApiOperation(value = "查询 ${entity} 记录列表分页")
    </#if>
    public Result<${entity}> listWithPage(${entity} entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    <#if swagger2>
    @ApiOperation(value = "添加一条 ${entity} 记录")
    </#if>
    public Result save(${entity} entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    <#if swagger2>
    @ApiOperation(value = "批量添加 ${entity} 记录")
    </#if>
    public Result saveBatch(List<${entity}> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    <#if swagger2>
    @ApiOperation(value = "删除一条 ${entity} 记录")
    </#if>
    public Result remove(${entity} entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("update")
    <#if swagger2>
    @ApiOperation(value = "更新一条 ${entity} 记录")
    </#if>
    public Result update(${entity} origin, ${entity} target) {
        return super.update(origin, target);
    }
}
</#if>
