package io.github.dunwu.app.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.app.system.entity.User;
import io.github.dunwu.app.system.query.UserQuery;
import io.github.dunwu.app.system.service.UserDao;
import io.github.dunwu.data.mybatis.util.MybatisPlusUtil;
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
 * 用户信息 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/user")
@Api(tags = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserDao service;

    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 User 总记录数")
    public ResponseEntity<Object> count(User entity) {
        return new ResponseEntity<>(service.count(entity), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    @ApiOperation(value = "根据 ID 查询 User 记录")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 User 列表")
    public ResponseEntity<Object> list(User entity) {
        return new ResponseEntity<>(service.list(entity), HttpStatus.OK);
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有 User 记录")
    public ResponseEntity<Object> listAll() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 和 pageable 条件，分页查询 User 记录")
    public ResponseEntity<Object> page(Pageable pageable, UserQuery query) {
        QueryWrapper<User> wrapper = MybatisPlusUtil.buildQueryWrapper(query);
        try {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(service.springPage(pageable, wrapper), HttpStatus.OK);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 User 记录，插入成功返回 ID（选择字段，策略插入）")
    public ResponseEntity<Object> insert(@Validated @RequestBody User entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PostMapping("insert/batch")
    @ApiOperation(value = "批量添加 User 记录（选择字段，策略插入）")
    public ResponseEntity<Object> insertBatch(@RequestBody Collection<User> entities) {
        return new ResponseEntity<>(service.saveBatch(entities), HttpStatus.CREATED);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 ID 选择修改一条 User 记录")
    public ResponseEntity<Object> update(@Validated @RequestBody User entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @PostMapping("update/batch")
    @ApiOperation(value = "根据 ID 批量修改 User 记录")
    public ResponseEntity<Object> updateBatch(@RequestBody Collection<User> entities) {
        return new ResponseEntity<>(service.updateBatchById(entities), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "根据 ID 删除一条 User 记录")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("delete/batch")
    @ApiOperation(value = "根据 ID 批量删除 User 记录")
    public ResponseEntity<Object> deleteBatch(@RequestBody Collection<String> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

}
