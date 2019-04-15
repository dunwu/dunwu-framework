package io.github.dunwu.quickstart.controller;


import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.entity.User;
import io.github.dunwu.quickstart.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import io.github.dunwu.web.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-15
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("list")
    public DataListResult<User> list() {
        return ResultUtil.newSuccessDataListResult(userService.list());
    }
}
