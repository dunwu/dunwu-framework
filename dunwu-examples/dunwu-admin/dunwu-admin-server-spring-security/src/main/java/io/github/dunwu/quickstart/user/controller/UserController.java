package io.github.dunwu.quickstart.user.controller;

import io.github.dunwu.common.*;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.quickstart.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
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
public class UserController {

    private final UserManager userManager;

    private final UserService userService;

    public UserController(UserManager userManager, UserService userService) {
        this.userManager = userManager;
        this.userService = userService;
    }

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 User 总记录数")
    public DataResult<Integer> count(User entity) {
        return userService.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 User 总记录数")
    public DataResult<Integer> countAll() {
        return userService.count();
    }

    @GetMapping("getById")
    @ApiOperation(value = "根据 ID 查询 User 记录")
    public DataResult<User> getById(String id) {
        return userService.getById(id);
    }

    @GetMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 User 记录")
    public DataResult<User> getOne(User entity) {
        return userService.getOne(entity);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 User 记录")
    public DataListResult<User> list(User entity) {
        return userService.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 User 记录")
    public DataListResult<User> listAll() {
        return userService.list();
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 User 记录")
    public DataListResult<User> listByIds(@RequestParam Collection<String> idList) {
        return userService.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 User 记录")
    public DataListResult<User> listByMap(Map<String, Object> columnMap) {
        return userService.listByMap(columnMap);
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 条件，翻页查询 User 记录")
    public PageResult<User> page(Pagination<User> pagination, User entity) {
        return userService.page(pagination, entity);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 User 记录")
    public PageResult<User> pageAll(Pagination<User> pagination) {
        return userService.page(pagination);
    }

    @GetMapping("isUserExists")
    @ApiOperation(value = "新注册用户信息是否有效")
    public DataResult<Boolean> isUserExists(UserDTO userDTO) {
        return userManager.isUserExists(userDTO);
    }

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public BaseResult register(@RequestBody UserDTO userDTO) {
        return userManager.register(userDTO);
    }

    @PostMapping("remove")
    @ApiOperation(value = "根据 entity 条件，删除 User 记录")
    public BaseResult remove(@RequestBody User entity) {
        return userService.remove(entity);
    }

    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 User 记录")
    public BaseResult removeById(@RequestBody String id) {
        return userService.removeById(id);
    }

    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 User 记录")
    public BaseResult removeByIds(@RequestBody Collection<String> idList) {
        return userService.removeByIds(idList);
    }

    @PostMapping("removeByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 User 记录")
    public BaseResult removeByMap(@RequestBody Map<String, Object> columnMap) {
        return userService.removeByMap(columnMap);
    }

    @PostMapping("save")
    @ApiOperation(value = "插入一条 User 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<Integer> save(@RequestBody User entity) {
        return userService.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 User 记录（选择字段，策略插入）")
    public BaseResult saveBatch(@RequestBody Collection<User> entityList) {
        return userService.saveBatch(entityList);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult saveOrUpdate(@RequestBody User entity) {
        return userService.saveOrUpdate(entity);
    }

    @PostMapping("saveOrUpdateBatch")
    @ApiOperation(value = "批量添加或更新 User 记录")
    public BaseResult saveOrUpdateBatch(@RequestBody Collection<User> entityList) {
        return userService.saveOrUpdateBatch(entityList);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 User 记录")
    public BaseResult update(@RequestBody User target, @RequestParam User origin) {
        return userService.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 User 记录")
    public BaseResult updateBatchById(@RequestBody Collection<User> entityList) {
        return userService.updateBatchById(entityList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 User 记录")
    public BaseResult updateById(@RequestBody User entity) {
        return userService.updateById(entity);
    }

    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户信息")
    public DataResult<UserDTO> getInfo(@AuthenticationPrincipal Principal principal) {
        UserDTO userDTO = userManager.getByUsername(principal.getName());
        return ResultUtils.successDataResult(userDTO);
    }

}
