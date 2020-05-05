package io.github.dunwu.system.controller;

import io.github.dunwu.system.entity.Menu;
import io.github.dunwu.system.service.MenuService;
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
 * 菜单信息 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;


    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 Menu 总记录数")
    public ResponseEntity<Object> count(Menu entity) {
        return new ResponseEntity<>(service.count(entity), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    @ApiOperation(value = "根据 ID 查询 Menu 记录")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 Menu 列表")
    public ResponseEntity<Object> list(Menu entity) {
        return new ResponseEntity<>(service.list(entity), HttpStatus.OK);
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有 Menu 记录")
    public ResponseEntity<Object> listAll() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 和 pageable 条件，分页查询 Menu 记录")
    public ResponseEntity<Object> page(Pageable pageable, Menu entity) {
        return new ResponseEntity<>(service.page(pageable, entity), HttpStatus.OK);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 Menu 记录，插入成功返回 ID（选择字段，策略插入）")
    public ResponseEntity<Object> insert(@Validated @RequestBody Menu entity) {
        return new ResponseEntity<>(service.insert(entity), HttpStatus.CREATED);
    }

    @PostMapping("insert/batch")
    @ApiOperation(value = "批量添加 Menu 记录（选择字段，策略插入）")
    public ResponseEntity<Object> insertBatch(@RequestBody Collection<Menu> entities) {
        return new ResponseEntity<>(service.insertBatch(entities), HttpStatus.CREATED);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 ID 选择修改一条 Menu 记录")
    public ResponseEntity<Object> update(@Validated @RequestBody Menu entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @PostMapping("update/batch")
    @ApiOperation(value = "根据 ID 批量修改 Menu 记录")
    public ResponseEntity<Object> updateBatch(@RequestBody Collection<Menu> entities) {
        return new ResponseEntity<>(service.updateBatchById(entities), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "根据 ID 删除一条 Menu 记录")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/batch")
    @ApiOperation(value = "根据 ID 批量删除 Menu 记录")
    public ResponseEntity<Object> deleteBatch(@RequestBody Collection<String> ids) {
        return new ResponseEntity<>(service.deleteBatchIds(ids), HttpStatus.ACCEPTED);
    }

}
