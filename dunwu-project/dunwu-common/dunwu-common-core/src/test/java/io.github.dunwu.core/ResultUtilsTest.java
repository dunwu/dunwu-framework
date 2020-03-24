package io.github.dunwu.core;

import com.alibaba.fastjson.JSON;
import io.github.dunwu.common.*;
import io.github.dunwu.common.constant.ResultStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/15
 */
public class ResultUtilsTest {

    @Test
    void testBaseResult() {
        System.out.println(JSON.toJSONString(BaseResult.success()));
        System.out.println(JSON.toJSONString(BaseResult.fail()));
        System.out.println(JSON.toJSONString(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSON.toJSONString(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSON.toJSONString(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSON.toJSONString(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSON.toJSONString(DataResult.success(10000)));
        System.out.println(JSON.toJSONString(DataResult.failData()));
        System.out.println(JSON.toJSONString(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSON.toJSONString(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSON.toJSONString(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSON.toJSONString(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSON.toJSONString(DataListResult.success(Arrays.asList("how", "are", "you"))));
        System.out.println(JSON.toJSONString(DataListResult.failDataList()));
        System.out.println(JSON.toJSONString(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSON.toJSONString(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSON.toJSONString(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSON.toJSONString(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        List<String> list = Arrays.asList("how", "are", "you");
        Pagination<String> pagination = new Pagination<>(1, 10, list.size(), list);
        System.out.println(JSON.toJSONString(PageResult.success(pagination)));
        System.out.println(JSON.toJSONString(PageResult.failDataPage()));
        System.out.println(JSON.toJSONString(PageResult.failDataPage(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSON.toJSONString(PageResult.failDataPage(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSON.toJSONString(PageResult.failDataPage(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSON.toJSONString(PageResult.failDataPage(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        Map<String, Integer> map = new HashMap<>(3);
        map.put("MONDAY", 1);
        map.put("TUESDAY", 2);
        map.put("WEDNESDAY", 3);
        System.out.println(JSON.toJSONString(MapResult.success(map)));
        System.out.println(JSON.toJSONString(MapResult.failDataMap()));
        System.out.println(JSON.toJSONString(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSON.toJSONString(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSON.toJSONString(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSON.toJSONString(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));
    }

}
