package io.github.dunwu.data.core;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.data.mybatis.util.MybatisPlusUtil;
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
        System.out.println(JSONUtil.toJsonStr(BaseResult.success()));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail()));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSONUtil.toJsonStr(DataResult.success(10000)));
        System.out.println(JSONUtil.toJsonStr(DataResult.failData()));
        System.out.println(JSONUtil.toJsonStr(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(DataResult.failData(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSONUtil.toJsonStr(DataListResult.success(Arrays.asList("how", "are", "you"))));
        System.out.println(JSONUtil.toJsonStr(DataListResult.failDataList()));
        System.out.println(JSONUtil.toJsonStr(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(DataListResult.failDataList(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        List<String> list = Arrays.asList("how", "are", "you");
        Page<String> page = new Page<>(1, 10, list.size(), true);
        page.setRecords(list);
        System.out.println(JSONUtil.toJsonStr(PageResult.success(MybatisPlusUtil.toSpringPage(page))));
        System.out.println(JSONUtil.toJsonStr(PageResult.failPageResult()));
        System.out.println(JSONUtil.toJsonStr(PageResult.failPageResult(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(PageResult.failPageResult(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(PageResult.failPageResult(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(PageResult.failPageResult(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        Map<String, Integer> map = new HashMap<>(3);
        map.put("MONDAY", 1);
        map.put("TUESDAY", 2);
        map.put("WEDNESDAY", 3);
        System.out.println(JSONUtil.toJsonStr(MapResult.success(map)));
        System.out.println(JSONUtil.toJsonStr(MapResult.failDataMap()));
        System.out.println(JSONUtil.toJsonStr(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(MapResult.failDataMap(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));
    }

}
