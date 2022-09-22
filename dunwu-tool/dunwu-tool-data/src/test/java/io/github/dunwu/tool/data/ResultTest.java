package io.github.dunwu.tool.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    @Test
    void testResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.FAIL)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(new Result(ResultStatus.FAIL).isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"ok\":true}", objectMapper.writeValueAsString(new Result()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"success\",\"toast\":null,\"ok\":true}", objectMapper.writeValueAsString(new Result("success")));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"ok\":true}", objectMapper.writeValueAsString(new Result( "请求成功")));
        Assertions.assertTrue(new Result().isOk());

        // @formatter:on
    }

    @Test
    void testDataResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":\"success\",\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok("success")));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":\"success\",\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok("success", "请求成功")));
        Assertions.assertTrue(DataResult.ok().isOk());

        // @formatter:on
    }


    @Test
    void testDataListResult() throws JsonProcessingException {

        // @formatter:off

        List<String> list = Arrays.asList("how", "are", "you");
        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataListResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(list)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(list, "请求成功")));
        Assertions.assertTrue(DataListResult.ok().isOk());

        // @formatter:on
    }

    @Test
    void testMapResult() throws JsonProcessingException {

        // @formatter:off

        Map<String, Integer> map = new HashMap<>(3);
        map.put("MONDAY", 1);
        map.put("TUESDAY", 2);
        map.put("WEDNESDAY", 3);
        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(MapResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(map)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(map, "请求成功")));
        Assertions.assertTrue(MapResult.ok().isOk());

        // @formatter:on
    }

    @Test
    void testPageResult() throws JsonProcessingException {

        // @formatter:off

        PageRequest pageRequest =  PageRequest.of(1, 10);
        List<String> list = Arrays.asList("how", "are", "you");
        Page<String> page = new PageImpl<>(list, pageRequest, 1000);
        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(PageResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(PageResult.ok()));
        PageResult<String> pageResult = PageResult.ok(page, "请求成功");
        Assertions.assertEquals(ResultStatus.OK.getCode(), pageResult.getCode());
        Assertions.assertEquals("请求成功", pageResult.getMsg());
        Assertions.assertEquals(page, pageResult.getData());
        Assertions.assertTrue(PageResult.ok().isOk());

        // @formatter:on
    }
}
