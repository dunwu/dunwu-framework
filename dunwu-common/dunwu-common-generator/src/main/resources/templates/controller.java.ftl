package ${package.Controller};

import ${package.Dao}.${table.daoName};
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${package.Service}.${table.serviceName};
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>

import java.io.IOException;
import java.util.List;
import java.util.Set;
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

    private final ${table.daoName} dao;

    <#if !swagger2>
    public ${table.controllerName}(${table.daoName} dao) {
        this.dao = dao;
    }
    </#if>

    @PostMapping
    <#if swagger2>
    @ApiOperation("创建一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> create(@Validated @RequestBody ${entity} entity) {
        return new ResponseEntity<>(dao.save(entity), HttpStatus.CREATED);
    }

    @PutMapping
    <#if swagger2>
    @ApiOperation("更新一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> update(@Validated(UpdateValidate.class) @RequestBody ${entity} entity) {
        return new ResponseEntity<>(dao.updateById(entity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    <#if swagger2>
    @ApiOperation("删除一条 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return new ResponseEntity<>(dao.removeById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    <#if swagger2>
    @ApiOperation("根据 ID 集合批量删除 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> deleteByIds(@RequestBody Set<String> ids) {
        return new ResponseEntity<>(dao.removeByIds(ids), HttpStatus.ACCEPTED);
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
        return new ResponseEntity<>(dao.pojoPageByQuery(query, pageable, ${table.dtoName}.class), HttpStatus.OK);
    }

    @GetMapping("{id}")
    <#if swagger2>
    @ApiOperation("根据 ID 查询 ${entity} 记录")
    </#if>
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(dao.pojoById(id, ${table.dtoName}.class), HttpStatus.OK);
    }

    @GetMapping("count")
    <#if swagger2>
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    </#if>
    public ResponseEntity<Object> count(${table.queryName} query) {
        return new ResponseEntity<>(dao.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("list")
    <#if swagger2>
    @ApiOperation("根据 query 条件，查询匹配条件的 ${table.dtoName} 列表")
    </#if>
    public ResponseEntity<Object> list(${table.queryName} query) {
        return new ResponseEntity<>(dao.pojoListByQuery(query, ${table.dtoName}.class), HttpStatus.OK);
    }

    @GetMapping("export")
    @ApiOperation("根据 ID 集合批量导出 ${table.dtoName} 列表数据")
    public void exportPageData(@RequestBody Set<String> ids, HttpServletResponse response) throws IOException {
        List<${table.dtoName}> list = dao.dtoListByIds(ids, ${table.dtoName}.class);
        service.exportDtoList(list, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 ${table.dtoName} 列表数据")
    public void exportByIds(${table.queryName} query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<${table.dtoName}> page = dao.dtoPageByQuery(query, pageable, ${table.dtoName}.class);
        service.exportDtoList(page.getContent(), response);
    }

}
