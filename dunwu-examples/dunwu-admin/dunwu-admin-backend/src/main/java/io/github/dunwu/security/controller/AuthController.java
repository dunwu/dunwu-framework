package io.github.dunwu.security.controller;

import io.github.dunwu.security.service.AuthService;
import io.github.dunwu.system.dto.MenuDTO;
import io.github.dunwu.system.dto.UserDTO;
import io.github.dunwu.system.entity.Menu;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 用户表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-17
 */
@RestController
@RequestMapping("auth")
@Api(tags = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("exists")
    @ApiOperation(value = "新注册用户信息是否有效")
    public DataResult<Boolean> isUserExists(UserDTO userDTO) {
        return authService.isUserExists(userDTO);
    }

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public BaseResult register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public DataResult<UserDTO> getInfo(@AuthenticationPrincipal Principal principal) {
        UserDTO userDTO = authService.loadUserByUniqueKey(principal.getName());
        userDTO.setPassword(null);
        return DataResult.success(userDTO);
    }

    @GetMapping("treeList")
    @ApiOperation(value = "添加或更新记录时进行检查")
    public DataListResult<MenuDTO> treeList() {
        return authService.menuTree();
    }

    @PostMapping("check")
    @ApiOperation(value = "添加或更新记录时进行检查")
    public BaseResult checkBeforeInsert(@RequestBody Menu entity) {
        return authService.checkBeforeInsert(entity);
    }

    @PostMapping("relatedDeleteById")
    @ApiOperation(value = "根据 ID 关联删除 Menu 记录")
    public DataResult<Integer> relatedDeleteById(@RequestBody Menu entity) {
        return authService.relatedDeleteById(entity.getId(), entity.getParentId());
    }

}
