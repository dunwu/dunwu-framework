package io.github.dunwu.quickstart.tool.controller;

import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.ResultUtils;
import io.github.dunwu.util.code.IdUtil;
import io.github.dunwu.util.code.support.SnowFlakeId;
import io.github.dunwu.util.collection.ArrayExtUtils;
import io.github.dunwu.util.social.IpUtils;
import io.github.dunwu.web.util.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@RestController
@Api(tags = "tool")
public class ToolController {

    private static final int MAX = 100;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/id/generateSnowFlakeId")
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
        return ResultUtils.successDataListResult(ids);
    }

    @GetMapping("/id/generateUuid")
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
        return ResultUtils.successDataListResult(ids);
    }

    @GetMapping("/ip/getRegion")
    @ApiOperation(value = "获取 IP 所在位置")
    public DataListResult<String> getRegion(@RequestParam("ip") String ip) {
        String[] regions = IpUtils.getFullRegionName(ip);
        if (ArrayExtUtils.isEmpty(regions)) {
            String message = String.format(AppCode.ERROR_NOT_FOUND.getTemplate(), ip);
            return ResultUtils.failDataListResult(AppCode.ERROR_NOT_FOUND.getCode(), message);
        }
        return ResultUtils.successDataListResult(Arrays.asList(regions));
    }

    @GetMapping("/ip/getLocalRegion")
    public DataResult<Map<String, Object>> getRegion(HttpServletRequest request) {
        String address = ServletUtil.getRealRemoteAddr(request);
        String[] regions = IpUtils.getFullRegionName(address);
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("regions", regions);
        return ResultUtils.successDataResult(map);
    }

}
