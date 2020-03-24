package io.github.dunwu.quickstart.tool.controller;

import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.tool.lang.Snowflake;
import io.github.dunwu.tool.util.ArrayUtil;
import io.github.dunwu.tool.util.IdUtil;
import io.github.dunwu.util.net.IpUtils;
import io.github.dunwu.web.util.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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
        Snowflake snowFlakeId = IdUtil.getSnowflake(dataCenterId, machineId);
        int max = num < MAX ? num : MAX;
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < max; i++) {
            ids.add(snowFlakeId.nextIdStr());
        }
        return DataListResult.success(ids);
    }

    @GetMapping("/id/generateUuid")
    @ApiOperation(value = "生成随机 UUID")
    public DataListResult<String> generateUuid(@RequestParam("num") Integer num,
        @RequestParam("withSeparator") Boolean withSeparator) {
        int max = num < MAX ? num : MAX;
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < max; i++) {
            if (withSeparator) {
                ids.add(IdUtil.randomUUID());
            } else {
                ids.add(IdUtil.simpleUUID());
            }
        }
        return DataListResult.success(ids);
    }

    @GetMapping("/ip/getRegion")
    @ApiOperation(value = "获取 IP 所在位置")
    public DataListResult<String> getRegion(@RequestParam("ip") String ip) {
        String[] regions = IpUtils.getFullRegionName(ip);
        if (ArrayUtil.isEmpty(regions)) {
            String message = String.format("未找到 %s", ip);
            return DataListResult.failDataList(ResultStatus.HTTP_NOT_FOUND.getCode(), message);
        }
        return DataListResult.success(Arrays.asList(regions));
    }

    @GetMapping("/ip/getLocalRegion")
    public DataResult<Map<String, Object>> getRegion(HttpServletRequest request) {
        String address = ServletUtil.getRealRemoteAddr(request);
        String[] regions = IpUtils.getFullRegionName(address);
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("regions", regions);
        return DataResult.success(map);
    }

}
