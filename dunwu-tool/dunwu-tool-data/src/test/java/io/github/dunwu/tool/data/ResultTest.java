package io.github.dunwu.tool.data;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.data.constant.enums.ResultStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/15
 */
public class ResultTest {

    @Test
    void testBaseResult() {

        // @formatter:off


        Assertions.assertEquals("{\"code\":-1,\"message\":\"失败\"}", JSONUtil.toJsonStr(DataResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"message\":\"成功\"}", JSONUtil.toJsonStr(DataResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"data\":\"success\",\"message\":\"成功\"}", JSONUtil.toJsonStr(DataResult.ok("success")));
        Assertions.assertEquals("{\"code\":0,\"data\":\"success\",\"message\":\"请求成功\"}", JSONUtil.toJsonStr(DataResult.ok("success", "请求成功")));
        Assertions.assertTrue(DataResult.ok().isOk());


        List<String> list = Arrays.asList("how", "are", "you");
        Assertions.assertEquals("{\"code\":-1,\"message\":\"失败\"}", JSONUtil.toJsonStr(DataListResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataListResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"message\":\"成功\"}", JSONUtil.toJsonStr(DataListResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"data\":[\"how\",\"are\",\"you\"],\"message\":\"成功\"}", JSONUtil.toJsonStr(DataListResult.ok(list)));
        Assertions.assertEquals("{\"code\":0,\"data\":[\"how\",\"are\",\"you\"],\"message\":\"请求成功\"}", JSONUtil.toJsonStr(DataListResult.ok(list, "请求成功")));
        Assertions.assertTrue(DataListResult.ok().isOk());


        Map<String, Integer> map = new HashMap<>(3);
        map.put("MONDAY", 1);
        map.put("TUESDAY", 2);
        map.put("WEDNESDAY", 3);
        Assertions.assertEquals("{\"code\":-1,\"message\":\"失败\"}", JSONUtil.toJsonStr(MapResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(MapResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"message\":\"成功\"}", JSONUtil.toJsonStr(MapResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"data\":{\"TUESDAY\":2,\"WEDNESDAY\":3,\"MONDAY\":1},\"message\":\"成功\"}", JSONUtil.toJsonStr(MapResult.ok(map)));
        Assertions.assertEquals("{\"code\":0,\"data\":{\"TUESDAY\":2,\"WEDNESDAY\":3,\"MONDAY\":1},\"message\":\"请求成功\"}", JSONUtil.toJsonStr(MapResult.ok(map, "请求成功")));
        Assertions.assertTrue(MapResult.ok().isOk());


        PageRequest pageRequest =  PageRequest.of(1, 10);
        Page<String> page = new PageImpl<>(list, pageRequest, 1000);
        Assertions.assertEquals("{\"code\":-1,\"message\":\"失败\"}", JSONUtil.toJsonStr(PageResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"message\":\"未授权访问资源\"}", JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMessage())));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"message\":\"[\\\"错误一\\\",\\\"错误二\\\"]\"}", JSONUtil.toJsonStr(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(PageResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"message\":\"成功\"}", JSONUtil.toJsonStr(PageResult.ok()));
        PageResult<String> pageResult = PageResult.ok(page, "请求成功");
        Assertions.assertEquals(ResultStatus.OK.getCode(), pageResult.getCode());
        Assertions.assertEquals("请求成功", pageResult.getMessage());
        Assertions.assertEquals(page, pageResult.getData());
        Assertions.assertTrue(PageResult.ok().isOk());

        // @formatter:on
    }

}
