package io.github.dunwu.tool.data;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.MapResult;
import io.github.dunwu.tool.data.response.PageImpl;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
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

    private static final ObjectMapper objectMapper;
    private static final String GLOBAL_DATA = "Hello World";
    private static final List<String> GLOBAL_LIST;
    private static final Map<String, Integer> GLOBAL_MAP = new HashMap<>(3);
    private static final PageImpl<String> GLOBAL_PAGE;

    static {
        GLOBAL_LIST = Arrays.asList("how", "are", "you");
        GLOBAL_MAP.put("MONDAY", 1);
        GLOBAL_MAP.put("TUESDAY", 2);
        GLOBAL_MAP.put("WEDNESDAY", 3);

        PageRequest pageRequest = PageRequest.of(1, 3);
        GLOBAL_PAGE = new PageImpl<>(GLOBAL_LIST, pageRequest, 100);
        objectMapper = JsonMapper.builder()
                                 .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                 .serializationInclusion(JsonInclude.Include.ALWAYS)
                                 .build();
    }

    @RepeatedTest(3)
    @DisplayName("测试 Result")
    void testResult() throws JsonProcessingException {

        // @formatter:off

        Result result = objectMapper.readValue("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"ok\":false}", Result.class);
        Assertions.assertEquals(result, new Result(ResultCode.FAIL));

        Result result2 = objectMapper.readValue("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"ok\":false}", Result.class);
        Assertions.assertEquals(result2, new Result(ResultCode.AUTH_ERROR));

        Result result3 = objectMapper.readValue("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"ok\":false}", Result.class);
        Assertions.assertEquals(result3, new Result(ResultCode.AUTH_ERROR.getCode(), ResultCode.AUTH_ERROR.getMsg()));

        Result result4 = objectMapper.readValue("{\"code\":4000,\"msg\":\"错误一\",\"toast\":\"错误二\",\"ok\":false}", Result.class);
        Assertions.assertEquals(result4, new Result(ResultCode.AUTH_ERROR.getCode(), "错误一", "错误二"));

        Result result5 = objectMapper.readValue("{\"code\":4000,\"msg\":\"错误一,错误二\",\"toast\":null,\"ok\":false}", Result.class);
        Assertions.assertEquals(result5, new Result(ResultCode.AUTH_ERROR.getCode(), Arrays.asList("错误一", "错误二")));

        Result result6 = objectMapper.readValue("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"ok\":true}", Result.class);
        Assertions.assertEquals(result6, new Result());

        Assertions.assertFalse(new Result(ResultCode.FAIL).isOk());
        Assertions.assertTrue(new Result().isOk());

        // @formatter:on
    }

    @RepeatedTest(3)
    @DisplayName("测试 DataResult")
    void testDataResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.fail()));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.build(ResultCode.AUTH_ERROR)));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.build(ResultCode.AUTH_ERROR.getCode(), ResultCode.AUTH_ERROR.getMsg())));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.build(ResultCode.AUTH_ERROR.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataResult.build(ResultCode.AUTH_ERROR.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":\"success\",\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok("success")));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":\"success\",\"ok\":true}", objectMapper.writeValueAsString(DataResult.ok("success", "请求成功")));
        Assertions.assertTrue(DataResult.ok().isOk());

        Result result1 = new Result();
        DataResult<String> result2 = BeanUtil.toBean(result1, DataResult.class);
        result2.setData(GLOBAL_DATA);
        Assertions.assertEquals(result1, result2);

        DataResult<String> result3 = new DataResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @RepeatedTest(3)
    @DisplayName("测试 DataListResult")
    void testDataListResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.fail()));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.build(ResultCode.AUTH_ERROR)));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.build(ResultCode.AUTH_ERROR.getCode(), ResultCode.AUTH_ERROR.getMsg())));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.build(ResultCode.AUTH_ERROR.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(DataListResult.build(ResultCode.AUTH_ERROR.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(DataListResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(GLOBAL_LIST)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":[\"how\",\"are\",\"you\"],\"ok\":true}", objectMapper.writeValueAsString(DataListResult.ok(GLOBAL_LIST, "请求成功")));
        Assertions.assertTrue(DataListResult.ok().isOk());

        Result result1 = new Result();
        DataListResult<String> result2 = BeanUtil.toBean(result1, DataListResult.class);
        result2.setData(GLOBAL_LIST);
        Assertions.assertEquals(result1, result2);

        DataListResult<String> result3 = new DataListResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @RepeatedTest(3)
    @DisplayName("测试 MapResult")
    void testMapResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.fail()));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.build(ResultCode.AUTH_ERROR)));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.build(ResultCode.AUTH_ERROR.getCode(), ResultCode.AUTH_ERROR.getMsg())));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.build(ResultCode.AUTH_ERROR.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(MapResult.build(ResultCode.AUTH_ERROR.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(MapResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok()));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(GLOBAL_MAP)));
        Assertions.assertEquals("{\"code\":0,\"msg\":\"请求成功\",\"toast\":null,\"data\":{\"MONDAY\":1,\"TUESDAY\":2,\"WEDNESDAY\":3},\"ok\":true}", objectMapper.writeValueAsString(MapResult.ok(GLOBAL_MAP, "请求成功")));
        Assertions.assertTrue(MapResult.ok().isOk());

        Result result1 = new Result();
        MapResult<String, Integer> result2 = BeanUtil.toBean(result1, MapResult.class);
        result2.setData(GLOBAL_MAP);
        Assertions.assertEquals(result1, result2);

        MapResult<String, Integer> result3 = new MapResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

    @RepeatedTest(3)
    @DisplayName("测试 PageResult")
    void testPageResult() throws JsonProcessingException {

        // @formatter:off

        Assertions.assertEquals("{\"code\":-1,\"msg\":\"失败\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.fail()));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.build(ResultCode.AUTH_ERROR)));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"认证错误\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.build(ResultCode.AUTH_ERROR.getCode(), ResultCode.AUTH_ERROR.getMsg())));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一\",\"toast\":\"错误二\",\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.build(ResultCode.AUTH_ERROR.getCode(), "错误一", "错误二")));
        Assertions.assertEquals("{\"code\":4000,\"msg\":\"错误一,错误二\",\"toast\":null,\"data\":null,\"ok\":false}", objectMapper.writeValueAsString(PageResult.build(ResultCode.AUTH_ERROR.getCode(), Arrays.asList("错误一", "错误二"))));
        Assertions.assertFalse(PageResult.fail().isOk());
        Assertions.assertEquals("{\"code\":0,\"msg\":\"成功\",\"toast\":null,\"data\":null,\"ok\":true}", objectMapper.writeValueAsString(PageResult.ok()));
        PageResult<String> pageResult = PageResult.ok(GLOBAL_PAGE, "请求成功");
        Assertions.assertEquals(ResultCode.OK.getCode(), pageResult.getCode());
        Assertions.assertEquals("请求成功", pageResult.getMsg());
        Assertions.assertEquals(GLOBAL_PAGE, pageResult.getData());
        Assertions.assertTrue(PageResult.ok().isOk());

        Result result1 = new Result();
        PageResult<String> result2 = BeanUtil.toBean(result1, PageResult.class);
        result2.setData(GLOBAL_PAGE);
        Assertions.assertEquals(result1, result2);

        PageResult<String> result3 = new PageResult<>(result2);
        Assertions.assertEquals(result2, result3);
        Assertions.assertNotSame(result2, result3);

        // @formatter:on
    }

}
