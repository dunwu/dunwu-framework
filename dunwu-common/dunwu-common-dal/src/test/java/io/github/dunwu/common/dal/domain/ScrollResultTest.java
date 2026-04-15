package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * {@link ScrollResult} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class ScrollResultTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            ScrollResult<String> result = new ScrollResult<>();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("data 构造方法")
        public void testDataConstructor() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a", "b"));
            ScrollResult<String> result = new ScrollResult<>(data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("拷贝构造方法")
        public void testCopyConstructor() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            ScrollResult<String> original = new ScrollResult<>(data);
            original.setCode("200");
            original.setMsg("success");
            ScrollResult<String> copy = new ScrollResult<>(original);
            Assertions.assertThat(copy.getCode()).isEqualTo("200");
            Assertions.assertThat(copy.getMsg()).isEqualTo("success");
            Assertions.assertThat(copy.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("CodeMsg 构造方法")
        public void testCodeMsgConstructor() {
            ScrollResult<String> result = new ScrollResult<>(ResultCode.FAIL);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            ScrollResult<String> result = new ScrollResult<>(ResultCode.FAIL, "自定义错误");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg + data 构造方法")
        public void testCodeMsgWithDataConstructor() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("x"));
            ScrollResult<String> result = new ScrollResult<>(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("code + messages 构造方法")
        public void testCodeWithMessagesConstructor() {
            ScrollResult<String> result = new ScrollResult<>("500", Arrays.asList("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("code + msg 构造方法")
        public void testCodeWithMsgConstructor() {
            ScrollResult<String> result = new ScrollResult<>("404", "未找到");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("未找到");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            ScrollResult<String> result = new ScrollResult<>("200", "成功", data);
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
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a", "b"));
            ScrollResult<String> result = new ScrollResult<>(data);
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("getData - null 数据")
        public void testGetDataNull() {
            ScrollResult<String> result = new ScrollResult<>();
            Assertions.assertThat(result.getData()).isNull();
        }

    }

    @Nested
    @DisplayName("hasData 方法测试")
    class HasDataTest {

        @Test
        @DisplayName("hasData - 有数据")
        public void testHasData() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            ScrollResult<String> result = new ScrollResult<>(data);
            Assertions.assertThat(result.hasData()).isTrue();
        }

        @Test
        @DisplayName("hasData - 空内容")
        public void testHasDataEmpty() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Collections.emptyList());
            ScrollResult<String> result = new ScrollResult<>(data);
            Assertions.assertThat(result.hasData()).isFalse();
        }

        @Test
        @DisplayName("hasData - null 数据")
        public void testHasDataNull() {
            ScrollResult<String> result = new ScrollResult<>();
            Assertions.assertThat(result.hasData()).isFalse();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("fail()")
        public void testFail() {
            ScrollResult<String> result = ScrollResult.fail();
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("fail(msg)")
        public void testFailWithMsg() {
            ScrollResult<String> result = ScrollResult.fail("系统异常");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("系统异常");
        }

        @Test
        @DisplayName("ok()")
        public void testOk() {
            ScrollResult<String> result = ScrollResult.ok();
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("ok(data)")
        public void testOkWithData() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a", "b"));
            ScrollResult<String> result = ScrollResult.ok(data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("ok(data, msg)")
        public void testOkWithDataAndMsg() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            ScrollResult<String> result = ScrollResult.ok(data, "操作成功");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
            Assertions.assertThat(result.getMsg()).isEqualTo("操作成功");
        }

        @Test
        @DisplayName("build(CodeMsg)")
        public void testBuildWithCodeMsg() {
            ScrollResult<String> result = ScrollResult.build(ResultCode.SUCCESS);
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg)")
        public void testBuildWithCodeMsgAndMsg() {
            ScrollResult<String> result = ScrollResult.build(ResultCode.FAIL, "失败");
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg, data)")
        public void testBuildWithCodeMsgMsgAndData() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("x"));
            ScrollResult<String> result = ScrollResult.build(ResultCode.SUCCESS, "成功", data);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, msg)")
        public void testBuildWithCodeAndMsg() {
            ScrollResult<String> result = ScrollResult.build("200", "成功");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
        }

        @Test
        @DisplayName("build(code, msg, data)")
        public void testBuildWithCodeMsgAndData() {
            ScrollData<String> data = new ScrollData<>("scroll-1", 10, 100L, Arrays.asList("a"));
            ScrollResult<String> result = ScrollResult.build("200", "成功", data);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getData()).isEqualTo(data);
        }

        @Test
        @DisplayName("build(code, messages)")
        public void testBuildWithCodeAndMessages() {
            ScrollResult<String> result = ScrollResult.build("500", Arrays.asList("错误1", "错误2"));
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
            ScrollResult<String> result = new ScrollResult<>();
            ScrollData<String> newData = new ScrollData<>("scroll-2", 20, 200L, Arrays.asList("new"));
            result.setData(newData);
            Assertions.assertThat(result.getData()).isEqualTo(newData);
        }

    }

}
