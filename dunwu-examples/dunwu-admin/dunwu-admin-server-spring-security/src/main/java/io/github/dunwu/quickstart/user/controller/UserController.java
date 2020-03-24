package io.github.dunwu.quickstart.user.controller;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.data.DataOrderItem;
import io.github.dunwu.data.PageQuery;
import io.github.dunwu.data.QueryRequest;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.quickstart.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 用户表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-17
 */
@RestController
@RequestMapping("user")
@Api(tags = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserManager manager;
    private final UserService service;

    // ------------------------------------------------------------------------------
    // 代码生成器生成的代码
    // ------------------------------------------------------------------------------

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 User 总记录数")
    public DataResult<Integer> count(User entity) {
        return service.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 User 总记录数")
    public DataResult<Integer> countAll() {
        return service.count();
    }

    @GetMapping("getById")
    @ApiOperation(value = "根据 ID 查询 User 记录")
    public DataResult<User> getById(String id) {
        return service.getById(id);
    }

    @GetMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 User 记录")
    public DataResult<User> getOne(User entity) {
        return service.getOne(entity);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 User 记录")
    public DataListResult<User> list(User entity) {
        return service.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 User 记录")
    public DataListResult<User> listAll() {
        return service.list();
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 User 记录")
    public DataListResult<User> listByIds(@RequestParam Collection<String> idList) {
        return service.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 User 记录")
    public DataListResult<User> listByMap(Map<String, Object> columnMap) {
        return service.listByMap(columnMap);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 条件，翻页查询 User 记录")
    public PageResult<User> page(PageQuery page, User user) {
        QueryRequest<User> request = QueryRequest.build(user, page, Collections.emptyList());
        request.setOrders(Collections.singletonList(DataOrderItem.asc("username")));
        return service.page(request);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 User 记录")
    public PageResult<User> pageAll(PageQuery page, User user) {
        QueryRequest<User> request = QueryRequest.build(user, page, Collections.emptyList());
        request.setOrders(Collections.singletonList(DataOrderItem.asc("username")));
        return service.page(request);
    }

    @PostMapping("insert")
    @ApiOperation(value = "插入一条 User 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<Integer> insert(@RequestBody User entity) {
        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        return service.insert(entity);
    }

    @PostMapping("insertBatch")
    @ApiOperation(value = "批量添加 User 记录（选择字段，策略插入）")
    public BaseResult insertBatch(@RequestBody Collection<User> entityList) {
        return service.insertBatch(entityList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 User 记录")
    public BaseResult updateById(@RequestBody User entity) {
        return service.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 User 记录")
    public BaseResult update(@RequestBody User target, @RequestParam User origin) {
        return service.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 User 记录")
    public BaseResult updateBatchById(@RequestBody Collection<User> entityList) {
        return service.updateBatchById(entityList);
    }

    @PostMapping("save")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult save(@RequestBody User entity) {
        return service.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加或更新 User 记录")
    public BaseResult saveBatch(@RequestBody Collection<User> entityList) {
        return service.saveBatch(entityList);
    }

    @PostMapping("deleteById")
    @ApiOperation(value = "根据 ID 删除一条 User 记录")
    public BaseResult deleteById(@RequestBody String id) {
        return service.deleteById(id);
    }

    @PostMapping("delete")
    @ApiOperation(value = "根据 entity 条件，删除 User 记录")
    public BaseResult delete(@RequestBody User entity) {
        return service.delete(entity);
    }

    @PostMapping("deleteBatchIds")
    @ApiOperation(value = "根据 ID 批量删除 User 记录")
    public BaseResult deleteBatchIds(@RequestBody Collection<String> idList) {
        return service.deleteBatchIds(idList);
    }

    @PostMapping("deleteByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 User 记录")
    public BaseResult deleteByMap(@RequestBody Map<String, Object> columnMap) {
        return service.deleteByMap(columnMap);
    }

    // ------------------------------------------------------------------------------
    // 自行实现的代码
    // ------------------------------------------------------------------------------

    @GetMapping("exists")
    @ApiOperation(value = "新注册用户信息是否有效")
    public DataResult<Boolean> isUserExists(UserDTO userDTO) {
        return manager.isUserExists(userDTO);
    }

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public BaseResult register(@RequestBody UserDTO userDTO) {
        return manager.register(userDTO);
    }

    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public DataResult<UserDTO> getInfo(@AuthenticationPrincipal Principal principal) {
        UserDTO userDTO = manager.getByUsername(principal.getName());
        return DataResult.success(userDTO);
    }

}
