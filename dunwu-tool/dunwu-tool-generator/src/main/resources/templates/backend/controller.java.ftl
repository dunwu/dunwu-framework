package ${package.Controller};

import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.PageResult;
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
import org.springframework.data.domain.Pageable;
<#if table.enablePermission>
import org.springframework.security.access.prepost.PreAuthorize;
</#if>
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
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
    <#if !entityLombokModel>

    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }
    </#if>

    <#if enableSwagger>
    @ApiOperation("添加一条 ${entity} 记录")
    <#else>
    /** 添加一条 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:add')")
    </#if>
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        return DataResult.ok(service.insert(entity));
    }

    <#if enableSwagger>
    @ApiOperation("批量添加 ${entity} 记录")
    <#else>
    /** 批量添加 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:add')")
    </#if>
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<${entity}> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 更新一条 ${entity} 记录")
    <#else>
    /** 根据 id 更新一条 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:edit')")
    </#if>
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        return DataResult.ok(service.updateById(entity));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 批量更新 ${entity} 记录")
    <#else>
    /** 根据 id 批量更新 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:edit')")
    </#if>
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<${entity}> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 删除一条 ${entity} 记录")
    <#else>
    /** 根据 id 删除一条 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:del')")
    </#if>
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 列表批量删除 ${entity} 记录")
    <#else>
    /** 根据 id 列表批量删除 {@link ${entity}} 记录 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:del')")
    </#if>
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询 ${table.dtoName} 列表")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询 {@link ${table.dtoName}} 列表 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:view')")
    </#if>
    @GetMapping("/list")
    public DataListResult<${table.dtoName}> list(${table.queryName} query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 Pageable 和 ${table.queryName} 分页查询 ${table.dtoName} 列表")
    <#else>
    /** 根据 {@link Pageable} 和 {@link ${table.queryName}} 分页查询 {@link ${table.dtoName}} 列表 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:view')")
    </#if>
    @GetMapping("/page")
    public PageResult<${table.dtoName}> page(Pageable pageable, ${table.queryName} query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 查询 ${table.dtoName}")
    <#else>
    /** 根据 id 查询 {@link ${table.dtoName}} */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:view')")
    </#if>
    @GetMapping("/{id}")
    public DataResult<${table.dtoName}> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    <#if enableSwagger>
    @ApiOperation("根据 ${table.queryName} 查询匹配条件的记录数")
    <#else>
    /** 根据 {@link ${table.queryName}} 查询匹配条件的记录数 */
    </#if>
    @GetMapping("/count")
    public DataResult<Integer> count(${table.queryName} query) {
        return DataResult.ok(service.countByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("导入 excel 表单")
    <#else>
    /** 导入 excel 表单 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:edit')")
    </#if>
    @PostMapping("/import/list")
    public DataResult<Boolean> importList(@RequestBody MultipartFile file) {
        service.importList(file);
        return DataResult.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 id 列表查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 id 列表查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:view')")
    </#if>
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    <#if enableSwagger>
    @ApiOperation("根据 Pageable 和 ${table.queryName} 分页查询 ${table.dtoName} 列表，并导出 excel 表单")
    <#else>
    /** 根据 {@link Pageable} 和 {@link ${table.queryName}} 分页查询 {@link ${table.dtoName}} 列表，并导出 excel 表单 */
    </#if>
    <#if table.enablePermission>
    @PreAuthorize("@exp.check('<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:view')")
    </#if>
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, ${table.queryName} query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
