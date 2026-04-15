package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link DataListResult} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class DataListResultTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            DataListResult<String> result = new DataListResult<>();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("data 构造方法")
        public void testDataConstructor() {
            List<String> data = Arrays.asList("a", "b", "c");
            DataListResult<String> result = new DataListResult<>(data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("拷贝构造方法")
        public void testCopyConstructor() {
            List<String> data = Arrays.asList("a", "b");
            DataListResult<String> original = new DataListResult<>(data);
            original.setCode("200");
            original.setMsg("success");
            DataListResult<String> copy = new DataListResult<>(original);
            Assertions.assertThat(copy.getCode()).isEqualTo("200");
            Assertions.assertThat(copy.getMsg()).isEqualTo("success");
            Assertions.assertThat(copy.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("CodeMsg 构造方法")
        public void testCodeMsgConstructor() {
            DataListResult<String> result = new DataListResult<>(ResultCode.FAIL);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            DataListResult<String> result = new DataListResult<>(ResultCode.FAIL, "自定义错误");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg + data 构造方法")
        public void testCodeMsgWithDataConstructor() {
            List<String> data = Arrays.asList("x", "y");
            DataListResult<String> result = new DataListResult<>(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("code + messages 构造方法")
        public void testCodeWithMessagesConstructor() {
            DataListResult<String> result = new DataListResult<>("500", Arrays.asList("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("code + msg 构造方法")
        public void testCodeWithMsgConstructor() {
            DataListResult<String> result = new DataListResult<>("404", "未找到");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("未找到");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            List<String> data = Arrays.asList("a");
            DataListResult<String> result = new DataListResult<>("200", "成功", data);
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
            List<String> data = Arrays.asList("a", "b");
            DataListResult<String> result = new DataListResult<>(data);
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("getData - null 数据")
        public void testGetDataNull() {
            DataListResult<String> result = new DataListResult<>();
            Assertions.assertThat(result.getData()).isNull();
        }

    }

    @Nested
    @DisplayName("hasData 方法测试")
    class HasDataTest {

        @Test
        @DisplayName("hasData - 有数据")
        public void testHasData() {
            DataListResult<String> result = new DataListResult<>(Arrays.asList("a"));
            Assertions.assertThat(result.hasData()).isTrue();
        }

        @Test
        @DisplayName("hasData - 空集合")
        public void testHasDataEmpty() {
            DataListResult<String> result = new DataListResult<>(Collections.emptyList());
            Assertions.assertThat(result.hasData()).isFalse();
        }

        @Test
        @DisplayName("hasData - null 数据")
        public void testHasDataNull() {
            DataListResult<String> result = new DataListResult<>();
            Assertions.assertThat(result.hasData()).isFalse();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("fail()")
        public void testFail() {
            DataListResult<String> result = DataListResult.fail();
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("fail(msg)")
        public void testFailWithMsg() {
            DataListResult<String> result = DataListResult.fail("系统异常");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("系统异常");
        }

        @Test
        @DisplayName("ok()")
        public void testOk() {
            DataListResult<String> result = DataListResult.ok();
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("ok(data)")
        public void testOkWithData() {
            List<String> data = Arrays.asList("a", "b");
            DataListResult<String> result = DataListResult.ok(data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("ok(data, msg)")
        public void testOkWithDataAndMsg() {
            List<String> data = Arrays.asList("a");
            DataListResult<String> result = DataListResult.ok(data, "操作成功");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
            Assertions.assertThat(result.getMsg()).isEqualTo("操作成功");
        }

        @Test
        @DisplayName("build(CodeMsg)")
        public void testBuildWithCodeMsg() {
            DataListResult<String> result = DataListResult.build(ResultCode.SUCCESS);
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg)")
        public void testBuildWithCodeMsgAndMsg() {
            DataListResult<String> result = DataListResult.build(ResultCode.FAIL, "失败");
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg, data)")
        public void testBuildWithCodeMsgMsgAndData() {
            List<String> data = Arrays.asList("x");
            DataListResult<String> result = DataListResult.build(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, msg)")
        public void testBuildWithCodeAndMsg() {
            DataListResult<String> result = DataListResult.build("200", "成功");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
        }

        @Test
        @DisplayName("build(code, msg, data)")
        public void testBuildWithCodeMsgAndData() {
            List<String> data = Arrays.asList("a");
            DataListResult<String> result = DataListResult.build("200", "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, messages)")
        public void testBuildWithCodeAndMessages() {
            DataListResult<String> result = DataListResult.build("500", Arrays.asList("错误1", "错误2"));
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
            DataListResult<String> result = new DataListResult<>();
            List<String> newData = Arrays.asList("new");
            result.setData(newData);
            Assertions.assertThat(result.getData()).isEqualTo(newData);
        }

    }

}
