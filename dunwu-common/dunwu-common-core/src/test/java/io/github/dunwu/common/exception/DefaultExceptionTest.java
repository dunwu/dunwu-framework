package io.github.dunwu.common.exception;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link DefaultException} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
@DisplayName("DefaultException 单元测试")
class DefaultExceptionTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造使用 FAIL 错误码")
        void shouldConstructWithFailCodeByDefault() {
            DefaultException exception = new DefaultException();

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            assertThat(exception.getMsg()).isEqualTo(ResultCode.FAIL.getMsg());
        }

        @Test
        @DisplayName("使用自定义消息构造")
        void shouldConstructWithCustomMessage() {
            DefaultException exception = new DefaultException("自定义错误消息");

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            assertThat(exception.getMsg()).isEqualTo("自定义错误消息");
        }

        @Test
        @DisplayName("使用 CodeMsg 构造")
        void shouldConstructWithCodeMsg() {
            DefaultException exception = new DefaultException(ResultCode.PARAM_ERROR);

            assertThat(exception.getCode()).isEqualTo("PARAM_ERROR");
            assertThat(exception.getMsg()).isEqualTo("参数错误");
        }

        @Test
        @DisplayName("使用 CodeMsg 和自定义消息构造")
        void shouldConstructWithCodeMsgAndCustomMessage() {
            DefaultException exception = new DefaultException(ResultCode.PARAM_ERROR, "参数不能为空");

            assertThat(exception.getCode()).isEqualTo("PARAM_ERROR");
            assertThat(exception.getMsg()).isEqualTo("参数不能为空");
        }

        @Test
        @DisplayName("使用错误码和消息构造")
        void shouldConstructWithCodeAndMessage() {
            DefaultException exception = new DefaultException("CUSTOM_CODE", "自定义消息");

            assertThat(exception.getCode()).isEqualTo("CUSTOM_CODE");
            assertThat(exception.getMsg()).isEqualTo("自定义消息");
        }

        @Test
        @DisplayName("使用原始异常构造")
        void shouldConstructWithCause() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = new DefaultException(cause);

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常和自定义消息构造")
        void shouldConstructWithCauseAndMessage() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = new DefaultException(cause, "包装消息");

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
            assertThat(exception.getMsg()).isEqualTo("包装消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常和 CodeMsg 构造")
        void shouldConstructWithCauseAndCodeMsg() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = new DefaultException(cause, ResultCode.ERROR);

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getMsg()).isEqualTo("服务器内部异常");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、CodeMsg 和自定义消息构造")
        void shouldConstructWithCauseCodeMsgAndMessage() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = new DefaultException(cause, ResultCode.ERROR, "自定义包装消息");

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getMsg()).isEqualTo("自定义包装消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、错误码和消息构造")
        void shouldConstructWithCauseCodeAndMessage() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = new DefaultException(cause, "MY_CODE", "我的消息");

            assertThat(exception.getCode()).isEqualTo("MY_CODE");
            assertThat(exception.getMsg()).isEqualTo("我的消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

    }

    @Nested
    @DisplayName("静态工厂方法 of() 测试")
    class OfFactoryMethodTest {

        @Test
        @DisplayName("使用 CodeMsg 创建异常")
        void shouldCreateExceptionWithCodeMsg() {
            DefaultException exception = DefaultException.of(ResultCode.FAIL);

            assertThat(exception.getCode()).isEqualTo("FAIL");
            assertThat(exception.getMsg()).isEqualTo("业务执行失败");
        }

        @Test
        @DisplayName("使用 CodeMsg 并覆盖消息")
        void shouldCreateExceptionWithCodeMsgAndOverrideMsg() {
            DefaultException exception = DefaultException.of(ResultCode.FAIL, "覆盖消息");

            assertThat(exception.getCode()).isEqualTo("FAIL");
            assertThat(exception.getMsg()).isEqualTo("覆盖消息");
        }

        @Test
        @DisplayName("使用原始异常和 CodeMsg 创建")
        void shouldCreateExceptionWithCauseAndCodeMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            DefaultException exception = DefaultException.of(cause, ResultCode.ERROR);

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、CodeMsg 和自定义消息")
        void shouldCreateExceptionWithCauseCodeMsgAndMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            DefaultException exception = DefaultException.of(cause, ResultCode.ERROR, "自定义消息");

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getMsg()).isEqualTo("自定义消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

    }

    @Nested
    @DisplayName("静态工厂方法 noStackTrace() 测试")
    class NoStackTraceFactoryMethodTest {

        @Test
        @DisplayName("创建无堆栈异常")
        void shouldCreateNoStackTraceException() {
            DefaultException exception = DefaultException.noStackTrace(ResultCode.FAIL);

            assertThat(exception.getCode()).isEqualTo("FAIL");
            assertThat(exception.getMsg()).isEqualTo("业务执行失败");
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("创建无堆栈但保留原始异常")
        void shouldCreateNoStackTraceExceptionWithCause() {
            RuntimeException cause = new RuntimeException("根因");
            DefaultException exception = DefaultException.noStackTrace(cause, ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getCause()).isEqualTo(cause);
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("无堆栈异常的 fillInStackTrace 返回自身")
        void shouldReturnSelfInFillInStackTrace() {
            DefaultException exception = DefaultException.noStackTrace(ResultCode.FAIL);
            Throwable result = exception.fillInStackTrace();

            assertThat(result).isSameAs(exception);
        }

        @Test
        @DisplayName("codeMsg 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCodeMsgIsNull() {
            assertThatThrownBy(() -> DefaultException.noStackTrace((CodeMsg)null)).isInstanceOf(
                NullPointerException.class).hasMessageContaining("codeMsg must not be null");
        }

        @Test
        @DisplayName("cause 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCauseIsNull() {
            assertThatThrownBy(() -> DefaultException.noStackTrace(null, ResultCode.ERROR)).isInstanceOf(
                NullPointerException.class).hasMessageContaining("cause must not be null");
        }

    }

    @Nested
    @DisplayName("继承关系测试")
    class InheritanceTest {

        @Test
        @DisplayName("继承自 CodeMsgException")
        void shouldExtendCodeMsgException() {
            DefaultException exception = new DefaultException();

            assertThat(exception).isInstanceOf(CodeMsgException.class);
        }

        @Test
        @DisplayName("是 RuntimeException 类型")
        void shouldBeRuntimeException() {
            DefaultException exception = new DefaultException();

            assertThat(exception).isInstanceOf(RuntimeException.class);
        }

    }

    @Nested
    @DisplayName("getErrorMessage() 测试")
    class GetErrorMessageTest {

        @Test
        @DisplayName("格式化消息为 [code]msg 格式")
        void shouldFormatErrorMessage() {
            DefaultException exception = new DefaultException("CODE001", "测试错误");

            assertThat(exception.getErrorMessage()).isEqualTo("[CODE001]测试错误");
        }

        @Test
        @DisplayName("默认构造的格式化消息")
        void shouldFormatDefaultErrorMessage() {
            DefaultException exception = new DefaultException();

            assertThat(exception.getErrorMessage()).isEqualTo("[FAIL]业务执行失败");
        }

    }

}
