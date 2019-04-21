package ${package.Controller};

<#if superControllerClassPackage??>
import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${superControllerClassPackage};
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
    public Result<Integer> count(${entity} entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    public Result<${entity}> list(${entity} entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    public Result<${entity}> listWithPage(${entity} entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    public Result save(${entity} entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    public Result saveBatch(List<${entity}> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    public Result remove(${entity} entity) {
        return super.remove(entity);
    }
}
</#if>
