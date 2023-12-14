package io.github.dunwu.tool.data;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.MapResult;
import io.github.dunwu.tool.data.response.PageImpl;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Result 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/15
 */
public class ResultTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String GLOBAL_DATA = "Hello World";
    private final List<String> GLOBAL_LIST;
    private final Map<String, Integer> GLOBAL_MAP = new HashMap<>(3);
    private final Page<String> GLOBAL_PAGE;

    {
        GLOBAL_LIST = Arrays.asList("how", "are", "you");
        GLOBAL_MAP.put("MONDAY", 1);
        GLOBAL_MAP.put("TUESDAY", 2);
        GLOBAL_MAP.put("WEDNESDAY", 3);

        PageRequest pageRequest = PageRequest.of(1, 3);
        GLOBAL_PAGE = new PageImpl<>(GLOBAL_LIST, pageRequest, 100);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    @Test
    @DisplayName("测试 Result")
    void testResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.FAIL)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(new Result(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(new Result(ResultStatus.FAIL).isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(new Result()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":10000,\"ok\":true}", objectMapper.writeValueAsString(new Result( 10000)));
        Assertions.assertTrue(new Result().isOk());

        Result dataResult = new Result(GLOBAL_DATA);
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":\"Hello World\",\"ok\":true}", objectMapper.writeValueAsString(dataResult));
        Assertions.assertEquals(GLOBAL_DATA, dataResult.getData());

        Result dataListResult = new Result(GLOBAL_LIST);
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(dataListResult));
        Assertions.assertEquals(GLOBAL_LIST, dataListResult.getData());

        Result mapResult = new Result(GLOBAL_MAP);
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(mapResult));
        Assertions.assertEquals(GLOBAL_MAP, mapResult.getData());

        Result pageResult = new Result(GLOBAL_PAGE);
        Assertions.assertEquals(ResultStatus.OK.getCode(), pageResult.getCode());
        Assertions.assertEquals(ResultStatus.OK.getMsg(), pageResult.getMsg());
        Page<String> page = (Page<String>) pageResult.getData();
        Assertions.assertEquals(GLOBAL_LIST, page.getContent());

        // @formatter:on
    }

    @Test
    @DisplayName("测试 DataResult")
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

        Result result1 = new Result(GLOBAL_DATA);
        DataResult<String> result2 = BeanUtil.toBean(result1, DataResult.class);
        Assertions.assertEquals(result1, result2);

        DataResult<String> result3 = new DataResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @Test
    @DisplayName("测试 DataListResult")
    void testDataListResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataListResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(GLOBAL_LIST)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(GLOBAL_LIST, "请求成功")));
        Assertions.assertTrue(DataListResult.ok().isOk());

        Result result1 = new Result(GLOBAL_LIST);
        DataListResult<String> result2 = BeanUtil.toBean(result1, DataListResult.class);
        Assertions.assertEquals(result1, result2);

        DataListResult<String> result3 = new DataListResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @Test
    @DisplayName("测试 MapResult")
    void testMapResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(MapResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(GLOBAL_MAP)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(GLOBAL_MAP, "请求成功")));
        Assertions.assertTrue(MapResult.ok().isOk());

        Result result1 = new Result(GLOBAL_MAP);
        MapResult<String, Integer> result2 = BeanUtil.toBean(result1, MapResult.class);
        Assertions.assertEquals(result1, result2);

        MapResult<String, Integer> result3 = new MapResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @Test
    @DisplayName("测试 PageResult")
    void testPageResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail()));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED)));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"未授权访问资源\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), ResultStatus.HTTP_UNAUTHORIZED.getMsg())));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":401,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(PageResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(PageResult.ok()));
        PageResult<String> pageResult = PageResult.ok(GLOBAL_PAGE, "请求成功");
        Assertions.assertEquals(ResultStatus.OK.getCode(), pageResult.getCode());
        Assertions.assertEquals("请求成功", pageResult.getMsg());
        Assertions.assertEquals(GLOBAL_PAGE, pageResult.getData());
        Assertions.assertTrue(PageResult.ok().isOk());

        Result result1 = new Result(GLOBAL_PAGE);
        PageResult<String> result2 = BeanUtil.toBean(result1, PageResult.class);
        Assertions.assertEquals(result1, result2);

        PageResult<String> result3 = new PageResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

}
