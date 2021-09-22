package ${package.Controller};

import io.github.dunwu.tool.data.core.Result;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${package.Service}.${table.serviceName};
<#if enableSwagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Controller 类
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.apiBaseUrl}</#if>")
<#if enableSwagger>
@Api(tags = "${table.comment!} Controller 类")
</#if>
@RequiredArgsConstructor
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} service;
    <#if !enableSwagger>

    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }
    </#if>

    <#if enableSwagger>
    @ApiOperation("添加一条 ${entity} 记录")
    <#else>
    /** 添加一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("add")
    public Result<Boolean> add(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        return Result.ok(service.insert(entity));
    }

    <#if enableSwagger>
    @ApiOperation("批量添加 ${entity} 记录")
    <#else>
    /** 批量添加 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("add/batch")
    public Result<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<${entity}> list) {
        return Result.ok(service.insertBatch(list));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 更新一条 ${entity} 记录")
    <#else>
    /** 根据 ID 更新一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("edit")
    public Result<Boolean> edit(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        return Result.ok(service.updateById(entity));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 批量更新 ${entity} 记录")
    <#else>
    /** 根据 ID 批量更新 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("edit/batch")
    public Result<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<${entity}> list) {
        return Result.ok(service.updateBatchById(list));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 删除一条 ${entity} 记录")
    <#else>
    /** 根据 ID 删除一条 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("del/{id}")
    public Result<Boolean> deleteById(@PathVariable Serializable id) {
        return Result.ok(service.deleteById(id));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 列表批量删除 ${entity} 记录")
    <#else>
    /** 根据 ID 列表批量删除 {@link ${entity}} 记录 */
    </#if>
    @PostMapping("del/batch")
    public Result<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return Result.ok(service.deleteBatchByIds(ids));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询 ${table.dtoName} 列表")
    @GetMapping("list")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询 {@link ${table.dtoName}} 列表 */
    </#if>
    public Result<List<${table.dtoName}>> list(${table.queryName} query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 和 Pageable 分页查询 ${table.dtoName} 列表")
    <#else>
    /** 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表 */
    </#if>
    @GetMapping("page")
    public Result<Page<${table.dtoName}>> page(${table.queryName} query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 查询 ${table.dtoName}")
    <#else>
    /** 根据 id 查询 {@link ${table.dtoName}} */
    </#if>
    @GetMapping("{id}")
    public Result<${table.dtoName}> getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询匹配条件的记录数")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询匹配条件的记录数 */
    </#if>
    @GetMapping("count")
    public Result<Integer> count(${table.queryName} query) {
        return Result.ok(service.countByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 列表查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 id 列表查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 和 Pageable 分页查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    @GetMapping("export/page")
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(query, pageable, response);
    }

}
