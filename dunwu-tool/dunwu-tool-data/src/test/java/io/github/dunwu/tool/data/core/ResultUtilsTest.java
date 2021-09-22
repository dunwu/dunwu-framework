package io.github.dunwu.tool.data.core;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.tool.data.core.constant.ResultStatus;
import io.github.dunwu.tool.data.mybatis.util.MybatisPlusUtil;
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
        System.out.println(JSONUtil.toJsonStr(BaseResult.ok()));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail()));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSONUtil.toJsonStr(DataResult.ok(10000)));
        System.out.println(JSONUtil.toJsonStr(DataResult.fail()));
        System.out.println(JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        System.out.println(JSONUtil.toJsonStr(DataListResult.ok(Arrays.asList("how", "are", "you"))));
        System.out.println(JSONUtil.toJsonStr(DataListResult.fail()));
        System.out.println(JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        List<String> list = Arrays.asList("how", "are", "you");
        Page<String> page = new Page<>(1, 10, list.size(), true);
        page.setRecords(list);
        System.out.println(JSONUtil.toJsonStr(PageResult.ok(MybatisPlusUtil.toSpringPage(page))));
        System.out.println(JSONUtil.toJsonStr(PageResult.fail()));
        System.out.println(JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));

        Map<String, Integer> map = new HashMap<>(3);
        map.put("MONDAY", 1);
        map.put("TUESDAY", 2);
        map.put("WEDNESDAY", 3);
        System.out.println(JSONUtil.toJsonStr(MapResult.ok(map)));
        System.out.println(JSONUtil.toJsonStr(MapResult.fail()));
        System.out.println(JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        System.out.println(JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        System.out.println(JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            "%s 没有 %s 权限", "user", "write")));
        System.out.println(JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(),
            Arrays.asList("执行", ""))));
    }

}
