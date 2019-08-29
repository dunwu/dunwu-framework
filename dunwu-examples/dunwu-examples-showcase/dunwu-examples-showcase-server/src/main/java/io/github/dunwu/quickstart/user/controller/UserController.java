package io.github.dunwu.quickstart.user.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.user.dto.LoginInfoDTO;
import io.github.dunwu.quickstart.user.dto.UserInfoDTO;
import io.github.dunwu.quickstart.user.entity.UserInfo;
import io.github.dunwu.quickstart.user.service.UserInfoService;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.util.mapper.BeanMapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    public static final String TOKEN_KEY = "token";

    private final UserManager userManager;
    private final UserInfoService userInfoService;

    @PostMapping("register")
    public DataResult<Map<String, String>> register(@RequestBody LoginInfoDTO registerUserDTO) {
        return userManager.register(registerUserDTO);
    }

    @PostMapping("login")
    public DataResult<UserInfoDTO> login(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody Map<String, String> map) {
        return userManager.login(request, response, map);
    }

    @PostMapping("logout")
    public BaseResult logout(HttpServletRequest request) {
        request.getSession()
               .removeAttribute(TOKEN_KEY);
        return ResultUtil.successBaseResult();
    }

    @GetMapping("{id:.+}")
    public DataResult<UserInfoDTO> getUser(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            ResultUtil.failDataResult();
        }

        DataResult<UserInfo> dataResult = userInfoService.getById(id);
        if (ResultUtil.isNotValidResult(dataResult)) {
            return ResultUtil.failDataResult(AppCode.ERROR_DB);
        }
        UserInfoDTO userInfoDTO = BeanMapper.map(dataResult.getData(), UserInfoDTO.class);
        userInfoDTO.setIntroduction("I am a super administrator");
        userInfoDTO.setRoles("admin");
        return ResultUtil.successDataResult(userInfoDTO);
    }

    @GetMapping("currentUser")
    public DataResult<UserInfoDTO> currentUser() {
        return getUser("1");
    }

    @GetMapping("getInfo")
    public DataResult<UserInfoDTO> getInfo(@PathParam("token") String token) {
        if (StringUtils.isBlank(token)) {
            ResultUtil.failDataResult();
        }

        DataResult<UserInfo> dataResult = userInfoService.getById(1);
        if (ResultUtil.isNotValidResult(dataResult)) {
            return ResultUtil.failDataResult(AppCode.ERROR_DB);
        }
        UserInfoDTO userInfoDTO = BeanMapper.map(dataResult.getData(), UserInfoDTO.class);
        userInfoDTO.setIntroduction("I am a super administrator");
        userInfoDTO.setRoles("admin");
        return ResultUtil.successDataResult(userInfoDTO);
    }

    @PostMapping("save")
    @ApiOperation(value = "插入一条 UserInfo 记录，插入成功返回 ID（选择字段，策略插入）")
    public DataResult<? extends Serializable> save(UserInfo entity) {
        return userInfoService.save(entity);
    }

    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 UserInfo 记录（选择字段，策略插入）")
    public BaseResult saveBatch(Collection<UserInfo> entityList) {
        return userInfoService.saveBatch(entityList);
    }

    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 UserInfo 记录")
    public BaseResult removeById(Serializable id) {
        return userInfoService.removeById(id);
    }

    @PostMapping("removeByMap")
    @ApiOperation(value = "根据 columnMap 条件，删除 UserInfo 记录")
    public BaseResult removeByMap(Map<String, Object> columnMap) {
        return userInfoService.removeByMap(columnMap);
    }

    @PostMapping("remove")
    @ApiOperation(value = "根据 entity 条件，删除 UserInfo 记录")
    public BaseResult remove(UserInfo entity) {
        return userInfoService.remove(entity);
    }

    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 UserInfo 记录")
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return userInfoService.removeByIds(idList);
    }

    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 UserInfo 记录")
    public BaseResult updateById(UserInfo entity) {
        return userInfoService.updateById(entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新 UserInfo 记录")
    public BaseResult update(@RequestBody UserInfo target, @RequestParam UserInfo origin) {
        return userInfoService.update(target, origin);
    }

    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 UserInfo 记录")
    public BaseResult updateBatchById(Collection<UserInfo> entityList) {
        return userInfoService.updateBatchById(entityList);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
    public BaseResult saveOrUpdate(UserInfo entity) {
        return userInfoService.saveOrUpdate(entity);
    }

    @PostMapping("saveOrUpdateBatch")
    @ApiOperation(value = "批量添加或更新 UserInfo 记录")
    public BaseResult saveOrUpdateBatch(Collection<UserInfo> entityList) {
        return userInfoService.saveOrUpdateBatch(entityList);
    }

    @GetMapping("getById")
    @ApiOperation(value = "根据 ID 查询 UserInfo 记录")
    public DataResult<UserInfo> getById(Serializable id) {
        return userInfoService.getById(id);
    }

    @GetMapping("listByIds")
    @ApiOperation(value = "根据 ID 批量查询 UserInfo 记录")
    public DataListResult<UserInfo> listByIds(Collection<? extends Serializable> idList) {
        return userInfoService.listByIds(idList);
    }

    @GetMapping("listByMap")
    @ApiOperation(value = "根据 columnMap 批量查询 UserInfo 记录")
    public DataListResult<UserInfo> listByMap(Map<String, Object> columnMap) {
        return userInfoService.listByMap(columnMap);
    }

    @GetMapping("getOne")
    @ApiOperation(value = "根据 entity 查询一条 UserInfo 记录")
    public DataResult<UserInfo> getOne(UserInfo entity) {
        return userInfoService.getOne(entity);
    }

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 UserInfo 总记录数")
    public DataResult<Integer> count(UserInfo entity) {
        return userInfoService.count(entity);
    }

    @GetMapping("countAll")
    @ApiOperation(value = "查询 UserInfo 总记录数")
    public DataResult<Integer> countAll() {
        return userInfoService.count();
    }

    @GetMapping("list")
    @ApiOperation(value = "根据 entity 条件，查询匹配条件的 UserInfo 记录")
    public DataListResult<UserInfo> list(UserInfo entity) {
        return userInfoService.list(entity);
    }

    @GetMapping("listAll")
    @ApiOperation(value = "查询所有 UserInfo 记录")
    public DataListResult<UserInfo> listAll() {
        return userInfoService.list();
    }

    @GetMapping("page")
    @ApiOperation(value = "根据 entity 条件，翻页查询 UserInfo 记录")
    public PageResult<UserInfo> page(Pagination<UserInfo> pagination, UserInfo entity) {
        return userInfoService.page(pagination, entity);
    }

    @GetMapping("pageAll")
    @ApiOperation(value = "翻页查询所有 UserInfo 记录")
    public PageResult<UserInfo> pageAll(Pagination<UserInfo> pagination) {
        return userInfoService.page(pagination);
    }
}
