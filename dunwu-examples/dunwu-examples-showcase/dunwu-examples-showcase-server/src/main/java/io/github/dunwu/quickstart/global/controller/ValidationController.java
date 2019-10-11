package io.github.dunwu.quickstart.global.controller;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-11
 */
@RestController
@RequestMapping("valid")
@Api(tags = "valid", description = "ValidationController")
public class ValidationController {

	@PostMapping("test")
	@ApiOperation(value = "测试校验器")
	public BaseResult method(@Validated @RequestBody UserDTO userInfoDTO) {
		return ResultUtil.successBaseResult();
	}

}
