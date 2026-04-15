package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link MapResult} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class MapResultTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            MapResult<String, Integer> result = new MapResult<>();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("data 构造方法")
        public void testDataConstructor() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = new MapResult<>(data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("拷贝构造方法")
        public void testCopyConstructor() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> original = new MapResult<>(data);
            original.setCode("200");
            original.setMsg("success");
            MapResult<String, Integer> copy = new MapResult<>(original);
            Assertions.assertThat(copy.getCode()).isEqualTo("200");
            Assertions.assertThat(copy.getMsg()).isEqualTo("success");
            Assertions.assertThat(copy.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("CodeMsg 构造方法")
        public void testCodeMsgConstructor() {
            MapResult<String, Integer> result = new MapResult<>(ResultCode.FAIL);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            MapResult<String, Integer> result = new MapResult<>(ResultCode.FAIL, "自定义错误");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg + data 构造方法")
        public void testCodeMsgWithDataConstructor() {
            Map<String, Integer> data = new HashMap<>();
            data.put("x", 10);
            MapResult<String, Integer> result = new MapResult<>(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("code + messages 构造方法")
        public void testCodeWithMessagesConstructor() {
            MapResult<String, Integer> result = new MapResult<>("500", Arrays.asList("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("code + msg 构造方法")
        public void testCodeWithMsgConstructor() {
            MapResult<String, Integer> result = new MapResult<>("404", "未找到");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("未找到");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = new MapResult<>("200", "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

    }

    @Nested
    @DisplayName("getData 方法测试")
    class GetDataTest {

        @Test
        @DisplayName("getData - 有数据")
        public void testGetData() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = new MapResult<>(data);
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("getData - null 数据")
        public void testGetDataNull() {
            MapResult<String, Integer> result = new MapResult<>();
            Assertions.assertThat(result.getData()).isNull();
        }

    }

    @Nested
    @DisplayName("hasData 方法测试")
    class HasDataTest {

        @Test
        @DisplayName("hasData - 有数据")
        public void testHasData() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = new MapResult<>(data);
            Assertions.assertThat(result.hasData()).isTrue();
        }

        @Test
        @DisplayName("hasData - 空 Map")
        public void testHasDataEmpty() {
            MapResult<String, Integer> result = new MapResult<>(Collections.emptyMap());
            Assertions.assertThat(result.hasData()).isFalse();
        }

        @Test
        @DisplayName("hasData - null 数据")
        public void testHasDataNull() {
            MapResult<String, Integer> result = new MapResult<>();
            Assertions.assertThat(result.hasData()).isFalse();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("fail()")
        public void testFail() {
            MapResult<String, Integer> result = MapResult.fail();
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("fail(msg)")
        public void testFailWithMsg() {
            MapResult<String, Integer> result = MapResult.fail("系统异常");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("系统异常");
        }

        @Test
        @DisplayName("ok()")
        public void testOk() {
            MapResult<String, Integer> result = MapResult.ok();
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("ok(data)")
        public void testOkWithData() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = MapResult.ok(data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("ok(data, msg)")
        public void testOkWithDataAndMsg() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = MapResult.ok(data, "操作成功");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
            Assertions.assertThat(result.getMsg()).isEqualTo("操作成功");
        }

        @Test
        @DisplayName("build(CodeMsg)")
        public void testBuildWithCodeMsg() {
            MapResult<String, Integer> result = MapResult.build(ResultCode.SUCCESS);
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg)")
        public void testBuildWithCodeMsgAndMsg() {
            MapResult<String, Integer> result = MapResult.build(ResultCode.FAIL, "失败");
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg, data)")
        public void testBuildWithCodeMsgMsgAndData() {
            Map<String, Integer> data = new HashMap<>();
            data.put("x", 10);
            MapResult<String, Integer> result = MapResult.build(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, msg)")
        public void testBuildWithCodeAndMsg() {
            MapResult<String, Integer> result = MapResult.build("200", "成功");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
        }

        @Test
        @DisplayName("build(code, msg, data)")
        public void testBuildWithCodeMsgAndData() {
            Map<String, Integer> data = new HashMap<>();
            data.put("a", 1);
            MapResult<String, Integer> result = MapResult.build("200", "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, messages)")
        public void testBuildWithCodeAndMessages() {
            MapResult<String, Integer> result = MapResult.build("500", Arrays.asList("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setData")
        public void testSetData() {
            MapResult<String, Integer> result = new MapResult<>();
            Map<String, Integer> newData = new HashMap<>();
            newData.put("new", 99);
            result.setData(newData);
            Assertions.assertThat(result.getData()).isEqualTo(newData);
        }

    }

}
