package ${package.Controller};

import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import ${package.Query}.${table.queryName};
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
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
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if swagger2>
@Api(tags = "${table.controllerName}")
</#if>
@RequiredArgsConstructor
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} service;

    <#if !swagger2>
    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }
    </#if>

    @PostMapping
    <#if swagger2>
    @ApiOperation("创建一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> create(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping
    <#if swagger2>
    @ApiOperation("更新一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> update(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    <#if swagger2>
    @ApiOperation("删除一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    <#if swagger2>
    @ApiOperation("根据 ID 集合批量删除 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @ApiOperation("查询 ${table.dtoName} 记录")
    public ResponseEntity<Object> view(${table.queryName} query, Pageable pageable) {
        return page(query, pageable);
    }

    @GetMapping("page")
    <#if swagger2>
    @ApiOperation("根据 query 和 pageable 条件，分页查询 ${table.dtoName} 记录")
    </#if>
    public ResponseEntity<Object> page(${table.queryName} query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    <#if swagger2>
    @ApiOperation("根据 ID 查询 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @GetMapping("count")
    <#if swagger2>
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    </#if>
    public ResponseEntity<Object> count(${table.queryName} query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("list")
    <#if swagger2>
    @ApiOperation("根据 query 条件，查询匹配条件的 ${table.dtoName} 列表")
    </#if>
    public ResponseEntity<Object> list(${table.queryName} query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @GetMapping("export")
    @ApiOperation("根据 ID 集合批量导出 ${table.dtoName} 列表数据")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        service.exportByIds(ids, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 ${table.dtoName} 列表数据")
    public void exportPageData(${table.queryName} query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

}
