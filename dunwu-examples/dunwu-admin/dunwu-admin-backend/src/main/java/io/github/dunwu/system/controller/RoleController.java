package io.github.dunwu.system.controller;

import io.github.dunwu.system.entity.Role;
import io.github.dunwu.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 角色信息 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/role")
@Api(tags = "role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;


    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 Role 总记录数")
    public ResponseEntity<Object> count(Role entity) {
        return new ResponseEntity<>(service.count(entity), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    @ApiOperation(value = "根据 ID 查询 Role 记录")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 Role 列表")
    public ResponseEntity<Object> list(Role entity) {
        return new ResponseEntity<>(service.list(entity), HttpStatus.OK);
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有 Role 记录")
    public ResponseEntity<Object> listAll() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 和 pageable 条件，分页查询 Role 记录")
    public ResponseEntity<Object> page(Pageable pageable, Role entity) {
        return new ResponseEntity<>(service.page(pageable, entity), HttpStatus.OK);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 Role 记录，插入成功返回 ID（选择字段，策略插入）")
    public ResponseEntity<Object> insert(@Validated @RequestBody Role entity) {
        return new ResponseEntity<>(service.insert(entity), HttpStatus.CREATED);
    }

    @PostMapping("insert/batch")
    @ApiOperation(value = "批量添加 Role 记录（选择字段，策略插入）")
    public ResponseEntity<Object> insertBatch(@RequestBody Collection<Role> entities) {
        return new ResponseEntity<>(service.insertBatch(entities), HttpStatus.CREATED);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 ID 选择修改一条 Role 记录")
    public ResponseEntity<Object> update(@Validated @RequestBody Role entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @PostMapping("update/batch")
    @ApiOperation(value = "根据 ID 批量修改 Role 记录")
    public ResponseEntity<Object> updateBatch(@RequestBody Collection<Role> entities) {
        return new ResponseEntity<>(service.updateBatchById(entities), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "根据 ID 删除一条 Role 记录")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/batch")
    @ApiOperation(value = "根据 ID 批量删除 Role 记录")
    public ResponseEntity<Object> deleteBatch(@RequestBody Collection<String> ids) {
        return new ResponseEntity<>(service.deleteBatchIds(ids), HttpStatus.ACCEPTED);
    }

}
