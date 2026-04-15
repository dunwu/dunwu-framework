package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * {@link DataResult} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class DataResultTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            DataResult<String> result = new DataResult<>();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("data 构造方法")
        public void testDataConstructor() {
            DataResult<String> result = new DataResult<>("test data");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getData()).isEqualTo("test data");
        }

        @Test
        @DisplayName("拷贝构造方法")
        public void testCopyConstructor() {
            DataResult<String> original = new DataResult<>("original");
            original.setCode("200");
            original.setMsg("success");
            DataResult<String> copy = new DataResult<>(original);
            Assertions.assertThat(copy.getCode()).isEqualTo("200");
            Assertions.assertThat(copy.getMsg()).isEqualTo("success");
            Assertions.assertThat(copy.getData()).isEqualTo("original");
        }

        @Test
        @DisplayName("CodeMsg 构造方法")
        public void testCodeMsgConstructor() {
            DataResult<String> result = new DataResult<>(ResultCode.FAIL);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.FAIL.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            DataResult<String> result = new DataResult<>(ResultCode.FAIL, "自定义错误");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg + data 构造方法")
        public void testCodeMsgWithDataConstructor() {
            DataResult<String> result = new DataResult<>(ResultCode.SUCCESS, "成功", "data");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo("data");
        }

        @Test
        @DisplayName("code + messages 构造方法")
        public void testCodeWithMessagesConstructor() {
            DataResult<String> result = new DataResult<>("500", Arrays.asList("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("code + msg 构造方法")
        public void testCodeWithMsgConstructor() {
            DataResult<String> result = new DataResult<>("404", "未找到");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("未找到");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            DataResult<String> result = new DataResult<>("200", "成功", "data");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo("data");
        }

    }

    @Nested
    @DisplayName("getData 方法测试")
    class GetDataTest {

        @Test
        @DisplayName("getData - 有数据")
        public void testGetData() {
            DataResult<String> result = new DataResult<>("test");
            Assertions.assertThat(result.getData()).isEqualTo("test");
        }

        @Test
        @DisplayName("getData - null 数据")
        public void testGetDataNull() {
            DataResult<String> result = new DataResult<>();
            Assertions.assertThat(result.getData()).isNull();
        }

    }

    @Nested
    @DisplayName("hasData 方法测试")
    class HasDataTest {

        @Test
        @DisplayName("hasData - 有数据")
        public void testHasData() {
            DataResult<String> result = new DataResult<>("test");
            Assertions.assertThat(result.hasData()).isTrue();
        }

        @Test
        @DisplayName("hasData - null 数据")
        public void testHasDataNull() {
            DataResult<String> result = new DataResult<>();
            Assertions.assertThat(result.hasData()).isFalse();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("fail() - 默认失败")
        public void testFail() {
            DataResult<String> result = DataResult.fail();
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
        }

        @Test
        @DisplayName("fail(msg) - 自定义错误信息")
        public void testFailWithMsg() {
            DataResult<String> result = DataResult.fail("系统异常");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("系统异常");
        }

        @Test
        @DisplayName("ok() - 默认成功")
        public void testOk() {
            DataResult<String> result = DataResult.ok();
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("ok(data) - 带数据")
        public void testOkWithData() {
            DataResult<String> result = DataResult.ok("success data");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo("success data");
        }

        @Test
        @DisplayName("ok(data, msg) - 带数据和消息")
        public void testOkWithDataAndMsg() {
            DataResult<String> result = DataResult.ok("data", "操作成功");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo("data");
            Assertions.assertThat(result.getMsg()).isEqualTo("操作成功");
        }

        @Test
        @DisplayName("build(CodeMsg)")
        public void testBuildWithCodeMsg() {
            DataResult<String> result = DataResult.build(ResultCode.SUCCESS);
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg)")
        public void testBuildWithCodeMsgAndMsg() {
            DataResult<String> result = DataResult.build(ResultCode.FAIL, "失败");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("失败");
        }

        @Test
        @DisplayName("build(CodeMsg, msg, data)")
        public void testBuildWithCodeMsgMsgAndData() {
            DataResult<String> result = DataResult.build(ResultCode.SUCCESS, "成功", "data");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo("data");
        }

        @Test
        @DisplayName("build(code, msg)")
        public void testBuildWithCodeAndMsg() {
            DataResult<String> result = DataResult.build("200", "成功");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
        }

        @Test
        @DisplayName("build(code, msg, data)")
        public void testBuildWithCodeMsgAndData() {
            DataResult<String> result = DataResult.build("200", "成功", "data");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getData()).isEqualTo("data");
        }

        @Test
        @DisplayName("build(code, messages)")
        public void testBuildWithCodeAndMessages() {
            DataResult<String> result = DataResult.build("500", Arrays.asList("错误1", "错误2"));
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
            DataResult<String> result = new DataResult<>();
            result.setData("new data");
            Assertions.assertThat(result.getData()).isEqualTo("new data");
        }

    }

}
