package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

/**
 * {@link PageResult} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class PageResultTest {

    private final PageQuery DEFAULT_QUERY = new PageQuery();

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            PageResult<String> result = new PageResult<>();
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("data 构造方法")
        public void testDataConstructor() {
            PageImpl<String> page = new PageImpl<>(List.of("a", "b"), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>(page);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("拷贝构造方法")
        public void testCopyConstructor() {
            PageImpl<String> page = new PageImpl<>(List.of("a"), DEFAULT_QUERY);
            PageResult<String> original = new PageResult<>(page);
            original.setCode("200");
            original.setMsg("success");
            PageResult<String> copy = new PageResult<>(original);
            Assertions.assertThat(copy.getCode()).isEqualTo("200");
            Assertions.assertThat(copy.getMsg()).isEqualTo("success");
            Assertions.assertThat(copy.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("CodeMsg 构造方法")
        public void testCodeMsgConstructor() {
            PageResult<String> result = new PageResult<>(ResultCode.FAIL);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            PageResult<String> result = new PageResult<>(ResultCode.FAIL, "自定义错误");
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("CodeMsg + msg + data 构造方法")
        public void testCodeMsgWithDataConstructor() {
            PageImpl<String> page = new PageImpl<>(List.of("x"), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>(ResultCode.SUCCESS, "成功", page);
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("code + messages 构造方法")
        public void testCodeWithMessagesConstructor() {
            PageResult<String> result = new PageResult<>("500", List.of("错误1", "错误2"));
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("code + msg 构造方法")
        public void testCodeWithMsgConstructor() {
            PageResult<String> result = new PageResult<>("404", "未找到");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("未找到");
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            PageImpl<String> page = new PageImpl<>(List.of("a"), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>("200", "成功", page);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getMsg()).isEqualTo("成功");
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

    }

    @Nested
    @DisplayName("getData 方法测试")
    class GetDataTest {

        @Test
        @DisplayName("getData - 有数据")
        public void testGetData() {
            PageImpl<String> page = new PageImpl<>(List.of("a", "b"), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>(page);
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("getData - null 数据")
        public void testGetDataNull() {
            PageResult<String> result = new PageResult<>();
            Assertions.assertThat(result.getData()).isNull();
        }

    }

    @Nested
    @DisplayName("hasData 方法测试")
    class HasDataTest {

        @Test
        @DisplayName("hasData - 有数据")
        public void testHasData() {
            PageImpl<String> page = new PageImpl<>(List.of("a"), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>(page);
            Assertions.assertThat(result.hasData()).isTrue();
        }

        @Test
        @DisplayName("hasData - 空分页")
        public void testHasDataEmpty() {
            PageImpl<String> page = new PageImpl<>(Collections.emptyList(), DEFAULT_QUERY);
            PageResult<String> result = new PageResult<>(page);
            Assertions.assertThat(result.hasData()).isFalse();
        }

        @Test
        @DisplayName("hasData - null 数据")
        public void testHasDataNull() {
            PageResult<String> result = new PageResult<>();
            Assertions.assertThat(result.hasData()).isFalse();
        }

    }

    @Nested
    @DisplayName("静态工厂方法测试")
    class StaticFactoryTest {

        @Test
        @DisplayName("fail()")
        public void testFail() {
            PageResult<String> result = PageResult.fail();
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("fail(msg)")
        public void testFailWithMsg() {
            PageResult<String> result = PageResult.fail("系统异常");
            Assertions.assertThat(result.isFail()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("系统异常");
        }

        @Test
        @DisplayName("ok()")
        public void testOk() {
            PageResult<String> result = PageResult.ok();
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("ok(Page)")
        public void testOkWithPage() {
            Page<String> page = new PageImpl<>(List.of("a", "b"), DEFAULT_QUERY);
            PageResult<String> result = PageResult.ok(page);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isNotNull();
        }

        @Test
        @DisplayName("ok(Page, msg)")
        public void testOkWithPageAndMsg() {
            Page<String> page = new PageImpl<>(List.of("a"), DEFAULT_QUERY);
            PageResult<String> result = PageResult.ok(page, "操作成功");
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getMsg()).isEqualTo("操作成功");
        }

        @Test
        @DisplayName("build(CodeMsg)")
        public void testBuildWithCodeMsg() {
            PageResult<String> result = PageResult.build(ResultCode.SUCCESS);
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg)")
        public void testBuildWithCodeMsgAndMsg() {
            PageResult<String> result = PageResult.build(ResultCode.FAIL, "失败");
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("build(CodeMsg, msg, data)")
        public void testBuildWithCodeMsgMsgAndData() {
            PageImpl<String> page = new PageImpl<>(List.of("x"), DEFAULT_QUERY);
            PageResult<String> result = PageResult.build(ResultCode.SUCCESS, "成功", page);
            Assertions.assertThat(result.isOk()).isTrue();
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("build(code, msg)")
        public void testBuildWithCodeAndMsg() {
            PageResult<String> result = PageResult.build("200", "成功");
            Assertions.assertThat(result.getCode()).isEqualTo("200");
        }

        @Test
        @DisplayName("build(code, msg, data)")
        public void testBuildWithCodeMsgAndData() {
            PageImpl<String> page = new PageImpl<>(List.of("a"), DEFAULT_QUERY);
            PageResult<String> result = PageResult.build("200", "成功", page);
            Assertions.assertThat(result.getCode()).isEqualTo("200");
            Assertions.assertThat(result.getData()).isEqualTo(page);
        }

        @Test
        @DisplayName("build(code, messages)")
        public void testBuildWithCodeAndMessages() {
            PageResult<String> result = PageResult.build("500", List.of("错误1", "错误2"));
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
            PageResult<String> result = new PageResult<>();
            PageImpl<String> newData = new PageImpl<>(List.of("new"), DEFAULT_QUERY);
            result.setData(newData);
            Assertions.assertThat(result.getData()).isEqualTo(newData);
        }

    }

}
