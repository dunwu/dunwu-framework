package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.DefaultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * {@link Result} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class ResultTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法 - 初始化为成功状态")
        public void testDefaultConstructor() {
            Result result = new Result() { };
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.SUCCESS.getMsg());
        }

        @Test
        @DisplayName("CodeMsg 构造方法 - 正常参数")
        public void testCodeMsgConstructor() {
            Result result = new Result(ResultCode.FAIL) { };
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo(ResultCode.FAIL.getMsg());
        }

        @Test
        @DisplayName("CodeMsg 构造方法 - null 参数抛异常")
        public void testCodeMsgConstructorWithNull() {
            Assertions.assertThatThrownBy(() -> new Result(null) { }).isInstanceOf(DefaultException.class)
                .hasMessageContaining("参数不能为 null");
        }

        @Test
        @DisplayName("CodeMsg + msg 构造方法")
        public void testCodeMsgWithMsgConstructor() {
            Result result = new Result(ResultCode.FAIL, "自定义错误信息") { };
            Assertions.assertThat(result.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            Assertions.assertThat(result.getMsg()).isEqualTo("自定义错误信息");
        }

        @Test
        @DisplayName("code + messages 集合构造方法")
        public void testCodeWithMessagesConstructor() {
            Result result = new Result("500", Arrays.asList("错误1", "错误2")) { };
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isEqualTo("错误1,错误2");
        }

        @Test
        @DisplayName("code + messages 集合构造方法 - null 集合")
        public void testCodeWithNullMessagesConstructor() {
            Result result = new Result("500", (java.util.Collection<String>)null) { };
            Assertions.assertThat(result.getCode()).isEqualTo("500");
            Assertions.assertThat(result.getMsg()).isNull();
        }

        @Test
        @DisplayName("全参构造方法 - 正常参数")
        public void testFullConstructor() {
            Result result = new Result("404", "资源未找到") { };
            Assertions.assertThat(result.getCode()).isEqualTo("404");
            Assertions.assertThat(result.getMsg()).isEqualTo("资源未找到");
        }

        @Test
        @DisplayName("全参构造方法 - null code 抛异常")
        public void testFullConstructorWithNullCode() {
            Assertions.assertThatThrownBy(() -> new Result((String)null, "消息") { })
                .isInstanceOf(DefaultException.class).hasMessageContaining("状态码不能为 null");
        }

    }

    @Nested
    @DisplayName("业务方法测试")
    class BusinessMethodTest {

        @Test
        @DisplayName("isOk - 成功状态")
        public void testIsOkSuccess() {
            Result result = new Result(ResultCode.SUCCESS) { };
            Assertions.assertThat(result.isOk()).isTrue();
        }

        @Test
        @DisplayName("isOk - 失败状态")
        public void testIsOkFail() {
            Result result = new Result(ResultCode.FAIL) { };
            Assertions.assertThat(result.isOk()).isFalse();
        }

        @Test
        @DisplayName("isFail - 成功状态")
        public void testIsFailSuccess() {
            Result result = new Result(ResultCode.SUCCESS) { };
            Assertions.assertThat(result.isFail()).isFalse();
        }

        @Test
        @DisplayName("isFail - 失败状态")
        public void testIsFailFail() {
            Result result = new Result(ResultCode.FAIL) { };
            Assertions.assertThat(result.isFail()).isTrue();
        }

        @Test
        @DisplayName("getData - 基础 Result 抛异常")
        public void testGetDataThrowsException() {
            Result result = new Result() { };
            Assertions.assertThatThrownBy(result::getData).isInstanceOf(DefaultException.class)
                .hasMessageContaining("不支持");
        }

    }

    @Nested
    @DisplayName("equals/hashCode 测试")
    class EqualsHashCodeTest {

        @Test
        @DisplayName("equals - 相同对象")
        public void testEqualsSameObject() {
            Result result = new Result("200", "成功") { };
            Assertions.assertThat(result.equals(result)).isTrue();
        }

        @Test
        @DisplayName("equals - null 对象")
        public void testEqualsNull() {
            Result result = new Result("200", "成功") { };
            Assertions.assertThat(result.equals(null)).isFalse();
        }

        @Test
        @DisplayName("equals - 不同类型")
        public void testEqualsDifferentType() {
            Result result = new Result("200", "成功") { };
            Assertions.assertThat(result.equals("string")).isFalse();
        }

        /**
         * 测试equals方法 - 当两个Result对象的code和msg都相同时应返回true
         */
        @Test
        @DisplayName("equals - code 和 msg 均相同")
        public void testEqualsSameCodeAndMsg() {
            Result result1 = new Result("200", "成功") { };
            Result result2 = new Result("200", "成功") { };
            Assertions.assertThat(result1.equals(result2)).isTrue();
        }

        @Test
        @DisplayName("equals - code 相同但 msg 不同")
        public void testEqualsSameCodeDifferentMsg() {
            Result result1 = new Result("200", "成功") { };
            Result result2 = new Result("200", "操作成功") { };
            Assertions.assertThat(result1.equals(result2)).isFalse();
        }

        @Test
        @DisplayName("equals - code 不同")
        public void testEqualsDifferentCode() {
            Result result1 = new Result("200", "成功") { };
            Result result2 = new Result("500", "失败") { };
            Assertions.assertThat(result1.equals(result2)).isFalse();
        }

        @Test
        @DisplayName("hashCode - 相同对象哈希值相同")
        public void testHashCodeSame() {
            Result result1 = new Result("200", "成功") { };
            Result result2 = new Result("200", "成功") { };
            Assertions.assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
        }

        @Test
        @DisplayName("hashCode - 不同对象哈希值不同")
        public void testHashCodeDifferent() {
            Result result1 = new Result("200", "成功") { };
            Result result2 = new Result("500", "失败") { };
            Assertions.assertThat(result1.hashCode()).isNotEqualTo(result2.hashCode());
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setCode")
        public void testSetCode() {
            Result result = new Result() { };
            result.setCode("404");
            Assertions.assertThat(result.getCode()).isEqualTo("404");
        }

        @Test
        @DisplayName("setMsg")
        public void testSetMsg() {
            Result result = new Result() { };
            result.setMsg("新消息");
            Assertions.assertThat(result.getMsg()).isEqualTo("新消息");
        }

    }

}
