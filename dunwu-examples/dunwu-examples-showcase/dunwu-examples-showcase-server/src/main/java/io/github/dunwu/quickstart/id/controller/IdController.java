package io.github.dunwu.quickstart.id.controller;

import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.util.code.IdUtil;
import io.github.dunwu.util.code.support.SnowFlakeId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@RestController
@RequestMapping("id")
@Api(tags = "id")
public class IdController {

	public static final int MAX = 100;

	@GetMapping("generateUuid")
	@ApiOperation(value = "生成随机 UUID")
	public DataListResult<String> generateUuid(@RequestParam("num") Integer num,
		@RequestParam("withSeparator") Boolean withSeparator) {
		int max = num < MAX ? num : MAX;
		Set<String> ids = new HashSet<>();
		for (int i = 0; i < max; i++) {
			if (withSeparator) {
				ids.add(IdUtil.randomUuid());
			} else {
				ids.add(IdUtil.randomUuid2());
			}
		}
		return ResultUtil.successDataListResult(ids);
	}

	@GetMapping("generateSnowFlakeId")
	@ApiOperation(value = "生成 SnowFlake ID")
	public DataListResult<String> generateSnowFlakeId(@RequestParam("num") Integer num,
		@RequestParam("dataCenterId") long dataCenterId,
		@RequestParam("machineId") long machineId) {
		SnowFlakeId snowFlakeId = IdUtil.newSnowFlakeId(dataCenterId, machineId);
		int max = num < MAX ? num : MAX;
		Set<String> ids = new HashSet<>();
		for (int i = 0; i < max; i++) {
			ids.add(String.valueOf(snowFlakeId.generate()));
		}
		return ResultUtil.successDataListResult(ids);
	}

}
